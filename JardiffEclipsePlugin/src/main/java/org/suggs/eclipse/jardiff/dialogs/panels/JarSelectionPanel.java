/*
 * JarSelectionPanel.java created on 4 Aug 2010 07:33:10 by suggitpe for project JardiffPlugin
 * 
 */
package org.suggs.eclipse.jardiff.dialogs.panels;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * GUI panel that will allow you to select a jar file and then
 * 
 * @author suggitpe
 * @version 1.0 4 Aug 2010
 */
public class JarSelectionPanel extends Composite {

    private Text fromJarFileTextField;
    private Text toJarFileTextField;

    private String initialFromJarFileName;

    public JarSelectionPanel( Composite aComposite, String aInitialFromJarFilename ) {
        super( aComposite, SWT.NONE );
        initialFromJarFileName = aInitialFromJarFilename;
        initialisePanel();
    }

    public JarSelectionPanel( Composite aComposite ) {
        this( aComposite, null );
    }

    private void initialisePanel() {
        Group group = new Group( this, SWT.None );
        group.setText( "Jar file selection" );

        group.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
        group.setLayout( new GridLayout( 3, false ) );

        buildFromJarRowInPanel( group );
        buildToJarRowInPanel( group );
        buildFlipJarsRowInPanel( group );

        group.pack();
    }

    private void buildFromJarRowInPanel( Composite aComposite ) {
        final Label fromJarLabel = new Label( aComposite, SWT.NONE );
        fromJarLabel.setText( "From Jar:" );

        fromJarFileTextField = new Text( aComposite, SWT.BORDER );
        if ( initialFromJarFileName != null && !initialFromJarFileName.isEmpty() ) {
            fromJarFileTextField.setText( initialFromJarFileName );
        }
        GridData gridData = new GridData( GridData.FILL_HORIZONTAL );
        gridData.widthHint = 400;
        fromJarFileTextField.setLayoutData( gridData );

        final Button fromButton = new Button( aComposite, SWT.NONE );
        fromButton.setText( "..." );
        fromButton.addSelectionListener( buildSelectionListenerFileDialogForTextField( fromJarFileTextField,
                                                                                       toJarFileTextField ) );
    }

    private void buildToJarRowInPanel( Composite aComposite ) {
        final Label toJarLabel = new Label( aComposite, SWT.NONE );
        toJarLabel.setText( "To Jar:" );

        toJarFileTextField = new Text( aComposite, SWT.BORDER );
        GridData gridData = new GridData( GridData.FILL_HORIZONTAL );
        gridData.widthHint = 400;
        toJarFileTextField.setLayoutData( gridData );

        final Button toButton = new Button( aComposite, SWT.NONE );
        toButton.setText( "..." );
        toButton.addSelectionListener( buildSelectionListenerFileDialogForTextField( toJarFileTextField,
                                                                                     fromJarFileTextField ) );
    }

    private void buildFlipJarsRowInPanel( Composite aComposite ) {
        final Button flipToFromButton = new Button( aComposite, SWT.NONE );
        flipToFromButton.setText( "Flip to and from jars" );
        flipToFromButton.addSelectionListener( buildJarFlipSelectionListener() );
    }

    private SelectionListener buildJarFlipSelectionListener() {
        return new SelectionAdapter() {

            @Override
            public void widgetSelected( SelectionEvent e ) {
                String originalFromText = fromJarFileTextField.getText();
                setFromJarNameText( toJarFileTextField.getText() );
                setToJarNameText( originalFromText );
            }
        };
    }

    private SelectionListener buildSelectionListenerFileDialogForTextField( final Text aTextField,
                                                                            final Text aAlternateTextField ) {
        return new SelectionAdapter() {

            @Override
            public void widgetSelected( SelectionEvent e ) {

                String initialDirectory = null;
                if ( aTextField.getText() != null && !aTextField.getText().isEmpty() ) {
                    initialDirectory = extractParentDirectoryFromPathString( aTextField.getText() );
                }
                else if ( aAlternateTextField != null && !aAlternateTextField.getText().isEmpty() ) {
                    initialDirectory = extractParentDirectoryFromPathString( aAlternateTextField.getText() );
                }
                FileDialog fileDialog = buildArchiveFileDialog( initialDirectory );
                String fileLocation = fileDialog.open();
                if ( fileLocation != null && !fileLocation.equals( "" ) ) {
                    aTextField.setText( fileLocation );
                    aTextField.setToolTipText( fileLocation );
                }
            }
        };
    }

    private final FileDialog buildArchiveFileDialog( String fileDialogStartDirectory ) {
        FileDialog fileDialog = new FileDialog( Display.getCurrent().getActiveShell(), SWT.READ_ONLY );
        fileDialog.setFilterNames( new String[] { "Java Archives", "Web Archives",
                                                 "Enterprise Application Archives", "Resource Archives" } );
        fileDialog.setFilterExtensions( new String[] { "*.jar", "*.war", "*.ear", "*.rar" } );
        if ( fileDialogStartDirectory != null && !fileDialogStartDirectory.isEmpty() ) {
            fileDialog.setFilterPath( fileDialogStartDirectory );
        }
        return fileDialog;
    }

    private void setToJarNameText( String aJarName ) {
        toJarFileTextField.setText( aJarName );
        toJarFileTextField.setToolTipText( aJarName );
    }

    private void setFromJarNameText( String aJarName ) {
        fromJarFileTextField.setText( aJarName );
        fromJarFileTextField.setToolTipText( aJarName );
    }

    /**
     * Static method that allows us to extract the parnt dir from a given file location
     * 
     * @param initialPath
     *            the path from which to extract the parent dir
     * @return the parent dir
     */
    static String extractParentDirectoryFromPathString( String initialPath ) {
        if ( initialPath == null || initialPath.isEmpty() ) {
            return null;
        }

        int locationOfLastSeparator = initialPath.lastIndexOf( File.separatorChar );
        if ( locationOfLastSeparator == -1 ) {
            return null;
        }

        return initialPath.substring( 0, locationOfLastSeparator );
    }

    public void setInitialFromJarFileName( String aInitialFromJarFile ) {
        initialFromJarFileName = aInitialFromJarFile;
        setFromJarNameText( aInitialFromJarFile );
    }

    /**
     * Returns the value of fromJarFileTextField.
     * 
     * @return Returns the fromJarFileTextField.
     */
    public Text getFromJarFileTextField() {
        return fromJarFileTextField;
    }

    /**
     * Returns the value of toJarFileTextField.
     * 
     * @return Returns the toJarFileTextField.
     */
    public Text getToJarFileTextField() {
        return toJarFileTextField;
    }

}
