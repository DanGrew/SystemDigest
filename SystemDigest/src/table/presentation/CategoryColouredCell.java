/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
 package table.presentation;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import table.model.DigestTableRow;

/**
 * The {@link CategoryColouredCell} provides a {@link TableCell} that colours the text associated
 * based on the associated {@link core.category.Category} in the {@link DigestTableRow}.
 */
public class CategoryColouredCell< DataTypeT > extends TableCell< DigestTableRow, DataTypeT > {

   /**
    * {@inheritDoc}
    */
   @Override protected void updateItem( DataTypeT item, boolean empty ) {
      super.updateItem( item, empty );

      @SuppressWarnings("unchecked") // This is actually an unchecked due to raw types in JavaFx.
      TableRow< DigestTableRow > row = ( TableRow< DigestTableRow > ) getTableRow();

      if ( item !=null && row != null && row.getItem() != null ) {
         setText( item.toString() );
         setTextFill( row.getItem().getCategory().getColour() );
      } else {
         setText( null );
      }
   }//End Method
}//End Class
