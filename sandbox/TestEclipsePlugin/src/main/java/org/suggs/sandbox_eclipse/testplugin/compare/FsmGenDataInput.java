/*
 * FsmGenDataInput.java created on 14 Apr 2008 18:33:41 by suggitpe for project Sandbox - TestEclipsePlugin
 * 
 */
package org.suggs.sandbox_eclipse.testplugin.compare;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.swt.graphics.Image;

/**
 * TODO Write javadoc for FsmGenDataInput
 * 
 * @author suggitpe
 * @version 1.0 14 Apr 2008
 */
public class FsmGenDataInput implements ITypedElement, IStreamContentAccessor {

    private String string;
    private String name;

    /**
     * Constructs a new instance.
     * 
     * @param aString
     * @param aName
     */
    public FsmGenDataInput( String aString, String aName ) {
        string = aString;
        name = aName;
    }

    /**
     * @see org.eclipse.compare.ITypedElement#getImage()
     */
    public Image getImage() {
        return null;
    }

    /**
     * @see org.eclipse.compare.ITypedElement#getName()
     */
    public String getName() {
        return name;
    }

    /**
     * @see org.eclipse.compare.ITypedElement#getType()
     */
    public String getType() {
        return "String";
    }

    /**
     * @see org.eclipse.compare.IStreamContentAccessor#getContents()
     */
    public InputStream getContents() {
        return new ByteArrayInputStream( string.getBytes() );
    }

}
