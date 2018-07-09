var URL_COMBOS = '../ccr-web/consignado/Administracao/Combo/consultar';

function gMontaSelect($select, valor, texto, data, selecionado) {
	
	try {
		var options = $select.prop ? $select.prop('options') : $select.attr('options');

		// remove as opcoes anteriores
		$('option', $select).remove();
		options[options.length] = new Option('Selecione', '');

		$.each(data, function() {
			options[options.length] = new Option(this[texto], this[valor]);
		});

		gSelecionar($select, selecionado);
		$select.trigger("liszt:updated");
		
	} catch (e) {
		console.log('gMontaSelect catch', e);
	}
}

function gSelecionar(select, valorSelecionado) {
	if (valorSelecionado != undefined) {
		select.val(valorSelecionado);
	}
}

function gMensagemCarregandoCombo($select) {
	$('option', $select).remove();
	
	var options = $select.prop ? $select.prop('options') : $select.attr('options');
	options[options.length] = new Option('Carregando...', '');	
}

function descricaoMes(mes) {
	var meses = [ 'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
			'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro' ];
	return meses[mes - 1];
}

function gCarregarComboAnos(idSelect, valorSelecionado) {
	var anoInicio = 2000;
	var mesAtual = obterMesAtual();
	var anoLimite = mesAtual == 12 ? obterAnoAtual() + 1 : obterAnoAtual();

	var anos = '[';
	for (var ano = anoLimite; ano >= anoInicio; ano--) {
		anos += '{"ano" :' + ano + '}';
		if (ano != anoInicio)
			anos += ',';
	}
	anos += ']';

	valorSelecionado = valorSelecionado == '' ? obterAnoAtual()
			: valorSelecionado;

	gMontaSelect(idSelect, 'ano', 'ano', $.parseJSON(anos), valorSelecionado);
}

function gCarregarComboConstantes(select, valorSelecionado, arquivoJson, valor, texto) {
	var caminho = '../ccr-web/resources/constantes/';
	
	valor = valor == undefined || valor == null ? 'id' : valor; 
	texto = texto == undefined || texto == null ? 'descricao' : texto;
	
	$.getJSON(caminho + arquivoJson,
		function(colecao) {
			try{
				gMontaSelect(select, valor, texto, colecao, valorSelecionado);
			}catch(Exception){}
		}
	);
}

function gCarregaComboContaCorrente(el, id, selecionado, listaContaCorrente) {
	
	console.log('Inicio gCarregaComboContaCorrente');
	
	var idSelect = id ? (id != '' ? '#'+id : '#contasCorrentes') : '#contasCorrentes';
	var select = $(idSelect, el);
	
	gMensagemCarregandoCombo(select);
	gMontaSelect(select, 'id', 'descricao', listaContaCorrente, selecionado);
	
}

function gCarregarConvenio(el, id, selecionado) {
	console.log('Inicio gCarregarConvenio');
	
	var idSelect = id ? (id != '' ? '#'+id : '#nuConvenio') : '#nuConvenio';
	var select = $(idSelect, el);
	
	gMensagemCarregandoCombo(select);

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		dataType : "json",
		url : URL_COMBOS,
		data : getComboJson(0, 'dominio'),
		async : false,
		success : function success(data, textStatus, jqXHR) {					
			if (data.codigoRetorno == 0)
				gMontaSelect(select, 'id', 'descricao', data.listaRetorno, selecionado);
			else
				msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + data.mensagemRetorno);
		},
		error : function error(jqXHR, textStatus, errorThrown) {
			console.log('Carrega combo gCarregarConvenio', jqXHR);
			msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + jqXHR.responseText);
		}
	});
	
}

function gCarregarGrupoTaxa(el, id, selecionado) {
	console.log('Inicio gCarregarGrupoTaxa');
	
	var idSelect = id ? (id != '' ? '#'+id : '#nuGrupoTaxa') : '#nuGrupoTaxa';
	var select = $(idSelect, el);
	
	gMensagemCarregandoCombo(select);

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		dataType : "json",
		url : URL_COMBOS,
		data : getComboJson(1, 'dominio'),
		async : false,
		success : function success(data, textStatus, jqXHR) {
			if (data.codigoRetorno == 0)
				gMontaSelect(select, 'id', 'descricao', data.listaRetorno, selecionado);
			else
				msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + data.mensagemRetorno);
		},
		error : function error(jqXHR, textStatus, errorThrown) {
			console.log('Carrega combo gCarregarGrupoTaxa', jqXHR);
			msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + jqXHR.responseText);
		}
	});

}

