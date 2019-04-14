package com.alpha.springmvc.domain;

import java.util.Date;
import java.util.List;

public class Backlog {
    private String title;
    private String description;
    private Date dueTime;
    private Address address;
    private List<Cooperator> cooperators;
    private BacklogState state;

    public Backlog() {
    }

    public Backlog(String title, String description, Date dueTime, Address address, List<Cooperator> cooperators, BacklogState state) {
        this.title = title;
        this.description = description;
        this.dueTime = dueTime;
        this.address = address;
        this.cooperators = cooperators;
        this.state = state;
    }

    public Backlog(String title) {
        this.title = title;
    }

    public Backlog(String title, String description, Date dueTime, BacklogState state) {
        this.title = title;
        this.description = description;
        this.dueTime = dueTime;
        this.state = state;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Cooperator> getCooperators() {
        return cooperators;
    }

    public void setCooperators(List<Cooperator> cooperators) {
        this.cooperators = cooperators;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public BacklogState getState() {
        return state;
    }

    public void setState(BacklogState state) {
        this.state = state;
    }

}
