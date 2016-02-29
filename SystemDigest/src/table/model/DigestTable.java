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

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import table.presentation.CategoryColouredCell;

/**
 * The {@link DigestTable} provides a simple {@link TableView} that receives logs and {@link core.message.Messages}
 * from system's digest and displays them.
 */
public class DigestTable extends TableView< DigestTableRow > {

   static final String COLUMN_TITLE_TIMESTAMP = "Timestamp";
   static final String COLUMN_TITLE_CATEGORY = "Category";
   static final String COLUMN_TITLE_SOURCE = "Source";
   static final String COLUMN_TITLE_MESSAGE = "Message";

   /**
    * Constructs a new {@link DigestTable}.
    */
   public DigestTable() {
      initialiseColumns();
      new DigestTableController( this );
   }//End Constructor
   
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
      TableColumn< DigestTableRow, LocalTime > timestampColumn = new TableColumn<>( COLUMN_TITLE_TIMESTAMP );
      timestampColumn.prefWidthProperty().bind( widthProperty().divide( 6 ) );
      timestampColumn.setCellValueFactory( object -> new SimpleObjectProperty<>( object.getValue().getTimestamp() ) );
      timestampColumn.setCellFactory( column -> { return new CategoryColouredCell< LocalTime >(); } );
      getColumns().add( timestampColumn );
      
      TableColumn< DigestTableRow, String > categoryColumn = new TableColumn<>( COLUMN_TITLE_CATEGORY );
      categoryColumn.prefWidthProperty().bind( widthProperty().divide( 6 ) );
      categoryColumn.setCellValueFactory( object -> new SimpleStringProperty( object.getValue().getCategory().getName() ) );
      categoryColumn.setCellFactory( column -> { return new CategoryColouredCell< String >(); } );
      getColumns().add( categoryColumn );
      
      TableColumn< DigestTableRow, String > sourceColumn = new TableColumn<>( COLUMN_TITLE_SOURCE );
      sourceColumn.prefWidthProperty().bind( widthProperty().divide( 6 ) );
      sourceColumn.setCellValueFactory( object -> new SimpleStringProperty( object.getValue().getSource().getIdentifier() ) );
      sourceColumn.setCellFactory( column -> { return new CategoryColouredCell< String >(); } );
      getColumns().add( sourceColumn );
      
      TableColumn< DigestTableRow, String > messageColumn = new TableColumn<>( COLUMN_TITLE_MESSAGE );
      messageColumn.prefWidthProperty().bind( widthProperty().divide( 2 ) );
      messageColumn.setCellValueFactory( object -> new SimpleStringProperty( object.getValue().getMessage().getMessage() ) );
      messageColumn.setCellFactory( column -> { return new CategoryColouredCell< String >(); } );
      getColumns().add( messageColumn );
   }//End Method
   
}//End Class
