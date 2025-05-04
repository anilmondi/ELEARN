package com.cts.elearn.dto;

public class LoginRequest {
    private String contactNumber;
    private String password;

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest(contactNumber=" + contactNumber + ", password=" + password + ")";
    }
}
