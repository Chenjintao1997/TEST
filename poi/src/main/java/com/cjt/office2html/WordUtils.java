package com.cjt.office2html;

import com.cjt.file.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.util.XMLHelper;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * @Author: chenjintao
 * @Date: 2019-04-11 11:21
 */
public class WordUtils {

    public static void wordToHtml(InputStream is, String fileName, String tempPath) throws IOException, ParserConfigurationException, TransformerException {

        String htmlTatgetPath = tempPath + File.separatorChar + FileUtil.getFileName(fileName,false)+".html";
        HWPFDocument hwpfDocument = new HWPFDocument(is);

        Document newDocument = XMLHelper.getDocumentBuilderFactory().newDocumentBuilder().newDocument();
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                newDocument);

        wordToHtmlConverter.setPicturesManager((content, pictureType, suggestedName, widthInches, heightInches) -> "./" + suggestedName);

        wordToHtmlConverter.processDocument(hwpfDocument);

        StringWriter stringWriter = new StringWriter();

        Transformer transformer = TransformerFactory.newInstance()
                .newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        transformer.setOutputProperty(OutputKeys.METHOD, "html");
        transformer.transform(
                new DOMSource(wordToHtmlConverter.getDocument()),
                new StreamResult(stringWriter));

        String htmlContent =  stringWriter.toString();

        FileUtil.writeFile(htmlContent,htmlTatgetPath);
    }
}
