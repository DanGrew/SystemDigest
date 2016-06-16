/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.table.presentation;

import javafx.collections.ObservableList;

/**
 * The {@link DigestTableRowLimit} is responsible for limiting the {@link uk.dangrew.sd.table.model.DigestTable}
 * rows to a certain, configured amount.
 */
public enum DigestTableRowLimit {
   
   OneHundred( 100 ),
   FiveHundred( 500 ),
   OneThousand( 1000 ),
   FiveThousand( 5000 ),
   TenThousand( 10000 ),
   Unlimited( null );
   
   static final String UNLIMITED = "Unlimited";
   private final Integer limit;
   private final String readable;
   
   /**
    * Constructs a new {@link DigestTableRowLimit}.
    * @param limit the max number of rows.
    */
   private DigestTableRowLimit( Integer limit ) {
      this.limit = limit;
      if ( limit == null ) {
         readable = UNLIMITED; 
      } else {
         readable = limit.toString();
      }
   }//End Constructor

   /**
    * Getter for the max number of rows.
    * @return the limit, can be null for unlimited.
    */
   public Integer getLimit(){
      return limit;
   }//End Method
   
   /**
    * Method to limit the given {@link ObservableList} to the associated limit.
    * @param listToLimit the {@link ObservableList} to limit.
    */
   public void limit( ObservableList< ? > listToLimit ) {
      if ( limit == null ) return;
      
      if ( listToLimit.size() <= limit ) return;
      
      listToLimit.remove( limit, listToLimit.size() );
   }//End Method
   
   /**
    * Method to get the readable form of this limit.
    * @return the readable, displayable form.
    */
   public String readable(){
      return readable;
   }//End Method

}//End Enum
