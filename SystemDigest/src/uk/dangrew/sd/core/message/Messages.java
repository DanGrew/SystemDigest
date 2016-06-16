/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.core.message;

/**
 * {@link Messages} provides a source of default {@link Messages} and {@link Message} construction.
 */
public class Messages {
   
   /**
    * Method to construct a simple {@link Message} for some {@link String} information.
    * @param text the text in the {@link Message}.
    * @return the {@link Message} constructed.
    */
   public static Message simpleMessage( String text ) {
      return new MessageImpl( text );
   }//End Method

}//End Class
