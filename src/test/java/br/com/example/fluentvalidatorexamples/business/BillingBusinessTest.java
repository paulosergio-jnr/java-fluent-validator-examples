package br.com.example.fluentvalidatorexamples.business;

import br.com.example.fluentvalidatorexamples.domain.Billing;
import br.com.example.fluentvalidatorexamples.exception.BillingNotFoundException;
import br.com.example.fluentvalidatorexamples.exception.BillingValidationException;
import br.com.example.fluentvalidatorexamples.repository.BillingRepository;
import br.com.example.fluentvalidatorexamples.validator.BillingValitador;
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

import java.util.*;

import static br.com.example.fluentvalidatorexamples.utils.BillingTemplate.createBilling;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class BillingBusinessTest {

  @Mock
  private Appender mockedAppender;

  @Captor
  private ArgumentCaptor<LoggingEvent> loggingEventCaptor;

  @Mock
  private BillingValitador billingValitador;

  @Mock
  private BillingRepository billingRepository;

  @InjectMocks
  private BillingBusiness billingBusiness;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    final Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    root.addAppender(mockedAppender);
  }

  @Test
  void Should_ReturnSavedBilling_When_ValidationsAreOk() {
    final Billing billing = createBilling();

    final Billing savedBilling = createBilling();

    when(billingValitador.validate(eq(billing))).thenReturn(ValidationResult.ok());
    when(billingRepository.save(eq(billing))).thenReturn(savedBilling);

    final Billing returnedBilling = billingBusiness.save(billing);

    verify(mockedAppender, times(2)).doAppend(loggingEventCaptor.capture());
    verify(billingRepository, only()).save(eq(billing));

    final List<LoggingEvent> loggingEventList = loggingEventCaptor.getAllValues();

    assertThat(returnedBilling, not(nullValue()));
    assertThat(returnedBilling, not(equalTo(billing)));
    assertThat(returnedBilling, equalTo(savedBilling));

    assertThat(loggingEventList, hasSize(2));
    assertThat(loggingEventList, everyItem(hasProperty("level", equalTo(Level.INFO))));
  }

  @Test
  void Should_ThrowException_When_ValidationsAreNotOkOnSaving() {
    final Billing billing = createBilling();

    final Billing savedBilling = createBilling();

    final Error error = Error.create("field", "message", "code", "attemptedValue");

    final ArrayList<Error> errors = new ArrayList<>();
    errors.add(error);

    when(billingValitador.validate(eq(billing))).thenReturn(ValidationResult.fail(errors));
    when(billingRepository.save(eq(billing))).thenReturn(savedBilling);

    final BillingValidationException exception = catchThrowableOfType(() -> billingBusiness.save(billing), BillingValidationException.class);

    verify(mockedAppender, times(2)).doAppend(loggingEventCaptor.capture());
    verify(billingRepository, never()).save(eq(billing));

    final List<LoggingEvent> loggingEventList = loggingEventCaptor.getAllValues();

    assertThat(exception, not(nullValue()));
    assertThat(exception, instanceOf(BillingValidationException.class));

    assertThat(loggingEventList, hasSize(2));
    assertThat(loggingEventList, hasItem(hasProperty("level", equalTo(Level.INFO))));
    assertThat(loggingEventList, hasItem(hasProperty("level", equalTo(Level.ERROR))));
  }


  @Test
  void Should_ReturnUpdatedBilling_When_ValidationsAreOk() throws BillingNotFoundException {
    final Billing billing = createBilling();

    final Billing savedBilling = createBilling();

    when(billingValitador.validate(eq(billing))).thenReturn(ValidationResult.ok());
    when(billingRepository.update(eq(billing))).thenReturn(savedBilling);

    final Billing returnedBilling = billingBusiness.update(billing);

    verify(mockedAppender, times(2)).doAppend(loggingEventCaptor.capture());
    verify(billingRepository, only()).update(eq(billing));

    final List<LoggingEvent> loggingEventList = loggingEventCaptor.getAllValues();

    assertThat(returnedBilling, not(nullValue()));
    assertThat(returnedBilling, not(equalTo(billing)));
    assertThat(returnedBilling, equalTo(savedBilling));

    assertThat(loggingEventList, hasSize(2));
    assertThat(loggingEventList, everyItem(hasProperty("level", equalTo(Level.INFO))));
  }

  @Test
  void Should_ThrowException_When_ValidationsAreNotOkOnUpdating() throws BillingNotFoundException {
    final Billing billing = createBilling();

    final Billing savedBilling = createBilling();

    final Error error = Error.create("field", "message", "code", "attemptedValue");

    final ArrayList<Error> errors = new ArrayList<>();
    errors.add(error);

    when(billingValitador.validate(eq(billing))).thenReturn(ValidationResult.fail(errors));
    when(billingRepository.update(eq(billing))).thenReturn(savedBilling);

    final BillingValidationException exception = catchThrowableOfType(() -> billingBusiness.update(billing), BillingValidationException.class);

    verify(mockedAppender, times(2)).doAppend(loggingEventCaptor.capture());
    verify(billingRepository, never()).update(eq(billing));

    final List<LoggingEvent> loggingEventList = loggingEventCaptor.getAllValues();

    assertThat(exception, not(nullValue()));
    assertThat(exception, instanceOf(BillingValidationException.class));

    assertThat(loggingEventList, hasSize(2));
    assertThat(loggingEventList, hasItem(hasProperty("level", equalTo(Level.INFO))));
    assertThat(loggingEventList, hasItem(hasProperty("level", equalTo(Level.ERROR))));
  }

  @Test
  void Should_ThrowException_When_TryingToUpdateAnNonExistingBilling() throws BillingNotFoundException {
    final Billing billing = createBilling();

    when(billingValitador.validate(eq(billing))).thenReturn(ValidationResult.ok());
    when(billingRepository.update(eq(billing))).thenThrow(new BillingNotFoundException());

    final BillingNotFoundException exception = catchThrowableOfType(() -> billingBusiness.update(billing), BillingNotFoundException.class);

    verify(mockedAppender, times(2)).doAppend(loggingEventCaptor.capture());
    verify(billingRepository, only()).update(eq(billing));

    final List<LoggingEvent> loggingEventList = loggingEventCaptor.getAllValues();

    assertThat(exception, not(nullValue()));
    assertThat(exception, instanceOf(BillingNotFoundException.class));

    assertThat(loggingEventList, hasSize(2));
    assertThat(loggingEventList, everyItem(hasProperty("level", equalTo(Level.INFO))));
  }


  @Test
  void Should_DeleteBilling_When_TryingToDeleteExistingBilling() throws BillingNotFoundException {
    final UUID id = UUID.randomUUID();

    billingBusiness.delete(id);

    verify(mockedAppender, times(2)).doAppend(loggingEventCaptor.capture());
    verify(billingRepository, only()).delete(eq(id));

    final List<LoggingEvent> loggingEventList = loggingEventCaptor.getAllValues();

    assertThat(loggingEventList, hasSize(2));
    assertThat(loggingEventList, everyItem(hasProperty("level", equalTo(Level.INFO))));
  }

  @Test
  void Should_ThrowException_When_TryingToDeleteNonExistingBilling() throws BillingNotFoundException {
    final UUID id = UUID.randomUUID();

    doThrow(new BillingNotFoundException()).when(billingRepository).delete(eq(id));

    final BillingNotFoundException exception = catchThrowableOfType(() -> billingBusiness.delete(id), BillingNotFoundException.class);

    verify(mockedAppender, only()).doAppend(loggingEventCaptor.capture());
    verify(billingRepository, only()).delete(eq(id));

    final List<LoggingEvent> loggingEventList = loggingEventCaptor.getAllValues();

    assertThat(exception, not(nullValue()));
    assertThat(exception, instanceOf(BillingNotFoundException.class));

    assertThat(loggingEventList, hasSize(1));
    assertThat(loggingEventList, everyItem(hasProperty("level", equalTo(Level.INFO))));
  }


  @Test
  void Should_ReturnEmptyList_When_FindingAllBillings() {
    when(billingRepository.findAll()).thenReturn(new HashSet<>());

    final Set<Billing> billings = billingBusiness.findAllBillings();

    verify(billingRepository, only()).findAll();

    assertThat(billings, not(nullValue()));
    assertThat(billings, is(empty()));
  }

  @Test
  void Should_ReturnBilling_When_FindingForExistingBilling() throws BillingNotFoundException {
    final UUID id = UUID.randomUUID();

    final Billing billing = createBilling();

    when(billingRepository.findById(eq(id))).thenReturn(billing);

    final Billing foundBilling = billingBusiness.findBillingById(id);

    verify(billingRepository, only()).findById(eq(id));

    assertThat(foundBilling, not(nullValue()));
    assertThat(foundBilling, equalTo(billing));
  }

}
