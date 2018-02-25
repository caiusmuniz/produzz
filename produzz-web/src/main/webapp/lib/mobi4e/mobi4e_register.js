var rootURL = 'ws/contatos';

$(function() {
	$('#senha').on('blur',function() {
		var that = $(this), value = md5(that.val());
		that.val(value);
	});

	$('#confirma').on('blur',function() {
		var that = $(this), value = md5(that.val());
		that.val(value);
	});

	$('#register-form').validate({
		rules: {
			nome: {
				required: true,
				minlength: 3,
				maxlength: 64
			},
			sobrenome: {
				required: true,
				minlength: 3,
				maxlength: 64
			},
			login: {
				required: true,
				email: true
			},
			senha: {
				required: true,
				minlength: 6
			},
			confirma: {
				required: true,
				minlength: 6
			},
			cupom: {
				minlength: 6,
				maxlength: 64
			}
		},
		messages: {
			nome: {
				required: "Campo obrigatorio",
				minlength: "Exige no mínimo 3 caracteres.",
				maxlength: "Tamanho máximo 64 caracteres."
			},
			sobrenome: {
				required: "Campo obrigatorio",
				minlength: "Exige no mínimo 3 caracteres.",
				maxlength: "Tamanho máximo 64 caracteres."
			},
			login: {
				required: "Campo obrigatorio",
				email: "Digite um email valido."
			},
			senha: {
				required: "Campo obrigatorio",
				minlength: "Exige no mínimo 6 caracteres."
			},
			confirma: {
				required: "Campo obrigatorio",
				minlength: "Exige no mínimo 6 caracteres."
			},
			cupom: {
				minlength: "Exige no mínimo 6 caracteres.",
				maxlength: "Tamanho máximo 64 caracteres."
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
			console.log('adiciona um novo contato!');
			$.ajax({
				type: 'POST',
				url: rootURL,
				contentType: 'application/json',
				dataType: "json",
				data: formToJSON(),
				success: function(data) {
					console.log(data);
					if (data.temErro == false) {
						$.cookie.json = true;
						$.cookie('contato', data);
						$(location).attr('href', 'login.html');
					} else {
						var erro = '<small>' + data.msgsErro[0] + '</small>';
						$('.alert').html(erro).addClass('alert-danger').show();
					}
				},
				error: function(xhr,status,thr) {
					var erro = '<small>' + thr + '</small>';
					$('.alert').html(thr).addClass('alert-danger').show();
				}
			});
			return false;
		}
	});
});

function formToJSON() {
	return JSON.stringify({
		"id": null,
		"nome": $('#nome').val(),
		"sobrenome": $('#sobrenome').val(),
		"login": $('#login').val(),
		"senha": $('#senha').val(),
		"cupom": $('#cupom').val(),
		"indicador": "A",
		"criacao":  new Date(),
		"atualizacao": null,
		"conta": null
	});
}
