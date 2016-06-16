/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.table.context;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.ContextMenuEvent;
import uk.dangrew.sd.core.category.Categories;
import uk.dangrew.sd.core.message.Messages;
import uk.dangrew.sd.core.source.SourceImpl;
import uk.dangrew.sd.graphics.launch.TestApplication;
import uk.dangrew.sd.table.model.DigestTable;
import uk.dangrew.sd.table.model.DigestTableController;
import uk.dangrew.sd.table.model.DigestTableRow;
import uk.dangrew.sd.table.presentation.DigestTableRowLimit;

/**
 * {@link DigestTableContextMenu} test.
 */
public class DigestTableContextMenuTest {
   
   //Note that these are not object requirements, but useful for the test.
   private static final int CONNECTION = 0;
   private static final int TABLE_LIMIT = 1;
   private static final int CLEAR_TABLE = 2;
   private static final int FIRST_SEPARATOR = 3;
   private static final int CANCEL = 4;
   
   private DigestTable fullyLaunchedTable;
   @Mock private DigestTableController controller;
   private DigestTableContextMenu systemUnderTest;
   private DigestTableContextMenuOpener opener;
   
   @BeforeClass public static void initialisePlatform(){
      PlatformImpl.startup( () -> {} );
   }//End Method
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );;
      systemUnderTest = new DigestTableContextMenu( controller );
   }//End Method

   @Ignore
   @Test public void manualInspection() throws InterruptedException {
      fullLaunch();
      Thread.sleep( 10000000 );
   }//End Method
   
   /**
    * Method to fully launch the {@link DigestTable} and to use the 
    * {@link DigestTableContextMenu}.
    */
   private void fullLaunch() throws InterruptedException{
      TestApplication.launch( () -> {
         fullyLaunchedTable = new DigestTable();
         for ( int i = 0; i < 1000; i++ ) {
            fullyLaunchedTable.getRows().add( new DigestTableRow( 
                     LocalDateTime.now(), new SourceImpl( this ), Categories.error(), Messages.simpleMessage( "sd" ) ) 
            );
         }
         opener = new DigestTableContextMenuOpener( fullyLaunchedTable, systemUnderTest );
         fullyLaunchedTable.setOnContextMenuRequested( opener );
         return fullyLaunchedTable; 
      } );
   }//End Method
   
   @Test public void shouldContainMenusInOrder(){
      assertThat( retrieveMenuItem( CONNECTION ).getText(), is( DigestTableContextMenu.DISCONNECT ) );
      assertThat( retrieveMenuItem( TABLE_LIMIT ).getText(), is( DigestTableContextMenu.TABLE_LIMIT ) );
      assertThat( retrieveMenuItem( CLEAR_TABLE ).getText(), is( DigestTableContextMenu.CLEAR_TABLE ) );
      assertThat( retrieveMenuItem( FIRST_SEPARATOR ), instanceOf( SeparatorMenuItem.class ) );
      assertThat( retrieveMenuItem( CANCEL ).getText(), is( DigestTableContextMenu.CANCEL ) );
      assertThat( systemUnderTest.getItems(), hasSize( 5 ) );
   }//End Method
   
   /**
    * Convenience method to retrieve the appropriate {@link MenuItem} from the {@link DigestTableContextMenu}.
    * @param item the item required.
    * @return the {@link MenuItem}.
    */
   private MenuItem retrieveMenuItem( int item ) {
      return systemUnderTest.getItems().get( item );
   }//End Method
   
   @Test public void shouldControlConnection() {
      MenuItem controConnection = retrieveMenuItem( CONNECTION );
      
      controConnection.getOnAction().handle( new ActionEvent() );
      verify( controller ).disconnect();
      assertThat( controConnection.getText(), is( DigestTableContextMenu.CONNECT ) );
      
      controConnection.getOnAction().handle( new ActionEvent() );
      verify( controller ).connect();
      assertThat( controConnection.getText(), is( DigestTableContextMenu.DISCONNECT ) );
   }//End Method
   
   @Test public void shouldAutohide() {
      assertThat( systemUnderTest.isAutoHide(), is( true ) );
   }//End Method
   
   @Test public void cancelShouldHideWhenUsingHeavySetup() throws InterruptedException{
      fullLaunch();
      PlatformImpl.runAndWait( () -> {
         opener.handle( new ContextMenuEvent( null, 0, 0, 0, 0, false, null ) );
      } );
      assertThat( systemUnderTest.friendly_isShowing(), is( true ) );
      PlatformImpl.runAndWait( () -> {
         retrieveMenuItem( CANCEL ).getOnAction().handle( new ActionEvent() );
      } );
      assertThat( systemUnderTest.friendly_isShowing(), is( false ) );
   }//End Method
   
   /**
    * Method to retrieve the uk.dangrew.sd.table limit {@link Menu} and assert its state.
    * @return the {@link Menu}.
    */
   private Menu retrieveTableLimitMenu(){
      MenuItem tableLimit = retrieveMenuItem( TABLE_LIMIT );
      assertThat( tableLimit, instanceOf( Menu.class ) );
      
      Menu tableLimitMenu = ( Menu )tableLimit;
      assertThat( tableLimitMenu.getItems(), hasSize( DigestTableRowLimit.values().length ) );
      
      return tableLimitMenu;
   }//End Method
   
   @Test public void tableLimitsShouldContainAnItemPerRowLimit(){
      Menu tableLimitMenu = retrieveTableLimitMenu();
      
      for ( DigestTableRowLimit limit : DigestTableRowLimit.values() ) {
         MenuItem limitItem = tableLimitMenu.getItems().get( limit.ordinal() );
         assertThat( limitItem.getText(), is( limit.readable() ) );
      }
   }//End Method
   
   @Test public void tableLimitsShouldBeRadioItemsInASingleToggleGroup(){
      Menu tableLimitMenu = retrieveTableLimitMenu();
      
      MenuItem firstItem = tableLimitMenu.getItems().get( 0 );
      assertThat( firstItem, instanceOf( RadioMenuItem.class ) );
      RadioMenuItem firstRadioItem = ( RadioMenuItem ) firstItem;
      ToggleGroup expectedGroup = firstRadioItem.getToggleGroup();
      assertThat( expectedGroup, notNullValue() );
      
      for ( DigestTableRowLimit limit : DigestTableRowLimit.values() ) {
         MenuItem limitItem = tableLimitMenu.getItems().get( limit.ordinal() );
         assertThat( limitItem, instanceOf( RadioMenuItem.class ) );
         RadioMenuItem limitRadioItem = ( RadioMenuItem ) firstItem;
         assertThat( limitRadioItem.getToggleGroup(), is( expectedGroup ) );
      }
   }//End Method
   
   @Test public void tableLimitsShouldUseControllerToSetLimit(){
      Menu tableLimitMenu = retrieveTableLimitMenu();
      
      for ( DigestTableRowLimit limit : DigestTableRowLimit.values() ) {
         MenuItem limitItem = tableLimitMenu.getItems().get( limit.ordinal() );
         limitItem.getOnAction().handle( new ActionEvent() );
         verify( controller ).setTableRowLimit( limit );
      }
   }//End Method
   
   @Test public void shouldClearTable(){
      MenuItem clearTable = retrieveMenuItem( CLEAR_TABLE );
      clearTable.getOnAction().handle( new ActionEvent() );
      verify( controller ).clearTable();
   }//End Method

}//End Class
