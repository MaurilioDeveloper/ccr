package br.gov.caixa.ccr.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.excecao.SystemException;
import br.gov.caixa.ccr.dominio.AuditoriaRetorno;
import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.negocio.IAuditoriaBean;

@RequestScoped
@Path("/auditoria")
public class AuditoriaRest extends AbstractSecurityRest {
	
	@Inject
	private IAuditoriaBean auditoriaService;
	
	
	@GET
	@Path("/lista")
	@Produces(MediaType.APPLICATION_JSON)
	public AuditoriaRetorno consultaListaAutorizacao(
			@QueryParam("nuContrato") Long nuContrato,
			@QueryParam("cpf") Long cpf, 
			@QueryParam("cnpj") Long cnpj,
			@QueryParam("convenio") Long convenio, 
			@QueryParam("dtInicio") String dtInicio,
			@QueryParam("dtFim") String dtFim, 
			@QueryParam("usuario") String usuario,
			@QueryParam("transacao") Long transacao,
			@QueryParam("sEcho") Integer draw,
			@QueryParam("iDisplayStart") Integer start,
			@QueryParam("iDisplayLength") Integer lenght,
			@QueryParam("qtdTotalRegistros") Long qtdTotalRegistros) 
			throws SystemException, BusinessException {

		AuditoriaRetorno auditoriaRetorno = new AuditoriaRetorno();

		try {
	        Integer pagina = 1;
	        if(lenght < start){
	        	pagina = draw;
	        }
			 
			
			auditoriaRetorno = auditoriaService.consultar(nuContrato, cpf, cnpj, convenio, dtInicio, dtFim, usuario, transacao, lenght, qtdTotalRegistros, start);
			
			auditoriaRetorno.setCodigoRetorno(0L); 
			auditoriaRetorno.setMensagemRetorno("");
			auditoriaRetorno.setTipoRetorno(Retorno.SUCESSO);
			auditoriaRetorno.setIdMsg("");
			auditoriaRetorno.setRegistrosPorPagina(lenght);
			auditoriaRetorno.setPaginaAtual(pagina);
			
			if (auditoriaRetorno.getTotalRegistros() != null){
				auditoriaRetorno.setTotalPaginas((int) (auditoriaRetorno.getTotalRegistros()/ lenght));
				auditoriaRetorno.setTotalRegistros(auditoriaRetorno.getTotalRegistros() );
			} else {
				auditoriaRetorno.setTotalPaginas((int) (qtdTotalRegistros/ lenght));
				auditoriaRetorno.setTotalRegistros(qtdTotalRegistros);
			}
	
			return auditoriaRetorno;
			
		} catch (Exception e) {

			auditoriaRetorno.setCodigoRetorno(1L); 
			auditoriaRetorno.setMensagemRetorno(e.getMessage());
			auditoriaRetorno.setTipoRetorno(Retorno.ERRO_EXCECAO);
			auditoriaRetorno.setIdMsg("");
			
			return auditoriaRetorno;
		}

	}
}
