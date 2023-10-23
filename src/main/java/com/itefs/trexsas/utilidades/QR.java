package com.itefs.trexsas.utilidades;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class QR 
{
    private final String separador = System.getProperty("file.separator");
    
    //private final String pathQr ="trexsas"+separador+"reportes"+separador;
    //private final String pathQr ="trexsas"+separador+"prueba"+separador;
    //private final String pathQr ="src"+separador+"main"+separador+"resources"+separador+"qr"+separador;
    //private final String pathQr ="C:/Users/ITEFS-FRONT END/Documents/qrJar/";
    //private final String pathQr ="C:/Users/ITEFS-BACKEND/Documents/archivos_trex/fuecs/qr/";
    private final String pathQr = PropertiesReader.pathQR;
    public String generateQR(String text, int h, int w) throws Exception {
    	Calendar c = Calendar.getInstance();
    	String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
        String nombre=spn+"_QR"+".png";
        String salidaQR = PropertiesReader.pathQR;
        File f = new File(salidaQR+nombre);
        GeneradorToken cifrador = new GeneradorToken();
        String fuecTraido = cifrador.generarTokenQR("identificadorFuec_"+text);
        //String linkwhat = "https://wa.me/3244106819";
        String text1 =  PropertiesReader.URL_FUEC_QR+"fuecs/fuecs/"+fuecTraido;//"181.143.139.108:4200";
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(text1, com.google.zxing.BarcodeFormat.QR_CODE, w, h);
        BufferedImage image = new BufferedImage(matrix.getWidth(), matrix.getHeight(), BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrix.getWidth(), matrix.getHeight());
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < matrix.getWidth(); i++) {
            for (int j = 0; j < matrix.getHeight(); j++) {
                if (matrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        ImageIO.write(image, "png", f);
        
        System.out.println("ruta de salida "+f.getPath());
        return f.getPath();
        //return f.getName();
    }
    
    /*public File generateQR2(String text, int h, int w) throws Exception {
    	Calendar c = Calendar.getInstance();
    	String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
        String nombre=spn+"_QR"+".png";
        File f = new File(pathQr+nombre);

        String text1 = "181.143.139.108:4200";
        QRCodeWriter writer = new QRCodeWriter();
        
        BitMatrix matrix = writer.encode(text1, com.google.zxing.BarcodeFormat.QR_CODE, w, h);

        BufferedImage image = new BufferedImage(matrix.getWidth(), matrix.getHeight(), BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrix.getWidth(), matrix.getHeight());
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < matrix.getWidth(); i++) {
            for (int j = 0; j < matrix.getHeight(); j++) {
                if (matrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        ImageIO.write(image, "png", f);
        System.out.println("ruta de salida "+f.getPath());
        return f;
    }*/
    
    public void eliminarQR(String nombre) {
        File f = new File(pathQr+nombre);
        if (f.exists()) {
            f.delete();
        }
    }
    
    public String toString(String numeroFuec, String numeroContrato, String contratante, String nitContratante, String objetoContrato,
        String origenDestino, String convenio, String diaInicial, String mesInicial, String añoInicial, String diaFinal,
        String mesFinal, String añoFinal, String placa, String modelo, String marca, String clase, String numeroInterno, String tarjetaOperacion,
        String nombreUno, String cedulaUno,String nombreDos, String cedulaDos, String nombreTres, String cedulaTres, 
        String nombreResponsable,String nombrePdf
    ){
        return "Codigo: "+numeroFuec+"\n"
                +"Razon social: TRANSPORTES EXCLUSIVOS TREX S.A.S.\n"
                +"Nit empresa: 900.973.521-5\n"
                +"Contrato numero: "+numeroContrato+"\n"
                +"Contratante: "+contratante+"\n"
                +"Nit contratante: "+nitContratante+"\n"
                +"Objeto contrato: "+objetoContrato+"\n"
                +"Origen - Destino: "+origenDestino+"\n"
                +"Convenio: "+convenio+"\n"
                +"Fecha inicial: "+diaInicial+" de "+mesInicial+" de "+añoInicial+"\n"
                +"Fecha vencimiento: "+diaFinal+" de "+mesFinal+" de "+añoFinal+"\n"
                +"Placa: "+placa+"\n"
                +"Modelo: "+modelo+"\n"
                +"Marca: "+marca+"\n"
                +"Clase: "+clase+"\n"
                +"Responsable: "+nombreResponsable+"\n"
                +"Numero interno: "+numeroInterno+"\n"
                +"Tarjeta de operacion: "+tarjetaOperacion+"\n"
                +"Conductor 1: "+nombreUno+"\n"
                +"CC 1: "+cedulaUno+"\n"
                +"Conductor 2: "+nombreDos+"\n"
                +"CC 2: "+cedulaDos+"\n"
                +"Conductor 3: "+nombreTres+"\n"
                +"CC 3: "+cedulaTres+"\n"
                +"Click aqui para validar el documento completo \n"
                +"http://181.143.139.108:8080/trexsas-desarrollo/archivo/fuec/"+nombrePdf;
    }

    public String getPathQr() {
        return pathQr;
    }
}
