package br.gov.caixa.ccr.util;

import br.gov.caixa.ccr.dominio.Usuario;

public class UsuarioCorrente {

	private static ThreadLocal<Usuario> usuariosCorrente = new ThreadLocal<Usuario>();

	public static void set(Usuario usuarioLogado) {
		usuariosCorrente.set(usuarioLogado);
	}

	public static Usuario get() {
		return usuariosCorrente.get();
	}
}