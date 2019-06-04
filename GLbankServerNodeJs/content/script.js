
var user = JSON.parse(window.localStorage.getItem('user'));
  var data = JSON.stringify({
    "login": user.login,
    "token": user.token
  });


window.onload = inicialise();

function inicialise(){
  var xhr = new XMLHttpRequest();
  xhr.withCredentials = true;
  
  xhr.addEventListener("readystatechange", function () {
    if (this.readyState === 4) {
      var userInfo = JSON.parse(this.responseText);

      document.getElementById('fname').innerHTML = "fname: "+userInfo.fname;
      document.getElementById('lname').innerHTML = "lname: "+userInfo.lname;
      document.getElementById('email').innerHTML = "email: "+userInfo.email;
      document.getElementById('id').innerHTML = "id: "+userInfo.id;

      console.log("fname "+userInfo.fname);
      console.log("lname "+userInfo.lname);
      console.log("email "+userInfo.email);
      console.log("id "+userInfo.id);
    }
  });
  
  xhr.open("POST", "http://localhost:3000/userinfo");
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.setRequestHeader("cache-control", "no-cache");
  xhr.setRequestHeader("Postman-Token", "de674f50-48b1-459b-9028-5139dad83453");
  
  xhr.send(data);
}

function logOut(){
  window.localStorage.removeItem('user');
  location.replace("../login/index.html");
}
  