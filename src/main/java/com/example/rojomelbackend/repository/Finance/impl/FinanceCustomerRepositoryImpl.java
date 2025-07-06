package com.example.rojomelbackend.repository.Finance.impl;

import com.example.rojomelbackend.model.entity.Finance.FinanceEntry;
import com.example.rojomelbackend.repository.Finance.FinanceCustomRepository;
import com.example.rojomelbackend.util.Finance.enums.FinanceType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FinanceCustomerRepositoryImpl implements FinanceCustomRepository {

    private static final String BASE_QUERY = "FROM FinanceEntry WHERE isDeleted = false ORDER BY dateCreated DESC";
    private static final String SELECT_QUERY = "SELECT * " + BASE_QUERY;
    private static final String COUNT_QUERY = "SELECT COUNT(*) " + BASE_QUERY;

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public Page<FinanceEntry> getAllFinanceEntry(String query, PageRequest pageRequest, FinanceType financeType) {
        String whereClause = buildWhereClause(financeType);
        String finalSelectQuery = SELECT_QUERY + whereClause;
        String finalCountQuery = COUNT_QUERY + whereClause;

        Query nativeQuery = entityManager.createNativeQuery(finalSelectQuery, FinanceEntry.class)
                .setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize())
                .setMaxResults(pageRequest.getPageSize());

        Query countNativeQuery = entityManager.createNativeQuery(finalCountQuery);

        List<FinanceEntry> financeEntryList = nativeQuery.getResultList();
        Long count = (Long) countNativeQuery.getSingleResult();

        return new PageImpl<>(financeEntryList, pageRequest, count);
    }

    private String buildWhereClause(FinanceType financeType) {
        if (financeType == FinanceType.BOTH) {
            return "";
        }
        String columnName = financeType == FinanceType.EXPENSE ? "expense" : "income";
        return " AND " + columnName + " IS NOT NULL AND " + columnName + " != 0";
    }
}