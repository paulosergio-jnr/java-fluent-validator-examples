package br.com.example.fluentvalidatorexamples.business;

import br.com.example.fluentvalidatorexamples.domain.Billing;
import br.com.example.fluentvalidatorexamples.repository.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BillingBusiness {

  @Autowired
  private BillingRepository billingRepository;

  public Billing save(final Billing billing) {
    return billingRepository.save(billing);
  }

  public Billing findBillingById(final UUID id) {
    return billingRepository.findById(id);
  }

}
