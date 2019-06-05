
var user = JSON.parse(window.localStorage.getItem('user'));
  var data = JSON.stringify({
    "login": user.login,
    "token": user.token
  });


let fname;
let lname;
let email;
let id;

window.onload = inicialise();

function inicialise(){
  var xhr = new XMLHttpRequest();
  xhr.withCredentials = true;
  
  xhr.addEventListener("readystatechange", function () {
    if (this.readyState === 4) {
      var userInfo = JSON.parse(this.responseText);

      fname = ""+userInfo.fname;
      lname = ""+userInfo.lname;
      email = ""+userInfo.email;
      id = ""+userInfo.id;
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
  

let body;
var table;
let content;
function profile(){
  document.getElementById('content').innerHTML="";
  content = document.getElementById('content');

  body= document.createElement('div');
  body.setAttribute("id","bodyProfil");
  content.appendChild(body);
  table = document.createElement('TABLE');
  table.setAttribute("id","tableInfo");
  body.appendChild(table);

  appendTr("FirstName:","fname",fname);
  appendTr("LastName:","lname",lname);
  appendTr("Email:","email",email);
  appendTr("IdClient:","id",id);
}

function appendTr(nameTh,nameClass,valueTd){
  var tr = document.createElement('tr');
  var th = document.createElement('th');
  var td = document.createElement('td');
  var text = document.createTextNode(nameTh);

  th.appendChild(text);
  tr.appendChild(th);

  td.setAttribute("id",nameClass);
  text = document.createTextNode(valueTd);
  td.appendChild(text);
  tr.appendChild(td);
  table.appendChild(tr);
}


function home(){
  document.getElementById('content').style.visibility="visible";
}
