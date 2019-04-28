package com.alpha.springmvc.core.conversion;

import com.alpha.springmvc.core.TypeConversionController;
import com.alpha.springmvc.domain.Address;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.core.convert.converter.Converter;

public class AddressConverter implements Converter<String, Address> {
    private static final Logger log = LogManager.getLogger(TypeConversionController.class);
    @Override
    public Address convert(String s) {
        log.info("AddressConverter is working....");
        Address address = new Address();
        String[] values = s.split(";");
        address.setStreet(values[0]);
        address.setNumber(Integer.valueOf(values[1]));
        return address;
    }

}
