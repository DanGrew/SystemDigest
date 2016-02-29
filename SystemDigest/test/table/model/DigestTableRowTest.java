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

import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import core.category.Category;
import core.message.Message;
import core.source.Source;

/**
 * {@link DigestTableRow} test.
 */
public class DigestTableRowTest {

   private LocalTime timestamp;
   @Mock private Source source;
   @Mock private Category category;
   @Mock private Message message;
   private DigestTableRow systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      timestamp = LocalTime.now();
      systemUnderTest = new DigestTableRow( timestamp, source, category, message );
   }//End Method
   
   @Test public void shouldProvideConstructedValues() {
      assertThat( systemUnderTest.getTimestamp(), is( timestamp ) );
      assertThat( systemUnderTest.getSource(), is( source ) );
      assertThat( systemUnderTest.getCategory(), is( category ) );
      assertThat( systemUnderTest.getMessage(), is( message ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullTimestamp(){
      systemUnderTest = new DigestTableRow( null, source, category, message );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullSource(){
      systemUnderTest = new DigestTableRow( timestamp, null, category, message );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullCategory(){
      systemUnderTest = new DigestTableRow( timestamp, source, null, message );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullMessage(){
      systemUnderTest = new DigestTableRow( timestamp, source, category, null );
   }//End Method

}//End Class
