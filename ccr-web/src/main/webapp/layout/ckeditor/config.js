/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	config.toolbar = [
		{ name: 'document', items: [ 'Source', '-', 'NewPage' ] },
		{ name: 'clipboard', items: [ 'Cut', 'Copy', 'Paste', 'PasteText', '-', 'Undo', 'Redo' ] },
		{ name: 'editing', items: [ 'SelectAll', '-', 'Scayt' ] },
		{ name: 'forms', items: [ 'Form' ] },
		{ name: 'basicstyles', items: [ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'CopyFormatting' ] },
		{ name: 'paragraph', items: [ 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock',] },
		{ name: 'insert', items: [ 'Image', 'Table' ] },
		'/',
		{ name: 'styles', items: [ 'Styles', 'Format', 'Font', 'FontSize' ] },
		{ name: 'tools', items: [ 'Maximize' ] }
	];
	
	config.forcePasteAsPlainText = false;
	config.pasteFromWordRemoveFontStyles = false;
	config.pasteFromWordRemoveStyles = false;
	config.allowedContent = true;
	config.extraAllowedContent = 'p(mso*,Normal)';
	config.pasteFilter = null;
};
