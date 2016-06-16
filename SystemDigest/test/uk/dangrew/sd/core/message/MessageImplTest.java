/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.core.message;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.message.MessageImpl;

/**
 * {@link MessageImpl} test.
 */
public class MessageImplTest {

   private static final String MESSAGE_TEXT = "anything to communicate";
   private Message systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      systemUnderTest = new MessageImpl( MESSAGE_TEXT );
   }//End Method
   
   @Test public void shouldProvideBasicMessage() {
      assertThat( systemUnderTest.getMessage(), is( MESSAGE_TEXT ) );
   }//End Method
   
   @Test public void messagesWithSameTextShouldBeEqual(){
      assertThat( systemUnderTest, not( equalTo( null ) ) );
      assertThat( systemUnderTest, not( equalTo( 67 ) ) );
      assertThat( systemUnderTest, equalTo( systemUnderTest ) );
      assertThat( systemUnderTest, equalTo( new MessageImpl( MESSAGE_TEXT ) ) );
      assertThat( systemUnderTest, not( equalTo( new MessageImpl( "anything else" ) ) ) );
      assertThat( new MessageImpl( null ), not( equalTo( systemUnderTest ) ) );
      assertThat( new MessageImpl( null ), equalTo( new MessageImpl( null ) ) );
   }//End Method
   
   @Test public void hashCodeSholdVaryWithMessage(){
      assertThat( systemUnderTest.hashCode(), is( new MessageImpl( MESSAGE_TEXT ).hashCode() ) );
      assertThat( systemUnderTest.hashCode(), is( not( new MessageImpl( "something else" ).hashCode() ) ) );
      assertThat( systemUnderTest.hashCode(), is( not( new MessageImpl( null ).hashCode() ) ) );
   }//End Method

}//End Class
