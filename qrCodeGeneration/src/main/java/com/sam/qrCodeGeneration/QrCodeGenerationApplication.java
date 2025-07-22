package com.sam.qrCodeGeneration;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import net.glxn.qrgen.javase.QRCode;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@SpringBootApplication
@RestController
public class QrCodeGenerationApplication {

    private static final Font BARCODE_TEXT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 12);

     /* REFERENCE LINK:
        https://www.baeldung.com/java-generating-barcodes-qr-codes
     */

    public static void main(String[] args) {
        SpringApplication.run(QrCodeGenerationApplication.class, args);
    }

    /* {barcode} contains 12 digit.
         If BarcodeFactory.createEAN13 specifically expects 12 digits,
         it may be using a variation of the EAN-13 format where you provide the first 12 digits,
         and the library automatically calculates and appends the 13th check digit.
     */
    @GetMapping(value = "/barbecue/ean13/{barcode}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generateEAN13BarcodeImageUsingBarbecue(
            @PathVariable("barcode") String barcodeText) throws Exception {
        Barcode barcode = BarcodeFactory.createEAN13(barcodeText);
        barcode.setFont(BARCODE_TEXT_FONT);
        return ResponseEntity.ok(BarcodeImageHandler.getImage(barcode));
    }

    @GetMapping(value = "/barcode4j/ean13/{barcode}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generateEAN13BarcodeImageUsingBarCode4j(@PathVariable("barcode") String barcodeText) {
        EAN13Bean barcodeGenerator = new EAN13Bean();
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(160, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        barcodeGenerator.generateBarcode(canvas, barcodeText);
        return ResponseEntity.ok(canvas.getBufferedImage());
    }

    @GetMapping(value = "/ZXing/ean13/{barcode}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generateEAN13BarcodeImageUsingZXing(@PathVariable("barcode") String barcodeText) {
        EAN13Writer barcodeWriter = new EAN13Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.EAN_13, 100, 150);
        return ResponseEntity.ok(MatrixToImageWriter.toBufferedImage(bitMatrix));
    }

    /* Below ones are to generate QR Codes
        {barcodeText} can be a number/text
     */

    @GetMapping(value = "/ZXing/{barcodeText}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generateQRCodeImageUsingZXing(@PathVariable("barcodeText") String barcodeText) throws WriterException {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);
        return ResponseEntity.ok(MatrixToImageWriter.toBufferedImage(bitMatrix));
    }

    @GetMapping(value = "/QR/{barcodeText}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generateQRCodeImageUsingQRGen(@PathVariable("barcodeText") String barcodeText) throws Exception {
        ByteArrayOutputStream stream = QRCode.from(barcodeText).withSize(250, 250).stream();
        ByteArrayInputStream bis = new ByteArrayInputStream(stream.toByteArray());
        return ResponseEntity.ok(ImageIO.read(bis));
    }

    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }

}
