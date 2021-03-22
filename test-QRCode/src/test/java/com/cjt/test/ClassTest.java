package com.cjt.test;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * @Author: chenjintao
 * @Date: 2020-02-27 15:28
 */
@RunWith(JUnit4.class)
public class ClassTest {

    @Test
    public void test1() throws WriterException, IOException {
        String path = "./test1.png";


        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        Path path1 = FileSystems.getDefault().getPath(path);

        BitMatrix bitMatrix = qrCodeWriter.encode("123", BarcodeFormat.QR_CODE,350,350);

        MatrixToImageWriter.writeToPath(bitMatrix,"png",path1);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
