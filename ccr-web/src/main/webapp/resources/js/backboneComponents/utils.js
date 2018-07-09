tpl = {

    // Hash of preloaded templates for the app
    templates:{},

    // Recursively pre-load all the templates for the app.
    // This implementation should be changed in a production environment. All the template files should be
    // concatenated in a single file.
    loadTemplates:function (names, callback) {

        var that = this;

        var loadTemplate = function (index) {
            var name = names[index];
            //console.log('Loading template: ' + name);
            $.get('../ccr-web/'+name + '.html', function (data) {
                that.templates[name] = data;
                index++;
                if (index < names.length) {
                    loadTemplate(index);
                } else {
                    callback();
                }
            });
        };

        loadTemplate(0);
    },
    
    loadTemplates2:function (names, callback) {

        var that = this;

        var loadTemplate = function (index) {
        	
            var name = names[index];
            
            var n = name.lastIndexOf('/');
        	var result = name.substring(n + 1);
            //console.log('Loading template: ' + result);
            //console.log('Loading template url: ' + name);
            $.get('../ccr-web/'+name + '.html', function (data) {
                that.templates[result] = data;
                index++;
                if (index < names.length) {
                    loadTemplate(index);
                } else {
                    callback();
                }
            });
        };

        loadTemplate(0);
    },

    // Get template by name from hash of preloaded templates
    get:function (name) {
        return this.templates[name];
    }

};