function gCarregarRemessaExtrato(el, id, selecionado) {
	console.log('Inicio gCarregarRemessaExtrato');
	
	var idSelect = id ? (id != '' ? '#'+id : '#remessaExtrato') : '#remessaExtrato';
	var select = $(idSelect, el);
	
	gMensagemCarregandoCombo(select);

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		dataType : "json",
		url : URL_COMBOS,
		data : getComboJson(2, 'dominio'),
		async : false,
		success : function success(data, textStatus, jqXHR) {
			if (data.codigoRetorno == 0)
				gMontaSelect(select, 'id', 'descricao', data.listaRetorno, selecionado);
			else
				msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + data.mensagemRetorno);
		},
		error : function error(jqXHR, textStatus, errorThrown) {
			console.log('Carrega combo gCarregarRemessaExtrato', jqXHR);
			msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + jqXHR.responseText);
		}
	});

}

function gCarregarSituacao(el, id, selecionado, tipoSituacao) {
	console.log('Inicio gCarregarSituacao');
	
	var idSelect = id ? (id != '' ? '#'+id : '#situacao') : '#situacao';
	var select = $(idSelect, el);
	
	gMensagemCarregandoCombo(select);

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		dataType : "json",
		url : URL_COMBOS,
		data : getComboJson(3, 'dominio', tipoSituacao),
		async : false,
		success : function success(data, textStatus, jqXHR) {
			if (data.codigoRetorno == 0)
				gMontaSelect(select, 'id', 'descricao', data.listaRetorno, selecionado);
			else
				msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + data.mensagemRetorno);
		},
		error : function error(jqXHR, textStatus, errorThrown) {
			console.log('Carrega combo gCarregarSituacao', jqXHR);
			msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + jqXHR.responseText);
		}
	});

}


function gCarregarSituacaoArquivo(el, id, selecionado, tipoSituacao) {
	console.log('Inicio gCarregarSituacaoArquivo');
	
	var idSelect = id ? (id != '' ? '#'+id : '#select_situacao') : '#select_situacao';
	var select = $(idSelect, el);
	
	gMensagemCarregandoCombo(select);
//	gCarregarComboConstantes(select, selecionado, 'situacaoArquivo.json');
	
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		dataType : "json",
		url : URL_COMBOS,
		data : getComboJson(12, 'dominio', tipoSituacao),
		async : false,
		success : function success(data, textStatus, jqXHR) {
			if (data.codigoRetorno == 0)
				gMontaSelect(select, 'id', 'descricao', data.listaRetorno, selecionado);
			else
				msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + data.mensagemRetorno);
		},
		error : function error(jqXHR, textStatus, errorThrown) {
			console.log('Carrega combo gCarregarSituacaoArquivo', jqXHR);
			msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + jqXHR.responseText);
		}
	});

}


function gCarregarTransacao(el, id, selecionado, tipoSituacao) {
	console.log('Inicio gCarregarTransacao');
	
	var idSelect = id ? (id != '' ? '#'+id : '#select_transacao') : '#select_transacao';
	var select = $(idSelect, el);
	
	gMensagemCarregandoCombo(select);
//	gCarregarComboConstantes(select, selecionado, 'transacoes.json');
	
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		dataType : "json",
		url : URL_COMBOS,
		data : getComboJson(13, 'dominio'),
		async : false,
		success : function success(data, textStatus, jqXHR) {
			if (data.codigoRetorno == 0)
				gMontaSelect(select, 'id', 'descricao', data.listaRetorno, selecionado);
			else
				msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + data.mensagemRetorno);
		},
		error : function error(jqXHR, textStatus, errorThrown) {
			console.log('Carrega combo gCarregarTransacaoAuditada', jqXHR);
			msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + jqXHR.responseText);
		}
	});

}




