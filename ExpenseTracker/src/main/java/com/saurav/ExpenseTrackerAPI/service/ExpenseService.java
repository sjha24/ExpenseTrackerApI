package com.saurav.ExpenseTrackerAPI.service;
import com.saurav.ExpenseTrackerAPI.model.Expense;
import com.saurav.ExpenseTrackerAPI.model.User;
import com.saurav.ExpenseTrackerAPI.repository.IExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    IExpenseRepo expenseRepo;

    public String createExpense(Expense expense) {
        expenseRepo.save(expense);
        return "Expense Created Successfull";
    }
    public List<Expense> getAllExpenseByUser(User user) {
        return expenseRepo.findByExpenseUser(user);
    }

    public String updateExpenseByExpenseId(User user, Integer expenseId, Expense expense) {
        List<Expense>expenseList = expenseRepo.findByExpenseUser(user);
        for(Expense oldExpense : expenseList){
            if(oldExpense.getExpenseId().equals(expenseId)){
                oldExpense.setExpenseId(expense.getExpenseId());
                oldExpense.setExpenseTitle(expense.getExpenseTitle());
                oldExpense.setExpenseDescription(expense.getExpenseDescription());
                oldExpense.setExpensePrice(expense.getExpensePrice());
                oldExpense.setExpenseDate(expense.getExpenseDate());
                oldExpense.setExpenseMonth(expense.getExpenseMonth());
                expenseRepo.save(oldExpense);
                return "Expense Update Successfull";
            }
        }
        return "Please Enter valid User ID";
    }

    public String deleteExpenseByUserID(User user, Integer expenseID) {
       List<Expense>expenseList = expenseRepo.findByExpenseUser(user);
       for(Expense expense : expenseList) {
           if(expense.getExpenseId().equals(expenseID)){
               expenseRepo.deleteById(expenseID);
               return "Delete successfull";
           }
       }
       return "This Expense Id is Not Found from Your Expense History";
    }

    public String getExpenseByMonthly(User user, String month) {
        List<Expense>expenseList = expenseRepo.findByExpenseUser(user);
        Double totalExpenditure =0.0;
        for (Expense expense : expenseList){
            if(expense.getExpenseMonth().equals(month)) {
                Double price = expense.getExpensePrice();
                totalExpenditure = totalExpenditure + price;
            }
        }
        return String.valueOf(totalExpenditure);
    }

}
