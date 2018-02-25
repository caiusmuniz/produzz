var contato = JSON.parse($.cookie('contato'));
if (jQuery.type(contato) != 'undefined') {
	var conta = contato.conta;
	$('#idConta').val(conta.idConta);
}

findAll();

function findAll() {
	console.log('findAll Templates');
	var urlWs = 'ws/templates';
	$.getJSON(urlWs, function(data) {
		var list = data == null ? [] :(data instanceof Array ? data : [data]);
		$('#dataTable tbody').remove();
		$.each(list,function(index, template) {
			console.log(template.deTemplate);
			$('#dataTable').append('<tbody><tr><td class="col-md-10">'+
			'<div><div id="titulo" class="font-16 block">'+template.deTemplate+'</div>' +
			'<div id="mensagem">'+template.deMensagem+'</div></div></td>' +
			'<td><div class="btn-group"><button class="btn btn-default">Editar</button>' +
			'<button class="btn btn-default dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>' +
			'<ul class="dropdown-menu"><li><a class="template-link" href="remove/'+template.idTemplate+'">Remover</a></li>' +
			'<li><a class="template-link" href="copia/'+template.idTemplate+'">Fazer uma cópia</a></li></ul></div></td></tr></tbody>')
		});
	});
}

function create() {
	console.log('adiciona um novo template');
	var urlWs = 'ws/templates';
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: urlWs,
		dataType: "json",
		data: function() {
			var templateId = $('#idTemplate').val();
			return JSON.stringify({
				"idTemplate": templateId == "" ? null : templateId,
				"deTemplate": $('#deTemplate').val(),
				"deMensagem": $('#deMensagem').val(),
				"idConta": $('#idConta').val()		
			});
		},
		success: function(data, textStatus, xhr) {
			$('#idTemplate').val(data.idTemplate);
			$(location).attr('href','#/templates')
		}
	});
}

$('#btnSalvar').click(function() {
	create();
})

//function renderList(data) {
//	var list = data == null ? [] :(data instanceof Array ? data : [data]);
//	$('#dataTable tbody').remove();
//	$.each(list,function(index, template) {
//		console.log(template.deTemplate);
//		$('#dataTable').append('<tbody><tr><td class="col-md-10">'+
//		'<div><div id="titulo" class="font-16 block">'+template.deTemplate+'</div>' +
//		'<div id="mensagem">'+template.deMensagem+'</div></div></td>' +
//		'<td><div class="btn-group"><button class="btn btn-default">Editar</button>' +
//		'<button class="btn btn-default dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>' +
//		'<ul class="dropdown-menu"><li><a href="'+template.idTemplate+'">Remover</a></li>' +
//		'<li><a href="'+template.idTemplate+'">Fazer uma cópia</a></li></ul></div></td></tr></tbody>')
//	});
//}

//function formToJSON() {
//	var templateId = $('#idTemplate').val();
//	return JSON.stringify({
//		"idTemplate": templateId == "" ? null : templateId,
//		"deTemplate": $('#deTemplate').val(),
//		"deMensagem": $('#deMensagem').val(),
//		"idConta": $('#idConta').val()		
//	});
//}