function gCarregarUF(el, id, selecionado) {
	console.log('Inicio gCarregarUF');
	
	var idSelect = id ? (id != '' ? '#'+id : '#uf') : '#uf';
	var select = $(idSelect, el);
	
	gMensagemCarregandoCombo(select);
	gCarregarComboConstantes(select, selecionado, 'uf.json');
	
}

function gCarregarTaxaPadrao(el, id, selecionado) {
	console.log('Inicio gCarregarTaxaPadrao');
	
	var idSelect = id ? (id != '' ? '#'+id : '#taxaPadrao') : '#taxaPadrao';
	var select = $(idSelect, el);
	
	gMensagemCarregandoCombo(select);
	gCarregarComboConstantes(select, selecionado, 'taxaPadrao.json');
	
}

function gCarregarFormaAverbacao(el, id, selecionado) {
	console.log('Inicio gCarregarFormaAverbacao');
	
	var idSelect = id ? (id != '' ? '#'+id : '#formaAverbacao') : '#formaAverbacao';
	var select = $(idSelect, el);
	
	gMensagemCarregandoCombo(select);
	gCarregarComboConstantes(select, selecionado, 'formaAverbacao.json');
	
}

function gCarregarCanal(el, id, selecionado) {
	console.log('Inicio gCarregarCanal');
	
	var idSelect = id ? (id != '' ? '#'+id : '#canal') : '#canal';
	var select = $(idSelect, el);
	
	gMensagemCarregandoCombo(select);

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		dataType : "json",
		url : URL_COMBOS,
		data : getComboJson(4, 'dominio'),
		async : false,
		success : function success(data, textStatus, jqXHR) {
			if (data.codigoRetorno == 0)
				gMontaSelect(select, 'id', 'descricao', data.listaRetorno, selecionado);
			else
				msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + data.mensagemRetorno);
		},
		error : function error(jqXHR, textStatus, errorThrown) {
			console.log('Carrega combo gCarregarSituacao', jqXHR);
			msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + jqXHR.responseText);
		}
	});

}

function gCarregarGrupoAverbacao(el, id, selecionado) {
	console.log('Inicio gCarregarGrupoAverbacao');
	var idSelect = id ? (id != '' ? '#' + id : '#grupoAverbacao')
			: '#grupoAverbacao';
	var select = $(idSelect, el);
	gMensagemCarregandoCombo(select);

		$.ajax({
				type : 'POST',
				contentType : 'application/json',
				dataType : "json",
				url : URL_COMBOS,
				data : getComboJson(5, 'dominio'),
				async : false,
				success : function success(data, textStatus, jqXHR) {
					if (data.codigoRetorno == 0)
						gMontaSelect(select, 'id', 'descricao',
								data.listaRetorno, selecionado);
					else
						msgCCR
								.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />'
										+ data.mensagemRetorno);
				},
				error : function error(jqXHR, textStatus, errorThrown) {
					console.log('Carrega combo gCarregarGrupoAverbacao', jqXHR);
					msgCCR
							.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />'
									+ jqXHR.responseText);
				}
			});

}


function gCarregarConvenioGrupoAverbacao(el, id, grupo, selecionado) {
	console.log('Inicio gCarregarConvenioGrupoAverbacao');
	
	var idSelect = id ? (id != '' ? '#'+id : '#nuConvenio') : '#nuConvenio';
	var select = $(idSelect, el);
	
	gMensagemCarregandoCombo(select);

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		dataType : "json",
		url : URL_COMBOS,
		data : getComboJson(6, 'dominio', grupo),
		async : false,
		success : function success(data, textStatus, jqXHR) {					
			if (data.codigoRetorno == 0)
				gMontaSelect(select, 'id', 'descricao', data.listaRetorno, selecionado);
			else
				msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + data.mensagemRetorno);
		},
		error : function error(jqXHR, textStatus, errorThrown) {
			console.log('Carrega combo gCarregarConvenioGrupoAverbacao', jqXHR);
			msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + jqXHR.responseText);
		}
	});
	
}

