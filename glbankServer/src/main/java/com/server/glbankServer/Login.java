package com.server.glbankServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(path = "/client")
public class Login {

    @Autowired
    LoginRepository loginRepository;

    @GetMapping(path = "/insertLoginClient")
    public @ResponseBody
    String insertClient(@RequestParam String login, @RequestParam String password, @RequestParam int idc){
        loginclient newLogCl = new loginclient();
        newLogCl.setLogin(login);
        newLogCl.setPassword(password);
        newLogCl.setIdc(idc);
        loginRepository.save(newLogCl);
        return "Saved";
    }

    @GetMapping(path="/allClientLogin")
    public @ResponseBody Iterable<loginclient> getAllUsers() {
        // This returns a JSON or XML with the users
        return loginRepository.findAll();
    }

}
