package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.example.demo.models.Customer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

   
    @Query("SELECT c FROM Customer c WHERE c.userName = :userName")
    Customer findByUserName(@Param("userName") String userName);

    @Query("SELECT c FROM Customer c WHERE c.userName = :userName AND c.password = :password")
    Customer findByUserNameAndPassword(String userName, String password);

    @Query("SELECT c FROM Customer c WHERE c.ID = :ID")
    Optional<Customer> findById(@Param("ID") Long ID);

    @Query(value = "SELECT jsonb_agg(jsonb_build_object('id', c.customer_id, 'username', c.user_name,'role' , c.role, 'email', c.email, 'date of birth', c.date_of_birth, 'first name', c.first_name, 'last name', c.last_name, 'created by', c.admin_id)) FROM Customers c", nativeQuery = true)
    String findAllCustomersAsJson();

    @Query("SELECT c.ID FROM Customer c WHERE c.email = :email")
    Long findCustomerIdByEmail(String email);

    @Query("SELECT c FROM Customer c WHERE c.email = :email")
    Customer findByEmail(String email);

}

    
   
 

    

    

