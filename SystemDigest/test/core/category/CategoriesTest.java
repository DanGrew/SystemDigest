/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package core.category;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * {@link Categories} test.
 */
public class CategoriesTest {

   @Test public void shouldProvideConsistentError() {
      Category error = Categories.error();
      assertThat( error, notNullValue() );
      
      assertThat( error, instanceOf( Error.class ) );
      assertThat( Categories.error(), is( error ) );
      assertThat( Categories.error(), is( error ) );
      assertThat( Categories.error(), is( error ) );
   }//End Method
   
   @Test public void shouldProvideConsistentInformation() {
      Category information = Categories.information();
      assertThat( information, notNullValue() );
      
      assertThat( information, instanceOf( Information.class ) );
      assertThat( Categories.information(), is( information ) );
      assertThat( Categories.information(), is( information ) );
      assertThat( Categories.information(), is( information ) );
   }//End Method
   
   @Test public void shouldProvideConsistentStatus() {
      Category status = Categories.status();
      assertThat( status, notNullValue() );
      
      assertThat( status, instanceOf( Status.class ) );
      assertThat( Categories.status(), is( status ) );
      assertThat( Categories.status(), is( status ) );
      assertThat( Categories.status(), is( status ) );
   }//End Method
   
   @Test public void shouldProvideConsistentObjectAllocation() {
      Category objectAllocation = Categories.objectAllocation();
      assertThat( objectAllocation, notNullValue() );
      
      assertThat( objectAllocation, instanceOf( ObjectAllocation.class ) );
      assertThat( Categories.objectAllocation(), is( objectAllocation ) );
      assertThat( Categories.objectAllocation(), is( objectAllocation ) );
      assertThat( Categories.objectAllocation(), is( objectAllocation ) );
   }//End Method
   
   @Test public void shouldProvideConsistentProcessingSequence() {
      Category category = Categories.processingSequence();
      assertThat( category, notNullValue() );
      
      assertThat( category, instanceOf( ProcessingSequence.class ) );
      assertThat( Categories.processingSequence(), is( category ) );
      assertThat( Categories.processingSequence(), is( category ) );
      assertThat( Categories.processingSequence(), is( category ) );
   }//End Method

}//End Class
