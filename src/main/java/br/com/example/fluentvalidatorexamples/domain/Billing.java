package br.com.example.fluentvalidatorexamples.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class Billing {

  private UUID id;

  private Payer payer;

  private Receiver receiver;

  private BigDecimal balance;

  private LocalDate dueDate;

  private Boolean acceptPastPayment;

  private LocalDate expirationDate;

  private Boolean applyFineForPastPayment;

  private BigDecimal fineAmount;

  private List<String> additionalInfo;

}
