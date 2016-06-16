/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.core.progress;

import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.source.Source;

/**
 * The {@link ProgressFilter} provides the interface required for an object to filter progress
 * logged to a {@link uk.dangrew.sd.core.lockdown.DigestProgressReceiver}. 
 */
@FunctionalInterface
public interface ProgressFilter {

   /**
    * Method to determine whether the given digest information is applicable for the
    * associated receiver.
    * @param source the {@link Source}.
    * @param progress the {@link Progress}.
    * @param message the {@link Message}.
    * @return true if relevant, false to ignore.
    */
   public boolean matches( Source source, Progress progress, Message message );

}//End Interface
