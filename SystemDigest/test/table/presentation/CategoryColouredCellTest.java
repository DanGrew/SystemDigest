/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
 package table.presentation;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import core.category.Categories;
import graphics.launch.TestApplication;
import javafx.scene.control.TableRow;
import table.model.DigestTableRow;

/**
 * {@link CategoryColouredCell} test.
 */
public class CategoryColouredCellTest {

   private TableRow< DigestTableRow > tableRow;
   @Mock private DigestTableRow row;
   private CategoryColouredCell< String > systemUnderTest;
   
   @BeforeClass public static void initialisePlatform(){
      TestApplication.startPlatform();
   }//End Method
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      tableRow = new TableRow<>();
      systemUnderTest = new CategoryColouredCell< String >();
   }//End Method
   
   @Test public void shouldRemoveTextIfNoTableRowPresent() {
      systemUnderTest.setText( "anything" );
      systemUnderTest.updateItem( "something else", false );
      assertThat( systemUnderTest.getText(), nullValue() );
   }//End Method
   
   @Test public void shouldRemoveTextIfNoRowValuePresent() {
      systemUnderTest.updateTableRow( tableRow );
      systemUnderTest.setText( "anything" );
      systemUnderTest.updateItem( "something else", false );
      assertThat( systemUnderTest.getText(), nullValue() );
   }//End Method
   
   @Test public void shouldSetCategoryColourAndSetText(){
      tableRow.setItem( row );
      when( row.getCategory() ).thenReturn( Categories.objectAllocation() );
      
      systemUnderTest.updateTableRow( tableRow );
      systemUnderTest.setText( "anything" );
      
      final String value = "some value to set";
      systemUnderTest.updateItem( value, false );
      assertThat( systemUnderTest.getText(), is( value ) );
      assertThat( systemUnderTest.getTextFill(), is( Categories.objectAllocation().getColour() ) );
   }//End Method
   
   @Test public void shouldAcceptNullItem(){
      tableRow.setItem( row );
      when( row.getCategory() ).thenReturn( Categories.objectAllocation() );
      
      systemUnderTest.updateTableRow( tableRow );
      systemUnderTest.setText( "anything" );
      
      systemUnderTest.updateItem( null, false );
      assertThat( systemUnderTest.getText(), nullValue() );
   }//End Method

}//End Class
