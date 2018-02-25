var contato = JSON.parse($.cookie('contato')),
	conta = contato.conta;

var rootURL = 'ws/contacanal';

if (contato != undefined) {
	$('#idConta').val(conta.idConta);
	$('#id').val(contato.id);
	contaCanalByConta();
}

function contaCanalByConta() {
	console.log('contaCanal GET');
	$.ajax({
		url: rootURL + '/' + conta.idConta,
		type: 'GET',
		dataType: "json",
		success: function(data, textStatus, xhr) {
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

$('#btn_vtex_teste').click(function(e) {
	e.preventDefault();
	if ( $('#form_contacanal').parsley('isValid')){
		saveOrUpdate();
	}
	else
	{
		$.gritter.add({
			title: '<i class="fa fa-check-circle"></i> Campos não pode ficar em branco',
			text: 'URL do site, Web store, App Key, e App Token',
			sticky: false,
			time: '',
			class_name: 'gritter-danger'
		});
	}
	
})

function saveOrUpdate() {
	console.log('adiciona ou atualiza conta canal!');
	$.ajax({
		type: 'POST',
		url: rootURL,
		contentType: 'application/json',
		dataType: "json",
		data: formToJSON(),
		success: function(response)
		{
			//Teste o método com a Vtex
			
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

function formToJSON() {
	var idConta = $('#idConta').val();
	var idContato = $('#idContato').val();
	
	return JSON.stringify({
		"idConta": idConta == "" ? null : idConta,
		"idCanal": "1",
		"deURLLoja": $('#vtexUrl').val(),
		"deUsuario": $('#vtexUsuario').val(),
		"deSenha": $('#vtexSenha').val(),
		"deToken": $('#vtexToken').val()
	});
}



