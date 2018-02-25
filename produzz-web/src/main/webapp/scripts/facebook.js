function statusChangeCallback(response) {
	if (response.status === 'connected') {
		login();

	} else {
		document.getElementById('status').innerHTML = '';
	}
}

function checkLoginState() {
	FB.getLoginStatus(function(response) {
		statusChangeCallback(response);
	}, true);
}

window.fbAsyncInit = function() {
	FB.init({
		appId:'777797732407573',
		status:true,
		cookie:true,
		xfbml:true,
		version:'v2.10'
	});

	FB.getLoginStatus(function(response) {
		statusChangeCallback(response);
	});
};

(function(d, s, id) {
	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id)) return;
	js = d.createElement(s); js.id = id;
	js.src = "//connect.facebook.net/pt_BR/sdk.js";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

function login() {
	FB.api('/me', {fields: ['name','email']}, function(response) {
		console.log(JSON.stringify(response));
		document.getElementById('status').innerHTML = 'Thanks for logging in, ' + response.name + '!';
		httpGet('http://localhost:8080/app/ws/login/' + response.name + '/' + response.email);
	});
}

function httpGet(url) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", url, false );
    xmlHttp.send( null );
    return xmlHttp.responseText;
}
