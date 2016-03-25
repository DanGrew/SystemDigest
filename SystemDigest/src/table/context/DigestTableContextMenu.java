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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import table.model.DigestTableController;
import table.presentation.DigestTableRowLimit;

/**
 * The {@link DigestTableContextMenu} provides a {@link ContextMenu} that allows
 * the user to change the {@link DigestTable}.
 */
public class DigestTableContextMenu extends ContextMenu {
   
   static final String DISCONNECT = "Disconnect from Digest";
   static final String CONNECT = "Connect to Digest";
   static final String TABLE_LIMIT = "Table limit";
   static final String CLEAR_TABLE = "Clear Table";
   static final String CANCEL = "Cancel";
   
   private final DigestTableController controller;

   /**
    * Constructs a new {@link DigestTableContextMenu}.
    * @param table the {@link DigestTableController} to send instructions to. 
    */
   DigestTableContextMenu( DigestTableController controller ) {
      this.controller = controller;
      
      MenuItem connectionControl = new MenuItem( DISCONNECT );
      connectionControl.setOnAction( event -> controlConnection( controller, connectionControl ) );
      
      Menu rowLimits = constructRowLimitItems();
      
      MenuItem clearTable = new MenuItem( CLEAR_TABLE );
      clearTable.setOnAction( event -> controller.clearTable() );
      
      MenuItem cancel = new MenuItem( CANCEL );
      cancel.setOnAction( event -> hide() );
      
      getItems().addAll( 
            connectionControl,
            rowLimits,
            clearTable,
            new SeparatorMenuItem(),
            cancel
      );
      
      setAutoHide( true );
   }//End Constructor
   
   /**
    * Method to construct the {@link DigestTableRowLimit} {@link MenuItem}s for this {@link ContextMenu}.
    * @return the {@link Menu} to add in.
    */
   private Menu constructRowLimitItems(){
      Menu rowLimits = new Menu( TABLE_LIMIT );
      
      ToggleGroup limitGroup = new ToggleGroup();
      for ( DigestTableRowLimit limit : DigestTableRowLimit.values() ) {
         RadioMenuItem limitItem = new RadioMenuItem( limit.readable() );
         limitItem.setToggleGroup( limitGroup );
         limitItem.setOnAction( event -> controller.setTableRowLimit( limit ) );
         rowLimits.getItems().add( limitItem );
      }
      
      return rowLimits;
   }//End Method

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
