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
import uk.dangrew.sd.core.progress.ProgressImpl;

/**
 * {@link ProgressImpl} test.
 */
public class ProgressImplTest {

   @Test public void shouldProvideBasicProgressPercentage() {
      final double percentage = 56.8;
      Progress progress = new ProgressImpl( percentage );
      assertThat( progress.getPercentage(), is( percentage ) );
      assertThat( progress.isComplete(), is( false ) );
   }//End Method

}//End Class
