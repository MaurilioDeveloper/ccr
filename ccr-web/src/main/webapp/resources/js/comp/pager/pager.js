var PagerCollection = Backbone.Collection.extend({

	initialize : function(perPage) {
		//_.bindAll(this, 'parse', 'url', 'pageInfo', 'nextPage', 'previousPage');
		//typeof (options) != 'undefined' || (options = {});
		this.page = 1;
		if (perPage == null) {
			perPage = 10;
		}
		this.perPage = perPage;
		//typeof (this.perPage) != 'undefined' || (this.perPage = 5);
	},

	fetch : function(options) {
		typeof (options) != 'undefined' || (options = {});
		var self = this;
		var success = options.success;
		options.success = function(response) {
			if (success) {
				success(self, response);
			}
		};

		return Backbone.Collection.prototype.fetch.call(this, options);
	},

	parse : function(response) {
		this.page = response.page;
		this.perPage = response.perPage;
		this.total = response.total;
		return response.list;
	},

	url : function() {
		return this.baseUrl + '/pager/' + this.page + '/' + this.perPage;
	},

	pageInfo : function() {
		var info = {
			total : this.total,
			page : this.page,
			perPage : this.perPage,
			pages : Math.ceil(this.total / this.perPage),
			prev : false,
			next : false
		};

		//1 2 3 4 5 6 7 8 9 10
		var min = info.page-5;
		if (min < 1) {
			min = 1;
		}
		var max = info.page+5;
		if (max > info.pages) {
			max = info.pages;
		}
		info.range = [];
		var i = min;
		while (i <= max) {
			info.range[i-min] = i;
			i++;
		}

		if (this.page > 1) {
			info.prev = this.page - 1;
		}

		if (this.page < info.pages) {
			info.next = this.page + 1;
		}

		return info;
	},

	nextPage : function() {
		if (!this.pageInfo().next) {
			return false;
		}

		this.page = this.page + 1;
	},

	previousPage : function() {
		if (!this.pageInfo().prev) {
			return false;
		}

		this.page = this.page - 1;
	}
});

var PagerView = Backbone.View.extend({

	tagName: 'div',
	
	className: 'pagination',
	
	initialize: function(collection) {
		this.collection = collection;
		//_.bindAll(this, 'previous', 'next', 'render');
		//this.collection.bind('refresh', this.render);
	},
	
	loadTemplate: function(path) {
		var template = null;
		
		$.ajax({url: path, async: false})
			.done(function(html) {
				template = html;
			})
			.fail(function(response) {
				console.log('Erro ao carregar o template: ' + response.responseText);
			});
		
		return template;
	},

	events: {
		'click a.first': 'first',
		'click a.previous': 'previous',
		'click a.next': 'next',
		'click a.page': 'page',
		'click a.last': 'last',
	},

	render: function() {
		if (this.template == null) {
			this.template = this.loadTemplate('../gec-web/bibliotecas/javascript/componentes/pager/pagerBootstrap.html');
		}
		this.$el.html(_.template(this.template, this.collection.pageInfo()));

		return this;
	},

	previous: function() {
		this.collection.previousPage();
		this.trigger('PAGER');

		return false;
	},

	next: function() {
		this.collection.nextPage();
		this.trigger('PAGER');

		return false;
	},

	first: function() {
		this.collection.page = 1;
		this.trigger('PAGER');
	},

	last: function() {
		this.collection.page = this.collection.pageInfo().pages;
		this.trigger('PAGER');
	},

	page: function(e) {
		this.collection.page = $(e.target).data('page');
		this.trigger('PAGER');
	}
});