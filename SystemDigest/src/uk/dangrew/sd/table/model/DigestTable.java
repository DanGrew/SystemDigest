/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
 package uk.dangrew.sd.table.model;

import java.time.LocalDateTime;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import uk.dangrew.sd.table.context.DigestTableContextMenuOpener;
import uk.dangrew.sd.table.presentation.CategoryColouredCell;
import uk.dangrew.sd.table.presentation.DigestTableRowLimit;

/**
 * The {@link DigestTable} provides a simple {@link TableView} that receives logs and {@link uk.dangrew.sd.core.message.Messages}
 * from system's digest and displays them.
 */
public class DigestTable extends TableView< DigestTableRow > {

   static final String COLUMN_TITLE_TIMESTAMP = "Timestamp";
   static final String COLUMN_TITLE_CATEGORY = "Category";
   static final String COLUMN_TITLE_SOURCE = "Source";
   static final String COLUMN_TITLE_MESSAGE = "Message";
   
   private final DigestTableController controller;

   /**
    * Constructs a new {@link DigestTable}.
    */
   public DigestTable() {
      this( new DigestTableController() );
   }//End Constructor
      
   /**
    * Constructs a new {@link DigestTable}.
    * @param controller the {@link DigestTableController}.
    */
   DigestTable( DigestTableController controller ) {
      this.controller = controller;
      this.controller.associate( this );
      initialiseColumns();
      setOnContextMenuRequested( new DigestTableContextMenuOpener( this, controller ) );
   }//End Constructor
   
   /**
    * Setter for the {@link DigestTableRowLimit}, applying ti immediately.
    * @param limit the {@link DigestTableRowLimit}.
    */
   public void setRowLimit( DigestTableRowLimit limit ) {
      controller.setTableRowLimit( limit );
   }//End Method
   
   /**
    * Method to get the rows in the {@link DigestTable}. Should be used over {@link #getItems()} because
    * this method is tested.
    * @return the {@link ObservableList} of {@link DigestTableRow}, {@link #getItems()}.
    */
   public ObservableList< DigestTableRow > getRows(){
      return getItems();
   }//End Method
   
   /**
    * Method to initialise the {@link TableColumn}s and their associated constraints.
    */
   private void initialiseColumns(){
      TableColumn< DigestTableRow, LocalDateTime > timestampColumn = new TableColumn<>( COLUMN_TITLE_TIMESTAMP );
      timestampColumn.prefWidthProperty().bind( widthProperty().divide( 6 ) );
      timestampColumn.setCellValueFactory( object -> new SimpleObjectProperty<>( object.getValue().getTimestamp() ) );
      timestampColumn.setCellFactory( column -> new CategoryColouredCell< LocalDateTime >() );
      getColumns().add( timestampColumn );
      
      TableColumn< DigestTableRow, String > categoryColumn = new TableColumn<>( COLUMN_TITLE_CATEGORY );
      categoryColumn.prefWidthProperty().bind( widthProperty().divide( 6 ) );
      categoryColumn.setCellValueFactory( object -> new SimpleStringProperty( object.getValue().getCategory().getName() ) );
      categoryColumn.setCellFactory( column -> new CategoryColouredCell< String >() );
      getColumns().add( categoryColumn );
      
      TableColumn< DigestTableRow, String > sourceColumn = new TableColumn<>( COLUMN_TITLE_SOURCE );
      sourceColumn.prefWidthProperty().bind( widthProperty().divide( 6 ) );
      sourceColumn.setCellValueFactory( object -> new SimpleStringProperty( object.getValue().getSource().getIdentifier() ) );
      sourceColumn.setCellFactory( column -> new CategoryColouredCell< String >() );
      getColumns().add( sourceColumn );
      
      TableColumn< DigestTableRow, String > messageColumn = new TableColumn<>( COLUMN_TITLE_MESSAGE );
      messageColumn.prefWidthProperty().bind( widthProperty().divide( 2 ) );
      messageColumn.setCellValueFactory( object -> new SimpleStringProperty( object.getValue().getMessage().getMessage() ) );
      messageColumn.setCellFactory( column -> new CategoryColouredCell< String >() );
      getColumns().add( messageColumn );
   }//End Method

}//End Class
