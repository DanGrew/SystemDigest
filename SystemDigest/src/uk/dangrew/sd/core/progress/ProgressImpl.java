/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.core.progress;

/**
 * {@link ProgressImpl} provides a basic implementation of the {@link Progress} interface.
 */
public class ProgressImpl implements Progress {

   private double percentage;
   
   /**
    * Constructs a new {@link ProgressImpl}.
    * @param percentage the percentage complete.
    */
   public ProgressImpl( double percentage ) {
      this.percentage = percentage;
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public double getPercentage(){
      return percentage;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public boolean isComplete() {
      return false;
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public int hashCode() {
      final int prime = 31;
      int result = 1;
      long temp;
      temp = Double.doubleToLongBits( percentage );
      result = prime * result + ( int ) ( temp ^ ( temp >>> 32 ) );
      return result;
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public boolean equals( Object obj ) {
      if ( this == obj ) {
         return true;
      }
      if ( obj == null ) {
         return false;
      }
      if ( !( obj instanceof ProgressImpl ) ) {
         return false;
      }
      ProgressImpl other = ( ProgressImpl ) obj;
      if ( Double.doubleToLongBits( percentage ) != Double.doubleToLongBits( other.percentage ) ) {
         return false;
      }
      return true;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public String toString() {
      return "Basic progress of " + percentage + "%";
   }//End Method

}//End Class
