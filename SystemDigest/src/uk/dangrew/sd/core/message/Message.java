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
 * The {@link Message} provides an interface for information that can be shared that forms
 * the digest.
 */
public interface Message {

   /**
    * Method to get the {@link String} message provided.
    * @return the {@link String} message.
    */
   public String getMessage();

}//End Interface
