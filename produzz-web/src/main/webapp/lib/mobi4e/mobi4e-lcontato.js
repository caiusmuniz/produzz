/* 
 * 
 */
$(function	()	{
	$('#div_lista_contatos').show(); //hide();

	$('.email-link').click(function()	{
	
		window.open($(this).attr('href'));
		
		return false;
	});
	
	$('#dataTable').dataTable( {
		"bJQueryUI": true,
		"destroy": true,
		"sPaginationType": "full_numbers",

		language: {
			"sEmptyTable": "Nenhum registro encontrado",
			"sInfo": "",
			"sInfoEmpty": "",
			"sInfoFiltered": "(Filtrados de _MAX_ registros)",
			"sInfoPostFix": "",
			"sInfoThousands": "",
			"sLengthMenu": "",
			"sLoadingRecords": "Carregando...",
			"sProcessing": "Processando...",
			"sZeroRecords": "Nenhum registro encontrado",
			"sSearch": "",
			"oPaginate": {
				"sNext": "",
				"sPrevious": "",
				"sFirst": "",
				"sLast": ""
			},
			"oAria": {
				"sSortAscending": ": Ordenar colunas de forma ascendente",
				"sSortDescending": ": Ordenar colunas de forma descendente"
			}		
		}

	});
	
	
	$('#chk-all').click(function()	{
		if($(this).is(':checked'))	{
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

	$('#chk_msn_concordo').click(function()	{
		if($(this).is(':checked'))	{
			$('#div_lista_contatos').show();
		}
		else	{
			$('#div_lista_contatos').hide();
		}
	});
	//
});
