/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package digest.object;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import core.category.Category;
import core.lockdown.DigestConnector;
import core.message.Message;
import core.progress.Progress;
import core.source.Source;

/**
 * {@link ObjectDigestImpl} test.
 */
public class ObjectDigestImplTest {

   private ObjectDigest systemUnderTest;
   
   @Mock private DigestConnector connector;
   @Mock private Source source;
   @Mock private Category category;
   @Mock private Progress progress;
   @Mock private Message message;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ObjectDigestImpl( source, connector );
   }//End Method
   
   @Test public void shouldForwardLogToDigestManager(){
      systemUnderTest.log( category, message );
      verify( connector ).log( source, category, message );
   }//End Method
   
   @Test public void shouldForwardProgressToDigestManager(){
      systemUnderTest.progress( progress, message );
      verify( connector ).progress( source, progress, message );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullSource(){
      systemUnderTest = new ObjectDigestImpl( null );
   }//End Method

}//End Class
