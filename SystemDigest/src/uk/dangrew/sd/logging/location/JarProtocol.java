/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */

package uk.dangrew.sd.logging.location;

import java.io.File;
import java.security.CodeSource;

/**
 * The {@link JarProtocol} is a {@link FileLocationProtocol} for establishing a {@link File}
 * in the same location as the jar, optionally in a sub folder.
 */
public class JarProtocol implements FileLocationProtocol {
   
   private static final String FILE_SEPARATOR = "/";
   private final File source;
   
   /**
    * Constructs a new {@link JarProtocol}.
    * @param subFolder the name of the sub folder the file will be placed in.
    * @param filename the filename to read/write to.
    * @param relativeTo the {@link Class} providing the {@link CodeSource} to place
    * the {@link File} relative to.
    */
   protected JarProtocol( String subFolder, String filename, Class< ? > relativeTo ) {
      if ( filename == null || relativeTo == null ) {
         throw new NullPointerException( "Parameters cannot be null." );
      }
      
      CodeSource codeSource = relativeTo.getProtectionDomain().getCodeSource();
      if ( codeSource == null ) {
         throw new IllegalArgumentException( "Can only use class that has a code source. Its not clear what"
                  + "determines this. Current project classes and dependencies appear to be acceptable. Java"
                  + "source such as String.class is not acceptable." );
      }
      
      File jarPath = new File( codeSource.getLocation().getPath() );
      StringBuilder propertiesPath = new StringBuilder( jarPath.getParentFile().getAbsolutePath() );
      
      if ( subFolder != null ) {
         propertiesPath.append( FILE_SEPARATOR ).append( subFolder );
      }
      
      propertiesPath.append( FILE_SEPARATOR ).append( filename );
      source = new File( propertiesPath.toString() );
   }//End Constructor
   
   /**
    * Method to get the {@link File} source.
    * @return the {@link File}.
    */
   File getSource(){
      return source;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public String getLocation() {
      return source.getAbsolutePath();
   }//End Method

}//End Class
