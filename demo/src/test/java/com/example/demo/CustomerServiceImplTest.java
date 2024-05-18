package com.example.demo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.models.Address;
import com.example.demo.models.Customer;
import com.example.demo.repositories.AddressRepository;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.services.impl.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private Address address;

    @BeforeEach
    public void setup() {
        customer = new Customer();
        customer.setID(1L);
        customer.setUserName("user1");
        customer.setFirstName("John");
        customer.setLastName("Doe");

        address = new Address();
        address.setAddressID(1L);
        address.setStreetAddress("123 Main St");
        address.setCity("City");
        address.setCountry("Country");
        address.setPostalCode(12345);
        customer.setCurrentResidence(address);
    }

    @Test
    public void testCreateCustomer() {
        when(addressRepository.findByStreetAddress("123 Main St")).thenReturn(Optional.of(address));
        when(adminRepository.findByID(anyLong())).thenReturn(null);
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer createdCustomer = customerService.createCustomer(1L, customer);

        assertNotNull(createdCustomer);
        assertEquals("user1", createdCustomer.getUserName());
        assertEquals("123 Main St", createdCustomer.getCurrentResidence().getStreetAddress());
    }
    @Test
    public void testGetCustomer() {
        // Stubbing behavior for findById method
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Test the getCustomer method
        Customer foundCustomer = customerService.getCustomer(1L);

        // Verify the result
        assertNotNull(foundCustomer);
        assertEquals(1L, foundCustomer.getID());
        assertEquals("user1", foundCustomer.getUserName());
    }

    @Test
    public void testUpdateCustomer() {
        // Stubbing behavior for findById method
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Stubbing behavior for save method
        when(customerRepository.save(any())).thenReturn(customer);

        // Test the updateCustomer method
        Customer updatedCustomer = new Customer();
        updatedCustomer.setID(1L);
        updatedCustomer.setFirstName("UpdatedFirstName");
        updatedCustomer.setLastName("UpdatedLastName");
        updatedCustomer.setEmail("updated@example.com");

        Customer result = customerService.updateCustomer(1L, updatedCustomer);

        // Verify the result
        assertNotNull(result);
        assertEquals("UpdatedFirstName", result.getFirstName());
        assertEquals("UpdatedLastName", result.getLastName());
        assertEquals("updated@example.com", result.getEmail());
    }

    @Test
    public void testDeleteCustomer() {
        // Stubbing behavior for findByUserName method
        when(customerRepository.findByUserName("user1")).thenReturn(customer);

        // Test the deleteCustomer method
        boolean result = customerService.deleteCustomer("user1");

        // Verify the result
        assertTrue(result);
        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    public void testJSONCustomers() {
        // Stubbing behavior for findAllCustomersAsJson method
        when(customerRepository.findAllCustomersAsJson()).thenReturn("jsonString");

        // Test the JSONCustomers method
        String result = customerService.JSONCustomers();

        // Verify the result
        assertEquals("jsonString", result);
    }

    @Test
    public void testGetCustomers() {
        // Stubbing behavior for findAll method
        List<Customer> customers = new ArrayList<Customer>();
        customers.add(customer);
        when(customerRepository.findAll()).thenReturn(customers);

        // Test the getCustomers method
        List<Customer> result = customerService.getCustomers();

        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getUserName());
    }

    @Test
    public void testValidateCustomer() {
        // Stubbing behavior for findByUserNameAndPassword method
        when(customerRepository.findByUserNameAndPassword("user1", "password")).thenReturn(customer);

        // Test the validateCustomer method
        boolean result = customerService.validateCustomer("user1", "password");

        // Verify the result
        assertTrue(result);
    }

    @Test
    public void testCustomerExists() {
        // Stubbing behavior for findByUserName method
        when(customerRepository.findByUserName("user1")).thenReturn(customer);

        // Test the customerExists method
        boolean result = customerService.customerExists("user1");

        // Verify the result
        assertTrue(result);
    }

}
