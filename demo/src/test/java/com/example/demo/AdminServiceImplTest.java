package com.example.demo;
import static org.junit.jupiter.api.Assertions.*;
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

import com.example.demo.dto.AdminDTO;
import com.example.demo.models.Admin;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.impl.AdminServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    private Admin admin;

    @BeforeEach
    public void setup() {
        admin = new Admin();
        admin.setID(1L);
        admin.setUserName("admin");
        admin.setPassword("password");
    }

    @Test
    public void testCreateAdmin() {
        when(adminRepository.findByUserName("admin")).thenReturn(null);
        when(adminRepository.save(admin)).thenReturn(admin);

        Admin createdAdmin = adminService.createAdmin(admin);

        assertNotNull(createdAdmin);
        assertEquals("admin", createdAdmin.getUserName());
    }

    @Test
    public void testGetAdmin() {
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));

        Admin foundAdmin = adminService.getAdmin(1L);

        assertNotNull(foundAdmin);
        assertEquals(1L, foundAdmin.getID());
    }

    @Test
    public void testGetAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        admins.add(admin);

        when(adminRepository.findAll()).thenReturn(admins);

        List<Admin> foundAdmins = adminService.getAllAdmins();

        assertNotNull(foundAdmins);
        assertEquals(1, foundAdmins.size());
        assertEquals(admin, foundAdmins.get(0));
    }

    @Test
    public void testExists() {
        when(adminRepository.findByUserName("admin")).thenReturn(admin);

        boolean exists = adminService.exists("admin");

        assertTrue(exists);
    }

    @Test
    public void testGetAdmins() {
        // Create sample admins
        Admin admin1 = new Admin();
        admin1.setID(1L);
        admin1.setUserName("admin1");
        admin1.setEmail("admin1@example.com");
        admin1.setRole("admin");

        Admin admin2 = new Admin();
        admin2.setID(2L);
        admin2.setUserName("admin2");
        admin2.setEmail("admin2@example.com");
        admin2.setRole("admin");

        List<Admin> admins = new ArrayList<>();
        admins.add(admin1);
        admins.add(admin2);

        // Mock adminRepository.findAll() to return sample admins
        when(adminRepository.findAll()).thenReturn(admins);

        // Call the method under test
        List<AdminDTO> adminDTOs = adminService.getAdmins();

        // Verify the result
        assertNotNull(adminDTOs);
        assertEquals(2, adminDTOs.size());

        AdminDTO adminDTO1 = adminDTOs.get(0);
        assertEquals(1L, adminDTO1.getID());
        assertEquals("admin1", adminDTO1.getUserName());
        assertEquals("admin1@example.com", adminDTO1.getEmail());
        assertEquals("admin", adminDTO1.getRole());

        AdminDTO adminDTO2 = adminDTOs.get(1);
        assertEquals(2L, adminDTO2.getID());
        assertEquals("admin2", adminDTO2.getUserName());
        assertEquals("admin2@example.com", adminDTO2.getEmail());
        assertEquals("admin", adminDTO2.getRole());
    }
    @Test
    public void testUpdateAdmin() {
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));
        when(adminRepository.save(admin)).thenReturn(admin);

        Admin updatedAdmin = adminService.updateAdmin(1L, admin);

        assertNotNull(updatedAdmin);
        assertEquals(admin, updatedAdmin);
    }

    @Test
    public void testDeleteAdmin() {
        when(adminRepository.findByUserName("admin")).thenReturn(admin);

        boolean deleted = adminService.deleteAdmin("admin");

        assertTrue(deleted);
    }

    @Test
    public void testValidateAdmin() {
        when(adminRepository.findByUserNameAndPassword("admin", "password")).thenReturn(admin);

        boolean isValid = adminService.validateAdmin("admin", "password");

        assertTrue(isValid);
    }

    @Test
    public void testGetAdminByUserName() {
        when(adminRepository.findByUserName("admin")).thenReturn(admin);

        Admin foundAdmin = adminService.getAdminByUserName("admin");

        assertNotNull(foundAdmin);
        assertEquals("admin", foundAdmin.getUserName());
    }

    @Test
    public void testJSONAdmins() {
        // Mocking adminRepository.findAllAdminsJson() to return a sample JSON string
        String sampleJson = "{\"admins\":[{\"id\":1,\"userName\":\"admin1\",\"password\":\"password1\"},{\"id\":2,\"userName\":\"admin2\",\"password\":\"password2\"}]}";
        when(adminRepository.findAllAdminsJson()).thenReturn(sampleJson);

        // Testing JSONAdmins() method
        String jsonAdmins = adminService.JSONAdmins();

        assertEquals(sampleJson, jsonAdmins);
    }


    @Test
    public void testExportAdmins() {
        // Creating sample admins
        Admin admin1 = new Admin();
        admin1.setID(1L);
        admin1.setUserName("admin1");
        admin1.setPassword("password1");

        Admin admin2 = new Admin();
        admin2.setID(2L);
        admin2.setUserName("admin2");
        admin2.setPassword("password2");

        List<Admin> admins = new ArrayList<>();
        admins.add(admin1);
        admins.add(admin2);

        // Mocking adminRepository.findAll() to return sample admins
        when(adminRepository.findAll()).thenReturn(admins);

        // Testing exportAdmins() method
        String exportedAdmins = adminService.exportAdmins();

        // Verifying the format of exportedAdmins JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(exportedAdmins);
            // Add assertions to verify the structure or content of the JSON if necessary
            assertNotNull(jsonNode);
            assertTrue(jsonNode.has("admins"));
            assertTrue(jsonNode.get("admins").isArray());
            assertEquals(2, jsonNode.get("admins").size());
        } catch (Exception e) {
            fail("Failed to parse exportedAdmins JSON string");
        }

    }
}
