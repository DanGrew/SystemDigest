/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package core.message;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * {@link Messages} test.
 */
public class MessagesTest {

   @Test public void shouldProvideSimpleMessageWithText() {
      final String messageText = "any text put into a message";
      Message message = Messages.simpleMessage( messageText );
      assertThat( message.getMessage(), is( messageText ) );
   }//End Method

}//End Class
