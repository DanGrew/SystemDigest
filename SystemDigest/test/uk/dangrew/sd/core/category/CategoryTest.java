package uk.dangrew.sd.core.category;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import javafx.scene.paint.Color;

public class CategoryTest {

   private CategoryEnablement enablement;
   private Category systemUnderTest;
   
   private class TestCategory extends Category {

      protected TestCategory( String name, Color colour, boolean enabled ) {
         super( enablement, name, colour, enabled );
      }//End Class
      
   }//End Class
   
   @Before public void initialiseSystemUnderTest(){
      enablement = new CategoryEnablement();
      systemUnderTest = new TestCategory( "name", Color.RED, false );
   }//End Method

   @Test public void shouldProvideName() {
      assertThat( systemUnderTest.getName(), is( "name" ) );
   }//End Method
   
   @Test public void shouldProvideColour() {
      assertThat( systemUnderTest.getColour(), is( Color.RED ) );
   }//End Method
   
   @Test public void shouldAutomaticallyBeEnabled() {
      systemUnderTest = new TestCategory( "anything", Color.ALICEBLUE, true );
      assertThat( enablement.isEnabled( systemUnderTest ), is( true ) );
   }//End Method
   
   @Test public void shouldAutomaticallyBeDisabled() {
      systemUnderTest = new TestCategory( "anything", Color.ALICEBLUE, false );
      assertThat( enablement.isEnabled( systemUnderTest ), is( false ) );
   }//End Method
   
   @Test public void shouldRegisterWithEnablement(){
      assertThat( enablement.categories().contains( systemUnderTest ), is( true ) );
   }//End Method

}//End Class
