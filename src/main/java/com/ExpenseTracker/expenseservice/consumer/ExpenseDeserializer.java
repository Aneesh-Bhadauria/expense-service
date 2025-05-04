package com.ExpenseTracker.expenseservice.consumer;

import com.ExpenseTracker.expenseservice.DTO.ExpenseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.ByteBuffer;
import java.util.Map;

public class ExpenseDeserializer implements Deserializer <ExpenseDTO>{

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public ExpenseDTO deserialize(String arg0, byte[] arg1) {
        ObjectMapper objectMapper = new ObjectMapper();
        ExpenseDTO expenseDTO = null;

        try{
           expenseDTO = objectMapper.readValue(arg1, ExpenseDTO.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return expenseDTO;
    }

    @Override
    public void close() {

    }
}
