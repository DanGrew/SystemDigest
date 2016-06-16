/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.table.presentation;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.sd.table.presentation.DigestTableRowLimit;

/**
 * {@link DigestTableRowLimit} test.
 */
public class DigestTableRowLimitTest {

   @Test public void readableShouldBeToStringOfLimit() {
      for ( DigestTableRowLimit limit : DigestTableRowLimit.values() ) {
         if ( limit.getLimit() == null ) {
            assertThat( limit.readable(), is( DigestTableRowLimit.UNLIMITED ) );
         } else {
            assertThat( limit.readable(), is( limit.getLimit().toString() ) );
         }
      }
   }//End Method
   
   @Test public void limitShouldReduceTheNumberOfItemsFromTheBottom() {
      for ( DigestTableRowLimit limit : DigestTableRowLimit.values() ) {
         ObservableList< String > list = FXCollections.observableArrayList();
         for ( int i = 0; i < 20000; i++ ) {
            list.add( "" + i );
         }
         
         limit.limit( list );
         
         Integer limitValue = limit.getLimit();
         if ( limitValue == null ) {
            assertThat( list, hasSize( 20000 ) );
         } else {
            int limitIndex = limitValue - 1;
            assertThat( list, hasSize( limitValue ) );
            
            assertThat( list.get( 0 ), is( "0" ) );
            assertThat( list.get( limitIndex ), is( "" + limitIndex ) );
         }
      }
   }//End Method

}//End Class
