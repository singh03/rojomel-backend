package com.example.rojomelbackend.mapper.Finance;

import com.example.rojomelbackend.model.dto.Finance.FinanceDto;
import com.example.rojomelbackend.model.entity.Finance.FinanceEntry;
import org.springframework.stereotype.Component;

@Component
public class FinanceMapper {


    public FinanceDto convert(FinanceEntry financeEntry){
        return FinanceDto.Builder.financeDto()
                .withId(financeEntry.getId())
                .withExpense(financeEntry.getExpense())
                .withIncome(financeEntry.getIncome())
                .withDateCreated(financeEntry.getDateCreated())
                .withDateUpdated(financeEntry.getDateUpdated())
                .withUserId(financeEntry.getUser().getId())
                .withUserName(financeEntry.getUser().getName())
                .withBalance(financeEntry.getBalance())
                .build();
    }


    public FinanceEntry convert(FinanceDto financeDto){
        return FinanceEntry.Builder.financeEntry()
                .withId(financeDto.getId())
                .withExpense(financeDto.getExpense())
                .withIncome(financeDto.getIncome())
                .withDateCreated(financeDto.getDateCreated())
                .withDateUpdated(financeDto.getDateUpdated())
                .withBalance(financeDto.getBalance())
                .withDeleted(false)
                .build();
    }

}
