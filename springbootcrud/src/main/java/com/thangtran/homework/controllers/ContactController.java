package com.thangtran.homework.controllers;

import com.thangtran.homework.model.ContactInformation;
import com.thangtran.homework.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    ContactService contactService;

    //The function receives a GET request, processes it and gives back a list of Contact as a response.
    @GetMapping
    public ResponseEntity<List<ContactInformation>> getAllContacts() {
        List<ContactInformation> contacts = contactService.getContacts();
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    //The function receives a GET request with id in the url path, processes it and returns a Contact with the specified Id
    @GetMapping({"/{contactId}"})
    public ResponseEntity<ContactInformation> getContact(@PathVariable Long contactId) {
        return new ResponseEntity<>(contactService.getContactById(contactId), HttpStatus.OK);
    }

    //The function receives a POST request, processes it, creates a new Contact and saves it to the database and returns a resource link to the created todo.
    @PostMapping
    public ResponseEntity<ContactInformation> saveContact(@RequestBody ContactInformation contact) {
        ContactInformation contact1 = contactService.insert(contact);
        return new ResponseEntity<>(contact1, HttpStatus.CREATED);
    }

    //The function receives a PUT request, updates the Contact with the specified Id and returns the updated Todo
    @PutMapping({"/{contactId}"})
    public ResponseEntity<ContactInformation> updateContact(@PathVariable("contactId") Long contactId, @RequestBody ContactInformation contactInformation) {
        contactService.updateContact(contactId, contactInformation);
        return new ResponseEntity<>(contactService.getContactById(contactId), HttpStatus.OK);
    }

    //The function receives a DELETE request, deletes the Contact with the specified Id.
    @DeleteMapping({"/{contactId}"})
    public ResponseEntity<ContactInformation> deleteContact(@PathVariable("contactId") Long contactId) {
        contactService.deleteContact(contactId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping({"/search"})
    public ResponseEntity<List<ContactInformation>> search(@RequestParam(value = "firstName", required = false) String firstName, @RequestParam(value = "lastName", required = false) String lastName) {
        List<ContactInformation> contactInformations = contactService.search(firstName, lastName);
        return new ResponseEntity<>(contactInformations, HttpStatus.OK);
    }

    @GetMapping({"/paginated/{pageNo}/{pageSize}"})
    public ResponseEntity<List<ContactInformation>> getAllContactsPaginated(@PathVariable int pageNo, @PathVariable int pageSize) {
        System.out.println(pageNo + " -- " + pageSize);
        List<ContactInformation> contacts = contactService.findPaginated(pageNo, pageSize);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }
}
