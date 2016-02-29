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
import core.message.Message;
import core.source.Source;

/**
 * The {@link DigestTableRow} is responsible for providing a read only object that the {@link DigestTable}
 * can interact with and display, representing a single row of data.
 */
public class DigestTableRow {

   private LocalTime timestamp;
   private Source source;
   private Category category;
   private Message message;
   
   /**
    * Constructs a new {@link DigestTableRow}.
    * @param timestamp the {@link LocalTime} as a timestamp of the {@link Message}.
    * @param source the {@link Source} of the message.
    * @param category the {@link Category} of the message.
    * @param message the {@link Message}.
    */
   public DigestTableRow( LocalTime timestamp, Source source, Category category, Message message ) {
      if ( timestamp == null ) throw new IllegalArgumentException( "Null timestamp provided." );
      if ( source == null ) throw new IllegalArgumentException( "Null source provided." );
      if ( category == null ) throw new IllegalArgumentException( "Null category provided." );
      if ( message == null ) throw new IllegalArgumentException( "Null message provided." );
      
      this.timestamp = timestamp;
      this.source = source;
      this.category = category;
      this.message = message;
   }//End Constructor
   
   /**
    * Getter for the timestamp of the {@link Message}.
    * @return the {@link LocalTime} timestamp.
    */
   public LocalTime getTimestamp() {
      return timestamp;
   }//End Method

   /**
    * Getter for the {@link Source} of the {@link Message}.
    * @return the {@link Source}.
    */
   public Source getSource() {
      return source;
   }//End Method

   /**
    * Getter for the {@link Category} of the {@link Message}.
    * @return the {@link Category}.
    */
   public Category getCategory() {
      return category;
   }//End Method

   /**
    * Getter for the {@link Message}.
    * @return the {@link Message}.
    */
   public Message getMessage() {
      return message;
   }//End Method

}//End Class
