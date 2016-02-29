/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package core.lockdown;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import core.category.Category;
import core.message.Message;
import core.source.Source;

/**
 * {@link DigestMessageReceiverImpl} test.
 */
public class DigestMessageReceiverImplTest {
   
   @Mock private DigestMessageReceiver actualReceiver; 
   @Mock private Source source;
   @Mock private Category category;
   @Mock private Message message;
   private DigestMessageReceiverImpl systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new DigestMessageReceiverImpl( actualReceiver );
   }//End Method

   @Test public void shouldReceiveLogMessagesFromManager() {
      DigestManager.getInstance().log( source, category, message );
      verify( actualReceiver ).log( source, category, message );
   }//End Method
   
}//End Class
