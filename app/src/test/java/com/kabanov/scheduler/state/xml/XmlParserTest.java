package com.kabanov.scheduler.state.xml;

import org.junit.Test;

import com.kabanov.scheduler.state.data.ApplicationState;

public class XmlParserTest {
    
    private XmlParser xmlParser = new XmlParser();
    
    @Test
    public void shouldFormatStateWhenValidStateIsGiven() throws Exception {

        ApplicationState result = xmlParser.format(state);
        System.out.println(result);
        
    }
    
    private String state = "<ApplicationState>\n" +
            "    <ActionDataStateList class=\"java.util.ArrayList\">\n" +
            "        <actionDataState><id>action1</id><name>Подстричься</name><periodicityDays>45</periodicityDays><lastExecutionDate>2020-02-09</lastExecutionDate></actionDataState>\n" +
            "        <actionDataState><id>action2</id><name>Гигиеническая чистка зубов</name><periodicityDays>360</periodicityDays><lastExecutionDate>2019-04-15</lastExecutionDate></actionDataState>\n" +
            "        <actionDataState><id>action3</id><name>Помыть машину</name><periodicityDays>45</periodicityDays><lastExecutionDate>2020-03-01</lastExecutionDate></actionDataState>\n" +
            "        <actionDataState><id>action4</id><name>Побриться</name><periodicityDays>7</periodicityDays><lastExecutionDate>2020-04-14</lastExecutionDate></actionDataState>\n" +
            "        <actionDataState><id>action5</id><name>Кварплата Коммунарка</name><periodicityDays>30</periodicityDays><lastExecutionDate>2020-04-16</lastExecutionDate></actionDataState>\n" +
            "        <actionDataState><id>action6</id><name>Почистить отпариватель</name><periodicityDays>75</periodicityDays><lastExecutionDate>2020-03-12</lastExecutionDate></actionDataState>\n" +
            "        <actionDataState><id>action7</id><name>Почистить пылесос</name><periodicityDays>60</periodicityDays><lastExecutionDate>2020-04-11</lastExecutionDate></actionDataState>\n" +
            "        <actionDataState><id>action8</id><name>Поменять фильтр в пылесосе</name><periodicityDays>90</periodicityDays><lastExecutionDate>2020-04-11</lastExecutionDate></actionDataState>\n" +
            "        <actionDataState><id>action9</id><name>Поменять зубную щетку</name><periodicityDays>90</periodicityDays><lastExecutionDate>2020-04-28</lastExecutionDate></actionDataState>\n" +
            "        <actionDataState><id>action10</id><name>Поменять накладки</name><periodicityDays>180</periodicityDays><lastExecutionDate>2020-01-31</lastExecutionDate></actionDataState>\n" +
            "    </ActionDataStateList>\n" +
            "</ApplicationState>";
}