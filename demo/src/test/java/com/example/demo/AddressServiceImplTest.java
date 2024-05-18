package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.models.Address;
import com.example.demo.repositories.AddressRepository;
import com.example.demo.services.impl.AddressServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Test
    public void testCreateAddress() {
        Address address = new Address();
        address.setStreetAddress("123 Main St");
        address.setCity("City");
        address.setCountry("Country");
        address.setPostalCode(12345);

        when(addressRepository.findByStreetAddress("123 Main St")).thenReturn(Optional.empty());
        when(addressRepository.save(address)).thenReturn(address);

        Address createdAddress = addressService.createAddress(address);

        assertNotNull(createdAddress);
        assertEquals("123 Main St", createdAddress.getStreetAddress());
    }

    @Test
    public void testGetAddress() {
        Address address = new Address();
        address.setAddressID(1L);
        address.setStreetAddress("123 Main St");
        address.setCity("City");
        address.setCountry("Country");
        address.setPostalCode(12345);

        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

        Address foundAddress = addressService.getAddress(1L);

        assertNotNull(foundAddress);
        assertEquals(1L, foundAddress.getAddressID());
    }

    @Test
    public void testGetAddressByStreetAddress() {
        Address address = new Address();
        address.setStreetAddress("123 Main St");
        address.setCity("City");
        address.setCountry("Country");
        address.setPostalCode(12345);

        when(addressRepository.findByStreetAddress("123 Main St")).thenReturn(Optional.of(address));

        Optional<Address> foundAddress = addressService.getAddressByStreetAddress("123 Main St");

        assertTrue(foundAddress.isPresent());
        assertEquals("123 Main St", foundAddress.get().getStreetAddress());
    }

    @Test
    public void testUpdateAddress() {
        Address existingAddress = new Address();
        existingAddress.setAddressID(1L);
        existingAddress.setStreetAddress("123 Main St");
        existingAddress.setCity("City");
        existingAddress.setCountry("Country");
        existingAddress.setPostalCode(12345);

        Address updatedAddress = new Address();
        updatedAddress.setAddressID(1L);
        updatedAddress.setStreetAddress("456 Updated St");
        updatedAddress.setCity("Updated City");
        updatedAddress.setCountry("Updated Country");
        updatedAddress.setPostalCode(54321);

        when(addressRepository.findById(1L)).thenReturn(Optional.of(existingAddress));
        when(addressRepository.save(existingAddress)).thenReturn(updatedAddress);

        Address result = addressService.updateAddress(1L, updatedAddress);

        assertNotNull(result);
        assertEquals("456 Updated St", result.getStreetAddress());
        assertEquals("Updated City", result.getCity());
        assertEquals("Updated Country", result.getCountry());
        assertEquals(54321, result.getPostalCode());
    }

    @Test
    public void testDeleteAddress() {
        Address existingAddress = new Address();
        existingAddress.setAddressID(1L);
        existingAddress.setStreetAddress("123 Main St");
        existingAddress.setCity("City");
        existingAddress.setCountry("Country");
        existingAddress.setPostalCode(12345);

        when(addressRepository.findById(1L)).thenReturn(Optional.of(existingAddress));

        boolean result = addressService.deleteAddress(1L);

        assertTrue(result);
        verify(addressRepository, times(1)).delete(existingAddress);
    }

    @Test
    public void testGetAddresses() {
        Address address1 = new Address();
        address1.setAddressID(1L);
        address1.setStreetAddress("123 Main St");
        address1.setCity("City");
        address1.setCountry("Country");
        address1.setPostalCode(12345);

        Address address2 = new Address();
        address2.setAddressID(2L);
        address2.setStreetAddress("456 Second St");
        address2.setCity("City");
        address2.setCountry("Country");
        address2.setPostalCode(54321);

        List<Address> addresses = Arrays.asList(address1, address2);

        when(addressRepository.findAll()).thenReturn(addresses);

        List<Address> result = addressService.getAddresses();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getAddressID());
        assertEquals("123 Main St", result.get(0).getStreetAddress());
        assertEquals(2L, result.get(1).getAddressID());
        assertEquals("456 Second St", result.get(1).getStreetAddress());
    }

}
