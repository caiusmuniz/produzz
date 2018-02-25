var contato = JSON.parse($.cookie('contato')),
conta = contato.conta,
plano = conta.plano;

var rootURL = 'ws/campanhas';

if (contato != undefined) {
	$('#id').empty().html(contato.id);
	$('#meuCredito').empty().html(conta.qtSaldoDisponivel);
	$('#meuPlano').empty().html(plano.noPlano);
	$('#totalCredito').val(conta.qtSaldoDisponivel);
	campanhaByConta();
	campanha30dias();
	campanhaAvulsas();
	sugerirCredito();
}

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

$(document).ready(function() {
	setInterval(function() {
		findContaById();
	}, 50000);
});

function findContaById() {
	$.ajax({
		url: 'ws/contas/' + conta.idConta,
		type: 'GET',
		dataType: "json",
		success: function(data, textStatus, xhr) {
			$('#meuCredito').empty().html(data.qtSaldoDisponivel);
			$('#meuPlano').empty().html(data.plano.noPlano);
		}
	});
}

function campanhaByConta() {
	$.ajax({
		url: rootURL + '/' + conta.idConta,
		type: 'GET',
		dataType: "json",
		success: function(data, textStatus, xhr) {
			$('#qtEntregue').empty().html(data[0].qtEntregue);
			$('#qtClicada').empty().html(data[0].qtClicada);
			$('#qtVisualizada').empty().html(data[0].qtVisualizada);
		}
	});
}

function campanha30dias() {
	$.ajax({
		url: rootURL + '/' + conta.idConta + '/evento',
		type: 'GET',
		dataType: "json",
		success: function(data, textStatus, xhr) {
			var enviado = data.ENV;
					pcenviado = data.PC_ENV;
					recebido = data.REC;
					pcrecebido = data.PC_REC;
					aberto = data.RED;
					pcaberto = data.PC_RED;

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
		}
	});
}

function campanhaAvulsas() {
	$.ajax({
		url: rootURL + '/' + conta.idConta + '/avulsa',
		type: 'GET',
		dataType: "json",
		success: function(data, textStatus, xhr) {	
			var avulsa = data.campanhaavulsa;

			if (avulsa == undefined || avulsa == 0 || avulsa == null) { avulsa = 0; }
			$('#qtAvulsa').empty().html(avulsa);
		}
	});
}

function  enviaRequisicao(url,type,contentType,dataType,data) {}

$(function() {
	var	that = $('#form2'),
		url = that.attr('action'),
		type = that.attr('method'),
		data = {};

	that.find('[hs-model]').each(function(index,value) {
		var that = $(this),
			name = that.attr('hs-model'),
			value = that.val();

		data[name] = value;
	});

	$.ajax({
		url: url,
		type: type,
		data: data,
		dataType: "json",
		success: function(response) {
			var newDashboard = response;
			if (newDashboard.temErro == true) {
				var erro = '<span>' + newDashboard.msgsErro[0] + '</span>';
				$('.alert').html(erro).addClass('alert-danger').show();
			}

			if (newDashboard.temErro == false) {
				$('#qtEntregue').empty().html(newDashboard.qtEntregue);
				$('#qtClicada').empty().html(newDashboard.qtClicada);
				$('#qtVisualizada').empty().html(newDashboard.qtVisualizada);

				var opcao = '';
				$.each(newDashboard.grupoCampanhas, function(key, value) {
					var campanha = value;
					opcao = opcao + '<optgroup label="' + campanha.deTipoCampanha + '">'
					$.each(campanha.campanhas, function(key, value) {
						var campanha = value;
						opcao = opcao + '<option value="' + campanha.idCampanha + '">' + campanha.deCampanha + '</option>'
					});
					opcao = opcao + '</optgroup>'
				});

				$('#idCampanha').empty().html(opcao);
			}
		}
	});

	$('#idCampanha').on('change', function() {
		var	that = $('#form1'),
			url = that.attr('action'),
			type = that.attr('method'),
			data = {};

		that.find('[hs-model]').each(function(index,value) {
			var that = $(this),
				name = that.attr('hs-model'),
				value = that.val();

			data[name] = value;
		});

		$.ajax({
			url: url,
			type: type,
			data: data,
			dataType: "json",
			success: function(response) {
				var json = response;
				if (json.temErro == true) {
					var erro = '<span>' + json.msgsErro[0] + '</span>';
					$('.alert').html(erro).addClass('alert-danger').show();
				}
				if (json.temErro == false) {
					$('#qtEntregue').empty().html(json.qtEntregue);
					$('#qtClicada').empty().html(json.qtClicada);
					$('#qtVisualizada').empty().html(json.qtVisualizada);

					var ultimaCampanha = json.qtContato+
						' contatos da campanha "'+
						json.descricao+
						'" realizada em '+
						json.dtCampanha+
						' as '+
						json.hrCampanha;
					$('#ultimaCampanha').empty().html(ultimaCampanha);
				}
			}
		});
	});
});
