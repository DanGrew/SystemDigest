/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package table.context;

import javafx.event.EventHandler;
import javafx.scene.input.ContextMenuEvent;
import table.model.DigestTable;

/**
 * {@link DigestTableContextMenuOpener} provides an {@link EventHandler} to control
 * the opening and closing of the {@link DigestTableContextMenu}.
 */
public class DigestTableContextMenuOpener implements EventHandler< ContextMenuEvent >{

   private DigestTable table;
   private DigestTableContextMenu contextMenu;
   
   /**
    * Constructs a new {@link DigestTableContextMenuOpener}.
    * @param display the {@link DigestTable} the {@link DigestTableContextMenu}
    * is for.
    */
   public DigestTableContextMenuOpener( DigestTable table ) {
      this( table, new DigestTableContextMenu( table ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link DigestTableContextMenuOpener}.
    * @param table the {@link DigestTable} the {@link DigestTableContextMenu}
    * is for.
    * @param menu the {@link DigestTableContextMenu} to show and hide.
    */
   DigestTableContextMenuOpener( DigestTable table, DigestTableContextMenu menu ) {
      this.table = table;
      this.contextMenu = menu;
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public void handle( ContextMenuEvent event ) {
      if ( contextMenu.friendly_isShowing() ) return;
      
      contextMenu.friendly_show( table, event.getScreenX(), event.getScreenY() );
   }//End Method

}//End Class
