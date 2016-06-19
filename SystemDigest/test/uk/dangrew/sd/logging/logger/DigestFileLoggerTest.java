/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.logging.logger;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import uk.dangrew.sd.core.category.Categories;
import uk.dangrew.sd.core.category.Category;
import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.message.Messages;
import uk.dangrew.sd.core.source.Source;
import uk.dangrew.sd.core.source.SourceImpl;
import uk.dangrew.sd.digest.object.ObjectDigestImpl;
import uk.dangrew.sd.logging.location.LoggingLocationProtocol;
import uk.dangrew.sd.utility.threading.ThreadedWrapper;

/**
 * {@link DigestFileLogger} test.
 */
public class DigestFileLoggerTest {
   
   @Rule public final Timeout timeout = new Timeout( 50000000, TimeUnit.MILLISECONDS );
   
   private LocalDateTime timestamp;
   @Mock private Source source;
   @Mock private Category category;
   @Mock private Message message;
   
   @Mock private LoggingLocationProtocol protocol;
   private DigestFileLogger systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      
      timestamp = LocalDateTime.MIN;
      when( source.getIdentifier() ).thenReturn( "source" );
      when( category.getName() ).thenReturn( "category" );
      when( message.getMessage() ).thenReturn( "message" );
      
      systemUnderTest = new DigestFileLogger();
      systemUnderTest.setFileLocation( protocol );
      systemUnderTest.connect();
   }//End Method
   
   /**
    * Convenience method to fire a message from an {@link ObjectDigestImpl}.
    */
   private void fireMessage(){
      new ObjectDigestImpl( new SourceImpl( this ) ).log( Categories.error(), Messages.simpleMessage( "anything" ) );
   }//End Method
   
   @Test public void shouldConnectToDigest() {
      fireMessage();
      systemUnderTest.iterate();
      verify( protocol ).logToLocation( Mockito.anyString() );
   }//End Method
   
   @Test public void shouldDisconnectFromDigest() {
      systemUnderTest.disconnect();
      fireMessage();
      
      for ( int i = 0; i < 10; i++ ) {
         systemUnderTest.iterate();
      }
      
      verify( protocol, never() ).logToLocation( Mockito.anyString() );
   }//End Method
   
   @Test public void runShouldPollForMessagesToLogAndFindOne(){
      systemUnderTest.log( timestamp, source, category, message );
      verify( protocol, never() ).logToLocation( Mockito.anyString() );
      
      systemUnderTest.iterate();
      verify( protocol ).logToLocation( DigestFileLogger.format( timestamp, source, category, message ) );
   }//End Method
   
   @Test public void runShouldNotLogIfNoProtocolAssociated(){
      systemUnderTest = new DigestFileLogger();
      systemUnderTest.connect();
      
      systemUnderTest.log( timestamp, source, category, message );
      verify( protocol, never() ).logToLocation( Mockito.anyString() );
      
      systemUnderTest.iterate();
      verify( protocol, never() ).logToLocation( DigestFileLogger.format( timestamp, source, category, message ) );
   }//End Method
   
   @Test public void runShouldPollForMessagesToLogAndFindMany(){
      systemUnderTest.log( timestamp, source, category, message );
      systemUnderTest.log( timestamp, source, category, message );
      systemUnderTest.log( timestamp, source, category, message );
      
      String firstExpected = DigestFileLogger.format( timestamp, source, category, message );
      
      when( source.getIdentifier() ).thenReturn( "a" );
      when( category.getName() ).thenReturn( "b" );
      when( message.getMessage() ).thenReturn( "c" );
      
      String secondExpected = DigestFileLogger.format( timestamp, source, category, message );
      
      systemUnderTest.log( timestamp, source, category, message );
      verify( protocol, never() ).logToLocation( Mockito.anyString() );
      
      for ( int i = 0; i < 4; i++ ) {
         systemUnderTest.iterate();
      }
      verify( protocol, times( 3 ) ).logToLocation( firstExpected );
      verify( protocol, times( 1 ) ).logToLocation( secondExpected );
   }//End Method
   
   @Test public void shouldConvertLogIntoReadableString(){
      String expected = "-999999999-01-01T00:00, source, category, message\n";
      assertThat( DigestFileLogger.format( timestamp, source, category, message ), is( expected ) );
   }//End Method
   
   @Test public void shouldUseProtocolFromWithinAThread() throws InterruptedException{
      CountDownLatch latch = new CountDownLatch( 1 );
      when( protocol.logToLocation( Mockito.anyString() ) ).then( invocation -> { latch.countDown(); return null; } );
      
      fireMessage();
      new ThreadedWrapper().wrap( systemUnderTest, 1 );
      
      latch.await();
      verify( protocol ).logToLocation( Mockito.anyString() );
   }//End Method
   
   @Test public void shouldPropogateFromOneThreadToAnother() throws InterruptedException{
      final int wantedNumberOfMessages = 20;
      CountDownLatch latch = new CountDownLatch( wantedNumberOfMessages );
      when( protocol.logToLocation( Mockito.anyString() ) ).then( invocation -> { latch.countDown(); return null; } );
      
      ThreadedWrapper wrapper = new ThreadedWrapper();
      wrapper.wrap( systemUnderTest, -1 );
      
      for ( int i = 0; i < wantedNumberOfMessages; i++ ) {
         fireMessage();
      }
      
      latch.await( 5000, TimeUnit.MILLISECONDS );
      wrapper.shutdown();
      verify( protocol, times( wantedNumberOfMessages ) ).logToLocation( Mockito.anyString() );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAccpetNullProtocol(){
      systemUnderTest.setFileLocation( null );
   }//End Method
}//End Class
