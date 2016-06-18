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
import java.io.IOException;

import uk.dangrew.sd.core.category.Categories;
import uk.dangrew.sd.core.message.Messages;
import uk.dangrew.sd.core.source.SourceImpl;
import uk.dangrew.sd.digest.object.ObjectDigestImpl;

/**
 * The {@link BasicStringIODigest} provides the logging options that the {@link BasicStringIO}
 * can use when processing.
 */
class BasicStringIODigest extends ObjectDigestImpl {
   
   static final String BASIC_STRING_IO_NAME = "Basic String IO";
   static final String FAILED_TO_SETUP = "Failed to setup files: ";
   static final String FAILED_TO_WRITE = "Failed to write files: ";
   static final String FILE_NOT_AVAILABLE = "File not available";

   /**
    * Method to attach the given {@link BasicStringIO} as {@link uk.dangrew.sd.core.source.Source}
    * to this {@link ObjectDigestImpl}.
    * @param source the {@link BasicStringIO}.
    */
   void attachSource( BasicStringIO source ) {
      super.attachSource( new SourceImpl( source, BASIC_STRING_IO_NAME ) );
   }//End Method
   
   /**
    * Method to log that the {@link BasicStringIO} has failed to set up the {@link File}
    * required to write to.
    * @param file the {@link File} being worked with.
    * @param exception the {@link IOException} raised.
    */
   void failedToSetupFiles( File file, IOException exception ) {
      log( Categories.error(), Messages.simpleMessage( format( FAILED_TO_SETUP, file, exception ) ) );
   }//End Method

   /**
    * Method to log that the {@link BasicStringIO} has failed to write to the {@link File}.
    * @param file the {@link File} being worked with.
    * @param exception the {@link IOException} raised.
    */
   void failedToWriteToFile( File file, IOException exception ) {
      log( Categories.error(), Messages.simpleMessage( format( FAILED_TO_WRITE, file, exception ) ) );
   }//End Method
   
   /**
    * Method to format the input into a readable, loggable {@link String}.
    * @param failure the {@link String} description of the failure.
    * @param file the {@link File} being worked with.
    * @param exception the {@link IOException} raised.
    * @return a formatted {@link String}.
    */
   static String format( String failure, File file, IOException exception ) {
      return failure + "\n" + format( file, exception ); 
   }//End Method
   
   /**
    * Method to format the input into a readable, loggable {@link String}.
    * @param file the {@link File} being worked with.
    * @param exception the {@link IOException} raised.
    * @return a formatted {@link String}.
    */
   static String format( File file, IOException exception ) {
      StringBuilder builder = new StringBuilder();
      builder.append( "File: " );
      if ( file == null || file.getAbsolutePath() == null ) {
         builder.append( FILE_NOT_AVAILABLE );
      } else {
         builder.append( file.getAbsolutePath() ); 
      }
      builder.append( "\nException: " ).append( exception.getMessage() );
      return builder.toString();
   }//End Method

}//End Class
