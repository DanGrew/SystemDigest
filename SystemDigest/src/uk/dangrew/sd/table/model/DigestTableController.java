/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
 package uk.dangrew.sd.table.model;

import java.time.LocalDateTime;

import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.sd.core.category.Category;
import uk.dangrew.sd.core.lockdown.DigestMessageReceiver;
import uk.dangrew.sd.core.lockdown.DigestMessageReceiverImpl;
import uk.dangrew.sd.core.lockdown.DigestReceiverConnection;
import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.source.Source;
import uk.dangrew.sd.table.presentation.DigestTableRowLimit;

/**
 * The {@link DigestTableController} is responsible for receiving information from the digest
 * and passing it on to the {@link DigestTable} is a format fit for the uk.dangrew.sd.table.
 */
public class DigestTableController implements DigestReceiverConnection, DigestMessageReceiver {

   private final DigestReceiverConnection digestConnection;
   private DigestTable digestTable;
   private DigestTableRowLimit rowLimit;
   
   /**
    * Constructs a new {@link DigestTableController}.
    */
   DigestTableController() {
      this.digestConnection = new DigestMessageReceiverImpl( this );
      this.rowLimit = DigestTableRowLimit.Unlimited;
   }//End Constructor

   /**
    * Associates the controller with the given.
    * @param table the associated {@link DigestTable}.
    */
   void associate( DigestTable table ) {
      this.digestTable = table;
   }//End Method
   
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
    * {@inheritDoc}
    */
   @Override public boolean isConnected() {
      return digestConnection.isConnected();
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
         JavaFxThreading.runLater( () -> {
            limit.limit( digestTable.getRows() );
         } );
      }
      this.rowLimit = limit;
   }//End Method
   
   /**
    * Method to clear the {@link DigestTable}.
    */
   public void clearTable() {
      JavaFxThreading.runLater( () -> {
         digestTable.getRows().clear();
      } );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void log( LocalDateTime timestamp, Source source, Category category, Message message ) {
      JavaFxThreading.runLater( () -> {
         digestTable.getRows().add( 0, new DigestTableRow( timestamp, source, category, message ) );
         rowLimit.limit( digestTable.getRows() );
      } );
   }//End Method
   
}//End Class
