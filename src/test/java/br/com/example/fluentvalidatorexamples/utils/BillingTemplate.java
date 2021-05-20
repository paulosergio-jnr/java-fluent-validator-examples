package br.com.example.fluentvalidatorexamples.utils;

import br.com.example.fluentvalidatorexamples.domain.Address;
import br.com.example.fluentvalidatorexamples.domain.Billing;
import br.com.example.fluentvalidatorexamples.domain.Payer;
import br.com.example.fluentvalidatorexamples.domain.Receiver;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class BillingTemplate {

  public static Billing createBilling() {
    final Billing billing = new Billing();
    billing.setPayer(createPayer());
    billing.setReceiver(createReceiver());
    billing.setBalance(BigDecimal.TEN);
    billing.setDueDate(LocalDate.now().plusDays(1));
    billing.setAcceptPastPayment(true);
    billing.setExpirationDate(LocalDate.now().plusMonths(1));
    billing.setApplyFineForPastPayment(true);
    billing.setFineAmount(BigDecimal.ONE);
    billing.setAdditionalInfo(createAdditionalInfo());

    return billing;
  }

  public static Address createAddress() {
    final Address billingAddress = new Address();
    billingAddress.setAddressLine1("Defined st");
    billingAddress.setAddressLine2("no number");
    billingAddress.setCity("San Francisco");
    billingAddress.setState("California");
    billingAddress.setCountry("us");
    billingAddress.setZipcode("123456");

    return billingAddress;
  }

  public static List<String> createAdditionalInfo() {
    final ArrayList<String> additionalInfo = new ArrayList<>();
    additionalInfo.add("This is an additional info");

    return additionalInfo;
  }

  public static Payer createPayer() {
    final Payer payer = new Payer();
    payer.setFirstName("Paulo");
    payer.setLastName("Sergio");
    payer.setBirthday(LocalDate.of(1990, 10, 5));
    payer.setEmail("paulo@java-fluent-validator.com");
    payer.setBillingAddress(createAddress());

    return payer;
  }

  public static Receiver createReceiver() {
    final Receiver receiver = new Receiver();
    receiver.setFirstName("Marcos");
    receiver.setLastName("Tischer");
    receiver.setBirthday(LocalDate.of(1980, 8, 11));
    receiver.setEmail("marcos@java-fluent-validator.com");
    receiver.setAddress(createAddress());

    return receiver;
  }

}
