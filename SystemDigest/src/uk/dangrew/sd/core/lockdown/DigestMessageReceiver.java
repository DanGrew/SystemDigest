/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.core.lockdown;

import java.time.LocalDateTime;

import uk.dangrew.sd.core.category.Category;
import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.source.Source;

/**
 * The {@link DigestMessageReceiver} provides an interface for an object that wishes to receive
 * information from the system digest for categorised messages.
 */
@FunctionalInterface
public interface DigestMessageReceiver {
   
   /**
    * Method to log a {@link Message} for the given {@link Category} for the given {@link Source}.
    * @param timestamp the {@link LocalDateTime} timestamp ofthe log.
    * @param source the {@link Source} of the {@link Message}.
    * @param category the {@link Category} of the {@link Message}.
    * @param message the {@link Message}.
    */
   public void log( LocalDateTime timestamp, Source source, Category category, Message message );

}//End Interface
