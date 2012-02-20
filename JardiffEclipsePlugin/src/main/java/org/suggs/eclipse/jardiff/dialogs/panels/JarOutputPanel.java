/*
 * JarOutputPanel.java created on 4 Aug 2010 07:34:44 by suggitpe for project JardiffPlugin
 * 
 */
package org.suggs.eclipse.jardiff.dialogs.panels;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * TODO Write javadoc for JarOutputPanel
 * 
 * @author suggitpe
 * @version 1.0 4 Aug 2010
 */
public class JarOutputPanel extends Composite {

    @SuppressWarnings("unused")
    private static final Log LOG = LogFactory.getLog( JarOutputPanel.class );

    public JarOutputPanel( Composite aComposite ) {
        super( aComposite, SWT.NONE );
    }
}
