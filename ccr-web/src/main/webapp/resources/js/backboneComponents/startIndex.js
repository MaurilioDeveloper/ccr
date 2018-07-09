(function(MYAPP, $, undefined) {
    // Prevent errors in browsers without console.log
    if (!window.console) window.console = {};
    if (!window.console.log) window.console.log = function(){};

    //Private var
    var console_log = console.log;  

    //Public methods
    MYAPP.enableLog = function enableLogger() { console.log = console_log; };   
    MYAPP.disableLog = function disableLogger() { console.log = function() {}; };

}(window.MYAPP = window.MYAPP || {}, jQuery));


jQuery(document).ready(function() {
	//MYAPP.disableLog(); 
	
	tpl.loadTemplates([ 'dashboardCCR' ], function() {
		app = new AppRouter();
	});
	
	document.title = 'SICCR - Sistema de Controle de Cr&eaute;dito';
});

