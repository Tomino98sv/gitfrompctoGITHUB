let mysql = require('mysql');
const TokenGenerator = require('uuid-token-generator');
const tokgen = new TokenGenerator(); // Default is a 128-bit token encoded in base58
let tokens=[];

let con = mysql.createConnection({
    host:"itsovy.sk",
    user:"glbank",
    password:"password",
    port:3306,
    database:"glbank"
});

const checkLoginMeth = (username,password,callback) => {
    console.log("checkLogin "+username+" "+password);
    con.connect(function(err){
    console.log("Connection to database has been estabilished"); 
    let sql="SELECT * FROM loginclient where login like '"+username+"' and password like MD5('"+password+"')"; 
    con.query(sql,(err,result) => {
        if(err) throw err;
        if(result.length==0){
            console.log('login '+username+' password '+password+' login like this not exists');
            callback("{Wrong credintials!}",401);
        }else{
                tokens=tokens.filter(person => person.login != username); // vsetky okrem mna ponechat
                console.log("login je v databaze");
                let token=tokgen.generate();
                let obj=new Object();
                obj.login=result[0].login;
                obj.token=token;
                tokens.push(obj);
                // console.log(JSON.stringify(obj));
                callback(JSON.stringify(obj),200);
        }
    })
    });
}

const logoutMeth = (login,token,callback) => {
    console.log("logout method "+login+" "+token);
    if(tokens.find(
        person => (person.login == login && person.token == token)
        )
    ){
        tokens=tokens.filter(
            person => (person.login != login && person.token != token)
        );
        let mess = new Object();
    
        mess.message="User has been logged off";
        callback(JSON.stringify(mess),200);
    }else{
        let mess = new Object();
        mess.message="Wrong credintials!";
        callback(JSON.stringify(mess),401);
    }
}

const userinfoMeth = (login,token,callback) => {


    if(tokens.find(
        person => (person.login == login && person.token == token)
        )
    ){
     
    con.connect(function(err){
    console.log("Connection to database has been estabilished"); 
    let sql="SELECT client.fname,client.lname,client.email,client.id from loginclient inner join client on client.id=loginclient.idc where loginclient.login like '"+login+"'"; 
    con.query(sql,(err,res) => {
        if(err) throw err;
        if(res.length==0){
            callback("{Could not get user info!}",403);
        }else{
                console.log(res[0].fname+" "+res[0].lname+" "+" "+res[0].email+" "+res[0].id);
                let obj=new Object();
                obj.fname=res[0].fname;
                obj.lname=res[0].lname;
                obj.email=res[0].email;
                obj.id=res[0].id;
                callback(JSON.stringify(obj),200);
        }
    })
    });

    }else{
        let mess = new Object();
        mess.message = "Wrong credintials!";
        callback(JSON.stringify(mess),401);
    }
}

const getAccountsMeth = (login,token,callback) => {


    if(tokens.find(
        person => (person.login == login && person.token == token)
        )
    ){
     
    con.connect(function(err){
    console.log("Connection to database has been estabilished"); 
    let sql="select * from account where account.idc="+
    "(select client.id from loginclient inner join client on "+
    "client.id=loginclient.idc where loginclient.login like '"+login+"')"; 
    con.query(sql,(err,res) => {
        if(err) throw err;
        if(res.length==0){
            callback("{Could not get any account!}",403);
        }else{
                callback(JSON.stringify(res),200);
        }
    })
    });

    }else{
        let mess = new Object();
        mess.message = "Wrong credintials!";
        callback(JSON.stringify(mess),401);
    }
}

const getAccountInfoMeth = (login,token,accNum,callback) => {


    if(tokens.find(
        person => (person.login == login && person.token == token)
        )
    ){
     
    con.connect(function(err){
    console.log("Connection to database has been estabilished"); 
    let sql="select * from account where account.idc="+
    "(select client.id from loginclient inner join client on "+
    "client.id=loginclient.idc where loginclient.login like '"+login+"') and account.accnum like '"+accNum+"'"; 
    con.query(sql,(err,res) => {
        if(err) throw err;
        if(res.length==0){
            callback("{Could not get account info!}",403);
        }else{
                let obj = new Object();
                obj.id=res[0].id;
                obj.idc=res[0].idc;
                obj.AccNum=res[0].AccNum;
                obj.amount=res[0].amount;
                callback(JSON.stringify(obj),200);
        }
    })
    });

    }else{
        let mess = new Object();
        mess.message = "Wrong credintials!";
        callback(JSON.stringify(mess),401);
    }
}


const transHistoryMeth = (login,idAcc,token,callback) => {


    if(tokens.find(
        person => (person.login == login && person.token == token)
        )
    ){
     
    con.connect(function(err){
    console.log("Connection to database has been estabilished"); 
    let sql="select * from transaction where idAcc like '"+idAcc+"'"; 
    con.query(sql,(err,res) => {
        if(err) throw err;
        if(res.length==0){
            callback("{Could not get any transHistory info!}",403);
        }else{
                callback(JSON.stringify(res),200);
        }
    })
    });

    }else{
        let mess = new Object();
        mess.message = "Wrong credintials!";
        callback(JSON.stringify(mess),401);
    }
}

