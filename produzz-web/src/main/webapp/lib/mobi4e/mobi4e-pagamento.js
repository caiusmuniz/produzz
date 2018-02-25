var contato = JSON.parse($.cookie('contato'));
if(jQuery.type(contato) != 'undefined') {
	$('#emailInfo').empty().html('<strong>Seu e-mail de login é '+contato.login +'</strong>');
}

(function() {
	var appUrl = encodeURIComponent( document.location.href );
	var iframePlanos = document.createElement("iframe");
	iframePlanos.setAttribute("frameborder",0);
	iframePlanos.setAttribute("width","90%");
	iframePlanos.setAttribute("height",800);
	iframePlanos.src="https://mobi4e.superlogica.net/clients/areadocliente/publico/";
	document.getElementById("superlogica_planos_script").appendChild(iframePlanos);
}());


$('#sl_Plano').change(function() {

	if ($(this).val() == '1')
	{
		$('#totalSMS').empty().html('1.000');
		$('#valorSMS').empty().html('R$ 390,00');
	}
	else if ($(this).val() == '2')
	{
		$('#totalSMS').empty().html('5.000');
		$('#valorSMS').empty().html('R$ 990,00');
	}
	else if ($(this).val() == '3')
	{
		$('#totalSMS').empty().html('20.000');
		$('#valorSMS').empty().html('R$ 3.590,00');
	}
	else
	{
		$('#totalSMS').empty().html('');
		$('#valorSMS').empty().html('');
	}
	
	
	//alert('The option with value ' + $(this).val() + ' and text ' + $(this).text() + ' was selected.');
});

function doSelecionaPlano(select){

	 var selectedOption = select.options[select.selectedIndex];
	
	if (selectedOption.value = '1')
		$('#totalSMS').empty().html('1.000');
	else if (selectedOption.value = '2')
		$('#totalSMS').empty().html('3.000');
	
	//if you want to verify a change took place...
    //if(obj._initValue == obj.value){
      //do nothing, no actual change occurred...
      //or in your case if you want to make a minor update
      //doMinorUpdate();
    	
    //} else {
      //change happened
      //getNewData(obj.value);
}
    
$(document).ready(function(){
	$('#div_cartao_novo').hide();
	$('#btn_novo_cartao').click(function(){
		$('#div_cartao_novo').toggle();
		$('#div_cartao_atual').toggle();
	});        

	$('#btn_cancelar_novo_cartao').click(function(){
		$('#div_cartao_novo').toggle();
		$('#div_cartao_atual').toggle();
	});  

	$('#form_compra').submit(function(e) {
		e.preventDefault();
		if ( $('#form_compra').parsley('isValid')) {

			$.ajax({
						type: 'POST',
						url: 'dashboard.html', //URL Post
						data: $(this).serialize(), //your form datas to post          
						success: function(response)
						{
							$('#div_cartao_novo').toggle();
							$('#div_cartao_atual').toggle();
						},
						error: function()
						{
							//$('#div_vtex_autenticacao_falhou').show();
						}
					});
		
		} else return false;
	});	


	
});	
