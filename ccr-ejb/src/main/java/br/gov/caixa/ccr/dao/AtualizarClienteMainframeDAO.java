package br.gov.caixa.ccr.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.gov.caixa.arqrefcore.barramento.DAOBarramento;
import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.excecao.mensagem.MessageDefault;
import br.gov.caixa.arqrefcore.excecao.mensagem.Messages;
import br.gov.caixa.arqrefcore.jaxb.conversor.ConverterXML;
import br.gov.caixa.arqrefcore.jms.Correlation;
import br.gov.caixa.arqrefservices.dominio.barramento.enumerador.EFilasMQ;
import br.gov.caixa.ccr.dominio.Usuario;
import br.gov.caixa.ccr.dominio.barramento.SibarHeader;
import br.gov.caixa.ccr.dominio.barramento.alteracaocliente.AtualizarCliente;
import br.gov.caixa.ccr.dominio.barramento.alteracaocliente.ManutencaoErro;
import br.gov.caixa.ccr.dominio.barramento.alteracaocliente.MensagemAtualizacaoCliente;
import br.gov.caixa.ccr.dominio.barramento.alteracaocliente.MensagemSicliRetorno;
import br.gov.caixa.ccr.dominio.barramento.alteracaocliente.MensagemSiemp;
import br.gov.caixa.ccr.dominio.barramento.anotacao.SICCRMQ;

public class AtualizarClienteMainframeDAO implements AtualizarClienteDAO{

	private static DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");

	@Inject
	@SICCRMQ
	public DAOBarramento barramentoDAO;

	@Override
	public AtualizarCliente atualizarCliente(AtualizarCliente atualizarCliente, Usuario usuario) throws BusinessException {
		Correlation correlation = enviarDadosBarramentoAlteracaoClienteSicli(atualizarCliente.getMensagemAtualizacao(),
				usuario);
		MensagemSicliRetorno msgRetorno = retornoDadosClienteSicli(correlation.getId());

		verificaRetornoSicli(atualizarCliente, msgRetorno);

		return atualizarCliente;
	}

	private Correlation enviarDadosBarramentoAlteracaoClienteSicli(MensagemAtualizacaoCliente msgEnvio,
			Usuario usuario) {
		String correlationId = null;
		preencheDadosHedearBarramentoAlteraDadosCliente(msgEnvio, usuario);
		String mensagem = ConverterXML.convertToXml(msgEnvio);
		correlationId = barramentoDAO.put(mensagem, EFilasMQ.SICLI_REQ_MANUTENCAO_CLIENTE.toString());
		return new Correlation(correlationId);
	}

	private void preencheDadosHedearBarramentoAlteraDadosCliente(MensagemAtualizacaoCliente msgEnvio, Usuario usuario) {

		SibarHeader header = new SibarHeader();
		header.setVersao("1.0");
		header.setUsuarioServico("SFECEMPD");
		header.setUsuario(usuario.getCodigoUsuario());
		header.setOperacao("ALTERACAO_PF");
		header.setSistemaOrigem("SIEMP");
		header.setUnidade(String.valueOf(usuario.getNumeroUnidadeLogado()));
		header.setDataHora(DF.format(new Date()));
		header.setIdProcesso("ALTERACAO_PF");

		msgEnvio.setSibarHeader(header);
	}

	private MensagemSicliRetorno retornoDadosClienteSicli(String correlationId) {
		MensagemSicliRetorno msgRetorno = null;
		String retorno = null;
		if (!StringUtils.isEmpty(correlationId)) {
			retorno = barramentoDAO.getWithTimeout(correlationId, EFilasMQ.SICLI_RSP_MANUTENCAO_CLIENTE.toString(), 10000L);

			if (!StringUtils.isEmpty(retorno)) {
				msgRetorno = ConverterXML.convertFromXml(retorno, MensagemSicliRetorno.class);
			} else {
				retorno = barramentoDAO.getWithTimeout(correlationId, EFilasMQ.SICLI_RSP_MANUTENCAO_CLIENTE.toString(),
						10000L);
			}
		}
		return msgRetorno;
	}

