/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package core.source;

/**
 * The {@link Source} provides an interface for objects describing the source of information
 * in the system digest.
 */
public interface Source {

   /**
    * Method to get the identifier for the object source.
    * @return the {@link String} identifier.
    */
   public String getIdentifier();
   
}//End Interface
