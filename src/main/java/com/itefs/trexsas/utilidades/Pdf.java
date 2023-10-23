package com.itefs.trexsas.utilidades;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class Pdf {
	
	 private final String separador = System.getProperty("file.separator");
	    private final QR qr=new QR();
	    private final String pathReportes="trexsas"+separador+"reportes"+separador;
	    private final String pathDiseñoCO=pathReportes+"disenos"+separador+"contrato_ocasional.jrxml";
	    private final String pathDiseñoF=pathReportes+"disenos"+separador+"fuec.jrxml";
	    private final String pathDiseñoOS=pathReportes+"disenos"+separador+"orden_servicio.jrxml";
	    private final String pathDiseñoFac=pathReportes+"disenos"+separador+"factura.jrxml";
	    private final String pathDiseñoCC=pathReportes+"disenos"+separador+"cuenta_cobro.jrxml";
	    private final String pathOS=pathReportes+"orden_servicio"+separador;
	    private final String pathF=pathReportes+"disenos"+separador+"contrato_ocasional.jrxml";
	    private final String pathCO=pathReportes+"ocasional"+separador;
	    private final String pathFac=pathReportes+"factura"+separador;
	    private final String pathCC=pathReportes+"cuenta_cobro"+separador;
	    
	    
	    
	    public void crearContratoOcasional(String numeroFuec, String numeroContrato, String placaVehiculo, String marcaVehiculo, String modeloVehiculo,
	        String numeroInternoVehiculo, String nombrePropietarioVehiculo, String ciudadPropietarioVehiculo, String cedulaPropietarioVehiculo, 
	        String telefonoPropietarioVehiculo, String numeroSoat, String vigenciaSoat, String aseguradoraSoat, String vigenciaPoliza, 
	        String aseguradoraPoliza, String numeroPasajerosVehiculo, String numeroTarjetaOperacion, String vigenciaTarjetaOperacion, String diaContrato,
	        String mesContrato, String añoContrato, String razonSocialCliente, String nitCliente, String objetoContrato, String origenDestino, 
	        String fechaInicial, String fechaFinal, String nombrePdf
	    ) throws FileNotFoundException,JRException{
	    	JRDataSource fuenteDatos= new JREmptyDataSource();
		    Map<String, Object> parametros;
	        
            parametros = new HashMap<String , Object>();
            parametros.put("numeroFuec", numeroFuec);
            parametros.put("numeroContrato", numeroContrato);
            parametros.put("placaVehiculo", placaVehiculo);
            parametros.put("marcaVehiculo", marcaVehiculo);
            parametros.put("modeloVehiculo", modeloVehiculo);
            parametros.put("numeroInternoVehiculo", numeroInternoVehiculo);
            parametros.put("nombrePropietarioVehiculo", nombrePropietarioVehiculo);
            parametros.put("ciudadPropietarioVehiculo", ciudadPropietarioVehiculo);
            parametros.put("cedulaPropietarioVehiculo", cedulaPropietarioVehiculo);
            parametros.put("telefonoPropietarioVehiculo", telefonoPropietarioVehiculo);
            parametros.put("numeroSoat", numeroSoat);
            parametros.put("vigenciaSoat", vigenciaSoat);
            parametros.put("aseguradoraSoat", aseguradoraSoat);
            parametros.put("vigenciaPoliza", vigenciaPoliza);
            parametros.put("aseguradoraPoliza", aseguradoraPoliza);
            parametros.put("numeroPasajerosVehiculo", numeroPasajerosVehiculo);
            parametros.put("numeroTarjetaOperacion", numeroTarjetaOperacion);
            parametros.put("vigenciaTarjetaOperacion", vigenciaTarjetaOperacion);
            parametros.put("diaContrato", diaContrato);
            parametros.put("mesContrato", mesContrato);
            parametros.put("añoContrato", añoContrato);
            parametros.put("razonSocialCliente", razonSocialCliente);
            parametros.put("nitCliente", nitCliente);
            parametros.put("objetoContrato", objetoContrato);
            parametros.put("origenDestino", origenDestino);
            parametros.put("fechaInicial", fechaInicial);
            parametros.put("fechaFinal", fechaFinal);
            
            JasperReport reporte=JasperCompileManager.compileReport(pathDiseñoCO);
            JasperPrint jprint=JasperFillManager.fillReport(reporte, parametros,fuenteDatos);
            JasperExportManager.exportReportToPdfFile(jprint,pathCO+nombrePdf);
            if(jprint.getPages().isEmpty()){
                System.out.println("no se encontro el jasper");
            }
            
            System.out.println("creacion exitosa del pdf");
	        
	        
	    }
	    
	    
	    public String crearFuec(String numeroFuec, String numeroContrato, String contratante, String nitContratante, String objetoContrato,
	        String origenDestino, String convenio, String diaInicial, String mesInicial, String añoInicial, String diaFinal,
	        String mesFinal, String añoFinal, String placa, String modelo, String marca, String clase, String numeroInterno, String tarjetaOperacion,
	        String nombreUno, String cedulaUno, String licenciaUno, String vigenciaUno, String nombreDos, String cedulaDos, String licenciaDos, 
	        String vigenciaDos, String nombreTres, String cedulaTres, String licenciaTres, String vigenciaTres, String nombreResponsable, 
	        String cedulaResponsable, String telefonoResponsable, String direccionResponsable,String nombrePdf
	    ) throws FileNotFoundException,JRException,Exception{
	    	
	        String txt=qr.toString(numeroFuec, numeroContrato, contratante, nitContratante, objetoContrato, 
	                origenDestino, convenio, diaInicial, mesInicial, añoInicial, diaFinal, mesFinal, añoFinal, 
	                placa, modelo, marca, clase, numeroInterno, tarjetaOperacion, nombreUno, cedulaUno, nombreDos, 
	                cedulaDos, nombreTres, cedulaTres, nombreResponsable, nombrePdf);
	        JRDataSource fuenteDatos= new JREmptyDataSource();
		    Map<String, Object> parametros;
		    System.out.println("mensaje datos:"+txt);
	            String nombreQR=qr.generateQR(txt, 4000, 4000);
	            parametros = new HashMap<String , Object>();
	            parametros.put("numeroFuec", numeroFuec);
	            parametros.put("numeroContrato", numeroContrato);
	            parametros.put("contratante", contratante);
	            parametros.put("nitContratante", nitContratante);
	            parametros.put("objetoContrato", objetoContrato);
	            parametros.put("origenDestino", origenDestino);
	            parametros.put("convenio", convenio);
	            parametros.put("diaInicial", diaInicial);
	            parametros.put("mesInicial", mesInicial);
	            parametros.put("añoInicial", añoInicial);
	            parametros.put("diaFinal", diaFinal);
	            parametros.put("mesFinal", mesFinal);
	            parametros.put("añoFinal", añoFinal);
	            parametros.put("placa", placa);
	            parametros.put("modelo", modelo);
	            parametros.put("marca", marca);
	            parametros.put("clase", clase);
	            parametros.put("numeroInterno", numeroInterno);
	            parametros.put("tarjetaOperacion", tarjetaOperacion);
	            parametros.put("nombreUno", nombreUno);
	            parametros.put("cedulaUno", cedulaUno);
	            parametros.put("licenciaUno", licenciaUno);
	            parametros.put("vigenciaUno", vigenciaUno);
	            parametros.put("nombreDos", nombreDos);
	            parametros.put("cedulaDos", cedulaDos);
	            parametros.put("licenciaDos", licenciaDos);
	            parametros.put("vigenciaDos", vigenciaDos);
	            parametros.put("nombreTres", nombreTres);
	            parametros.put("cedulaTres", cedulaTres);
	            parametros.put("licenciaTres", licenciaTres);
	            parametros.put("vigenciaTres", vigenciaTres);
	            parametros.put("nombreResponsable", nombreResponsable);
	            parametros.put("cedulaResponsable", cedulaResponsable);
	            parametros.put("telefonoResponsable", telefonoResponsable);
	            parametros.put("direccionResponsable", direccionResponsable);
	            parametros.put("pathQR", qr.getPathQr()+nombreQR);
	            File file= ResourceUtils.getFile(pathDiseñoF);
	            JasperReport reporte2=JasperCompileManager.compileReport(file.getAbsolutePath());
	            JasperPrint jprint2=JasperFillManager.fillReport(reporte2, parametros,fuenteDatos);
	            JasperExportManager.exportReportToPdfFile(jprint2,pathF+nombrePdf);
	            if(jprint2.getPages().isEmpty()){
	            	System.out.println(pathDiseñoF+"nada");
	            }
	            qr.eliminarQR(nombreQR);
	            
	            return nombrePdf;
	        
	    }
	    
	    public void crearOrdenServicio(String numeroOrden, String fecha, String fechaViaje, String nombreConductor, String razonSocialCliente, 
	            String distancia, String voucher, String clase, String placa, String origenDestino, String comentarios,
	            String nombrePasajero, String nombrePdf)throws FileNotFoundException,JRException,Exception{
	    	JRDataSource fuenteDatos= new JREmptyDataSource();
		    Map<String, Object> parametros;
	        
	            parametros = new HashMap<String , Object>();
	            parametros.put("numeroOrden", numeroOrden);
	            parametros.put("fecha", fecha);
	            parametros.put("fechaViaje", fechaViaje);
	            parametros.put("nombreConductor", nombreConductor);
	            parametros.put("razonSocialCliente", razonSocialCliente);
	            parametros.put("distancia", distancia);
	            parametros.put("voucher", voucher);
	            parametros.put("clase", clase);
	            parametros.put("placa", placa);
	            parametros.put("origenDestino", origenDestino);
	            parametros.put("comentarios", comentarios);
	            parametros.put("nombrePasajero", nombrePasajero);
	            JasperReport reporte=JasperCompileManager.compileReport(PropertiesReader.diseno+"orden_servicio.jrxml");
	            JasperPrint jprint=JasperFillManager.fillReport(reporte, parametros,fuenteDatos);
	            String salidaOS = PropertiesReader.orden_servicio;
	            JasperExportManager.exportReportToPdfFile(jprint,salidaOS+nombrePdf);
	            if(jprint.getPages().isEmpty()){
	            	System.out.println("no se encontro el jasper");
	            }
	    }
	    
	    public void crearFactura(String numeroFactura, String fechaFactura, String descripcion, String razonSocialCliente, 
	            String nit, String telefono, String total, String valorLetras, String direccion, String fechaVencimiento,
	            String nombrePdf)throws FileNotFoundException,JRException,Exception{
	    	JRDataSource fuenteDatos= new JREmptyDataSource();
		    Map<String, Object> parametros;
		    ConvertidorNumeroLetra cnal=new ConvertidorNumeroLetra();
	        
	            parametros = new HashMap<String , Object>();
	            parametros.put("numeroFactura", numeroFactura);
	            parametros.put("fechaFactura", fechaFactura);
	            parametros.put("descripcion", descripcion);
	            parametros.put("razonSocialCliente", razonSocialCliente);
	            parametros.put("nit", nit);
	            parametros.put("telefono", telefono);
	            parametros.put("total", total);
	            parametros.put("valorLetras", cnal.cantidadConLetra(valorLetras)+" Pesos m/cte");
	            parametros.put("direccion", direccion);
	            parametros.put("fechaVencimiento", fechaVencimiento);
	            JasperReport reporte=JasperCompileManager.compileReport(PropertiesReader.diseno+"factura.jrxml");
	            JasperPrint jprint=JasperFillManager.fillReport(reporte, parametros,fuenteDatos);
	            String salidaFactura = PropertiesReader.orden_servicio;
	            JasperExportManager.exportReportToPdfFile(jprint,salidaFactura+nombrePdf);
	            JasperExportManager.exportReportToPdfFile(jprint,pathFac+nombrePdf);
	            if(jprint.getPages().isEmpty()){
	            	System.out.println("no se encontro el jasper");
	            }
	        
	    }
	    
	    public void crearCuentaCobro(String fecha, String numeroCuenta, String valorLetras, String valor, 
	            String nombre, String nit, String concepto, String nombrePdf) throws FileNotFoundException,JRException,Exception{
	    	JRDataSource fuenteDatos= new JREmptyDataSource();
		    Map<String, Object> parametros;
		    ConvertidorNumeroLetra cnal=new ConvertidorNumeroLetra();
	        
	            parametros = new HashMap<String , Object>();
	            parametros.put("fecha", fecha);
	            parametros.put("numeroCuenta", numeroCuenta);
	            parametros.put("valor", valor);
	            parametros.put("nombre", nombre);
	            parametros.put("nit", nit);
	            parametros.put("concepto", concepto);
	            parametros.put("valorLetras", cnal.cantidadConLetra(valorLetras)+"Pesos m/cte");
	            JasperReport reporte=JasperCompileManager.compileReport(PropertiesReader.diseno+"cuenta_cobro.jrxml");
	            JasperPrint jprint=JasperFillManager.fillReport(reporte, parametros,fuenteDatos);
	            String salidaCC = PropertiesReader.cuenta_cobro;
	            JasperExportManager.exportReportToPdfFile(jprint,salidaCC+nombrePdf);
	            //JasperExportManager.exportReportToPdfFile(jprint,pathCC+nombrePdf);
	            if(jprint.getPages().isEmpty()){
	            	System.out.println("no se encontro el jasper");
	            }
	        
	    }

	    public String getPathCO() {
	        return pathDiseñoCO;
	    }

	    public String getPathF() {
	        return pathDiseñoF;
	    }

	    public String getPathOS() {
	        return pathDiseñoOS;
	    }


}
