package com.cjt.test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.browser.BrowserFetcher;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.*;
import com.ruiyun.jvppeteer.util.StreamUtil;
import gui.ava.html.image.generator.HtmlImageGenerator;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bytedeco.javacv.FrameRecorder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.ExecutionException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @Author: chenjintao
 * @Date: 2020/11/16 11:33
 */
@RunWith(JUnit4.class)
public class TestClazz {

    @Test
    public void test1() {

        //s:10,fps:10,p:20
        int s = 3;

        int fps = 5;

        int p = 100; //p = files.length
        if (p == 0 || s == 0 || fps == 0) return;
        if (s * fps < p) fps = p % s > 0 ? (p / s) + 1 : p / s;
        int allFrameCount = s * fps;
        int pps = allFrameCount / p;
        int ppsRemainder = allFrameCount % p;


        for (int i = 0; i < p; i++) {
            System.out.println("p:" + i);
            //new BufferedImage = files[i];

            for (int j = 0; j < pps; j++) {
                System.out.println("j:" + j);
            }

            //余数桢处理
            if (i % (p / ppsRemainder) == 0) {
                System.out.println("do remainder");
            }
        }


//        for (int i = 0; i < allFrameCount; i++) {
//            System.out.println("frame:" + i);
//            if (i % 5 == 0)
//                int file_index = 0;
//            //new BufferedImage = files[]
//        }
    }

    @Test
    public void test2() {
        System.out.println(1 / 2);

        System.out.println(24 % 20);

    }

    @Test
    public void test3() throws IOException {
        //合成的MP4
        String mp4SavePath = "./video/1/img.mp4";
        //图片地址
//        String img = "/Users/chenjintao/Pictures/表情包";
//        String img = "/Users/chenjintao/Pictures/1";
        String img = "./img/1/";
//        int width = 580;
//        int height = 720;
        int width = 544;
        int height = 960;
        //读取所有图片
        File file = new File(img);
        File[] files = file.listFiles();
        int frameRate = 24;         //fps
        int videoLen = 10;          //video时长(s)
//        createMp4(mp4SavePath, imgMap, frameRate, videoLen, width, height);
        Img2Video.createMp4(mp4SavePath, files, frameRate, videoLen, width, height);
    }


