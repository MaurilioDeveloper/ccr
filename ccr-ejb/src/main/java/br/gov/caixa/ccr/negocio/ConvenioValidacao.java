package br.gov.caixa.ccr.negocio;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;

import br.gov.caixa.arqrefservices.dominio.sicli.CamposRetornados;
import br.gov.caixa.ccr.dominio.Convenio;
import br.gov.caixa.ccr.dominio.ConvenioCNPJVinculado;
import br.gov.caixa.ccr.dominio.ConvenioSR;
import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.dominio.transicao.ConvenioCNPJVinculadoTO;
import br.gov.caixa.ccr.dominio.transicao.ConvenioTO;
import br.gov.caixa.ccr.dominio.transicao.UnidadeTO;
import br.gov.caixa.ccr.dominio.transicao.VinculoUnidadeTO;
import br.gov.caixa.ccr.util.ValoresUtil;


@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConvenioValidacao implements IConvenioValidacao {
 
	@PersistenceContext
	private EntityManager em;
	
    @Context
	private SecurityContext securityContext;
	  
	@EJB
	private IClienteBean clienteBean;
	
	
	private boolean validaCnpjSicli(Long cnpj, String userLog) throws Exception {
		String cnpjStr = StringUtils.leftPad(""+cnpj, 14, '0');
		CamposRetornados campos = clienteBean.consultar(cnpjStr, userLog);
			
		if (campos == null || campos.getTipoPessoa() == null) {
			//throw new BusinessException("MA0068");
			return false;
		}
		
		return true;
	}
	
	
	@Override
	public Retorno validar(Convenio convenio, String usuario, boolean isUserInRole) throws Exception {	
		Retorno retorno = null;
		
		/** FE11. Convenente não encontrada SICLI - MA0068
		 	o sistema verifica que a empresa não existe no SICLI,
		 */
		//try {
			/*if(!validaCnpjSicli(convenio.getCnpjConvenente(), usuario)){
				retorno = new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0068");
				return retorno;
			}*/
			
			//FE17. Convente não pode ser um CNPJ Vinculado.
			if(validaCNPJEstaVinculado(convenio.getCnpjConvenente(), convenio.getId())){
				return new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0099");
			}
			
			//FE14. CNPJ não pode ser Convenente
//			Retorno validaVinculadoRetorno = validaCNPJConvenente(convenio.getCnpjConvenente());
//			if( validaVinculadoRetorno != null){
//				return validaVinculadoRetorno;
//			}
			
			
		//} catch (BusinessException e) {
			//throw new BusinessException(retorno.getIdMsg());
			//retorno = new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0068");
			//return retorno;
		//}
		
		/** FE2. CNPJ inválido -  MA0024
		 */
		if(!ValoresUtil.isNumeroValido(convenio.getConvenente().getCnpj())){
			retorno = new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0024");
			return retorno;
		}
		
		/** FE2. validar se vinculados  estao no SICLI SICLI - 
	 	o sistema verifica que a empresa não existe no SICLI,
		 */
		//try {
			
			//ConvenioCNPJVinculado -- CONVERTER/GRAVAR em TO
			for (ConvenioCNPJVinculado convenioCNPJVinculado : convenio.getConvenioCNPJVinculado()) {
				
				/*if(!validaCnpjSicli(Long.valueOf(convenioCNPJVinculado.getNuCNPJ()), usuario)){
					retorno = new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0068");
					return retorno;
				}*/
				
				//FE18. CNPJ Vinculado já vinculado a outro convênio
				if(validaCNPJEstaVinculado(Long.valueOf(convenioCNPJVinculado.getNuCNPJ()), convenio.getId())){
					return new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0100");
				}
				
				//FE14. CNPJ Vinculado não pode ser Convenente
				Retorno validaVinculadoRetorno = validaCNPJConvenente(Long.valueOf(convenioCNPJVinculado.getNuCNPJ()), convenio.getId());
				if( validaVinculadoRetorno != null){
					return validaVinculadoRetorno;
				}
				
			}
			
			
		//} catch (BusinessException e) {
			//throw new BusinessException(retorno.getIdMsg());
			//retorno = new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0068");
			//return retorno;
		//}
		

		/** FE3. Prazo de Emissão Inválido - MA0073 
		 *  o sistema verifica que o prazo de emissão informado não possui o valor entre 7 a 55 dias
		 */
		
		if(convenio.getPrzEmissaoExtrato()< 7 || convenio.getPrzEmissaoExtrato() > 55){
			retorno = new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0073");
			return retorno;
		}
		
		
		/** FE4. Dia de Crédito Salário Inválido - MA0062
		 	o sistema verifica que o dia de crédito salário informado não está no intervalo de 01 a 28
		*/ 
		if(convenio.getDiaCreditoSal() < 1 || convenio.getDiaCreditoSal() > 28){
			retorno = new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0062");
			return retorno;
		}
		
		
		/** FE5. Prazo Máximo Inválido - MA0012
			o sistema verifica que o prazo máximo informado é maior que 120 meses
		*/
		
		/*if(convenio.getCanais().size() > 0){
			for (ConvenioCanal convenioCanal : convenio.getCanais()) {
				if((convenioCanal != null && convenioCanal.getPrzMaximoContratacao() != null  &&  convenioCanal.getPrzMaximoRenovacao() > 120)){
					retorno = new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0012");
					return retorno;
				}else if((convenioCanal != null && convenioCanal.getPrzMaximoRenovacao() != null  && convenioCanal.getPrzMaximoRenovacao().intValue() > 120)){
					retorno = new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0012");
					return retorno;
				}
			}
			
		}*/
		
		/** FE6. SR já Informado - MA0074	
		 	sistema verifica que o indicador da Abrangência a SR já foi informada,
		 **/
		if((convenio.getIndAbrangencia() != null && convenio.getIndAbrangencia() == 0) ||
		   (convenio.getIndAbrangencia() != null && convenio.getIndAbrangencia() == 3 && convenio.getAbrangenciaSR().size() < 1)){
			retorno = new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0074");
			return retorno;
		}
					
		//FE7. SR Inválido - MA0059
		/**
		 	o sistema verifica que a SR informado é inválida
		 */
		//1--->>> convenio.getNumSprnaResponsavel()
		retorno = validaSR(convenio.getNumSprnaResponsavel());
		if(retorno !=null){
			return retorno;
		}
		
		//FE8. Agência Inválida - MA0060
		/**
		 	o sistema verifica que a agência informada é inválida, 
		 */
		/*@ TODO AVALIAR*/
		retorno = validaAG(convenio.getNumPvResponsavel());
		if(retorno!=null){
			return retorno;
		}
		
		/******************************************************************************/
		/**
		  o sistema verifica que a agência informada não pertence a SR
		*/
		retorno = validaAG(convenio.getNumPvCobranca());
		if(retorno!=null){
			return retorno;
		}
		
		
		//FE9. Agência não é do SR - MA0061 --  siico
		retorno = validaUnidadeVinculada(convenio.getNumSprnaResponsavel(), convenio.getNumPvResponsavel());
		if(retorno!=null){
			return retorno;
		}
		
		retorno = validaUnidadeVinculada(convenio.getNumSprnaCobranca(), convenio.getNumPvCobranca());
		if(retorno!=null){
			return retorno;
		}
		
		/** FE10. Situação Ativa para Perfil Agência na Inclusão - MA0075
		Perfil diferente de Gestor(GESTOR);
		Campo “Situação” do convênio diferente de "Aguardando aprovação".
		 */
		if(convenio.getSituacao().getDescricao().equals("1 - ATIVO") && !isUserInRole){
			retorno = new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0075");
			return retorno;
		}
		
		/** FE13. Órgão não informado - MA0070
		 	Campo “Grupo Averbação” é igual a 0003 – MPOG;
			Campo “Código do Órgão” não foi preenchido.
		 */
		if(convenio.getGrupoAverbacao().getId() == 3 && convenio.getCodigoOrgao()== null){
			retorno = new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0070");
			return retorno;
		}
		

		/** FE15. Nenhum canal cadastrado com situação ativo - MA0072
		 	Campo “Situação” é igual a ativo;
			Não foi cadastrado nenhum Canal.
		*/
		if(convenio.getSituacao().getDescricao().equals("1 - ATIVO") && convenio.getCanais().size() < 1){
			retorno = new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0072");
			return retorno;
		}
		
		//2--->>> convenio.getNumSprnaCobranca()
		retorno = validaSR(convenio.getNumSprnaCobranca());
		if(retorno !=null){
			return retorno;
		}
		
		//3--->>> convenio.getAbrangenciaSR()
		if(convenio.getIndAbrangencia() == 3){
			if(convenio.getAbrangenciaSR().size() > 0){
				
				for (ConvenioSR convenioSR : convenio.getAbrangenciaSR()) {
					retorno = validaSR(Long.valueOf(convenioSR.getUnidade()));
					if(retorno!=null){
						return retorno;
					}
				}
				
			}
		}

		return retorno;
	}
	
	
	public Retorno validaSR(Long valor){
		
		Retorno retorno = null;
		TypedQuery<UnidadeTO> queryUnidadeTO = null;
		queryUnidadeTO = em.createNamedQuery("UnidadeTO.verificaSRPorNuUnidade", UnidadeTO.class);
		queryUnidadeTO.setParameter(1, valor);
		List<UnidadeTO> listConvenioTO = (List<UnidadeTO> ) queryUnidadeTO.getResultList();
		if(listConvenioTO.size() < 1){
			return new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0059");
		}
		return retorno;
		
	}
	
	
	public Retorno validaAG(Long valor){
		
		Retorno retorno = null;
		TypedQuery<UnidadeTO> queryUnidadeTO = null;
		queryUnidadeTO = em.createNamedQuery("UnidadeTO.verificaAgPorNuUnidade", UnidadeTO.class);
		queryUnidadeTO.setParameter(1, valor);
		List<UnidadeTO> listConvenioTO = (List<UnidadeTO> ) queryUnidadeTO.getResultList();
		if(listConvenioTO.size() < 1){
			return new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0060");
		}
		return retorno;
		
	}
	
	public Retorno validaUnidadeVinculada(Long sr, Long agencia){
		
		Retorno retorno = null;
		TypedQuery<VinculoUnidadeTO> queryVinculoUnidadeTO = null;
		queryVinculoUnidadeTO = em.createNamedQuery("VinculoUnidadeTO.unidadeViculada", VinculoUnidadeTO.class);
		queryVinculoUnidadeTO.setParameter(1, sr);
		queryVinculoUnidadeTO.setParameter(2, agencia);
		List<VinculoUnidadeTO> listConvenioTO = (List<VinculoUnidadeTO> ) queryVinculoUnidadeTO.getResultList();
		if(listConvenioTO.size() < 1){
			return new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0061");
		}
		return retorno;
		
	}
	/**
	 *	//verifica se CNPJ é ja existe na tabela de CCRTB001_CONVENIO-campo>> NU_CNPJ_CONVENENTE 
	 * @param cnpj
	 * @return
	 */

	public Retorno validaCNPJConvenente(Long cnpj, Long idConvenio){
			Retorno retorno = null;
		
		if(idConvenio != null){
			//alterar
			//inclusao
			TypedQuery<ConvenioTO> queryConvenioTO = null;
			queryConvenioTO = em.createNamedQuery("ConvenioCNPJVinculadoTO.verificaCNPJConvenenteAlteracao", ConvenioTO.class);
			queryConvenioTO.setParameter(1, cnpj);
			queryConvenioTO.setParameter(2, idConvenio);
			
			List<ConvenioTO> listConvenioTO = (List<ConvenioTO> ) queryConvenioTO.getResultList();
			if(listConvenioTO.size() > 0){
				return new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0087");
			}
			
			
		}else{
			//inclusao
			TypedQuery<ConvenioTO> queryConvenioTO = null;
			queryConvenioTO = em.createNamedQuery("ConvenioCNPJVinculadoTO.verificaCNPJConvenente", ConvenioTO.class);
			queryConvenioTO.setParameter(1, cnpj);
			
			List<ConvenioTO> listConvenioTO = (List<ConvenioTO> ) queryConvenioTO.getResultList();
			if(listConvenioTO.size() > 0){
				return new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0087");
			}
		}
	
		
		return retorno;
		
	}
	
	/**
	 * verifica se algum CNPJ vinculado está vinculado na tabela CCRTB049_CONVENIO_CNPJ_VNCLDO- campo>>NU_CNPJ 
	 * @param cnpj
	 * @return
	 */
	public boolean validaCNPJEstaVinculado(Long cnpj, Long idConvenio){
		
		if(idConvenio != null){
			//alterar
			TypedQuery<ConvenioCNPJVinculadoTO> queryConvenioCNPJVinculadoTO = null;
			queryConvenioCNPJVinculadoTO = em.createNamedQuery("ConvenioCNPJVinculadoTO.verificaCNPJEstaVinculadoAlteracao", ConvenioCNPJVinculadoTO.class);
			queryConvenioCNPJVinculadoTO.setParameter(1, cnpj);
			queryConvenioCNPJVinculadoTO.setParameter(2, idConvenio);
			
			List<ConvenioCNPJVinculadoTO> listConvenioCNPJVinculadoTO = (List<ConvenioCNPJVinculadoTO> ) queryConvenioCNPJVinculadoTO.getResultList();
			if(listConvenioCNPJVinculadoTO.size() > 0){
				return true;
			}
		}else{
			//incuir
			TypedQuery<ConvenioCNPJVinculadoTO> queryConvenioCNPJVinculadoTO = null;
			queryConvenioCNPJVinculadoTO = em.createNamedQuery("ConvenioCNPJVinculadoTO.verificaCNPJEstaVinculado", ConvenioCNPJVinculadoTO.class);
			queryConvenioCNPJVinculadoTO.setParameter(1, cnpj);
			
			List<ConvenioCNPJVinculadoTO> listConvenioCNPJVinculadoTO = (List<ConvenioCNPJVinculadoTO> ) queryConvenioCNPJVinculadoTO.getResultList();
			if(listConvenioCNPJVinculadoTO.size() > 0){
				return true;
			}
			
		}
		return false;
		
	}
	
	
	
	
	

}