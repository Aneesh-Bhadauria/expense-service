package com.ExpenseTracker.expenseservice.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseDTO {

    private String externalId;

    @NonNull
    @JsonProperty(value = "amount")
    private String amount;

    @JsonProperty(value="user_id")
    private String userId;

    @JsonProperty(value = "merchant")
    private String merchant;

    @JsonProperty(value = "currency")
    private String currency;

    @JsonProperty(value = "created_at")
    private Timestamp createdAt;

    @PrePersist
    @PreUpdate
    private void generateExternalId(){
        if(this.externalId == null){
            this.externalId = UUID.randomUUID().toString();
        }
        if(this.createdAt == null){
            this.createdAt = new Timestamp(Instant.now().toEpochMilli());
        }
    }
}