    @Test
    public void testHtml2Img() throws Exception {
        String html = "\n" +
                "<!doctype html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>div分区元素</title>\n" +
                "<meta charset=\"utf-8\" />\n" +
                "</head>\n" +
                "<body>\n" +
                "  <!-- logo/工具 -->\n" +
                "  <div style=\"border: 1px solid yellow;\">\n" +
                "    <h1>这里放置logo和按钮</h1>\n" +
                "<img src=\"data:image/png;base64,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\" alt=\"国内新闻\" />" +

                "  </div>\n" +
                "  <!-- 内容 -->\n" +
                "  <div style=\"border: 1px solid yellow;\">\n" +
                "    <p>Hello.</p>\n" +
                "    <p>How are you.</p>\n" +
                "    <p>And you?</p>\n" +
                "  </div>\n" +
                "  <!-- 版权标识 -->\n" +
                "  <div style=\"border: 1px solid yellow;\">\n" +
                "    <p>盗版必究</p>\n" +
                "  </div>\n" +
                "</body>\n" +
                " \n" +
                "</html>";

        StringBuilder html1 = new StringBuilder();
        html1.append(
                "<!doctype html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                        "<title>新闻中国</title>\n" +
                        "<link href=\"CSS/main.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<div id=\"header\">\n" +
                        "  <div id=\"top_login\">\n" +
                        "    <label> 登录名 </label>\n" +
                        "    <input type=\"text\" id=\"uname\" value=\"\" class=\"login_input\" />\n" +
                        "    <label> 密&#160;&#160;码 </label>\n" +
                        "    <input type=\"password\" id=\"upwd\" value=\"\" class=\"login_input\" />\n" +
                        "    <input type=\"button\" class=\"login_sub\" value=\"登录\" onclick=\"login()\"/>\n" +
                        "    <label id=\"error\"> </label>\n" +
                        "    <img src=\"Images/friend_logo.gif\" alt=\"Google\" id=\"friend_logo\" /> </div>\n" +
                        "  <div id=\"nav\">\n" +
                        "    <div id=\"logo\"> <img src=\"Images/logo.jpg\" alt=\"新闻中国\" /> </div>\n" +
                        "    <div id=\"a_b01\"> <img src=\"Images/a_b01.gif\" alt=\"\" /> </div>\n" +
                        "    <!--mainnav end-->\n" +
                        "  </div>\n" +
                        "</div>\n" +
                        "<div id=\"container\">\n" +
                        "  <div class=\"sidebar\">\n" +
                        "    <h1> <img src=\"Images/title_1.gif\" alt=\"国内新闻\" /> </h1>\n" +
                        "    <div class=\"side_list\">\n" +
                        "      <ul>\n" +
                        "        <li> <a href='#'><b> 重庆涉黑富豪黎强夫妇庭审答辩言辞相互矛盾 </b></a> </li>\n" +
                        "        <li> <a href='#'><b> 发改委：4万亿投资计划不会挤占民间投资空间 </b></a> </li>\n" +
                        "        <li> <a href='#'><b> 河南2个乡镇政绩报告内容完全一致引关注 </b></a> </li>\n" +
                        "      </ul>\n" +
                        "    </div>\n" +
                        "    <h1> <img src=\"Images/title_2.gif\" alt=\"国际新闻\" /> </h1>\n" +
                        "    <div class=\"side_list\">\n" +
                        "      <ul>\n" +
                        "        <li> <a href='#'><b> 日本首相鸠山首次全面阐述新政府外交政策 </b></a> </li>\n" +
                        "        <li> <a href='#'><b> 黎巴嫩以色列再次交火互射炮弹 </b></a> </li>\n" +
                        "        <li> <a href='#'><b> 伊朗将于30日前就核燃料供应方案作出答复 </b></a> </li>\n" +
                        "        <li> <a href='#'><b> 与基地有关组织宣称对巴格达连环爆炸负责 </b></a> </li>\n" +
                        "      </ul>\n" +
                        "    </div>\n" +
                        "    <h1> <img src=\"Images/title_3.gif\" alt=\"娱乐新闻\" /> </h1>\n" +
                        "    <div class=\"side_list\">\n" +
                        "      <ul>\n" +
                        "        <li> <a href='#'><b> 施瓦辛格启动影视业回迁计划 推进加州经济复苏 </b></a> </li>\n" +
                        "        <li> <a href='#'><b> 《沧海》导演回应观众质疑 自信能超越《亮剑》 </b></a> </li>\n" +
                        "        <li> <a href='#'><b> 《海角七号》导演新片开机 吴宇森等出席 </b></a> </li>\n" +
                        "        <li> <a href='#'><b> 《四大名捕》敦煌热拍 八主演飙戏火花四溅 </b></a> </li>\n" +
                        "      </ul>\n" +
                        "    </div>\n" +
                        "  </div>\n" +
                        "  <div class=\"main\">\n" +
                        "    <div class=\"class_type\"> <img src=\"Images/class_type.gif\" alt=\"新闻中心\" /> </div>\n" +
                        "    <div class=\"content\">\n" +
                        "      <ul class=\"class_date\">\n" +
                        "        <li id='class_month'> <a href='#'><b> 国内 </b></a> <a href='#'><b> 国际 </b></a> <a href='#'><b> 军事 </b></a> <a href='#'><b> 体育 </b></a> <a href='#'><b> 娱乐 </b></a> <a href='#'><b> 社会 </b></a> <a href='#'><b> 财经 </b></a> <a href='#'><b> 科技 </b></a> <a href='#'><b> 健康 </b></a> <a href='#'><b> 汽车 </b></a> <a href='#'><b> 教育 </b></a> </li>\n" +
                        "        <li id='class_month'> <a href='#'><b> 房产 </b></a> <a href='#'><b> 家居 </b></a> <a href='#'><b> 旅游 </b></a> <a href='#'><b> 文化 </b></a> <a href='#'><b> 其他 </b></a> </li>\n" +
                        "      </ul>\n" +
                        "      <ul class=\"classlist\">\n" +
                        "        <li><a href='newspages/news_add.html'> 深足教练组：说我们买球是侮辱 朱广沪常暗中支招 </a><span> 2009-10-28 01:03:51.0 </span></li>\n" +
                        "        <li><a href='#'> 省政府500万悬赏建业登顶 球员:遗憾主场放跑国安 </a><span> 2009-10-28 01:03:19.0 </span></li>\n" +
                        "        <li><a href='#'> 洪元硕：北京人的脸就看你们了 最后一哆嗦看着办 </a><span> 2009-10-28 01:02:56.0 </span></li>\n" +
                        "        <li><a href='#'> 临界冠军京城夺票总动员 球迷:夺冠!让所有人闭嘴 </a><span> 2009-10-28 01:02:17.0 </span></li>\n" +
                        "        <li><a href='#'> 一纸传真暗含申花处理态度 国足征调杜威突生悬疑 </a><span> 2009-10-28 01:01:47.0 </span></li>\n" +
                        "        <li class='space'></li>\n" +
                        "        <li><a href='#'> 气候变化导致海平面上升 </a><span> 2009-10-28 01:00:37.0 </span></li>\n" +
                        "        <li><a href='#'> 商贸联委会在杭州开会 中美对贸易争端态度低调 </a><span> 2009-10-28 01:00:11.0 </span></li>\n" +
                        "        <li><a href='#'> 迟福林：“十二五”改革应向消费大国转型 </a><span> 2009-10-28 12:59:45.0 </span></li>\n" +
                        "        <li><a href='#'> 伊朗称放弃美元作为外储地位 人民币或上位 </a><span> 2009-10-28 12:58:42.0 </span></li>\n" +
                        "        <li><a href='#'> “持械抢劫，当场击毙” 浙江永康现超雷人标语 </a><span> 2009-10-28 12:58:20.0 </span></li>\n" +
                        "        <li class='space'></li>\n" +
                        "        <li><a href='#'> 国内成品油价格上调几成定局 </a><span> 2009-10-28 12:57:18.0 </span></li>\n" +
                        "        <li><a href='#'> 俄数百所幼儿园和学校因流感停课 </a><span> 2009-10-28 12:56:51.0 </span></li>\n" +
                        "        <li><a href='#'> 喀布尔市中心传出爆炸和枪声 目前原因不明 </a><span> 2009-10-28 12:56:24.0 </span></li>\n" +
                        "        <li><a href='#'> 国台办介绍大陆高校加大对台招生力度情况 </a><span> 2009-10-28 12:55:07.0 </span></li>\n" +
                        "        <li><a href='#'> 国台办将授权福建等六省市部分赴台管理审批权 </a><span> 2009-10-28 12:54:12.0 </span></li>\n" +
                        "        <li class='space'></li>\n" +
                        "        <li><a href='#'> 人保部将首次就同工同酬做出规定 </a><span> 2009-10-28 11:00:45.0 </span></li>\n" +
                        "        <li><a href='#'> 重庆警方否认被围殴致死吸毒者曾针刺民众 </a><span> 2009-10-28 11:00:25.0 </span></li>\n" +
                        "        <li><a href='#'> 美国一名外交官辞职抗议美对阿富汗政策 </a><span> 2009-10-28 11:00:02.0 </span></li>\n" +
                        "        <li><a href='#'> 埃及交通部长因致18死火车相撞事故辞职 </a><span> 2009-10-28 10:59:38.0 </span></li>\n" +
                        "        <li><a href='#'> 无姚时代如何关注内线比拼 新赛季中锋PK五岳崛起 </a><span> 2009-10-28 10:59:07.0 </span></li>\n" +
                        "        <li class='space'></li>\n" +
                        "        <li><a href='#'> 火箭揭幕战是试金石摸底测验 五号位二选一有玄机 </a><span> 2009-10-28 10:58:36.0 </span></li>\n" +
                        "        <li><a href='#'> 时过境迁火箭优势变劣势 对抗双塔阿帅只一计可施 </a><span> 2009-10-28 10:58:11.0 </span></li>\n" +
                        "        <li><a href='#'> 无姚新赛季落寞油然而生 周遭一种改变正悄悄开始 </a><span> 2009-10-28 10:57:49.0 </span></li>\n" +
                        "        <li><a href='#'> 美媒体称小布乃火箭新核心 一大缺陷令阿帅很担心 </a><span> 2009-10-28 10:57:11.0 </span></li>\n" +
                        "        <li><a href='#'> 皮特车祸之后仍然开朗 轻松改驾四轮车(图) </a><span> 2009-10-28 10:56:43.0 </span></li>\n" +
                        "        <li class='space'></li>\n" +
                        "        <li><a href='#'> 8名美军士兵在阿富汗遭路边炸弹袭击身亡 </a><span> 2009-10-28 10:55:38.0 </span></li>\n" +
                        "        <li><a href='#'> 与基地有关组织宣称对巴格达连环爆炸负责 </a><span> 2009-10-28 10:55:14.0 </span></li>\n" +
                        "        <li><a href='#'> 伊朗将于30日前就核燃料供应方案作出答复 </a><span> 2009-10-28 10:54:54.0 </span></li>\n" +
                        "        <p align=\"right\"> 当前页数:[1/2]&nbsp; <a href=\"#\">下一页</a> <a href=\"#\">末页</a> </p>\n" +
                        "      </ul>\n" +
                        "    </div>\n" +
                        "    <div class=\"picnews\">\n" +
                        "      <ul>\n" +
                        "        <li> <a href=\"#\"><img src=\"Images/Picture1.jpg\" width=\"249\" alt=\"\" /> </a><a href=\"#\">幻想中穿越时空</a> </li>\n" +
                        "        <li> <a href=\"#\"><img src=\"Images/Picture2.jpg\" width=\"249\" alt=\"\" /> </a><a href=\"#\">国庆多变的发型</a> </li>\n" +
                        "        <li> <a href=\"#\"><img src=\"Images/Picture3.jpg\" width=\"249\" alt=\"\" /> </a><a href=\"#\">新技术照亮都市</a> </li>\n" +
                        "        <li> <a href=\"#\"><img src=\"Images/Picture4.jpg\" width=\"249\" alt=\"\" /> </a><a href=\"#\">群星闪耀红地毯</a> </li>\n" +
                        "      </ul>\n" +
                        "    </div>\n" +
                        "  </div>\n" +
                        "</div>\n" +
                        "<div id=\"friend\">\n" +
                        "  <h1 class=\"friend_t\"> <img src=\"Images/friend_ico.gif\" alt=\"合作伙伴\" /> </h1>\n" +
                        "  <div class=\"friend_list\">\n" +
                        "    <ul>\n" +
                        "      <li> <a href=\"#\">中国政府网</a> </li>\n" +
                        "      <li> <a href=\"#\">中国政府网</a> </li>\n" +
                        "      <li> <a href=\"#\">中国政府网</a> </li>\n" +
                        "      <li> <a href=\"#\">中国政府网</a> </li>\n" +
                        "      <li> <a href=\"#\">中国政府网</a> </li>\n" +
                        "      <li> <a href=\"#\">中国政府网</a> </li>\n" +
                        "      <li> <a href=\"#\">中国政府网</a> </li>\n" +
                        "    </ul>\n" +
                        "  </div>\n" +
                        "</div>\n" +
                        "<div id=\"footer\">\n" +
                        "  <p class=\"\"> 24小时客户服务热线：010-68988888 &#160;&#160;&#160;&#160; <a href=\"#\">常见问题解答</a> &#160;&#160;&#160;&#160; 新闻热线：010-627488888 <br />\n" +
                        "    文明办网文明上网举报电话：010-627488888 &#160;&#160;&#160;&#160; 举报邮箱： <a href=\"#\">jubao@jb-aptech.com.cn</a> </p>\n" +
                        "  <p class=\"copyright\"> Copyright &copy; 1999-2009 News China gov, All Right Reserver <br />\n" +
                        "    新闻中国 版权所有 </p>\n" +
                        "</div>\n" +
                        "</body>\n" +
                        "</html>\n"
        );
        html2jpeg(Color.white, html.toString(), 900, 1600, null);
    }

