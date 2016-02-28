/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package core.lockdown;

import core.category.Category;
import core.message.Message;
import core.progress.Progress;
import core.source.Source;

/**
 * The {@link DigestReceiverImpl} is responsible for connecting to the {@link DigestManager}. It
 * should be used to wrap a {@link DigestReceiver} that wants to receive the system digest.
 */
public class DigestReceiverImpl implements DigestReceiver {
   
   private DigestReceiver actualReceiver;
   
   /**
    * Constructs a new {@link DigestReceiverImpl}.
    * @param actualReceiver the {@link DigestReceiver} that is actually interested in the digest.
    */
   public DigestReceiverImpl( DigestReceiver actualReceiver ) {
      this.actualReceiver = actualReceiver;
      DigestManager.getInstance().registerReceiver( this );
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public void log( Source source, Category category, Message message ) {
      actualReceiver.log( source, category, message );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void progress( Source source, Progress progress, Message message ) {
      actualReceiver.progress( source, progress, message );
   }//End Method

}//End Class
