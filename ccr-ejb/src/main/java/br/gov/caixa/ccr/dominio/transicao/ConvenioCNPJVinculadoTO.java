package br.gov.caixa.ccr.dominio.transicao;
//
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the CCRTB004_CANAL_ATENDIMENTO database table.
 * 
 */
@Entity
@Table(name="CCRTB049_CONVENIO_CNPJ_VNCDO")
@NamedNativeQuery(name = "ConvenioCNPJVinculadoTO.excluiConveniosVinculados", query = "DELETE FROM CCR.CCRTB049_CONVENIO_CNPJ_VNCDO WHERE NU_CONVENIO = :pConvenio", resultClass = ConvenioCNPJVinculadoTO.class)

@NamedNativeQueries({
	
	@NamedNativeQuery(name="ConvenioCNPJVinculadoTO.consultarPorConvenio", 
	query="SELECT * FROM CCR.CCRTB049_CONVENIO_CNPJ_VNCDO WHERE NU_CONVENIO = ? ", resultClass=ConvenioCNPJVinculadoTO.class),
	
	@NamedNativeQuery(name="ConvenioCNPJVinculadoTO.consultarPorCNPJ",
//	query=  " SELECT C.* FROM "+
//			" CCR.CCRTB049_CONVENIO_CNPJ_VNCDO  CV, CCR.CCRTB001_CONVENIO C "+
//			" WHERE CV.NU_CONVENIO = C.NU_CONVENIO "+
//			" AND (CV.NU_CNPJ = ? OR C.NU_CNPJ_CONVENENTE = ?) ", resultClass=ConvenioTO.class),
	
	query=
	" SELECT C.* FROM CCR.CCRTB001_CONVENIO C "+
	" WHERE  C.NU_CNPJ_CONVENENTE = ? "+
	" OR C.NU_CONVENIO IN(SELECT C.NU_CONVENIO "+
	                            " FROM CCR.CCRTB049_CONVENIO_CNPJ_VNCDO  CV, CCR.CCRTB001_CONVENIO C "+ 
	                            " WHERE CV.NU_CONVENIO = C.NU_CONVENIO "+ 
	                            " AND (CV.NU_CNPJ = ? OR C.NU_CNPJ_CONVENENTE = ?)) ", resultClass=ConvenioTO.class),	
	
	//FE14 incluir
	@NamedNativeQuery(name="ConvenioCNPJVinculadoTO.verificaCNPJConvenente", 
	query="SELECT * FROM CCR.CCRTB001_CONVENIO WHERE NU_CNPJ_CONVENENTE = ? ", resultClass=ConvenioTO.class),
	
	//FE14  alterar
	@NamedNativeQuery(name="ConvenioCNPJVinculadoTO.verificaCNPJConvenenteAlteracao", 
	query="SELECT * FROM CCR.CCRTB001_CONVENIO WHERE NU_CNPJ_CONVENENTE = ? AND NU_CONVENIO <> ?", resultClass=ConvenioTO.class),
	
	//FE18 incluir
	@NamedNativeQuery(name="ConvenioCNPJVinculadoTO.verificaCNPJEstaVinculado", 
	query="select * from CCR.CCRTB049_CONVENIO_CNPJ_VNCDO WHERE NU_CNPJ = ?", resultClass=ConvenioCNPJVinculadoTO.class),
	
	//FE18 alterar
	@NamedNativeQuery(name="ConvenioCNPJVinculadoTO.verificaCNPJEstaVinculadoAlteracao", 
	query="select * from CCR.CCRTB049_CONVENIO_CNPJ_VNCDO WHERE NU_CNPJ = ? AND NU_CONVENIO <> ?", resultClass=ConvenioCNPJVinculadoTO.class)
})

public class ConvenioCNPJVinculadoTO implements Serializable {
	private static final long serialVersionUID = 1L;

	//FIXME SKMT corrigir nome da sequence qdo criarem a CCRSQ049_CONVENIO_CNPJ_VNCDO (Corrigido Jungles)
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONV_CNPJ")
    @SequenceGenerator(sequenceName = "CCRSQ049_CONVENIO_CNPJ_VNCDO", allocationSize = 1, name = "SEQ_CONV_CNPJ")
    @Column(name = "NU_SEQUENCIAL_CONVENIO_CNPJ", length=18)
    private Long numSeqConvCNPJ;
	
	@ManyToOne
	@JoinColumn(name="NU_CONVENIO")
	private ConvenioTO nuConvenio;

	@Column(name="NU_CNPJ")
	private String nuCNPJ;

	public Long getNumSeqConvCNPJ() {
		return numSeqConvCNPJ;
	}

	public void setNumSeqConvCNPJ(Long numSeqConvCNPJ) {
		this.numSeqConvCNPJ = numSeqConvCNPJ;
	}

	public ConvenioTO getNuConvenio() {
		return nuConvenio;
	}

	public void setNuConvenio(ConvenioTO nuConvenio) {
		this.nuConvenio = nuConvenio;
	}

	public String getNuCNPJ() {
		return nuCNPJ;
	}

	public void setNuCNPJ(String nuCNPJ) {
		this.nuCNPJ = nuCNPJ;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}