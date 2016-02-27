/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package core.lockdown;

import java.util.LinkedHashSet;
import java.util.Set;

import core.category.Category;
import core.message.Message;
import core.progress.Progress;
import core.source.Source;

/**
 * The {@link DigestManager} is responsible for connecting objects that providing logging
 * and {@link Message}s to {@link DigestReceiver}s that want the information.
 */
class DigestManager {
   
   private static DigestManager instance = new DigestManager();

   /**
    * Getter for the {@link DigestManager} instance.
    * @return the {@link DigestManager}.
    */
   static DigestManager getInstance() {
      return instance;
   }//End Method
   
   /**
    * Setter for the {@link DigestManager} instance.
    * @param instance the {@link DigestManager} to set.
    */
   static void setInstance( DigestManager instance ) {
      DigestManager.instance = instance;
   }//End Method
   
   private Set< DigestReceiver > receivers;
   
   /**
    * Constructs a new {@link DigestManager}.
    */
   DigestManager() {
      receivers = new LinkedHashSet<>();
   }//End Constructor

   /**
    * Method to log a {@link Message} for the given {@link Category} for the given {@link Source}.
    * @param source the {@link Source} of the {@link Message}.
    * @param category the {@link Category} of the {@link Message}.
    * @param message the {@link Message}.
    */
   void log( Source source, Category category, Message message ) {
      receivers.forEach( connector -> connector.log( source, category, message ) );
   }//End Method

   /**
    * Method to register the given {@link DigestReceiver} for logs and progress.
    * @param receiver the {@link DigestReceiver} to receive information. 
    */
   void registerReceiver( DigestReceiver receiver ) {
      if ( receivers.contains( receiver ) ) return;
      
      receivers.add( receiver );
   }//End Method

   /**
    * Method to log a {@link Message} indicating the given {@link Progress} for the given {@link Source}.
    * @param source the {@link Source} of the {@link Message}.
    * @param progress the {@link Progress} being reported.
    * @param message the {@link Message}.
    */
   void progress( Source source, Progress progress, Message message ) {
      receivers.forEach( connector -> connector.progress( source, progress, message ) );
   }//End Method

}//End Class
