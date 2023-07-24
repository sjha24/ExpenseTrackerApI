package com.saurav.ExpenseTrackerAPI.repository;

import com.saurav.ExpenseTrackerAPI.model.Expense;
import com.saurav.ExpenseTrackerAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IExpenseRepo extends JpaRepository<Expense,Integer> {
    List<Expense> findByExpenseMonth(String month);
    List<Expense> findByExpenseUser(User user);
}
