var contato = JSON.parse($.cookie('contato')),
	conta = contato.conta;

var rootURL = 'ws/campanhas';

if (contato != undefined) {
	$('#id').empty().html(contato.id);
	$('#idConta').empty().html(conta.idConta);
	campanhaByContaCanal();
}


function campanhaByContaCanal() {
	console.log('campanhaByContaCanalLista GET');
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

			var campanhasAtivas = '';
			if ((newCampanhas.temErro == undefined) || (newCampanhas.temErro == false)) {
				
				for(var i=0; i<newCampanhas.length; i++) {
				    //console.log(newCampanhas[i]);
					var campanha = newCampanhas[i];
					var bCheck = (campanha.icStatus == "I");
					
					//Pedido Realizado
					if (campanha.idTipoCampanha == 4)
					{
						if (bCheck)
							campanhasAtivas = campanhasAtivas + '- ATUALIZAÇÃO DE PEDIDO VTEX - REALIZADO <br>'
					}//Cancelado
					else if (campanha.idTipoCampanha == 6)
					{
						if (bCheck)
							campanhasAtivas = campanhasAtivas + '- ATUALIZAÇÃO DE PEDIDO VTEX - CANCELADO <br>'
					}//Pagamento Aprovado
					else if (campanha.idTipoCampanha == 7)
					{
						if (bCheck)
							campanhasAtivas = campanhasAtivas + '- ATUALIZAÇÃO DE PEDIDO VTEX - PAGAMENTO APROVADO <br>'
					}//Separação
					else if (campanha.idTipoCampanha == 8)
					{
						if (bCheck)
							campanhasAtivas = campanhasAtivas + '- ATUALIZAÇÃO DE PEDIDO VTEX - EM SEPARAÇÃO <br>'
					}//Transportadora
					else if (campanha.idTipoCampanha == 9)
					{
						if (bCheck)
							campanhasAtivas = campanhasAtivas + '- ATUALIZAÇÃO DE PEDIDO VTEX - ENTREGUE À TRANSPORTADORA <br>'
					};
				};
				
				if (campanhasAtivas == '')
					campanhasAtivas = 'Não existe campanha ativa!';
			}
			
			var Integracaoativa = "";
			
			var htmlLista = "<thead>"+
							"		<tr>"+
							"		<th>Plataforma</th>"+
							"		<th>Campanhas ativas</th>"+
							"		<th></th>"+
							"	</tr>"+
							"</thead>"+
							"<tbody>"+
							"<tr>" + 
							"<td class='col-md-4'>" + 
							"	<div>" +
							"		<div class='font-16 block'>Vtex"+Integracaoativa+"</div>" +
							"	</div>" +
							"</td>" +
							"<td>" +
							campanhasAtivas +
							"</td>" +
							"<td>" +
							"	<div class='btn-group'>" +
							"		<button class='btn btn-default'><a href='campanhaatualizacaopedidoDetalhe.html?idConta="+ conta.idConta +"'>Editar</a></button>" +
							"	</div>" +
							"</td>" +
							"</tr>"+
							"</tbody>";
			
			$('#listCampanhaCanal').empty().html(htmlLista);
			
		},
		error: function()
		{
			var erro = '<span>' + newCampanhas.msgsErro[0] + '</span>';
			$('.alert').html(erro).addClass('alert-danger').show();
		}	
	});
}




