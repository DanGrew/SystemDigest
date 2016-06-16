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
 * {@link MessageImpl} provides a basic implementation of the {@link Message} interface.
 */
public class MessageImpl implements Message {

   private String messageText;
   
   /**
    * Constructs a new {@link MessageImpl}.
    * @param messageText the message.
    */
   public MessageImpl( String messageText ) {
      this.messageText = messageText;
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public String getMessage() {
      return messageText;
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ( ( messageText == null ) ? 0 : messageText.hashCode() );
      return result;
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public boolean equals( Object obj ) {
      if ( this == obj ) {
         return true;
      }
      if ( obj == null ) {
         return false;
      }
      if ( !( obj instanceof MessageImpl ) ) {
         return false;
      }
      MessageImpl other = ( MessageImpl ) obj;
      if ( messageText == null ) {
         if ( other.messageText != null ) {
            return false;
         }
      } else if ( !messageText.equals( other.messageText ) ) {
         return false;
      }
      return true;
   }//End Method
   
}//End Class
