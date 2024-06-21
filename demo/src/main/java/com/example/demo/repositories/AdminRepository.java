package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.AdminDTO;
import com.example.demo.models.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {


    @Query("SELECT a FROM Admin a WHERE a.ID = :ID")
    Admin findByID(@Param("ID") Long ID);

    @Query("SELECT a FROM Admin a WHERE a.userName = :userName")
    Admin findByUserName(String userName);

    @Query("SELECT a FROM Admin a WHERE a.userName = :userName AND a.password = :password")
    Admin findByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);

    @Query(value = "SELECT jsonb_agg(jsonb_build_object('ID', a.admin_id, 'userName', a.user_name, 'email', a.email, 'role' , a.role, 'createBy', a.created_by_admin_id)) FROM Admins a;", nativeQuery = true)
    String findAllAdminsJson();

    @Query("SELECT a.id FROM Admin a WHERE a.email = :email")
    Long findAdminIdByEmail(String email);



} 

    

