package com.saurav.ExpenseTrackerAPI.service;
import com.saurav.ExpenseTrackerAPI.model.Expense;
import com.saurav.ExpenseTrackerAPI.model.User;
import com.saurav.ExpenseTrackerAPI.model.dto.SignInInput;
import com.saurav.ExpenseTrackerAPI.model.dto.SignupOutput;
import com.saurav.ExpenseTrackerAPI.repository.IUserRepo;
import com.saurav.ExpenseTrackerAPI.service.hashPaasword.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserService {
    @Autowired
    IUserRepo userRepo;

    @Autowired
    ExpenseService expenseService;
    public SignupOutput signupUser(User user) throws NoSuchAlgorithmException {
        boolean signupStatus = true;
        String signupStatusMessage = null;
        boolean isExistEmail = false;
        String newEmail = user.getUserEmail();
        //check if the User Email already exist :-
        if(newEmail == null) {
            signupStatus = false;
            signupStatusMessage = "Invalid Email";
            return new SignupOutput(false, signupStatusMessage);
        }
        // check if this User email already exist or not
        User existingUser = userRepo.findFirstByUserEmail(newEmail);
        if(existingUser != null) {
            signupStatusMessage = "Email already registered !!!";
            signupStatus = false;
            return new SignupOutput(false, signupStatusMessage);
        }
        //hash the password or encrypt the password
        String encryptPassword = PasswordEncrypter.encryptPassword(user.getUserPassword());
        //save the User with the new encrypted password
        user.setUserPassword(encryptPassword);
        userRepo.save(user);
        signupStatusMessage = "User Registered Successfully";
        return new SignupOutput(true,signupStatusMessage);
    }
    public String signInUser(SignInInput signInInput) throws NoSuchAlgorithmException{
        boolean signInStatus = true;
        String signInStatusMessage = null;
        String signInEmail = signInInput.getEmail();
        if(signInEmail == null){
            signInStatus = false;
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;
        }
        User existingUser = userRepo.findFirstByUserEmail(signInEmail);
        if(existingUser == null) {
            signInStatusMessage = "Email Not registered !!!";
            signInStatus = false;
            return signInStatusMessage;
        }
        //match password--->
        String encryptPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
        if(existingUser.getUserPassword().equals(encryptPassword)){
            signInStatusMessage = "SignIn Successfully";
        }else{
            signInStatusMessage = "Invalid email or password";
        }
        return signInStatusMessage;

    }

    public String createExpense(Expense expense) {
        return expenseService.createExpense(expense);
    }

    public List<Expense> getAllExpense(String userEmail) {
        User user = userRepo.findFirstByUserEmail(userEmail);
        if(expenseService.getAllExpenseByUser(user) != null) {
            return expenseService.getAllExpenseByUser(user);
        }
        return null;
    }

    public String updateExpenseByExpenseId(String userEmail, Integer expenseId, Expense expense) {
        User user = userRepo.findFirstByUserEmail(userEmail);
        return expenseService.updateExpenseByExpenseId(user,expenseId,expense);
    }

    public String deleteExpenseByExpenseId(Integer expenseID, String userEmail) {
        User user = userRepo.findFirstByUserEmail(userEmail);
        if(user.getUserEmail().equals(userEmail)) {
            return expenseService.deleteExpenseByUserID(user,expenseID);
        }
        return "Please Enter valid Identity";
    }

    public String getExpenseByMonthly(String userEmail,String month) {
        User user = userRepo.findFirstByUserEmail(userEmail);
        return expenseService.getExpenseByMonthly(user,month);
    }
}
