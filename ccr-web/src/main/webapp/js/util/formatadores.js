/**
 * @author c110503
 * 
 * Scritp criado para tratamento das mascaras dos sistema. Tem dependencia das
 * seguintes JS: JQUERY.MASKEDINPUT Version: 1.3.1 JQUERY.PRICE-FORMAT
 * JQUERY.ALPHANUM
 * 
 */
window.formatadores = {
	/**
	 * Função utilizada para formatar CNPJ
	 * 
	 * Depende de JQUERY.MASKEDINPUT
	 */
	formatarCNPJ : function(valor) {
		if(this.verificaValor(valor)){
			return "";
		}
		var formatador = "00.000.000/0000-00";
		return this.formatadorGenerico(valor, formatador);
	},
	
	/**
	 * Função utilizada para formatar CPF
	 * 
	 * Depende de JQUERY.MASKEDINPUT
	 */
	formatarCPF : function(valor) {
		if(this.verificaValor(valor)){
			return "";
		}
		var formatador ="000.000.000-00";
		return this.formatadorGenerico(valor, formatador);
	},
	
	/**
	 * Função utilizada para formatar RG
	 * 
	 * Depende de JQUERY.MASKEDINPUT
	 */
	formatarRG : function(valor) {
		if(this.verificaValor(valor)){
			return "";
		}
		var formatador ="0.000.000";
		return this.formatadorGenerico(valor, formatador);
	},
	/**
	 * Função utilizada para formatar RG_SP
	 * 
	 * Depende de JQUERY.MASKEDINPUT
	 */
	formatarRG_SP : function(valor) {
		if(this.verificaValor(valor)){
			return "";
		}
		var formatador ="0.000.000 aaa";
		return this.formatadorGenerico(valor, formatador);
	},
	
	/**
	 * Função utilizada para formatar RG_SP_UF
	 * 
	 * Depende de JQUERY.MASKEDINPUT
	 */
	formatarRG_SP_UF : function(valor) {
		if(this.verificaValor(valor)){
			return "";
		}
		var formatador ="0.000.000 aaa/aa";
		return this.formatadorGenerico(valor, formatador);

	},
	
	/**
	 * Função utilizada para formatar Telefone
	 * 
	 * Depende de JQUERY.MASKEDINPUT
	 */
	formatarTelefone : function(valor) {
		if(this.verificaValor(valor)){
			return "";
		}
		var formatador ="0000-0000";
		return this.formatadorGenerico(valor, formatador)
	},
	
	/**
	 * Função utilizada para formatar DDDTelefone
	 * 
	 * Depende de JQUERY.MASKEDINPUT
	 */
	formatarDDDTelefone : function(valor) {
		if(this.verificaValor(valor)){
			return "";
		}
		var formatador ="(00) 0000-0000";
		return this.formatadorGenerico(valor, formatador);
	},
	
	/**
	 * Função utilizada para formatar TelefoneCompleto
	 * 
	 * Depende de JQUERY.MASKEDINPUT
	 */
	formatarTelefoneCompleto : function(valor) {
		if(this.verificaValor(valor)){
			return "";
		}
		var formatador = "+00 (00) 0000-0000";
		return this.formatadorGenerico(valor, formatador)
	},
	
	/**
	 * Função utilizada para formatar CEP
	 * 
	 * Depende de JQUERY.MASKEDINPUT
	 */
	formatarCEP : function(valor) {
		if(this.verificaValor(valor)){
			return "";
		}
		var formatador = "00000-000";
		return this.formatadorGenerico(valor, formatador);
	},
	
	/**
	 * Função utilizada para formatar PlacaVeiculo
	 * 
	 * Depende de JQUERY.MASKEDINPUT
	 */
	formatarPlacaVeiculo : function(valor) {
		if(this.verificaValor(valor)){
			return "";
		}
		var formatador = "aaa 0000";
		return this.formatadorGenerico(valor, formatador);
	},
	
	/**
	 * Função utilizada para formatar HoraMinuto
	 * 
	 * Depende de JQUERY.MASKEDINPUT
	 */
	formatarHoraMinuto : function(valor) {
		if(this.verificaValor(valor)){
			return "";
		}
		var formatador = "00:00";
		return this.formatadorGenerico(valor, formatador);
	},
	
	/**
	 * Função utilizada para formatar HoraCompleto
	 * 
	 * Depende de JQUERY.MASKEDINPUT
	 */
	formatarHoraCompleto : function(valor) {
		if(this.verificaValor(valor)){
			return "";
		}
		var formatador = "00:00:00";
		return this.formatadorGenerico(valor, formatador);
	},
	
	/**
	 * Função utilizada para formatar Data
	 * 
	 * Depende de date.js
	 */
	formatarData : function(valor) {
		if(this.verificaValor(valor)){
			return "";
		}
		//Verifica se a data nao contem mais que 10 caracteres para o formato
		// dd/MM/yyyy ou dd-MM-yyyy e variacoes desse formato
		if(valor != null && valor.length > 10){
			valor = valor.substr(0,10).trim();
			
		}
		//Verifica se a data contem somente 7 caracteres e adiciona
		//0 no comeco
		if(valor != null && valor.length === 7){
			valor = "0" + valor;
		}
		//Verifica se a data nao contem 8 caracteres e adiciona as /
		//convencional os padroes ddMMyyyy		
		if(valor != null && valor.length === 8){
			valor = valor.substr(0,2)+"/"+valor.substr(2,2)+"/"+valor.substr(4,4);
			return valor;
		}
		//Verifica se a data  contem 10 caracteres 
		//convencional os padroes ddMMyyyy
		if(valor != null && valor.length === 10 && valor.indexOf("/") != -1){	
			return valor;
		}
		if(valor instanceof Date){
			return formatDate(valor,"dd/MM/yyyy");	
		}
		var data = parseDate(valor);
		if(data != null){
			return formatDate(data,"dd/MM/yyyy");			
		}
		
		return "";
	},	
	
	/**
	 * Função utilizada para formatar 
	 * uma string no formato yyyyMM
	 * para o padrao MM/yyyy
	 * 
	 * Depende de date.js
	 */
	formatarDataMesAno : function(valor) {
		if(this.verificaValor(valor)){
			return "";
		}
		valor = valor.slice(4,6) + "/"+ valor.slice(0,4);
		return valor;
		
	},
	
	/**
	 * Função utilizada para formatar Data por Extenso
	 * 
	 * valor no formato dd/MM/yyyy
	 * Depende de date.js
	 */
	formatarDataExtenso : function(valor) {
		meses = new Array("Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro");
		semana = new Array("Domingo","Segunda-feira","Terça-feira","Quarta-feira","Quinta-feira","Sexta-feira","Sábado");
		
		//invertendo para MM/dd/yyyy
		if(valor.length == 9){
			valor = "0" + valor;
		}
		valor = this.formatarData(valor);
		valor = valor.slice(3,5) + "/" + valor.slice(0,2) + "/" + valor.slice(6,10);
		hoje = new Date(valor);
		dia = hoje.getDate();
		dias = hoje.getDay();
		mes = hoje.getMonth();
		ano = hoje.getYear();
		if (navigator.appName == "Netscape")
		  ano = ano + 1900;
		diaext = semana[dias] + ", " + dia + " de " + meses[mes]
		+ " de " + ano;
		return diaext;
	},
	
	/**
	 * Função utilizada para formatar NumeroConta
	 * 
	 * Depende de JQUERY.MASKEDINPUT
	 */
	formatarNumeroConta : function(valor) {
		if(this.verificaValor(valor)){
			return "";
		}
		var formatador = "9999999999-**";
		return this.formatadorGenerico(valor, formatador);
	},
	
	/**
	 * Função utilizada para formatar NumeroContaCompleto
	 * 
	 * Depende de JQUERY.MASKEDINPUT
	 */
	formatarNumeroContaCompleto : function(valor) {
		if(this.verificaValor(valor)){
			return "";
		}
		var formatador = "9999.999.99999999-9";
		return this.formatadorGenerico(valor, formatador);
	},
	/**
	 * Função utilizada para formatar DataHora
	 * 
	 * Depende de JQUERY.MASKEDINPUT
	 */
	formatarDataHora : function(valor) {
		if(this.verificaValor(valor)){
			return "";
		}
		var formatador = "99/99/9999 99:99";
		return this.formatadorGenerico(valor, formatador);
	},
	
	/**
	 * Função utilizada para formatar NumeroContrato
	 * 
	 * Depende de JQUERY.MASKEDINPUT
	 */
	formatarNumeroContrato : function(valor) {
		if(this.verificaValor(valor)){
			return "";
		}
		var formatador = "00.0000.000.0000000-00";
		return this.formatadorGenerico(valor, formatador);
	},
	
	formatarBeneficio : function(valor) {
		if(this.verificaValor(valor)){
			return "";
		}
		
		var formatador = "0000000000-0";
		return this.formatadorGenerico(valor, formatador);
	},

	
	/**
	 * Função utilizada para formatar Moeda
	 * 
	 * Padrao D*.DD (85201.40)
	 * Saida D*,DD (85.201,40)
	 * 
	 * Depende de JQUERY.MASKEDINPUT
	 */
	formatarMoeda : function(valor) {
		if(this.verificaValor(valor)){
			return "";
		}
		var valorStr = new String(parseFloat(valor).toFixed(2));
		valorStr = valorStr.replace(/\D/g, "");
		valorStr = valorStr.replace(/[0-9]{19}/, "inválido");
		valorStr = valorStr.replace(/(\d{1})(\d{17})$/, "$1.$2");
		valorStr = valorStr.replace(/(\d{1})(\d{14})$/, "$1.$2");
		valorStr = valorStr.replace(/(\d{1})(\d{11})$/, "$1.$2");
		valorStr = valorStr.replace(/(\d{1})(\d{8})$/, "$1.$2");
		valorStr = valorStr.replace(/(\d{1})(\d{5})$/, "$1.$2");
		valorStr = valorStr.replace(/(\d{1})(\d{1,2})$/, "$1,$2");
		//verifcador de valor negativo
		if(typeof valor ==="string" && valor.indexOf('-') != -1)
		{
			valorStr = "-" + valorStr;
		}
		return valorStr;
	},
	
	/**
	 * Função utilizada para formatar Taxas
	 * 
	 * Padrao DD.DDDDD (1.40501)
	 * Saida DD,DDDD (01,40501)
	 * 
	 * Depende de JQUERY.MASKEDINPUT
	 */
	formatarTaxa : function(valor, casasDecimais) {
		if(this.verificaValor(valor)){
			return "";
		}
		
		//casasDecimais = casasDecimais == null || casasDecimais == undefined || isNaN(casasDecimais) ? 2 : casasDecimais;		
		//var valorStr = new String(parseFloat(valor).toFixed(5));

		valor = "" + valor;
		valor = valor.replace(",", ".");
		valor = Number(valor).toFixed(casasDecimais);		
		
		//valorStr = valorStr.replace(/[^.]*/, function (n) { return n.length > 1 ? n: "0" + n; });
		//valorStr = valorStr.replace(/\D/g, "");
		//valorStr = valorStr.replace(/(\d{2})(\d{1,5})$/, "$1,$2");
		//valorStr = valorStr.replace(/[.]/g, ",");
		
		valorStr = valor.replace(".", ",");		

		//verifcador de valor negativo
		if(typeof valor ==="string" && valor.indexOf('-') != -1)
			valorStr = "-" + valorStr;
		
		return valorStr;
	},
	
	/**
	 * Função utilizada para formatar Moeda sem formato
	 * 
	 * Padrao D* (8520140)
	 * Saida D*,DD (85.201,40)
	 * 
	 * Depende de JQUERY.MASKEDINPUT
	 */
	formatarMoedaSemFormato : function(valor) {
		if(this.verificaValor(valor)){
			return "";
		}
		valor = this.retiraFormatoComSinal(valor);
		//formata o valor para  D*.DD (85201.40)
		var valorStr = valor.slice(0,valor.length-2) + "." + valor.slice(valor.length-2,valor.length);
		valorStr = this.formatarMoeda(valorStr);
		
		return valorStr;
	},
	
	/**
	 * CFunção utilizada para converte moeda 
	 * em um float com 2 casas fixas.
	 * 
	 * @param valor
	 * @returns float
	 */
	formatarMoedaParaFloat : function (valor) {
		if (valor == '' || valor == undefined || valor == null) {
			return 0;
		}
		//quando o ultimo caracter for .		
		if((valor.length - valor.lastIndexOf(".")) == 3){
			valor = valor.replace(/\.(?=[^.]*$)/, ",");
		}
		//quando nao conter , retorna o mesmo valor
		if(valor.indexOf(",") == -1){
			return valor;
		}
		
		return valor.replace(/\.+/g, "").replace(/\,/, '.');
	},
	
	/**
	 * Função que formata um valor com a mascara informada.
	 * 
	 * @param valor
	 *            a ser formatado
	 * @param Mascara
	 *            formato da mascara
	 * @returns {String} valor mascarado
	 * @see http://forum.imasters.com.br/topic/222133-mascara-e-validao-de-cpfcnpjcepdatatelefone/
	 * @since 11/07/2013
	 */
	formatadorGenerico : function (valor, Mascara) {
		var boleanoMascara;
		var exp = /\-|\.|\/|\(|\)| /g;
		var mascaraSemMascara = Mascara.replace(exp, '');
		campoSoNumeros = new String(valor);

		// Criar metodo fora para o tratamento
		if (mascaraSemMascara.length !== campoSoNumeros.length) {
			var caract = mascaraSemMascara.length - campoSoNumeros.length;
			var valorCompleto = mascaraSemMascara.substring(0, caract) +''+ campoSoNumeros;
			campoSoNumeros = valorCompleto;
		}

		var posicaoCampo = 0;
		var NovoValorCampo = '';
		var TamanhoMascara = campoSoNumeros.length;

		for ( var i = 0; i <= TamanhoMascara; i++) {

			boleanoMascara = ((Mascara.charAt(i) == '-')
					|| (Mascara.charAt(i) == '.') || (Mascara.charAt(i) == '/'));
			boleanoMascara = boleanoMascara
					|| ((Mascara.charAt(i) == '(') || (Mascara.charAt(i) == ')') || (Mascara.charAt(i) == ' '));

			if (boleanoMascara) {
				NovoValorCampo += Mascara.charAt(i);
				TamanhoMascara++;
			} else {
				NovoValorCampo += campoSoNumeros.charAt(posicaoCampo);
				posicaoCampo++;
			}
		}

		return NovoValorCampo;
	},
	
	/**
	 * Retira mascara de valores numericos.
	 * 
	 * @param valor
	 * @returns valor
	 * @since 22/10/2013
	 */
	retiraFormato : function (valor) {
		return valor.replace(/[^\d]+/g, '');
	},
	
	/**
	 * Retira mascara de valores numericos mantendo o sinal.
	 * 
	 * @param valor
	 * @returns valor
	 * @since 04/05/2015
	 */
	retiraFormatoComSinal : function (valor) {
		return valor.replace(/[^\d\-]+/g, '');
	},
	
	/**
	 * 
	 */
	verificaValor : function(valor){		
		if(valor == null || valor === "" ){
			return true;
		}		
	},
	
	left: function (valor, n) {		
		return valor == null || valor == '' ? '' : (valor.length <= n ? valor : valor.slice(0, n));
	},
	
	right: function (valor, n) {		
		return valor == null || valor == '' ? '' : (valor.length <= n ? valor : valor.slice(-n));
	},
	
	formatarMoedaSemFormatoParaFloat : function(valor) {
		if(this.verificaValor(valor)){
			return "";
		}
		valor = this.retiraFormatoComSinal(valor);
		//formata o valor para  D*.DD (85201.40)
		var valorStr = valor.slice(0,valor.length-2) + "." + valor.slice(valor.length-2,valor.length);
		valorStr = this.formatarMoedaParaFloat(valorStr);
		
		return valorStr;
	},
	
};