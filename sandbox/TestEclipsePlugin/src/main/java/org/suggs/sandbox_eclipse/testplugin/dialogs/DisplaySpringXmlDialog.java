/*
 * DisplaySpringXmlDialog.java created on 3 Apr 2008 18:16:58 by suggitpe for project Sandbox - TestEclipsePlugin
 * 
 */
package org.suggs.sandbox_eclipse.testplugin.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Dialog to show to the user the results from the xml generation
 * 
 * @author suggitpe
 * @version 1.0 3 Apr 2008
 */
public class DisplaySpringXmlDialog extends Dialog {

    String xml;

    /**
     * Constructs a new instance.
     * 
     * @param aParentShell
     * @param aXml
     */
    public DisplaySpringXmlDialog( Shell aParentShell, String aXml ) {
        super( aParentShell );
        xml = aXml;
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createDialogArea( Composite parent ) {
        Composite ret = (Composite) super.createDialogArea( parent );

        // set the group
        Group grp = new Group( parent, SWT.None );
        grp.setText( "Spring XML" );

        // set the layout
        grp.setLayout( new GridLayout( 3, false ) );
        grp.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

        // now we can build the internal widgets
        Text res = new Text( grp, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY );
        res.setText( xml );
        // res.selectAll();

        // now we want to set up the layout data for the text area
        GridData data = new GridData();
        data.widthHint = 700;
        data.heightHint = 300;
        data.horizontalSpan = 3;
        res.setLayoutData( data );

        ret.pack();
        return ret;
    }

    /**
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    @Override
    protected void configureShell( Shell newShell ) {
        super.configureShell( newShell );
        newShell.setText( "FSM Spring XML Results" );
    }

}
