package com.example.rojomelbackend.repository.Finance;

import com.example.rojomelbackend.model.entity.Finance.FinanceEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FinanceRepository extends JpaRepository<FinanceEntry, Long> {

    Optional<FinanceEntry> findTopByOrderByDateCreatedDesc();

}
