/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.logging.location;

/**
 * The {@link LoggingLocationProtocol} is responsible for defining the interface that
 * should be implemented when providing a method of logging digest output to a {@link java.io.File}.
 */
public interface LoggingLocationProtocol extends FileLocationProtocol {
   
   /**
    * Method to instruct the mechanism to log the given line to the {@link java.io.File}
    * associated by appending.
    * @param logLine the {@link String} line to log.
    * @return true if successful.
    */
   public boolean logToLocation( String logLine );
   
}//End Interface
