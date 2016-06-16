/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.core.lockdown;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import uk.dangrew.sd.core.category.Category;
import uk.dangrew.sd.core.lockdown.DigestManager;
import uk.dangrew.sd.core.lockdown.DigestMessageReceiver;
import uk.dangrew.sd.core.lockdown.DigestProgressReceiver;
import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.message.MessageFilter;
import uk.dangrew.sd.core.progress.Progress;
import uk.dangrew.sd.core.progress.ProgressFilter;
import uk.dangrew.sd.core.source.Source;

/**
 * {@link DigestManager} test.
 */
public class DigestManagerTest {

   private static final int CONCURRENCY_ITERATIONS = 100;
   @Mock private Source source;
   @Mock private Category category;
   @Mock private Progress progress;
   @Mock private Message message;
   @Mock private DigestMessageReceiver messageReceiver;
   @Mock private DigestProgressReceiver progressReceiver;
   private DigestManager systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      DigestManager.setInstance( new DigestManager() );
      systemUnderTest = DigestManager.getInstance(); 
      assertThat( systemUnderTest, notNullValue() );
   }//End Method

   @Test public void shouldNotForwardLogMessagesOnWhenNoReceivers() {
      systemUnderTest.log( source, category, message );
   }//End Method
   
   @Test public void shouldForwardLogMessagesOnToReceivers() {
      systemUnderTest.registerMessageReceiver( messageReceiver );
      systemUnderTest.log( source, category, message );
      verify( messageReceiver ).log( source, category, message );
   }//End Method
   
   @Test public void shouldForwardLogMessageOnToMultipleReceivers() {
      systemUnderTest.registerMessageReceiver( messageReceiver );
      DigestMessageReceiver anotherConnector = mock( DigestMessageReceiver.class );
      systemUnderTest.registerMessageReceiver( anotherConnector );
      
      systemUnderTest.log( source, category, message );
      verify( messageReceiver ).log( source, category, message );
      verify( anotherConnector ).log( source, category, message );
   }//End Method
   
   @Test public void shouldNotForwardProgressOnWhenNoReceivers() {
      systemUnderTest.progress( source, progress, message );
   }//End Method
   
   @Test public void shouldForwardProgressOnToReceivers() {
      systemUnderTest.registerProgressReceiver( progressReceiver );
      systemUnderTest.progress( source, progress, message );
      verify( progressReceiver ).progress( source, progress, message );
   }//End Method
   
   @Test public void shouldForwardProgressOnToMultipleReceivers() {
      systemUnderTest.registerProgressReceiver( progressReceiver );
      DigestProgressReceiver anotherConnector = mock( DigestProgressReceiver.class );
      systemUnderTest.registerProgressReceiver( anotherConnector );
      
      systemUnderTest.progress( source, progress, message );
      verify( progressReceiver ).progress( source, progress, message );
      verify( anotherConnector ).progress( source, progress, message );
   }//End Method
   
   @Test public void shouldNotRegisterSameMessageReceiverMultipleTimes(){
      systemUnderTest.registerMessageReceiver( messageReceiver );
      systemUnderTest.registerMessageReceiver( messageReceiver );
      systemUnderTest.registerMessageReceiver( messageReceiver );
      systemUnderTest.registerMessageReceiver( messageReceiver );
      systemUnderTest.log( source, category, message );
      verify( messageReceiver, times( 1 ) ).log( source, category, message );
   }//End Method
   
   @Test public void shouldNotRegisterSameProgressReceiverMultipleTimes(){
      systemUnderTest.registerProgressReceiver( progressReceiver );
      systemUnderTest.registerProgressReceiver( progressReceiver );
      systemUnderTest.registerProgressReceiver( progressReceiver );
      systemUnderTest.registerProgressReceiver( progressReceiver );
      systemUnderTest.progress( source, progress, message );
      verify( progressReceiver, times( 1 ) ).progress( source, progress, message );
   }//End Method
   
   @Test public void shouldFilterMessagesUsingMatcher(){
      MessageFilter filter = mock( MessageFilter.class );
      when( filter.matches( Mockito.any(), Mockito.any(), Mockito.any() ) ).thenReturn( false, true );
      
      systemUnderTest.registerMessageReceiver( messageReceiver, filter );
      
      systemUnderTest.log( source, category, message );
      verify( messageReceiver, times( 0 ) ).log( source, category, message );
      
      systemUnderTest.log( source, category, message );
      verify( messageReceiver, times( 1 ) ).log( source, category, message );
   }//End Method
   
   @Test public void shouldFilterProgressUsingMatcher(){
      ProgressFilter filter = mock( ProgressFilter.class );
      when( filter.matches( Mockito.any(), Mockito.any(), Mockito.any() ) ).thenReturn( false, true );
      
      systemUnderTest.registerProgressReceiver( progressReceiver, filter );
      
      systemUnderTest.progress( source, progress, message );
      verify( progressReceiver, times( 0 ) ).progress( source, progress, message );
      
      systemUnderTest.progress( source, progress, message );
      verify( progressReceiver, times( 1 ) ).progress( source, progress, message );
   }//End Method
   
   @Test public void shouldOverrideMessageFilterWhenReregistered(){
      MessageFilter firstFilter = mock( MessageFilter.class );
      MessageFilter secondfilter = mock( MessageFilter.class );
      
      systemUnderTest.registerMessageReceiver( messageReceiver, firstFilter );
      systemUnderTest.registerMessageReceiver( messageReceiver, secondfilter );
      
      systemUnderTest.log( source, category, message );
      verify( firstFilter, times( 0 ) ).matches( source, category, message );
      verify( secondfilter, times( 1 ) ).matches( source, category, message );
   }//End Method
   
   @Test public void shouldOverrideProgressFilterWhenReregistered(){
      ProgressFilter firstFilter = mock( ProgressFilter.class );
      ProgressFilter secondFilter = mock( ProgressFilter.class );
      
      systemUnderTest.registerProgressReceiver( progressReceiver, firstFilter );
      systemUnderTest.registerProgressReceiver( progressReceiver, secondFilter );
      
      systemUnderTest.progress( source, progress, message );
      verify( firstFilter, times( 0 ) ).matches( source, progress, message );
      verify( secondFilter, times( 1 ) ).matches( source, progress, message );
   }//End Method
   
   @Test public void shouldUnregisterMessageReceiver(){
      systemUnderTest.registerMessageReceiver( messageReceiver );
      systemUnderTest.log( source, category, message );
      verify( messageReceiver, times( 1 ) ).log( source, category, message );
      
      systemUnderTest.unregisterMessageReceiver( messageReceiver );
      
      systemUnderTest.log( source, category, message );
      systemUnderTest.log( source, category, message );
      verify( messageReceiver, times( 1 ) ).log( source, category, message );
   }//End Method
   
   @Test public void logShouldNotBreakWithConcurrentAccess() throws InterruptedException{
      CountDownLatch latch = new CountDownLatch( 2 );
      
      new Thread( () -> {
         for ( int i = 0; i < CONCURRENCY_ITERATIONS; i++ ) {
            systemUnderTest.log( source, category, message );
         }
         latch.countDown();
      } ).start();
      
      new Thread( () -> {
         for ( int i = 0; i < CONCURRENCY_ITERATIONS; i++ ) {
            systemUnderTest.registerMessageReceiver( mock( DigestMessageReceiver.class ) );
         }
         latch.countDown();
      } ).start();
      
      latch.await();
   }//End Method
   
   @Test public void progressShouldNotBreakWithConcurrentAccess() throws InterruptedException{
      CountDownLatch latch = new CountDownLatch( 2 );
      
      new Thread( () -> {
         for ( int i = 0; i < CONCURRENCY_ITERATIONS; i++ ) {
            systemUnderTest.progress( source, progress, message );
         }
         latch.countDown();
      } ).start();
      
      new Thread( () -> {
         for ( int i = 0; i < CONCURRENCY_ITERATIONS; i++ ) {
            systemUnderTest.registerProgressReceiver( mock( DigestProgressReceiver.class ) );
         }
         latch.countDown();
      } ).start();
      
      latch.await();
   }//End Method

}//End Class
