/**
 * Classe para representar um stub sinon.
 */
//Construtor
var StubSinon = function(url, metodo, json){
	this.url = url;
	this.metodo = metodo;
	this.json = json;
};

//Definição dos métodos da classe
StubSinon.prototype = 
(function(){	
	return {
		
	};
}	
)();

//Módulo para encapsular os stubs
var STUB = (function(){	
	var stubs = [];
	
	var obterStubs = function(){
		return stubs;
	};
	
	var adicionarStub = function(url, metodo, json){
		stubs.push(new StubSinon(url, metodo, json));
	};
	
	return {
		obterStubs : obterStubs,
		adicionarStub : adicionarStub
	};
}	
)();