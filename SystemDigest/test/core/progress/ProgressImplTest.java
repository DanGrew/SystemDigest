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
 * {@link ProgressImpl} test.
 */
public class ProgressImplTest {

   @Test public void shouldProvideBasicProgressPercentage() {
      final double percentage = 56.8;
      Progress progress = new ProgressImpl( percentage );
      assertThat( progress.getPercentage(), is( percentage ) );
   }//End Method

}//End Class
