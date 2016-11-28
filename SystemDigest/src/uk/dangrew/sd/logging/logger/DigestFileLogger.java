/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.logging.logger;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import uk.dangrew.sd.core.category.Category;
import uk.dangrew.sd.core.lockdown.DigestMessageReceiver;
import uk.dangrew.sd.core.lockdown.DigestMessageReceiverImpl;
import uk.dangrew.sd.core.lockdown.DigestReceiverConnection;
import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.source.Source;
import uk.dangrew.sd.logging.location.LoggingLocationProtocol;
import uk.dangrew.sd.utility.threading.ProtectedRunnable;

/**
 * The {@link DigestFileLogger} is responsible for logging digest {@link Message}s to
 * a {@link LoggingLocationProtocol}.
 */
public class DigestFileLogger implements ProtectedRunnable, DigestReceiverConnection, DigestMessageReceiver {
   
   private final DigestReceiverConnection receiver;
   private final BlockingQueue< String > logQueue;
   
   private LoggingLocationProtocol protocol;
   
   /**
    * Constructs a new {@link DigestFileLogger}. 
    * @param protocol the {@link LoggingLocationProtocol} to log to.
    */
   public DigestFileLogger() {
      this.receiver = new DigestMessageReceiverImpl( this );
      this.logQueue = new LinkedBlockingQueue<>();
   }//End Constructor
   
   /**
    * Method to set the {@link LoggingLocationProtocol}. This must be called in
    * order for logging to function.
    * @param protocol the {@link LoggingLocationProtocol}, cannot be null.
    */
   public void setFileLocation( LoggingLocationProtocol protocol ) {
      if ( protocol == null ) {
         throw new IllegalArgumentException( "Must provide non null file location." );
      }
      
      this.protocol = protocol;
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void disconnect() {
      receiver.disconnect();
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void connect() {
      receiver.connect();
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public boolean isConnected() {
      return receiver.isConnected();
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void log( LocalDateTime timestamp, Source source, Category category, Message message ) {
      logQueue.add( format( timestamp, source, category, message ) );
   }//End Method

   /**
    * {@inheritDoc}
    * <p>
    * Should only be run from within a {@link Thread}, the SystemDigest provides a 
    * {@link uk.dangrew.sd.utility.threading.ThreadedWrapper} to do this.
    */
   @Override public void iterate() {
      if ( protocol == null ) {
         return;
      }
      
      if ( logQueue.isEmpty() ) {
         return;
      }
      
      String log;
      try {
         log = logQueue.take();
         protocol.logToLocation( log );
      } catch ( InterruptedException exception ) {
         exception.printStackTrace();
      }
   }//End Method

   /**
    * Method to format the given log information into a writable log line, with a line break.
    * @param timestamp the {@link LocalDateTime} timestamp of the log.
    * @param source the {@link Source} logged.
    * @param category the {@link Category} logged.
    * @param message the {@link Message} logged.
    * @return a formatted {@link String}.
    */
   static String format( LocalDateTime timestamp, Source source, Category category, Message message ) {
      return timestamp.toString() + ", " + source.getIdentifier() + ", " + category.getName() + ", " + message.getMessage() + "\n";
   }//End Method
}//End Class
