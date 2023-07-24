package com.saurav.ExpenseTrackerAPI.controller;
import com.saurav.ExpenseTrackerAPI.model.Expense;
import com.saurav.ExpenseTrackerAPI.model.User;
import com.saurav.ExpenseTrackerAPI.model.dto.SignInInput;
import com.saurav.ExpenseTrackerAPI.model.dto.SignupOutput;
import com.saurav.ExpenseTrackerAPI.service.AuthenticateService;
import com.saurav.ExpenseTrackerAPI.service.ExpenseService;
import com.saurav.ExpenseTrackerAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AuthenticateService authenticateService;
    @PostMapping("user/signUp")
    public SignupOutput signUpUser(@RequestBody User user) throws NoSuchAlgorithmException {
        return userService.signupUser(user);
    }
    @PostMapping("user/signIn")
    public String signInInput(@RequestBody SignInInput signInInput) throws NoSuchAlgorithmException {
        return userService.signInUser(signInInput);
    }
    @PostMapping("user/expense")
    public String createExpense(@RequestParam String userEmail,@RequestParam String userPassword ,@RequestBody Expense expense) throws NoSuchAlgorithmException {
        if(authenticateService.authenticate(userEmail,userPassword)) {
            return userService.createExpense(expense);
        }
        return "Unauthorized activity deducted";
    }
    @GetMapping("user/expense")
    public List<Expense>getAllExpense(@RequestParam String userEmail, @RequestParam String userPassword) throws NoSuchAlgorithmException {
        if(authenticateService.authenticate(userEmail,userPassword)){
            return userService.getAllExpense(userEmail);
        }
       return null;
    }
    @PutMapping("user/expense/ID")
    public String updateExpenseByExpenseId(@RequestParam String userEmail, @RequestParam String userPassword, @RequestParam Integer expenseID, @RequestBody Expense expense) throws NoSuchAlgorithmException {
        if(authenticateService.authenticate(userEmail,userPassword)){
            return userService.updateExpenseByExpenseId(userEmail,expenseID,expense);
        }
        return "Unauthorized activity deducted";
    }
    @DeleteMapping("user/expense/Id")
    public String deleteExpenseByExpenseId(@RequestParam String userEmail, @RequestParam String userPassword, @RequestParam Integer expenseID) throws NoSuchAlgorithmException {
        if(authenticateService.authenticate(userEmail,userPassword)){
            return userService.deleteExpenseByExpenseId(expenseID,userEmail);
        }
        return "Unauthorized activity deducted";
    }
    @GetMapping("user/expense/month")
    public String getExpenseByMonthly(@RequestParam String userEmail, @RequestParam String userPassword,@RequestParam String month) throws NoSuchAlgorithmException {
        if(authenticateService.authenticate(userEmail,userPassword)){
            return userService.getExpenseByMonthly(userEmail,month);
        }
        return "Unauthorized activity deducted";
    }
}
