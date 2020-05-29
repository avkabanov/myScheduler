package com.kabanov.scheduler.state.xml;

import java.io.ByteArrayOutputStream;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.kabanov.scheduler.state.data.ApplicationState;
import com.kabanov.scheduler.state.data.legacy.ApplicationState00;

public class XmlParser {

    public String parse(ApplicationState applicationState) throws Exception {
        Serializer serializer = new Persister();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        serializer.write(applicationState, byteArrayOutputStream);
        
        return byteArrayOutputStream.toString();
    }
    
    public ApplicationState format(String xml) throws Exception {
        Serializer serializer = new Persister();
        try {
            return serializer.read(ApplicationState.class, xml);
        } catch (Exception e) {
            return new ApplicationState00.Converter().convert(serializer.read(ApplicationState00.class, xml));
        }
    }
}
