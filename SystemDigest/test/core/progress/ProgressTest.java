/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package core.progress;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * {@link Progress} test.
 */
public class ProgressTest {

   @Test public void shouldConvertPercentageToProgress() {
      assertThat( Progress.percentageToProgress( 45.67 ), is( 0.4567 ) );
      assertThat( Progress.percentageToProgress( -239847 ), is( 0.0 ) );
      assertThat( Progress.percentageToProgress( 239847 ), is( 1.0 ) );
   }//End Method

}//End Class
