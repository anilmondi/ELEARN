package com.cts.elearn.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponse {
    private int userId;
    private String name;
    private String contactNumber;  // ✅ Ensure this field is present
    private String email;
}
