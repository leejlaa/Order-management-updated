package com.example.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.example.demo.dto.AddressDTO;
import com.example.demo.models.Address;
import com.example.demo.services.impl.AddressServiceImpl;

@RestController
@RequestMapping("/addresses")
@CrossOrigin(origins="*")
public class AddressConstoller {
    
    @Autowired
    private AddressServiceImpl addressServiceImpl;
    
    @PostMapping
    public Address createAddress(@RequestBody Address address) {
        return addressServiceImpl.createAddress(address);
    }

    @GetMapping
    public List<AddressDTO> getAddresses() {
        List<Address> addresses =  addressServiceImpl.getAddresses();

        return addresses.stream().map(address -> {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setCity(address.getCity());
            addressDTO.setCountry(address.getCountry());
            addressDTO.setPostalCode(address.getPostalCode());
            addressDTO.setStreetAddress(address.getStreetAddress());
            return addressDTO;
        }).collect(Collectors.toList());

    }

    @PutMapping("{ID}")
    public AddressDTO updateAddress(@PathVariable Long ID , Address address) {
        Address addressNew =  addressServiceImpl.updateAddress(ID, address);
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCity(addressNew.getCity());
        addressDTO.setCountry(addressNew.getCountry());
        addressDTO.setPostalCode(addressNew.getPostalCode());
        addressDTO.setStreetAddress(addressNew.getStreetAddress());
        return addressDTO;
    }

    
  
}
