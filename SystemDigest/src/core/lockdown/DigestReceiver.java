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
import core.progress.Progress;
import core.source.Source;

/**
 * The {@link DigestReceiver} provides an interface for an object that wishes to recieve
 * information from the system digest.
 */
public interface DigestReceiver {
   
   /**
    * Method to log a {@link Message} for the given {@link Category} for the given {@link Source}.
    * @param source the {@link Source} of the {@link Message}.
    * @param category the {@link Category} of the {@link Message}.
    * @param message the {@link Message}.
    */
   public void log( Source source, Category category, Message message );

   /**
    * Method to log a {@link Message} indicating the given {@link Progress} for the given {@link Source}.
    * @param source the {@link Source} of the {@link Message}.
    * @param progress the {@link Progress} being reported.
    * @param message the {@link Message}.
    */
   public void progress( Source source, Progress progress, Message message );

}//End Interface
