
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
      document.getElementsByName('content')[1].style.display="block";
      document.getElementsByName('content')[0].style.display="none";
      document.getElementsByName('content')[2].style.display="none";
      var wrongMess = document.getElementById("bodyWrongMess");
      wrongMess.style.visibility="hidden";

      // document.getElementById('tableLeftDown').style.display = 'block';
      document.getElementById('tablePayment').style.display = 'none';
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
  document.getElementsByName('content')[2].style.display="none";
}
function home(){
  document.getElementsByName('content')[0].style.display="block";
  document.getElementsByName('content')[1].style.display="none";
  document.getElementsByName('content')[2].style.display="none";
  document.getElementsByClassName("accName")[0].innerHTML = ""+userInfo.fname+" "+userInfo.lname;
 if(accounts==undefined){
    document.getElementsByClassName("accNumb")[0].innerHTML = "Ziaden ucet nemas takze nechapem co tu vlastne robis";
    document.getElementsByClassName("currBalance")[0].innerHTML ="\u20ac 0 Nemaaaas nic ty ***";
  }else{
    document.getElementsByClassName("accNumb")[0].innerHTML = ""+accounts[0].AccNum;
    document.getElementsByClassName("currBalance")[0].innerHTML ="\u20ac "+accounts[0].amount;
  }

  getTransactions(accounts[0].id);
  getTransactionsReceived(accounts[0].AccNum);

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
      var dropCont = document.getElementsByClassName('dropdown-content')[0];
      dropCont.innerHTML="";
      for(var a=0;a<accounts.length;a++){
        addAccNumb(accounts[a],a);
        // getTransactions(accounts[a].id);
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
  button.setAttribute("onclick","changeAcc("+index+","+account.id+",'"+account.AccNum+"')");
  var p = document.createElement('P');
  var text = document.createTextNode(account.AccNum);

  button.appendChild(text);
  p.appendChild(button);
  dropCont.appendChild(p);
}

function changeAcc(index,currAccIdAcc,currAccNum){
  console.log("currAccNum "+currAccNum);
  currentAcc = accounts[index];
  document.getElementsByClassName("accName")[0].innerHTML = ""+userInfo.fname+" "+userInfo.lname;
  document.getElementsByClassName("accNumb")[0].innerHTML = ""+accounts[index].AccNum;
  document.getElementsByClassName("currBalance")[0].innerHTML ="\u20ac "+accounts[index].amount;
  currentAmount = accounts[index].amount;
  console.log(currAccIdAcc);
  getTransactions(currAccIdAcc);
  getTransactionsReceived(currAccNum);
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
        addTransHistorySent(trans);
    }

    if (this.status == 403) {
      console.log(this.responseText);
      addTransHistorySent(null);
  }
  });
  
  xhr.open("POST", "http://localhost:3000/transHistory");
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.setRequestHeader("cache-control", "no-cache");
  xhr.setRequestHeader("Postman-Token", "ebfb6e12-0c8f-4cdb-8c3f-74fdd6cf8841");
  
  xhr.send(data);
}

function getTransactionsReceived(receivedAccNum){
  var data = JSON.stringify({
    "login": user.login,
    "RecAccount": receivedAccNum,
    "token": user.token
  });
  
  var xhr = new XMLHttpRequest();
  xhr.withCredentials = true;
  
  xhr.addEventListener("readystatechange", function () {
    if (this.readyState === 4 && this.status == 200) {
      var trans = JSON.parse(this.responseText);
      addTransHistoryReceived(trans);
  }

  if (this.status == 403) {
    console.log(this.responseText);
    addTransHistoryReceived(null);
}
  });
  
  xhr.open("POST", "http://localhost:3000/transHistoryRecAccMeth");
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.setRequestHeader("cache-control", "no-cache");
  xhr.setRequestHeader("Postman-Token", "fc645bfd-ceca-42bc-b590-6218f624c1d2");
  
  xhr.send(data);
}

function addTransHistorySent(transHistory){
  document.getElementsByClassName('tableTrans')[0].innerHTML='';
  var captionHead = document.createElement('CAPTION');
  var captionText = document.createTextNode("Sent-Transactions");
  captionHead.appendChild(captionText);
  var tableTrans = document.getElementsByClassName('tableTrans')[0];
  tableTrans.appendChild(captionHead);
  var tr = document.createElement("tr");
  appendTransTh(tr,"Account FROM");
  appendTransTh(tr,"Recipient Account");
  appendTransTh(tr,"TransactionDate");
  appendTransTh(tr,"TransactionAmount");
  tableTrans.appendChild(tr);
  if(transHistory==null){
      var text = document.createTextNode(" ");
      var p = document.createElement('P');
      p.appendChild(text);
      tableTrans.appendChild(p);
  }else{
    for(var a=0;a<transHistory.length;a++){
      var date = changeDateFormat(transHistory[a].TransDate);
      var tr = document.createElement('TR');
      if(currentAcc!=undefined){
        appendTransTD(tr,currentAcc.AccNum);
        appendTransTD(tr,transHistory[a].RecAccount);
        appendTransTD(tr,date);
        appendTransTD(tr,transHistory[a].TransAmount+" \u20ac");
      }
      tableTrans.appendChild(tr);
  }
  }
}

