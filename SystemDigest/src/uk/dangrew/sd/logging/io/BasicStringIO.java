/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.logging.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * {@link BasicStringIO} provides basic read and write functionality for files.
 */
public class BasicStringIO {

   /**
    * Method to read the {@link String} data from the given {@link File}.
    * @param file the {@link File} to read from.
    * @return the {@link String} read, or null if anything goes wrong.
    */
   public String read( File file ) {
      if ( !file.exists() ) {
         return null;
      }
      return readFileIntoString( file );
   }//End Method
   
   /**
    * Method to read the given {@link Scanner} and extract a {@link String}.
    * @param scanner the {@link Scanner} to read.
    * @return the {@link String} containing all text from the {@link Scanner}.
    */
   String readScannerContentAndClose( Scanner scanner ) {
      String content = scanner.useDelimiter( "//Z" ).next();
      scanner.close();
      return content;
   }//End Method
   
   /**
    * Method to read a text file into a {@link String}.
    * @param file the {@link File} to read into a {@link String}.
    * @return the {@link String} containing all text from the {@link File}.
    */
   String readFileIntoString( File file ) {
      try ( InputStream stream = new FileInputStream( file ) ){
         Scanner scanner = new Scanner( stream );
         return readScannerContentAndClose( scanner );
      } catch ( IOException e ) {
         return null;
      } 
   }//End Method

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
            //TODO - digest.
            return false;
         }
      }
      
      try ( FileWriter writer = new FileWriter( file, append ) ) {
         writer.write( object );
         return true;
      } catch ( IOException e ) {
         //TODO - digest.
         return false;
      }
   }//End Method

}//End Class
