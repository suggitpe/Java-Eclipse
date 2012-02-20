/*
 * CreateXmlFileAction.java created on 10 Mar 2008 19:24:19 by suggitpe for project Sandbox - TestEclipsePlugin
 * 
 */
package org.suggs.sandbox_eclipse.testplugin.actions;

import org.suggs.sandbox_eclipse.testplugin.FsmGenerationException;
import org.suggs.sandbox_eclipse.testplugin.compare.FsmGenDataInput;
import org.suggs.sandbox_eclipse.testplugin.compare.FsmGenerationEditorInput;
import org.suggs.sandbox_eclipse.testplugin.dialogs.DisplaySpringXmlDialog;
import org.suggs.sandbox_eclipse.testplugin.dialogs.GetSpringXmlOptionsDialog;
import org.suggs.sandbox_eclipse.testplugin.fsmgeneration.FsmXmlGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.eclipse.compare.CompareUI;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

/**
 * Action to create an Spring XML representation from a selection in the eclipse framework.
 * 
 * @author suggitpe
 * @version 1.0 10 Mar 2008
 */
public class CreateXmlFileAction extends Action {

    // this is the selction in the eclipse framework
    IFile model;

    /**
     * Constructs a new instance.
     * 
     * @param aModel
     */
    public CreateXmlFileAction( IFile aModel ) {
        super();
        model = aModel;
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        GetSpringXmlOptionsDialog d = new GetSpringXmlOptionsDialog( Display.getDefault().getActiveShell() );

        d.open();

        if ( d.getReturnCode() == Window.CANCEL ) {
            return;
        }

        try {
            FsmXmlGenerator gen = new FsmXmlGenerator( model );
            String xml = gen.generateXml( d.getFsmKeySelection() );

            if ( d.getDestination() == GetSpringXmlOptionsDialog.DEST_POPUP ) {
                dispatchXmlToPopup( xml );
            }
            else if ( d.getDestination() == GetSpringXmlOptionsDialog.DEST_FILE ) {
                dispatchXmlToFile( xml, d.getFileName() );
            }
        }
        catch ( Exception e ) {
            MessageDialog.openError( Display.getDefault().getActiveShell(),
                                     "FSM Generation Failure",
                                     "Failed to generate FSM XML file because:\n" + e.getMessage() );
            e.printStackTrace();
        }
    }

    /**
     * Drop the contents of the the xml generation process to a popup dialog window for the user do do with as
     * they wish.
     * 
     * @param aXml
     *            the xml to show to the user.
     */
    private void dispatchXmlToPopup( String aXml ) {
        DisplaySpringXmlDialog d = new DisplaySpringXmlDialog( Display.getDefault().getActiveShell(), aXml );
        d.open();
    }

    /**
     * Take the contents of the generated XML and drops it to a diff viewer for review.
     * 
     * @param aXml
     *            the generasted xml
     * @param aFilename
     *            the filename to write to
     * @throws IOException
     */
    private void dispatchXmlToFile( String aXml, String aFilename ) throws IOException,
                    FsmGenerationException {
        if ( aFilename == null || aFilename.equals( "" ) ) {
            throw new FsmGenerationException( "No filename has been specified by the options dialog (cannot procedd without a filename" );
        }
        File dest = new File( aFilename );

        if ( !dest.exists() || !dest.isFile() || !dest.canWrite() ) {
            throw new FsmGenerationException( "The filename specified [" + aFilename
                                              + "] does not exist or is not writable" );
        }

        StringBuffer existingXml = new StringBuffer();
        BufferedReader reader = new BufferedReader( new FileReader( dest ) );
        String ln = null;
        while ( ( ln = reader.readLine() ) != null ) {
            existingXml.append( ln ).append( "\n" );
        }

        FsmGenerationEditorInput diag = new FsmGenerationEditorInput( new FsmGenDataInput( existingXml.toString(),
                                                                                           "From file "
                                                                                                           + aFilename ),
                                                                      new FsmGenDataInput( aXml,
                                                                                           "New FSM XML" ) );
        CompareUI.openCompareDialog( diag );
        if ( diag.wasCancelPressed() ) {
            MessageDialog.openWarning( Display.getCurrent().getActiveShell(),
                                       "Operation cancelled",
                                       "FSM Spring XML generation cancelled by user." );
            return;
        }

        if ( diag.getCompareResult() == null ) {
            return;
        }

        FileOutputStream fos = new FileOutputStream( aFilename );
        FileChannel chan = fos.getChannel();
        try {
            // load up the buffer and pop the marker to the start
            int strSize = aXml.getBytes().length;
            ByteBuffer buff = ByteBuffer.allocate( strSize );
            buff.put( aXml.getBytes() );
            buff.flip();

            chan.write( buff );
        }
        finally {
            chan.close();
            fos.close();
        }
        MessageDialog.openInformation( Display.getDefault().getActiveShell(),
                                       "File Written",
                                       "Have written " + aXml.getBytes().length + " bytes to " + aFilename
                                                       + " correctly" );
    }

}