function gCarregarTransacaoLog(el, id, selecionado) {
	console.log('Inicio gCarregarTransacaoLog');
	
	var idSelect = id ? (id != '' ? '#'+id : '#transacao') : '#transacao';
	var select = $(idSelect, el);
	
	gMensagemCarregandoCombo(select);

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		dataType : "json",
		url : URL_COMBOS,
		data : getComboJson(7, 'dominio'),
		async : false,
		success : function success(data, textStatus, jqXHR) {
			if (data.codigoRetorno == 0)
				gMontaSelect(select, 'id', 'descricao', data.listaRetorno, selecionado);
			else
				msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + data.mensagemRetorno);
		},
		error : function error(jqXHR, textStatus, errorThrown) {
			console.log('Carrega combo gCarregarTransacaoLog', jqXHR);
			msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + jqXHR.responseText);
		}
	});

}

function gCarregarFuncionalidadeAuditoria(el, id, selecionado) {
	console.log('Inicio gCarregarFuncionalidadeAuditoria');
	
	var idSelect = id ? (id != '' ? '#'+id : '#funcionalidade') : '#funcionalidade';
	var select = $(idSelect, el);
	
	gMensagemCarregandoCombo(select);
	gCarregarComboConstantes(select, selecionado, 'funcionalidadesAudit.json');
	
}

function getComboJson(id, descricao) {
	var combo = {
		"id" : id,
		"descricao" : descricao
	};
	return JSON.stringify(combo);
}

function getComboJson(id, descricao, filtroNumerico) {
	var combo = {
		"id" : id,
		"descricao" : descricao,
		"filtroNumerico" : filtroNumerico
	};
	return JSON.stringify(combo);
}

// carrega combo especifico para o Incluir/Alterar Convenio

function gMontaSelectConvenio($select, valor, texto, data, selecionado) {
	
	try {
		var options = $select.prop ? $select.prop('options') : $select.attr('options');

		// remove as opcoes anteriores
		$('option', $select).remove();
		options[options.length] = new Option('Selecione', '');

		$.each(data, function() {
			options[options.length] = new Option(this[valor]+" - "+this[texto], this[valor]);
		});

		gSelecionar($select, selecionado);
		$select.trigger("liszt:updated");
		
	} catch (e) {
		console.log('gMontaSelect catch', e);
	}
}

// Grupo Taxa Combo Incluir/Alterar Convenio
function gMontaSelectGTConvenio($select, valor, texto, data, selecionado) {
	
	try {
		var options = $select.prop ? $select.prop('options') : $select.attr('options');

		// remove as opcoes anteriores
		$('option', $select).remove();
		options[options.length] = new Option('Selecione', '');

		$.each(data, function(index, obj) {
			options[options.length] = new Option(obj.codigo+" - "+this[texto], this[valor]);
		});

		gSelecionar($select, selecionado);
		$select.trigger("liszt:updated");
		
	} catch (e) {
		console.log('gMontaSelect catch', e);
	}
}


function gCarregarGrupoTaxaConvenio(el, id, selecionado) {
	console.log('Inicio gCarregarGrupoTaxa');
	
	var idSelect = id ? (id != '' ? '#'+id : '#nuGrupoTaxa') : '#nuGrupoTaxa';
	var select = $(idSelect, el);
	
	gMensagemCarregandoCombo(select);

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		dataType : "json",
		url : URL_COMBOS,
		data : getComboJson(1, 'dominio'),
		async : false,
		success : function success(data, textStatus, jqXHR) {
			if (data.codigoRetorno == 0)
				gMontaSelectGTConvenio(select, 'id', 'descricao', data.listaRetorno, selecionado);
			else
				msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + data.mensagemRetorno);
		},
		error : function error(jqXHR, textStatus, errorThrown) {
			console.log('Carrega combo gCarregarGrupoTaxa', jqXHR);
			msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + jqXHR.responseText);
		}
	});

}

