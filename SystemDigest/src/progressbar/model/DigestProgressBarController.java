/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package progressbar.model;

import com.sun.javafx.application.PlatformImpl;

import core.lockdown.DigestProgressReceiver;
import core.lockdown.DigestProgressReceiverImpl;
import core.message.Message;
import core.progress.Progress;
import core.source.Source;

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
      PlatformImpl.runLater( () -> {
         digestProgressBars.handleProgress( source, progress, message );
      } );
   }//End Method

}//End Class
