package com.example.demo.services;

import java.util.List;
import java.util.*;

import com.example.demo.models.Address;

public interface AddressService {

    public Address createAddress(Address address);
    public Address getAddress(Long id);
    public Optional<Address> getAddressByStreetAddress(String streetAddress);
    public Address updateAddress(Long id, Address address);
    public boolean deleteAddress(Long id);
    public List<Address> getAddresses(); 
    
}