function gCarregarSituacaoConvenio(el, id, selecionado, tipoSituacao) {
	console.log('Inicio gCarregarSituacao');
	
	var idSelect = id ? (id != '' ? '#'+id : '#situacaoIncluir') : '#situacaoIncluir';
	var select = $(idSelect, el);
	
	gMensagemCarregandoCombo(select);

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		dataType : "json",
		url : URL_COMBOS,
		data : getComboJson(3, 'dominio', tipoSituacao),
		async : false,
		success : function success(data, textStatus, jqXHR) {
			if (data.codigoRetorno == 0)
				gMontaSelectConvenio(select, 'id', 'descricao', data.listaRetorno, selecionado);
			else
				msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + data.mensagemRetorno);
		},
		error : function error(jqXHR, textStatus, errorThrown) {
			console.log('Carrega combo gCarregarSituacao', jqXHR);
			msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + jqXHR.responseText);
		}
	});

}

function gCarregarGrupoAverbacaoConvenio(el, id, selecionado) {
	console.log('Inicio gCarregarGrupoAverbacao');
	var idSelect = id ? (id != '' ? '#' + id : '#grupoAverbacao')
			: '#grupoAverbacao';
	var select = $(idSelect, el);
	gMensagemCarregandoCombo(select);

		$.ajax({
				type : 'POST',
				contentType : 'application/json',
				dataType : "json",
				url : URL_COMBOS,
				data : getComboJson(5, 'dominio'),
				async : false,
				success : function success(data, textStatus, jqXHR) {
					if (data.codigoRetorno == 0)
						gMontaSelectConvenio(select, 'id', 'descricao',
								data.listaRetorno, selecionado);
					else
						msgCCR
								.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />'
										+ data.mensagemRetorno);
				},
				error : function error(jqXHR, textStatus, errorThrown) {
					console.log('Carrega combo gCarregarGrupoAverbacao', jqXHR);
					msgCCR
							.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />'
									+ jqXHR.responseText);
				}
			});

}

function gCarregarCanalConvenio(el, id, selecionado) {
	console.log('Inicio gCarregarCanal');
	
	var idSelect = id ? (id != '' ? '#'+id : '#canal') : '#canal';
	var select = $(idSelect, el);
	
	gMensagemCarregandoCombo(select);

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		dataType : "json",
		url : URL_COMBOS,
		data : getComboJson(4, 'dominio'),
		async : false,
		success : function success(data, textStatus, jqXHR) {
			if (data.codigoRetorno == 0)
				gMontaSelectConvenio(select, 'id', 'descricao', data.listaRetorno, selecionado);
			else
				msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + data.mensagemRetorno);
		},
		error : function error(jqXHR, textStatus, errorThrown) {
			console.log('Carrega combo gCarregarSituacao', jqXHR);
			msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + jqXHR.responseText);
		}
	});

}

function gCarregarUFConvenio(el, id, selecionado, tipoSituacao) {
	console.log('Inicio gCarregarUF');
	
	var idSelect = id ? (id != '' ? '#'+id : '#uf') : '#uf';
	var select = $(idSelect, el);
	
	gMensagemCarregandoCombo(select);

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		dataType : "json",
		url : URL_COMBOS,
		data : getComboJson(8, 'dominio'),
		async : false,
		success : function success(data, textStatus, jqXHR) {
			if (data.codigoRetorno == 0)
				gMontaSelectConvenioUf(select, 'id', 'descricao', data.listaRetorno, selecionado);
			else
				msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + data.mensagemRetorno);
		},
		error : function error(jqXHR, textStatus, errorThrown) {
			console.log('Carrega combo gCarregarSituacao', jqXHR);
			msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + jqXHR.responseText);
		}
	});

}

function gMontaSelectConvenioUf($select, valor, texto, data, selecionado) {
	
	try {
		var options = $select.prop ? $select.prop('options') : $select.attr('options');

		// remove as opcoes anteriores
		$('option', $select).remove();
		options[options.length] = new Option('Selecione', '');

		$.each(data, function() {
			options[options.length] = new Option(this[valor]);
		});

		gSelecionar($select, selecionado);
		$select.trigger("liszt:updated");
		
	} catch (e) {
		console.log('gMontaSelect catch', e);
	}
}

