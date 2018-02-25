$(function() {
	$('#forgot-form').validate({
		rules: {
			login: {
				required: true,
				email: true
			},
			ddi: {
				required: true,
				maxlength: 3
			},
			ddd: {
				required: true,
				maxlength: 2
			},
			telefone: {
				required: true,
				maxlength: 9
			}
		},
		messages: {
			login: {
				required: "Campo obrigatorio",
				email: "Digite um email valido."
			},
			ddi: {
				required: "Campo obrigatorio", 
				maxlength: "O campo DDI deve ter conter o maximo de {0} caracters"
			},
			ddd: {
				required: "Campo obrigatorio", 
				minlength: "O campo DDD deve ter conter o minimo de {0} caracters",
				maxlength: "O campo DDD deve ter conter o maximo de {0} caracters"
			},
			telefone: {
				required: "Campo obrigatorio", 
				maxlength: "O campo Telefone deve ter {0} caracters"
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
			var urlWs = 'ws/contatos/'+ $('#login').val() + '/' + $('#ddi').val() +'/' + $('#ddd').val() + '/' + $('#telefone').val();
			$.ajax({
				url: url,
				type: 'GET',
				dataType: 'json',
				success: function(data) {
					if (data.temErro == true) {
						$('.alert').html('<span>'+data.msgsErro[0]+'</span>')
							.addClass('alert-danger').show();
					}
					if (data.temErro == false) {
						$.cookie.json = true;
						$.cookie('contato',data.contato);
						$(location).attr('href', 'confirmacao.html');
					}
				}
			});
			return false;
		}
	});
});

