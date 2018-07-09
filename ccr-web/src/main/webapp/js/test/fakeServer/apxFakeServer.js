var APX_FAKE_SERVER =
	(function(stubs){
		var server = undefined;
		server = sinon.fakeServer.create();
		
		server.xhr.useFilters = true;
		server.autoRespond = true;
		server.autoRespondAfter = 1000;
		
//		server.xhr.addFilter(function(method, url){
//			if(url.match(/\/apx-web\/emprest\/consignado\/consultarMargem\/*/) 
//					|| url.match(/\/apx-web\/emprest\/avaliacaoRiscoCredito\/recebeAvaliacaoRiscoCredito/)){
//				return false;
//			}else{
//				return true;
//			}
//		});
		
		//Filtro para que o fake server não atrapalhe requisições ajax à outros recuros
		server.xhr.addFilter(function(method, url){
			var stubsLen = stubs.length;
			//Verifica todas as urls dos stubs
			for(var i=0; i<stubsLen; i++){
				var stub = stubs[i];
				console.log(stub);
				//Define o retorno para o stub
				server.respondWith(
					stub.metodo, 
					stub.url,
					[200, { "Content-Type": "application/json" },
						JSON.stringify(
							stub.json
						)
					]);
				//Se for uma url dos stubs executa com stub
				if(url.match(stub.url)){
					return false;
				}
			}
			return true;
		});
		return server;
	})(STUB.obterStubs());

