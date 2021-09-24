package org.jc1c.qr;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import org.jc1c.annotations.JHandler;
import org.jc1c.annotations.JHandlerControllers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@JHandlerControllers
public class AppHandlers {

    @JHandler(methodName = "create")
    public static String createQR(String data) throws WriterException, IOException {
        return createQR(data,
                AppSettings.QR_CODE_DEFAULT_FORMAT,
                AppSettings.QR_CODE_DEFAULT_WIDTH,
                AppSettings.QR_CODE_DEFAULT_HEIGHT);
    }

    @JHandler(methodName = "create")
    public static String createQR(String data, String format) throws WriterException, IOException {
        return createQR(data,
                format,
                AppSettings.QR_CODE_DEFAULT_WIDTH,
                AppSettings.QR_CODE_DEFAULT_HEIGHT);
    }

    @JHandler(methodName = "create")
    public static String createQR(String data, Long width) throws WriterException, IOException {
        return createQR(data,
                AppSettings.QR_CODE_DEFAULT_FORMAT,
                width,
                width);
    }

    @JHandler(methodName = "create")
    public static String createQR(String data, String format, Long width) throws WriterException, IOException {

        if (format.isBlank()) {
            format = AppSettings.QR_CODE_DEFAULT_FORMAT;
        }

        if (width == 0) {
            width = AppSettings.QR_CODE_DEFAULT_WIDTH;
        }

        return createQR(data, format, width, width);
    }


    public static String createQR(String data, String format, Long width, Long height) throws WriterException, IOException {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 0);

        BitMatrix matrix = new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, width.intValue(), height.intValue(), hints);
        MatrixToImageWriter.writeToStream(matrix, format, stream);

        return Base64.getEncoder().encodeToString(stream.toByteArray());
    }


    @JHandler(methodName = "read")
    public static String readQR(String data) throws IOException, ChecksumException, NotFoundException, FormatException {

        byte[] bytes = Base64.getDecoder().decode(data.getBytes());

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
        BufferedImageLuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(bufferedImage);
        HybridBinarizer hybridBinarizer = new HybridBinarizer(bufferedImageLuminanceSource);
        BinaryBitmap binaryBitmap = new BinaryBitmap(hybridBinarizer);

        Result result = new QRCodeReader().decode(binaryBitmap);
        String text = result.getText();

        return Objects.isNull(text) ? "" : text;
    }

}
