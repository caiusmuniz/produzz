var contato = JSON.parse($.cookie('contato')),
	conta = contato.conta;

var rootURL = 'ws/campanhas';
var text_max = 160;

if (contato != undefined) {
	$('#idConta').val(conta.idConta);
	$('#id').val(contato.id);
	campanhaByContaCanal();
}


function campanhaByContaCanal() {
	console.log('campanhaByContaCanal GET');
	$.ajax({
		url: rootURL + '/' + conta.idConta + '/1',
		type: 'GET',
		dataType: "json",
		success: function(response) {
			var newCampanhas = response;
			if (newCampanhas.temErro == true) {
				var erro = '<span>' + newCampanhas.msgsErro[0] + '</span>';
				$('.alert').html(erro).addClass('alert-danger').show();
			}

			if ((newCampanhas.temErro == undefined) || (newCampanhas.temErro == false)) {
				
				for(var i=0; i<newCampanhas.length; i++) {
				    //console.log(newCampanhas[i]);
					var campanha = newCampanhas[i];
					var bCheck = (campanha.icStatus == "I");
					var id = campanha.idCampanha;
					var msg = campanha.deMensagem;
					
					//Pedido Realizado
					if (campanha.idTipoCampanha == 4)
					{
						$('#idPedidoRealizado').val(id);
						$('#chk_pedido_realizado').attr('checked', bCheck);
						$('#msgtext_pedido_realizado').val(msg);
						atualizarContador('#msgtext_pedido_realizado', '#count_message');
					}//Cancelado
					else if (campanha.idTipoCampanha == 6)
					{
						$('#msgtext_cancelado').val(msg);
						$('#chk_cancelado').attr('checked', bCheck);
						$('#idCancelado').val(id);
						atualizarContador('#msgtext_cancelado', '#count_message_cancelado');
					}//Pagamento Aprovado
					else if (campanha.idTipoCampanha == 7)
					{
						$('#msgtext_pg_aprovado').val(msg);
						$('#chk_pg_aprovado').attr('checked', bCheck);
						$('#idPgAprovado').val(id);
						atualizarContador('#msgtext_pg_aprovado', '#count_message_pg_aprovado');
					}//Separação
					else if (campanha.idTipoCampanha == 8)
					{
						$('#msgtext_separacao').val(msg);
						$('#chk_separacao').attr('checked', bCheck);
						$('#idSeparacao').val(id);
						atualizarContador('#msgtext_separacao', '#count_message_separacao');
					}//Transportadora
					else if (campanha.idTipoCampanha == 9)
					{
						$('#msgtext_transportadora').val(msg);
						$('#chk_transportadora').attr('checked', bCheck);
						$('#idTransportadora').val(id);
						atualizarContador('#msgtext_transportadora', '#count_message_transportadora');
					};
				};
				
				//atualizo o check da primeira campanha
				atualizaCampanhaPedidoIniciado();
			}			
			
		},
		error: function()
		{
			var erro = '<span>' + newCampanhas.msgsErro[0] + '</span>';
			$('.alert').html(erro).addClass('alert-danger').show();
		}	
	});
}

function atualizarContador(text_msg, text_counter) {
	  var text_length = $(text_msg).val().length;
	  var text_remaining = text_max - text_length;
	  $(text_counter).html(text_remaining + ' restantes');
}			

function salvarCampanhas() {
	//Atualiza os dados antes de mandar por tipo
	var json = "";
	var checkResult;

	//Pedido Realizado
	checkResult = $('#chk_pedido_realizado').is(':checked');
	json = formToJSON($('#idPedidoRealizado').val(), 
					  $('#msgtext_pedido_realizado').val(), 
					  checkResult == true ? "I" : "S");
	saveOrUpdate(json);

	//Pedido Cancelado
	checkResult = $('#chk_cancelado').is(':checked');
	json = formToJSON($('#idCancelado').val(), 
					  $('#msgtext_cancelado').val(), 
					  checkResult == true ? "I" : "S");
	saveOrUpdate(json);
	
	//Pagamento Aprovado
	checkResult = $('#chk_pg_aprovado').is(':checked');
	json = formToJSON($('#idPgAprovado').val(), 
					  $('#msgtext_pg_aprovado').val(), 
					  checkResult == true ? "I" : "S");
	saveOrUpdate(json);

	//Separacao
	checkResult = $('#chk_separacao').is(':checked');
	json = formToJSON($('#idSeparacao').val(), 
					  $('#msgtext_separacao').val(), 
					  checkResult == true ? "I" : "S");
	saveOrUpdate(json);
	
	//Entregue Transportadora
	checkResult = $('#chk_transportadora').is(':checked');
	json = formToJSON($('#idTransportadora').val(), 
					  $('#msgtext_transportadora').val(), 
					  checkResult == true ? "I" : "S");
	saveOrUpdate(json);

}

