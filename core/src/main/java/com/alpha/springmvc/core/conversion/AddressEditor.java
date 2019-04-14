package com.alpha.springmvc.core.conversion;

import com.alpha.springmvc.core.TypeConversionController;
import com.alpha.springmvc.domain.Address;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.beans.PropertyEditorSupport;

public class AddressEditor extends PropertyEditorSupport {
    private static final Logger log = LogManager.getLogger(TypeConversionController.class);
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        log.info("AddressEditor is working....");
        Address address = new Address();
        String[] values = text.split(";");
        address.setStreet(values[0]);
        address.setNumber(Integer.valueOf(values[1]));
        setValue(address);
    }
}
