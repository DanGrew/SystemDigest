package uk.dangrew.sd.core.category;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.dangrew.kode.observable.PrivatelyModifiableObservableListImpl;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class CategoryEnablementTest {
   
   @Mock private Category categoryA;
   @Mock private Category categoryB;
   private CategoryEnablement systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new CategoryEnablement();
   }//End Method

   @Test public void shouldProvideCategories() {
      assertThat( systemUnderTest.categories(), is( instanceOf( PrivatelyModifiableObservableListImpl.class ) ) );
      assertThat( systemUnderTest.categories(), is( empty() ) );
      systemUnderTest.register( categoryA );
      assertThat( systemUnderTest.categories(), contains( categoryA ) );
      systemUnderTest.register( categoryB );
      assertThat( systemUnderTest.categories(), contains( categoryA, categoryB ) );
   }//End Method
   
   @Test public void shouldIgnoreAlreadyRegisteredCategories() {
      systemUnderTest.register( categoryA );
      systemUnderTest.register( categoryA );
      assertThat( systemUnderTest.categories(), contains( categoryA ) );
   }//End Method
   
   @Test public void shouldProvideCategoryEnablement() {
      assertThat( systemUnderTest.isEnabled( categoryA ), is( false ) );
      assertThat( systemUnderTest.isEnabled( categoryB ), is( false ) );
      systemUnderTest.setEnabled( categoryA, true );
      assertThat( systemUnderTest.isEnabled( categoryA ), is( true ) );
      assertThat( systemUnderTest.isEnabled( categoryB ), is( false ) );
      systemUnderTest.setEnabled( categoryB, true );
      assertThat( systemUnderTest.isEnabled( categoryA ), is( true ) );
      assertThat( systemUnderTest.isEnabled( categoryB ), is( true ) );
      systemUnderTest.setEnabled( categoryA, false );
      assertThat( systemUnderTest.isEnabled( categoryA ), is( false ) );
      assertThat( systemUnderTest.isEnabled( categoryB ), is( true ) );
   }//End Method

}//End Class
