package br.gov.caixa.ccr.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.excecao.SystemException;
import br.gov.caixa.arqrefcore.excecao.mensagem.MessageDefault;
import br.gov.caixa.arqrefcore.ldap.enumerador.ETipoChaveLDAP;
import br.gov.caixa.ccr.dominio.Empregado;
import br.gov.caixa.ccr.dominio.Usuario;
import br.gov.caixa.ccr.ldap.ConexaoLDAP;
import br.gov.caixa.ccr.util.UsuarioCorrente;

@RequestScoped
public abstract class AbstractSecurityRest {

	@Context
	private SecurityContext securityContext;

	@Inject
	private ConexaoLDAP conexaoLdap;

	private Usuario usuario;
	private Empregado empregado;
	
	protected Logger log = Logger.getLogger(getClass().getName());

	protected Usuario getDadosUsuarioLogado() {
		getDadosUsuarioLDAP().setCodigoUsuario(getSecurityContext().getUserPrincipal().getName().toUpperCase());

		return getDadosUsuarioLDAP();
	}
	
	protected Empregado getEmpregado(String login) throws BusinessException {
		
//		// FIXME - Remover
//		empregado = new Empregado();
//		empregado.setCpf("46930973068");
//		empregado.setNumeroMatricula(891843);
//		empregado.setNumeroDvMatricula(0);
//		empregado.setNomePessoa("TESTE");
//		empregado.setNumeroUnidade(664);
//		empregado.setNumeroNatural(645);
//		empregado.setCodigoCargo("TBN");
//		empregado.setNomeUnidade("Teste");
//		
//		if (1 == 1) {
//			return empregado;
//		}
		
		if (login != null) {
			if (login.contains("@")) {
				HashMap<String, String> mapAtributos = conexaoLdap.getAtributosUsuarioExternoPorLogin(login);
				Empregado empregado = new Empregado();

				if (mapAtributos != null && !mapAtributos.isEmpty()) {
					// Serie de teste para verificar se existe a Chave
					// Se existir seta o valor para o empregao.
					if (mapAtributos.containsKey(ETipoChaveLDAP.NOME_COMPLETO.getValor())) {
						empregado.setNomePessoa(mapAtributos.get(ETipoChaveLDAP.NOME_COMPLETO.getValor()));
					}
					
					if (mapAtributos.containsKey(ETipoChaveLDAP.NIS.getValor())) {
						empregado.setNis(mapAtributos.get(ETipoChaveLDAP.NIS.getValor()));
					}
					
					if (mapAtributos.containsKey(ETipoChaveLDAP.CNPJ.getValor())) {
						empregado.setCnpj(mapAtributos.get(ETipoChaveLDAP.CNPJ.getValor()));
					}
					
					if (mapAtributos.containsKey(ETipoChaveLDAP.DATA_NASCIMENTO.getValor())) {
						empregado.setDataNascimento(mapAtributos.get(ETipoChaveLDAP.DATA_NASCIMENTO.getValor()));
					}
					
					if (mapAtributos.containsKey(ETipoChaveLDAP.CN.getValor())) {
						empregado.setEmail(mapAtributos.get(ETipoChaveLDAP.CN.getValor()));
					}
					
					if (mapAtributos.containsKey(ETipoChaveLDAP.NOME_MAE.getValor())) {
						empregado.setNomeMae(mapAtributos.get(ETipoChaveLDAP.NOME_MAE.getValor()));
					}
					
					if (mapAtributos.containsKey(ETipoChaveLDAP.CPF.getValor())) {
						empregado.setCpf(mapAtributos.get(ETipoChaveLDAP.CPF.getValor()));
					}
					
					if (mapAtributos.containsKey("co-unidade")){
						empregado.setNumeroUnidade(Integer.parseInt(mapAtributos.get("co-unidade")));
					}
					if (mapAtributos.containsKey("no-unidade")){
						empregado.setNomeUnidade(mapAtributos.get("no-unidade"));
					}
					
					empregado.setMatriculaUsuario(login);
					return empregado;
				} else {				
					throw new BusinessException();
				}
			} else {
				if (login.toUpperCase().startsWith("C")) {
					HashMap<String, String> mapAtributos = conexaoLdap.getAtributosUsuarioInternoPorLogin(login);
					Empregado empregado = new Empregado();
					
					if (mapAtributos != null && !mapAtributos.isEmpty()) {
					
						// Serie de teste para verificar se existe a Chave
						// Se existir seta o valor para o empregao.
						if (mapAtributos.containsKey(ETipoChaveLDAP.NOME_COMPLETO.getValor())) {
							empregado.setNomePessoa(mapAtributos.get(ETipoChaveLDAP.NOME_COMPLETO.getValor()));
						}
						if (mapAtributos.containsKey("co-unidade")){
							empregado.setNumeroUnidade(Integer.parseInt(mapAtributos.get("co-unidade")));
						}
						if (mapAtributos.containsKey("no-unidade")){
							empregado.setNomeUnidade(mapAtributos.get("no-unidade"));
						}
					}
					empregado.setMatriculaUsuario(login);
					return empregado;
				} else {
					throw new BusinessException();
				}
			}
		} else {
			// Lanca excecao pela string esta com algum problema
			throw new BusinessException();
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Empregado getEmpregado() {
		return empregado;
	}

	public void setEmpregado(Empregado empregado) {
		this.empregado = empregado;
	}

	public SecurityContext getSecurityContext() {
		return securityContext;
	}

	public Usuario getDadosUsuarioLDAP() {
		if (this.usuario == null) {
			this.usuario = new Usuario();
			this.usuario.setCodigoUsuario(securityContext.getUserPrincipal().getName().toUpperCase());
		}
		return this.usuario;
	}

	@PostConstruct
	protected void carregarUsuarioLogado() {
		try {
			this.empregado = getEmpregado(getSecurityContext().getUserPrincipal().getName().toUpperCase());
		} catch (BusinessException e) {
			MessageDefault msg = new MessageDefault();
			List<String> listaMensagens = new ArrayList<String>();
			listaMensagens.add(e.getMessage());
			msg.setMensagemErro(listaMensagens);
			msg.setCategoriaErro("4");
			msg.setCodigoErro("SQL");
			msg.setOrigemErro("Conexão LDAP");
			msg.setParagrafoErro("");
			msg.setSeveridade(1);
			msg.setSistema("SICCR");
			msg.setInformacoesAdicionais(e.getCause().toString());
			throw new SystemException(msg);
		}
		Usuario usuarioLogado = this.getDadosUsuarioLogado();
		usuarioLogado.setCodigoUsuario(getSecurityContext().getUserPrincipal().getName().toUpperCase());
		UsuarioCorrente.set(usuarioLogado);
	}
	
	public boolean temPerfilUsuario(String role) {
		return securityContext.isUserInRole(role);
	}
	
}