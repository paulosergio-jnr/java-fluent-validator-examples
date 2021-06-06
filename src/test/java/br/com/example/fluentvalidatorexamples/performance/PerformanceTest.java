package br.com.example.fluentvalidatorexamples.performance;

import br.com.example.fluentvalidatorexamples.domain.Billing;
import br.com.example.fluentvalidatorexamples.utils.BillingTemplate;
import br.com.example.fluentvalidatorexamples.validator.BillingValidator;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

public class PerformanceTest {

  private final StopWatch stopWatch = new StopWatch();
  private final BillingValidator validator = new BillingValidator();
  private final Billing billing = BillingTemplate.createBilling();

  @Test
  void performanceTest() {
    System.err.println("Taken time running 1 iteration: " + executeTests(1) + "ms");
    System.err.println("Taken time running 10 iteration: " + executeTests(10) + "ms");
    System.err.println("Taken time running 100 iteration: " + executeTests(100) + "ms");
    System.err.println("Taken time running 1,000 iteration: " + executeTests(1_000) + "ms");
    System.err.println("Taken time running 10,000 iteration: " + executeTests(10_000) + "ms");
    System.err.println("Taken time running 100,000 iteration: " + executeTests(100_000) + "ms");
  }

  private long executeTests(final long iterations) {
    stopWatch.start();

    for (int i = 0; i < iterations; i++) {
      validator.validate(billing);
    }

    stopWatch.stop();

    return stopWatch.getTotalTimeMillis();
  }

}
