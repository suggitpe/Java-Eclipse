/*
 * FsmGenerationEditorInput.java created on 14 Apr 2008 18:30:57 by suggitpe for project Sandbox - TestEclipsePlugin
 * 
 */
package org.suggs.sandbox_eclipse.testplugin.compare;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

/**
 * TODO Write javadoc for FsmGenerationEditorInput
 * 
 * @author suggitpe
 * @version 1.0 14 Apr 2008
 */
public class FsmGenerationEditorInput extends CompareEditorInput {

    private ITypedElement lhs;
    private ITypedElement rhs;

    private boolean mCancelled_ = false;

    /**
     * Constructs a new instance.
     * 
     * @param aLhs
     * @param aRhs
     */
    public FsmGenerationEditorInput( ITypedElement aLhs, ITypedElement aRhs ) {
        super( new CompareConfiguration() );
        lhs = aLhs;
        rhs = aRhs;
    }

    /**
     * @see org.eclipse.compare.CompareEditorInput#prepareInput(org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    protected Object prepareInput( IProgressMonitor monitor ) {
        if ( lhs == null || rhs == null ) {
            return null;
        }
        Object obj = null;

        try {
            // set up the labels
            initLabels();

            Differencer diff = new Differencer();
            monitor.beginTask( "FsmGenerationEdittorInput.compare", 30 );

            IProgressMonitor sub = new SubProgressMonitor( monitor, 10 );
            try {
                sub.beginTask( "Finding differences", 50 );
                obj = diff.findDifferences( false, new NullProgressMonitor(), null, null, lhs, rhs );
            }
            finally {
                sub.done();
            }
        }
        finally {
            monitor.done();
        }

        // we do this to get the commit button enabled
        this.setDirty( true );

        return obj;
    }

    /**
     * @see org.eclipse.compare.CompareEditorInput#cancelPressed()
     */
    @Override
    public void cancelPressed() {
        mCancelled_ = true;
    }

    /**
     * Accessor to see if the dialog was cancelled.
     * 
     * @return whether the dialog wa cancelled.
     */
    public boolean wasCancelPressed() {
        return mCancelled_;
    }

    /**
     * This will set up the labels on the compare viewer.
     */
    private void initLabels() {
        CompareConfiguration cc = getCompareConfiguration();
        cc.setLeftLabel( lhs.getName() );
        cc.setRightLabel( rhs.getName() );

        setTitle( "FSM Spring XML configuration compare" );
    }
}
