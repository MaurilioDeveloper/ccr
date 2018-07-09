var APX_FAKE_SERVER =
	(function(server){
			
		server.respondWith(
				"GET", /\/apx-web\/emprest\/consignado\/consultarMargem\/*/,
				[200, { "Content-Type": "application/json" },
					JSON.stringify(
						{
							codigoOrgao: "2100",
							matriculaServidor:"00205021",
							identificacaoUnica:"000000000",
							valorMargemConsignado:"0000000047000",
							valorMargemUtilizada:"0000000000000",
							valorMargemDisponivel:"0000000047000",
							valorMargemFixa:"0000000000000",
							valorMargemVariavel:"0000000000000",
							valorMargemDisponivelFixa:"0000000000000",
							dataPrimeiroDesconto:"201503",
							codigoUPAG:"000000001",
							ufPAG:"SP",
							cnpj:"0"
						}
					)
				]);
		
		return server;
	})(APX_FAKE_SERVER);

