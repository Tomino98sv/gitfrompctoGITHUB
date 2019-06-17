const http=require('http');
const express=require('express');
const database=require('./database');
const cors = require('cors'); 


const app=express();
app.use(express.json());
app.use(cors());
app.use(express.static('public'));
app.use(express.static('./'));

app.get('/',function(req,res){

    console.log('new request: /test');
    res.sendFile(__dirname+'/login/index.html');

});

app.post('/login',(req,res)=>{
    let username = req.body.login;
    let password = req.body.password;
    let callback = function(value,status){
         res.status(status).send(value);
    };
    if(username != undefined && password != undefined){
       database.checkLoginMeth(username,password,callback);
       
    }else{
        console.log("loginClient is undefined");
        
    }
});

app.post('/logout', (req,res)=>{
    let login = req.body.login;
    let token = req.body.token;
    let callback = function(value,status){
        res.status(status).send(value);
    };
    database.logoutMeth(login,token,callback);

});

app.post('/userinfo', (req,res)=>{
    let login = req.body.login;
    let token = req.body.token;
    let callback = function(value,status){
        res.status(status).send(value);
    };
    database.userinfoMeth(login,token,callback);

});

app.post('/accounts', (req,res)=>{
    let login = req.body.login;
    let token = req.body.token;
    let callback = function(value,status){
        res.status(status).send(value);
    };
    database.getAccountsMeth(login,token,callback);

});

app.post('/accInfo',(req,res)=>{
    let login = req.body.login;
    let token = req.body.token;
    let accnum = req.body.accnum;
    let callback = function(value,status){
        res.status(status).send(value);
    };
    database.getAccountInfoMeth(login,token,accnum,callback);
});

app.post('/transHistory',(req,res)=>{
    let login = req.body.login;
    let idAcc = req.body.idAcc;
    let token = req.body.token;
    let callback = function(value,status){
        res.status(status).send(value);
    };
    database.transHistoryMeth(login,idAcc,token,callback);
});

app.post('/transHistoryRecAccMeth',(req,res)=>{
    let login = req.body.login;
    let RecAccount = req.body.RecAccount;
    let token = req.body.token;
    let callback = function(value,status){
        res.status(status).send(value);
    };
    database.transHistoryRecAccMeth(login,RecAccount,token,callback);
});

app.post('/cards',(req,res)=>{
    let login = req.body.login;
    let idAcc = req.body.idAcc;
    let token = req.body.token;
    let callback = function(value,status){
        res.status(status).send(value);
    };
    database.cardsMeth(login,idAcc,token,callback);
});

app.post('/cardinfo',(req,res)=>{
    let login = req.body.login;
    let token = req.body.token;
    let idCard = req.body.idCard;
    let callback = function(value,status){
        res.status(status).send(value);
    };
    database.cardInfoMeth(login,token,idCard,callback);
});
app.post('/changePassword',(req,res)=>{
    let login = req.body.login;
    let token = req.body.token;
    let newpassword = req.body.newpassword;
    let oldpassword = req.body.oldpassword;

    let callback = function(value,status){
        res.status(status).send(value);
    };
    database.changePasswordMeth(login,token,oldpassword,newpassword,callback);
});

app.post('/blockcard',(req,res)=>{
    let login = req.body.login;
    let token = req.body.token;
    let idCard = req.body.idCard;
    let callback = function(value,status){
        res.status(status).send(value);
    };
    database.blockcardMeth(login,token,idCard,callback);
});

app.post('/newTransaction',(req,res)=>{
    let login = req.body.login;
    let token = req.body.token;
    let amount = req.body.amount;
    let idAccFrom = req.body.idAccFrom;
    let RecAccNum = req.body.RecAccNum;
    let callback = function(value,status){
        res.status(status).send(value);
    };
    database.newTransactionMeth(login,token,amount,idAccFrom,RecAccNum,callback);
});

console.log("ide server");
app.listen(3000);