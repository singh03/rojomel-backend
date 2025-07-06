package com.example.rojomelbackend.repository.Customer.impl;

import com.example.rojomelbackend.mapper.Customer.CustomerMapper;
import com.example.rojomelbackend.model.entity.customer.Customer;
import com.example.rojomelbackend.repository.Customer.CustomCustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.rojomelbackend.util.Customer.constants.CustomerConstants.*;

@Component
public class CustomCustomerRepositoryImpl implements CustomCustomerRepository {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Page<Customer> getAllCustomers(String query, PageRequest pageRequest) {

        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();

        String searchField = determineSearchField(query);
        Long count = 0l;
        if (!searchField.isEmpty()) {
            switch (searchField) {
                case EMAIL:
                    count = checkCount(searchField, query);
                    if (count > 0) break;
                    searchField = NAME;
                default:
                    count = checkCount(searchField, query);
                    if (count > 0) break;
                    else searchField = null;

            }
        }

       String sqlQuery = "SELECT * FROM Customer as c WHERE c.isDeleted = false";
        String countQuery = "SELECT COUNT(*) FROM Customer as c WHERE c.isDeleted = false";

        if (searchField != null && !searchField.isEmpty()) {
        sqlQuery += " AND " + searchField + " LIKE '%" + query + "%'";
        countQuery += " AND " + searchField + " LIKE '%" + query + "%'";
        }

        sqlQuery += " ORDER BY c.dateCreated DESC";
        countQuery += " ORDER BY c.dateCreated DESC";

        Query nativeQuery = entityManager.createNativeQuery(sqlQuery, Customer.class);
        Query countNativeQuery = entityManager.createNativeQuery(countQuery);

        if (searchField != null && !searchField.isEmpty()) {
            nativeQuery.setParameter(1, query);
            countNativeQuery.setParameter(1, query);
        }


        nativeQuery.setFirstResult(pageNumber * pageSize).setMaxResults(pageSize);
        List<Customer> customerList = nativeQuery.getResultList();
        count = (Long) countNativeQuery.getSingleResult();

        return new PageImpl<>(customerList,  pageRequest, count);

    }

    private Long checkCount(String searchField, String query) {
        String countQuery = "SELECT COUNT(c) FROM Customer c WHERE c.isDeleted = false AND " + searchField + " LIKE '%" + query + "%'";
        Query countNativeQuery = entityManager.createNativeQuery(countQuery);
        if (searchField != null) {
            countNativeQuery.setParameter(1, query);
        }
        return (Long) countNativeQuery.getSingleResult();
    }

    private String determineSearchField(String query) {
        if(!query.isEmpty()){
            if(query.matches(EMAIL_REGEX)){
                return EMAIL;
            } else{
                return NAME;
            }
        }
        return "";
    }
}
