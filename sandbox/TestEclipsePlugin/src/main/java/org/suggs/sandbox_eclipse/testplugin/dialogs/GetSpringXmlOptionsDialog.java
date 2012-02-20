/*
 * GetSpringXmlOptionsDialog.java created on 11 Mar 2008 19:08:08 by suggitpe for project Sandbox - TestEclipsePlugin
 * 
 */
package org.suggs.sandbox_eclipse.testplugin.dialogs;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog;

/**
 * This class is a dialog class that allows us to collect data from the users as to the settings to use when
 * creating the Spring XML. This class extends the Dialog class so that it can implement its on version of the
 * Dialog box.
 * 
 * @author suggitpe
 * @version 1.0 11 Mar 2008
 */
public class GetSpringXmlOptionsDialog extends Dialog {

    private GridData d2;
    private GridData d3;

    {
        d2 = new GridData( GridData.HORIZONTAL_ALIGN_FILL );
        d2.horizontalSpan = 2;
        d3 = new GridData( GridData.HORIZONTAL_ALIGN_FILL );
        d3.horizontalSpan = 3;
    }

    private String destinationValue;
    private String keyValue;
    private String fileNameValue;

    public static final String DEST_POPUP = "POPUP";
    public static final String DEST_FILE = "FILE";

    public static final String KEY_NONE = "NONE";
    public static final String KEY_CONFS = "CONFS";
    public static final String KEY_ADVCS = "ADVICES";

    private Button popupRadio;
    private Button fileRadio;
    private Text fileName;

    private Button noneRadio;
    private Button confsRadio;
    private Button advicesRadio;

