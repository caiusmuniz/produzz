$(function	()	{
	var text_max = 255;
	$('#count_message').html(text_max + ' restantes');
	
	$('#msgtext').keyup(function() {
	  var text_length = $('#msgtext').val().length;
	  var text_remaining = text_max - text_length;
	  $('#count_message').html(text_remaining + ' restantes');
	});	
});
