/*******************************************************************************
 * Classe AjaxStatus *
 * ***********************************************************************
 * Define algoritmos e estruturas para iteracao com o broker. *
 * ***********************************************************************
 * 
 * @author eduardo *
 *         ***********************************************************************
 *         Dependencias: prototype.js 1.7.1 * Utilizacao: * var status = new
 *         AjaxStatus; * status.start(); * status.stop(); *
 ******************************************************************************/

var UserProfile = Class.create({

	/**
	 * Construtor
	 * 
	 * @param id
	 *            Id do componente visual. Se nao for informado o id default
	 *            sera ajaxStatus
	 */
	initialize : function(url, errorManager) {
		this.url = url;
		this.errorManager = errorManager;
	},

	/**
	 * Fecha o ajax status.
	 */
	load : function(idUsuario, idMatricula) {
		that = this;
		$.ajax({
			async : false,
			type : 'POST',
			url : that.url,
			dataType : 'json',
			contentType : 'application/json',
			success : function(data, textStatus, jqXHR) {
				if (data.empregado != null) {
					$('#' + idUsuario).html('Usuário: ' + data.empregado.nomePessoa);
					$('#' + idMatricula).html('Matrícula: ' + data.empregado.matriculaUsuario);
					
					$('#nomeUsuarioDashboard').html(data.empregado.nomePessoa);
					
					usuario = new Usuario(data);
				}

			},
			error : function(jqXHR, textStatus, errorThrown) {
				that.errorManager.ajaxError(jqXHR, textStatus, errorThrown);
			}
		});
	},
	/**
	 * Fecha o ajax status.
	 */
	loadgroups : function() {
		that = this;
		$.ajax({
			async : false,
			type : 'POST',
			url : that.url,
			dataType : 'json',
			contentType : 'application/json',
			success : function(data, textStatus, jqXHR) {
				if (data != null) {
					return;
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				that.errorManager.ajaxError(jqXHR, textStatus, errorThrown);
			}
		});
	}

});
//@ sourceURL=userProfile.js