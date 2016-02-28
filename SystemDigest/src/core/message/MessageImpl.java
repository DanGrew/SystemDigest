/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package core.message;

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

}//End Class
