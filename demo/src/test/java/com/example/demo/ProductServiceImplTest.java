package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import org.junit.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.models.Admin;
import com.example.demo.models.Product;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.impl.ProductServiceImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.demo.models.Product;


@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

   

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private ProductServiceImpl productService;


    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

    @Test
    public void testCreateProduct() {

        // Prepare test data
        Admin admin = new Admin();
        admin.setID(1L);

        Product product = new Product();
        product.setProductName("Test Product");
        product.setPrice(20.0);
        product.setAdmin(admin);

       
        
        when(adminRepository.findByID(admin.getID())).thenReturn(admin);

        // Mock product saving
        when(productRepository.save(product)).thenReturn(product);

        // Invoke method under test
        Product createdProduct = productService.createProduct(product);

        // Verify interactions
        verify(adminRepository).findByID(admin.getID());
        verify(productRepository).save(product);

        // Add assertions
        assertNotNull(createdProduct);
        assertEquals(product.getProductName(), createdProduct.getProductName());
        assertEquals(product.getPrice(), createdProduct.getPrice());
        assertEquals(product.getAdmin(), createdProduct.getAdmin());
    }

    @Test
    public void testFindByProductID() {
        // Prepare test data
        // ...

            Long productId = 1L;
            Product product = new Product();
            product.setProductID(productId);

            // Stubbing behavior for findByProductID method of productRepository
            when(productRepository.findByProductID(productId)).thenReturn(product);

            // Test the findByProductID method
            Product result = productService.findByProductID(productId);

            // Verify the result
            assertNotNull(result);
            assertEquals(productId, result.getProductID());
    }

    @Test
    public void testDeleteByProductID() {
        // Prepare test data
        Long productId = 1L;
        Product product = new Product();
        product.setProductID(productId);

        // Stubbing behavior for findByProductID method of productRepository
        when(productRepository.findByProductID(productId)).thenReturn(product);

        // Test the deleteByProductID method
        boolean result = productService.deleteByProductID(productId);

        // Verify the result
        assertTrue(result);

        // Verify that productRepository.delete was called with the correct product
        verify(productRepository).delete(product);
    }

    @Test
    public void testJSONProducts() {
        // Prepare test data
        String jsonProducts = "[{\"id\":1,\"name\":\"Product 1\",\"price\":20.0}," +
                              "{\"id\":2,\"name\":\"Product 2\",\"price\":30.0}]";

        // Mock product repository behavior
        when(productRepository.findAllProductsJson()).thenReturn(jsonProducts);

        // Invoke method under test
        String result = productService.JSONProducts();

        // Verify interactions
        verify(productRepository).findAllProductsJson();

        // Verify result
        assertEquals(jsonProducts, result);
    }

    @Test
    public void testGetProduct() {
        // Prepare test data
        Long productId = 1L;
        Product product = new Product();
        product.setProductID(productId);
        product.setProductName("Test Product");
        product.setPrice(20.0);

        // Mock product repository behavior
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Invoke method under test
        Product result = productService.getProduct(productId);

        // Verify interactions
        verify(productRepository).findById(productId);

        // Verify result
        assertEquals(product, result);
    }

    @Test
    public void testGetProductByProductName() {
        // Prepare test data
        String productName = "Test Product";
        Product product = new Product();
        product.setProductID(1L);
        product.setProductName(productName);
        product.setPrice(20.0);

        // Mock product repository behavior
        when(productRepository.findByProductName(productName)).thenReturn(product);

        // Invoke method under test
        Product result = productService.getProductByProductName(productName);

        // Verify interactions
        verify(productRepository).findByProductName(productName);

        // Verify result
        assertEquals(product, result);
    }

    @Test
    public void testUpdateProduct() throws ParseException {
        // Prepare test data
        Long productId = 1L;
        Product existingProduct = new Product();
        existingProduct.setProductID(productId);
        existingProduct.setProductName("Existing Product");
        existingProduct.setPrice(20.0);
        existingProduct.setQuantity(10);
        existingProduct.setAvailabilityDate(formatter.parse("10-05-2024")); // Convert integer to LocalDate
        existingProduct.setReleaseDate(formatter.parse("01-01-2024"));

        Product updatedProduct = new Product();
        updatedProduct.setProductName("Updated Product");
        updatedProduct.setPrice(25.0);
        updatedProduct.setQuantity(15);
        updatedProduct.setAvailabilityDate(formatter.parse("01-06-2024")); // Convert integer to LocalDate
        updatedProduct.setReleaseDate(formatter.parse("01-01-2024"));

        // Mock product repository behavior
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct); // Assuming save operation returns the updated product

        // Invoke method under test
        Product result = productService.updateProduct(productId, updatedProduct);

        // Verify interactions
        verify(productRepository).findById(productId);
        verify(productRepository).save(existingProduct);

        // Verify result
        assertEquals(updatedProduct.getProductName(), result.getProductName());
        assertEquals(updatedProduct.getPrice(), result.getPrice());
        assertEquals(updatedProduct.getQuantity(), result.getQuantity());
        assertEquals(updatedProduct.getAvailabilityDate(), result.getAvailabilityDate());
        assertEquals(updatedProduct.getReleaseDate(), result.getReleaseDate());
    }

    @Test
    public void testDeleteProduct() {
        // Prepare test data
        String productName = "Test Product";
        Product existingProduct = new Product();
        existingProduct.setProductName(productName);

        // Mock product repository behavior
        when(productRepository.findByProductName(productName)).thenReturn(existingProduct);

        // Invoke method under test
        boolean result = productService.deleteProduct(productName);

        // Verify interactions
        verify(productRepository).findByProductName(productName);
        verify(productRepository).delete(existingProduct);

        // Verify result
        assertTrue(result);
    }

    @Test
    public void testDeleteProductWithQuantity_SufficientQuantity() {
        // Prepare test data
        String productName = "Test Product";
        int availableQuantity = 10; // Available quantity in the existing product
        int quantityToRemove = 5; // Quantity to remove

        Product existingProduct = new Product();
        existingProduct.setProductName(productName);
        existingProduct.setQuantity(availableQuantity);

        // Mock product repository behavior
        when(productRepository.findByProductName(productName)).thenReturn(existingProduct);

        // Invoke method under test
        boolean result = productService.deleteProduct(productName, quantityToRemove);

        // Verify interactions
        verify(productRepository).findByProductName(productName);
        verify(productRepository).save(existingProduct);

        // Verify result
        assertTrue(result);
        assertEquals(availableQuantity - quantityToRemove, existingProduct.getQuantity());
    }

    @Test
    public void testGetProducts() {
        // Prepare test data
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Product 1", 20.0, new Date(), new Date(), 10, new Admin(), new ArrayList<>()));
        products.add(new Product(2L, "Product 2", 30.0, new Date(), new Date(), 15, new Admin(), new ArrayList<>()));

        // Mock product repository behavior
        when(productRepository.findAll()).thenReturn(products);

        // Invoke method under test
        List<Product> result = productService.getProducts();

        // Verify interactions
        verify(productRepository).findAll();

        // Verify result
        assertEquals(products.size(), result.size());
        assertTrue(result.containsAll(products));
    }
}
