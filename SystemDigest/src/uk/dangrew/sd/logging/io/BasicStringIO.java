/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.logging.io;

import uk.dangrew.kode.utility.io.IoCommon;

import java.io.*;
import java.util.Scanner;

/**
 * {@link BasicStringIO} provides basic read and write functionality for files.
 */
public class BasicStringIO {

   private final BasicStringIODigest digest;
   
   /**
    * Constructs a new {@link BasicStringIO} with a default {@link BasicStringIODigest}.
    */
   public BasicStringIO() {
      this( new BasicStringIODigest() );
   }//End Constructor
   
   /**
    * Constructs a new {@link BasicStringIO} with the given {@link BasicStringIODigest}.
    * @param digest the {@link BasicStringIODigest} to attach to.
    */
   BasicStringIO( BasicStringIODigest digest ) {
      this.digest = digest;
      this.digest.attachSource( this );
   }//End Constructor

   /**
    * Method to write the given {@link String} to the given {@link File}.
    * @param file the {@link File} to write to.
    * @param object the {@link String} to write.
    * @param append whether to append to the file or replace.
    * @return true if written successfully.
    */
   public boolean write( File file, String object, boolean append ) {
      if ( object == null ) {
         throw new NullPointerException( "Write object must not be null." );
      }
      
      if ( !file.exists() ) {
         try {
            if ( file.getParentFile() != null ) {
               file.getParentFile().mkdirs();
            }
            file.createNewFile();
         } catch ( IOException e ) {
            digest.failedToSetupFiles( file, e );
            return false;
         }
      }
      
      try ( FileWriter writer = new FileWriter( file, append ) ) {
         writer.write( object );
         return true;
      } catch ( IOException e ) {
         digest.failedToWriteToFile( file, e );
         return false;
      }
   }//End Method

}//End Class
