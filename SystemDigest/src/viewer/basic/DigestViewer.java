/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package viewer.basic;

import javafx.scene.layout.BorderPane;
import progressbar.model.DigestProgressBars;
import table.model.DigestTable;

/**
 * The {@link DigestViewer} provides a basic arrangement of {@link DigestProgressBars} and {@link DigestTable}
 * that can be easily added into an external gui.
 */
public class DigestViewer extends BorderPane {
   
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
      setCenter( new DigestProgressBars() );
      DigestTable table = new DigestTable();
      table.setPrefHeight( height );
      table.setPrefWidth( width );
      setBottom( table );
   }//End Constructor

}//End Class
