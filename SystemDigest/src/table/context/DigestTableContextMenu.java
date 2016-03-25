/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package table.context;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import table.model.DigestTableController;

/**
 * The {@link DigestTableContextMenu} provides a {@link ContextMenu} that allows
 * the user to change the {@link DigestTable}.
 */
public class DigestTableContextMenu extends ContextMenu {
   
   static final String DISCONNECT = "Disconnect from Digest";
   static final String CONNECT = "Connect to Digest";
   static final String CANCEL = "Cancel";

   /**
    * Constructs a new {@link DigestTableContextMenu}.
    * @param table the {@link DigestTableController} to send instructions to. 
    */
   DigestTableContextMenu( DigestTableController controller ) {
      MenuItem connectionControl = new MenuItem( DISCONNECT );
      connectionControl.setOnAction( event -> controlConnection( controller, connectionControl ) );
      
      MenuItem cancel = new MenuItem( CANCEL );
      cancel.setOnAction( event -> hide() );
      
      getItems().addAll( 
            connectionControl,
            new SeparatorMenuItem(),
            cancel
      );
      
      setAutoHide( true );
   }//End Constructor

   /**
    * Method to control the connection to the system digest.
    * @param table the {@link DigestTableController} to send instructions to.
    * @param connectionControl the {@link MenuItem} to update.
    */
   private void controlConnection( DigestTableController controller, MenuItem connectionControl ) {
      if ( connectionControl.getText() == CONNECT ) {
         controller.connect();
         connectionControl.setText( DISCONNECT );
      } else {
         controller.disconnect();
         connectionControl.setText( CONNECT );
      }
   }//End Method
   
   /**
    * {@link ContextMenu#isShowing()}.
    * @return true if showing.
    */
   public boolean friendly_isShowing(){
      return isShowing();
   }//End Method
   
   /**
    * {@link ContextMenu#show(Node, double, double)}.
    * @param anchor the anchor for the {@link ContextMenu}.
    * @param screenX the location to place the {@link ContextMenu}.
    * @param screenY the location to place the {@link ContextMenu}.
    */
   public void friendly_show( Node anchor, double screenX, double screenY ) {
      show( anchor, screenX, screenY );
   }//End Method

}//End Class
