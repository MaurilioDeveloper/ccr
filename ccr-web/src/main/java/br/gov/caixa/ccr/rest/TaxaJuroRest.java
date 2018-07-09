package br.gov.caixa.ccr.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.dominio.TaxaJuro;
import br.gov.caixa.ccr.negocio.ITaxaJuroBean;
import br.gov.caixa.ccr.util.RetornoListaVO;

@RequestScoped
@Path("/TaxaJuro")
public class TaxaJuroRest extends AbstractSecurityRest {
	
	@Inject
	private ITaxaJuroBean taxaJuroSevice;
	
	/********************************************************************************
	 * **************************** REST TAXA JUROS *********************************
	 * ******************************************************************************/	
	@SuppressWarnings("unchecked")
	@GET
	//@Path("/lista/{tipoConsulta}/{codigo}/{utilizarEm}/{dataInicial}/{dataFinal}")
	@Path("/lista")
	@Produces({ MediaType.APPLICATION_JSON})
	public Object listarTaxaJuro(
			@QueryParam("tipoConsulta") String tipoConsulta, 
			@QueryParam("codigo") int codigo,
			@QueryParam("utilizarEm")String utilizarEm, 
			@QueryParam("dataInicial")String dataInicial,
			@QueryParam("dataFinal")String dataFinal,
			@QueryParam("convenio")String convenio) {
		
		System.out.println("chegou:"+utilizarEm);
		try {
			Object o = taxaJuroSevice.listar(tipoConsulta, codigo, utilizarEm, dataInicial, dataFinal, convenio);
			if (o instanceof Retorno)
				return o;
			return new RetornoListaVO<TaxaJuro>((List<TaxaJuro>) o);
		} catch (Exception e) {
			return new RetornoListaVO<TaxaJuro>(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	
	@POST
	@Path("/salvar")
	@Consumes({MediaType.APPLICATION_JSON})
	public Retorno salvarTaxaJuro(TaxaJuro taxaJuro, @QueryParam("inicioVigenciaChave") String inicioVigenciaChave) {
		try {
			return taxaJuroSevice.salvar(taxaJuro, inicioVigenciaChave, getDadosUsuarioLogado().getCodigoUsuario());
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	@POST
	@Path("/excluir")
	public Retorno excluirTaxaJuro(TaxaJuro taxaJuro) {
		try {
			return taxaJuroSevice.excluir(taxaJuro);
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
}