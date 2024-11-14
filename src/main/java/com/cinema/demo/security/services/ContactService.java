package com.cinema.demo.security.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cinema.demo.entities.Contact;
import com.cinema.demo.entities.UserEntity;

public interface ContactService {
    // save contacts
    Contact save(Contact contact);

    // update contact
    Contact update(Contact contact);

    // get contacts
    List<Contact> getAll();

    // get contact by id

    Contact getById(String id);

    // delete contact

    void delete(String id);

    // search contact
    Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, UserEntity user);

    Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, UserEntity user);

    Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order,
            UserEntity user);

    // get contacts by userId
    List<Contact> getByUserId(String userId);

    Page<Contact> getByUser(UserEntity user, int page, int size, String sortField, String sortDirection);

}
