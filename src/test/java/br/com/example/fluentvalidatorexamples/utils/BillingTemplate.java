package br.com.example.fluentvalidatorexamples.utils;

import br.com.example.fluentvalidatorexamples.domain.Address;
import br.com.example.fluentvalidatorexamples.domain.Billing;
import br.com.example.fluentvalidatorexamples.domain.Payer;
import br.com.example.fluentvalidatorexamples.domain.Receiver;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public final class BillingTemplate {

  public static Billing createBilling() {
    final Address billingAddress = new Address();
    billingAddress.setAddressLine1("Defined st");
    billingAddress.setAddressLine2("no number");
    billingAddress.setCity("San Francisco");
    billingAddress.setState("California");
    billingAddress.setCountry("us");
    billingAddress.setZipcode("123456");

    final Payer payer = new Payer();
    payer.setFirstName("Paulo");
    payer.setLastName("Sergio");
    payer.setBirthday(LocalDate.of(1990, 10, 5));
    payer.setEmail("paulo@java-fluent-validator.com");
    payer.setBillingAddress(billingAddress);

    final Address receiverAddress = new Address();
    billingAddress.setAddressLine1("Defined av");
    billingAddress.setAddressLine2("#33");
    billingAddress.setCity("Oakland");
    billingAddress.setState("California");
    billingAddress.setCountry("us");
    billingAddress.setZipcode("654321");

    final Receiver receiver = new Receiver();
    receiver.setFirstName("Marcos");
    receiver.setLastName("Tischer");
    receiver.setBirthday(LocalDate.of(1980, 8, 11));
    receiver.setEmail("marcos@java-fluent-validator.com");
    receiver.setAddress(receiverAddress);

    final ArrayList<String> additionalInfo = new ArrayList<>();

    final Billing billing = new Billing();
    billing.setPayer(payer);
    billing.setReceiver(receiver);
    billing.setBalance(BigDecimal.TEN);
    billing.setDueDate(LocalDate.now().plusDays(1));
    billing.setAcceptPastPayment(true);
    billing.setExpirationDate(LocalDate.now().plusMonths(1));
    billing.setApplyFineForPastPayment(true);
    billing.setFineAmount(BigDecimal.ONE);
    billing.setAdditionalInfo(additionalInfo);

    return billing;
  }

}
