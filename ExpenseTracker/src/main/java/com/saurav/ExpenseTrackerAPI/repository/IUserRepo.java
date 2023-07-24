package com.saurav.ExpenseTrackerAPI.repository;

import com.saurav.ExpenseTrackerAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepo extends JpaRepository<User,Integer> {
    User findFirstByUserEmail(String newEmail);
}
