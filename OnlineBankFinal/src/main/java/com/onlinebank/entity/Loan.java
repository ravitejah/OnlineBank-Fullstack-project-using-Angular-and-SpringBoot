package com.onlinebank.entity;

//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The unique identifier of the loan", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    
    @Schema(description = "The amount of the loan", example = "10000.0", required = true)
    private Double amount;
    
    @Schema(description = "The status of the loan", example = "pending", accessMode = Schema.AccessMode.READ_ONLY)
    private String status;

    @ManyToOne
    @JoinColumn(name = "bank_user_id", nullable = false)
    @Schema(description = "The bank user associated with the loan", accessMode = Schema.AccessMode.READ_ONLY)
    private BankUser bankUser;

    // Default Constructor
    public Loan() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BankUser getBankUser() {
        return bankUser;
    }

    public void setBankUser(BankUser bankUser) {
        this.bankUser = bankUser;
    }
}
