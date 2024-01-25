package com.eteration.simplebanking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import java.sql.Timestamp;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",
    discriminatorType = DiscriminatorType.STRING)
public abstract class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Long id;

  private String approvalCode;

  private Timestamp date;

  private Double amount;

  @Column(insertable=false, updatable=false)
  private String type;

  Transaction(double amount) {
    this.date = new Timestamp(System.currentTimeMillis());
    this.amount = amount;
    this.approvalCode = UUID.randomUUID().toString();
  }

  public String getApprovalCode() {
    return approvalCode;
  }

  public abstract void post(Account account);

}
