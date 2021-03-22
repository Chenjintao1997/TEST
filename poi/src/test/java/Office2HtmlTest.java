import com.cjt.office2html.WordUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Author: chenjintao
 * @Date: 2019-04-11 11:16
 */
@RunWith(JUnit4.class)
public class Office2HtmlTest {

    @Test
    public void doc2Html() throws IOException, ParserConfigurationException, TransformerException {

        WordUtils.wordToHtml(new FileInputStream("/Users/chenjintao/work/知识库/sckm/云客服-知识库-需求文档 v1.1.doc"),
                "云客服-知识库-需求文档 v1.1.doc",
                "/Users/chenjintao/work/知识库/sckm/云客服-知识库-需求文档 v1.1");
    }
}
