package com.example.rojomelbackend.controller.Finance;

import com.example.rojomelbackend.model.dto.Finance.FinanceDto;
import com.example.rojomelbackend.service.Finance.FinanceService;
import com.example.rojomelbackend.util.Finance.enums.FinanceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.rojomelbackend.util.Finance.constants.FinanceConstants.FINANCE_ENTRY_DELETED;
import static com.example.rojomelbackend.util.Finance.constants.FinanceConstants.FINANCE_ENTRY_NOT_FOUND;

@RestController
@RequestMapping("/finance")
public class FinanceController {

    private final FinanceService financeService;

    @Autowired
    public FinanceController(FinanceService financeService){
        this.financeService = financeService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createFinanceEntry(@RequestBody FinanceDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(financeService.createFinanceEntry(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFinanceEntry(@RequestBody FinanceDto dto, @PathVariable("id") Long id) {
        FinanceDto financeDto = financeService.findById(id);
        if (financeDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FINANCE_ENTRY_NOT_FOUND);
        }
        return ResponseEntity.ok(financeService.updateFinanceEntry(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        FinanceDto financeDto = financeService.findById(id);
        if (financeDto == null) {
            return ResponseEntity
                    .badRequest()
                    .body(FINANCE_ENTRY_NOT_FOUND);
        }
        financeService.deleteFinanceEntry(id);
        return ResponseEntity.ok(FINANCE_ENTRY_DELETED);
    }

    @GetMapping("/allFinanceEntry")
    public ResponseEntity<?> getAllCustomers(@RequestParam(required = false, defaultValue = "") String query,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "BOTH") FinanceType financeType) {
        return ResponseEntity.ok(financeService.getAllFinanceEntries(query, page, size, financeType));
    }
}
