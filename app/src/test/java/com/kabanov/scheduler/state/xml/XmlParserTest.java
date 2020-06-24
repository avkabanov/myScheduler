package com.kabanov.scheduler.state.xml;

import android.support.annotation.NonNull;
import com.kabanov.scheduler.state.data.ActionDataState;
import com.kabanov.scheduler.state.data.ApplicationState;
import com.kabanov.scheduler.state.data.SettingsPersistence;
import com.kabanov.scheduler.utils.IOTestUtils;
import com.kabanov.scheduler.utils.TimeUtilsTest;
import java.util.Arrays;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;

public class XmlParserTest {

    private XmlParser xmlParser = new XmlParser();
    
    @Test
    public void stateShouldBeParsedToGivenXml() throws Exception {
        ApplicationState state = generateApplicationState();
        String formattedXml = xmlParser.parse(state);
        
        String expectedXml = IOTestUtils.readAllLinesFromResource("xml_state/current_state.xml").replace("\r\n", "\n");
        Assert.assertEquals(expectedXml, formattedXml);
    }
    
    @Test
    public void stateShouldBeReadFromGivenXml() throws Exception {
        ApplicationState expectedState = generateApplicationState();
        ApplicationState actualState = xmlParser
                .format(IOTestUtils.readAllLinesFromResource("xml_state/current_state.xml"));
        
        Assert.assertEquals(expectedState, actualState);
    }

    private ApplicationState generateApplicationState() {
        ApplicationState applicationState = new ApplicationState();
        applicationState.setActionDataStateList(generateActions());
        applicationState.setSettingsPersistence(generateSettings());
        return applicationState;
    }

    private SettingsPersistence generateSettings() {
        SettingsPersistence result = new SettingsPersistence();
        result.setFishModeEnabled(true);
        return result;
    }

    @NonNull
    private List<ActionDataState> generateActions() {
        return Arrays.asList(
                    new ActionDataState("action1", "firstActionName", 45, TimeUtilsTest.toDate("2020-02-09")),
                    new ActionDataState("action2", "secondActionName", 360, TimeUtilsTest.toDate("2019-04-15"))
            );
    }
}