function gCarregarSituacaoConvenioAlterar(el, id, selecionado, tipoSituacao) {
	console.log('Inicio gCarregarSituacao');
	
	var idSelect = id ? (id != '' ? '#'+id : '#situacaoAlterar') : '#situacaoAlterar';
	var select = $(idSelect, el);
	
	gMensagemCarregandoCombo(select);

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		dataType : "json",
		url : URL_COMBOS,
		data : getComboJson(3, 'dominio', tipoSituacao),
		async : false,
		success : function success(data, textStatus, jqXHR) {
			if (data.codigoRetorno == 0)
				gMontaSelectConvenio(select, 'id', 'descricao', data.listaRetorno, selecionado);
			else
				msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + data.mensagemRetorno);
		},
		error : function error(jqXHR, textStatus, errorThrown) {
			console.log('Carrega combo gCarregarSituacao', jqXHR);
			msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + jqXHR.responseText);
		}
	});

}

function gCarregaComboContaCorrenteAlteraConvenio(el, id, selecionado, listaContaCorrente) {
	
	console.log('Inicio gCarregaComboContaCorrente');
	
	var idSelect = id ? (id != '' ? '#'+id : '#contasCorrentes') : '#contasCorrentes';
	var select = $(idSelect, el);
	
	gMensagemCarregandoCombo(select);
	gMontaSelect(select, 'id', 'descricao', listaContaCorrente, selecionado);
	
}

function gMontaSelectAlteraConvenio($select, valor, texto, data, selecionado) {
	
	try {
		var options = $select.prop ? $select.prop('options') : $select.attr('options');

		// remove as opcoes anteriores
		$('option', $select).remove();
		
		$.each(data, function() {
			options[options.length] = new Option(this[texto], this[valor]);
		});

		gSelecionar($select, selecionado);
		$select.trigger("liszt:updated");
		
	} catch (e) {
		console.log('gMontaSelect catch', e);
	}
}


function gCarregarCampoDinamico(el, id, selecionado) {
console.log('Inicio gCarregarCanal');
	
	var idSelect = id ? (id != '' ? '#'+id : '#canal') : '#canal';
	var select = $(idSelect, el);
	
	gMensagemCarregandoCombo(select);

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		dataType : "json",
		url : URL_COMBOS,
		data : getComboJson(10, 'dominio'),
		async : false,
		success : function success(data, textStatus, jqXHR) {
			if (data.codigoRetorno == 0)
				gMontaSelectDocumento(select, 'id', 'descricao', data.listaRetorno, selecionado);
			else
				msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + data.mensagemRetorno);
		},
		error : function error(jqXHR, textStatus, errorThrown) {
			console.log('Carrega combo gCarregarSituacao', jqXHR);
			msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + jqXHR.responseText);
		}
	});
}

function gCarregarCampoTipoDocumento(el, id, selecionado) {
console.log('Inicio gCarregarCanal');
	
	var idSelect = id ? (id != '' ? '#'+id : '#canal') : '#canal';
	var select = $(idSelect, el);
	
	gMensagemCarregandoCombo(select);

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		dataType : "json",
		url : URL_COMBOS,
		data : getComboJson(9, 'dominio'),
		async : false,
		success : function success(data, textStatus, jqXHR) {
			if (data.codigoRetorno == 0)
				gMontaSelectConvenio(select, 'id', 'descricao', data.listaRetorno, selecionado);
			else
				msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + data.mensagemRetorno);
		},
		error : function error(jqXHR, textStatus, errorThrown) {
			console.log('Carrega combo gCarregarSituacao', jqXHR);
			msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + jqXHR.responseText);
		}
	});

}

function gMontaSelectDocumento($select, valor, texto, data, selecionado) {
	
	try {
		var options = $select.prop ? $select.prop('options') : $select.attr('options');

		// remove as opcoes anteriores
		$('option', $select).remove();
		options[options.length] = new Option('Selecione', '');

		$.each(data, function() {
			options[options.length] = new Option(this[texto], this[valor]);
		});

		gSelecionar($select, selecionado);
		$select.trigger("liszt:updated");
		
	} catch (e) {
		console.log('gMontaSelect catch', e);
	}
}


//# sourceURL=comboUtils.js