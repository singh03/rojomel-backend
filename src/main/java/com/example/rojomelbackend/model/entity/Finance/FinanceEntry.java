package com.example.rojomelbackend.model.entity.Finance;

import com.example.rojomelbackend.model.entity.customer.Customer;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "financeEntry")
public class FinanceEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    private Double expense;

    private Double income;

    private Date dateCreated;

    private Date dateUpdated;

    @Column(nullable = false)
    private Double balance;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Customer user;

    private boolean isDeleted;

    public static interface IdStep {
        ExpenseStep withId(Long id);
    }

    public static interface ExpenseStep {
        IncomeStep withExpense(Double expense);
    }

    public static interface IncomeStep {
        DateCreatedStep withIncome(Double income);
    }

    public static interface DateCreatedStep {
        DateUpdatedStep withDateCreated(Date dateCreated);
    }

    public static interface DateUpdatedStep {
        BalanceStep withDateUpdated(Date dateUpdated);
    }

    public static interface BalanceStep {
        DeletedStep withBalance(Double balance);
    }

    public static interface DeletedStep {
        BuildStep withDeleted(boolean deleted);
    }

    public static interface BuildStep {
        FinanceEntry build();
    }


    public static class Builder implements IdStep, ExpenseStep, IncomeStep, DateCreatedStep, DateUpdatedStep, BalanceStep, DeletedStep, BuildStep {
        private Long id;
        private Double expense;
        private Double income;
        private Date dateCreated;
        private Date dateUpdated;
        private Double balance;
        private boolean deleted;

        private Builder() {
        }

        public static IdStep financeEntry() {
            return new Builder();
        }

        @Override
        public ExpenseStep withId(Long id) {
            this.id = id;
            return this;
        }

        @Override
        public IncomeStep withExpense(Double expense) {
            this.expense = expense;
            return this;
        }

        @Override
        public DateCreatedStep withIncome(Double income) {
            this.income = income;
            return this;
        }

        @Override
        public DateUpdatedStep withDateCreated(Date dateCreated) {
            this.dateCreated = dateCreated;
            return this;
        }

        @Override
        public BalanceStep withDateUpdated(Date dateUpdated) {
            this.dateUpdated = dateUpdated;
            return this;
        }

        @Override
        public DeletedStep withBalance(Double balance) {
            this.balance = balance;
            return this;
        }

        @Override
        public BuildStep withDeleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        @Override
        public FinanceEntry build() {
            FinanceEntry financeEntry = new FinanceEntry();
            financeEntry.setId(this.id);
            financeEntry.setExpense(this.expense);
            financeEntry.setIncome(this.income);
            financeEntry.setDateCreated(this.dateCreated);
            financeEntry.setDateUpdated(this.dateUpdated);
            financeEntry.setBalance(this.balance);
            financeEntry.setDeleted(this.deleted);
            return financeEntry;
        }
    }
}
