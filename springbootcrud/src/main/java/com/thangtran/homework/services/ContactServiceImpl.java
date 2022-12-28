package com.thangtran.homework.services;

import com.thangtran.homework.model.ContactInformation;
import com.thangtran.homework.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService{
    @Autowired
    ContactRepository contactRepository;

    @Override
    public List<ContactInformation> getContacts() {
        List<ContactInformation> contacts = new ArrayList<>();
        contactRepository.findAll().forEach(contacts::add);;
        return contacts;
    }

    @Override
    public ContactInformation getContactById(Long id) {
        ContactInformation contactInformation = contactRepository.findById(id).get();
        return contactInformation;
    }

    @Override
    public ContactInformation insert(ContactInformation contact) {
        return contactRepository.save(contact);
    }

    @Override
    public ContactInformation updateContact(Long id, ContactInformation contact) {
        ContactInformation contactFromDb = contactRepository.findById(id).get();
        System.out.println(contactFromDb.toString());
        contactFromDb.setEmailAddress(contact.getEmailAddress());
        contactFromDb.setName(contact.getName());
        contactFromDb.setPostalAddress(contact.getPostalAddress());
        contactFromDb.setTelephoneNumber(contact.getTelephoneNumber());
        return contactRepository.save(contactFromDb);
    }

    @Override
    public void deleteContact(Long contactId) {
        contactRepository.deleteById(contactId);
    }

    @Override
    public List<ContactInformation> search(String firstName, String lastName) {
        List<ContactInformation> contactInformations = contactRepository.search(firstName,lastName);
        return contactInformations;
    }

    @Override
    public List<ContactInformation> findPaginated(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<ContactInformation> pageResult = contactRepository.findAll(paging);
        return pageResult.toList();
    }
}