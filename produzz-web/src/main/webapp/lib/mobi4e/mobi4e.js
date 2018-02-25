$(function() {
//	var	contato = JSON.parse($.cookie('contato'));
//	if (jQuery.type(contato) != 'undefined') {
//		$('#nome-profile').empty().html(contato.nome + ' ' + contato.sobrenome);
//		$('#nome').empty().html(contato.nome + ' ' + contato.sobrenome);
//		$('#email').empty().html(contato.login);
//		$('#saudacao').empty().html('Bem-vindo, ' + contato.nome + ' ' + contato.sobrenome + '! Veja aqui seus resultados.');
//		$('#login').empty().html(contato.login);
//	}
//	
//	setInterval(function() {
//		carregaPaginaUsuario();
//		carregaPaginaDashboard();
//	}, 5000);
	
//	$('#usuario').on('click', function() {
//		$('#mobi4e-container').load("usuario.html #main-container");		
//		setTimeout(function() {
//			carregaUsuario();
//		}, 300);
//	});
	
//	$('#dashboard').on('click', function() {
//		carregaDashboardPeloIndex();
//	});
	
	//=======================================
	//carregaDashboardPeloIndex();
	
//	$('.login-link').click(function(e) {
//		e.preventDefault();
//		href = $(this).attr('href');
//		
//		//$('.login-wrapper').addClass('fadeOutUp');
//
//		setTimeout(function() {
//			console.log('entrei1....('+ href +')');
//			$.getJSON("js/mobi4e/paginas.json", function(data){
//				$.each(data, function(i, item){
//					console.log('entrei2....('+ href +')');
//					if(href.substring(1) === item.href){
//						console.log('entrei3....('+ href +','+ item.href +')');
//						$('#mobi4e-container').load(item.url);
//					}
//				});			
//			});
//		}, 900);
//					
//		return false;	
//	});
});

function carregaDashboardPeloIndex() {
	$('#mobi4e-container').load("dashboard.html #main-container");
	setTimeout(function() {
		dashboardConta();			
	}, 300);
	setTimeout(function() {
		obtemDadosCampanha();
	}, 900);	
}
