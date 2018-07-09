/**
 * @author f620600
 * 
 * JavaScript para o tratamento do objeto Retorno
 * 
 * @version 1.0.0.0
 * 
 * 
 */
define(['enumeracoes/eMensagemCCR'], 
function(EMensagemCCR){
		
	var Retorno = { 
		codigo: 0,
		mensagem: '',
		tipo: 'SUCESSO',
		idMsg: '',
		htmlRetorno: '',
		isExcecaoJS: false,
		excecaoArqRef: {
			sistema: '',
			origemErro: '',
			paragrafoErro: '',
			categoriaErro: '',
			codigoErro: '',
			mensagemNegocial: '',
			mensagemErro: '',
			informacoesAdicionais: ''
		},
		
	 retornoMensageria : {'codigo' : '', 'mensagem' : '' , 'tipo' : '', 'idMsg' : '' , 'htmlRetorno' : '', 'isExcecaoJS' : '' , 'excecaoArqRef' : '', 'messages' : [], 'mensagemNegocial' : '' , 'origemErro' : '' , 'sistema' : '' , 'categoriaErro' : ''  },	
		
		initialize: function (Retorno) {
			this.htmlRetorno = '';
			
			this.codigo = ('codigoRetorno' in Retorno) ? Retorno.codigoRetorno : (('codigo' in Retorno) ? Retorno.codigo : 0);
			this.mensagem = ('mensagemRetorno' in Retorno) ? Retorno.mensagemRetorno : (('mensagem' in Retorno) ? Retorno.mensagem : '');
			this.tipo = ('tipoRetorno' in Retorno) ? Retorno.tipoRetorno : (('tipo' in Retorno) ? Retorno.tipo : 'SUCESSO');
			this.idMsg = ('idMsg' in Retorno) ? Retorno.idMsg : '';
			
			if (this.excecaoArqRef==null || this.excecaoArqRef.codigoErro==null ||this.excecaoArqRef==undefined ){				
				this.excecaoArqRef = {
						sistema: '',
						status: '',
						origemErro: '',
						paragrafoErro: '',
						categoriaErro: '',
						codigoErro: '',
						mensagemNegocial: '',
						mensagemErro: '',
						informacoesAdicionais: ''
				};
			}			
		},
		
		trataRetorno: function (retorno, classe, excecaoJS, mostraMsgSucesso) {
			this.initialize(retorno);
			this.isExcecaoJS = excecaoJS == undefined || excecaoJS == null ? false : true;
			
			mostraMsgSucesso = mostraMsgSucesso == undefined ? true : mostraMsgSucesso;
			var erro = 'messages' in retorno ? (retorno.messages.length > 0 ? retorno.messages[0] : {}) : retorno;	
			
			
			var msgArqRef = this.getErroArqRef(erro);
						
			if ((classe != undefined && classe != null) && (this.idMsg != undefined && this.idMsg != null))
				this.mensagem = classe in EMensagemCCR ? ((this.idMsg in EMensagemCCR[classe]) ? EMensagemCCR[classe][this.idMsg] : this.mensagem) : this.mensagem;
			
			if (this.isExcecaoJS)
				console.log('Excecao JS', excecaoJS);
			
			// Adiciona uma mensagem padrao
			if (this.tipo == 'ERRO_EXCECAO'){
				this.htmlRetorno += '<h4><b>Mensagens Erro </h4></b>';
				this.htmlRetorno += 'Ocorreu um erro na aplica&ccedil;&atilde;o. Para consultar mais informa&ccedil;&otilde;es, clique no botão Detalhar. <br />';								  
			}
			
			// Adiciona uma mensagem padrao
			if (this.tipo == 'ERRO_NEGOCIAL'){
//				this.htmlRetorno += '<h4><b>Mensagens Erro </h4></b>';
				this.htmlRetorno += this.tipo == 'ERRO_NEGOCIAL' ? '<b>Erro Negocial:</b><br />' : (this.tipo == 'ERRO_EXCECAO' ? '<br><b>Exce&ccedil;&atilde;o:</b><br />' : '');				
			}
			
			this.htmlRetorno += this.mensagem;
			
			var idDetalheMsg = "msgErroDetalhar" + String(Math.random()).replace('.' , '');
			
			if (msgArqRef != '') {
				var func = '$(\'#'+ idDetalheMsg +'\').toggleClass(\'hide\');';
				this.htmlRetorno += '&nbsp;[<span class="btn btn-mini btn-link" onClick="javascript: '+ func +'"><strong>Detalhar</strong></span>]</b>';
				this.htmlRetorno += '<span class="hide" id="'+ idDetalheMsg +'"><br /><br />' + msgArqRef + '</span>';				
			}
			
			if (this.codigo == 0)
				if (this.tipo == 'SUCESSO') {
					if (mostraMsgSucesso) 
						msgCCR.info(this.htmlRetorno);
				} else {
					msgCCR.aviso(this.htmlRetorno);
				}
			else
				msgCCR.erro(this.htmlRetorno);
			
			// sucesso no retorno: true | erro no retorno: false 
			return (this.codigo == 0);
		},
		
		validarRetorno: function (response) {
			if (response.dados != null && response.dados.responseArqRef.status != '') {
				this.retornoMensageria.excecaoArqRef = response.dados.responseArqRef.status;
				this.retornoMensageria.codigo = this.retornoMensageria.excecaoArqRef.codigo;
				this.retornoMensageria.mensagemRetorno = this.retornoMensageria.excecaoArqRef.mensagem;
				this.retornoMensageria.tipoRetorno = this.retornoMensageria.excecaoArqRef.tipo;
				this.retornoMensageria.idMsg = this.retornoMensageria.excecaoArqRef.origemErro;
				this.retornoMensageria.origemErro =this.retornoMensageria.excecaoArqRef.origemErro;
				this.retornoMensageria.mensagemNegocial = this.retornoMensageria.excecaoArqRef.categoriaErro;
				//this.retornoMensageria.mensagemNegocial.categoriaErro = this.retornoMensageria.excecaoArqRef.categoriaErro;
				this.retornoMensageria.messages.push(this.retornoMensageria.excecaoArqRef);
				return this.trataRetorno(this.retornoMensageria,"", "", true);	
			}else if(response.codigoRetorno == "X5"){
				this.retornoMensageria.excecaoArqRef = "";
				this.retornoMensageria.codigo = response.codigoRetorno;
				this.retornoMensageria.mensagemRetorno = response.mensagemRetorno;
				this.retornoMensageria.tipoRetorno = this.retornoMensageria.excecaoArqRef.tipo;
				this.retornoMensageria.idMsg = this.retornoMensageria.excecaoArqRef.origemErro;
				this.retornoMensageria.origemErro =this.retornoMensageria.excecaoArqRef.origemErro;
				this.retornoMensageria.mensagemNegocial = this.retornoMensageria.excecaoArqRef.categoriaErro;
				this.retornoMensageria.messages.push(this.retornoMensageria.excecaoArqRef);
				return this.trataRetorno(this.retornoMensageria,"", "", true);	
			}else {
				this.excecaoArqRef=null;
			}
			
		},
		
		
		
		getErroArqRef: function (erro) {
			
			try {
				var msg = "";
				
				this.excecaoArqRef.sistema = 'sistema' in erro ? (erro.sistema == null ? '' : erro.sistema) : this.excecaoArqRef.sistema;
				this.excecaoArqRef.origemErro = 'origemErro' in erro ? (erro.origemErro == null ? '' : erro.origemErro) : this.excecaoArqRef.origemErro;
				this.excecaoArqRef.paragrafoErro = 'paragrafoErro' in erro ? (erro.paragrafoErro == null ? '' : erro.paragrafoErro) : this.excecaoArqRef.paragrafoErro;
				this.excecaoArqRef.codigoErro = 'codigoErro' in erro ? (erro.codigoErro == null ? '' : erro.codigoErro) : this.excecaoArqRef.codigoErro;
				this.excecaoArqRef.mensagemErro = 'mensagemErro' in erro ? (erro.mensagemErro == null ? '' : (erro.mensagemErro.length > 0 ? erro.mensagemErro[0] : '')) : this.excecaoArqRef.mensagemErro;
				this.excecaoArqRef.mensagemNegocial = 'mensagemNegocial' in erro ? (erro.mensagemNegocial == null ? '' : (erro.mensagemNegocial.length > 0 ? erro.mensagemNegocial[0] : '')) : this.excecaoArqRef.mensagemNegocial;
				this.excecaoArqRef.categoriaErro = 'categoriaErro' in erro ? (erro.categoriaErro == null ? '' : erro.categoriaErro) : this.excecaoArqRef.categoriaErro;
				
				// prepara detalhes msg
				msg += this.excecaoArqRef.origemErro != '' ? "<b>Detalhe Erro</b>: <br />" + this.excecaoArqRef.origemErro +"<br /><br />" : "";this.excecaoArqRef.origemErro;
				
				if (msg == '')
					return '';
				
				// atualiza variaveis do objeto se for necessario
				this.codigo = this.codigo == "0" ? this.excecaoArqRef.codigoErro : this.codigo;
				this.mensagem = this.mensagem == "" ? this.excecaoArqRef.categoriaErro : this.mensagem;
				this.tipo = this.excecaoArqRef.categoriaErro.toUpperCase().indexOf("NEGOCIAL") > 0 ? "ERRO_NEGOCIAL" : 'ERRO_EXCECAO';
				this.idMsg = this.tipo == "ERRO_NEGOCIAL" ? "MN" + pad(this.excecaoArqRef.codigoErro, 3) : this.idMsg;				
				
				return msg;
			} catch (e) {
				console.info("Retorno, function getErroArqRef(erro)", erro, e);
				return "";
			}
			
		},
		
		getObjetoErroArqRef: function (erro) {
			
			try {
				var msg = "";
				
				this.excecaoArqRef.sistema = 'sistema' in erro ? (erro.sistema == null ? '' : erro.sistema) : this.excecaoArqRef.sistema;
				this.excecaoArqRef.origemErro = 'origemErro' in erro ? (erro.origemErro == null ? '' : erro.origemErro) : this.excecaoArqRef.origemErro;
				this.excecaoArqRef.paragrafoErro = 'paragrafoErro' in erro ? (erro.paragrafoErro == null ? '' : erro.paragrafoErro) : this.excecaoArqRef.paragrafoErro;
				this.excecaoArqRef.codigoErro = 'codigoErro' in erro ? (erro.codigoErro == null ? '' : erro.codigoErro) : this.excecaoArqRef.codigoErro;
				this.excecaoArqRef.mensagemErro = 'mensagemErro' in erro ? (erro.mensagemErro == null ? '' : (erro.mensagemErro.length > 0 ? erro.mensagemErro[0] : '')) : this.excecaoArqRef.mensagemErro;
				this.excecaoArqRef.mensagemNegocial = 'mensagemNegocial' in erro ? (erro.mensagemNegocial == null ? '' : (erro.mensagemNegocial.length > 0 ? erro.mensagemNegocial[0] : '')) : this.excecaoArqRef.mensagemNegocial;
				this.excecaoArqRef.categoriaErro = 'categoriaErro' in erro ? (erro.categoriaErro == null ? '' : erro.categoriaErro) : this.excecaoArqRef.categoriaErro;
					
				return this.excecaoArqRef;
			} catch (e) {
				console.info("Retorno, function getObjetoErroArqRef(erro)", erro, e);
				return "";
			}
			
		}
	
	};
	
	return Retorno;
});
