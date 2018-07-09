package br.gov.caixa.ccr.negocio;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefservices.dominio.sicli.CamposRetornados;
import br.gov.caixa.ccr.dominio.CamposRetornadosCCR;
import br.gov.caixa.ccr.dominio.CamposRetornadosImpressao;
import br.gov.caixa.ccr.dominio.Documento;
import br.gov.caixa.ccr.dominio.DocumentoAutoComplete;
import br.gov.caixa.ccr.dominio.Impressao;
import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.dominio.transicao.AuditoriaTO;
import br.gov.caixa.ccr.dominio.transicao.CampoTO;
import br.gov.caixa.ccr.dominio.transicao.CampoUsadoTO;
import br.gov.caixa.ccr.dominio.transicao.ContratoTO;
import br.gov.caixa.ccr.dominio.transicao.DocumentoTO;
import br.gov.caixa.ccr.dominio.transicao.TipoDocumentoTO;
import br.gov.caixa.ccr.enums.EnumCampoAuditoria;
import br.gov.caixa.ccr.enums.EnumSituacaoDocumento;
import br.gov.caixa.ccr.enums.EnumTipoAuditoria;
import br.gov.caixa.ccr.enums.EnumTipoDocumento;
import br.gov.caixa.ccr.enums.EnumTransacaoAuditada;
import br.gov.caixa.ccr.template.TemplatePopulator;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DocumentoBean implements IDocumentoBean {
	
	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private TemplatePopulator templatePop;
	
	@Inject
	private IDocumentoValidacao iDocumentoValidacao;
	
	@Inject
	private IContratoBean contratoService;
	
	private static final String CARACTER_ESPECIAL_CAMPO_DINAMICO = "##";
	 
	@Override
	public List<Documento> listaDocumento(Documento documento) throws Exception {
		
		List<Documento> docLst = new ArrayList<>();
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<DocumentoTO> criteria = qb.createQuery(DocumentoTO.class);
		Root<DocumentoTO> documentoFiltro = criteria.from(DocumentoTO.class);
		Predicate p = qb.conjunction();
		criteria.orderBy(qb.asc(documentoFiltro.get("dtInicioVigencia")));
		if(documento.getNomeModeloDocumento()!= null && documento.getNomeModeloDocumento() != ""){
			p.getExpressions().add(qb.equal(documentoFiltro.get("noDocumento"), documento.getNomeModeloDocumento()));
			
		}
		
		if(documento.getTipoDocumento() != null && documento.getTipoDocumento() != ""){
			TipoDocumentoTO tipoDocTo = new TipoDocumentoTO();
			tipoDocTo.setIdTipoDocumento(Long.valueOf(documento.getTipoDocumento()));
			p.getExpressions().add(qb.equal(documentoFiltro.get("tipoDocumento"), tipoDocTo));
			
		}
		
		if(documento.getInicioVigencia()!= null && documento.getInicioVigencia() != ""){
			Timestamp timestampIni = null; 
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = sdf.parse(documento.getInicioVigencia());
			timestampIni = new Timestamp(date.getTime()); 
			
			if(timestampIni!=null){
				p.getExpressions().add(qb.greaterThanOrEqualTo(documentoFiltro.<Date>get("dtInicioVigencia"), timestampIni));
			}	
			
		}
		
		criteria.where(p);
		List<DocumentoTO> listaRetorno = em.createQuery(criteria).getResultList();
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatTimeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		for (DocumentoTO documentoTO : listaRetorno) {
			Date now = new Date();
			Long idDoc = documentoTO.getIdDocumento();
			String inicioVigencia = format.format(documentoTO.getDtInicioVigencia());  
			
			String fimVigencia = ""; 
			if(documentoTO.getDtFimVigencia()!=null && !documentoTO.getDtFimVigencia().equals("")){		
				fimVigencia = format.format(documentoTO.getDtFimVigencia());
			}	
			
			String tipoDocumento = documentoTO.getTipoDocumento().getNoTipoDocumento();
			Long nrTipoDocumento = documentoTO.getTipoDocumento().getIdTipoDocumento();
			String nomeModeloDocumento = documentoTO.getNoDocumento(); 
			String nomeUsuario = documentoTO.getCoUsuario(); 
			String data = formatTimeStamp.format(documentoTO.getDtInclusao()); 
			Date dtInclusao = documentoTO.getDtInclusao();
			Date dtFimVigencia = documentoTO.getDtFimVigencia();
			Date dtInicioVigencia = documentoTO.getDtInicioVigencia();
			String icModeloTestemunha  = "";
			if(documentoTO.getIcModeloTestemunha() != null){
				icModeloTestemunha = documentoTO.getIcModeloTestemunha().equals(EnumSituacaoDocumento.ATIVO.getValor())  ? "Sim" : ""; 
			}

			StringBuilder templateHtml = new StringBuilder();
			templateHtml.append(documentoTO.getDeModelo());
			String camposDinamicos = "";
			boolean updatable = documentoTO.getDtInicioVigencia().after(now) && documentoTO.getIcStatus().equals(EnumSituacaoDocumento.ATIVO.getValor());
			boolean exclusao =	documentoTO.getDtInicioVigencia().after(now) && documentoTO.getIcStatus().equals(EnumSituacaoDocumento.ATIVO.getValor());	
			Documento doc = new Documento(idDoc, inicioVigencia, fimVigencia, tipoDocumento, nomeModeloDocumento,
					nomeUsuario, data, templateHtml, camposDinamicos, updatable, exclusao, nrTipoDocumento, 
					dtInicioVigencia,dtFimVigencia, dtInclusao, icModeloTestemunha);
			docLst.add(doc);
		}
		
		Collections.sort (docLst, ordenarDescrescente());
		
  
		return docLst;
	}
	
	private Comparator<Documento> ordenarDescrescente() {
		return new Comparator<Documento>() {
				@Override
				public int compare(Documento o1, Documento o2) {
					final Documento p1 = (Documento) o1;
	            	final Documento p2 = (Documento) o2;
	                return p1.getDtInicioVigencia().after(p2.getDtInicioVigencia()) ? -1 : (p1.getDtInicioVigencia().before(p2.getDtInicioVigencia()) ? +1 : 0);
	            
				}
	        };
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Retorno salvar(Documento documento, String usuario) throws Exception {
		Retorno retorno = this.iDocumentoValidacao.validar(documento, usuario);
		
		if(documento.getId() == null){
			 retorno = this.validaNovoDocumento(documento, retorno);
		}
	
		
		if(retorno != null){
			return retorno;
		}
			
		DocumentoTO documentoTO = this.convertDocumentoViewToDocumentoTO(documento);
		documentoTO.setCoUsuario(usuario);
		this.montaObjCampoUsados(documento.getTemplateHtml(), documentoTO);
		em.persist(documentoTO);
		em.flush();
		
		if(retorno == null){
			retorno = new Retorno(0L, "", Retorno.SUCESSO, "MA002");
		}

		return retorno;
	}
	
	private  DocumentoTO convertDocumentoViewToDocumentoTO(Documento documentoView) throws IllegalArgumentException, IllegalAccessException, ParseException{
		DocumentoTO documentoTO = new DocumentoTO();
		if(documentoView.getId() != null){
			documentoTO = em.find(DocumentoTO.class, documentoView.getId());
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dtInicioVigencia = formatter.parse(documentoView.getInicioVigencia());
	    documentoTO.setIdDocumento(documentoView.getId());
		documentoTO.setDtInclusao(new Date());
		documentoTO.setDtInicioVigencia(dtInicioVigencia);
		documentoTO.setDtAtualizacao(dtInicioVigencia);
		documentoTO.setIcStatus(EnumSituacaoDocumento.ATIVO.getValor());
		documentoTO.setTipoDocumento(new TipoDocumentoTO(Long.parseLong(documentoView.getTipoDocumento())));
		documentoTO.setNoDocumento(documentoView.getNomeModeloDocumento());
		documentoTO.setDeModelo(String.valueOf(documentoView.getTemplateHtml()));
		documentoTO.setIcModeloTestemunha(documentoView.getIcModeloTestemunha());
		
		return documentoTO;
	}
	
	private Retorno validaNovoDocumento(Documento doc, Retorno retorno) throws BusinessException{
		try{
			if(this.buscaUltimoModeloDocumentoTipoVirgente(Long.valueOf(doc.getTipoDocumento()),doc.getInicioVigencia()) ){
				TypedQuery<DocumentoTO> query = null;
				query = em.createNamedQuery("Documento.consultarDocumento", DocumentoTO.class);
				query.setParameter("tipoDocumento", Long.parseLong(doc.getTipoDocumento()));
				
				DocumentoTO documentoTO  =query.getSingleResult();
				atualizaModeloDocumentoAnterior(documentoTO, doc.getInicioVigencia());
			}else{
				retorno = new Retorno(-1L, "", Retorno.ERRO_NEGOCIAL, "MA005");
			}
		
		}catch (NoResultException e) {
			/**Caso ocorra NoResultException, continuar o fluxo de incluir*/
			return retorno;
		}
		
		return retorno;
	}
	
	private Boolean buscaUltimoModeloDocumentoTipoVirgente(Long nrtipoDocumento, String dtInicioVigencia){
		TypedQuery<DocumentoTO> query = null;
		query = em.createNamedQuery("Documento.consultarQntDocumentoVigente", DocumentoTO.class);
		query.setParameter("nrTipoDocumento", nrtipoDocumento);
		query.setParameter("dtInicioVirgencia", dtInicioVigencia);	
		List<DocumentoTO> docList =query.getResultList();
		
		return docList.size() == 0;
	}
	
	private void atualizaModeloDocumentoAnterior(DocumentoTO documentoTO, String iniVigencia) throws BusinessException{
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dtFimVigencia = formatter.parse(iniVigencia);
		
			Calendar c = Calendar.getInstance();
			c.setTime(dtFimVigencia);
			c.add(Calendar.DATE, -1);
			
			documentoTO.setDtFimVigencia(c.getTime());
			documentoTO.setDtAtualizacao(new Date());
			
			em.persist(documentoTO);
			em.flush();
		
		} catch (ParseException e) {
			throw new BusinessException();
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Retorno excluir(Documento documento, String usuario) throws Exception {	
		
		Query queryCampUsa = em.createNamedQuery("DocumentoTO.excluirDocumentoCamposUsados", DocumentoTO.class);
		queryCampUsa.setParameter("pDocumento", documento.getId());
		queryCampUsa.executeUpdate();
		
		Query queryDoc = em.createNamedQuery("DocumentoTO.excluirDocumento", DocumentoTO.class);
		queryDoc.setParameter("pDocumento", documento.getId());
		queryDoc.executeUpdate();
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dtFimVigencia = formatter.parse(documento.getInicioVigencia());
	
		Calendar c = Calendar.getInstance();
		c.setTime(dtFimVigencia);
		c.add(Calendar.DATE, -1);
		
		
		Query queryDocUpdate = em.createNamedQuery("Documento.atualizaUltimoVigente", DocumentoTO.class);
		queryDocUpdate.setParameter("nrTipoDocumento", documento.getNrTipoDocumento());
		queryDocUpdate.setParameter("dtFimVigencia", c.getTime() );
		queryDocUpdate.executeUpdate();
		
		return new Retorno(0L, "", Retorno.SUCESSO, "MA002");
	}
	
	private List<String> buscacamposUsadosTemplate(StringBuilder htmlTemplate){
		String[] linhas = htmlTemplate.toString().split(CARACTER_ESPECIAL_CAMPO_DINAMICO);
		List<String> camposDinamicosTemplate = new ArrayList<String>();
		int contador = 0;
		
		for(String item : linhas){
			if(contador % 2 !=0){
				camposDinamicosTemplate.add(item);
			}
			contador ++;
		}
		return camposDinamicosTemplate;
	}
		
	private List<CampoTO> getAllCamposDinamicos(){
		TypedQuery<CampoTO> query = null;
		query = em.createNamedQuery("CampoDinamico.consultarAll", CampoTO.class);
		List<CampoTO> campos  =query.getResultList();
		
		return campos;
	}
	
	private void montaObjCampoUsados(StringBuilder htmlTemplate, DocumentoTO docTo) {
		if(docTo.getIdDocumento() != null){
			this.excluiCamposUsado(docTo);
		}
		
		List<CampoTO>  camposDinamicosBase = this.getAllCamposDinamicos();
		List<String> camposDinamicosTemplate = this.buscacamposUsadosTemplate(htmlTemplate);		 
		
		for(String cdt : camposDinamicosTemplate){
			for(CampoTO cdb : camposDinamicosBase){
				if(cdb.getNoCampo().equals(cdt)){
					CampoUsadoTO item = new CampoUsadoTO();
					item.setCampo(cdb);
					docTo.addCampoUsado(item);
				}
			}
		}
	}
	
	private void excluiCamposUsado(DocumentoTO documentoTo){
		TypedQuery<CampoUsadoTO> query = null;
		query = em.createNamedQuery("CampoUsado.excluirPorIdDocumento", CampoUsadoTO.class);
		query.setParameter("idDocumento", documentoTo.getIdDocumento());
		query.executeUpdate();
	}
		
	
	@Override
	public String imprimeDocumento(Long idDocumento, Long idContrato, CamposRetornados sicli) throws Exception {
		DocumentoTO doc = em.find(DocumentoTO.class, idDocumento);
		
		ContratoTO contrato = em.find(ContratoTO.class, idContrato);
		
		List<Object> objectList = new ArrayList<Object>(); 
		objectList.add(contrato);
		if (sicli != null) {
			objectList.add(sicli);
		}
		String arquivo = templatePop.gerarArquivoFromDocumento(doc, objectList);
		return arquivo;
	}
	
	private List<CampoUsadoTO> buscaCamposUsadosTOImpressaoTeste(List<String> camposUsados){
		List<CampoUsadoTO> retorno = new ArrayList<CampoUsadoTO>();
		TypedQuery<CampoTO> query = null;
		query = em.createNamedQuery("CampoDinamico.consultar", CampoTO.class);
		query.setParameter("campos", camposUsados);
		
		List<CampoTO> campos  =query.getResultList();
		
		for (CampoTO campoTO : campos) {
			CampoUsadoTO campoUsado = new CampoUsadoTO();
			campoUsado.setCampo(campoTO);	
			retorno.add(campoUsado);
		}
		return retorno;
	}

	@Override
	public List<DocumentoAutoComplete> consultarAutocomplete(String desc) throws Exception {
		TypedQuery<DocumentoTO> query = null;
		query = em.createNamedQuery("DocumentoTO.listarDocumentosNomeModelAutocomplete", DocumentoTO.class);
		query.setParameter("nomeDoc",desc+"%");
		
		List<DocumentoTO> listaRetorno = query.getResultList();
		List<DocumentoAutoComplete> docLst = new ArrayList<>();
		
		for (DocumentoTO documentoTO : listaRetorno) {
			DocumentoAutoComplete doc = new DocumentoAutoComplete();
			doc.setNomeModeloDocumento(documentoTO.getNoDocumento());
			docLst.add(doc);
		}

	return docLst;
	}

	@Override
	public String imprimeDocumento(Documento documento, CamposRetornados sicli)
			throws Exception {
		
		List<String> camposUsados = this.buscacamposUsadosTemplate(documento.getTemplateHtml());
				
		DocumentoTO doc = new DocumentoTO();
		doc.setNoDocumento("visualizaTeste");
		doc.setDeModelo(documento.getTemplateHtml().toString());
		
		if(!camposUsados.isEmpty()){
			List<CampoUsadoTO> camposUsadosTOImpressaoTeste = this.buscaCamposUsadosTOImpressaoTeste(camposUsados);
			doc.setCampoUsados(camposUsadosTOImpressaoTeste);
		}
		
		ContratoTO contrato = em.find(ContratoTO.class, documento.getNrContrato());
		
		List<Object> objectList = new ArrayList<Object>(); 
		objectList.add(contrato);
		if (sicli != null) {
			CamposRetornadosImpressao sicliImpressao = new CamposRetornadosImpressao(sicli);
			objectList.add(sicliImpressao);
		}

		return  templatePop.gerarArquivoFromDocumento(doc, objectList);
	}
	
	@Override
	public List<String> imprimeDocumentoContrato(Impressao impressao)
			throws Exception {
		
		ContratoTO contratoTO = contratoService.consultarContratoPorNr(Long.valueOf(impressao.getNumContrato()));
		List<DocumentoTO> docList  = new ArrayList<DocumentoTO>();
		AuditoriaTO auditoriaTO = new AuditoriaTO();
		
		if(contratoTO.getIcSeguroPrestamista().equals("S")){
			docList = this.buscaDocumento(EnumTipoDocumento.TIPO_CONTRATO_CCR, EnumTipoDocumento.TIPO_PRESTAMISTA_CCR);
		}else{
			docList = this.buscaDocumento(EnumTipoDocumento.TIPO_CONTRATO_CCR);
		}
		
	
		CamposRetornadosCCR sicli = impressao.getSicli();
		impressao.setSicli(null);
		
		List<Object> objectList = new ArrayList<Object>(); 
		
		objectList.add(contratoTO);
		
		impressao.setDataImpressao(new Date());
		objectList.add(impressao);
		
		if (sicli != null) {
			CamposRetornadosImpressao sicliImpressao = new CamposRetornadosImpressao(sicli);
			objectList.add(sicliImpressao);
		}
		
		List<String> templates = new ArrayList<String>();
		for(DocumentoTO doc : docList){	
			templates.add(templatePop.gerarArquivoFromDocumento(doc, objectList));
		}
		
		auditoriaTO.setDhTransacaoAuditada(new Date());
		auditoriaTO.setCoUsuarioAuditado(contratoTO.getCoUsuario());
		auditoriaTO.setNuTransacaoAuditada(EnumTransacaoAuditada.CONTRATACAO.getCodigo());
		auditoriaTO.setIcTipoAcao(EnumTipoAuditoria.INCLUSAO.getDescricao());
		auditoriaTO.setNoCampoAuditado(EnumCampoAuditoria.ICONTRATO.getDescricao());
		// FIXME (Maurilio) Corrigir após arrumar a base (valor atual).
		auditoriaTO.setDeValorAtual(contratoTO.getNuContrato().toString());
		// FIXME (Maurilio) Ao ser criada a tabela de contrato popular a mesma.
		
		em.persist(auditoriaTO);
		em.flush();
		
		
		return  templates;
	}
	
	@Override
	public String buscarTipoDocumentoImpressaoCCR()
			throws Exception {
		
		List<DocumentoTO> docList  = new ArrayList<DocumentoTO>();
		docList = this.buscaDocumento(EnumTipoDocumento.TIPO_CONTRATO_CCR);

		for(DocumentoTO doc : docList){	
			return doc.getIcModeloTestemunha();
		}
		
		return  null;
	}
	
	
	private List<DocumentoTO> buscaDocumento(EnumTipoDocumento... tipoDocumento){
		List<Long> nrTipos = new ArrayList<Long>();
		for(EnumTipoDocumento tipo : tipoDocumento){
			nrTipos.add(tipo.getCodigo());
		}
		
		TypedQuery<DocumentoTO> query = null;
		query = em.createNamedQuery("Documento.consultarDocumentoPrint", DocumentoTO.class);
		query.setParameter("nrTipoDocumento", nrTipos);
		
		List<DocumentoTO> documentoTOList  = query.getResultList();
		return documentoTOList;
	}
	
	@Override
	public List<Documento> listaDocumentoRetorno(Documento documento) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatTimeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		List<Documento> retorno = new ArrayList<Documento>();
		TypedQuery<DocumentoTO> query = null;
		query = em.createNamedQuery("Documento.consultarDocumentoRetorno", DocumentoTO.class);
		query.setParameter("tipoDocumento", documento.getTipoDocumento());
		
		List<DocumentoTO> campos  = query.getResultList();
		
		for (DocumentoTO documentoTO : campos) {
			Date now = new Date();
			Long idDoc = documentoTO.getIdDocumento();
			String inicioVigencia = format.format(documentoTO.getDtInicioVigencia());  
			
			String fimVigencia = ""; 
			if(documentoTO.getDtFimVigencia()!=null && !documentoTO.getDtFimVigencia().equals("")){		
				fimVigencia = format.format(documentoTO.getDtFimVigencia());
			}	
			
			String tipoDocumento = documentoTO.getTipoDocumento().getNoTipoDocumento();
			Long nrTipoDocumento = documentoTO.getTipoDocumento().getIdTipoDocumento();
			String nomeModeloDocumento = documentoTO.getNoDocumento(); 
			String nomeUsuario = documentoTO.getCoUsuario(); 
			String data = formatTimeStamp.format(documentoTO.getDtInclusao()); 
			StringBuilder templateHtml = new StringBuilder();
			templateHtml.append(documentoTO.getDeModelo());
			Date dtInicioVigencia = documentoTO.getDtInclusao();
			Date dtInclusao = documentoTO.getDtInclusao();
			Date dtFimVigencia = documentoTO.getDtFimVigencia();
			String camposDinamicos = "";
			
			String icModeloTestemunha  = "";
			if(documentoTO.getIcModeloTestemunha() != null){
				icModeloTestemunha = documentoTO.getIcModeloTestemunha().equals(EnumSituacaoDocumento.ATIVO.getValor())  ? "Sim" : ""; 
			}

			
			boolean updatable = documentoTO.getDtInicioVigencia().after(now) && documentoTO.getIcStatus().equals(EnumSituacaoDocumento.ATIVO.getValor());
			boolean exclusao =	documentoTO.getDtInicioVigencia().after(now) && documentoTO.getIcStatus().equals(EnumSituacaoDocumento.ATIVO.getValor());	
			Documento doc = new Documento(idDoc, inicioVigencia, fimVigencia, tipoDocumento, nomeModeloDocumento,
					nomeUsuario, data, templateHtml, camposDinamicos, updatable, exclusao, nrTipoDocumento, 
					dtInicioVigencia,dtFimVigencia, dtInclusao, icModeloTestemunha);
			retorno.add(doc);
		}
	  
		return retorno;

	}	
}
