/*
 * 
 */
$(function	()	{
	$('#dataTable').dataTable( {
		"bJQueryUI": true,
		"destroy": true,
		"sPaginationType": "full_numbers",
		
		language: {
			"sEmptyTable": "Nenhum registro encontrado",
			"sInfo": "Mostrando de _START_ - _END_ de _TOTAL_ registros",
			"sInfoEmpty": "Mostrando 0 - 0 de 0 registros",
			"sInfoFiltered": "(Filtrados de _MAX_ registros)",
			"sInfoPostFix": "",
			"sInfoThousands": ".",
			"sLengthMenu": "Mostrar _MENU_ resultados por página",
			"sLoadingRecords": "Carregando...",
			"sProcessing": "Processando...",
			"sZeroRecords": "Nenhum registro encontrado",
			"sSearch": "",
			"oPaginate": {
				"sNext": "Próximo",
				"sPrevious": "Anterior",
				"sFirst": "",
				"sLast": ""
			},
			"oAria": {
				"sSortAscending": ": Ordenar colunas de forma ascendente",
				"sSortDescending": ": Ordenar colunas de forma descendente"
			}		
		}
		
	});
	
	$('#chk-all').click(function()	{
		if($(this).is(':checked'))	{
			$('#responsiveTable').find('.chk-row').each(function()	{
				$(this).prop('checked', true);
				$(this).parent().parent().parent().addClass('selected');
			});
		}
		else	{
			$('#responsiveTable').find('.chk-row').each(function()	{
				$(this).prop('checked' , false);
				$(this).parent().parent().parent().removeClass('selected');
			});
		}
	});
	
	var text_max = 160;
	$('#count_message').html(text_max + ' restantes');
	
	$('#msgtext').keyup(function() {
	  var text_length = $('#msgtext').val().length;
	  var text_remaining = text_max - text_length;
	  $('#count_message').html(text_remaining + ' restantes');
	});	

});

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
