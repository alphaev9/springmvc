package com.alpha.springmvc.core.conversion;

import com.alpha.springmvc.core.TypeConversionController;
import com.alpha.springmvc.domain.Address;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.format.Formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class AddressFormatter implements Formatter<Address> {
    private static final Logger log = LogManager.getLogger(TypeConversionController.class);

    @Override
    public Address parse(String s, Locale locale) throws ParseException {
        log.info("AddressFormatter is working....");
        Address address = new Address();
        String[] values = s.split(";");
        address.setStreet(values[0]);
        address.setNumber(Integer.valueOf(values[1]));
        return address;
    }

    @Override
    public String print(Address address, Locale locale) {
        return address.getStreet() + " " + address.getNumber();
    }
}
