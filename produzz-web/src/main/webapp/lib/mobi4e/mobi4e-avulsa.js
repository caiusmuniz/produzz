/*
 * 
 */
$(function	()	{
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
});
