package com.saurav.ExpenseTrackerAPI.service;
import com.saurav.ExpenseTrackerAPI.model.User;
import com.saurav.ExpenseTrackerAPI.repository.IUserRepo;
import com.saurav.ExpenseTrackerAPI.service.hashPaasword.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class AuthenticateService {
    @Autowired
    IUserRepo userRepo;
    public boolean authenticate(String email,String password) throws NoSuchAlgorithmException {
        User user = userRepo.findFirstByUserEmail(email);
        if (user != null) {
            if (user.getUserEmail() != null && user.getUserPassword() != null) {
                String userEmail = user.getUserEmail();
                String userPassword = user.getUserPassword();
                String Password = PasswordEncrypter.encryptPassword(password);
                return email.equals(userEmail) && userPassword.equals(Password);
            }
        }
        return false;
    }
}