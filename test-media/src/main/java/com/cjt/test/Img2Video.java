package com.cjt.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Author: chenjintao
 * @Date: 2020/11/16 15:33
 */
@Slf4j
public class Img2Video {
    private final static int default_videoWidth = 544;
    private final static int default_videoHeight = 960;
    private final static int default_frameRate = 24;


    public static void createMp4(String mp4SavePath, String imgPath, int videoLen) throws IOException {
        //读取所有图片
        File file = new File(imgPath);
        File[] files = file.listFiles();
        createMp4(mp4SavePath, files, default_frameRate, videoLen, default_videoWidth, default_videoHeight);
    }

    public static void createMp4(String mp4SavePath, File[] imgFiles, int videoLen) throws IOException {
        createMp4(mp4SavePath, imgFiles, default_frameRate, videoLen, default_videoWidth, default_videoHeight);
    }

    public static void createMp4(String mp4SavePath, File[] imgFiles, int frameRate, int videoLen, int width, int height) throws IOException {
        FileUtils.forceMkdirParent(new File(mp4SavePath));
        if (imgFiles == null || imgFiles.length == 0) {
            log.error("生成视频所需的图片资源为空");
            throw new RuntimeException("生成视频所需的图片资源为空");
        }
        int imgSize = imgFiles.length;              //计算图片资源的数量

        //判断图片资源数量是否大于视频总桢数
        if (frameRate * videoLen < imgSize) {
            frameRate = imgSize % videoLen > 0 ? (imgSize / videoLen) + 1 : imgSize / videoLen;
        }

        int allFrameCount = frameRate * videoLen;        //计算视频总帧数
        int fpi = allFrameCount / imgSize;           //计算每个图片所可以制作的帧数
        int fpiRemainder = allFrameCount % imgSize;  //计算每个图片所可以制作的帧数的余数

        //视频宽高最好是按照常见的视频的宽高  16：9  或者 9：16
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(mp4SavePath, width, height);
        //设置视频编码层模式
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        //设置视频帧数
        recorder.setFrameRate(frameRate);
        //设置视频图像数据格式
        recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
        recorder.setFormat("mp4");
        try {
            recorder.start();
            Java2DFrameConverter converter = new Java2DFrameConverter();

            for (int i = 0; i < imgSize; i++) {
                BufferedImage image = ImageIO.read(imgFiles[i]);
                Frame frame = converter.getFrame(image);

                for (int j = 0; j < fpi; j++) {
                    recorder.record(frame);
                }

                //余数桢处理
                if (fpiRemainder != 0 && i % (imgSize / fpiRemainder) == 0) {
                    recorder.record(frame);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //最后一定要结束并释放资源
            recorder.close();
        }
    }

    private static void createMp4(String mp4SavePath, File[] imgFiles, int frameRate, int width, int height) throws IOException {
        FileUtils.forceMkdirParent(new File(mp4SavePath));
        if (imgFiles == null || imgFiles.length == 0) {
            log.error("生成视频所需的图片资源为空");
            throw new RuntimeException("生成视频所需的图片资源为空");
        }

        //视频宽高最好是按照常见的视频的宽高  16：9  或者 9：16
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(mp4SavePath, width, height);
        //设置视频编码层模式
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        //设置视频帧数
        recorder.setFrameRate(frameRate);
        //设置视频图像数据格式
        recorder.setPixelFormat(avutil.AV_PIX_FMT_YUVJ420P);
        recorder.setFormat("mp4");
        try {
            recorder.start();
            Java2DFrameConverter converter = new Java2DFrameConverter();

            for (File imgFile : imgFiles) {
                BufferedImage image = ImageIO.read(imgFile);
                Frame frame = converter.getFrame(image);
                recorder.record(frame);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //最后一定要结束并释放资源
            recorder.close();
        }
    }
}
