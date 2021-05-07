package br.com.example.fluentvalidatorexamples.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
public enum BillingStatus {

  OPEN("open"),
  PAST_DUE("past_due"),
  PAYD("payd"),
  CANCELED("canceled");

  private static final Map<String, BillingStatus> ENTRIES = new HashMap<>();

  static {
    for (final BillingStatus status : BillingStatus.values()) {
      ENTRIES.put(status.value, status);
    }
  }

  private final String value;

  BillingStatus(final String value) {
    this.value = value;
  }

  public static BillingStatus entryOf(final String billingStatus) {
    return Objects.nonNull(billingStatus) ? ENTRIES.get(billingStatus.toLowerCase()) : null;
  }

  public static boolean exists(final String billingStatus) {
    return Objects.nonNull(billingStatus) && ENTRIES.containsKey(billingStatus);
  }

}