function validaDados() {
	var valido = true;
	
	valido = validaCampanha('Campanha: Pedido realizado', $('#chk_pedido_realizado').is(':checked'), $('#msgtext_pedido_realizado').val());
	if (valido == true)
		valido = validaCampanha('Campanha: Pedido cancelado', $('#chk_cancelado').is(':checked'), $('#msgtext_cancelado').val());

	if (valido == true)
		valido = validaCampanha('Campanha: Pagamento aprovado', $('#chk_pg_aprovado').is(':checked'), $('#msgtext_pg_aprovado').val());

	if (valido == true)
		valido = validaCampanha('Campanha: Pedido em separação', $('#chk_separacao').is(':checked'), $('#msgtext_separacao').val());

	if (valido == true)
		valido = validaCampanha('Campanha: Entregue na transportadora', $('#chk_transportadora').is(':checked'), $('#msgtext_transportadora').val());

	
	return valido;
}

function validaCampanha(descricao, marcado, msg) {
	var valido = true;
	
	if (marcado == true)
	{
		valido = (msg != '');
	
		if (valido == false)
			$.gritter.add({
				title: '<i class="fa fa-check-circle"></i> ' + descricao,
				text: 'Mensagem não pode ficar vazia',
				sticky: false,
				time: '',
				class_name: 'gritter-danger'
			});
		else if (msg.length > 160)
		{
			valido = false;
			$.gritter.add({
				title: '<i class="fa fa-check-circle"></i> ' + descricao,
				text: 'Mensagem não pode ultrapassar 160 caracteres',
				sticky: false,
				time: '',
				class_name: 'gritter-danger'
			});
		}
		

	}
	
	return valido;
}

$('#btn_Salvar').click(function(e) {
	e.preventDefault();
	
	if (validaDados() == true)
	{
		salvarCampanhas();
		
		$.gritter.add({
			title: '<i class="fa fa-check-circle"></i> Campanha atualizada com sucesso',
			text: '',
			sticky: false,
			time: '',
			class_name: 'gritter-success'
		});
	}
	
});

$("#chk_cancelado").change(function() {
   	atualizaCampanhaPedidoIniciado();
});

$("#chk_pg_aprovado").change(function() {
   	atualizaCampanhaPedidoIniciado();
});

$("#chk_separacao").change(function() {
   	atualizaCampanhaPedidoIniciado();
});

$("#chk_transportadora").change(function() {
   	atualizaCampanhaPedidoIniciado();
});

function atualizaCampanhaPedidoIniciado() {
	var bEnable = $('#chk_cancelado').is(':checked') ||
				  $('#chk_pg_aprovado').is(':checked') ||
				  $('#chk_separacao').is(':checked') ||
				  $('#chk_transportadora').is(':checked');
	
	if (bEnable == false)
		$('#chk_pedido_realizado').removeAttr('disabled');
	else
		{
			$('#chk_pedido_realizado').prop('checked', true);
			$('#chk_pedido_realizado').attr("disabled", "disabled");
		}
}


function formToJSON(id, msg, status) {
	
	return JSON.stringify({
		"idCampanha": id,
		"deCampanha": "",
		"qtEntregue": 0,
		"qtClicada": 0,
		"qtVisualizada": 0,
		"deMensagem": msg,
		"deUrl": "",
		"tsCriacao": "",
		"icStatus": status,
		"idTipoCampanha": 0
	});
}

function saveOrUpdate(jsonCampanha) {
	console.log('Atualizar campanha pedido!');
	$.ajax({
		type: 'POST',
		url: rootURL,
		contentType: 'application/json',
		dataType: "json",
		data: jsonCampanha,
		success: function(response)
		{
		},
		error: function()
		{
			var erro = '<span>Erro ao atualizar campanhas</span>';
			$('.alert').html(erro).addClass('alert-danger').show();
		}
	})
}

var validChars = /[^A-Za-z0-9 :?"'.,<>@_!#$%&*()-/;+=\s\r\n]/g;

$('#msgtext_pedido_realizado').bind('keyup blur', function() {
    $(this).val($(this).val().replace(validChars, ''))
});

$('#msgtext_cancelado').bind('keyup blur', function() {
    $(this).val($(this).val().replace(validChars, ''))
});

$('#msgtext_pg_aprovado').bind('keyup blur', function() {
    $(this).val($(this).val().replace(validChars, ''))
});

$('#msgtext_separacao').bind('keyup blur', function() {
    $(this).val($(this).val().replace(validChars, ''))
});

$('#msgtext_transportadora').bind('keyup blur', function() {
    $(this).val($(this).val().replace(validChars, ''))
});


