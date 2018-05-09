package com.github.signed.sandboxes.spring.beanvalidation;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

public class AddressBookEntryTO {

    @NotEmpty
    public String name;

    @Valid
    public PhoneNumberTO phoneNumber;

    public PhoneNumberTO getPhoneNumber() {
        return phoneNumber;
    }

}
