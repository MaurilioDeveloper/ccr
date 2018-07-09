package br.gov.caixa.ccr.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.gov.caixa.arqrefcore.excecao.enumerador.ECategoriaErro;
import br.gov.caixa.arqrefcore.excecao.mensagem.Message;
import br.gov.caixa.arqrefcore.util.Stringer;
import br.gov.caixa.ccr.dominio.barramento.alteracaocliente.MensagemSiemp;

public class MensagemSiempAdapter implements Message {

	private static final long serialVersionUID = -8093400652003989908L;

	private static final String SISTEMA = "SIEMP";
	
	private MensagemSiemp mensagemSiemp;
	 
	private Stringer stringer;

	public MensagemSiempAdapter(MensagemSiemp mensagemSiemp) {
		this.mensagemSiemp = mensagemSiemp;
	}
	
	private boolean isSQLError() {
		return mensagemSiemp.getSqlCode() != null 
				&& !mensagemSiemp.getSqlCode().isEmpty()
				&& !mensagemSiemp.getSqlCode().equals("000")
				&& !mensagemSiemp.getSqlCode().equals("100");
	}
	
	private boolean isCICSError() {
		return mensagemSiemp.getNumeroMensagemCICS() != null 
				&& mensagemSiemp.getNumeroMensagemCICS() != 0;
	}
	
	private boolean isBusinessError() {
		return mensagemSiemp.getNumeroMensagemErro() != null 
				&& mensagemSiemp.getNumeroMensagemErro() != 0;
	}
	
	@Override
	public String getSistema() {
		return SISTEMA;  
	}

	@Override
	public String getOrigemErro() {
		if(mensagemSiemp.getNomePrograma() != null && !mensagemSiemp.getNomePrograma().isEmpty()){
		    return MessageFormat.format("Programa: {0}",mensagemSiemp.getNomePrograma());
		}		
		return "SIEMP";
	}

	@Override
	public String getParagrafoErro() {
		return mensagemSiemp.getNomeProcesso();
	}

	@Override
	public String getCategoriaErro() {
		if (isSQLError()) {
			return ECategoriaErro.ERRO_SQL.getDescricao();
		} else if (isCICSError()) {
			return ECategoriaErro.ERRO_CICS.getDescricao();
		} else if (isBusinessError()) {
			return ECategoriaErro.ERRO_NEGOCIAL.getDescricao();
		} else {
			return ECategoriaErro.ERRO_CICS.getDescricao();
		}
	}

	@Override
	public String getCodigoErro() {
		if(mensagemSiemp.getNumeroMensagemErro()!=null)
			return String.valueOf(mensagemSiemp.getNumeroMensagemErro());
		if(mensagemSiemp.getNumeroMensagemCICS()!=null)
			return String.valueOf(mensagemSiemp.getNumeroMensagemCICS());
		
		return "0";
	}

	@Override
	public List<String> getMensagemNegocial() {
		List<String> ret = new ArrayList<String>();
		if (isBusinessError()) {
			ret.add(mensagemSiemp.getDescricaoMensagemErro());
		}else{
			ret.add(mensagemSiemp.getDescricaoMensagemCICS());
		}
		return ret;
	}

	@Override
	public List<String> getMensagemErro() {
		List<String> ret = new ArrayList<String>();
		if (!isBusinessError()) {
			if (mensagemSiemp.getDescricaoMensagemErro() != null && !mensagemSiemp.getDescricaoMensagemErro().isEmpty()) {
				ret.add(mensagemSiemp.getDescricaoMensagemErro());
			}
			if (mensagemSiemp.getDescricaoMensagemCICS() != null && !mensagemSiemp.getDescricaoMensagemCICS().isEmpty()) {
				ret.add(mensagemSiemp.getDescricaoMensagemCICS());
			}
		}else{
			ret.add(mensagemSiemp.getDescricaoMensagemErro());
		}
		return ret;
	}

	@Override
	public int getSeveridade() {
		//Se o COBOL nao retornar mensagem retorna -1 como severidade padrao
		return mensagemSiemp.getNumeroTipoMensagem() != null ? mensagemSiemp.getNumeroTipoMensagem() : -1;
	}

	@Override
	public String getInformacoesAdicionais() {
		stringer = new Stringer();
		stringer.with("");
		if (mensagemSiemp.getDescricaoDaAcao() != null && !mensagemSiemp.getDescricaoDaAcao().isEmpty()) {
			stringer.append(" DETALHES: " +mensagemSiemp.getDescricaoDaAcao());
		}
		if (mensagemSiemp.getNomeTabela() != null && !mensagemSiemp.getNomeTabela().isEmpty()) {
			stringer.append("\n TABELA: " +mensagemSiemp.getNomeTabela());
		}
		if (mensagemSiemp.getSqlCode() != null && !mensagemSiemp.getSqlCode().isEmpty()) {
			stringer.append("\n SQLCODE: " +mensagemSiemp.getSqlCode());
		}
		if (mensagemSiemp.getDescricaoMensagemCICS() != null && !mensagemSiemp.getDescricaoMensagemCICS().isEmpty()  && !mensagemSiemp.getDescricaoMensagemCICS().trim().isEmpty()) {
			stringer.append("\n Mensagem CICS: " +mensagemSiemp.getDescricaoMensagemCICS());
		}
			stringer.append("\n DATA/HORA: " + new Date().toString());
		return stringer.get();
	}

	@Override
	public String getSeveridadeDescricao() {
		// TODO Auto-generated method stub
		return null;
	}
}
