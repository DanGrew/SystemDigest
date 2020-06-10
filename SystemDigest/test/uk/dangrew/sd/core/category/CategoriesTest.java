/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.core.category;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.message.MessageFilter;
import uk.dangrew.sd.core.source.Source;

/**
 * {@link Categories} test.
 */
public class CategoriesTest {

   @Test public void shouldProvideConsistentError() {
      JavaFxThreading.startup();
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
   
   @Test public void shouldProvideDefaultCategoryFilter(){
      MessageFilter filter = Categories.filter( Categories.information() );
      assertThat( filter.matches( mock( Source.class ), mock( Category.class ), mock( Message.class ) ), is( false ) );
      assertThat( filter.matches( mock( Source.class ), Categories.information(), mock( Message.class ) ), is( true ) );
      assertThat( filter.matches( mock( Source.class ), Categories.error(), mock( Message.class ) ), is( false ) );
   }//End Method

   @Test public void shouldProvideDefaultCategoryFilterForMultiple(){
      MessageFilter filter = Categories.filter( Categories.information(), Categories.objectAllocation() );
      assertThat( filter.matches( mock( Source.class ), mock( Category.class ), mock( Message.class ) ), is( false ) );
      assertThat( filter.matches( mock( Source.class ), Categories.information(), mock( Message.class ) ), is( true ) );
      assertThat( filter.matches( mock( Source.class ), Categories.objectAllocation(), mock( Message.class ) ), is( true ) );
      assertThat( filter.matches( mock( Source.class ), Categories.error(), mock( Message.class ) ), is( false ) );
   }//End Method
   
}//End Class