    /**
     * Constructs a new instance.
     * 
     * @param parentShell
     */
    public GetSpringXmlOptionsDialog( Shell parentShell ) {
        super( parentShell );
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#close()
     */
    @Override
    public boolean close() {
        // here we need to get the data out of the dialog widgets
        // before we close the dialog
        fileNameValue = fileName.getText();
        destinationValue = popupRadio.getSelection() ? DEST_POPUP : DEST_FILE;
        keyValue = noneRadio.getSelection() ? KEY_NONE : confsRadio.getSelection() ? KEY_CONFS : KEY_ADVCS;
        return super.close();
    }

    /**
     * Getter for the required destination. This should be replaced with an enumeration when we are fully Java
     * 5 compatible.
     * 
     * @return the int representing the destination.
     */
    public String getDestination() {
        return destinationValue;
    }

    /**
     * Getter for the contents of the file text box
     * 
     * @return the contents of the file text box
     */
    public String getFileName() {
        return fileNameValue;
    }

    /**
     * Getter for the required FSM key selected. This should be replaced with an enumeration when we are fully
     * Java 5 compatible.
     * 
     * @return the int representing the FSM key selection.
     */
    public String getFsmKeySelection() {
        return keyValue;
    }

    /**
     * This is overridden so we can create the internal Dialog controls
     * 
     * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createDialogArea( Composite parent ) {
        Composite ret = (Composite) super.createDialogArea( parent );
        createKeySelection( ret );
        createDestinationSection( ret );
        ret.pack();
        return ret;
    }

    /**
     * This method is overriden so that we can add in some validation logic around the passed in filenames.
     * 
     * @see org.eclipse.jface.dialogs.Dialog#okPressed()
     */
    @Override
    protected void okPressed() {
        // now we do the validation
        if ( fileRadio.getSelection() ) {
            String name = fileName.getText();
            if ( name == null || name.equals( "" ) ) {
                MessageDialog.openError( Display.getCurrent().getActiveShell(),
                                         "Incomplete input",
                                         "If you select the file destination, then "
                                                         + "you also need to select a filename" );
                return;
            }

            File dest = new File( name );
            if ( !dest.exists() ) {
                MessageDialog.openError( Display.getCurrent().getActiveShell(),
                                         "Bad data",
                                         "The filename entered ["
                                                         + name
                                                         + "], does not exist. Please make sure that the file exists before continuing." );
                return;
            }
        }

        super.okPressed();
    }

    /**
     * Helper method to create the key selection section of the dialog
     * 
     * @param aComp
     *            the base shell
     */
    private void createKeySelection( final Composite aComp ) {
        // -------------------
        // create the group to use including any text
        Group group1 = new Group( aComp, SWT.None );
        group1.setText( "FSM Key Selection" );

        // set the layout such that we have to columns and they do not
        // have the same size
        group1.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
        group1.setLayout( new GridLayout( 3, false ) );

        noneRadio = new Button( group1, SWT.RADIO );
        noneRadio.setSelection( true );
        noneRadio.setText( "None" );

        advicesRadio = new Button( group1, SWT.RADIO );
        advicesRadio.setText( "Advices" );

        confsRadio = new Button( group1, SWT.RADIO );
        confsRadio.setText( "Confirmations" );
    }

    /**
     * Helper method to create the destination section of the dialog
     * 
     * @param aComp
     *            the base shell
     */
    private void createDestinationSection( final Composite aComp ) {

        // -------------------
        Group group2 = new Group( aComp, SWT.None );
        group2.setText( "Destination" );

        // set the layout such that we have to columns and they do not
        // have the same size
        group2.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
        group2.setLayout( new GridLayout( 3, false ) );

        popupRadio = new Button( group2, SWT.RADIO );
        popupRadio.setSelection( true );
        popupRadio.setText( "Popup" );
        popupRadio.setToolTipText( "Selecting this option means that the end result will end up in a little popup window to copy and paste" );

        fileRadio = new Button( group2, SWT.RADIO );
        fileRadio.setText( "File" );
        fileRadio.setLayoutData( d2 );
        fileRadio.setToolTipText( "Selecting this option means that the end result will be written to a file" );

        // now we should create the file selection
        final Label fileLabel = new Label( group2, SWT.None );
        fileLabel.setText( "File:" );
        fileLabel.setEnabled( false );

        fileName = new Text( group2, SWT.BORDER );
        fileName.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
        // mFileName_.setEditable( false );
        fileName.setEnabled( false );
        fileName.selectAll();

        // set the button
        final Button file = new Button( group2, SWT.None );
        file.setText( "..." );
        file.setEnabled( false );

        // ------------------
        // set up the selection listeners
        file.addSelectionListener( new SelectionListener() {

            public void widgetDefaultSelected( SelectionEvent e ) {}

            public void widgetSelected( SelectionEvent e ) {
                FilteredResourcesSelectionDialog fd = new FilteredResourcesSelectionDialog( Display.getCurrent()
                                                                                                .getActiveShell(),
                                                                                            false,
                                                                                            ResourcesPlugin.getWorkspace()
                                                                                                .getRoot(),
                                                                                            IResource.FILE );
                fd.setInitialPattern( "*.xml" );

                if ( fd.open() == Window.OK ) {
                    IFile selectedFile = (IFile) fd.getFirstResult();
                    if ( selectedFile != null ) {
                        fileName.setText( selectedFile.getLocation().toFile().getAbsolutePath() );
                    }
                }
            }
        } );

        fileRadio.addSelectionListener( new SelectionListener() {

            public void widgetDefaultSelected( SelectionEvent e ) {}

            public void widgetSelected( SelectionEvent e ) {
                if ( fileRadio.getSelection() ) {
                    fileLabel.setEnabled( true );
                    fileName.setEnabled( true );
                    file.setEnabled( true );
                    // mFileName_.setEditable( false );
                }
                else {
                    fileLabel.setEnabled( false );
                    fileName.setEnabled( false );
                    file.setEnabled( false );
                }
            }
        } );
    }

    /**
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    @Override
    protected void configureShell( Shell newShell ) {
        super.configureShell( newShell );
        newShell.setText( "FSM Generation Options" );
    }

}
