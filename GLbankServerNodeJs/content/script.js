
var user = JSON.parse(window.localStorage.getItem('user'));
  var data = JSON.stringify({
    "login": user.login,
    "token": user.token
  });
  var accounts;
  var userInfo;

window.onload = inicialise();

function inicialise(){
  var xhr = new XMLHttpRequest();
  xhr.withCredentials = true;
  
  xhr.addEventListener("readystatechange", function () {
    if (this.readyState === 4 && this.status==200) {
      userInfo = JSON.parse(this.responseText);

      document.getElementById('fname').innerHTML = ""+userInfo.fname;
      document.getElementById('lname').innerHTML = ""+userInfo.lname;
      document.getElementById('email').innerHTML = ""+userInfo.email;
      document.getElementById('id').innerHTML = ""+userInfo.id;
      document.getElementsByName('content')[1].style.display="none";
      document.getElementsByName('content')[0].style.display="none";
      getAccounts();
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
  

let content;
function profile(){
  document.getElementsByName('content')[1].style.display="block";
  document.getElementsByName('content')[0].style.display="none";
}
function home(){
  document.getElementsByName('content')[0].style.display="block";
  document.getElementsByName('content')[1].style.display="none";
  console.log(""+userInfo.fname+" "+userInfo.lname+" "+accounts[0].AccNum);
  document.getElementsByClassName("accName")[0].innerHTML = ""+userInfo.fname+" "+userInfo.lname;
  document.getElementsByClassName("accNumb")[0].innerHTML = ""+accounts[0].AccNum;
  document.getElementsByClassName("currBalance")[0].innerHTML ="\u20ac "+accounts[0].amount;
}

function getAccounts(){
  var data = JSON.stringify({
    "login": user.login,
    "token": user.token
  });
  
  var xhr = new XMLHttpRequest();
  xhr.withCredentials = true;
  
  xhr.addEventListener("readystatechange", function () {
    if (this.readyState === 4 && this.status == 200) {
      console.log(this.responseText);
      accounts= JSON.parse(this.responseText);
      console.log(accounts);
    }
  });
  
  xhr.open("POST", "http://localhost:3000/accounts");
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.setRequestHeader("cache-control", "no-cache");
  xhr.setRequestHeader("Postman-Token", "bdd97c40-eefa-47cb-869c-ba8caf45f636");
  
  xhr.send(data);
}