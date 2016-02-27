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
   @Mock private DigestReceiver guiConnector;
   private DigestManager systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      DigestManager.setInstance( new DigestManager() );
      systemUnderTest = DigestManager.getInstance(); 
      assertThat( systemUnderTest, notNullValue() );
   }//End Method

   @Test public void shouldNotForwardLogMessagesOnWhenNoConnectedGuis() {
      systemUnderTest.log( source, category, message );
   }//End Method
   
   @Test public void shouldForwardLogMessagesOnToConnectedGuis() {
      systemUnderTest.registerReceiver( guiConnector );
      systemUnderTest.log( source, category, message );
      verify( guiConnector ).log( source, category, message );
   }//End Method
   
   @Test public void shouldForwardLogMessageOnToMultipleConnectedGuis() {
      systemUnderTest.registerReceiver( guiConnector );
      DigestReceiver anotherConnector = mock( DigestReceiver.class );
      systemUnderTest.registerReceiver( anotherConnector );
      
      systemUnderTest.progress( source, progress, message );
      verify( guiConnector ).progress( source, progress, message );
      verify( anotherConnector ).progress( source, progress, message );
   }//End Method
   
   @Test public void shouldNotForwardProgressOnWhenNoConnectedGuis() {
      systemUnderTest.progress( source, progress, message );
   }//End Method
   
   @Test public void shouldForwardProgressOnToConnectedGuis() {
      systemUnderTest.registerReceiver( guiConnector );
      systemUnderTest.progress( source, progress, message );
      verify( guiConnector ).progress( source, progress, message );
   }//End Method
   
   @Test public void shouldForwardProgressOnToMultipleConnectedGuis() {
      systemUnderTest.registerReceiver( guiConnector );
      DigestReceiver anotherConnector = mock( DigestReceiver.class );
      systemUnderTest.registerReceiver( anotherConnector );
      
      systemUnderTest.progress( source, progress, message );
      verify( guiConnector ).progress( source, progress, message );
      verify( anotherConnector ).progress( source, progress, message );
   }//End Method
   
   @Test public void shouldNotRegisterSameConnectorMultipleTimes(){
      systemUnderTest.registerReceiver( guiConnector );
      systemUnderTest.registerReceiver( guiConnector );
      systemUnderTest.registerReceiver( guiConnector );
      systemUnderTest.registerReceiver( guiConnector );
      systemUnderTest.log( source, category, message );
      verify( guiConnector, times( 1 ) ).log( source, category, message );
   }//End Method

}//End Class
