
var user = JSON.parse(window.localStorage.getItem('user'));
  var data = JSON.stringify({
    "login": user.login,
    "token": user.token
  });
  var accounts;
  var userInfo;
  var currentAcc;
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
  document.getElementsByClassName("accName")[0].innerHTML = ""+userInfo.fname+" "+userInfo.lname;
 if(accounts==undefined){
    document.getElementsByClassName("accNumb")[0].innerHTML = "Ziaden ucet nemas takze nechapem co tu vlastne robis";
    document.getElementsByClassName("currBalance")[0].innerHTML ="\u20ac 0 Nemaaaas nic ty ***";
  }else{
    document.getElementsByClassName("accNumb")[0].innerHTML = ""+accounts[0].AccNum;
    document.getElementsByClassName("currBalance")[0].innerHTML ="\u20ac "+accounts[0].amount;
  }

  getTransactions(accounts[0].id);

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
      accounts= JSON.parse(this.responseText);
      for(var a=0;a<accounts.length;a++){
        addAccNumb(accounts[a],a);
        getTransactions(accounts[a].id);
      }
    }
  });
  
  xhr.open("POST", "http://localhost:3000/accounts");
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.setRequestHeader("cache-control", "no-cache");
  xhr.setRequestHeader("Postman-Token", "bdd97c40-eefa-47cb-869c-ba8caf45f636");
  
  xhr.send(data);
}

function addAccNumb(account,index){
  var dropCont = document.getElementsByClassName('dropdown-content')[0];
  var button = document.createElement('BUTTON');
  button.setAttribute("class","buttonAccount");
  button.setAttribute("onclick","changeAcc("+index+","+account.id+")");
  var p = document.createElement('P');
  var text = document.createTextNode(account.AccNum);

  button.appendChild(text);
  p.appendChild(button);
  dropCont.appendChild(p);
}

function changeAcc(index,currAccIdAcc){
  currentAcc = accounts[index];
  document.getElementsByClassName("accName")[0].innerHTML = ""+userInfo.fname+" "+userInfo.lname;
  document.getElementsByClassName("accNumb")[0].innerHTML = ""+accounts[index].AccNum;
  document.getElementsByClassName("currBalance")[0].innerHTML ="\u20ac "+accounts[index].amount;
  getTransactions(currAccIdAcc);
}

function getTransactions(idAcc){
  var data = JSON.stringify({
    "login": user.login,
    "idAcc": idAcc,
    "token": user.token
  });
  
  var xhr = new XMLHttpRequest();
  xhr.withCredentials = true;
  
  xhr.addEventListener("readystatechange", function () {
    if (this.readyState === 4 && this.status == 200) {
        var trans = JSON.parse(this.responseText);
        addTransHistory(trans);
    }

    if (this.status == 403) {
      console.log(this.responseText);
      addTransHistory(null);
  }
  });
  
  xhr.open("POST", "http://localhost:3000/transHistory");
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.setRequestHeader("cache-control", "no-cache");
  xhr.setRequestHeader("Postman-Token", "ebfb6e12-0c8f-4cdb-8c3f-74fdd6cf8841");
  
  xhr.send(data);
}

function addTransHistory(transHistory){
  document.getElementsByClassName('tableTrans')[0].innerHTML='';
  var tableTrans = document.getElementsByClassName('tableTrans')[0];
  var tr = document.createElement("tr");
  appendTransTh(tr,"Account FROM");
  appendTransTh(tr,"Recipient Account");
  appendTransTh(tr,"TransactionDate");
  appendTransTh(tr,"TransactionAmount");
  tableTrans.appendChild(tr);
  if(transHistory==null){
      var text = document.createTextNode("NONE TRANSHISTORY");
      var p = document.createElement('P');
      p.appendChild(text);
      tableTrans.appendChild(p);
  }else{
    for(var a=0;a<transHistory.length;a++){

      var tr = document.createElement('TR');
      appendTransTD(tr,currentAcc.AccNum);
      appendTransTD(tr,transHistory[a].RecAccount);
      appendTransTD(tr,transHistory[a].TransDate);
      appendTransTD(tr,transHistory[a].TransAmount);
      tableTrans.appendChild(tr);
  }
  }
}

function appendTransTD(tr,value){
  var td = document.createElement('TD');
  var text = document.createTextNode(value);
  td.appendChild(text);
  tr.appendChild(td);
}

function appendTransTh(tr,value){
  var th = document.createElement('TH');
  var text = document.createTextNode(value);
  th.appendChild(text);
  tr.appendChild(th);
}