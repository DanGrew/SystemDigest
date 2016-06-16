/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.core.lockdown;

import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.progress.Progress;
import uk.dangrew.sd.core.source.Source;

/**
 * The {@link DigestProgressReceiverImpl} is responsible for connecting to the {@link DigestManager}. It
 * should be used to wrap a {@link DigestProgressReceiver} that wants to receive the system digest.
 */
public class DigestProgressReceiverImpl implements DigestProgressReceiver {
   
   private DigestProgressReceiver actualReceiver;
   
   /**
    * Constructs a new {@link DigestProgressReceiverImpl}.
    * @param actualReceiver the {@link DigestProgressReceiver} that is actually interested in the digest.
    */
   public DigestProgressReceiverImpl( DigestProgressReceiver actualReceiver ) {
      this.actualReceiver = actualReceiver;
      DigestManager.getInstance().registerProgressReceiver( this );
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public void progress( Source source, Progress progress, Message message ) {
      actualReceiver.progress( source, progress, message );
   }//End Method

}//End Class
