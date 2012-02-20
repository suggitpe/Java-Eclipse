/*
 * ExtractParentDirParameterizedTest.java created on 3 Aug 2010 07:27:42 by suggitpe for project JardiffPlugin
 * 
 */
package org.suggs.eclipse.jardiff.dialogs.panels;

import org.suggs.eclipse.jardiff.dialogs.panels.JarSelectionPanel;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Test class for the get jars dialog box parent dir extraction function. This is a parameterized test so that
 * it can exectute the same function for a given set of inputs.
 * 
 * @author suggitpe
 * @version 1.0 3 Aug 2010
 */
@RunWith(Parameterized.class)
public class ExtractParentDirParameterizedTest {

    private static final Log LOG = LogFactory.getLog( ExtractParentDirParameterizedTest.class );

    private String initialPath;
    private String extractedPath;

    public ExtractParentDirParameterizedTest( String initial, String extracted ) {
        initialPath = initial;
        extractedPath = extracted;
    }

    @Parameters
    public static Collection<String[]> data() {
        String[][] data = new String[][] {
                                          { "/home/foobar/somewhere/over/the/rainbow/dorothy.jar",
                                           "/home/foobar/somewhere/over/the/rainbow" },
                                          { "/home/foobar/hello.jar", "/home/foobar" }, { "goo.jar", null },
                                          { null, null }, { "", null } };
        return Arrays.asList( data );
    }

    @Test
    public void extractsParentDirFromParameter() {
        String actualExtracted = JarSelectionPanel.extractParentDirectoryFromPathString( initialPath );
        assertThat( actualExtracted, equalTo( extractedPath ) );
        LOG.debug( "Parent dir extractor has extracted [" + actualExtracted + "] from [" + initialPath + "]" );
    }
}
