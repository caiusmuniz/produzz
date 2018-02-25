var contato = JSON.parse($.cookie('contato')),
	conta = contato.conta;

var rootURL = 'ws/contacanal';

if (jQuery.type(contato) != 'undefined') {
	$('#idConta').val(conta.idConta);
	$('#id').val(contato.id);
	contaCanalByConta();
}

function contaCanalByConta() {
	console.log('contaCanal GET');
	$.ajax({
		url: 'ws/contacanal/' + conta.idConta,
		type: 'GET',
		dataType: "json",
		success: function(data, textStatus, xhr) {
			//MOBI4E
			$('#mobi4eUsuario').val(data[1].deUsuario);
			$('#mobi4eToken').val(data[1].deToken);

			if (data[1].deToken != "")
				$('#mobi4e_conectado').show();
			else
				$('#mobi4e_conectado').hide();
			
			//VTEX
			$('#vtexUrl').val(data[0].deURLLoja);
			$('#vtexUsuario').val(data[0].deUsuario);
			$('#vtexSenha').val(data[0].deSenha);
			$('#vtexToken').val(data[0].deToken);

			if (data[0].deToken != "")
				$('#vtex_conectado').show();
			else
				$('#vtex_conectado').hide();

		},
		error: function()
		{
			console.log('Ocorreu um erro no GET Conta Canal');
		}		
	});
}

$('#btn_gerar').click(function(e) {
	gerarToken();
})

function gerarToken() {
	console.log('contaCanal GET TST');
	$.ajax({
		url: 'ws/contacanal/' + conta.idConta + '/2/token',
		type: 'GET',
		dataType: "json",
		success: function(data, textStatus, xhr) {
			//MOBI4E
			$('#mobi4eToken').val(data.deToken);

		},
		error: function()
		{
			console.log('Ocorreu um erro no GET Conta Canal');
		}		
	});
}

$('#btn_mobi4e').click(function(e) {
	e.preventDefault();
	if ( $('#form_mobi4e').parsley('isValid')) {
		saveOrUpdate("2");
	}
	else
	{
		$.gritter.add({
			title: '<i class="fa fa-check-circle"></i> Campos não podem ficar em branco',
			text: 'App Key, e App Token',
			sticky: false,
			time: '',
			class_name: 'gritter-danger'
		});
	}
})

$('#btn_vtex').click(function(e) {
	e.preventDefault();
	if ( $('#form_vtex').parsley('isValid')) {
		saveOrUpdate("1");
	}
	else
	{
		$.gritter.add({
			title: '<i class="fa fa-check-circle"></i> Campos não podem ficar em branco',
			text: 'URL do site, Web store, App Key, e App Token',
			sticky: false,
			time: '',
			class_name: 'gritter-danger'
		});
	}
})

function saveOrUpdate(canal) {
	console.log('adiciona ou atualiza conta canal!');
	$.ajax({
		type: 'POST',
		url: 'ws/contacanal',
		contentType: 'application/json',
		dataType: "json",
		data: formToJSON(canal),
		success: function(response)
		{
			$.gritter.add({
				title: '<i class="fa fa-check-circle"></i> Configuração atualizada com sucesso',
				text: '',
				sticky: false,
				time: '',
				class_name: 'gritter-success'
			});
		},
		error: function()
		{
			$.gritter.add({
				title: '<i class="fa fa-check-circle"></i> Ocorreu um erro na configuração',
				text: 'A configuração não foi salva',
				sticky: false,
				time: '',
				class_name: 'gritter-danger'
			});
		}
	})
}

function formToJSON(canal) {
	var idConta = $('#idConta').val();
	var idContato = $('#idContato').val();

	return JSON.stringify({
		"idConta": idConta == "" ? null : idConta,
		"idCanal": canal,
		"deURLLoja": canal == 1 ? $('#vtexUrl').val() : $('#mobi4eUrl').val(),
		"deUsuario": canal == 1 ? $('#vtexUsuario').val() : $('#mobi4eUsuario').val(),
		"deSenha": canal == 1 ? $('#vtexSenha').val() : $('#mobi4eSenha').val(),
		"deToken": canal == 1 ? $('#vtexToken').val() : $('#mobi4eToken').val()
	});
}

$(document).ready(function(){
	$('#div_vtex_autenticacao_falhou').hide();
	$('#div_vtex_autenticacao_ok').hide();
});	
