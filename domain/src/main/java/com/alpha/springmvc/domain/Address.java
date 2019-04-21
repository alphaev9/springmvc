package com.alpha.springmvc.domain;

import com.alpha.springmvc.validation.Step2;

import javax.validation.constraints.Negative;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;

public class Address {
    @NotBlank(message = "street is non-option", groups = {Default.class, Step2.class})
    private String street;
    @Negative(message = "negative is illegal", groups = {Default.class, Step2.class})
    private Integer number;

    public Address() {
    }

    public Address(String street, Integer number) {
        this.street = street;
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Address)) {
            return false;
        }
        Address address = (Address) obj;
        return number.equals(address.getNumber()) && street.equals(address.getStreet());
    }
}
