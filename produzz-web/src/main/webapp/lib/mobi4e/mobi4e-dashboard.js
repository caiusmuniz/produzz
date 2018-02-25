var contato = JSON.parse($.cookie('contato'));

if (jQuery.type(contato) != 'undefined') {
	var conta = contato.conta, plano = conta.plano;

	$('#id').empty().html(contato.id);
	$('#meuCredito').empty().html(conta.qtSaldoDisponivel);
	$('#meuPlano').empty().html(plano.noPlano);
	$('#totalCredito').val(conta.qtSaldoDisponivel);

	var texto = 'Bem-vindo, ' + contato.nome + ' ' + contato.sobrenome + '! Veja aqui seus resultados.';
	$('#saudacao').empty().html(texto);
}

var rootURL = 'ws/campanhas';

$(function() {
	$('#idCampanha').on('change', function() {
		var campanha = $(this).val();
		obtemDadosCampanha(campanha);
	});

//	campanhaByConta();
	campanha30dias();
	campanhaAvulsas();
	sugerirCredito();
	findContaById();

	dashboardConta();
	//setTimeout(function() {
	//	obtemDadosCampanha();
	//}, 300);
});

function sugerirCredito() {
	var credito = $('#totalCredito').val();
	if(credito <= 0) {
		//$('#modalCredito').modal('show');
		$.gritter.add({
			title: '<i class="fa fa-warning"></i> Você não tem créditos no Mobi4e!',
			text: 'Contrate mais crétidos agora clicando no botão abaixo:<br><br><a class="btn btn-sm btn-warning" href="pagamento.html">CONTRATE AQUI!</a>',
			sticky: false,
			time: '',
			class_name: 'gritter-warning'
		});		
	}
}

function findContaById() {
	var urlWs = 'ws/contas/'+conta.idConta;
	$.getJSON(urlWs, function(data){
		var conta = data.conta;
		$('#meuCredito').empty().html(conta.qtSaldoDisponivel);
		$('#meuPlano').empty().html(conta.plano.noPlano);
	});
}

function campanhaByConta() {
	var urlWs = 'ws/campanhas/' + conta.idConta;
	$.getJSON(urlWs, function(data){
		$('#qtEntregue').empty().html(data[0].qtEntregue);
		$('#qtClicada').empty().html(data[0].qtClicada);
		$('#qtVisualizada').empty().html(data[0].qtVisualizada);
	});
}

function campanha30dias() {
	var urlWs = 'ws/campanhas/' + conta.idConta + '/evento'; 
	$.getJSON(urlWs, function(data){
		var enviado = data.ENV,
		pcenviado = data.PC_ENV,
		recebido = data.REC,
		pcrecebido = data.PC_REC,
		aberto = data.RED,
		pcaberto = data.PC_RED;
		erro = data.ERR,
		pcerro = data.PC_ERR;
		pendente = data.PEN,
		pcpendente = data.PC_PEN;

		if (enviado == undefined || enviado == 0 || enviado == null) { enviado = 0; }
		$('#qtEnviada').empty().html(enviado);
		if (pcenviado == undefined || pcenviado == 0 || pcenviado == null) { pcenviado = 0; }
		$('#pcEnviada').empty().html(pcenviado);

		if (recebido == undefined || recebido == 0 || recebido == null) { recebido = 0; }
		$('#qtRecebido').empty().html(recebido);
		if (pcrecebido == undefined || pcrecebido == 0 || pcrecebido == null) { pcrecebido = 0; }
		$('#pcRecebido').empty().html(pcrecebido);

		if (aberto == undefined || aberto == 0 || aberto == null) { aberto = 0; }
		$('#qtAberto').empty().html(aberto);
		if (pcaberto == undefined || pcaberto == 0 || pcaberto == null) { pcaberto = 0; }
		$('#pcAberto').empty().html(pcaberto);

		if (erro == undefined || erro == 0 || erro == null) { erro = 0; }
		$('#qtErro').empty().html(erro);
		if (pcerro == undefined || pcerro == 0 || pcerro == null) { pcerro = 0; }
		$('#pcErro').empty().html(pcerro);

		if (pendente == undefined || pendente == 0 || pendente == null) { pendente = 0; }
		$('#qtPendente').empty().html(pendente);
		if (pcpendente == undefined || pcpendente == 0 || pcpendente == null) { pcpendente = 0; }
		$('#pcPendente').empty().html(pcpendente);
	});
}

function campanhaAvulsas() {
	var urlWs = 'ws/campanhas/' + conta.idConta + '/avulsa'; 
	$.getJSON(urlWs, function(data){
		var avulsa = data.campanhaavulsa;
		if (avulsa == undefined || avulsa == 0 || avulsa == null) { avulsa = 0; }
		$('#qtAvulsa').empty().html(avulsa);
	});
}

function dashboardConta() {
	var urlWs = 'ws/dashboard/' + conta.idConta;
	$.getJSON(urlWs, function(data){
		if (data.temErro == true) {
			var erro = '<span>' + data.msgsErro[0] + '</span>';
			$('.alert').html(erro).addClass('alert-danger').show();
		}
		if (data.temErro == false) {
			$('#idCampanha').empty();
			var xgrupo = '';			
			$.each(data.grupoCampanhas, function(key, grupo) {
				xgrupo = $('<optgroup/>').prop('label', grupo.deTipoCampanha);
				$.each(grupo.campanhas, function(key, campanha) {
					$('<option/>').val(campanha.idCampanha)
					.text(campanha.deCampanha).appendTo(xgrupo);
				});
				$('#idCampanha').append(xgrupo);
			});
			var campanha = $("#idCampanha option:selected").val();
			console.debug('SELECIONADO: '+campanha);
			obtemDadosCampanha(campanha);
		}
	});
}

function obtemDadosCampanha(campanha) {
	var urlWs = 'ws/dashboard/' + conta.idConta + '/' + campanha;
	console.debug(urlWs);
	$.getJSON(urlWs, function(data){
		if (data.temErro == true) {
			var erro = '<span>' + data.msgsErro[0] + '</span>';
			$('.alert').html(erro).addClass('alert-danger').show();
		}
		if (data.temErro == false) {
			var campanhaWs = data.campanha;
			var deMensagem = campanhaWs.deMensagem;
			var qtContato = campanhaWs.qtContato;

			var qtEntregue = campanhaWs.qtEntregue;
			var qtClicada = campanhaWs.qtClicada;
			var qtVisualizada = campanhaWs.qtVisualizada;

			var pcClicada;
			if (qtEntregue == undefined || qtEntregue == 0 || qtEntregue == null) { pcClicada = 0 } else { pcClicada = Math.round((qtClicada / qtEntregue) * 100);}
			var pcVisualizada;
			if (qtEntregue == undefined || qtEntregue == 0 || qtEntregue == null) { pcVisualizada = 0 } else { pcVisualizada = Math.round((qtVisualizada / qtEntregue) * 100);}

			$('.progress-bar-success').prop('aria-valuemax', qtContato).prop('aria-valuenow', qtClicada).css('width', pcClicada + '%');
			$('.progress-bar-danger').prop('aria-valuemax', qtContato).prop('aria-valuenow', qtVisualizada).css('width', pcVisualizada + '%');

			$('#pcClicada').empty().html(pcClicada + '%');
			$('#pcVisualizada').empty().html(pcVisualizada + '%');

			$('#qtClicada').empty().html(qtClicada);
			$('#qtVisualizada').empty().html(qtVisualizada);

			$('#ultimaCampanha').empty().html(deMensagem);
		}
	});
}
