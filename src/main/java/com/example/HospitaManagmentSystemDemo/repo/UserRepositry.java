package com.example.HospitaManagmentSystemDemo.repo;

import com.example.HospitaManagmentSystemDemo.Entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositry  extends JpaRepository<User,Long> {
}
