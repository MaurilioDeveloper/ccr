/**
 * Responsável por centralizar a lógica de comunicação com a mensageria(JMS, MQ) disponibilizada atraés dos serviços REST.
 */
define(['enumeracoes/eMensageria'], 
	function(EMensageria){
		
		const QT_TENTATIVAS = 3;		
		const INTERVALO_TENTATIVAS = 1000;  //intervalo em milisegundos
		
		//As Funções criadas antes do modelo são privadas, portanto, acessíveis apenas internamente neste modelo
		
		/**
		 * A ser executada quando houver resposta de sucesso na requisição.
		 * Deve receber como parâmetro um objeto do modelo ComunicacaoAssincrona.
		 */
		var resposta = function(modelo){
			console.log('Mensageria - resposta - ini', modelo);
			
			//Verifica se o modelo já possui um correlationId
			if(modelo.get('correlationId') != undefined){
				//Se não houver retorno
				if(modelo.get('dados') == null){
					//Verifica o numero de tentativas ainda não foi excedido
					if(modelo.tentativas-- > 0){
						//Agenda a execução de uma nova requisição no javascript de acordo com o intervalo definido no modelo
						setTimeout(function(){
							modelo.requisicao(modelo.urlRest, modelo.eventoSucesso, modelo.tentativas, modelo.intervalo, false);
						}, modelo.intervalo);						
					}else{
						modelo.trigger(EMensageria.NAO_RECEBIDO, modelo);
					}
				}else{
					modelo.trigger(modelo.eventoSucesso, modelo); //Aciona o evento configurado
				}
			}

			console.log('Mensageria - resposta - fim');
		};
		
		/**
		 * A ser executada quando houver resposta de erro na requisição.
		 * Parâmetros: modelo backbone de origem, resposta HTTP e XmlHttpRequest
		 */
		var erro = function(modelo, response, XHR){
			console.log('Resposta de erro!');
		};
		
		//---------------------------------------------------------------------------------------------------------
		var Mensageria = Backbone.Model.extend({
			
			defaults : {
				correlationId : undefined, //Identificador único da mensagem enviada ao provedor de mensagens
				dados : undefined          //JSON com os dados retornados em caso de sucessos
			},
			
			initialize : function() {
				//Inclusão das funções a serem executados quando houver resposta à requisição, por tratar-se de comunicação assíncrona AJAX
				this.listenTo(this, 'sync', resposta);
				//Escuta o erro para tratamentos locais 
				this.listenTo(this, 'error', erro);
			},
			
			/**
			 * Realiza a execução da comunicação assíncrona com o servidor.
			 * @params 
			 * - urlRest = url base para a requisição, as urls do serviço devem utilizar o padrão: <urlRest>_PUT para a solicitação de requisição inicial
			 * e <urlRest>_GET para a solicitação de recebimento da resposta à requisição inicial
			 * - eventoSucesso - se houver retorno acionará este evento.
			 * - tentativas - a quantidade de vezes que tentativas para obter a resposta
			 * - intervalo - tempo a aguardar em milisegundos entre as tentativas
			 * - novaSolicitacao - utilizado para permitir que hajam novas tentativas para uma requisição anterior, utilizando o mesmo correlationId
			 * 
			 * Se não houver resposta acionará o evento conforme a constante EVENTO_NAO_RECEBIDO = NAO_RECEBIDO
			 */
			requisicao : function(urlRest, eventoSucesso, tentativas, intervalo, novaSolicitacao, queryParam){
				console.log('Mensageria - requisicao - ini');
				
				//Guarda os atributos do modelo pois após a requisição se não houver retorno, os valores podem se perder
				this.atributosInicio = this.attributes;
				
				//Tipo de método HTTP a ser utilizado na requisição
				var metodoHttp = undefined;
				//Tipo de conteúdo da requisição
				var tipoConteudo = undefined;
				//Dados a serem enviados na requisição
				var dados = undefined;
				
				//Inicializa a quantidade de tentativas, a url de requisição e o intervalo para novas tentativas
				this.urlRest = urlRest;

				//se os parâmetros não forem informados, define valores default
				this.eventoSucesso = eventoSucesso || EMensageria.SUCESSO;
				this.tentativas = (tentativas === undefined ? QT_TENTATIVAS : tentativas);
				this.intervalo = intervalo || INTERVALO_TENTATIVAS;
				
				//Se for passado o parâmetro novaSolicitacao ou se não for informado limpa o correlationId
				if(novaSolicitacao === true || novaSolicitacao === undefined){
					this.unset('correlationId');
				}
				
				if (queryParam == undefined || queryParam == null)
					queryParam = '';
				
				//Se o correlationId não estiver definido significa que ainda não houve uma solicitação de mensageria enviada
				if(this.get('correlationId') == undefined){
					
					if (queryParam != '')
						metodoHttp = "GET";
					else						
						metodoHttp = "POST";
					
					//Define a url com o padrao <urlRest>_PUT para a requisição inicial de PUT
					urlRest = this.urlRest;					
					
					//O tipo de conteúdo de envio do modelo é JSON
					tipoConteudo = "application/json";
					
					//Dados do modelo formatados para JSON
					if (queryParam != '')
						dados = queryParam;
					else 
						dados = JSON.stringify(this.toJSON());	
					
					//Realiza a requisição ao servidor
					this.fetch({
						url: urlRest,
						type: metodoHttp,
						contentType: tipoConteudo,
						data: dados
					});
				}else{
					//Utiliza sempre o post pois é necessário enviar parâmetros
					metodoHttp = "GET";
					//Define a url com o padrao url_PUT para as requisições
					urlRest = this.urlRest+"/"+this.get('correlationId');
					
					//Realiza a requisição ao servidor
					this.fetch({
						url: urlRest,
						type: metodoHttp
					});
				}
				
				console.log('Mensageria - requisicao - fim');
			}
						
		  });
		
	return Mensageria;
	
});