var rootURL = 'ws/contatos';

$(function() {
	$('#senha').on('change',function() { $(this).val(md5($(this).val())); });
	$('#login-form').validate({
		rules: {
			login: {
				required: true,
				email: true
			},
			senha: {
				required: true,
				minlength: 6
			}
		},
		messages: {
			login: {
				required: "Campo obrigatorio",
				email: "Digite um email valido."
			},
			senha: {
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
			var urlWs = rootURL + '/' + $('#login').val() + '/' + $('#senha').val();		
			$.ajax({
				type: 'GET',
				url: urlWs,
				dataType: "json",
				success: function(data) {
					if(data.temErro == true) {
						var erro = '<small>'+data.msgsErro[0]+'</small>';
						$('.alert').html(erro).addClass('alert-danger').show();
					}
					if(data.temErro == false) {
						var n = $( "input:checked" ).length;
						$.cookie.json = true;
						if(n === 1) {
							console.debug('checked...')
							$.cookie('contato', data.contato, { expires : 365 });
						} else {
							console.debug('unchecked...')
							$.cookie('contato', data.contato);
						}
						$(location).attr('href', '/admin');
					}
				}
			});
			return false;
		}
	});
});
