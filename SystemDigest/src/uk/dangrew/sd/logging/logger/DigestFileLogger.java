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
import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.source.Source;
import uk.dangrew.sd.logging.location.LoggingLocationProtocol;

/**
 * The {@link DigestFileLogger} is responsible for logging digest {@link Message}s to
 * a {@link LoggingLocationProtocol}.
 */
public class DigestFileLogger implements Runnable, DigestMessageReceiver {
   
   private final DigestMessageReceiver receiver;
   private final LoggingLocationProtocol protocol;
   private final BlockingQueue< String > logQueue;
   
   private Integer shutdownCounter = null;
   
   /**
    * Constructs a new {@link DigestFileLogger}. 
    * @param protocol the {@link LoggingLocationProtocol} to log to.
    */
   public DigestFileLogger( LoggingLocationProtocol protocol ) {
      this.receiver = new DigestMessageReceiverImpl( this );
      this.protocol = protocol;
      this.logQueue = new LinkedBlockingQueue<>();
   }//End Constructor

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
   @Override public void log( LocalDateTime timestamp, Source source, Category category, Message message ) {
      logQueue.add( format( timestamp, source, category, message ) );
   }//End Method

   /**
    * {@inheritDoc}
    * <p>
    * Should only be run from within a {@link Thread}, the SystemDigest provides a 
    * {@link uk.dangrew.sd.utility.threading.ThreadedWrapper} to do this.
    */
   @Override public void run() {
      while ( shutdownCounter == null || shutdownCounter > 0 ){
         if ( shutdownCounter != null ) {
            shutdownCounter--;
         }
         
         if ( logQueue.isEmpty() ) {
            continue;
         }
         
         String log;
         try {
            log = logQueue.take();
            protocol.logToLocation( log );
         } catch ( InterruptedException exception ) {
            exception.printStackTrace();
         }
      }
   }//End Method
   
   /**
    * Method to shutdown the logger which will stop processing the queued logs
    * after the given log iterations.
    * @param iterations the number of times to check for new logs before stopping.
    */
   public void shutdown( int iterations ) {
      shutdownCounter = iterations;
   }//End Method

   /**
    * Method to format the given log information into a writable log file.
    * @param timestamp the {@link LocalDateTime} timestamp of the log.
    * @param source the {@link Source} logged.
    * @param category the {@link Category} logged.
    * @param message the {@link Message} logged.
    * @return a formatted {@link String}.
    */
   static String format( LocalDateTime timestamp, Source source, Category category, Message message ) {
      return timestamp.toString() + ", " + source.getIdentifier() + ", " + category.getName() + ", " + message.getMessage();
   }//End Method
}//End Class
