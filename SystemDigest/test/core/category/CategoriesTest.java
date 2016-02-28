/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package core.category;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * {@link Categories} test.
 */
public class CategoriesTest {

   @Test public void shouldProvideConsistentObjectAllocation() {
      Category objectAllocation = Categories.objectAllocation();
      assertThat( objectAllocation, notNullValue() );
      
      assertThat( Categories.objectAllocation(), is( objectAllocation ) );
      assertThat( Categories.objectAllocation(), is( objectAllocation ) );
      assertThat( Categories.objectAllocation(), is( objectAllocation ) );
   }//End Method
   
   @Test public void shouldProvideConsistentProcessingSequence() {
      Category category = Categories.processingSequence();
      assertThat( category, notNullValue() );
      
      assertThat( Categories.processingSequence(), is( category ) );
      assertThat( Categories.processingSequence(), is( category ) );
      assertThat( Categories.processingSequence(), is( category ) );
   }//End Method

}//End Class
