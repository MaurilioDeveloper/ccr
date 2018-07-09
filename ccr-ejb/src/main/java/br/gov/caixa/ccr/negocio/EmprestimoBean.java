package br.gov.caixa.ccr.negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.gov.caixa.ccr.dominio.ConsultaPrazo;
import br.gov.caixa.ccr.dominio.Convenio;
import br.gov.caixa.ccr.dominio.ConvenioCanal;
import br.gov.caixa.ccr.dominio.TaxaJuro;
import br.gov.caixa.ccr.util.DataUtil;

public class EmprestimoBean implements IEmprestimoBean {
	
	
	
	@Inject
	private IConvenioBean convenioService;
	
	@Inject
	private ITaxaJuroBean taxaJuroSevice;
	
	@Override
	public ConsultaPrazo consultarPrazos(String idConvenio, Long canal) throws Exception {
		ConsultaPrazo resp = new ConsultaPrazo();
		ConvenioCanal conveniocanal=convenioService.consultarConvenioCanal(Long.valueOf(idConvenio),canal);
		
		if(conveniocanal == null){
			throw new Exception("[REGRA_NEGOCIO]Nenhum convenio relacioando com este canal!");

		}
		Integer minimo=Integer.parseInt(conveniocanal.getPrzMinimoContratacao());
		Integer maximo=conveniocanal.getPrzMaximoContratacao().intValue();
		
		
		String dataVigente= DataUtil.formatar(new Date(), DataUtil.PADRAO_DATA);
		
		Convenio convenio= convenioService.consultar(Long.valueOf(idConvenio), null);
		List<TaxaJuro> listTaxaJuro=null;
		if(convenio != null && convenio.getGrupo()!= null && convenio.getGrupo().getId() !=null){
			listTaxaJuro=taxaJuroSevice.listar(TaxaJuro.GRUPO, convenio.getGrupo().getId().intValue() ,TaxaJuro.CONTRATACAO,dataVigente,dataVigente, idConvenio) ;
		}
		if(listTaxaJuro == null){
			 listTaxaJuro=taxaJuroSevice.listar(TaxaJuro.CONVENIO, 0,TaxaJuro.CONTRATACAO,dataVigente,dataVigente, idConvenio) ;
		}
		setaPrazos(minimo, maximo, listTaxaJuro);
		prazoETaxaJuroPadrao(resp, conveniocanal, listTaxaJuro);
		resp.setListaTaxaJuro(listTaxaJuro);
		resp.setFaixaJuroPadrao(conveniocanal.getIndFaixaJuroContratacao());
		return resp;
	}

	private void prazoETaxaJuroPadrao(ConsultaPrazo resp, ConvenioCanal conveniocanal, List<TaxaJuro> listTaxaJuro) {
		int prazoMax=0;
		double taxaJuroTeste = 0;
		if(listTaxaJuro!= null && !listTaxaJuro.isEmpty()){
			for(TaxaJuro taxa:listTaxaJuro){
				if(taxa.getPrazoMax()> prazoMax){
					prazoMax=taxa.getPrazoMax();
					if("A".equals(conveniocanal.getIndFaixaJuroContratacao())){
						taxaJuroTeste=taxa.getPcMinimo();	
					}else if("B".equals(conveniocanal.getIndFaixaJuroContratacao())){
						taxaJuroTeste=taxa.getPcMinimo();	
					}
					else if("C".equals(conveniocanal.getIndFaixaJuroContratacao())){
						taxaJuroTeste=taxa.getPcMinimo();	
					}
				}
			}
		}
		resp.setPrazo(String.valueOf(prazoMax));
		resp.setTaxaJuro(Double.toString(taxaJuroTeste));
	}

	private void setaPrazos(Integer minimo, Integer maximo, List<TaxaJuro> listTaxaJuro) {
		List<TaxaJuro> listaParaRemover= new ArrayList<TaxaJuro>();
	
		for(TaxaJuro taxaJuro:listTaxaJuro){
			if(minimo>=taxaJuro.getPrazoMin() && minimo<= taxaJuro.getPrazoMax() ){
				taxaJuro.setPrazoMin(minimo);
			}
			if(maximo <=taxaJuro.getPrazoMax() && maximo >= taxaJuro.getPcMinimo()){
				taxaJuro.setPrazoMax(maximo);
			}
			
			if(taxaJuro.getPrazoMin()> maximo || taxaJuro.getPrazoMax() < minimo)      {
				listaParaRemover.add(taxaJuro);
			}
			
		}
		for(int i=0; i< listTaxaJuro.size();i++){
			TaxaJuro taxaJuro= listTaxaJuro.get(i);
			if(listaParaRemover!= null && !listaParaRemover.isEmpty()){
				for(TaxaJuro taxaJuroRemover:listaParaRemover){
					if(taxaJuro.getId()== taxaJuroRemover.getId()){
						listTaxaJuro.remove(i);
					}
				}
			}
		}
	}

	
	
}
