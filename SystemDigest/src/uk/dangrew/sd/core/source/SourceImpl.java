/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.core.source;

import java.util.function.Function;

/**
 * {@link SourceImpl} provides a basic implementation of the {@link Source} interface.
 */
public class SourceImpl implements Source {
   
   private final Object source;
   private final Function< Object, String > identifierFunction;
   
   /**
    * Constructs a new {@link SourceImpl}.
    * @param source the {@link Object} at the source of the information.
    * @param identifierFunction the {@link Function} providing the identification from the source.
    */
   public SourceImpl( Object source, Function< Object, String > identifierFunction ) {
      if ( source == null ) throw new IllegalArgumentException( "Source object not provided." );
      if ( identifierFunction == null ) throw new IllegalArgumentException( "Identifier function not provided." );
      
      this.source = source;
      this.identifierFunction = identifierFunction;
   }//End Constructor
   
   /**
    * Constructs a new {@link SourceImpl} with the given source a default identifier {@link Object#toString()}.
    * @param source the {@link Object} source.
    */
   public SourceImpl( Object source ) {
      this( source, object -> { return source.toString(); } );
   }//End Constructor

   /**
    * Constructs a new {@link SourceImpl}.
    * @param source the {@link Object} source.
    * @param name the constant name to use as an identifier.
    */
   public SourceImpl( Object source, String name ) {
      this( source, object -> { return name; } );
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
      if ( !( obj instanceof SourceImpl ) ) {
         return false;
      }
      SourceImpl other = ( SourceImpl ) obj;
      if ( !source.equals( other.source ) ) {
         return false;
      }
      return true;
   }//End Method
   
}//End Class
