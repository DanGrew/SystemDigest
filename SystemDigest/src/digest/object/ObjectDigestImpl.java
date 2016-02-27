/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package digest.object;

import core.category.Category;
import core.lockdown.DigestConnector;
import core.lockdown.DigestConnectorImpl;
import core.message.Message;
import core.progress.Progress;
import core.source.Source;

/**
 * {@link ObjectDigestImpl} is a base implementation of the {@link ObjectDigest}.
 */
public class ObjectDigestImpl implements ObjectDigest {

   private DigestConnector digestConnector;
   private Source source;
   
   /**
    * Constructs a new {@link ObjectDigestImpl}.
    * @param source the {@link Source} associated.
    */
   public ObjectDigestImpl( Source source ) {
      this( source, new DigestConnectorImpl() );
   }//End Constructor
   
   /**
    * Constructs a new {@link ObjectDigestImpl}.
    * @param source the {@link Source} associated.
    * @param digestConnector the {@link DigestConnector} to connect to the system digest.
    */
   ObjectDigestImpl( Source source, DigestConnector digestConnector ) {
      if ( source == null ) throw new IllegalArgumentException( "Source not provided." );
      
      this.source = source;
      this.digestConnector = digestConnector;
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public void log( Category category, Message message ) {
      digestConnector.log( source, category, message );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void progress( Progress progress, Message message ) {
      digestConnector.progress( source, progress, message );
   }//End Method

}//End Class
