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
   
   /**
    * Method to reset the instance of the {@link DigestManager}.
    */
   public static void reset() {
      setInstance( new DigestManager() );
   }//End Method
   
   private Set< DigestMessageReceiver > messageReceivers;
   private Set< DigestProgressReceiver > progressReceivers;
   
   /**
    * Constructs a new {@link DigestManager}.
    */
   DigestManager() {
      messageReceivers = new LinkedHashSet<>();
      progressReceivers = new LinkedHashSet<>();
   }//End Constructor

   /**
    * Method to log a {@link Message} for the given {@link Category} for the given {@link Source}.
    * @param source the {@link Source} of the {@link Message}.
    * @param category the {@link Category} of the {@link Message}.
    * @param message the {@link Message}.
    */
   void log( Source source, Category category, Message message ) {
      messageReceivers.forEach( connector -> connector.log( source, category, message ) );
   }//End Method

   /**
    * Method to register the given {@link DigestMessageReceiver} for messages.
    * @param receiver the {@link DigestMessageReceiver} to receive information. 
    */
   void registerMessageReceiver( DigestMessageReceiver receiver ) {
      if ( messageReceivers.contains( receiver ) ) return;
      
      messageReceivers.add( receiver );
   }//End Method

   /**
    * Method to log a {@link Message} indicating the given {@link Progress} for the given {@link Source}.
    * @param source the {@link Source} of the {@link Message}.
    * @param progress the {@link Progress} being reported.
    * @param message the {@link Message}.
    */
   void progress( Source source, Progress progress, Message message ) {
      progressReceivers.forEach( connector -> connector.progress( source, progress, message ) );
   }//End Method

   /**
    * Method to register the given {@link DigestProgressReceiver} for progress.
    * @param receiver the {@link DigestProgressReceiver} to receive information. 
    */
   void registerProgressReceiver( DigestProgressReceiver progressReceiver ) {
      if ( progressReceivers.contains( progressReceiver ) ) return;
      
      progressReceivers.add( progressReceiver );
   }//End Method

}//End Class
