/**/
$(function(){
	renderDetail();
});

function renderDetail() {
	var contato = JSON.parse($.cookie('contato'));
	var conta = contato.conta;
	if (conta != undefined) {
		$('#empresa').val(conta.noEmpresa);
		$('#telefone').val(conta.nuTelefone);
		$('#endereco').val(conta.noLogradouro);
		$('#cep').val(conta.nuCep);
		$('#numero').val(conta.nuLogradouro);
		$('#complemento').val(conta.deComplemento);
		$('#cidade').val(conta.noMunicipio);
		$('#estado').val(conta.noEstado);
		$('#pais').val(conta.noPais);
		$('#idContato').val(conta.idContato);
		$('#idConta').val(conta.idConta);
		$('#dtCriacao').val(conta.dtCriacao);
		$('#qtSaldoDisponivel').val(conta.qtSaldoDisponivel);
	}
}

//var rootURL = 'ws/contas';

function saveOrUpdate() {
	console.log('adiciona ou atualiza nova conta!');
	$.ajax({
		type: 'POST',
		url: 'ws/contas',
		contentType: 'application/json',
		dataType: "json",
		data: function() {
			var idConta = $('#idConta').val();
			var nuCep = $('#cep').val();
			var idContato = $('#idContato').val();
			var nuLogradouro = $('#numero').val();
			
			return JSON.stringify({
				"idConta": idConta == "" ? null : idConta,
				"noEmpresa": $('#empresa').val(),
				"nuTelefone": $('#telefone').val(),
				"nuCep": nuCep == "" ? null : nuCep,
				"noLogradouro": $('#endereco').val(),
				"nuLogradouro": nuLogradouro == "" ? null : nuLogradouro,
				"deComplemento": $('#complemento').val(),
				"noMunicipio": $('#cidade').val(),
				"noEstado": $('#estado').val(),
				"noPais": $('#pais').val(),
				"idContato": idContato == "" ? null : idContato,
				"dtCriacao": $('#dtCriacao').val(),
				"qtSaldoDisponivel": $('#qtSaldoDisponivel').val()
			});
		},
		success: function(data) {
			console.log('CONTA: '+data);
			if(data.temErro == false) {
				var contato = JSON.parse($.cookie('contato'));
				var conta = data;
				contato.conta = conta;
				$.cookie.json = true;
				$.cookie('contato', contato);
				console.log('ContatoConta: '+$.cookie('contato'));
				
				$.gritter.add({
					title: '<i class="fa fa-check-circle"></i> Conta atualizada com sucesso',
					text: '',
					sticky: false,
					time: '',
					class_name: 'gritter-success'
				});
			} else {
				var erro = '<small>'+data.msgsErro[0]+'</small>';
				$('.alert').html(erro).addClass('alert-danger').show();
			}
		},
		error: function(xhr) {
			console.log(xhr)
			var erro = '<small>Erro interno no servidor.</small>';
			$('.alert').html(erro).addClass('alert-danger').show();
		}
	})
}

$('#btnSave').click(function(e) {
	
	e.preventDefault();
	if ( $('#form_conta').parsley('isValid')){
		saveOrUpdate();
	}
	else
	{
		$.gritter.add({
			title: '<i class="fa fa-check-circle"></i> Erro ao salvar conta',
			text: 'Todos os campos devem ser preenchidos: Telefone, CEP, Endereço, Número, Complemento, Município, Estado e País.',
			sticky: false,
			time: '',
			class_name: 'gritter-danger'
		});
	}
	
})

//function formToJSON() {
//	var idConta = $('#idConta').val();
//	var nuCep = $('#cep').val();
//	var idContato = $('#idContato').val();
//	var nuLogradouro = $('#numero').val();
//	
//	return JSON.stringify({
//		"idConta": idConta == "" ? null : idConta,
//		"noEmpresa": $('#empresa').val(),
//		"nuTelefone": $('#telefone').val(),
//		"nuCep": nuCep == "" ? null : nuCep,
//		"noLogradouro": $('#endereco').val(),
//		"nuLogradouro": nuLogradouro == "" ? null : nuLogradouro,
//		"deComplemento": $('#complemento').val(),
//		"noMunicipio": $('#cidade').val(),
//		"noEstado": $('#estado').val(),
//		"noPais": $('#pais').val(),
//		"idContato": idContato == "" ? null : idContato,
//		"dtCriacao": $('#dtCriacao').val(),
//		"qtSaldoDisponivel": $('#qtSaldoDisponivel').val()
//	});
//}
//
//function sucesso(data) {
//	console.log('CONTA: '+data);
//	if(data.temErro == false) {
//		var contato = JSON.parse($.cookie('contato'));
//		var conta = data;
//		contato.conta = conta;
//		$.cookie.json = true;
//		$.cookie('contato', contato);
//		console.log('ContatoConta: '+$.cookie('contato'));
//		
//		$.gritter.add({
//			title: '<i class="fa fa-check-circle"></i> Conta atualizada com sucesso',
//			text: '',
//			sticky: false,
//			time: '',
//			class_name: 'gritter-success'
//		});
//	} else {
//		var erro = '<small>'+data.msgsErro[0]+'</small>';
//		$('.alert').html(erro).addClass('alert-danger').show();
//	}
//}
//
//function erro(xhr) {
//	console.log(xhr)
//	var erro = '<small>Erro interno no servidor.</small>';
//	$('.alert').html(erro).addClass('alert-danger').show();
//}