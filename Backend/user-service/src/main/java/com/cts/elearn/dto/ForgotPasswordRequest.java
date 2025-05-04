package com.cts.elearn.dto;

public class ForgotPasswordRequest {
    private String contactNumber;
    private String newPassword;

    // Getters & Setters
    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
