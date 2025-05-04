package com.ExpenseTracker.expenseservice.service;

import com.ExpenseTracker.expenseservice.DTO.ExpenseDTO;
import com.ExpenseTracker.expenseservice.entities.Expense;
import com.ExpenseTracker.expenseservice.repository.ExpenseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExpenseService {

    private ExpenseRepository expenseRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ExpenseService(ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
    }

    public boolean createExpense(ExpenseDTO expenseDTO){
        setCurrency(expenseDTO);

        try{
           expenseRepository.save(objectMapper.convertValue(expenseDTO, Expense.class));
           return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean updateExpense(ExpenseDTO expenseDTO){
        Optional<Expense> expenseOptional = expenseRepository.findByUserIdAndExternalId(expenseDTO.getUserId(),expenseDTO.getExternalId());
        if(expenseOptional.isEmpty()){
            return false;
        }
        Expense expense = expenseOptional.get();
        expense.setCurrency(Strings.isNotBlank(expenseDTO.getCurrency()) ? expenseDTO.getCurrency():expense.getCurrency());
        expense.setMerchant(Strings.isNotBlank(expenseDTO.getMerchant()) ? expenseDTO.getMerchant():expense.getMerchant());
        expense.setAmount(expenseDTO.getAmount());
        expenseRepository.save(expense);
        return true;
    }

    public List<ExpenseDTO> getExpense(String userId){
        List<Expense> expenseList = expenseRepository.findByUserId(userId);
        return objectMapper.convertValue(expenseList, new TypeReference<List<ExpenseDTO>>() {});
    }

    private void setCurrency(ExpenseDTO expenseDTO){
        if(Objects.isNull(expenseDTO.getCurrency())){
            expenseDTO.setCurrency("INR");
        }
    }
}
