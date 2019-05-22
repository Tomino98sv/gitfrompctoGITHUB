const http=require('http');
const express=require('express');
const database=require('./database');
const cors = require('cors'); 


const app=express();
app.use(express.json());
app.use(cors());

app.get('/test',function(req,res){
    console.log('new request: /test');
    res.status(200).send();
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

console.log("ide server");
app.listen(3000);