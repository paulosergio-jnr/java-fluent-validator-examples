package br.com.example.fluentvalidatorexamples.controller;

import br.com.example.fluentvalidatorexamples.business.BillingBusiness;
import br.com.example.fluentvalidatorexamples.domain.Billing;
import br.com.example.fluentvalidatorexamples.exception.BillingNotFoundException;
import br.com.example.fluentvalidatorexamples.exception.BillingValidationException;
import br.com.fluentvalidator.context.Error;
import br.com.fluentvalidator.context.ValidationResult;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static br.com.example.fluentvalidatorexamples.utils.BillingTemplate.createBilling;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

class BillingControllerTest {

  @Mock
  private Appender mockedAppender;

  @Captor
  private ArgumentCaptor<LoggingEvent> loggingEventCaptor;

  @Mock
  private BillingBusiness billingBusiness;

  @InjectMocks
  private BillingController billingController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    final Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    root.addAppender(mockedAppender);
  }


  @Test
  void Should_ReturnResponseOk_When_CreatingBilling() {
    final Billing billing = createBilling();

    final Billing persistedBilling = createBilling();

    when(billingBusiness.save(billing)).thenReturn(persistedBilling);

    final ResponseEntity<Object> response = billingController.create(billing);

    verify(mockedAppender, times(2)).doAppend(loggingEventCaptor.capture());
    verify(billingBusiness, only()).save(eq(billing));

    final List<LoggingEvent> loggingEventList = loggingEventCaptor.getAllValues();

    assertThat(response, not(nullValue()));
    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    assertThat(response.getBody(), not(nullValue()));
    assertThat(response.getBody(), is(instanceOf(Billing.class)));

    final Billing responseBilling = (Billing) response.getBody();
    assertThat(responseBilling, equalTo(persistedBilling));

    assertThat(loggingEventList, hasSize(2));
    assertThat(loggingEventList, everyItem(hasProperty("level", equalTo(Level.INFO))));
  }

  @Test
  void Should_ReturnResponseUnprocessableEntity_When_TryingToCreateBillingWithErrors() {
    final Billing billing = createBilling();

    final Error error = Error.create("field", "message", "code", "attemptedValue");

    final List<Error> errorList = Collections.singletonList(error);

    final ValidationResult validationResult = ValidationResult.fail(errorList);

    when(billingBusiness.save(billing)).thenThrow(new BillingValidationException(validationResult));

    final ResponseEntity<Object> response = billingController.create(billing);

    verify(mockedAppender, times(2)).doAppend(loggingEventCaptor.capture());
    verify(billingBusiness, only()).save(eq(billing));

    final List<LoggingEvent> loggingEventList = loggingEventCaptor.getAllValues();

    assertThat(response, not(nullValue()));
    assertThat(response.getStatusCode(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY));
    assertThat(response.getBody(), not(nullValue()));
    assertThat(response.getBody(), is(instanceOf(Collection.class)));

    final Collection<Error> errors = (Collection<Error>) response.getBody();

    assertThat(errors, hasSize(1));
    assertThat(errors.iterator().next(), equalTo(error));

    assertThat(loggingEventList, hasSize(2));
    assertThat(loggingEventList, hasItem(hasProperty("level", equalTo(Level.INFO))));
    assertThat(loggingEventList, hasItem(hasProperty("level", equalTo(Level.ERROR))));
  }


  @Test
  void Should_ReturnResponseOk_When_FindingAllBillings() {
    final Billing billing = createBilling();

    final HashSet<Billing> billingList = new HashSet<>();
    billingList.add(billing);

    when(billingBusiness.findAllBillings()).thenReturn(billingList);

    final ResponseEntity<Object> response = billingController.findAllBillings();

    verify(mockedAppender, times(2)).doAppend(loggingEventCaptor.capture());
    verify(billingBusiness, only()).findAllBillings();

    final List<LoggingEvent> loggingEventList = loggingEventCaptor.getAllValues();

    assertThat(response, not(nullValue()));
    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    assertThat(response.getBody(), not(nullValue()));
    assertThat(response.getBody(), is(instanceOf(Set.class)));

    final Set<Billing> billings = (Set<Billing>) response.getBody();
    assertThat(billings, hasSize(1));
    assertThat(billings.iterator().next(), equalTo(billing));

    assertThat(loggingEventList, hasSize(2));
    assertThat(loggingEventList, everyItem(hasProperty("level", equalTo(Level.INFO))));
  }


  @Test
  void Should_ReturnResponseOk_When_FindingExistingBilling() throws BillingNotFoundException {
    final Billing billing = createBilling();

    when(billingBusiness.findBillingById(ArgumentMatchers.any(UUID.class))).thenReturn(billing);

    final ResponseEntity<Object> response = billingController.findBilling(UUID.randomUUID().toString());

    verify(mockedAppender, times(2)).doAppend(loggingEventCaptor.capture());
    verify(billingBusiness, only()).findBillingById(ArgumentMatchers.any(UUID.class));

    final List<LoggingEvent> loggingEventList = loggingEventCaptor.getAllValues();

    assertThat(response, not(nullValue()));
    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    assertThat(response.getBody(), not(nullValue()));
    assertThat(response.getBody(), equalTo(billing));

    assertThat(loggingEventList, hasSize(2));
    assertThat(loggingEventList, everyItem(hasProperty("level", equalTo(Level.INFO))));
  }

  @Test
  void Should_ReturnResponseNotFound_When_FindingNonExistingBilling() throws BillingNotFoundException {
    when(billingBusiness.findBillingById(ArgumentMatchers.any(UUID.class))).thenThrow(new BillingNotFoundException());

    final ResponseEntity<Object> response = billingController.findBilling(UUID.randomUUID().toString());

    verify(mockedAppender, times(2)).doAppend(loggingEventCaptor.capture());
    verify(billingBusiness, only()).findBillingById(ArgumentMatchers.any(UUID.class));

    final List<LoggingEvent> loggingEventList = loggingEventCaptor.getAllValues();

    assertThat(response, not(nullValue()));
    assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    assertThat(response.getBody(), is(nullValue()));

    assertThat(loggingEventList, hasSize(2));
    assertThat(loggingEventList, hasItem(hasProperty("level", equalTo(Level.INFO))));
    assertThat(loggingEventList, hasItem(hasProperty("level", equalTo(Level.ERROR))));
  }


  @Test
  void Should_ReturnResponseOk_When_UpdatingBilling() throws BillingNotFoundException {
    final Billing billing = createBilling();

    final Billing persistedBilling = createBilling();

    when(billingBusiness.update(billing)).thenReturn(persistedBilling);

    final ResponseEntity<Object> response = billingController.update(UUID.randomUUID().toString(), billing);

    verify(mockedAppender, times(2)).doAppend(loggingEventCaptor.capture());
    verify(billingBusiness, only()).update(eq(billing));

    final List<LoggingEvent> loggingEventList = loggingEventCaptor.getAllValues();

    assertThat(response, not(nullValue()));
    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    assertThat(response.getBody(), not(nullValue()));
    assertThat(response.getBody(), is(instanceOf(Billing.class)));

    final Billing responseBilling = (Billing) response.getBody();
    assertThat(responseBilling, equalTo(persistedBilling));

    assertThat(loggingEventList, hasSize(2));
    assertThat(loggingEventList, everyItem(hasProperty("level", equalTo(Level.INFO))));
  }

  @Test
  void Should_ReturnResponseUnprocessableEntity_When_TryingToUpdateBillingWithErrors() throws BillingNotFoundException {
    final Billing billing = createBilling();

    final Error error = Error.create("field", "message", "code", "attemptedValue");

    final List<Error> errorList = Collections.singletonList(error);

    final ValidationResult validationResult = ValidationResult.fail(errorList);

    when(billingBusiness.update(billing)).thenThrow(new BillingValidationException(validationResult));

    final ResponseEntity<Object> response = billingController.update(UUID.randomUUID().toString(), billing);

    verify(mockedAppender, times(2)).doAppend(loggingEventCaptor.capture());
    verify(billingBusiness, only()).update(eq(billing));

    final List<LoggingEvent> loggingEventList = loggingEventCaptor.getAllValues();

    assertThat(response, not(nullValue()));
    assertThat(response.getStatusCode(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY));
    assertThat(response.getBody(), not(nullValue()));
    assertThat(response.getBody(), is(instanceOf(Collection.class)));

    final Collection<Error> errors = (Collection<Error>) response.getBody();

    assertThat(errors, hasSize(1));
    assertThat(errors.iterator().next(), equalTo(error));

    assertThat(loggingEventList, hasSize(2));
    assertThat(loggingEventList, hasItem(hasProperty("level", equalTo(Level.INFO))));
    assertThat(loggingEventList, hasItem(hasProperty("level", equalTo(Level.ERROR))));
  }

  @Test
  void Should_ReturnResponseNotFound_When_TryingToUpdateNonExistingBilling() throws BillingNotFoundException {
    final Billing billing = createBilling();

    when(billingBusiness.update(billing)).thenThrow(new BillingNotFoundException());

    final ResponseEntity<Object> response = billingController.update(UUID.randomUUID().toString(), billing);

    verify(mockedAppender, times(2)).doAppend(loggingEventCaptor.capture());
    verify(billingBusiness, only()).update(eq(billing));

    final List<LoggingEvent> loggingEventList = loggingEventCaptor.getAllValues();

    assertThat(response, not(nullValue()));
    assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    assertThat(response.getBody(), is(nullValue()));

    assertThat(loggingEventList, hasSize(2));
    assertThat(loggingEventList, hasItem(hasProperty("level", equalTo(Level.INFO))));
    assertThat(loggingEventList, hasItem(hasProperty("level", equalTo(Level.ERROR))));
  }


  @Test
  void Should_ReturnResponseOk_When_DeletingBilling() throws BillingNotFoundException {
    final UUID id = UUID.randomUUID();

    final ResponseEntity<Object> response = billingController.delete(id.toString());

    verify(mockedAppender, times(2)).doAppend(loggingEventCaptor.capture());
    verify(billingBusiness, only()).delete(ArgumentMatchers.any(UUID.class));

    final List<LoggingEvent> loggingEventList = loggingEventCaptor.getAllValues();

    assertThat(response, not(nullValue()));
    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    assertThat(response.getBody(), is(nullValue()));

    assertThat(loggingEventList, hasSize(2));
    assertThat(loggingEventList, everyItem(hasProperty("level", equalTo(Level.INFO))));
  }

  @Test
  void Should_ReturnResponseNotFound_When_TryingToDeleteNonExistingBilling() throws BillingNotFoundException {
    doThrow(new BillingNotFoundException()).when(billingBusiness).delete(ArgumentMatchers.any(UUID.class));

    final ResponseEntity<Object> response = billingController.delete(UUID.randomUUID().toString());

    verify(mockedAppender, times(2)).doAppend(loggingEventCaptor.capture());
    verify(billingBusiness, only()).delete(ArgumentMatchers.any(UUID.class));

    final List<LoggingEvent> loggingEventList = loggingEventCaptor.getAllValues();

    assertThat(response, not(nullValue()));
    assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    assertThat(response.getBody(), is(nullValue()));

    assertThat(loggingEventList, hasSize(2));
    assertThat(loggingEventList, hasItem(hasProperty("level", equalTo(Level.INFO))));
    assertThat(loggingEventList, hasItem(hasProperty("level", equalTo(Level.ERROR))));
  }

}
