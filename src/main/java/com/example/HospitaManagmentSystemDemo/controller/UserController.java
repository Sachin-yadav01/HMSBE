package com.example.HospitaManagmentSystemDemo.controller;

import com.example.HospitaManagmentSystemDemo.Entitys.User;
import com.example.HospitaManagmentSystemDemo.service.UserService;

import org.antlr.v4.runtime.InterpreterRuleContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {
     @Autowired
     UserService userService;

    @GetMapping
    public String Hello(){
        return "hello i am hello api sachin created me ";
    }


    @PostMapping
    public String createEmployee(@RequestBody User user) {

             userService.regiter(user);

        return " Register successful";
    }



    @GetMapping("/alluser")
    public ResponseEntity<List<User>> GetUser(){

         List<User>  userlist= userService.getUsers();
        System.out.println(userlist);

        return ResponseEntity.ok(userlist)  ;
    }

}
