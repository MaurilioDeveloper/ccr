/**
 * @author f620600
 * 
 * JavaScript para o objeto Mensagem
 * 
 * JS do tipo enumerado que contem as mesnagem : 4.1. Mensagens de Negócio 4.2.
 * Mensagens de Hint 4.3. Mensagens de Apresentação.
 * 
 * VIDE DOCUMENTO SICCRWEB – Sistema de Controle de Credito WEB Mensagens do
 * Sistema
 * 
 * @version 1.0.0.0
 * 
 * 
 */
define(
		[],
		function() {

			var enums = {
				manterIOF : {},
				manterGrupo : {},
				manterJuros : {},
				manterSeguro : {},
				convenio : {},
				SICLI : {},
				SIRIC : {},
				SIABE : {},
				simular : {}
			};

			enums.manterGrupo = {
				MA0046 : "Confirma operação?",
				MA001 : "Operação realizada com sucesso.",
				MA004 : "Operação realizada com sucesso.",
				MA0003 : "Operação realizada com sucesso.",
				MA0069 : "Nenhum informação do filtro foi informada.",
				MA0093 : "Código já possui cadastro.",
				MA0004 :  "Nenhum registro encontrado.'" 
					
					
			},
			enums.manterIOF = {
				MN001 : "Taxa IOF não pode ser alterada pois esta vigente",
				MN002 : "Taxa IOF não pode ser excluída pois esta vigente",

				MH001 : "",

				MA001 : "Taxa IOF incluída com sucesso.",
				MA002 : "Taxa IOF alterada com sucesso.",
				MA003 : "Operação realizada com sucesso.",
				MA004 : "Taxa de juros excluída com sucesso.",
				MA0046 : "Confirma operação?",
				MA0052 : "Já existe alíquota para o período informado."
			};

			enums.manterJuros = {
				MN001 : "Taxa de juros não pode ser alterada pois esta vigente",
				MN002 : "Taxa de juros não pode ser excluída pois esta vigente",
				MN003 : "Taxa de juros não pode ser incluída pois já existe uma taxa cadastrada com esse período de vigência.",
				MN004 : "Taxa de juros não pode ser alterada pois já existe uma taxa cadastrada com esse período de vigência.",
				MA0052 : "Já existe alíquota para o período informado.",
				MA0053 : "Valor Fim do Prazo não pode ser menor que o Valor Início do Prazo.",
				MA0054 : "Valor Início do Prazo não pode ser igual a zero.",

				MH001 : "",

				MA001 : "Taxa de juros incluída com sucesso.",
				MA002 : "Taxa de juros alterada com sucesso.",
				MA003 : "Confirma exclusão do item selecionado?",
				MA0046 : "Confirma operação?",
				MA004 : "Taxa de juros excluída com sucesso.",
				MA005 : "Operação realizada com sucesso.",
				MA006 : "Não existe taxa de juros cadastrada para o convênio informado."
			};

			enums.manterSeguro = {
				MN001 : "Taxa de seguro não pode ser alterada pois esta vigente",
				MN002 : "Taxa de seguro não pode ser excluída pois esta vigente",
				MN003 : "Taxa de seguro não pode ser incluída pois já existe uma taxa cadastrada com esse período de vigência.",
				MA0052 : "Já existe alíquota para o período informado.",
				MA0054 : "Valor Início do Prazo não pode ser igual a zero.",
				MA0053 : "Valor Fim do Prazo não pode ser menor que o Valor Início do Prazo.",
				MA0055 : "Valor Início de Idade não pode ser igual a zero.",
				MA0056 : "Valor Fim da Idade não pode ser igual a zero.",
				ll056 : "Valor Fim de Idade não pode ser menor que o Valor Início de Idade.",
				MN004 : "Taxa de seguro não pode ser alterada pois já existe uma taxa cadastrada com esse período de vigência.",

				MH001 : "",

				MA001 : "Taxa de seguro incluída com sucesso.",
				MA002 : "Taxa de seguro alterada com sucesso.",
				MA003 : "Confirma exclusão do item selecionado?",
				MA004 : "Taxa de seguro excluída com sucesso.",
				MA0046 : "Confirma operação?",
				MA005 : "Operação realizada com sucesso."
			};

			enums.convenio = {

				// Dados obrigatórios não info1rmados - MA0001
				MA001 : "Nenhum convênio encontrado com o filtro informado!",
				MA002 : "Convênio  salvo com sucesso!",
				MA003 : "Operação realizada com sucesso.",
				MA004 : "Convenente não existe no SICLI.",
				MA005 : "Usuário não autorizado a executar a transação no SICLI.",
				MA006 : "Exclusão realizada com sucesso.",
				MA0010 : "Este canal não pode ser excluido, pois possui registro vinculado em alguma tabela.",
				MA0012 : "Prazo máximo não pode ser maior que 120 meses.",
				MA0024 : "CNPJ inválido.",
				MA0059 : "A unidade informada não é um SR.",
				MA0060 : "Unidade informada não é Agência.",
				MA0061 : "Agência não pertence à SR informada.",
				MA0067 : "Convênio não encontrado.",
				MA0062 : "O dia de Crédito não pode estar fora do intervalo (1 a 28).",
				MA0068 : "Convenente não existe no SICLI.",
				MA0069 : "Nenhuma informação do filtro foi informada.",
				MA0070 : "Órgão não informado. (Grupo Averbação igual a MPOG).",
				MA0072 : "Situação só pode ser ativa quando tiver um canal cadastrado.",
				MA0073 : "Prazo de Emissão não pode estar fora do intervalo (7 a 55).",
				MA0074 : "SR já Informado.",
				MA0075 : "Situação Ativa para Perfil Agência na Inclusão.",
				MA0076 : "Código Convênio não existe.",
				MA0087 : "CNPJ informado é uma Convenente.",
				MA0097 : "Fonte Pagadora não é CNPJ, Formal e Tipo (1, 2 e 3).",
				MA0099 : "Convenente não pode ser um CNPJ Vinculado.",
				MA0100 : "CNPJ informado já vinculado ao convênio <código do convênio>.",
				MA0101 : "Pelo menos uma opção entre a Contratação e a Renovação deve ser permitida.",
			};

			enums.SICLI = {
				MA001 : "Resposta n&atilde;o recebida do SICLI ap&oacute;s 5 tentativas. Tente mais tarde.",
				MA0026 : "Cliente não possui cadastro no SICLI.",

				MN004 : "Cliente n&atilde;o possui cadastro no SICLI!<br />Favor proceder com o cadastro do cliente no SICLI para continuar.",

			};

			enums.SIABE = {
				MA001 : "Resposta n&atilde;o recebida do SIABE ap&oacute;s 5 tentativas. Tente mais tarde.",

			};

			enums.SIRIC = {
				MA001 : 'Resposta n&atilde;o recebida do SIRIC ap&oacute;s 5 tentativas. <br />Para realizar nova tentativa, selecione a op&ccedil;&atilde;o Solicitar Avalia&ccedil;&atilde;o',
			};

			enums.simular = {
				MN001 : "Conv&ecirc;nio n&atilde;o possui o canal (1 - AG&Ecirc;NCIA) configurado!",
				MN002 : "Canal configurado no conv&ecirc;nio para n&atilde;o permitir contrata&ccedil;&atilde;o!",
				MN003 : "Idade do cliente est&aacute; fora da faixa permitida pela opera&ccedil;&atilde;o!",
				MN004 : "Data da contrata&ccedil;&atilde;o superior a quantidade m&aacute;xima poss&iacute;l de dias para contrata&ccedil;&atilde;o futura!",
				MN005 : "Prazo informado superior ao prazo m&aacute;ximo permitido pelo conv&ecirc;nio!",
				MA0015 : "Não existe taxa de juros vigente para a simulação.",
				MA001 : "Resposta n&atilde;o recebida ap&oacute;s 5 tentativas. Tente mais tarde.",
				MA004 : "Nenhum registro encontrado.",
				MA0018 : " Para a simulação deve ser informado o valor da parcela ou o valor líquido.",
				MA0094: "Não existe seguro prestamista para as condições solicitadas.",
				MA0080: "Não é permitido contratar com prestamista. Para prosseguir somente pode contratar sem seguro prestamista",
				MA0014: "Não existe IOF vigente para a simulação.",
				MA0081: "Informar um campo para realizar a ação",

			};

			enums.contrato = {
				MA001 : "Contrato cadastrado com sucesso!",
				MA002 : "Proposta cadastrada com sucesso!",
				MA003 : "Nenhuma avaliação encontrada. Para continuar, realize uma solicitação.",
				MA004 : "Avaliação solicitada com sucesso!", 
				MA005 : "Prazo da avaliação menor que o da simulação!",
				MA006 : "Valor da Capacidade de Pagamento Mensal menor que a prestação!",
				MA007 : "Valor do financiamento menor que o valor do contrato!",
				MA008 : "Avaliação bloqueada por alçada!",
				MA009 : "Para continuar, aguarde o desbloqueio.",
				MA010 : "Ocorreu um erro ao desbloquear", 
				MA011 : "Ocorreu um erro ao solicitar Avaliacao de Risco cliente ao Siric",
				MA012 : "Ocorreu um erro ao solicitar Avaliacao de Risco da Operação ao Siric",
				MA013 : "Ocorreu um erro ao solicitar Avaliacao de Risco ao Siric"
					

			};
			enums.autorizar = {
				MA001 : "Campo obrigatório",
				MA002 : "Autorização realizada com sucesso!",
				MA0003 : "Operação realizada com sucesso.",
				MA004 : "Usuário não possui permissão para realizar essa operação",
				MA0010 : "Este Contrato já está NEGADO e não pode mais realizar nenhum autorização.",
			};


			enums.documento = {
				MA001 : "Nenhuma informação do filtro foi informada.",
				MA002 : "Operação realizada com sucesso.",
				MA003 : "Campo modelo do documento obrigatório.",
				MA004 : "Não foram encontrados modelos de contratação vigentes para imprimir o contrato.",
				MA005 : "Já existe contrato vigente no período."
					
			};
			
			enums.arquivo = {
				MA0069 : "Nenhum informação do filtro foi informada.",
				MA004 : "Nenhum registro encontrado."
			};
			
			enums.auditoria = {
				MA004 : "Nenhum registro encontrado."
			};
			
			enums.agendador = {
				MA001 : "Ocorreu um erro ao buscar processo",
				MA002 : "Operação realizada com sucesso.",
				MA003 : "Ocorreu um erro ao agendar processo de Envio Contrato SIAPX"
			};

			return enums;
		});
