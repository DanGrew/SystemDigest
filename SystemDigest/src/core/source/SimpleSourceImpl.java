/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package core.source;

import java.util.function.Function;

/**
 * {@link SimpleSourceImpl} provides a basic implementation of the {@link Source} interface.
 */
public class SimpleSourceImpl implements Source {
   
   private final Object source;
   private final Function< Object, String > identifierFunction;
   
   /**
    * Constructs a new {@link SimpleSourceImpl}.
    * @param source the {@link Object} at the source of the information.
    * @param identifierFunction the {@link Function} providing the identification from the source.
    */
   public SimpleSourceImpl( Object source, Function< Object, String > identifierFunction ) {
      if ( source == null ) throw new IllegalArgumentException( "Source object not provided." );
      if ( identifierFunction == null ) throw new IllegalArgumentException( "Identifier function not provided." );
      
      this.source = source;
      this.identifierFunction = identifierFunction;
   }//End Constructor
   
   /**
    * Constructs a new {@link SimpleSourceImpl} with the given source a default identifier {@link Object#toString()}.
    * @param source the {@link Object} source.
    */
   public SimpleSourceImpl( Object source ) {
      this( source, object -> { return source.toString(); } );
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public String getIdentifier() {
      return identifierFunction.apply( source );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + source.hashCode();
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
      if ( !( obj instanceof SimpleSourceImpl ) ) {
         return false;
      }
      SimpleSourceImpl other = ( SimpleSourceImpl ) obj;
      if ( !source.equals( other.source ) ) {
         return false;
      }
      return true;
   }//End Method
   
}//End Class