    public String html =
            "<div class=\"ctrlItem\" data-type=\"2\" style=\"position: absolute; top: 109px; left: 17px; width: 48px; height: 48px; right: auto; bottom: auto;\">\n" +
                    "      <img tabindex=\"0\" class=\"ctrl ctrlImg\" style=\"vertical-align: middle; width: 32px; height: 32px;\" src=\"http://123.57.89.226:8002/ccas/call/ivr/video/resource/download/350868f2cc534296b1d40c1a69e36635\">\n" +
                    "    <i style=\"display:none;\" class=\"el-icon-close btnRemove\"></i></div><div class=\"ctrlItem\" data-type=\"2\" style=\"position: absolute; top: 208px; left: 17px; width: 48px; height: 48px; right: auto; bottom: auto;\">\n" +
                    "      <img tabindex=\"0\" class=\"ctrl ctrlImg\" style=\"vertical-align: middle; width: 32px; height: 32px;\" src=\"http://123.57.89.226:8002/ccas/call/ivr/video/resource/download/617fd7193d1f46aa82f1643747a283a6\">\n" +
                    "    <i style=\"display:none;\" class=\"el-icon-close btnRemove\"></i></div><div class=\"ctrlItem active\" data-type=\"1\" style=\"position: absolute; top: 115.5px; left: 63px; width: 164.125px; height: 19px; right: auto; bottom: auto;\">\n" +
                    "        <span tabindex=\"0\" class=\"ctrl ctrlText\" style=\"color: rgb(96, 98, 102); font-size: 14px; font-weight: bold;\">企业{WANG_DOG}钱一只</span>\n" +
                    "    <i style=\"display:none;\" class=\"el-icon-close btnRemove\"></i></div><div class=\"ctrlItem\" data-type=\"1\" style=\"position: absolute; top: 214.5px; left: 63px; width: 189px; height: 19px; right: auto; bottom: auto;\">\n" +
                    "        <span tabindex=\"0\" class=\"ctrl ctrlText\" style=\"color: rgb(96, 98, 102); font-size: 14px; font-weight: bold;\">考拉{HELLO_WORLD}钱一只</span>\n" +
                    "    <i style=\"display:none;\" class=\"el-icon-close btnRemove\"></i></div><div class=\"ctrlItem\" data-type=\"1\" style=\"position: absolute; top: 16px; left: 135px; width: 105px; height: 20px; right: auto; bottom: auto;\">\n" +
                    "        <span tabindex=\"0\" class=\"ctrl ctrlText\" style=\"color: rgb(96, 98, 102); font-size: 15px; font-weight: bold;\">已购买商品清单</span>\n" +
                    "    <i style=\"display:none;\" class=\"el-icon-close btnRemove\"></i></div>";
    public static byte[] html2jpeg(Color bgColor, String html, int width,
                                   int height, EmptyBorder eb) throws Exception {
        String imageName = "./img/c1.jpg";
        JTextPane tp = new JTextPane();
        tp.setSize(width, height);
        if (eb == null) {
            eb = new EmptyBorder(0, 50, 0, 50);
        }
        if (bgColor != null) {
            tp.setBackground(bgColor);
        }
        if (width <= 0) {
            width = 900;
        }
        if (height <= 0) {
            height = 1600;
        }
        tp.setBorder(eb);
        tp.setContentType("text/html");
        tp.setText(html);

        int pageIndex = 1;
        boolean bcontinue = true;
        String resUrl = "";
        byte[] bytes = null;
        while (bcontinue) {
            BufferedImage image = new BufferedImage(width,
                    height, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            g.setClip(0, 0, width, height);
            bcontinue = paintPage(g, height, pageIndex, tp);
            g.dispose();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);

            baos.flush();
            bytes = baos.toByteArray();
            baos.close();
            FileUtils.writeByteArrayToFile(new File(imageName), bytes);
            //写入阿里云
            pageIndex++;
        }
        return bytes;
    }

