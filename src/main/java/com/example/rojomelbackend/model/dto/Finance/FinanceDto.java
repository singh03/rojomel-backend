package com.example.rojomelbackend.model.dto.Finance;

import lombok.Data;

import java.util.Date;

@Data
public class FinanceDto {

    private Long id;

    private Double expense;

    private Double income;

    private Date dateCreated;

    private Date dateUpdated;

    private Long userId;

    private String userName;

    private double balance;

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
        UserIdStep withDateUpdated(Date dateUpdated);
    }

    public static interface UserIdStep {
        UserNameStep withUserId(Long userId);
    }

    public static interface UserNameStep {
        BalanceStep withUserName(String userName);
    }

    public static interface BalanceStep {
        BuildStep withBalance(double balance);
    }

    public static interface BuildStep {
        FinanceDto build();
    }


    public static class Builder implements IdStep, ExpenseStep, IncomeStep, DateCreatedStep, DateUpdatedStep, UserIdStep, UserNameStep, BalanceStep, BuildStep {
        private Long id;
        private Double expense;
        private Double income;
        private Date dateCreated;
        private Date dateUpdated;
        private Long userId;
        private String userName;
        private double balance;

        private Builder() {
        }

        public static IdStep financeDto() {
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
        public UserIdStep withDateUpdated(Date dateUpdated) {
            this.dateUpdated = dateUpdated;
            return this;
        }

        @Override
        public UserNameStep withUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        @Override
        public BalanceStep withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        @Override
        public BuildStep withBalance(double balance) {
            this.balance = balance;
            return this;
        }

        @Override
        public FinanceDto build() {
            FinanceDto financeDto = new FinanceDto();
            financeDto.setId(this.id);
            financeDto.setExpense(this.expense);
            financeDto.setIncome(this.income);
            financeDto.setDateCreated(this.dateCreated);
            financeDto.setDateUpdated(this.dateUpdated);
            financeDto.setUserId(this.userId);
            financeDto.setUserName(this.userName);
            financeDto.setBalance(this.balance);
            return financeDto;
        }
    }
}
