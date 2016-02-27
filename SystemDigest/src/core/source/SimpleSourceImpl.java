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
    * {@inheritDoc}
    */
   @Override public String getIdentifier() {
      return identifierFunction.apply( source );
   }//End Method

}//End Class
