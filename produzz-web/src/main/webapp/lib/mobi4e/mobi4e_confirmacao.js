$(document).ready(function() {
	var contato = JSON.parse($.cookie('contato'));
	$('#ddi-p').val(contato.celular.ddi);
	$('#ddd-p').val(contato.celular.ddd);
	$('#telefone-p').val(contato.celular.telefone);
	$('#login-p').val(contato.login);
	$('#pass1').on('change',function() { $(this).val(md5($(this).val())); });
	$('#pass2').on('change',function() { $(this).val(md5($(this).val())); });
	$('#ns-form').validate({
		rules: {
			pass1: { 
				required: true,
				minlength: 6 
			},
			pass2: { 
				required: true,
				minlength: 6, 
				equalTo: '#pass1'
			}
		},
		messages: {
			pass1: {
				required: "Campo obrigatorio",
 				minlength: "Exige no mínimo 6 caracteres."
			},
			pass2: {
				required: "Campo obrigatorio",
 				minlength: "Exige no mínimo 6 caracteres.",
				equalTo: "A confirmação deve ser identica a nova senha"
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
			$.ajax({
				url: 'ws/contatos',
				type: 'POST',
				contentType: 'application/json',
				dataType: 'json',
				data: formToJSON(),
				success: function(data) {
					if (data.temErro == true) {
						$('.alert').html('<span>'+data.msgsErro[0]+'</span>')
							.addClass('alert-danger').show();
					}
					if (data.temErro == false) {
						$('.alert').html("Nova senha atualizada com sucesso.")
							.addClass('alert-success').show();
						$('#ns-button').hide();
						$('#ns-link').show();
					}
				}
			});			
			return false;
		}	
	});	
});

function formToJSON() {
	var contato = JSON.parse($.cookie('contato'));
	console.log('contato: '+contato)
	return JSON.stringify({
		"id": contato.id,
		"nome": contato.nome,
		"sobrenome": contato.sobrenome,
		"login": contato.login,
		"senha": $('#pass1').val(),
		"indicador": contato.indicador,
		"criacao":  contato.criacao,
		"atualizacao": null,
		"conta": null
	});
}
