/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package core.lockdown;

import java.util.LinkedHashMap;
import java.util.Map;

import core.category.Category;
import core.message.Message;
import core.message.MessageFilter;
import core.progress.Progress;
import core.progress.ProgressFilter;
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
   
   private Map< DigestMessageReceiver, MessageFilter > messageReceivers;
   private Map< DigestProgressReceiver, ProgressFilter > progressReceivers;
   
   /**
    * Constructs a new {@link DigestManager}.
    */
   DigestManager() {
      messageReceivers = new LinkedHashMap<>();
      progressReceivers = new LinkedHashMap<>();
   }//End Constructor

   /**
    * Method to log a {@link Message} for the given {@link Category} for the given {@link Source}.
    * @param source the {@link Source} of the {@link Message}.
    * @param category the {@link Category} of the {@link Message}.
    * @param message the {@link Message}.
    */
   void log( Source source, Category category, Message message ) {
      messageReceivers.forEach( ( receiver, filter ) -> {
         if ( filter == null || filter.matches( source, category, message ) ) {
            receiver.log( source, category, message );
         }
      } );
   }//End Method

   /**
    * Method to register the given {@link DigestMessageReceiver} for messages.
    * @param receiver the {@link DigestMessageReceiver} to receive information. 
    */
   void registerMessageReceiver( DigestMessageReceiver receiver ) {
      registerMessageReceiver( receiver, null );
   }//End Method

   /**
    * Method to register the given {@link DigestMessageReceiver} for messages.
    * @param messageReceiver the {@link DigestMessageReceiver} to receive information. 
    * @param filter the {@link MessageFilter}.
    */
   void registerMessageReceiver( DigestMessageReceiver messageReceiver, MessageFilter filter ) {
      messageReceivers.put( messageReceiver, filter );
   }//End Method
   
   /**
    * Method to log a {@link Message} indicating the given {@link Progress} for the given {@link Source}.
    * @param source the {@link Source} of the {@link Message}.
    * @param progress the {@link Progress} being reported.
    * @param message the {@link Message}.
    */
   void progress( Source source, Progress progress, Message message ) {
      progressReceivers.forEach( ( receiver, filter ) -> {
         if ( filter == null || filter.matches( source, progress, message ) ) {
            receiver.progress( source, progress, message );
         }
      } );
   }//End Method

   /**
    * Method to register the given {@link DigestProgressReceiver} for progress.
    * @param receiver the {@link DigestProgressReceiver} to receive information. 
    */
   void registerProgressReceiver( DigestProgressReceiver progressReceiver ) {
      registerProgressReceiver( progressReceiver, null );
   }//End Method

   /**
    * Method to register the given {@link DigestProgressReceiver} for progress.
    * @param progressReceiver the {@link DigestProgressReceiver} to receive information.
    * @param filter the {@link ProgressFilter}. 
    */
   void registerProgressReceiver( DigestProgressReceiver progressReceiver, ProgressFilter filter ) {
      progressReceivers.put( progressReceiver, filter );
   }//End Method

}//End Class
