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
 * {@link MessageImpl} test.
 */
public class MessageImplTest {

   @Test public void shouldProvideBasicMessage() {
      final String messageText = "anything to communicate";
      Message message = new MessageImpl( messageText );
      assertThat( message.getMessage(), is( messageText ) );
   }//End Method

}//End Class
