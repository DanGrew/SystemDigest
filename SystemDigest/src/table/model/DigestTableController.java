/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
 package table.model;

import java.time.LocalTime;

import com.sun.javafx.application.PlatformImpl;

import core.category.Category;
import core.lockdown.DigestMessageReceiver;
import core.lockdown.DigestMessageReceiverImpl;
import core.message.Message;
import core.source.Source;
import table.presentation.DigestTableRowLimit;

/**
 * The {@link DigestTableController} is responsible for receiving information from the digest
 * and passing it on to the {@link DigestTable} is a format fit for the table.
 */
public class DigestTableController implements DigestMessageReceiver {

   private final DigestTable digestTable;
   private final DigestMessageReceiver digestConnection;
   private DigestTableRowLimit rowLimit;
   
   /**
    * Constructs a new {@link DigestTableController}.
    * @param digestTable the associated {@link DigestTable}.
    */
   DigestTableController( DigestTable digestTable ) {
      this.digestTable = digestTable;
      this.digestConnection = new DigestMessageReceiverImpl( this );
      this.rowLimit = DigestTableRowLimit.Unlimited;
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void connect() {
      digestConnection.connect();
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void disconnect() {
      digestConnection.disconnect();
   }//End Method
   
   /**
    * Method to set the {@link DigestTableRowLimit} to use.
    * @param limit the {@link DigestTableRowLimit} to set. This immediately applies to the
    * the {@link DigestTable}. Null is acceptable, but will default to {@link DigestTableRowLimit#Unlimited}.
    */
   public void setTableRowLimit( DigestTableRowLimit limit ) {
      if ( limit == null ) {
         this.rowLimit = DigestTableRowLimit.Unlimited;
         return;
      }
      
      if ( limit != this.rowLimit ) {
         PlatformImpl.runLater( () -> {
            limit.limit( digestTable.getRows() );
         } );
      }
      this.rowLimit = limit;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void log( Source source, Category category, Message message ) {
      PlatformImpl.runLater( () -> {
         digestTable.getRows().add( 0, new DigestTableRow( LocalTime.now(), source, category, message ) );
         rowLimit.limit( digestTable.getRows() );
      } );
   }//End Method
   
}//End Class