	private void verificaRetornoSicli(AtualizarCliente atualizarCliente, MensagemSicliRetorno msgRetorno)
			throws BusinessException {
		// VERIFICA SE O RETORNO É NULL
		if (msgRetorno == null) {
			MessageDefault msg = new MessageDefault();
			msg.setCategoriaErro("3");
			msg.setCodigoErro("0");
			msg.setInformacoesAdicionais("Não houve retorno da manutençaõ do Cliente: "
					+ atualizarCliente.getMensagemAtualizacao().getAtualizacao().getCodigo());
			msg.setOrigemErro("MQ");
			msg.setSeveridade(1);
			msg.setSistema("SICLI");
			throw new BusinessException(msg);
		}

		// VERIFICA O COD_RETORNO E LANCA A EXCECAO
		if (msgRetorno.getCodigoRetorno().equalsIgnoreCase("MANUTENCAO_ERRO")) {
			Messages listaMessages = new Messages();
			for (ManutencaoErro erro : msgRetorno.getDados().getManutencaoErroList()) {
				MessageDefault msg = new MessageDefault();
				msg.setCategoriaErro("4");
				msg.setCodigoErro(erro.getCodigoErro());
				msg.setInformacoesAdicionais(erro.getMensagemErro());
				msg.setOrigemErro(erro.getCampoIPS());
				msg.setSeveridade(1);
				msg.setSistema(msgRetorno.getOrigemRetorno());
				listaMessages.add(msg);
			}
			throw new BusinessException(listaMessages);
		} else if (msgRetorno.getCodigoRetorno().equalsIgnoreCase("X5")) {
			MessageDefault msg = new MessageDefault();
			msg.setCategoriaErro("3");
			msg.setCodigoErro(msgRetorno.getCodigoRetorno());
			if (msgRetorno.getDados() != null) {
				msg.setInformacoesAdicionais(msgRetorno.getDados().getExcecao());
			}
			msg.setOrigemErro(msgRetorno.getMensagemRetorno());
			msg.setSeveridade(1);
			msg.setSistema(msgRetorno.getOrigemRetorno());
			throw new BusinessException(msg);
		}

		// Mensagem negocial retornada pelo SICLI
		if (msgRetorno.getDados().getControleNegocial() != null) {
			MensagemSiemp msg1 = new MensagemSiemp();

			if (msgRetorno.getDados().getControleNegocial().getCodigoRetorno().equals("11")) {
				msg1.setNumeroMensagemErro(
						Integer.parseInt(msgRetorno.getDados().getControleNegocial().getCodigoRetorno()));
				msg1.setDescricaoMensagemErro(msgRetorno.getDados().getControleNegocial().getCodigoRetorno() + " - "
						+ msgRetorno.getDados().getControleNegocial().getOrigemRetorno() + " - "
						+ msgRetorno.getDados().getControleNegocial().getMensagem());
				msg1.setNumeroTipoMensagem(4);
			} else {
				Messages listaMessages = new Messages();
				for (ManutencaoErro erro : msgRetorno.getDados().getManutencaoErroList()) {
					MessageDefault msg = new MessageDefault();
					msg.setCategoriaErro("4");
					msg.setCodigoErro(erro.getCodigoErro());
					msg.setInformacoesAdicionais(erro.getMensagemErro());
					msg.getMensagemNegocial().add(erro.getMensagemErro());
					msg.setOrigemErro(erro.getCampoIPS());
					msg.setSeveridade(2);
					msg.setSistema(msgRetorno.getOrigemRetorno());
					listaMessages.add(msg);
				}
				throw new BusinessException(listaMessages);
			}

			atualizarCliente.setMensagem(msg1);
		}
	}
}
