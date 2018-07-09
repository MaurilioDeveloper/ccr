package br.gov.caixa.ccr.negocio;

import java.text.ParseException;
import java.util.List;

import javax.ejb.Local;

import br.gov.caixa.ccr.dominio.Auditoria;
import br.gov.caixa.ccr.dominio.Convenio;
import br.gov.caixa.ccr.dominio.ConvenioCanal;
import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.enums.EnumTipoAuditoria;


@Local
public interface IConvenioBean {

	Convenio consultar(Long id, String userLog) throws Exception;
	Object consultarSR(Long id) throws Exception;
	ConvenioCanal consultarConvenioCanal(Long idConvenio, Long canal) throws Exception;
	Retorno salvar(Convenio convenio, String usuario, boolean isUserInRole) throws Exception;
	Retorno alterar(Convenio convenio, String usuario, boolean isUserInRole) throws Exception;
	List<Convenio> lista(Integer id, Long cnpj, String nome, Integer situacao, Integer sr, Integer agencia, List<Long> canais) throws Exception ;
	List<Auditoria> funcoesCanalAuditoria(Long nuCanal, Long nuConvenio, String usuario, EnumTipoAuditoria tipoAuditoria, ConvenioCanal convenioCanal);
	Retorno auditoriaCanaisPersiste(List<Auditoria> auditorias) throws IllegalArgumentException, IllegalAccessException, ParseException;
}
