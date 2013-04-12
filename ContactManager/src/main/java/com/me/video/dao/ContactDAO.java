package com.me.video.dao;

import java.util.List;
import com.me.video.domain.Contact;

public interface ContactDAO {

    public void addContact(Contact contact);

    public List<Contact> listContact();

    public void removeContact(Integer id);
}