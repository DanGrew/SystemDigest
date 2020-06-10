/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
 package uk.dangrew.sd.table.model;

import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.sd.core.category.Categories;
import uk.dangrew.sd.core.category.Category;
import uk.dangrew.sd.core.message.Messages;
import uk.dangrew.sd.core.source.SourceImpl;
import uk.dangrew.sd.digest.object.ObjectDigest;
import uk.dangrew.sd.digest.object.ObjectDigestImpl;
import uk.dangrew.sd.table.context.DigestTableContextMenuOpener;
import uk.dangrew.sd.table.presentation.CategoryColouredCell;
import uk.dangrew.sd.table.presentation.DigestTableRowLimit;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * {@link DigestTable} test.
 */
public class DigestTableTest {

   @Mock private DigestTableController controller;
   private ObjectDigest digest;
   private DigestTable systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      digest = new ObjectDigestImpl( new SourceImpl( this ) );
      systemUnderTest = new DigestTable( controller );
   }//End Method
   
   @Ignore
   @Test public void manual() throws Exception {
      TestApplication.launch( () -> { return new BorderPane( new DigestTable() ); } );
      
      ObjectDigest digest = new ObjectDigestImpl( new SourceImpl( this ) );
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
      
      Thread.sleep( 1000000000 );
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
               LocalDateTime.now(), 
               new SourceImpl( this ), 
               Categories.objectAllocation(), 
               Messages.simpleMessage( "anything" ) 
      );
      DigestTableRow row2 = new DigestTableRow( 
               LocalDateTime.now(), 
               new SourceImpl( new Object() ), 
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
   
   @Test public void shouldBeConnectedToSystemDigest(){
      systemUnderTest = new DigestTable();
      assertThat( systemUnderTest.getRows(), hasSize( 0 ) );
      sendDigestMessagesAndWait( Categories.error() );
      assertThat( systemUnderTest.getRows(), hasSize( 1 ) );
   }//End Method
   
   /**
    * Method to send messages through the system for the given {@link Category}s.
    * @param categories the {@link Category}s to send.
    */
   private void sendDigestMessagesAndWait( Category... categories ) {
      for ( Category category : categories ) {
         digest.log( category, Messages.simpleMessage( "any message" ) );
      }
      JavaFxThreading.runAndWait();
   }//End Method
   
   @Test public void shouldProvideDigestTableContextMenu(){
      assertThat( systemUnderTest.getOnContextMenuRequested(), instanceOf( DigestTableContextMenuOpener.class ) );
   }//End Method
   
   @Test public void shouldAssociateWithController(){
      verify( controller ).associate( systemUnderTest );
   }//End Method
   
   @Test public void shouldSetRowLimit(){
      systemUnderTest.setRowLimit( DigestTableRowLimit.TenThousand );
      verify( controller ).setTableRowLimit( DigestTableRowLimit.TenThousand );
   }//End Method
   
}//End Class
