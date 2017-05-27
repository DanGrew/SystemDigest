/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.viewer.basic;

import javafx.scene.layout.BorderPane;
import uk.dangrew.sd.progressbar.model.DigestProgressBars;
import uk.dangrew.sd.table.model.DigestTable;
import uk.dangrew.sd.table.presentation.DigestTableRowLimit;

/**
 * The {@link DigestViewer} provides a basic arrangement of {@link DigestProgressBars} and {@link DigestTable}
 * that can be easily added into an external gui.
 */
public class DigestViewer extends BorderPane {
   
   private final DigestTable table;
   
   /**
    * Constructs a new {@link DigestViewer}.
    */
   public DigestViewer() {
      this( 200, 200 );
   }//End Constructor

   /**
    * Constructs a new {@link DigestViewer}.
    * @param width the preferred width, applied to the {@link DigestTable}.
    * @param height the preferred height, applied to the {@link DigestTable}.
    */
   public DigestViewer( double width, double height ) {
      this( new DigestTable(), width, height );
   }//End Constructor
      
   /**
    * Constructs a new {@link DigestViewer}.
    * @param table the {@link DigestTable}.
    * @param width the preferred width, applied to the {@link DigestTable}.
    * @param height the preferred height, applied to the {@link DigestTable}.
    */
   DigestViewer( DigestTable table, double width, double height ) {
      this.setCenter( new DigestProgressBars() );
      this.table = table;
      this.table.setPrefHeight( height );
      this.table.setPrefWidth( width );
      this.setBottom( table );
   }//End Constructor
   
   /**
    * Method to set the {@link DigestTableRowLimit}.
    * @param limit the {@link DigestTableRowLimit} to immediately apply.
    */
   public void setTableRowLimit( DigestTableRowLimit limit ) {
      table.setRowLimit( limit );
   }//End Method

}//End Class
