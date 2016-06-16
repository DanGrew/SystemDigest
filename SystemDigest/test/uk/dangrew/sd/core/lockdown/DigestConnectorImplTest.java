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
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.sd.core.category.Category;
import uk.dangrew.sd.core.lockdown.DigestConnector;
import uk.dangrew.sd.core.lockdown.DigestConnectorImpl;
import uk.dangrew.sd.core.lockdown.DigestManager;
import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.progress.Progress;
import uk.dangrew.sd.core.source.Source;

/**
 * {@link DigestConnectorImpl} test.
 */
public class DigestConnectorImplTest {

   @Mock private DigestManager digestManager;
   @Mock private Source source;
   @Mock private Category category;
   @Mock private Progress progress;
   @Mock private Message message;
   private DigestConnector systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      DigestManager.setInstance( digestManager );
      systemUnderTest = new DigestConnectorImpl();
   }//End Method
   
   @Test public void shouldForwardLogToDigestManager() {
      systemUnderTest.log( source, category, message );
      verify( digestManager ).log( source, category, message );
   }//End Method
   
   @Test public void shouldForwardMultipleLogsToDigestManager() {
      systemUnderTest.log( source, category, message );
      systemUnderTest.log( source, category, message );
      systemUnderTest.log( source, category, message );
      systemUnderTest.log( source, category, message );
      verify( digestManager, times( 4 ) ).log( source, category, message );
   }//End Method
   
   @Test public void shouldForwardProgressToDigestManager() {
      systemUnderTest.progress( source, progress, message );
      verify( digestManager ).progress( source, progress, message );
   }//End Method
   
   @Test public void shouldForwardMultipleProgressToDigestManager() {
      systemUnderTest.progress( source, progress, message );
      systemUnderTest.progress( source, progress, message );
      systemUnderTest.progress( source, progress, message );
      systemUnderTest.progress( source, progress, message );
      verify( digestManager, times( 4 ) ).progress( source, progress, message );
   }//End Method

}//End Class
