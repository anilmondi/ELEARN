package com.cts.elearn.dto;

import lombok.Data;

@Data
public class UserRegisteredEvent {
    private Long userId;
    private String email;
}
