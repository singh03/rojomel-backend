package com.example.rojomelbackend.service.Finance;

import com.example.rojomelbackend.mapper.Customer.CustomerMapper;
import com.example.rojomelbackend.mapper.Finance.FinanceMapper;
import com.example.rojomelbackend.model.dto.Finance.FinanceDto;
import com.example.rojomelbackend.model.entity.Finance.FinanceEntry;
import com.example.rojomelbackend.model.entity.customer.Customer;
import com.example.rojomelbackend.repository.Finance.FinanceCustomRepository;
import com.example.rojomelbackend.repository.Finance.FinanceRepository;
import com.example.rojomelbackend.service.Customer.CustomerService;
import com.example.rojomelbackend.util.Finance.enums.FinanceType;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;

    public FinanceMapper financeMapper;

    public CustomerService customerService;

    public FinanceRepository financeRepository;

    public FinanceCustomRepository financeCustomRepository;

    public CustomerMapper customerMapper;

    @Autowired
    public FinanceServiceImpl(FinanceRepository financeRepository, CustomerService customerService, FinanceMapper financeMapper, CustomerMapper customerMapper, FinanceCustomRepository financeCustomRepository) {
        this.financeRepository = financeRepository;
        this.customerMapper = customerMapper;
        this.financeMapper = financeMapper;
        this.customerService = customerService;
        this.financeCustomRepository = financeCustomRepository;
    }

    @Override
    public FinanceDto createFinanceEntry(FinanceDto financeDto) {
        Date date = new Date();
        Customer customer = entityManager.getReference(Customer.class, financeDto.getUserId());
        financeDto.setDateCreated(date);
        financeDto.setDateUpdated(date);
        FinanceEntry financeEntry = financeMapper.convert(financeDto);
        Optional<FinanceEntry> lastEntry = financeRepository
                .findTopByOrderByDateCreatedDesc();
        Double latestBalance = lastEntry.map(FinanceEntry::getBalance).orElse(0.0);
        financeEntry.setUser(customer);
        financeEntry.setBalance(latestBalance + financeDto.getIncome() - financeDto.getExpense());
        financeRepository.save(financeEntry);
        return financeDto;
    }

    @Override
    public FinanceDto findById(Long id) {
        return financeRepository.findById(id).map(financeMapper::convert).orElse(null);
    }

    @Override
    public FinanceDto updateFinanceEntry(FinanceDto dto) {
        Date date = new Date();
        FinanceEntry financeEntry = financeRepository.findById(dto.getId()).orElse(null);
        if (financeEntry != null) {
            financeEntry.setDateUpdated(date);
            financeEntry.setExpense(dto.getExpense());
            financeEntry.setIncome(dto.getIncome());
            financeRepository.save(financeEntry);
            return financeMapper.convert(financeEntry);
        }
        return null;
    }

    @Override
    public void deleteFinanceEntry(Long id) {
        financeRepository.findById(id).ifPresent(financeEntry -> {
                financeEntry.setDeleted(true);
                financeRepository.save(financeEntry);
        });
    }

    @Override
    public Page<FinanceDto> getAllFinanceEntries(String query, int pageNumber, int pageSize, FinanceType financeType) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<FinanceEntry> financeEntryPage = financeCustomRepository.getAllFinanceEntry(query, pageRequest, financeType);
        return financeEntryPage.map(financeMapper::convert);
    }
}