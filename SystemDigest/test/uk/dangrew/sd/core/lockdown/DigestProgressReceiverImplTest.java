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
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.progress.Progress;
import uk.dangrew.sd.core.source.Source;

/**
 * {@link DigestProgressReceiverImpl} test.
 */
public class DigestProgressReceiverImplTest {
   
   @Captor private ArgumentCaptor< Source > sourceCaptor;
   @Captor private ArgumentCaptor< Progress > progressCaptor;
   @Captor private ArgumentCaptor< Message > messageCaptor;
   
   @Mock private DigestProgressReceiver actualReceiver; 
   @Mock private Source source;
   @Mock private Progress progress;
   @Mock private Message message;
   private DigestProgressReceiverImpl systemUnderTest;
   
   @BeforeClass public static void initialiseDigestManager(){
      DigestManager.reset();
   }//End Method
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new DigestProgressReceiverImpl( actualReceiver );
   }//End Method

   @Test public void shouldReceiveProgressMessagesFromManager() {
      DigestManager.getInstance().progress( source, progress, message );
      verify( actualReceiver ).progress( source, progress, message );
   }//End Method
   
   @Test public void shouldDisconnectFromDigest(){
      DigestManager.getInstance().progress( source, progress, message );
      assertObjectsPassedThroughToReceiver( 1 );
      
      systemUnderTest.disconnect();
      
      DigestManager.getInstance().progress( source, progress, message );
      DigestManager.getInstance().progress( source, progress, message );
      DigestManager.getInstance().progress( source, progress, message );
      assertObjectsPassedThroughToReceiver( 1 );
   }//End Method
   
   @Test public void shouldReconnectToDigest(){
      DigestManager.getInstance().progress( source, progress, message );
      assertObjectsPassedThroughToReceiver( 1 );
      
      systemUnderTest.disconnect();
      
      DigestManager.getInstance().progress( source, progress, message );
      DigestManager.getInstance().progress( source, progress, message );
      DigestManager.getInstance().progress( source, progress, message );
      assertObjectsPassedThroughToReceiver( 1 );
      
      systemUnderTest.connect();
      
      DigestManager.getInstance().progress( source, progress, message );
      DigestManager.getInstance().progress( source, progress, message );
      DigestManager.getInstance().progress( source, progress, message );
      assertObjectsPassedThroughToReceiver( 4 );
   }//End Method
   
   /**
    * Method to assert by capturing the values logged against the expected instance variables.
    * @param expectedTimes the number of time to verify.
    */
   private void assertObjectsPassedThroughToReceiver( int expectedTimes ){
      verify( actualReceiver, times( expectedTimes ) ).progress(  
               sourceCaptor.capture(), 
               progressCaptor.capture(), 
               messageCaptor.capture() 
      );
      
      for ( Source capturedSource : sourceCaptor.getAllValues() ) {
         assertThat( capturedSource, is( source ) );
      }
      for ( Progress progress : progressCaptor.getAllValues() ) {
         assertThat( progress, is( progress ) );
      }
      for ( Message message : messageCaptor.getAllValues() ) {
         assertThat( message, is( message ) );
      }
   }//End Method
   
   @Test public void shouldIdentifyAsConnectedAndDisconnected(){
      assertThat( systemUnderTest.isConnected(), is( true ) );
      systemUnderTest.disconnect();
      assertThat( systemUnderTest.isConnected(), is( false ) );
      systemUnderTest.connect();
      assertThat( systemUnderTest.isConnected(), is( true ) );
   }//End Method

   
}//End Class
