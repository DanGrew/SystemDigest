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

import core.category.Category;
import core.lockdown.DigestMessageReceiver;
import core.lockdown.DigestMessageReceiverImpl;
import core.message.Message;
import core.source.Source;

/**
 * The {@link DigestTableController} is responsible for receiving information from the digest
 * and passing it on to the {@link DigestTable} is a format fit for the table.
 */
public class DigestTableController implements DigestMessageReceiver {

   private DigestTable digestTable;
   
   /**
    * Constructs a new {@link DigestTableController}.
    * @param digestTable the associated {@link DigestTable}.
    */
   public DigestTableController( DigestTable digestTable ) {
      this.digestTable = digestTable;
      new DigestMessageReceiverImpl( this );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void log( Source source, Category category, Message message ) {
      digestTable.getRows().add( 0, new DigestTableRow( LocalTime.now(), source, category, message ) );
   }//End Method

}//End Class
