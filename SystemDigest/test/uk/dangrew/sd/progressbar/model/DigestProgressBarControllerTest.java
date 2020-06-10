/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
 package uk.dangrew.sd.progressbar.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.progress.Progress;
import uk.dangrew.sd.core.source.Source;
import uk.dangrew.sd.core.source.SourceImpl;
import uk.dangrew.sd.digest.object.ObjectDigest;
import uk.dangrew.sd.digest.object.ObjectDigestImpl;

import static org.mockito.Mockito.verify;

/**
 * {@link uk.dangrew.sd.table.model.DigestTableController} test.
 */
public class DigestProgressBarControllerTest {

   private Source source;
   @Mock private Progress progress;
   @Mock private Message message;
   @Mock private DigestProgressBars digestProgressBar;
   private ObjectDigest objectDigest;
   private DigestProgressBarController systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      JavaFxThreading.startup();
      MockitoAnnotations.initMocks( this );
      
      source = new SourceImpl( this );
      objectDigest = new ObjectDigestImpl( source );
      systemUnderTest = new DigestProgressBarController( digestProgressBar );
   }//End Method
   
   @Test public void shouldForwardMessagesOntoBar() {
      objectDigest.progress( progress, message );

      JavaFxThreading.runAndWait();
      verify( digestProgressBar ).handleProgress( source, progress, message );
   }//End Method
   
}//End Class
