/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.core.lockdown;

import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.progress.Progress;
import uk.dangrew.sd.core.source.Source;

/**
 * The {@link DigestProgressReceiver} provides an interface for an object that wishes to receive
 * information from the system digest.
 */
public interface DigestProgressReceiver {
   
   /**
    * Method to log a {@link Message} indicating the given {@link Progress} for the given {@link Source}.
    * @param source the {@link Source} of the {@link Message}.
    * @param progress the {@link Progress} being reported.
    * @param message the {@link Message}.
    */
   public void progress( Source source, Progress progress, Message message );

}//End Interface