function changeDateFormat(date){
  var d = new Date(date);
  return d.getFullYear()+"-"+("0"+(d.getMonth()+1)).slice(-2)+"-"+("0" + d.getDate()).slice(-2)+" "+("0" + d.getHours()).slice(-2)+":"+("0" + d.getMinutes()).slice(-2)+":"+("0" + d.getSeconds()).slice(-2);
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



function addTransHistoryReceived(transHistory){
  document.getElementsByClassName('tableTransReceived')[0].innerHTML='';
  var captionHead = document.createElement('CAPTION');
  var captionText = document.createTextNode("Received-Transactions");
  captionHead.appendChild(captionText);
  var tableTrans = document.getElementsByClassName('tableTransReceived')[0];
  tableTrans.appendChild(captionHead);
  var tr = document.createElement("tr");
  appendTransTh(tr,"Account FROM");
  appendTransTh(tr,"Recipient Account");
  appendTransTh(tr,"TransactionDate");
  appendTransTh(tr,"TransactionAmount");
  tableTrans.appendChild(tr);
  if(transHistory==null){
      var text = document.createTextNode(" ");
      var p = document.createElement('P');
      p.appendChild(text);
      tableTrans.appendChild(p);
  }else{
    for(var a=0;a<transHistory.length;a++){
      var date = changeDateFormat(transHistory[a].TransDate);
      var tr = document.createElement('TR');
      if(currentAcc!=undefined){
        appendTransTD(tr,transHistory[a].FromAcc);
        appendTransTD(tr,transHistory[a].RecAccount);
        appendTransTD(tr,date);
        appendTransTD(tr,transHistory[a].TransAmount+" \u20ac");
      }
      tableTrans.appendChild(tr);
  }
  }
}

function gotToPayment(){
  document.getElementById('tableLeftDown').style.display = 'none';
  document.getElementById('tablePayment').style.display = 'block';
}

function performPayment(){
  var toAcc = document.getElementById("toAccNum").value;
  var amountToSent = document.getElementById("amountToSent").value;
  if(currentAcc.amount-amountToSent>-1){
    
    var data = JSON.stringify({
      "login": user.login,
      "token": user.token,
      "amount": amountToSent,
      "idAccFrom": currentAcc.id,
      "RecAccNum": toAcc
    });
    
    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;
    
    xhr.addEventListener("readystatechange", function () {
      if (this.readyState === 4 && this.status == 200) {
        alert(this.responseText);
        getAccounts();
      }
    });
    
    xhr.open("POST", "http://localhost:3000/newTransaction");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("cache-control", "no-cache");
    xhr.setRequestHeader("Postman-Token", "25972099-e0ee-40e9-8e77-aa6c53f86aa5");
    
    xhr.send(data);

  }else{
    alert("Cannot sent this amount of money cause you haven't enough money for that");
  }

  document.getElementById('tableLeftDown').style.display = 'block';
  document.getElementById('tablePayment').style.display = 'none';

}

function getToChangePassword(){
  document.getElementsByName('content')[2].style.display="block";
  document.getElementsByName('content')[1].style.display="none";
  document.getElementsByName('content')[0].style.display="none";
}

function changePassword(){
  var oldPass = document.getElementById("oldPass").value;
  var newPass = document.getElementById("newPass").value;
  var repeatePass = document.getElementById("newRptPass").value;

  if(newPass === repeatePass){
    var wrongMess = document.getElementById("bodyWrongMess");
    wrongMess.innerHTML = "";
    wrongMess.style.visibility="hidden";

    var data = JSON.stringify({
      "login": user.login,
      "token": user.token,
      "oldpassword": oldPass,
      "newpassword": newPass
    });
    
    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;
    
    xhr.addEventListener("readystatechange", function () {
      if (this.readyState === 4 && this.status == 200) {
        wrongMess.innerHTML = "";
        wrongMess.style.visibility="visible";
        var par = document.createElement("P");
        par.style.color = "green";
        var text = document.createTextNode("Password successfully changed");
        par.appendChild(text);
        wrongMess.appendChild(par);
        cleandInputs();

      }

      if(this.readyState === 4 && this.status == 403){
        wrongMess.innerHTML = "";
        wrongMess.style.visibility="visible";
        var par = document.createElement("P");
        par.style.color = "red";
        var text = document.createTextNode("Wrong old password");
        par.appendChild(text);
        wrongMess.appendChild(par);
      }
    });
    
    xhr.open("POST", "http://localhost:3000/changePassword");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("cache-control", "no-cache");
    xhr.setRequestHeader("Postman-Token", "526c04aa-5756-48fb-ba32-118e560d5de4");
    
    xhr.send(data);


  }else{
    var wrongMess = document.getElementById("bodyWrongMess");
    wrongMess.innerHTML = "";
    wrongMess.style.visibility="visible";
    var par = document.createElement("P");
    par.style.color = "red";
    var text = document.createTextNode("New password and repeated password are not equal");
    par.appendChild(text);
    wrongMess.appendChild(par);
  }
}

function cleandInputs(){

  var oldpass = document.getElementById("oldPass");
  var newpass = document.getElementById("newPass");
  var repeatpass = document.getElementById("newRptPass");

  oldpass.value = "";
  newpass.value = "";
  repeatpass.value = "";

}