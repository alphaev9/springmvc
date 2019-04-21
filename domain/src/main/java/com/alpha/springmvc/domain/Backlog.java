package com.alpha.springmvc.domain;

import com.alpha.springmvc.validation.MeetingBacklogConstraint;
import com.alpha.springmvc.validation.Step1;
import com.alpha.springmvc.validation.Step2;

import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.validation.groups.Default;
import java.util.Date;
import java.util.List;

@MeetingBacklogConstraint
public class Backlog {
    @NotBlank(message = "must specify a backlog title", groups = {Default.class, Step1.class})
    private String title;

    private String description;
    @Future(message = "dueTime is expired!", groups = {Default.class, Step1.class})
    private Date dueTime;
    @Valid
    private Address address;
    @Valid
    @NotEmpty(message = "at least specify one cooperator", groups = {Default.class, Step2.class})
    private List<Cooperator> cooperators;
    @NotNull(message = "backlog should be at definite state", groups = {Default.class, Step2.class})
    private BacklogState state;

    /**
     * validation based on property accessor
     */
    @Size(min = 3, max = 10)
    public String getDescription() {
        return description;
    }

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
