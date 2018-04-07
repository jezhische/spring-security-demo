package com.jezh.springsecurity.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// CRM = Customer Relationship Management
public class CrmUser {

    @NotNull(message="is required")
    @Size(min=1, message="more than one charascter is required")
    private String userName;

    @NotNull(message="is required")
    @Size(min=5, message="at least 5 characters is required")
    private String password;

    public CrmUser() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
