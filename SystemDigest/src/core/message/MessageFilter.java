/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package core.message;

import core.category.Category;
import core.source.Source;

/**
 * The {@link MessageFilter} provides the interface required for an object to filter messages
 * logged to a {@link core.lockdown.DigestMessageReceiver}. 
 */
@FunctionalInterface 
public interface MessageFilter {

   /**
    * Method to determine whether the given digest information is applicable for the
    * associated receiver.
    * @param source the {@link Source}.
    * @param category the {@link Category}.
    * @param message the {@link Message}.
    * @return true if relevant, false to ignore.
    */
   public boolean matches( Source source, Category category, Message message );

}//End Interface
