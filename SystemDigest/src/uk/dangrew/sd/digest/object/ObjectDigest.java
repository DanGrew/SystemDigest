/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.digest.object;

import uk.dangrew.sd.core.category.Category;
import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.progress.Progress;

/**
 * The {@link ObjectDigest} provides the interface for a specific object, allowing it to contribute
 * to the system's digest.
 */
public interface ObjectDigest {
   
   /**
    * Method to log a {@link Message} for the given {@link Category} for the given {@link uk.dangrew.sd.core.source.Source}.
    * @param category the {@link Category} of the {@link Message}.
    * @param message the {@link Message}.
    */
   public void log( Category category, Message message );

   /**
    * Method to log a {@link Message} indicating the given {@link Progress} for the given {@link uk.dangrew.sd.core.source.Source}.
    * @param progress the {@link Progress} being reported.
    * @param message the {@link Message}.
    */
   public void progress( Progress progress, Message message );

}//End Interface
