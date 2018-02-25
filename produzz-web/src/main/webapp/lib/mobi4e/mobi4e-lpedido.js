/*
 * 
 */
$(function(){
	$('#dataTable').dataTable( {
		"bJQueryUI": true,
		"destroy": true,
		"sPaginationType": "full_numbers",
		
		language: {
			"sEmptyTable": "Nenhum registro encontrado",
			"sInfo": "Mostrando de _START_ - _END_ de _TOTAL_ registros",
			"sInfoEmpty": "Mostrando 0 - 0 de 0 registros",
			"sInfoFiltered": "(Filtrados de _MAX_ registros)",
			"sInfoPostFix": "",
			"sInfoThousands": ".",
			"sLengthMenu": "Mostrar _MENU_ resultados por página",
			"sLoadingRecords": "Carregando...",
			"sProcessing": "Processando...",
			"sZeroRecords": "Nenhum registro encontrado",
			"sSearch": "",
			"oPaginate": {
				"sNext": "Próximo",
				"sPrevious": "Anterior",
				"sFirst": "",
				"sLast": ""
			},
			"oAria": {
				"sSortAscending": ": Ordenar colunas de forma ascendente",
				"sSortDescending": ": Ordenar colunas de forma descendente"
			}		
		}
		
	});
	
	$('#chk-all').click(function(){
		if($(this).is(':checked')){
			$('#responsiveTable').find('.chk-row').each(function()	{
				$(this).prop('checked', true);
				$(this).parent().parent().parent().addClass('selected');
			});
		}
		else	{
			$('#responsiveTable').find('.chk-row').each(function()	{
				$(this).prop('checked' , false);
				$(this).parent().parent().parent().removeClass('selected');
			});
		}
	});
	
});

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
			
			var dpedido = "dpedido";
			
			var htmlLista = "<tbody>"+
							"<tr>" + 
							"<td class='col-md-4'><div>" + 
							"	<div class='font-16 block'>Vtex"+Integracaoativa+"</div></div>" +
							"</td>" +
							"<td>" + campanhasAtivas + "</td>" +
							"<td>" +
							'	<a class="btn btn-default" href="javascript:vaiPraPagina()">Editar</a>' +
							//'<button href="vaiPraPagina(#/dpedido)" class="btn btn-default mobi4e-link">Editar22</button>'+
							"</td>" +
							"</tr>"+
							"</tbody>";
			
			//console.debug(htmlLista);
			//console.log('htmlLista:=>'+htmlLista);
			$('#dataTable tbody').remove();
			$('#dataTable').append(htmlLista);//.append(htmlLista);
	// listCampanhaCanal--empty().html()		
		},
		error: function()
		{
			var erro = '<span>' + newCampanhas.msgsErro[0] + '</span>';
			$('.alert').html(erro).addClass('alert-danger').show();
		}	
	});
}

function vaiPraPagina() {
	console.log('vai pra=');
	$('#mobi4e-container').load('campanhaatualizacaopedidoDetalhe.html');
}