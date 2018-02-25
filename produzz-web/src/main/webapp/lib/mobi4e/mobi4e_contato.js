var contato = JSON.parse($.cookie('contato')),
	conta = contato.conta;

var rootURL = 'ws/contatos';

renderContato();
renderDetail();

function renderContato() {
	var $contato = JSON.parse($.cookie('contato'));
	console.log('ContatoConta: '+$contato);
	$('#nome').empty().html($contato.nome + ' ' + $contato.sobrenome);
	$('#nome1').empty().html($contato.nome + ' ' + $contato.sobrenome);
	$('#email').empty().html($contato.login);
	$('#idContato').val($contato.id);
}

function renderDetail() {
	console.log('contatoByID GET');
	$.ajax({
		url: rootURL + '/' + $('#idContato').val(),
		type: 'GET',
		dataType: "json",
		success: function(response) {
			var newContato = response;
			if (newContato.temErro == true) {
				var erro = '<span>' + newContato.msgsErro[0] + '</span>';
				$('.alert').html(erro).addClass('alert-danger').show();
			}

			if ((newContato.temErro == undefined) || (newContato.temErro == false)) {
				$('#nome2').val(newContato.nome);
				$('#sobrenome').val(newContato.sobrenome);
				$('#loginMobi4e').val(newContato.login);
				$('#senhaMobi4e').val('');
			}			
			
		},
		error: function()
		{
			var erro = '<span>' + $('#idContato').val() + '</span>';
			$('.alert').html(erro).addClass('alert-danger').show();
		}	
	});
}


$('#btn_Salvar').click(function(e) {
	e.preventDefault();
	if ( $('#form_contato').parsley('isValid')){
		saveOrUpdate();
	}
	else
	{
		$.gritter.add({
			title: '<i class="fa fa-check-circle"></i> Campos não preenchidos corretamente',
			text: 'Nome, Sobrenome, e Usuário (E-mail em branco ou inválido)',
			sticky: false,
			time: '',
			class_name: 'gritter-danger'
		});
	}
});	

function saveOrUpdate() {
	console.log('adiciona ou atualiza um novo contato!');
	$.ajax({
		type: 'POST',
		url: rootURL + '/' + $('#idContato').val(),
		contentType: 'application/json',
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, xhr) {
			/*
			console.log('CONTATO: '+data);
			var contato = JSON.parse($.cookie('contato'));
			var conta = data;
			contato.conta = conta;
			$.cookie.json = true;
			$.cookie('contato', contato);
			console.log('ContatoConta: '+$.cookie('contato'));
			*/
			$.gritter.add({
				title: '<i class="fa fa-check-circle"></i> Contato atualizado com sucesso',
				text: '',
				sticky: false,
				time: '',
				class_name: 'gritter-success'
			});
		}
	})
}

function formToJSON() {
	var idContato = $('#idContato').val();
	var senha = $('#senhaMobi4e').val();
	if (senha != '')
		senha = md5(senha);
	
	return JSON.stringify({
		"id": idContato,
		"nome": $('#nome2').val(),
		"sobrenome": $('#sobrenome').val(),
		"login": $('#loginMobi4e').val(),
		"senha": senha,
		"indicador": null,
		"criacao": null,
		"atualizacao": new Date(),
		"conta": conta
	});
}
