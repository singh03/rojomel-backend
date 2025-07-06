package com.example.rojomelbackend.service.Finance;

import com.example.rojomelbackend.model.dto.Finance.FinanceDto;
import com.example.rojomelbackend.util.Finance.enums.FinanceType;
import org.springframework.data.domain.Page;

public interface FinanceService {

    FinanceDto createFinanceEntry(FinanceDto financeDto);

    FinanceDto findById(Long id);

    FinanceDto updateFinanceEntry(FinanceDto dto);

    void deleteFinanceEntry(Long id);

    Page<FinanceDto> getAllFinanceEntries(String query, int pageNumber, int pageSize, FinanceType financeType);
}
