var AVALIACAO_RISCO_STUB_SERVER =
	(function(server){
		
		var ativar = function(){
			server = sinon.fakeServer.create();
			
			server.autoRespond = true;
			server.autoRespondAfter = 1000;
			
			server.respondWith(
					"GET", /\/apx-web\/emprest\\avaliacaoRiscoCredito\/recebeAvaliacaoRiscoCredito\/*/,
					[200, { "Content-Type": "application/json" },
						JSON.stringify(
							{
								
								aprovada:"S",
								tomadorValida: {
									codigoAvaliacao: "12345",
									dataInicioValidade:"20150201",
									dataFimValidade:"20151201",
									prazo:"20",
									modalidade: "0",
									produto: "110",
									proposta: "00000000",
									rating: "A00",
									valorCPM: "200,00",
									valorFinanciamento: "3245,00",
									valorLimiteGlobal: "3245,00"
								}
							}
						)
					]);
				
		};
		
		var desativar = function(){
			if(server != undefined){
				server.restore();
			}
		};

		return {
			ativar: ativar,
			desativar: desativar
		};
	})();

