package com.thangtran.homework;

import com.thangtran.homework.model.ContactInformation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CrudApplicationTests {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private TestH2Repository h2Repository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/contact");
    }

    @Test
    public void testAddProduct() {
//        Product product = new Product("headset", 2, 7999);
//        Product response = restTemplate.postForObject(baseUrl, product, Product.class);
//        assertEquals("headset", response.getName());
//        assertEquals(1, h2Repository.findAll().size());
        ContactInformation contactInformation = ContactInformation.builder()
                .id(1L)
                .name("Thang Tran")
                .emailAddress("trhthang0401@gmail.com")
                .telephoneNumber("0826645657")
                .postalAddress("Lam Dong")
                .build();

        ContactInformation response = restTemplate.postForObject(baseUrl,contactInformation, ContactInformation.class);

        assertEquals("Thang Tran", response.getName());
        assertEquals(1, h2Repository.findAll().size());
    }

    @Test
    @Sql(statements = "INSERT INTO CONTACT_INFORMATION (ID, NAME, EMAIL_ADDRESS, POSTAL_ADDRESS, TELEPHONE_NUMBER) VALUES (1, 'Thang Tran', 'trhthang0401@gmail.com', 'Lam Dong', '0826645657')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM CONTACT_INFORMATION WHERE EMAIL_ADDRESS='trhthang0401@gmail.com'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetProducts() {
        List<ContactInformation> contacts = restTemplate.getForObject(baseUrl, List.class);
        assertEquals(1, contacts.size());
        assertEquals(1, h2Repository.findAll().size());
    }

    @Test
    @Sql(statements = "INSERT INTO CONTACT_INFORMATION (ID, NAME, EMAIL_ADDRESS, POSTAL_ADDRESS, TELEPHONE_NUMBER) VALUES (1, 'Thang Tran', 'trhthang0401@gmail.com', 'Lam Dong', '0826645657')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM CONTACT_INFORMATION WHERE EMAIL_ADDRESS='trhthang0401@gmail.com'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindProductById() {
        ContactInformation contact = restTemplate.getForObject(baseUrl + "/{id}", ContactInformation.class, 1);
        assertAll(
                () -> assertNotNull(contact),
                () -> assertEquals(1, contact.getId()),
                () -> assertEquals("Thang Tran", contact.getName())
        );
    }

//    @Test
//    @Sql(statements = "INSERT INTO CONTACT_INFORMATION (ID, NAME, EMAIL_ADDRESS, POSTAL_ADDRESS, TELEPHONE_NUMBER) VALUES (1, 'Thang Tran', 'trhthang0401@gmail.com', 'Lam Dong', '0826645657')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(statements = "DELETE FROM CONTACT_INFORMATION WHERE ID=1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
//    public void testUpdateProduct(){
//        ContactInformation contactInformation = ContactInformation.builder()
//                .id(1L)
//                .name("Thang Tran")
//                .emailAddress("thangTest@gmail.com")
//                .telephoneNumber("0826645657")
//                .postalAddress("Lam Dong")
//                .build();
//        restTemplate.put(baseUrl+"/{id}", contactInformation, 1);
//        ContactInformation contactFromDB = h2Repository.findById(1L).get();
//        assertAll(
//                () -> assertNotNull(contactFromDB),
//                () -> assertEquals("Lam Dong", contactFromDB.getPostalAddress())
//        );
//    }

    @Test
    @Sql(statements = "INSERT INTO CONTACT_INFORMATION (ID, NAME, EMAIL_ADDRESS, POSTAL_ADDRESS, TELEPHONE_NUMBER) VALUES (1, 'Thang Tran', 'trhthang0401@gmail.com', 'Lam Dong', '0826645657')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testDeleteProduct(){
        int recordCount=h2Repository.findAll().size();
        assertEquals(1, recordCount);
        restTemplate.delete(baseUrl+"/{id}", 1);
        assertEquals(0, h2Repository.findAll().size());

    }
}
