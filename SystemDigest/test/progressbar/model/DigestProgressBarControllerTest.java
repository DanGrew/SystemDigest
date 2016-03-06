/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
 package progressbar.model;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import core.message.Message;
import core.progress.Progress;
import core.source.SourceImpl;
import core.source.Source;
import digest.object.ObjectDigest;
import digest.object.ObjectDigestImpl;

/**
 * {@link DigestTableController} test.
 */
public class DigestProgressBarControllerTest {

   private Source source;
   @Mock private Progress progress;
   @Mock private Message message;
   @Mock private DigestProgressBars digestProgressBar;
   private ObjectDigest objectDigest;
   private DigestProgressBarController systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      PlatformImpl.startup( () -> {} );
      MockitoAnnotations.initMocks( this );
      
      source = new SourceImpl( this );
      objectDigest = new ObjectDigestImpl( source );
      systemUnderTest = new DigestProgressBarController( digestProgressBar );
   }//End Method
   
   @Test public void shouldForwardMessagesOntoBar() {
      objectDigest.progress( progress, message );
      
      PlatformImpl.runAndWait( () -> {} );
      verify( digestProgressBar ).handleProgress( source, progress, message );
   }//End Method
   
}//End Class
