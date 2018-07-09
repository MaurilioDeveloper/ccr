var APX_FAKE_SERVER =
	(function(server){
			
		server.respondWith(
				"POST", /\/apx-web\/emprest\/avaliacaoRiscoCredito\/recebeAvaliacaoRiscoCredito/,
				[200, { "Content-Type": "application/json" },
					JSON.stringify(
						{
							aprovada: {
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
		
		return server;
	})(APX_FAKE_SERVER);
