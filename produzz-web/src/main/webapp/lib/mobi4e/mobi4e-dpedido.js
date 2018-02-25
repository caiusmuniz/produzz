/*
 */
$(function()	{
	console.log('COMECEI....');
	$('#count_message').html(text_max + ' restantes');
	$('#count_message_pg_aprovado').html(text_max + ' restantes');
	$('#count_message_pg_nao_aprovado').html(text_max + ' restantes');
	$('#count_message_separacao').html(text_max + ' restantes');
	$('#count_message_transportadora').html(text_max + ' restantes');
	$('#count_message_cancelado').html(text_max + ' restantes');
	
	$('#msgtext_cancelado').keyup(function() {
	  contadorCancelado();
	});	

	$('#msgtext_transportadora').keyup(function() {
		contadorTransportadora();
	});	

	$('#msgtext_separacao').keyup(function() {
		contadorSeparacao();
	});	
	
	$('#msgtext_pedido_realizado').keyup(function() {
		contadorPedidoRealizado();
	});	

	$('#msgtext_pg_aprovado').keyup(function() {
		contadorPagamentoAprovado();
	});	
	
	function contadorPedidoRealizado() {
		atualizarContador('#msgtext_pedido_realizado', '#count_message'); 
	}			
	
	function contadorPagamentoAprovado() {
		atualizarContador('#msgtext_pg_aprovado', '#count_message_pg_aprovado'); 
	}			

	function contadorSeparacao() {
		atualizarContador('#msgtext_separacao', '#count_message_separacao'); 
	}			

	function contadorCancelado() {
		atualizarContador('#msgtext_cancelado', '#count_message_cancelado'); 
	}			

	function contadorTransportadora() {
		atualizarContador('#msgtext_transportadora', '#count_message_transportadora'); 
	}			

	$('#btn_url_realizados').click(function(){
		var textarea = document.getElementById("msgtext_pedido_realizado");
		textarea.focus();
		insertTextAtCaret(textarea, "<LINK_PARA_O_PEDIDO>");
		contadorPedidoRealizado();
	});  
	
	$('#btn_num_pedido_realizados').click(function(){
		var textarea = document.getElementById("msgtext_pedido_realizado");
		textarea.focus();
		insertTextAtCaret(textarea, "<NUMERACAO_DO_PEDIDO>");
		contadorPedidoRealizado();
	});  

	$('#btn_nome_cliente_realizados').click(function() {
		var textarea = document.getElementById("msgtext_pedido_realizado");
		textarea.focus();
		insertTextAtCaret(textarea, "<NOME_DO_CLIENTE>");
		contadorPedidoRealizado();
	});

	$('#btn_url_pg_aprovado').click(function(){
		var textarea = document.getElementById("msgtext_pg_aprovado");
		textarea.focus();
		insertTextAtCaret(textarea, "<LINK_PARA_O_PEDIDO>");
		contadorPagamentoAprovado();
	});  
	
	$('#btn_num_pedido_pg_aprovado').click(function(){
		var textarea = document.getElementById("msgtext_pg_aprovado");
		textarea.focus();
		insertTextAtCaret(textarea, "<NUMERACAO_DO_PEDIDO>");
		contadorPagamentoAprovado();
	});  

	$('#btn_nome_cliente_pg_aprovado').click(function() {
		var textarea = document.getElementById("msgtext_pg_aprovado");
		textarea.focus();
		insertTextAtCaret(textarea, "<NOME_DO_CLIENTE>");
		contadorPagamentoAprovado();
	});

	$('#btn_url_separacao').click(function(){
		var textarea = document.getElementById("msgtext_separacao");
		textarea.focus();
		insertTextAtCaret(textarea, "<LINK_PARA_O_PEDIDO>");
		contadorSeparacao();
	});  
	
	$('#btn_num_pedido_separacao').click(function(){
		var textarea = document.getElementById("msgtext_separacao");
		textarea.focus();
		insertTextAtCaret(textarea, "<NUMERACAO_DO_PEDIDO>");
		contadorSeparacao();
	});

	$('#btn_nome_cliente_separacao').click(function() {
		var textarea = document.getElementById("msgtext_separacao");
		textarea.focus();
		insertTextAtCaret(textarea, "<NOME_DO_CLIENTE>");
		contadorSeparacao();
	});

	$('#btn_url_cancelado').click(function(){
		var textarea = document.getElementById("msgtext_cancelado");
		textarea.focus();
		insertTextAtCaret(textarea, "<LINK_PARA_O_PEDIDO>");
		contadorCancelado();
	});  

	$('#btn_num_pedido_cancelado').click(function(){
		var textarea = document.getElementById("msgtext_cancelado");
		textarea.focus();
		insertTextAtCaret(textarea, "<NUMERACAO_DO_PEDIDO>");
		contadorCancelado();
	});

	$('#btn_nome_cliente_cancelado').click(function() {
		var textarea = document.getElementById("msgtext_cancelado");
		textarea.focus();
		insertTextAtCaret(textarea, "<NOME_DO_CLIENTE>");
		contadorCancelado();
	});

	$('#btn_url_transportadora').click(function(){
		var textarea = document.getElementById("msgtext_transportadora");
		textarea.focus();
		insertTextAtCaret(textarea, "<LINK_PARA_O_PEDIDO>");
		contadorTransportadora();
	});
	
	$('#btn_num_pedido_transportadora').click(function(){
		var textarea = document.getElementById("msgtext_transportadora");
		textarea.focus();
		insertTextAtCaret(textarea, "<NUMERACAO_DO_PEDIDO>");
		contadorTransportadora();
	});

	$('#btn_nome_cliente_transportadora').click(function() {
		var textarea = document.getElementById("msgtext_transportadora");
		textarea.focus();
		insertTextAtCaret(textarea, "<NOME_DO_CLIENTE>");
		contadorTransportadora();
	});

	$('#btn_url_track_transportadora').click(function(){
		var textarea = document.getElementById("msgtext_transportadora");
		textarea.focus();
		insertTextAtCaret(textarea, "<LINK_PARA_RASTREIO>");
		contadorTransportadora();
	});

	function getInputSelection(el) {
		var start = 0, end = 0, normalizedValue, range,
			textInputRange, len, endRange;

		if (typeof el.selectionStart == "number" && typeof el.selectionEnd == "number") {
			start = el.selectionStart;
			end = el.selectionEnd;
		} else {
			range = document.selection.createRange();

			if (range && range.parentElement() == el) {
				len = el.value.length;
				normalizedValue = el.value.replace(/\r\n/g, "\n");

				// Create a working TextRange that lives only in the input
				textInputRange = el.createTextRange();
				textInputRange.moveToBookmark(range.getBookmark());

				// Check if the start and end of the selection are at the very end
				// of the input, since moveStart/moveEnd doesn't return what we want
				// in those cases
				endRange = el.createTextRange();
				endRange.collapse(false);

				if (textInputRange.compareEndPoints("StartToEnd", endRange) > -1) {
					start = end = len;
				} else {
					start = -textInputRange.moveStart("character", -len);
					start += normalizedValue.slice(0, start).split("\n").length - 1;

					if (textInputRange.compareEndPoints("EndToEnd", endRange) > -1) {
						end = len;
					} else {
						end = -textInputRange.moveEnd("character", -len);
						end += normalizedValue.slice(0, end).split("\n").length - 1;
					}
				}
			}
		}

		return {
			start: start,
			end: end
		};
	}

	function offsetToRangeCharacterMove(el, offset) {
		return offset - (el.value.slice(0, offset).split("\r\n").length - 1);
	}

	function setSelection(el, start, end) {
		if (typeof el.selectionStart == "number" && typeof el.selectionEnd == "number") {
			el.selectionStart = start;
			el.selectionEnd = end;
		} else if (typeof el.createTextRange != "undefined") {
			var range = el.createTextRange();
			var startCharMove = offsetToRangeCharacterMove(el, start);
			range.collapse(true);
			if (start == end) {
				range.move("character", startCharMove);
			} else {
				range.moveEnd("character", offsetToRangeCharacterMove(el, end));
				range.moveStart("character", startCharMove);
			}
			range.select();
		}
	}
	function insertTextAtCaret(el, text) {
		var pos = getInputSelection(el).end;
		var newPos = pos + text.length;
		var val = el.value;
		el.value = val.slice(0, pos) + text + val.slice(pos);
		setSelection(el, newPos, newPos);
	}

});
