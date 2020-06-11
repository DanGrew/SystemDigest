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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import uk.dangrew.kode.synchronization.SynchronizedObservableMap;
import uk.dangrew.sd.core.category.Category;
import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.message.MessageFilter;
import uk.dangrew.sd.core.progress.Progress;
import uk.dangrew.sd.core.progress.ProgressFilter;
import uk.dangrew.sd.core.source.Source;

/**
 * The {@link DigestManager} is responsible for connecting objects that providing logging
 * and {@link Message}s to {@link DigestMessageReceiver}s that want the information.
 */
public class DigestManager {

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
   
   private final Map< DigestMessageReceiver, MessageFilter > messageReceivers;
   private final Map< DigestProgressReceiver, ProgressFilter > progressReceivers;
   private final Supplier< LocalDateTime > timestampProvider;
   
   DigestManager() {
      this( LocalDateTime::now );
   }//End Constructor
   
   /**
    * Constructs a new {@link DigestManager}.
    * @param timestampProvider the {@link Supplier} to decouple time.
    */
   DigestManager( Supplier< LocalDateTime > timestampProvider ) {
      this.messageReceivers = new SynchronizedObservableMap<>( new LinkedHashMap<>() );
      this.progressReceivers = new SynchronizedObservableMap<>( new LinkedHashMap<>() );
      this.timestampProvider = timestampProvider;
   }//End Constructor

   /**
    * Method to log a {@link Message} for the given {@link Category} for the given {@link Source}.
    * @param source the {@link Source} of the {@link Message}.
    * @param category the {@link Category} of the {@link Message}.
    * @param message the {@link Message}.
    */
   void log( Source source, Category category, Message message ) {
      LocalDateTime timestamp = timestampProvider.get();
      
      messageReceivers.forEach( ( receiver, filter ) -> {
         if ( filter == null || filter.matches( source, category, message ) ) {
            receiver.log( timestamp, source, category, message );
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

   /**
    * Method to unregister the given {@link DigestMessageReceiver}. It will no longer receive messages.
    * @param messageReceiver the {@link DigestMessageReceiver} to unregister.
    */
   void unregisterMessageReceiver( DigestMessageReceiver messageReceiver ) {
      messageReceivers.remove( messageReceiver );
   }//End Method
   
   /**
    * Method to unregister the given {@link DigestProgressReceiver}. It will no longer receive progress.
    * @param progressReceiver the {@link DigestProgressReceiver} to unregister.
    */
   void unregisterProgressReceiver( DigestProgressReceiver progressReceiver ) {
      progressReceivers.remove( progressReceiver );
   }//End Method
   
   /**
    * Method to determine whether the given {@link DigestMessageReceiver} is registered.
    * @param receiver the {@link DigestMessageReceiver} in question.
    * @return true if registered.
    */
   boolean isRegisteredForMessages( DigestMessageReceiver receiver ) {
      return messageReceivers.containsKey( receiver );
   }//End Method
   
   /**
    * Method to determine whether the given {@link DigestProgressReceiver} is registered.
    * @param receiver the {@link DigestProgressReceiver} in question.
    * @return true if registered.
    */
   boolean isRegisteredForProgress( DigestProgressReceiver receiver ) {
      return progressReceivers.containsKey( receiver );
   }//End Method

}//End Class
