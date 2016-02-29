/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
 package table.model;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.time.LocalTime;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import core.category.Categories;
import core.message.Messages;
import core.source.SimpleSourceImpl;
import digest.object.ObjectDigest;
import digest.object.ObjectDigestImpl;
import graphics.launch.TestApplication;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;
import table.presentation.CategoryColouredCell;

/**
 * {@link DigestTable} test.
 */
public class DigestTableTest {

   private DigestTable systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      TestApplication.startPlatform();
      systemUnderTest = new DigestTable();
   }//End Method
   
   @Ignore
   @Test public void manual() throws Exception {
      TestApplication.launch( () -> { return new BorderPane( new DigestTable() ); } );
      
      ObjectDigest digest = new ObjectDigestImpl( new SimpleSourceImpl( this ) );
      digest.log( Categories.objectAllocation(), Messages.simpleMessage( "oh yes! a new object..." ) );
      
      digest.log( Categories.objectAllocation(), Messages.simpleMessage( "something else" ) );
      digest.log( Categories.objectAllocation(), Messages.simpleMessage( "and something further" ) );
      digest.log( Categories.processingSequence(), Messages.simpleMessage( "first" ) );
      digest.log( Categories.processingSequence(), Messages.simpleMessage( "second" ) );
      digest.log( Categories.processingSequence(), Messages.simpleMessage( "third" ) );
      digest.log( Categories.processingSequence(), Messages.simpleMessage( "fourth" ) );
      digest.log( Categories.objectAllocation(), Messages.simpleMessage( "still going" ) );
      
      for ( int i = 0; i < 10000; i++ ) {
         digest.log( Categories.processingSequence(), Messages.simpleMessage( "looping " + i ) );
         Thread.sleep( 1 );
      }
      
      Thread.sleep( 100000 );
   }//End Method
   
   @Test public void shouldHaveTimestampColumn(){
      @SuppressWarnings("unchecked") //fail fast 
      TableColumn< DigestTableRow, LocalTime > column = ( TableColumn< DigestTableRow, LocalTime > )systemUnderTest.getColumns().get( 0 );
      assertThat( column.getText(), is( DigestTable.COLUMN_TITLE_TIMESTAMP ) );
   }//End Method

   @Test public void shouldHaveCategoriesColumn() {
      @SuppressWarnings("unchecked") //fail fast 
      TableColumn< DigestTableRow, LocalTime > column = ( TableColumn< DigestTableRow, LocalTime > )systemUnderTest.getColumns().get( 1 );
      assertThat( column.getText(), is( DigestTable.COLUMN_TITLE_CATEGORY ) );
   }//End Method

   @Test public void shouldHaveSourceColumn() {
      @SuppressWarnings("unchecked") //fail fast 
      TableColumn< DigestTableRow, LocalTime > column = ( TableColumn< DigestTableRow, LocalTime > )systemUnderTest.getColumns().get( 2 );
      assertThat( column.getText(), is( DigestTable.COLUMN_TITLE_SOURCE ) );
   }//End Method

   @Test public void shouldHaveMessageColumn() {
      @SuppressWarnings("unchecked") //fail fast 
      TableColumn< DigestTableRow, LocalTime > column = ( TableColumn< DigestTableRow, LocalTime > )systemUnderTest.getColumns().get( 3 );
      assertThat( column.getText(), is( DigestTable.COLUMN_TITLE_MESSAGE ) );
   }//End Method
   
   @Test public void shouldPropertionallyShareColumns() throws InterruptedException{
      TestApplication.launch( () -> { return new BorderPane( systemUnderTest ); } );
      assertThat( systemUnderTest.getColumns().get( 0 ).getPrefWidth(), is( systemUnderTest.getWidth() / 6.0 ) );
      assertThat( systemUnderTest.getColumns().get( 1 ).getPrefWidth(), is( systemUnderTest.getWidth() / 6.0 ) );
      assertThat( systemUnderTest.getColumns().get( 2 ).getPrefWidth(), is( systemUnderTest.getWidth() / 6.0 ) );
      assertThat( systemUnderTest.getColumns().get( 3 ).getPrefWidth(), is( systemUnderTest.getWidth() / 2.0 ) );
   }//End Method
   
   @Test public void shouldGetItemsForGetRows(){
      assertThat( systemUnderTest.getItems(), is( systemUnderTest.getRows() ) );
   }//End Method
   
   @SuppressWarnings("unchecked") //using mockito's mock to get an example column, not important to enforce generics.
   @Test public void eachRowShouldHaveColouredText(){
      for ( TableColumn< DigestTableRow, ? > column : systemUnderTest.getColumns() ) {
         assertThat( column.getCellFactory().call( mock( TableColumn.class ) ), Matchers.instanceOf( CategoryColouredCell.class ) );
      }
   }//End Method
   
   @Test public void shouldDisplayRowInformationFromDigestTableRow(){
      DigestTableRow row1 = new DigestTableRow( 
               LocalTime.now(), 
               new SimpleSourceImpl( this ), 
               Categories.objectAllocation(), 
               Messages.simpleMessage( "anything" ) 
      );
      DigestTableRow row2 = new DigestTableRow( 
               LocalTime.now(), 
               new SimpleSourceImpl( new Object() ), 
               Categories.processingSequence(), 
               Messages.simpleMessage( "something else" ) 
      );
      systemUnderTest.getRows().addAll( row1, row2 );
      
      assertThat( systemUnderTest.getColumns().get( 0 ).getCellData( 0 ), is( row1.getTimestamp() ) );
      assertThat( systemUnderTest.getColumns().get( 0 ).getCellData( 1 ), is( row2.getTimestamp() ) );
      assertThat( systemUnderTest.getColumns().get( 0 ).getCellData( 2 ), nullValue() );
      
      assertThat( systemUnderTest.getColumns().get( 1 ).getCellData( 0 ), is( row1.getCategory().getName() ) );
      assertThat( systemUnderTest.getColumns().get( 1 ).getCellData( 1 ), is( row2.getCategory().getName() ) );
      assertThat( systemUnderTest.getColumns().get( 1 ).getCellData( 2 ), nullValue() );
      
      assertThat( systemUnderTest.getColumns().get( 2 ).getCellData( 0 ), is( row1.getSource().getIdentifier() ) );
      assertThat( systemUnderTest.getColumns().get( 2 ).getCellData( 1 ), is( row2.getSource().getIdentifier() ) );
      assertThat( systemUnderTest.getColumns().get( 2 ).getCellData( 2 ), nullValue() );
      
      assertThat( systemUnderTest.getColumns().get( 3 ).getCellData( 0 ), is( row1.getMessage().getMessage() ) );
      assertThat( systemUnderTest.getColumns().get( 3 ).getCellData( 1 ), is( row2.getMessage().getMessage() ) );
      assertThat( systemUnderTest.getColumns().get( 3 ).getCellData( 2 ), nullValue() );
   }//End Method
   
}//End Class
