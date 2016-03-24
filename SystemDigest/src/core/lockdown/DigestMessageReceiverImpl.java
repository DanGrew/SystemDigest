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
import core.source.Source;

/**
 * The {@link DigestMessageReceiverImpl} is responsible for connecting to the {@link DigestManager}. It
 * should be used to wrap a {@link DigestMessageReceiver} that wants to receive messages the system digest.
 */
public class DigestMessageReceiverImpl implements DigestMessageReceiver {
   
   private DigestMessageReceiver actualReceiver;
   
   /**
    * Constructs a new {@link DigestMessageReceiverImpl}.
    * @param actualReceiver the {@link DigestMessageReceiver} that is actually interested in the digest.
    */
   public DigestMessageReceiverImpl( DigestMessageReceiver actualReceiver ) {
      this.actualReceiver = actualReceiver;
      connect();
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
   @Override public void disconnect() {
      DigestManager.getInstance().unregisterMessageReceiver( this );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void connect() {
      DigestManager.getInstance().registerMessageReceiver( this );
   }//End Method

}//End Class
