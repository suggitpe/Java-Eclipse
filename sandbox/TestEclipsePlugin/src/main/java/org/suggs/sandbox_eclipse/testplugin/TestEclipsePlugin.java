package org.suggs.sandbox_eclipse.testplugin;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author suggitpe
 * @version 1.0 4 Mar 2008
 */
public class TestEclipsePlugin extends AbstractUIPlugin
{

    // The plug-in ID
    public static final String PLUGIN_ID = "SandboxTestEclipsePlugin";

    // The shared instance
    private static TestEclipsePlugin plugin;

    /**
     * The constructor
     */
    public TestEclipsePlugin()
    {
        super();
    }

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start( BundleContext context ) throws Exception
    {
        super.start( context );
        plugin = this;
    }

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop( BundleContext context ) throws Exception
    {
        plugin = null;
        super.stop( context );
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static TestEclipsePlugin getDefault()
    {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given
     * plug-in relative path
     * 
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor( String path )
    {
        return imageDescriptorFromPlugin( PLUGIN_ID, path );
    }
}
