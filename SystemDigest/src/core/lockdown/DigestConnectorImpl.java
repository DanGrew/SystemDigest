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
 * The {@link DigestConnectorImpl} provides a basic implementation of  the {@link DigestConnector}.
 */
public class DigestConnectorImpl implements DigestConnector {
   
   private DigestManager digestManager;
   
   /**
    * Constructs a new {@link DigestConnectorImpl}.
    */
   public DigestConnectorImpl() {
      digestManager = DigestManager.getInstance();
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public void log( Source source, Category category, Message message ) {
      digestManager.log( source, category, message );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void progress( Source source, Progress progress, Message message ) {
      digestManager.progress( source, progress, message );
   }//End Method

}//End Class
