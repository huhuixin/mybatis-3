package com.hhx.mybatis.parsing;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XPathParser;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class XPathParser001 {

    @Test
    public void test() {
        XPathParser pathParser = new XPathParser("other/text001.xml");
        log.info("configuration.properties.resource {}",
                pathParser.evalString("configuration.properties.resource"));

    }

    @Test
    public void testDoc() throws IOException, ParserConfigurationException, SAXException {
        InputStream inputStream = Resources.getResourceAsStream("other/text001.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputStream);
        log.info(" document {}", document);
    }
}
