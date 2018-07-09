package br.gov.caixa.ccr.dominio;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import br.gov.caixa.arqrefservices.dominio.sicli.CamposRetornados;
import br.gov.caixa.arqrefservices.dominio.sicli.EnderecoNacional;
import br.gov.caixa.arqrefservices.dominio.sicli.MeioComunicacao;

public class CamposRetornadosImpressao implements Serializable {

	private static final long serialVersionUID = 1L;
	private DataNascimentoImpressao dataNascimento;
	private List<EnderecoNacionalImpressao> enderecoNacional = new ArrayList<EnderecoNacionalImpressao>();
	private EstadoCivilImpressao estadoCivil;
	private IdentidadeImpressao identidade;
	private List<MeioComunicacaoImpressao> meioComunicacao = new ArrayList<MeioComunicacaoImpressao>();
	private NomeClienteImpressao nomeCliente;
	private ProfissaoSiricImpressao profissaoSiric;
	
	public CamposRetornadosImpressao(CamposRetornados sicli) throws IllegalAccessException, InvocationTargetException {
		if(sicli != null){
			this.dataNascimento = new DataNascimentoImpressao(sicli.getDataNascimento());
			this.identidade = new IdentidadeImpressao(sicli.getIdentidade());
			this.nomeCliente = new NomeClienteImpressao(sicli.getNomeCliente());
			this.estadoCivil = new EstadoCivilImpressao(sicli.getEstadoCivil());
			this.profissaoSiric= new ProfissaoSiricImpressao(sicli.getProfissaoSiric());
			
			for(EnderecoNacional item : sicli.getEnderecoNacional()){
				this.enderecoNacional.add(new EnderecoNacionalImpressao(item));
			}
			
			for(MeioComunicacao item : sicli.getMeioComunicacao()){
				this.meioComunicacao.add(new MeioComunicacaoImpressao(item));
			}
		}
	}
	public DataNascimentoImpressao getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(DataNascimentoImpressao dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public List<EnderecoNacionalImpressao> getEnderecoNacional() {
		return enderecoNacional;
	}
	public void setEnderecoNacional(List<EnderecoNacionalImpressao> enderecoNacional) {
		this.enderecoNacional = enderecoNacional;
	}
	public EstadoCivilImpressao getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(EstadoCivilImpressao estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	public IdentidadeImpressao getIdentidade() {
		return identidade;
	}
	public void setIdentidade(IdentidadeImpressao identidade) {
		this.identidade = identidade;
	}
	public List<MeioComunicacaoImpressao> getMeioComunicacao() {
		return meioComunicacao;
	}
	public void setMeioComunicacao(List<MeioComunicacaoImpressao> meioComunicacao) {
		this.meioComunicacao = meioComunicacao;
	}
	public NomeClienteImpressao getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(NomeClienteImpressao nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public ProfissaoSiricImpressao getProfissaoSiric() {
		return profissaoSiric;
	}
	public void setProfissaoSiric(ProfissaoSiricImpressao profissaoSiric) {
		this.profissaoSiric = profissaoSiric;
	}
	
	
	
}
