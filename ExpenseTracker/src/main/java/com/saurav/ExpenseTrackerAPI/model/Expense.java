package com.saurav.ExpenseTrackerAPI.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer expenseId;
    private String expenseTitle;
    private String expenseDescription;
    private Double expensePrice;
    private LocalDate expenseDate;
    private String expenseMonth;
    @ManyToOne
    @JoinColumn(name = "fk_user_ID")
    User expenseUser;
}
