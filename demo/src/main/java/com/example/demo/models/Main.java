package com.example.demo.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerResponse.SseBuilder;

import com.example.demo.dto.OrderProductDTO;
import com.example.demo.dto.PlaceOrderRequest;
import com.example.demo.services.impl.AddressServiceImpl;
import com.example.demo.services.impl.AdminServiceImpl;
import com.example.demo.services.impl.CustomerServiceImpl;
import com.example.demo.services.impl.OrderProductServiceImpl;
import com.example.demo.services.impl.OrderServiceImpl;
import com.example.demo.services.impl.ProductServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class Main {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

    Scanner scanner = new Scanner(System.in);

    @Autowired
    private AddressServiceImpl addressServiceImpl;

    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @Autowired
    private OrderProductServiceImpl orderProductServiceImpl;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    public void main() throws Exception{
        System.out.println("Welcome to the order management application!");
        System.out.println("Please select an option:");
        System.out.println("1. sign in as admin/customer");
        System.out.println("2. sign up as admin");

        int option = scanner.nextInt();
        scanner.nextLine();


        switch (option) {
            case 1:
                signInAsAdminOrCustomer();
                break;
        
            case 2:
                signUpAsAdmin();
                break;
        }
        
    }


    public void signUpAsAdmin(){

        Admin admin = new Admin();
        System.out.println("Please enter the username of the admin:");
        String adminUserName = scanner.nextLine();                        
        admin.setUserName(adminUserName);

        System.out.println("Please enter the password of the admin:");
        String adminPassword = scanner.nextLine();
        admin.setPassword(adminPassword);

        System.out.println("Please enter the email of the admin:");
        String email = scanner.nextLine();
        admin.setEmail(email);

        admin.setRole("admin");
                                
        if(adminServiceImpl.exists(adminUserName)){
            System.out.println("Admin with that username already exists!");}
        else{
            // adminServiceImpl.createAdmin(admin);
            System.out.println("Admin created successfully!");
        }

    }

    public void signInAsAdminOrCustomer() throws Exception{
        System.out.println("Please select an option:");
        System.out.println("1. sign in as admin");
        System.out.println("2. sign in as customer");

        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                Admin currentAdmin = signInAsAdmin();

                System.out.println("Welcome, " + currentAdmin.getUserName() + "!");

                System.out.println("Please select an option:");
                System.out.println("1. user administration");
                System.out.println("2. product catalog");
                System.out.println("3. list of orders");

                int option2 = scanner.nextInt();

                switch (option2) {
                    case 1:
                        userAdministation(currentAdmin);
                        break;
                
                    case 2:

                        productCatalog(currentAdmin);
                        break;

                    case 3:
                        listOrder();
                        break;
                }


                break;
        
            case 2:
                signInAsCustomer();
                break;
        }
    }

    private void listOrder() {
        scanner.nextLine(); // clean the buffer
        System.out.println("Please write the path:");
        String path = scanner.nextLine();
        orderServiceImpl.printAllOrdersToCSV(path);
    }


    private void productCatalog(Admin currentAdmin) throws ParseException {
      
        System.out.println("Please select an option:");
        System.out.println("1. add a new product");
        System.out.println("2. get a list of all products");
        System.out.println("3. delete a specific product");

        int option2 = scanner.nextInt();
        scanner.nextLine();

        switch (option2) {
            case 1:
                createNewProduct(currentAdmin);
                break;
        
            case 2:
                System.out.println(productServiceImpl.JSONProducts());
                break;

            case 3:
                deleteProduct();
                break;
        }
    }


    private void deleteProduct() {
       System.out.println("Do you want to delete a product by name or id?");
       System.out.println("1. by name");
       System.out.println("2. by id");

         int option = scanner.nextInt();
            scanner.nextLine();
        
         switch (option) {
            case 1:
                System.out.println("Please enter the name of the product you want to delete:");
                String name = scanner.nextLine();
                if(productServiceImpl.deleteProduct(name))
                    System.out.println("Successfully deleted!!");
                else
                    System.out.println("There is some error in deleting the product...");
                break;
         
            case 2:

            System.out.println(productServiceImpl.JSONProducts());

            System.out.println("Please enter the id of the product you want to delete:");
            Long id = scanner.nextLong();
            if(productServiceImpl.deleteByProductID(id))
                System.out.println("Successfully deleted!!");
            else 
                System.out.println("There is some error in deleting the product...");
                break;
         }

    }


    private void createNewProduct(Admin currentAdmin) throws ParseException {
        Product product = new Product();
        System.out.println("Enter the name of the product:");
        String name = scanner.nextLine();
        product.setProductName(name);

        System.out.println("Enter the price of the product:");
        double price = scanner.nextDouble();
        product.setPrice(price);
        scanner.nextLine();

        System.out.println("Enter the release date of the product:");
        String releaseDate = scanner.nextLine();
        product.setReleaseDate(formatter.parse(releaseDate));
        

        System.out.println("Enter the availability date of the product:");
        String availabilityDate = scanner.nextLine();
        product.setAvailabilityDate(formatter.parse(availabilityDate));
        

        System.out.println("Enter the quantity of the product:");
        int quantity = scanner.nextInt();
        product.setQuantity(quantity);

       product.setAdmin(adminServiceImpl.getAdminByUserName(currentAdmin.getUserName()));
       product.setOrderProducts(null);

       if(productServiceImpl.getProductByProductName(name) != null){
           System.out.println("Product with that name already exists!");
       }
         else{
            productServiceImpl.createProduct(product);
            System.out.println("Product created successfully!");
         }
    }


    public void userAdministation(Admin currentAdmin) throws ParseException{

        System.out.println("\nEnter 1 for adding a new user.\nEnter 2 for getting s list of all users.\nEnter 3 for deleting a specific user.");


        int option2 = scanner.nextInt();
        scanner.nextLine();

        switch (option2) {
            case 1:
                createNewUser(currentAdmin);
                break;
        
            case 2:
                System.out.println(allUsers());
                break;

            case 3:
                deleteUser();
                break;
        }
    }

    public void deleteUser(){


        System.out.println("Please enter the username of the user you want to delete:");
        String name = scanner.nextLine();

        if(adminServiceImpl.exists(name)){

        if(adminServiceImpl.deleteAdmin(name))
            System.out.println("Successfully deleted!!");
        else
            System.out.println("There is some error in deleting the admin...");
        }else if(customerServiceImpl.customerExists(name)){
            if(customerServiceImpl.deleteCustomer(name))
                System.out.println("Successfully deleted a customer!");
             else
                System.out.println("There is some error in deleting the customer...");
                        
        }

    }

    public String allUsers(){
        return adminServiceImpl.JSONAdmins()+"\n"+customerServiceImpl.JSONCustomers();
    }

    public void createNewUser(Admin currentAdmin) throws ParseException{
        System.out.println("Please select an option:");
        System.out.println("1. create a new customer");
        System.out.println("2. create a new admin");

        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                createNewCustomer(currentAdmin);
                break;
        
            case 2:
                createNewAdmin(currentAdmin);
                break;
        }
    }

    private void createNewAdmin(Admin currentAdmin) {

        Admin admin = new Admin();

        System.out.println("Please enter the username of the admin:");
        String adminUserName = scanner.nextLine();
        admin.setUserName(adminUserName);

        System.out.println("Please enter the password of the admin:");
        String adminPassword = scanner.nextLine();
        admin.setPassword(adminPassword);

        System.out.println("Please enter the email of the admin:");
        String email = scanner.nextLine();

        while(!(email.contains("@") && email.contains("."))){
            System.out.println("Invalid email address. Please enter a valid email address.");
            email = scanner.nextLine();
        }
        admin.setEmail(email);  


        admin.setRole("admin");
        admin.setCreatedByAdmin(currentAdmin);
                                
        while(adminServiceImpl.exists(adminUserName)){
            System.out.println("Admin with that username already exists!");
            System.out.println("Please enter the username of the admin:");
            adminUserName = scanner.nextLine();
            admin.setUserName(adminUserName);

        }  

        // adminServiceImpl.createAdmin(admin);
        System.out.println("Admin created successfully!");
    }


    private void createNewCustomer(Admin currentAdmin) throws ParseException {
        
        Customer customer = new Customer();

        System.out.println("Please enter the username of the customer:");
        String customerUserName = scanner.nextLine();
        customer.setUserName(customerUserName);

        System.out.println("Please enter the password of the customer:");
        String customerPassword = scanner.nextLine();
        customer.setPassword(customerPassword);

        System.out.println("Please enter the email of the customer:");
        String email = scanner.nextLine();
        while(!(email.contains("@") && email.contains("."))){
            System.out.println("Invalid email address. Please enter a valid email address.");
            email = scanner.nextLine();
        }
        customer.setEmail(email);  
        
        customer.setRole("customer");

        System.out.println("Please enter the first name of the customer:");
        String firstName = scanner.nextLine();
        customer.setFirstName(firstName);

        System.out.println("Please enter the last name of the customer:");
        String lastName = scanner.nextLine();
        customer.setLastName(lastName);

        System.out.println("Please enter the date of birth of the customer:");
        String dateOfBirth = scanner.nextLine();
        customer.setDateOfBirth(formatter.parse(dateOfBirth));

        System.out.println("Proceed to enter the address details of the customer:\nPlease enter the street:");
        String streetAddress = scanner.nextLine();

        System.out.println("Please enter the city:");
        String city = scanner.nextLine();

        System.out.println("Please enter the country:");
        String country = scanner.nextLine();

        System.out.println("Please enter the zip code:");
        int postalCode = scanner.nextInt();

        

        Address address = new Address();

         
        address.setStreetAddress(streetAddress);
        address.setCity(city);
        address.setCountry(country);
        address.setPostalCode(postalCode);

        if(addressServiceImpl.getAddressByStreetAddress(streetAddress) == null){
            addressServiceImpl.createAddress(address);
        }
        
        customer.setCurrentResidence(address);
        

        while(customerServiceImpl.customerExists(customerUserName)){
            System.out.println("Customer with that username already exists!");
            System.out.println("Please enter the username of the customer:");
            customerUserName = scanner.nextLine();
            customer.setUserName(customerUserName);

        }

        customerServiceImpl.createCustomer(customer.getID(),customer);
        System.out.println("Customer created successfully!");


    }

    public  void signInAsCustomer() throws Exception {

        System.out.println("Please enter your username:");
        String userName = scanner.nextLine();

        System.out.println("Please enter your password:");
        String password = scanner.nextLine();

        if(customerServiceImpl.customerExists(userName)){
            while(!customerServiceImpl.validateCustomer(userName, password)){
                System.out.println("Invalid username or password. Please try again.");
                System.out.println("Please enter your username:");
                userName = scanner.nextLine();
                System.out.println("Please enter your password:");
                password = scanner.nextLine();
            }
        }else{
            System.out.println("Customer does not exist. Please sign in as admin to create a new customer.");
            signInAsAdmin();
        }

        System.out.println("Welcome, " + userName + "!");


        System.out.println("Please select an option:");
        System.out.println("1. place an order");
        System.out.println("2. get a list of all previous orders you made");

        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                placeOrder(userName);
                break;
        
            case 2:
                previousOrders(userName);
                break;
        }



    }


    private void placeOrder(String userName) {

        scanner.nextLine();

        Customer customer = customerServiceImpl.getCustomerByUserName(userName);
        Long customerId = customer.getID();

        List<OrderProductDTO> orderProducts = new ArrayList<>();
        while (true) {
            System.out.println("Enter Product Name (or type 'done' to finish):");
            String productName = scanner.nextLine();
            if ("done".equalsIgnoreCase(productName)) {
                break;
            }

            System.out.println("Enter Quantity:");
            int quantity = Integer.parseInt(scanner.nextLine());

            orderProducts.add(new OrderProductDTO(productName, quantity));
        }

        PlaceOrderRequest orderRequest = new PlaceOrderRequest(customerId, orderProducts);

        try {
            // Create the order
            orderServiceImpl.createOrderForCustomer(customerId, orderRequest);
            
            // Display order confirmation
            System.out.println("Order placed successfully! Here is your order:");
            System.out.println("--------------------------------------------------");
            double totalPrice = 0.0;
            for (OrderProductDTO product : orderProducts) {
                double productPrice = productServiceImpl.getProductByProductName(product.getProductName()).getPrice(); // Fetch price from some service or database
                totalPrice += productPrice * product.getQuantity();
                System.out.printf("%s - $%.2f per unit\nQuantity: %d\n\n", product.getProductName(), productPrice, product.getQuantity());
            }
            System.out.println("--------------------------------------------------");
            System.out.printf("Total Price: $%.2f\n", totalPrice);
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            System.err.println("Error placing order: " + e.getMessage());
        }
    }




    public void previousOrders(String userName)  {
        Customer customer = customerServiceImpl.getCustomerByUserName(userName);
        try {
            System.out.println(orderServiceImpl.allPreviousOrders(customer.getID()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    public Admin signInAsAdmin(){

        System.out.println("Please enter your username:");

            String userName = scanner.nextLine();

            System.out.println("Please enter your password:");
            String password = scanner.nextLine();

            if(adminServiceImpl.exists(userName)){
    
            while(!adminServiceImpl.validateAdmin(userName, password)){
                System.out.println("Invalid username or password. Please try again.");
                System.out.println("Please enter your username:");
                userName = scanner.nextLine();
                System.out.println("Please enter your password:");
                password = scanner.nextLine();
            }
            }else{
                System.out.println("Admin does not exist. Please sign up as admin.");
                signUpAsAdmin();
            }

            System.out.println("Welcome, " + userName + "!");

            return adminServiceImpl.getAdminByUserName(userName);

        
        
    }


    
}
