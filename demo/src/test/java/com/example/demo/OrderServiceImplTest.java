package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.models.Admin;
import com.example.demo.models.Customer;
import com.example.demo.models.Order;
import com.example.demo.models.OrderProduct;
import com.example.demo.models.Product;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.OrderProductRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.impl.OrderServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;


@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderProductRepository orderProductRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

  
    private  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");



    @Test
    public void testFindByCustomerID() {
        Long customerID = 1L;
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderRepository.findByCustomerID(customerID)).thenReturn(orders);

        List<Order> result = orderService.findByCustomerID(customerID);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(orderRepository, times(1)).findByCustomerID(customerID);
    }

    @Test
    public void testGetOrdersByCustomer() {
        Long customerID = 1L;
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderRepository.findByCustomerID(customerID)).thenReturn(orders);

        List<Order> result = orderService.getOrdersByCustomer(customerID);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(orderRepository, times(1)).findByCustomerID(customerID);
    }

    @Test
    public void testGetOrder() {
        Long id = 1L;
        Order order = new Order();
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        Order result = orderService.getOrder(id);

        assertNotNull(result);
        assertEquals(order, result);
        verify(orderRepository, times(1)).findById(id);
    }

    @Test
    public void testGetOrder_NotFound() {
        Long id = 1L;
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        Order result = orderService.getOrder(id);

        assertNull(result);
        verify(orderRepository, times(1)).findById(id);
    }

    @Test
    public void testUpdateOrder() {
        Long id = 1L;
        Order existingOrder = new Order();
        Order updatedOrder = new Order();
        updatedOrder.setCustomer(new Customer());
        when(orderRepository.findById(id)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(existingOrder)).thenReturn(existingOrder);

        Order result = orderService.updateOrder(id, updatedOrder);

        assertNotNull(result);
        assertEquals(existingOrder.getCustomer(), updatedOrder.getCustomer());
        verify(orderRepository, times(1)).findById(id);
        verify(orderRepository, times(1)).save(existingOrder);
    }

    @Test
    public void testUpdateOrder_NotFound() {
        Long id = 1L;
        Order updatedOrder = new Order();
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        Order result = orderService.updateOrder(id, updatedOrder);

        assertNull(result);
        verify(orderRepository, times(1)).findById(id);
        verify(orderRepository, times(0)).save(any(Order.class));
    }
    @Test
    public void testPrintAllOrdersToCSV() throws IOException {
        String fileName = "test_orders.csv";
        String filePath = System.getProperty("user.dir") + File.separator + fileName;
        File file = new File(filePath);
        file.deleteOnExit(); // Ensure the file is deleted after the test

        // Mock order data
        Admin admin = new Admin();
        Product product = new Product(1L, "Product1", 10.0, new Date(), new Date(), 100, admin, new ArrayList<>());
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);

        List<OrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(orderProduct);

        Order order = new Order();
        order.setOrderID(1L);
        order.setOrderProducts(orderProducts);

        List<Order> orders = new ArrayList<>();
        orders.add(order);

        // Mock repository response
        when(orderRepository.findAllOrdersWithProducts()).thenReturn(orders);

        // Call the method to test
        orderService.printAllOrdersToCSV(fileName);

        // Verify the result
        assertTrue(file.exists());

        // Optionally, you can also verify the contents of the file
        try (FileReader reader = new FileReader(filePath)) {
            char[] buffer = new char[1024];
            int length = reader.read(buffer);
            String fileContent = new String(buffer, 0, length);

            String expectedContent = "Order ID,Product ID,Product Name\n1,1,Product1\n";
            assertEquals(expectedContent, fileContent);
        }
    }
    @Test
    public void testAllPreviousOrders() throws JsonProcessingException {
        Long customerId = 1L;

        // Mock order data
        Admin admin = new Admin();
        Product product = new Product(1L, "Product1", 10.0, new Date(), new Date(), 100, admin, new ArrayList<>());
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);

        List<OrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(orderProduct);

        Order order = new Order();
        order.setOrderID(1L);
        order.setOrderProducts(orderProducts);

        List<Order> orders = new ArrayList<>();
        orders.add(order);

        // Mock repository response
        when(orderRepository.findAllOrdersWithProductsByCustomerID(customerId)).thenReturn(orders);
        when(objectMapper.writeValueAsString(orderProduct)).thenReturn("{\"productId\":1,\"productName\":\"Product1\"}");

        // Call the method to test
        List<String> result = orderService.allPreviousOrders(customerId);

        // Verify the result
        List<String> expected = new ArrayList<>();
        expected.add("{\"productId\":1,\"productName\":\"Product1\"}");

        assertEquals(expected, result);
    }
    @Test
    public void testCreateOrderForCustomer() throws Exception{
        // Create a sample customer
        Customer customer = new Customer();
        customer.setID(1L); // Corrected method name
        customer.setFirstName("John");
        customer.setLastName("Doe");
        
    
        // Create a sample order
        Order order = new Order();
        order.setOrderID(1L);
    
        // Mock customer repository behavior
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
    
        // Mock order repository behavior
        when(orderRepository.save(order)).thenReturn(order);
    
        // Call the method under test
        Order createdOrder = orderService.createOrderForCustomer(1L, order);
    
        // Verify that the customer repository is called with the correct customer ID
        verify(customerRepository, times(1)).findById(1L);
    
        // Verify that the order repository is called to save the order
        verify(orderRepository, times(1)).save(order);
    
        // Verify that the order returned by the method under test is the same as the one saved
        assertEquals(order, createdOrder);
    }

    
    
}

    

