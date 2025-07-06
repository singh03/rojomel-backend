package com.example.rojomelbackend.repository.Finance;

import com.example.rojomelbackend.model.entity.Finance.FinanceEntry;
import com.example.rojomelbackend.util.Finance.enums.FinanceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface FinanceCustomRepository {

    Page<FinanceEntry> getAllFinanceEntry(String query, PageRequest pageRequest, FinanceType financeType);
}
