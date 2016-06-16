/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.core.progress;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import uk.dangrew.sd.core.progress.Progress;
import uk.dangrew.sd.core.progress.ProgressCompleteImpl;

/**
 * {@link ProgressCompleteImpl} test.
 */
public class ProgressCompleteImplTest {

   @Test public void shouldBeDefinedForCompletion() {
      Progress systemUnderTest = new ProgressCompleteImpl();
      assertThat( systemUnderTest.getPercentage(), is( 100.0 ) );
      assertThat( systemUnderTest.isComplete(), is( true ) );
   }//End Method

}//End Class
