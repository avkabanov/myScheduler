package com.kabanov.scheduler.state.xml;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.kabanov.scheduler.state.data.ApplicationState;
import com.kabanov.scheduler.utils.IOTestUtils;

import junit.framework.Assert;

@RunWith(Parameterized.class)
public class XmlParserRetrospectiveTest {

    private XmlParser xmlParser = new XmlParser();
    
    private String statePath;
    private ApplicationState expectedState;

    public XmlParserRetrospectiveTest(String statePath, ApplicationState expectedState) {
        this.statePath = statePath;
        this.expectedState = expectedState;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"xml_state/01.xml", ApplicationStateFactory.applicationState01()} 
        });
    }

    @Test
    public void shouldFormatAllPreviousStateVersionsToCurrentState() throws Exception {
        ApplicationState actualState = xmlParser.format(IOTestUtils.readAllLinesFromResource(statePath));
        Assert.assertEquals(expectedState, actualState);
    }
}