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
      this( digestConnector );
      attachSource( source );
   }//End Constructor
   
   /**
    * Constructs a new {@link ObjectDigestImpl} with a default {@link DigestConnector}.
    * Note that no {@link Source} is attached and must be done separately.
    */
   protected ObjectDigestImpl() {
      this( new DigestConnectorImpl() );
   }//End Constructor
   
   /**
    * Constructs a new {@link ObjectDigestImpl}.
    * Note that no {@link Source} is attached and must be done separately.
    * @param digestConnector the {@link DigestConnector}.
    */
   private ObjectDigestImpl( DigestConnector digestConnector ) {
      this.digestConnector = digestConnector;
   }//End Class
   
   /**
    * Method to attach the {@link Source} to this {@link ObjectDigestImpl}. This is vital
    * for this object to function correctly.
    * @param source the {@link Source} to attach.
    */
   protected final void attachSource( Source source ){
      if ( source == null ) throw new IllegalArgumentException( "Source not provided." );
      
      this.source = source;      
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void log( Category category, Message message ) {
      if ( source == null ) throw new IllegalStateException( "No source attached." );
      
      digestConnector.log( source, category, message );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void progress( Progress progress, Message message ) {
      if ( source == null ) throw new IllegalStateException( "No source attached." );
      
      digestConnector.progress( source, progress, message );
   }//End Method

}//End Class
