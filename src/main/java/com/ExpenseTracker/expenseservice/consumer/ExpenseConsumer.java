package com.ExpenseTracker.expenseservice.consumer;

import com.ExpenseTracker.expenseservice.DTO.ExpenseDTO;
import com.ExpenseTracker.expenseservice.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseConsumer {

    private ExpenseService expenseService;

    @Autowired
    ExpenseConsumer(ExpenseService expenseService){
        this.expenseService = expenseService;
    }

    @KafkaListener(topics = "${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(ExpenseDTO expenseDTO){
        try{
          expenseService.createExpense(expenseDTO);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Exception is thrown while consuming kafka event");
        }
    }

}
