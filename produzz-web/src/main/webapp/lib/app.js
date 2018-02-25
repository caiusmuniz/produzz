$(function() {
	$('#senha').on('blur',function() {
		var that = $(this),
			value = md5(that.val());

		that.val(value);
	});

	$('#confirma').on('blur',function() {
		var that = $(this),
			value = md5(that.val());

		that.val(value);
	});

	$('form.ajax').on('submit', function() {
		var that = $(this),
			url = that.attr('action'),
			type = that.attr('method'),
			data = {};

		that.find('[name]').each(function(index,value) {
			var that = $(this),
				name = that.attr('name'),
				value = that.val();

			data[name] = value;
			
		});

		$.ajax({
			url: url,
			type: type,
			data: data,
			success: function(response) {
				console.log(response);
			}
		})

		return false;
	});
});
