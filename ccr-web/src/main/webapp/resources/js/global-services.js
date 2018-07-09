/****************************************************************************
 * Classe GlobalServices *
 * **************************************************************************
 * Define acesso a servicos globais da aplicacao							*
 * **************************************************************************
 * @author eduardo 															*
 ****************************************************************************/

var GlobalServices = Class.create({

	/**
	 * Construtor
	 * 
	 */
	initialize : function(urlBase, errorManager) {
		this.urlBase = urlBase;
		this.errorManager = errorManager;
	},

	/**
	 * Retorna a versao do sistema.
	 */
	updadeVersion : function(uiId) {
		that = this;
		$.ajax({
			async : true,
			type : 'POST',
			url : that.urlBase + "/version",
			dataType : 'json',
			contentType : 'application/json',
			success : function(data, textStatus, jqXHR) {
				if (data) {
					$('#' + uiId).html(data.value);
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				that.errorManager.ajaxError(jqXHR, textStatus, errorThrown);
			}
		});
	},
	
	logout: function() {
		that = this;
		$.ajax({
			async : true,
			type : 'GET',
			url : that.urlBase + "/logout",
			dataType : 'json',
			contentType : 'application/json',
			success : function(data, textStatus, jqXHR) {
				window.location = "/ccr-web";
			},
			error : function(jqXHR, textStatus, errorThrown) {
				that.errorManager.ajaxError(jqXHR, textStatus, errorThrown);
			}
		});
	}
});
