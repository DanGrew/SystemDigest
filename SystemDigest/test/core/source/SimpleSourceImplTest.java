/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package core.source;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link SimpleSourceImpl} test.
 */
public class SimpleSourceImplTest {

   private Object source;
   private Function< Object, String > identifierFunction;
   private Source systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      source = new Object();
      identifierFunction = source -> { return source.toString(); };
      systemUnderTest = new SimpleSourceImpl( source, identifierFunction );
   }//End Method
   
   @Test public void shouldProvideIdentifierAccordingToFunction() {
      assertThat( systemUnderTest.getIdentifier(), is( identifierFunction.apply( source ) ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullSource(){
      systemUnderTest = new SimpleSourceImpl( null, identifierFunction );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullFunction(){
      systemUnderTest = new SimpleSourceImpl( source, null );
   }//End Method
   
   @Test public void shouldProvideObjectToStringByDefault(){
      systemUnderTest = new SimpleSourceImpl( source );
      assertThat( systemUnderTest.getIdentifier(), is( source.toString() ) );
   }//End Method
   
   @Test public void shouldBeEqual(){
      assertThat( systemUnderTest.equals( systemUnderTest ), is( true ) );
      assertThat( systemUnderTest.equals( new SimpleSourceImpl( source ) ), is( true ) );
      
      assertThat( systemUnderTest.equals( null ), is( false ) );
      assertThat( systemUnderTest.equals( "anything" ), is( false ) );
      assertThat( systemUnderTest.equals( new SimpleSourceImpl( new Object() ) ), is( false ) );
   }//End Method
   
   @Test public void shouldHashCodeToSameValues(){
      assertThat( systemUnderTest.hashCode(), is( new SimpleSourceImpl( source ).hashCode() ) );
   }//End Method

}//End Class
