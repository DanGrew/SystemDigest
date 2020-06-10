/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.progressbar.model;

import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.sd.core.lockdown.DigestProgressReceiver;
import uk.dangrew.sd.core.lockdown.DigestProgressReceiverImpl;
import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.progress.Progress;
import uk.dangrew.sd.core.source.Source;

/**
 * The {@link DigestProgressBarController} is responsible for receiving digest progress
 * and providing it to the {@link DigestProgressBars}.
 */
public class DigestProgressBarController implements DigestProgressReceiver {

   private DigestProgressBars digestProgressBars;
   
   /**
    * Constructs a new {@link DigestProgressBarController}.
    * @param digestProgresBar the {@link DigestProgressBars}.
    */
   DigestProgressBarController( DigestProgressBars digestProgresBar ) {
      this.digestProgressBars = digestProgresBar;
      new DigestProgressReceiverImpl( this );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void progress( Source source, Progress progress, Message message ) {
      JavaFxThreading.runLater( () -> {
         digestProgressBars.handleProgress( source, progress, message );
      } );
   }//End Method

}//End Class
