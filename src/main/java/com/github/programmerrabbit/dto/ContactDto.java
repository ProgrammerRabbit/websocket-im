package com.github.programmerrabbit.dto;

import com.github.programmerrabbit.dao.entity.Contact;
import com.github.programmerrabbit.utils.BeanUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Rabbit on 2016/12/17.
 */
@Data
public class ContactDto implements Serializable {
    private int id;
    private int oneUserId;
    private int anotherUserId;

    public Contact toEntity() {
        Contact contact = new Contact();
        BeanUtils.copyProperties(this, contact);
        return contact;
    }

    public static ContactDto fromEntity(Contact contact) {
        ContactDto contactDto = new ContactDto();
        BeanUtils.copyProperties(contact, contactDto);
        return contactDto;
    }
}
