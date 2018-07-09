var Combo = null;

// Filename: app.js
define(['controle/DashboardControle', 'modelo/Combo'], 
function( DashboardControle, ComboModel ){
	var initialize = function(){
		console.log('CCR app - ini');
				
		// variavel global do modelo combo
		Combo = ComboModel;
		
		console.log('CCR app - end');
	};
	
	return {
		initialize: initialize
	};
});