    public static boolean paintPage(Graphics g, int hPage, int pageIndex, JTextPane panel) {
        Graphics2D g2 = (Graphics2D) g;
        Dimension d = (panel.getUI()).getPreferredSize(panel);
        double panelHeight = d.height;
        double pageHeight = hPage;
        int totalNumPages = (int) Math.ceil(panelHeight / pageHeight);
        g2.translate(0f, -(pageIndex - 1) * pageHeight);
        panel.paint(g2);
        boolean ret = true;

        if (pageIndex >= totalNumPages) {
            ret = false;
            return ret;
        }
        return ret;
    }


    @Test
    public void testHtml2ImgForHtmlUnit() {
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);//新建一个模拟谷歌Chrome浏览器的浏览器客户端对象

        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX


        HtmlPage page = null;
        try {
            page = webClient.getPage("http://www.baidu.com/");//尝试加载上面图片例子给出的网页
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            webClient.close();
        }
    }


    @Test
    public void testHtml2Img3() throws FileNotFoundException {
        String html = "\n" +
                "<!doctype html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>div分区元素</title>\n" +
                "<meta charset=\"utf-8\" />\n" +
                "</head>\n" +
                "<body>\n" +
                "  <!-- logo/工具 -->\n" +
                "  <div style=\"border: 1px solid yellow;\">\n" +
                "    <h1>这里放置logo和按钮</h1>\n" +
                "<img src=\"data:image/png;base64,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\" alt=\"国内新闻\" />" +

                "  </div>\n" +
                "  <!-- 内容 -->\n" +
                "  <div style=\"border: 1px solid yellow;\">\n" +
                "    <p>Hello.</p>\n" +
                "    <p>How are you.</p>\n" +
                "    <p>And you?</p>\n" +
                "  </div>\n" +
                "  <!-- 版权标识 -->\n" +
                "  <div style=\"border: 1px solid yellow;\">\n" +
                "    <p>盗版必究</p>\n" +
                "  </div>\n" +
                "</body>\n" +
                " \n" +
                "</html>";
        StringBuilder html1 = new StringBuilder();
        html1.append(
                ""
        );
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
//        imageGenerator.loadUrl("http://www.google.com");
        imageGenerator.loadHtml(html);
        BufferedImage bufferedImage = getGrayPicture(imageGenerator.getBufferedImage());
        imageGenerator.saveAsImage("./img/c3.jpg");
//        OutputStream outputStream = new FileOutputStream("./img/c3.jpg");
//        try {
//            ImageIO.write(bufferedImage, "jpg", outputStream);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (outputStream != null) {
//                try {
//                    outputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }

    }

    public BufferedImage getGrayPicture(BufferedImage originalImage) {
        BufferedImage grayPicture;
        int imageWidth = originalImage.getWidth();
        int imageHeight = originalImage.getHeight();

        grayPicture = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_INT_RGB);
        ColorConvertOp cco = new ColorConvertOp(ColorSpace
                .getInstance(ColorSpace.CS_GRAY), null);
        cco.filter(originalImage, grayPicture);
        return grayPicture;
    }


    @Test
    public void testJvp() throws InterruptedException, ExecutionException, IOException {
        Puppeteer puppeteer = new Puppeteer();
        //自动下载，第一次下载后不会再下载
        //BrowserFetcher.downloadIfNotExist(null);

        String path = "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome";
//
        ArrayList<String> argList = new ArrayList<>();
        LaunchOptions options = new LaunchOptionsBuilder().withExecutablePath(path).withArgs(argList).withHeadless(true).build();
        argList.add("--no-sandbox");
        argList.add("--disable-setuid-sandbox");
        Browser browser = Puppeteer.launch(options);
        String version = browser.version();

        System.out.println(version);

        Page page = browser.newPage();
        page.goTo("https://www.baidu.com/");
        //page.goTo("./html/index.html");
//        page.setContent(
//                html
//        );

        ScreenshotOptions screenshotOptions = new ScreenshotOptions();
        //设置截图范围
//        Clip clip = new Clip(1.0,1.56,400,400);
        Clip clip = new Clip(1.0, 1.0, 900, 1600);
        //screenshotOptions.setClip(clip);

        //设置存放的路径
        screenshotOptions.setType("png");
        screenshotOptions.setPath("./img/c4.png");
        screenshotOptions.setFullPage(true);
        screenshotOptions.setEncoding("UTF-8");

        page.screenshot(screenshotOptions);
    }

    @Test
    public void testJvp2() throws InterruptedException, ExecutionException, IOException {
        Puppeteer puppeteer = new Puppeteer();
        //自动下载，第一次下载后不会再下载
        BrowserFetcher.downloadIfNotExist(null);

        String path = "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome";
//
        ArrayList<String> argList = new ArrayList<>();
        LaunchOptions options = new LaunchOptionsBuilder().withExecutablePath(path).withArgs(argList).withHeadless(false).build();
//        LaunchOptions options = new LaunchOptionsBuilder().withArgs(argList).withHeadless(false).build();

        argList.add("--no-sandbox");
        argList.add("--disable-setuid-sandbox");
        argList.add("--disable-gpu");
        Browser browser = Puppeteer.launch(options);

        //String version = browser.version();

        //System.out.println(version);

        Page page = browser.newPage();
        
//        page.goTo("http://caronline.deskpro.cn/h5/#/reservation/d89759bcb13a4837a2c738d6dd3e973b");
        //page.goTo("./html/index.html");
        PageNavigateOptions p = new PageNavigateOptions();
        ArrayList<String> waitUntil = new ArrayList<>();
        waitUntil.add("load");
        p.setWaitUntil(waitUntil);

        page.setContent(
                html
        );
        page.waitForNavigation();
       // page.setContent("<div> 123 </div>");

        ScreenshotOptions screenshotOptions = new ScreenshotOptions();
        //设置截图范围
//        Clip clip = new Clip(1.0,1.56,400,400);
        Clip clip = new Clip(1.0, 1.0, 900, 1600);
        //screenshotOptions.setClip(clip);

        //设置存放的路径
        screenshotOptions.setType("jpeg");
        screenshotOptions.setPath("./img/c7.jpeg");
        screenshotOptions.setFullPage(true);
        screenshotOptions.setEncoding("UTF-8");
        screenshotOptions.setQuality(100);

        Viewport viewport = new Viewport();
        viewport.setWidth(360);
        viewport.setHeight(640);


        page.setViewport(viewport);
        page.screenshot(screenshotOptions);
        page.close();
        System.out.println(page.isClosed());

    }

    @Test
    public void testAliasFile() {
    }

    @Test
    public void testa() {
        System.out.println(File.separator);
        System.out.println(File.separatorChar);
        System.out.println(File.pathSeparator);
        System.out.println(File.pathSeparatorChar);
    }

    @Test
    public void testUnZipMac() throws IOException {
        extractZip1("/Users/chenjintao/IdeaProjects/Test/test-media/.local-browser/chrome-mac.zip", "/Users/chenjintao/IdeaProjects/Test/test-media/.local-browser/mac-722234");
    }


    @Test
    public void testHtml2Img2() throws InterruptedException, ExecutionException, IOException {
        String html = "<div class=\"ctrlItem\" data-type=\"1\" style=\"position: absolute; inset: 71px auto auto 80.6719px; width: 70px; height: 21px;\">\n" +
                "        <span tabindex=\"0\" class=\"ctrl ctrlText\" style=\"color: rgb(96, 98, 102); font-size: 14px;\">窗前明月光</span>\n" +
                "    <i style=\"display:none;\" class=\"el-icon-close btnRemove\"></i></div><div class=\"ctrlItem\" data-type=\"1\" style=\"position: absolute; inset: 139px auto auto 75.6875px; width: 70px; height: 21px;\">\n" +
                "        <span tabindex=\"0\" class=\"ctrl ctrlText\" style=\"color: rgb(96, 98, 102); font-size: 14px;\">地下鞋两双</span>\n" +
                "    <i style=\"display:none;\" class=\"el-icon-close btnRemove\"></i></div><div class=\"ctrlItem\" data-type=\"1\" style=\"position: absolute; inset: 200px auto auto 75.6875px; width: 70px; height: 21px;\">\n" +
                "        <span tabindex=\"0\" class=\"ctrl ctrlText\" style=\"color: rgb(96, 98, 102); font-size: 14px;\">两个狗男女</span>\n" +
                "    <i style=\"display:none;\" class=\"el-icon-close btnRemove\"></i></div><div class=\"ctrlItem\" data-type=\"1\" style=\"position: absolute; inset: 264.969px auto auto 75.6875px; width: 70px; height: 21px;\">\n" +
                "        <span tabindex=\"0\" class=\"ctrl ctrlText\" style=\"color: rgb(96, 98, 102); font-size: 14px;\">其中就有你</span>\n" +
                "    <i style=\"display:none;\" class=\"el-icon-close btnRemove\"></i></div><div class=\"ctrlItem\" data-type=\"1\" style=\"position: absolute; inset: 71px auto auto 278.141px; width: 70px; height: 21px;\">\n" +
                "        <span tabindex=\"0\" class=\"ctrl ctrlText\" style=\"color: rgb(96, 98, 102); font-size: 14px;\">姑娘一十七</span>\n" +
                "    <i style=\"display:none;\" class=\"el-icon-close btnRemove\"></i></div><div class=\"ctrlItem\" data-type=\"1\" style=\"position: absolute; inset: 139px auto auto 278.141px; width: 70px; height: 21px;\">\n" +
                "        <span tabindex=\"0\" class=\"ctrl ctrlText\" style=\"color: rgb(96, 98, 102); font-size: 14px;\">漫步上楼梯</span>\n" +
                "    <i style=\"display:none;\" class=\"el-icon-close btnRemove\"></i></div><div class=\"ctrlItem\" data-type=\"1\" style=\"position: absolute; inset: 200px auto auto 278.141px; width: 70px; height: 21px;\">\n" +
                "        <span tabindex=\"0\" class=\"ctrl ctrlText\" style=\"color: rgb(96, 98, 102); font-size: 14px;\">风吹罗裙起</span>\n" +
                "    <i style=\"display:none;\" class=\"el-icon-close btnRemove\"></i></div><div class=\"ctrlItem\" data-type=\"1\" style=\"position: absolute; inset: 264.969px auto auto 282.797px; width: 65.3438px; height: 21px;\">\n" +
                "        <span tabindex=\"0\" class=\"ctrl ctrlText\" style=\"color: rgb(96, 98, 102); font-size: 14px;\">露出柳叶B</span>\n" +
                "    <i style=\"display:none;\" class=\"el-icon-close btnRemove\"></i></div><div class=\"ctrlItem\" data-type=\"1\" style=\"position: absolute; inset: 15px auto auto 192.664px; width: 65.3594px; height: 21px;\">\n" +
                "        <span tabindex=\"0\" class=\"ctrl ctrlText\" style=\"color: rgb(96, 98, 102); font-size: 14px;\">躺尸300首</span>\n" +
                "    <i style=\"display:none;\" class=\"el-icon-close btnRemove\"></i></div><div class=\"ctrlItem\" data-type=\"1\" style=\"position: absolute; inset: 341.969px auto auto 58.7969px; width: 289.344px; height: 21px;\">\n" +
                "        <span tabindex=\"0\" class=\"ctrl ctrlText\" style=\"color: rgb(96, 98, 102); font-size: 14px;\">来自天津和平区天津中心大厦B座柱子上的诗句</span>\n" +
                "    <i style=\"display:none;\" class=\"el-icon-close btnRemove\"></i></div><div class=\"ctrlItem active\" data-type=\"1\" style=\"position: absolute; inset: 406.969px auto auto 124.297px; width: 145.016px; height: 21px;\">\n" +
                "        <span tabindex=\"0\" class=\"ctrl ctrlText\" style=\"color: rgb(96, 98, 102); font-size: 14px;\">2018年5月10日 TianJin</span>\n" +
                "    <i style=\"display:none;\" class=\"el-icon-close btnRemove\"></i></div>";

        System.out.println(Html2Img.createJpeg(html));
    }




    private void extractZip(String archivePath, String folderPath) throws IOException {
        BufferedOutputStream wirter = null;
        BufferedInputStream reader = null;
        ZipFile zipFile = new ZipFile(archivePath);
        Enumeration entries = zipFile.entries();

        try {
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) entries.nextElement();
                String name = zipEntry.getName();

                Path path = Paths.get(folderPath, name);
                if (zipEntry.isDirectory()) {
                    path.toFile().mkdirs();
                } else {
                    reader = new BufferedInputStream(zipFile.getInputStream(zipEntry));
                    byte[] buffer = new byte[8192];
                    wirter = new BufferedOutputStream(new FileOutputStream(path.toString()));

                    int perReadcount;
                    while ((perReadcount = reader.read(buffer, 0, 8192)) != -1) {
                        wirter.write(buffer, 0, perReadcount);
                    }

                    wirter.flush();
                }
            }
        } finally {
            StreamUtil.closeQuietly(wirter);
            StreamUtil.closeQuietly(reader);
            StreamUtil.closeQuietly(zipFile);
        }

    }


    private void extractZip1(String archivePath, String folderPath) throws IOException {
        BufferedOutputStream wirter = null;
        BufferedInputStream reader = null;
        try (ZipArchiveInputStream is = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(archivePath), 8192))) {

            ZipArchiveEntry entry = null;

            while ((entry = is.getNextZipEntry()) != null) {
                String name = entry.getName();
                Path path = Paths.get(folderPath, name);
                entry.isUnixSymlink();
                if (entry.isDirectory()) {
                    path.toFile().mkdirs();

                } else {

                    OutputStream os = new BufferedOutputStream(new FileOutputStream(path.toString()), 8192);
                    IOUtils.copy(is, os);
                }
            }
        } finally {
            StreamUtil.closeQuietly(wirter);
            StreamUtil.closeQuietly(reader);
        }

    }
}
