package com.thangtran.homework.services;

import com.thangtran.homework.model.ContactInformation;


import java.util.List;

public interface ContactService {
    List<ContactInformation> getContacts();

    ContactInformation getContactById(Long id);

    ContactInformation insert(ContactInformation contact);

    ContactInformation updateContact(Long id, ContactInformation todo);

    void deleteContact(Long contactId);

    List<ContactInformation> search(String firstName, String lastName);

    List<ContactInformation> findPaginated(int pageNo, int pageSize);

}
