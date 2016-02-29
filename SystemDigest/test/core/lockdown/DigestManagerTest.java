/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package core.lockdown;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import core.category.Category;
import core.message.Message;
import core.progress.Progress;
import core.source.Source;

/**
 * {@link DigestManager} test.
 */
public class DigestManagerTest {

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

}//End Class
