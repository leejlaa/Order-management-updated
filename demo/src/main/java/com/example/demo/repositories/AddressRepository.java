package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    // Custom query to find an address by street address
    @Query("SELECT a FROM Address a WHERE a.streetAddress = :streetAddress")
    Optional<Address> findByStreetAddress(String streetAddress);

    // Custom query to find an address by ID
    @Query("SELECT a FROM Address a WHERE a.addressID = :addressID")
    Optional<Address> findById(Long addressID);
    


    
}

  
    

    
