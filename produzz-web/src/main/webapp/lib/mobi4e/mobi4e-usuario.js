/*
 * 
 */
$(function(){
	var contato = JSON.parse($.cookie('contato'));
	if(jQuery.type(contato) != 'undefined') {
		$('#idConta').empty();
		$('#idContato').val(contato.id);
		$('#dtCriacao').val(contato.criacao);
		$('#indicador').val(contato.indicador);
		$('#nome2').val(contato.nome);
		$('#sobrenome').val(contato.sobrenome);
		$('#loginMobi4e').val(contato.login);
		$('#senhaMobi4e').empty();
	}
});

function carregaUsuario() {
	var contato = JSON.parse($.cookie('contato'));
	if(jQuery.type(contato) != 'undefined') {
		var rootURL = 'ws/contatos/' + contato.id;
		$.ajax({
			url: rootURL,
			type: 'GET',
			dataType: 'json',
			success: function(data) {
				if(data.temErro == true) {
					console.log('erro...carregaUsuario()')
				}
				if(data.temErro == false) {
					var contato = data.contato;
					$('#idConta').val();
					$('#idContato').val(contato.id);
					$('#dtCriacao').val(contato.criacao);
					$('#indicador').val(contato.indicador);
					$('#nome2').val(contato.nome);
					$('#sobrenome').val(contato.sobrenome);
					$('#loginMobi4e').val(contato.login);
					$('#senhaMobi4e').empty();
				}
			}
		});
	}
}

function carregaPaginaUsuario() {
	if(jQuery.type($('#contato-form')) != 'undefined')	{
		$('#senhaMobi4e').on('change',function() { $(this).val(md5($(this).val())); });
		$('#contato-form').validate({
			rules: {
				nome2: {
					required: true
				},
				sobrenome: {
					required: true
				},
				loginMobi4e: {
					required: true,
					email: true
				},
				senhaMobi4e: {
					required: true,
					minlength: 6
				}
			},
			messages: {
				nome2: {
					required: "Campo obrigatorio",
				},
				sobrenome: {
					required: "Campo obrigatorio",
				},
				loginMobi4e: {
					required: "Campo obrigatorio",
					email: "Digite um email valido."
				},
				senhaMobi4e: {
					required: "Campo obrigatorio",
					minlength: "Exige no mínimo 6 caracteres."
				}
			},
			highlight: function(element) {
				$(element).closest('.form-group').removeClass('has-success').addClass('has-error');
			},
			unhighlight: function(element) {
				$(element).closest('.form-group').removeClass('has-error').addClass('has-success');
			},
			errorElement: 'small',
			errorClass: 'help-block',
			errorPlacement: function(error, element) {
				if (element.parent('.form-control').length) {
					error.insertAfter(element.parent());
				} else {
					error.insertAfter(element);
				}
			},
			submitHandler: function(form) {
				var rootURL = 'ws/contatos';		
				$.ajax({				
					url: 'ws/contatos',
					type: 'POST',
					contentType: 'application/json',
					dataType: "json",
					data: JSON.stringify({
						"id": $("#idContato").val(),
						"nome": $("#nome2").val(),
						"sobrenome": $("#sobrenome").val(),
						"login": $("#loginMobi4e").val(),
						"senha": $("#senhaMobi4e").val(),
						"indicador": $("#indicador").val(),
						"criacao":  $("#dtCriacao").val(),
						"atualizacao": null,
						"conta": null
					}),
					success: function(data) {
						if(data.temErro == true) {
							var erro = '<small>'+data.msgsErro[0]+'</small>';
							$('.alert').html(erro).addClass('alert-danger').show();
						}
						if(data.temErro == false) {
						}
					}
				});
				return false;
			}
		});		
	}
}

function formToJSON2() {
	return JSON.stringify({
		"id": $('#idContato').val(),
		"nome": $('#nome2').val(),
		"sobrenome": $('#sobrenome').val(),
		"login": $('#loginMobi4e').val(),
		"senha": $('#senhaMobi4e').val(),
		"indicador": $('#indicador').val(),
		"criacao":  $('#dtCriacao').val(),
		"atualizacao": null,
		"conta": null
	});
}

