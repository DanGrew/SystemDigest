/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.core.lockdown;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.sd.core.category.Category;
import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.source.Source;

/**
 * {@link DigestMessageReceiverImpl} test.
 */
public class DigestMessageReceiverImplTest {
   
   @Mock private DigestMessageReceiver actualReceiver;
   
   @Mock private Source source;
   @Mock private Category category;
   @Mock private Message message;
   
   @Captor private ArgumentCaptor< LocalDateTime > timestampCaptor;
   @Captor private ArgumentCaptor< Source > sourceCaptor;
   @Captor private ArgumentCaptor< Category > categoryCaptor;
   @Captor private ArgumentCaptor< Message > messageCaptor;
   
   private DigestMessageReceiverImpl systemUnderTest;
   
   @BeforeClass public static void initialiseSystemDigest(){
      DigestManager.reset();
   }//End Method
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new DigestMessageReceiverImpl( actualReceiver );
   }//End Method

   @Test public void shouldReceiveLogMessagesFromManager() {
      DigestManager.getInstance().log( source, category, message );
      assertObjectsPassedThroughToReceiver( 1 );
   }//End Method
   
   @Test public void shouldDisconnectFromDigest(){
      DigestManager.getInstance().log( source, category, message );
      assertObjectsPassedThroughToReceiver( 1 );
      
      systemUnderTest.disconnect();
      
      DigestManager.getInstance().log( source, category, message );
      DigestManager.getInstance().log( source, category, message );
      DigestManager.getInstance().log( source, category, message );
      assertObjectsPassedThroughToReceiver( 1 );
   }//End Method
   
   @Test public void shouldReconnectToDigest(){
      DigestManager.getInstance().log( source, category, message );
      assertObjectsPassedThroughToReceiver( 1 );
      
      systemUnderTest.disconnect();
      
      DigestManager.getInstance().log( source, category, message );
      DigestManager.getInstance().log( source, category, message );
      DigestManager.getInstance().log( source, category, message );
      assertObjectsPassedThroughToReceiver( 1 );
      
      systemUnderTest.connect();
      
      DigestManager.getInstance().log( source, category, message );
      DigestManager.getInstance().log( source, category, message );
      DigestManager.getInstance().log( source, category, message );
      assertObjectsPassedThroughToReceiver( 4 );
   }//End Method
   
   /**
    * Method to assert by capturing the values logged against the expected instance variables.
    * @param expectedTimes the number of time to verify.
    */
   private void assertObjectsPassedThroughToReceiver( int expectedTimes ){
      verify( actualReceiver, times( expectedTimes ) ).log( 
               timestampCaptor.capture(), 
               sourceCaptor.capture(), 
               categoryCaptor.capture(), 
               messageCaptor.capture() 
      );
      
      for ( LocalDateTime capturedTimestamp : timestampCaptor.getAllValues() ) {
         assertThat( capturedTimestamp, is( not( nullValue() ) ) );
      }
      for ( Source capturedSource : sourceCaptor.getAllValues() ) {
         assertThat( capturedSource, is( source ) );
      }
      for ( Category category : categoryCaptor.getAllValues() ) {
         assertThat( category, is( category ) );
      }
      for ( Message message : messageCaptor.getAllValues() ) {
         assertThat( message, is( message ) );
      }
   }//End Method
   
}//End Class
