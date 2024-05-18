package com.example.demo.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Address;
import com.example.demo.repositories.AddressRepository;
import com.example.demo.services.AddressService;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressRepository addressRepository;

    public Address createAddress(Address address) {
            if(addressRepository.findByStreetAddress(address.getStreetAddress()).isPresent() && addressRepository.findByStreetAddress(address.getStreetAddress()).equals(address)){
                System.out.println("Address already exists");
                return null;
            } 
        
        
        return addressRepository.save(address);
    }

    public Address getAddress(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    public Optional<Address> getAddressByStreetAddress(String streetAddress) {
        return addressRepository.findByStreetAddress(streetAddress);
    }

    public Address updateAddress(Long id, Address address) {
        Address existingAddress = addressRepository.findById(id).orElse(null);
        if (existingAddress != null) {
            existingAddress.setCountry(address.getCountry());
            existingAddress.setCity(address.getCity());
            existingAddress.setPostalCode(address.getPostalCode());
            existingAddress.setStreetAddress(address.getStreetAddress());
            return addressRepository.save(existingAddress);
        }
        return null;
    }

    public boolean deleteAddress(Long id) {
        Address existingAddress = addressRepository.findById(id).orElse(null);
        if (existingAddress != null) {
            addressRepository.delete(existingAddress);
            return true;
        }
        return false;
    }

    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    
    
}
