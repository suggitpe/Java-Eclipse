/*
 * DiffJarsActionDelegate.java created on 15 Mar 2010 07:11:19 by suggitpe for project JardiffPlugin
 * 
 */
package org.suggs.eclipse.jardiff.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * This class provides the link between the UI and the underlying action to perform the jar diff'ing process.
 * 
 * @author suggitpe
 * @version 1.0 15 Mar 2010
 */
public class DiffJarsActionDelegate implements IWorkbenchWindowActionDelegate, IObjectActionDelegate {

    private static final List<String> LIST_OF_DIFFABLE_FILE_TYPES = new ArrayList<String>();
    private IStructuredSelection selection;

    static {
        LIST_OF_DIFFABLE_FILE_TYPES.add( "jar" );
    }

    /**
     * Run call will verify what the user selection is and will try and use it if it is a valid jar file. If
     * no jar file selected then it will just run the plugin in the default mode.
     * 
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run( IAction aAction ) {
        DiffJarsAction diffAction = null;

        if ( selection != null && !selection.isEmpty() ) {
            Object element = selection.getFirstElement();
            if ( element instanceof IFile ) {
                IFile file = (IFile) element;
                if ( LIST_OF_DIFFABLE_FILE_TYPES.contains( file.getFileExtension() ) ) {
                    diffAction = new DiffJarsAction( file );
                }
            }
        }

        if ( diffAction == null ) {
            diffAction = new DiffJarsAction();
        }

        diffAction.run();
    }

    /**
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
     */
    public void dispose() {}

    /**
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
     */
    public void init( IWorkbenchWindow aWorkbenchWindow ) {}

    /**
     * Provides access to the underlying selection from the UI.
     * 
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged( IAction aAction, ISelection aSelection ) {
        selection = (IStructuredSelection) aSelection;
    }

    /**
     * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
     *      org.eclipse.ui.IWorkbenchPart)
     */
    public void setActivePart( IAction aAction, IWorkbenchPart aWorkbenchpart ) {}
}
