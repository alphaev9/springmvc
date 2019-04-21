package com.alpha.springmvc.domain;

import com.alpha.springmvc.validation.Step2;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.util.Objects;

public class Cooperator {
    @Size(max = 5, message = "simple name is preferred,be sure not beyond 5 character", groups = {Default.class, Step2.class})
    private String name;
    @Email(message = "illegal email format", groups = {Default.class, Step2.class})
    private String email;

    public Cooperator() {
    }

    public Cooperator(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cooperator that = (Cooperator) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(email, that.email);
    }

}
