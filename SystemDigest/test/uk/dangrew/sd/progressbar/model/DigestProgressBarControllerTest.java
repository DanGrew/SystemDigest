/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
 package uk.dangrew.sd.progressbar.model;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.progress.Progress;
import uk.dangrew.sd.core.source.Source;
import uk.dangrew.sd.core.source.SourceImpl;
import uk.dangrew.sd.digest.object.ObjectDigest;
import uk.dangrew.sd.digest.object.ObjectDigestImpl;
import uk.dangrew.sd.progressbar.model.DigestProgressBarController;
import uk.dangrew.sd.progressbar.model.DigestProgressBars;

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
