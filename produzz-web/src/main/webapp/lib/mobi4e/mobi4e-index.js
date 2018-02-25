$(function(){
	if (jQuery.type($.cookie('contato')) == 'undefined') {
		console.debug('contato: '+contato)
		$(location).attr('href','login.html');
	}
	
	var	contato = JSON.parse($.cookie('contato'));
	if (jQuery.type(contato) != 'undefined') {
		$('.dropdown-toggle strong').empty().html(contato.nome + ' ' + contato.sobrenome);
		$('.detail strong').empty().html(contato.nome + ' ' + contato.sobrenome);
		$('.detail .grey').empty().html(contato.login);
	}

	$('.mobi4e-link').click(function(e) {
		console.log('mobi4e-index.js');
		e.preventDefault();
		href = $(this).attr('href');
		console.log('href='+href);
		$.getJSON("json/paginas.json", function(data){
	        $.each(data, function(i, item){
	        	if(href.substring(1) === item.href){
	        		console.log('url='+item.url);
					$('#mobi4e-container').load(item.url);
				}
	        });
	    });
	});
})