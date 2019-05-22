function sendLogin(){
	var username = document.getElementsByTagName('username')[0].value;
	var password = document.getElementsByTagName('passwd')[0].value;

	console.log(username+" "+password);
	alert(username+" "+password);
}