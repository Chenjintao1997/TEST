package com.cjt.test;

import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.browser.BrowserFetcher;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;
import com.ruiyun.jvppeteer.options.ScreenshotOptions;
import com.ruiyun.jvppeteer.options.Viewport;
import com.ruiyun.jvppeteer.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @Author: chenjintao
 * @Date: 2020/11/20 16:43
 */
@Slf4j
public class Html2Img {
    public static Browser browser;
    private final static int pageWidth = 360;
    private final static int pageHeight = 640;
    private final static Set<String> formatSet = new HashSet<>();

    static {
        init();
        formatSet.add("png");
        formatSet.add("jpeg");
    }

    private static void init() {
        //判断是否是mac系统，如果是mac系统使用电脑本地的浏览器，如果为其他系统使用自动下载的浏览器；
        boolean isMac = Helper.isMac();

        //启动参数配置
        LaunchOptions options;
        ArrayList<String> argList = new ArrayList<>();
        argList.add("--no-sandbox");
        argList.add("--disable-setuid-sandbox");
        argList.add("--disable-gpu");

        if (isMac) {
//            String browserPath = "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome";
            String browserPath = "/Users/chenjintao/IdeaProjects/Test/test-media/.local-browser/chrome-mac/Chromium.app/Contents/MacOS/Chromium";

            options = new LaunchOptionsBuilder().withExecutablePath(browserPath).withArgs(argList).withHeadless(false).build();
        } else {
            //自动下载，第一次下载后不会再下载
            try {
                BrowserFetcher.downloadIfNotExist(null);
            } catch (InterruptedException | ExecutionException | IOException e) {
                log.error("IVR视频图片浏览器下载失败", e);
            }
            options = new LaunchOptionsBuilder().withArgs(argList).withHeadless(true).build();
        }
        try {
            browser = Puppeteer.launch(options);
        } catch (IOException e) {
            log.error("IVR视频图片浏览器启动失败", e);
        }
    }

    public static String createPng(String htmlContent) throws ExecutionException, InterruptedException, IOException {
        return createImg(htmlContent, "png");
    }

    public static String createJpeg(String htmlContent) throws ExecutionException, InterruptedException, IOException {
        return createImg(htmlContent, "jpeg");
    }

    public static String createImg(String htmlContent, String format) throws ExecutionException, InterruptedException, IOException {
        if (!formatSet.contains(format)) {
            log.error("不支持的格式");
//            throw new GeneralException("不支持的格式：" + format);
        }
        if (browser == null || !browser.isConnected()) init();
        Page page = browser.newPage();
        //增加html基本标签元素
        StringBuilder sb = new StringBuilder(htmlContent);
        sb.insert(0, "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0\">\n" +
                "  </head>\n" +
                "  <body style=\"height:100vh;\">");
        sb.append("  </body>\n" +
                "</html>");

        page.setContent(sb.toString());
        String tmpName = 1 + "." + format;
        String tmpPath = String.format("./img/page/tmp/img/1/%s", tmpName);
        FileUtils.forceMkdirParent(new File(tmpPath));

        ScreenshotOptions screenshotOptions = new ScreenshotOptions();

        screenshotOptions.setType(format);
        screenshotOptions.setPath(tmpPath);
        screenshotOptions.setFullPage(true);
        screenshotOptions.setEncoding("UTF-8");
        if (format.equals("jpeg"))
            screenshotOptions.setQuality(100);

        //设置页面尺寸
        Viewport viewport = new Viewport();
        viewport.setWidth(pageWidth);
        viewport.setHeight(pageHeight);

        page.setViewport(viewport);
        page.waitForNavigation();
        page.screenshot(screenshotOptions);
        // 改依赖存在bug，close方法会执行一个有可能会必须等待30s的锁，所以目前使用close(false)规避，后期依赖的jar包版本更新可以切换回来---已升级，后续删除注释
        page.close();
//        page.close(true);
        //todo 因目前还没有保证强制关闭java进程也连带杀掉chrome进程的方式，防止重复创建chrome进程，这里先设置为每创建一个页面就关闭一次chrome,大概每次创建页面增加耗时1.5s
        browser.close();
        return tmpPath;
    }
}
