package br.com.example.fluentvalidatorexamples.domain;

import br.com.example.fluentvalidatorexamples.enums.BillingStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Billing {

  private UUID id;

  private Receiver receiver;

  private Payer payer;

  private BigDecimal balance;

  private LocalDate dueDate;

  private Boolean acceptPastPayment;

  private LocalDate expirationDate;

  private Boolean applyFineForPastPayment;

  private BigDecimal fineAmount;

  private List<String> additionalInfo;

  private BillingStatus billingStatus;

}
