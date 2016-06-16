/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
 package uk.dangrew.sd.demo.object;

import uk.dangrew.sd.core.category.Categories;
import uk.dangrew.sd.core.message.MessageImpl;
import uk.dangrew.sd.core.progress.ProgressImpl;
import uk.dangrew.sd.core.source.SourceImpl;
import uk.dangrew.sd.digest.object.ObjectDigest;
import uk.dangrew.sd.digest.object.ObjectDigestImpl;

/**
 * {@link DemoObject} provides a simple object that uses the system digest.
 */
public class DemoObject {
   
   private ObjectDigest digest;
   
   /**
    * Constructs a new {@link DemoObject}.
    */
   public DemoObject() {
      digest = new ObjectDigestImpl( new SourceImpl( this ) );
      digest.log( Categories.objectAllocation(), new MessageImpl( toString() ) );
   }//End Constructor
   
   /**
    * Method to do some processing for the number of iterations given.
    * @param numberOfIterations the number of iterations to perform.
    */
   public void processSomeInformation( int numberOfIterations ) {
      for ( int i = 0; i < numberOfIterations; i++ ) {
         digest.log( Categories.processingSequence(), new MessageImpl( "Step " + numberOfIterations ) );
         digest.progress( new ProgressImpl( ( 100 / numberOfIterations ) * ( i + 1 ) ), new MessageImpl( "" + i ) );
      }
   }//End Method

}//End Class
