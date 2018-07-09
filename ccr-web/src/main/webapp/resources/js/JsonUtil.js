$cred.fes.ui.JsonUtil = {

	defaultParameters : {
		
	},

	descricao : function descricao(caminho, codigo) {
		var descricao = '';
		$.ajax({url: caminho, dataType : 'json', async : false }).done(
			function(data) { 
				$.each(data, 
					function(i, item) {
						if (item.id == codigo) { 
							descricao = item.descricao;
							return false;
						} 
					}
				);
			}
		); 
		return descricao;
	},
};

//# sourceURL=$cred.fes.ui.JsonUtil.js