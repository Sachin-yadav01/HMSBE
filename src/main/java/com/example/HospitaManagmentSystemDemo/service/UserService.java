package com.example.HospitaManagmentSystemDemo.service;



import com.example.HospitaManagmentSystemDemo.Entitys.User;
import com.example.HospitaManagmentSystemDemo.repo.UserRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepositry userRepositry;

    public void regiter(User user) {

        userRepositry.save(user);


    }

    public List<User> getUsers() {

        return userRepositry.findAll();
    }
}
