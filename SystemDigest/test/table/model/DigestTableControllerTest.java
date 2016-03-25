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
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import core.category.Category;
import core.message.Message;
import core.message.Messages;
import core.source.SourceImpl;
import digest.object.ObjectDigest;
import digest.object.ObjectDigestImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import table.presentation.DigestTableRowLimit;

/**
 * {@link DigestTableController} test.
 */
public class DigestTableControllerTest {

   @Mock private Category category;
   @Mock private Message message;
   @Mock private DigestTable digestTable;
   private ObservableList< DigestTableRow > rows;
   private ObjectDigest objectDigest;
   private DigestTableController systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      PlatformImpl.startup( () -> {} );
      MockitoAnnotations.initMocks( this );
      rows = FXCollections.observableArrayList();
      when( digestTable.getRows() ).thenReturn( rows );
      
      objectDigest = new ObjectDigestImpl( new SourceImpl( this ) );
      systemUnderTest = new DigestTableController( digestTable );
   }//End Method
   
   @Test public void shouldForwardMessagesOntoTable() {
      objectDigest.log( category, message );
      PlatformImpl.runAndWait( () -> {} );
      assertThat( rows.size(), is( 1 ) );
      
      DigestTableRow row = rows.get( 0 );
      assertThat( row.getSource(), is( new SourceImpl( this ) ) );
      assertThat( row.getCategory(), is( category ) );
      assertThat( row.getMessage(), is( message ) );
   }//End Method
   
   @Test public void shouldForwardMessagesOntoTableAppendingToTop() {
      Message first = Mockito.mock( Message.class );
      Message second = Mockito.mock( Message.class );
      Message third = Mockito.mock( Message.class );
      Message fourth = Mockito.mock( Message.class );
      
      objectDigest.log( category, message );
      objectDigest.log( category, first );
      objectDigest.log( category, second );
      objectDigest.log( category, third );
      objectDigest.log( category, fourth );
      
      PlatformImpl.runAndWait( () -> {} );
      assertThat( rows.size(), is( 5 ) );
      
      assertThat( rows.get( 0 ).getMessage(), is( fourth ) );
      assertThat( rows.get( 1 ).getMessage(), is( third ) );
      assertThat( rows.get( 2 ).getMessage(), is( second ) );
      assertThat( rows.get( 3 ).getMessage(), is( first ) );
      assertThat( rows.get( 4 ).getMessage(), is( message ) );
   }//End Method
   
   @Test public void shouldDisconnectFromDigest(){
      objectDigest.log( category, message );
      PlatformImpl.runAndWait( () -> {} );
      assertThat( rows.size(), is( 1 ) );

      systemUnderTest.disconnect();
      
      objectDigest.log( category, message );
      objectDigest.log( category, message );
      objectDigest.log( category, message );
      PlatformImpl.runAndWait( () -> {} );
      
      assertThat( rows.size(), is( 1 ) );
   }//End Method
   
   @Test public void shouldReconnectToDigest(){
      systemUnderTest.disconnect();
      
      objectDigest.log( category, message );
      objectDigest.log( category, message );
      objectDigest.log( category, message );
      PlatformImpl.runAndWait( () -> {} );
      
      assertThat( rows.size(), is( 0 ) );
      
      systemUnderTest.connect();
      
      objectDigest.log( category, message );
      objectDigest.log( category, message );
      PlatformImpl.runAndWait( () -> {} );
      assertThat( rows.size(), is( 2 ) );
   }//End Method
   
   @Test public void shouldLimitRowSizeWhenRowLimitSet(){
      digestSomeMessages( 600 );
      
      systemUnderTest.setTableRowLimit( DigestTableRowLimit.FiveHundred );
      PlatformImpl.runAndWait( () -> {} );
      assertThat( rows.size(), is( DigestTableRowLimit.FiveHundred.getLimit() ) );
      
      systemUnderTest.setTableRowLimit( DigestTableRowLimit.OneHundred );
      PlatformImpl.runAndWait( () -> {} );
      assertThat( rows.size(), is( DigestTableRowLimit.OneHundred.getLimit() ) );
      
      systemUnderTest.setTableRowLimit( DigestTableRowLimit.TenThousand );
      PlatformImpl.runAndWait( () -> {} );
      assertThat( rows.size(), is( DigestTableRowLimit.OneHundred.getLimit() ) );
   }//End Method

   /**
    * Method to push some messages through the system to the {@link DigestTableController}.
    * @param numberOfMessages the number of messages to send. These are waited for and asserted.
    */
   private void digestSomeMessages( int numberOfMessages ) {
      for ( int i = 0; i < numberOfMessages; i++ ) {
         objectDigest.log( category, message );
      }
      
      PlatformImpl.runAndWait( () -> {} );
      assertThat( rows.size(), is( numberOfMessages ) );
   }//End Method
   
   @Test public void shouldLimitRowSizeWhenRowAdded(){
      systemUnderTest.setTableRowLimit( DigestTableRowLimit.FiveHundred );
      
      digestSomeMessages( 500 );
      
      final String newestMessage = "something specifically new";
      objectDigest.log( category, Messages.simpleMessage( newestMessage ) );
      
      PlatformImpl.runAndWait( () -> {} );
      assertThat( rows.size(), is( DigestTableRowLimit.FiveHundred.getLimit() ) );
      
      assertThat( rows.size(), is( 500 ) );
      assertThat( rows.get( 0 ).getMessage().getMessage(), is( newestMessage ) );
   }//End Method
   
   @Test public void shouldNotAcceptNullRowLimitAndDefaultToUnlimited(){
      systemUnderTest.setTableRowLimit( DigestTableRowLimit.OneHundred );
      systemUnderTest.setTableRowLimit( null );
      
      digestSomeMessages( 101 );
   }//End Method
   
   @Test public void shouldClearTable(){
      digestSomeMessages( 5 );
      
      systemUnderTest.clearTable();
      
      PlatformImpl.runAndWait( () -> {} );
      assertThat( rows.size(), is( 0 ) );
      
      digestSomeMessages( 10 );
   }//End Method

}//End Class
