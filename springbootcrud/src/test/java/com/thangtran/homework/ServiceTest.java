package com.thangtran.homework;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.thangtran.homework.model.ContactInformation;
import com.thangtran.homework.repositories.ContactRepository;
import com.thangtran.homework.services.ContactServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)

public class ServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactServiceImpl contactService;

    @Test
    public void getAllContactsTest() {
        List<ContactInformation> contactInformations = new ArrayList<>();
        ContactInformation contac1 =  new ContactInformation();
        contac1.setName("Thang Tran");


        ContactInformation contac2 = new ContactInformation();
        contac2.setName("Thang nao do");

        contactInformations.add(contac1);
        contactInformations.add(contac2);

        lenient().when(contactService.getContacts()).thenReturn(contactInformations);

        assertEquals(2, contactInformations.size());
    }

    @Test
    public void getContactByIdTest() {
        Optional<ContactInformation> contac1 = Optional.ofNullable(ContactInformation.builder()
                .id(1L)
                .name("Thang Tran")
                .emailAddress("trhthang0401@gmail.com")
                .telephoneNumber("0826645657")
                .postalAddress("Lam Dong")
                .build());

        lenient().when(contactRepository.findById(1L)).thenReturn(contac1);
        assertEquals("trhthang0401@gmail.com", contactService.getContactById(1L).getEmailAddress());

    }

    @Test
    public void insertTest() {
         ContactInformation contac1 =  ContactInformation.builder()
                  .id(1L)
                  .name("Thang Tran")
                  .emailAddress("trhthang0401@gmail.com")
                  .telephoneNumber("0826645657")
                  .postalAddress("Lam Dong")
                  .build();

        lenient().when(contactService.insert(contac1)).thenReturn(contac1);

        assertEquals("Thang Tran", contac1.getName() );

    }

    @Test
    public void updateTest() {
        ContactInformation contac1 =  ContactInformation.builder()
                .id(1L)
                .name("Thang Tran")
                .emailAddress("trhthang0401@gmail.com")
                .telephoneNumber("0826645657")
                .postalAddress("Lam Dong")
                .build();

        ContactInformation contac2 =  ContactInformation.builder()
                .name("Thang Tran")
                .emailAddress("test@gmail.com")
                .telephoneNumber("0826645657")
                .postalAddress("Lam Dong")
                .build();

        lenient().when(contactRepository.findById(1L)).thenReturn(Optional.ofNullable(contac1));

        contactService.updateContact(1L, contac2);
        assertEquals("test@gmail.com", contac1.getEmailAddress() );

    }

    @Test
    public void deleteTest() {
        Optional<ContactInformation> contac1 = Optional.ofNullable(ContactInformation.builder()
                .id(1L)
                .name("Thang Tran")
                .emailAddress("trhthang0401@gmail.com")
                .telephoneNumber("0826645657")
                .postalAddress("Lam Dong")
                .build());

        contactService.deleteContact(contac1.get().getId());

        Mockito.verify(contactRepository).deleteById(contac1.get().getId());

    }

    @Test
    public void searchTest() {
        List<ContactInformation> contactInformations = new ArrayList<>();
        ContactInformation contac1 =  new ContactInformation();
        contac1.setName("Thang Tran");

        contactInformations.add(contac1);

        lenient().when(contactService.search("Thang", "Tran")).thenReturn(contactInformations);
        assertEquals(1, contactInformations.size() );
    }
}
