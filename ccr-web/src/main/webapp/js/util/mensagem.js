/**
 * @author f620600
 * 
 * Scritp criado para tratamento das mensagem dos sistema.
 * 
 * 
 */
define(['text!visao/mensagem.html'], 
function(mensagemViewTpl){
	
	var messages = {
	    	
		msgAlertConfirmTemplate: _.template( _.unescape($(mensagemViewTpl).filter('#template_alerta_confirma').html()) ), 
		msgInfoErroTemplate: _.template( _.unescape($(mensagemViewTpl).filter('#template_info_erro').html()) ),
		
		alerta: function(msg) {
			$('#divGlobalMsgCCR').find('#spnTipoMsgCCR').html('Alerta');
			$('#divGlobalMsgCCR').find('#divDetalheMsgCCR').html(msg);
			$('#divGlobalMsgCCR').find('#divAcoesMsgCCR_Alerta').show();
			$('#divGlobalMsgCCR').find('#divAcoesMsgCCR_Confirma').hide();
									
			$('#divGlobalMsgCCR').modal('show');
		},
		
		confirma: function(msg, callback) {
			$('#divGlobalMsgCCR').find('#spnTipoMsgCCR').html('Confirma&ccedil;&atilde;o');
			$('#divGlobalMsgCCR').find('#divDetalheMsgCCR').html(msg);
			$('#divGlobalMsgCCR').find('#divAcoesMsgCCR_Alerta').hide();
			$('#divGlobalMsgCCR').find('#divAcoesMsgCCR_Confirma').show();
						
			$('#divGlobalMsgCCR').find('#btnConfirmaMsgCCR').off('click').on('click', function () {
				callback();
				$('#divGlobalMsgCCR').modal('hide');
			});			
			
			$('#divGlobalMsgCCR').modal('show');
		},
		
		/***************************************************************************************************/
		// mostra info e erro na parte superior da tela
		info: function(msg){
			var classe = 'alert alert-info';
			$("#messageContainer").html( this.msgInfoErroTemplate({classe: classe, mensagem: msg }));
			$('#messageContainer').fadeIn();
			
			// fecha automaticamente
			setTimeout(function () { 
				$('#messageContainer').fadeOut();
			}, 7000);
		},
		
		aviso: function(msg){
			var classe = 'alert alert-warn';
			$("#messageContainer").html( this.msgInfoErroTemplate({classe: classe, mensagem: msg }));
			$('#messageContainer').fadeIn();
			
			// fecha automaticamente
			setTimeout(function () { 
				$('#messageContainer').fadeOut();
			}, 14000);
		},
		
		erro: function(msg){
			var classe = 'alert alert-error';
			$("#messageContainer").html( this.msgInfoErroTemplate({classe: classe, mensagem: msg}));
			$('#messageContainer').fadeIn();
			
			// fecha automaticamente
			setTimeout(function () { 
				$('#messageContainer').fadeOut();
			}, 21000);
		}
		
	}; 
		
	$('body').append(messages.msgAlertConfirmTemplate());
	
	$('#divGlobalMsgCCR').find('#btnOKMsgCCR').off('click').on('click', function () {
		$('#divGlobalMsgCCR').modal('hide');
	});
	
	$('#divGlobalMsgCCR').find('#btnCancelarMsgCCR').off('click').on('click', function () {
		$('#divGlobalMsgCCR').modal('hide');
	});
	
	return messages;
	
});