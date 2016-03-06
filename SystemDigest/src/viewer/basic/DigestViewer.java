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
      setCenter( new DigestProgressBars() );
      setBottom( new DigestTable() );
   }//End Constructor

}//End Class
