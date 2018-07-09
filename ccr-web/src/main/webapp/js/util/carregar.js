/**
 * @author c110503
 * 
 * Script criado para LOADING
 * 
 */
window.carregar = {
	/**
	 * Função utilizada para iniciar o loading
	 * 
	 */
	iniciar : function() {
		console.log("Carregar iniciar");
		$.ajax({
			async: false,
		}).always(function() {
			$('#ajaxStatus').modal('show');
		});
	},
	
	/**
	 * Inclui uma imagem de loading no objeto do id passado como parâmetro.
	 * @param id
	 */
	incluir : function(el, id){
		el.find(id).append('<div class="loading-img" align="center"><img src="/fec-web/layout/img/loading.gif" alt="Loading..."/></div>');
	},
	
	
	/**
	 * Remove todas as imagens de loading.
	 */
	removerTodos : function(){
		$('.loading-img').remove();
	},

	/**
	 * Função utilizada finalizar o loading
	 * 
	 */
	finalizar : function() {
		console.log("Carregar finalizar");
		$.ajax({
			async: false,
		}).always(function() {
			$('#ajaxStatus').modal('hide');
		});
		
	},

};