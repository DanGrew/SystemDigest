/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package core.lockdown;

import core.category.Category;
import core.message.Message;
import core.source.Source;

/**
 * The {@link DigestMessageReceiver} provides an interface for an object that wishes to receive
 * information from the system digest for categorised messages.
 */
public interface DigestMessageReceiver extends DigestReceiver {
   
   /**
    * Method to log a {@link Message} for the given {@link Category} for the given {@link Source}.
    * @param source the {@link Source} of the {@link Message}.
    * @param category the {@link Category} of the {@link Message}.
    * @param message the {@link Message}.
    */
   public void log( Source source, Category category, Message message );

}//End Interface