const transHistoryRecAccMeth = (login,RecAccount,token,callback) => {


    if(tokens.find(
        person => (person.login == login && person.token == token)
        )
    ){
     
    con.connect(function(err){
    console.log("Connection to database has been estabilished"); 
    let sql="select * from transaction where RecAccount like '"+RecAccount+"'"; 
    con.query(sql,(err,res) => {
        if(err) throw err;
        if(res.length==0){
            callback("{Could not get any transHistory with this RecAccount!}",403);
        }else{
                callback(JSON.stringify(res),200);
        }
    })
    });

    }else{
        let mess = new Object();
        mess.message = "Wrong credintials!";
        callback(JSON.stringify(mess),401);
    }
}

const cardsMeth = (login,idAcc,token,callback) => {


    if(tokens.find(
        person => (person.login == login && person.token == token)
        )
    ){
     
    con.connect(function(err){
    console.log("Connection to database has been estabilished"); 
    let sql="select * from card where ida like '"+idAcc+"'"; 
    con.query(sql,(err,res) => {
        if(err) throw err;
        if(res.length==0){
            callback("{Could not find any card!}",403);
        }else{
                callback(JSON.stringify(res),200);
        }
    })
    });

    }else{
        let mess = new Object();
        mess.message = "Wrong credintials!";
        callback(JSON.stringify(mess),401);
    }
}

const cardInfoMeth = (login,token,idCard,callback) => {


    if(tokens.find(
        person => (person.login == login && person.token == token)
        )
    ){
     
    con.connect(function(err){
    console.log("Connection to database has been estabilished"); 
    let sql="select * from card where id like '"+idCard+"'"; 
    con.query(sql,(err,res) => {
        if(err) throw err;
        if(res.length==0){
            callback("{Could not get any cardInfo!}",403);
        }else{
                let obj = new Object();
                 obj.id = res[0].id;
                 obj.ida = res[0].ida;
                 obj.PIN = res[0].PIN;
                 obj.expirem = res[0].expirem;
                 obj.expirey = res[0].expirey;
                 obj.Active = res[0].Active;
                callback(JSON.stringify(obj),200);
        }
    })
    });

    }else{
        let mess = new Object();
        mess.message = "Wrong credintials!";
        callback(JSON.stringify(mess),401);
    }
}

const cardTransMeth = (login,token,idCard,callback) => {


    if(tokens.find(
        person => (person.login == login && person.token == token)
        )
    ){
     
    con.connect(function(err){
    console.log("Connection to database has been estabilished"); 
    let sql="select * from cardtrans where idCard like '"+idCard+"'"; 
    con.query(sql,(err,res) => {
        if(err) throw err;
        if(res.length==0){
            callback("{Could not get any cardTransactions!}",403);
        }else{
            
                callback(JSON.stringify(res),200);
        }
    })
    });

    }else{
        let mess = new Object();
        mess.message = "Wrong credintials!";
        callback(JSON.stringify(mess),401);
    }
}

const changePasswordMeth = (login,token,oldpassword,newpassword,callback) => {


    if(tokens.find(
        person => (person.login == login && person.token == token)
        )
    ){
     
    con.connect(function(err){
    console.log("Connection to database has been estabilished"); 
    let sql=" UPDATE loginclient set password=MD5('"+newpassword+"')"+
    " where loginclient.login like '"+login+"' and loginclient.password "+
    "like MD5('"+oldpassword+"')"; 
    con.query(sql,(err,res) => {
        if(err) throw err;
        if(res.affectedRows==0){
            callback("{Could not change any password!}",403);
        }else{
                console.log(res.affectedRows + " record(s) updated");
                callback("{OK}",200);
        }
    })
    });

    }else{
        let mess = new Object();
        mess.message = "Wrong credintials!";
        callback(JSON.stringify(mess),401);
    }
}

const blockcardMeth = (login,token,idCard,callback) => {


    if(tokens.find(
        person => (person.login == login && person.token == token)
        )
    ){
     
    con.connect(function(err){
    console.log("Connection to database has been estabilished"); 
    let sql="UPDATE card set active="+0+" where card.id="+idCard; 
    con.query(sql,(err,res) => {
        if(err) throw err;
        if(res.affectedRows==0){
            callback("{Could not block card!}",403);
        }else{
                console.log(res.affectedRows + " record(s) updated");
                callback("{Blocked}",200);
        }
    })
    });

    }else{
        let mess = new Object();
        mess.message = "Wrong credintials!";
        callback(JSON.stringify(mess),401);
    }
}


module.exports={
    checkLoginMeth,
    logoutMeth,
    userinfoMeth,
    getAccountsMeth,
    getAccountInfoMeth,
    transHistoryMeth,
    transHistoryRecAccMeth,
    cardsMeth,
    cardInfoMeth,
    cardTransMeth,
    changePasswordMeth,
    blockcardMeth
};