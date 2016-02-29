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
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import core.message.Message;
import core.progress.Progress;
import core.source.Source;

/**
 * {@link DigestProgressReceiverImpl} test.
 */
public class DigestProgressReceiverImplTest {
   
   @Mock private DigestProgressReceiver actualReceiver; 
   @Mock private Source source;
   @Mock private Progress progress;
   @Mock private Message message;
   private DigestProgressReceiver systemUnderTest;
   
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
   
}//End Class
