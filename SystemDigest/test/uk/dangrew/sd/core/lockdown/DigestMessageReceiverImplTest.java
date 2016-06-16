/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.core.lockdown;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.sd.core.category.Category;
import uk.dangrew.sd.core.lockdown.DigestManager;
import uk.dangrew.sd.core.lockdown.DigestMessageReceiver;
import uk.dangrew.sd.core.lockdown.DigestMessageReceiverImpl;
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
      verify( actualReceiver ).log( source, category, message );
   }//End Method
   
   @Test public void shouldDisconnectFromDigest(){
      DigestManager.getInstance().log( source, category, message );
      verify( actualReceiver, times( 1 ) ).log( source, category, message );
      
      systemUnderTest.disconnect();
      
      DigestManager.getInstance().log( source, category, message );
      DigestManager.getInstance().log( source, category, message );
      DigestManager.getInstance().log( source, category, message );
      verify( actualReceiver, times( 1 ) ).log( source, category, message );
   }//End Method
   
   @Test public void shouldReconnectToDigest(){
      DigestManager.getInstance().log( source, category, message );
      verify( actualReceiver, times( 1 ) ).log( source, category, message );
      
      systemUnderTest.disconnect();
      
      DigestManager.getInstance().log( source, category, message );
      DigestManager.getInstance().log( source, category, message );
      DigestManager.getInstance().log( source, category, message );
      verify( actualReceiver, times( 1 ) ).log( source, category, message );
      
      systemUnderTest.connect();
      
      DigestManager.getInstance().log( source, category, message );
      DigestManager.getInstance().log( source, category, message );
      DigestManager.getInstance().log( source, category, message );
      verify( actualReceiver, times( 4 ) ).log( source, category, message );
   }//End Method
   
}//End Class
