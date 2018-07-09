//Filename: main.js

//Configurações do sistema
var requireCCR = require.config({
	context: 'siccr',
	baseUrl: '/ccr-web',
	waitSeconds: 200,
	urlArgs: "bust=" + (new Date()).getTime(),
	paths: { 
		text: 'require/text',
		order: 'require/order',
		app: 'require/app',
		router: 'require/router',
		configuracoes: 'require/configuracoes',
		enumeracoes: 'servicos/enumeracoes',
		controle: 'servicos/controle',
		modelo: 'servicos/modelo',
		visao: 'servicos/visao',
		util: 'js/util'
	}
});



