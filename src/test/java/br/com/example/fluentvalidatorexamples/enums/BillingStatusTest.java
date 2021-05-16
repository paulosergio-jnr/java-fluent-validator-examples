package br.com.example.fluentvalidatorexamples.enums;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class BillingStatusTest {

  @Test
  void testEntryOf() {
    assertThat(BillingStatus.entryOf("open"), equalTo(BillingStatus.OPEN));
    assertThat(BillingStatus.entryOf("past_due"), equalTo(BillingStatus.PAST_DUE));
    assertThat(BillingStatus.entryOf("payd"), equalTo(BillingStatus.PAYD));
    assertThat(BillingStatus.entryOf("canceled"), equalTo(BillingStatus.CANCELED));
    assertThat(BillingStatus.entryOf("non_present"), equalTo(null));
    assertThat(BillingStatus.entryOf(""), equalTo(null));
    assertThat(BillingStatus.entryOf(null), equalTo(null));
  }

  @Test
  void testExists() {
    assertThat(BillingStatus.exists(BillingStatus.OPEN.getValue()), equalTo(true));
    assertThat(BillingStatus.exists(BillingStatus.PAST_DUE.getValue()), equalTo(true));
    assertThat(BillingStatus.exists(BillingStatus.PAYD.getValue()), equalTo(true));
    assertThat(BillingStatus.exists(BillingStatus.CANCELED.getValue()), equalTo(true));
    assertThat(BillingStatus.exists("non_present"), equalTo(false));
    assertThat(BillingStatus.exists(""), equalTo(false));
    assertThat(BillingStatus.exists(null), equalTo(false));
  }

}
