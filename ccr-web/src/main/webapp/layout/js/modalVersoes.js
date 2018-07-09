/***
 * @author c110503
 * 
 * JS usuado para carregar dentro do acordion do modal de versões a versão de cada modulo.
 * 
 */
$(document).ready(function() {
	$.get('/ccr-web/versao.html', function(data) {
		console.log(data);
		$('#versao-siccr').html(data);
	});
});