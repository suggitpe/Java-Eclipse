/*
 * FsmGenerationException.java created on 21 Jun 2007 08:07:19 by suggitpe for project GUI - JmsHelper
 * 
 */
package org.suggs.sandbox_eclipse.testplugin;

/**
 * This exception class is used by all elements within the application
 * 
 * @author suggitpe
 * @version 1.0 15 Apr 2008
 */
public class FsmGenerationException extends Exception {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 593244495837017029L;

    /**
     * Constructs a new instance.
     */
    public FsmGenerationException() {
        super();
    }

    /**
     * Constructs a new instance.
     * 
     * @param aMessage
     */
    public FsmGenerationException( String aMessage ) {
        super( aMessage );
    }

    /**
     * Constructs a new instance.
     * 
     * @param aMessage
     * @param aError
     */
    public FsmGenerationException( String aMessage, Throwable aError ) {
        super( aMessage, aError );
    }

    /**
     * Constructs a new instance.
     * 
     * @param aError
     */
    public FsmGenerationException( Throwable aError ) {
        super( aError );
    }
}
