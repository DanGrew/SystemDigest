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
import core.progress.Progress;
import core.source.Source;

/**
 * {@link DigestReceiverImpl} test.
 */
public class DigestReceiverImplTest {
   
   @Mock private DigestReceiver actualReceiver; 
   @Mock private Source source;
   @Mock private Category category;
   @Mock private Progress progress;
   @Mock private Message message;
   private DigestReceiver systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new DigestReceiverImpl( actualReceiver );
   }//End Method

   @Test public void shouldReceiveLogMessagesFromManager() {
      DigestManager.getInstance().log( source, category, message );
      verify( actualReceiver ).log( source, category, message );
   }//End Method
   
   @Test public void shouldReceiveProgressMessagesFromManager() {
      DigestManager.getInstance().progress( source, progress, message );
      verify( actualReceiver ).progress( source, progress, message );
   }//End Method

}//End Class
