package com.saurav.ExpenseTrackerAPI.model.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupOutput {
    private Boolean signupStatus;
    private String signupStatusMessage;
}
