/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */

package uk.dangrew.sd.logging.location;

import uk.dangrew.sd.logging.io.BasicStringIO;

/**
 * The {@link JarLoggingProtocol} provides a method of holding a {@link File} in the
 * same place as the jar and providing read and write access to it.
 */
public class JarLoggingProtocol implements LoggingLocationProtocol {
   
   private final JarProtocol protocol;
   private final BasicStringIO stringIO;
   
   /**
    * Constructs a new {@link JarLoggingProtocol}.
    * @param filename the filename to read/write to.
    * @param relativeTo the {@link Class} providing the {@link CodeSource} to place
    * the {@link File} relative to.
    */
   public JarLoggingProtocol( String filename, Class< ? > relativeTo ) {
      this( null, filename, relativeTo );
   }//End Constructor
   
   /**
    * Constructs a new {@link JarLoggingProtocol}.
    * @param subFolder the name of the sub folder the file will be placed in.
    * @param filename the filename to read/write to.
    * @param relativeTo the {@link Class} providing the {@link CodeSource} to place
    * the {@link File} relative to.
    */
   public JarLoggingProtocol( String subFolder, String filename, Class< ? > relativeTo ) {
      this( new BasicStringIO(), new JarProtocol( subFolder, filename, relativeTo ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link JarLoggingProtocol}.
    * @param stringIO the {@link BasicStringIO} for read and writing.
    */
   JarLoggingProtocol( BasicStringIO stringIO, JarProtocol protocol ) {
      this.protocol = protocol;
      this.stringIO = stringIO;
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public String getLocation() {
      return protocol.getLocation();
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public boolean logToLocation( String logLine ) {
      return stringIO.write( protocol.getSource(), logLine, true );
   }//End Method

}//End Class
