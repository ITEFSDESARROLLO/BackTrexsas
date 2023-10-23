package com.itefs.trexsas.servicio;


import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itefs.trexsas.modelo.CuentaCobro;
import com.itefs.trexsas.modelo.Factura;
import com.itefs.trexsas.modelo.OrdenServicio;
import com.itefs.trexsas.modelo.Vehiculo;
import com.itefs.trexsas.repositorio.FuecRepositorio;
import com.itefs.trexsas.utilidades.GeneradorNumeroFuec;
import com.itefs.trexsas.utilidades.Pdf;
import com.itefs.trexsas.utilidades.PropertiesReader;
import com.itefs.trexsas.utilidades.QR;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import net.sf.jasperreports.engine.JRException;

@Service
public class ArchivoServicio {
	
	
	
	@Autowired
    private Pdf pdf;
    @Autowired
	private FuecServicio fuecServicio;
    @Autowired
	private OrdenServicioServicio ordenServicioServicio;
    @Autowired
	private FacturaServicio facturaServicio;
    @Autowired
	private CuentaCobroServicio cuentaCobroServicio;
    @Autowired
    private FuecRepositorio fuecRepo;
    
    
	
	
	private static final Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 26, Font.BOLDITALIC);
    private static final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
         
    private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static final Font blueFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);    
    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	
	
	
	
	
	
	
	
	
	private final String separador = System.getProperty("file.separator");
    private final String reportes ="trexsas"+separador+"reportes"+separador;
    //private final String pruebas = "trexsas"+separador+"prueba"+separador;
    private final String pruebas ="C:/Users/ITEFS-FRONT END/Documents/pruebaJar2/";
    
    //private final String pruebas =
    private final Path pathFuec = Paths.get(reportes+"fuec");
    private final Path pathCO = Paths.get(reportes+"ocasional");
    private final Path pathOS = Paths.get(reportes+"orden_servicio");
    private final Path pathFac = Paths.get(reportes+"factura");
    private final Path pathCC = Paths.get(reportes+"cuenta_cobro");
    private final Path pathPrueba = Paths.get(pruebas);
    
    
    //crea un archivo
	public void crear(MultipartFile file, Path path, String nombre) throws IOException {
		Files.copy(file.getInputStream(),path.resolve(nombre));
	}
	
	//consulta un archivo y lo retorna
	public Resource obtener(String filename, Path path) throws FileNotFoundException, IOException {
		
        Path file = path.resolve(filename);
        System.out.println("archivo : "+file);
        Resource resource = new UrlResource(file.toUri());
        return resource;
    }
	
	//elimina un archivo si existe
	public void eliminar(String filename, Path path) throws IOException{
        Files.deleteIfExists(path.resolve(filename));
    }
	
	//elimina una cuenta de cobro si existe
	public void eliminarCuentaCobro(String filename) throws IOException{
		System.out.println("salida : "+filename);
		String salidaPath = PropertiesReader.cuenta_cobro;
		Path salida = Paths.get(salidaPath);
        Files.deleteIfExists(salida.resolve(filename));
    }
	
	public File obtenerImagen(String filename, Path path) throws MalformedURLException {
		File file = new File(path.toString() +separador+ filename);
        return file;
    }
	
	
	
	//consulta en base de datos y crea el pdf de la orden de servicio asociada de manera asincrona
	@Async
	public void crearOrdenServicio(Long id,String nombre){
		try {
			List<Object[]> consulta=ordenServicioServicio.pdf(id);
			if(consulta.size()>0) {
				Object[] datos= consulta.get(0);
				String salida = PropertiesReader.orden_servicio;
				Path salidaPath = Paths.get(salida);
	        	this.eliminar(nombre,salidaPath);
		        String observaciones="";
		        String voucher="";
		        if(datos[10]!=null)
		        	observaciones=datos[10].toString();
		        if(datos[6]!=null)
		        	voucher=datos[6].toString();
		        pdf.crearOrdenServicio(
		        		datos[0].toString(), datos[1].toString(), datos[2].toString(), datos[3].toString(), datos[4].toString(), 
		        		datos[5].toString(), voucher, datos[7].toString(), datos[8].toString(), datos[9].toString(), 
		        		observaciones, datos[11].toString(),  nombre);
		        System.out.println("creacion exitosa del pdf");
		        
			}else {
				OrdenServicio os=ordenServicioServicio.obtenerPorId(id);
		        os.setOrdenServicio(null);
		        ordenServicioServicio.actualizar(os);
			}
	    	
		}catch (JRException ex) {
			ex.printStackTrace();
			OrdenServicio os=ordenServicioServicio.obtenerPorId(id);
	        os.setOrdenServicio(null);
	        ordenServicioServicio.actualizar(os);
        	System.out.println("no se creo el pdf jr" +ex.getMessage());
        } catch (FileNotFoundException ex) {
        	ex.printStackTrace();
			OrdenServicio os=ordenServicioServicio.obtenerPorId(id);
	        os.setOrdenServicio(null);
	        ordenServicioServicio.actualizar(os);
        	System.out.println("no se creo el pdf fn" +ex.getMessage());
        } catch(Exception ex) {
        	ex.printStackTrace();
			OrdenServicio os=ordenServicioServicio.obtenerPorId(id);
	        os.setOrdenServicio(null);
	        ordenServicioServicio.actualizar(os);
        	System.out.println("no se creo el pdf ex " +ex.getMessage());
		}
		
	}
	
	//consulta en base de datos y crea el pdf de la factura asociada de manera asincrona
	@Async
	public void crearFactura(Long id,String nombre){
		try {
			List<Object[]> consulta=facturaServicio.pdf(id);
			if(consulta.size()>0) {
				Object[] datos= consulta.get(0);
				String salida = PropertiesReader.factura;
				Path salidaPath = Paths.get(salida);
		        this.eliminar(nombre,salidaPath);
		        pdf.crearFactura(
		        		datos[0].toString(), datos[1].toString(), datos[2].toString(), datos[3].toString(), datos[4].toString(), 
		        		datos[5].toString(), datos[6].toString(), datos[6].toString(), datos[7].toString(), datos[8].toString(), 
		        		nombre);
		        System.out.println("creacion exitosa del pdf");
			}else {
				Factura fac=facturaServicio.obtenerPorId(id);
		        fac.setFactura(null);
		        facturaServicio.actualizar(fac);
			}
	    	
		}catch (JRException ex) {
			ex.printStackTrace();
			Factura fac=facturaServicio.obtenerPorId(id);
	        fac.setFactura(null);
	        facturaServicio.actualizar(fac);
        	System.out.println("no se creo el pdf jr" +ex.getMessage());
        } catch (FileNotFoundException ex) {
        	ex.printStackTrace();
			Factura fac=facturaServicio.obtenerPorId(id);
	        fac.setFactura(null);
	        facturaServicio.actualizar(fac);
        	System.out.println("no se creo el pdf fn" +ex.getMessage());
        } catch(Exception ex) {
        	ex.printStackTrace();
			Factura fac=facturaServicio.obtenerPorId(id);
	        fac.setFactura(null);
	        facturaServicio.actualizar(fac);
        	System.out.println("no se creo el pdf ex " +ex.getMessage());
		}
		
	}
	
	@Async
	public void crearCuentaCobro(Long id, String nombre, CuentaCobro cuenta){
		try {
			//List<Object[]> consulta=cuentaCobroServicio.pdf(id);
			System.out.println("fecha "+cuenta.getFechaCuentaCobro());
			System.out.println("fecha "+cuenta.getValorCuentaCobro());
			String salida = PropertiesReader.cuenta_cobro;
			Path salidaPath = Paths.get(salida);
			this.eliminar(nombre,salidaPath);
	        pdf.crearCuentaCobro( 
	        		cuenta.getFechaCuentaCobro().toString(),
	        		cuenta.getNumeroCuentaCobro().toString(),
	        		cuenta.getValorCuentaCobro().toString(),
	        		cuenta.getValorCuentaCobro().toString(), 
	        		cuenta.getNombreCuentaCobro().toString(),
	        		cuenta.getNitCuentaCobro().toString(), 
	        		cuenta.getConceptoCuentaCobro().toString(), 
	        		nombre);
	        System.out.println("creacion exitosa del pdf");
			/*if(consulta.size()>0) 
			{
				Object[] datos= consulta.get(0);
				for (Object object : datos) {
					System.out.println("datos "+object.toString());
				}
				String salida = PropertiesReader.cuenta_cobro;
				Path salidaPath = Paths.get(salida);
				this.eliminar(nombre,salidaPath);
		        pdf.crearCuentaCobro(
		        		datos[0].toString(), datos[1].toString(), datos[2].toString(),datos[2].toString(), datos[3].toString(), datos[4].toString(), 
		        		datos[5].toString(), nombre);
		        System.out.println("creacion exitosa del pdf");
		        
			}else {
				CuentaCobro cc=cuentaCobroServicio.obtenerPorId(id);
				cc.setCuentaCobro(null);
		        cuentaCobroServicio.actualizar(cc);
			}*/
	    	
		}catch (JRException ex) {
			ex.printStackTrace();
			CuentaCobro cc=cuentaCobroServicio.obtenerPorId(id);
			cc.setCuentaCobro(null);
	        cuentaCobroServicio.actualizar(cc);
        	System.out.println("no se creo el pdf jr" +ex.getMessage());
        } catch (FileNotFoundException ex) {
        	ex.printStackTrace();
        	CuentaCobro cc=cuentaCobroServicio.obtenerPorId(id);
			cc.setCuentaCobro(null);
	        cuentaCobroServicio.actualizar(cc);
        	System.out.println("no se creo el pdf fn" +ex.getMessage());
        } catch(Exception ex) {
        	ex.printStackTrace();
        	CuentaCobro cc=cuentaCobroServicio.obtenerPorId(id);
			cc.setCuentaCobro(null);
	        cuentaCobroServicio.actualizar(cc);
        	System.out.println("no se creo el pdf ex " +ex.getMessage());
		}
		
	}
	
	public String pruebaItext(Long id, Long cuenta) throws Exception
	{
		Document documento = new Document();
		List<Object[]> vehiculoFuec = fuecRepo.vehiculoFuec(id);
		
		List<Object[]> tarjetaOperacion = fuecRepo.numeroInternoVehiculoFuec(id);
		
		List<Object[]> conductoresFuec = fuecRepo.traerConductoresFuec(id);
		
		String[] contratoInfo = fuecRepo.contratoFuec(id);
		
		Integer ultimo =contratoInfo[0].split(",").length-1;
		//Long idContrato = contratoInfo[0].split(",").length == 13?Long.valueOf(contratoInfo[0].split(",")[12]):Long.valueOf(contratoInfo[0].split(",")[13]);
		System.out.println("tamaño "+contratoInfo[0]);
		System.out.println("contrato recibido : "+contratoInfo[0].split(",")[ultimo]);
		//System.out.println("tamaño1"+idContrato)
		Integer numeroContratoConsecutivo = Integer.valueOf(contratoInfo[0].split(",")[ultimo]);
		
		
		Integer totalFuecs = fuecRepo.contratosPorFuec(Long.valueOf(numeroContratoConsecutivo.toString()));
		
		System.out.println("total fuecs por contrato : "+totalFuecs);
		
		GeneradorNumeroFuec generador = new GeneradorNumeroFuec();
		
		String numeroFuec = generador.generarNumeroFuec(numeroContratoConsecutivo, totalFuecs);
		
		String nombrePersonaResponsable = fuecRepo.nombreResponsableFuec(cuenta);
		
		System.out.println("total conductores : "+conductoresFuec.size());
		
		
		///String[] str =  fuecRepo.contratoFuec(id);
		Object[] str = fuecRepo.contratoFuec(id);
		String strFuec = str[0].toString();
		String[] lststrFuec = strFuec.split(",");
		List<String> fuec = Arrays.asList(lststrFuec);
		System.out.println("tamaño fuec "+str[0]);
		
		
		BaseColor color = new BaseColor(new Color(0,29,105));
		BaseColor colorFuec = new BaseColor(new Color(213, 0, 0));
		
		
		Font estilosLabelsDescripcion = FontFactory.getFont(BaseFont.TIMES_ROMAN, 10, Font.BOLD);
		Font estilosLabelsFuec = FontFactory.getFont(BaseFont.TIMES_ROMAN, 12, Font.BOLD,colorFuec);
		Font estilosDescripcion = FontFactory.getFont(BaseFont.TIMES_ROMAN, 8, Font.NORMAL);
		Font pruebaEstilo = FontFactory.getFont(BaseFont.TIMES_ROMAN, 12, BaseFont.ASCENT);
		Font estiloParrafosPag2 = FontFactory.getFont(BaseFont.TIMES_ROMAN,11, Font.NORMAL);
		Font textoTablaEjemplo = FontFactory.getFont(BaseFont.TIMES_ROMAN,9, Font.NORMAL);
		Font estiloTitulosVigenciaContrato = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
		Font estilosFechas = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
		Font estiloTitulosVehiculo = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
		Font estiloTitulosConductor = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD,color);
		Font estiloFirmaRepresentante = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
		Font estiloTitulosResponsable = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD,color);
		Font estiloTitulosResponsable1 = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD,color);
		Font estilosContenidosTtablas = new Font(Font.FontFamily.HELVETICA,8,Font.NORMAL);
		Font estilosTitulosTabalas = new Font(Font.FontFamily.HELVETICA,10,Font.BOLD);
		Font estilosTitulosImagenContrato = new Font(Font.FontFamily.HELVETICA,9,Font.BOLD);
		Font estiloContenidoInfo = new Font(Font.FontFamily.HELVETICA,8,Font.BOLD);
		Font estiloContenidoLegal = new Font(Font.FontFamily.HELVETICA,4,Font.NORMAL);
		Font estiloMetadaFuec = new Font(Font.FontFamily.HELVETICA,5,Font.BOLD);
		
		Resource uriMinisterio = new ClassPathResource("logos/Logo mintransporte.png");
		Image ministerio = Image.getInstance(uriMinisterio.getURL());
		ministerio.scalePercent(100, 80);
		
		Resource uriLegal = new ClassPathResource("logos/firma don pablo fuec.png");
		System.out.println("encontrado legal: "+uriLegal.getURL());
		Image legalImagen = Image.getInstance(uriLegal.getURL());
		legalImagen.scalePercent(50, 40);
		
		Resource uriTrex = new ClassPathResource("logos/Logo trex.png");
		System.out.println("encontrado trex: "+uriTrex.getURL());
		Image trex = Image.getInstance(uriTrex.getURL());
		Resource uriTrexAgua = new ClassPathResource("logos/Logo trex.png");
		Image trexAgua = Image.getInstance(uriTrex.getURL());
		trex.scalePercent(90,80);
		trex.setAlignment(Element.ALIGN_RIGHT);
		

		
		
		PdfPTable tablaImagenes = new PdfPTable(2);
		PdfPCell celda1 = new PdfPCell(ministerio);
		celda1.setBorder(Rectangle.NO_BORDER);
		
		PdfPCell celda2 = new PdfPCell(trex);
		celda2.setBorder(Rectangle.NO_BORDER);
		celda2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tablaImagenes.addCell(celda1);
		tablaImagenes.addCell(celda2);
		
		tablaImagenes.setWidthPercentage(100);
		tablaImagenes.setSpacingBefore(60);
		tablaImagenes.setSpacingAfter(10);
		
		Paragraph labelNumeroFuec = new  Paragraph("No."+numeroFuec,estilosLabelsFuec);
		labelNumeroFuec.setAlignment(Element.ALIGN_CENTER);
		labelNumeroFuec.setSpacingBefore(2);
		labelNumeroFuec.setSpacingAfter(2);
		
		Paragraph labelrazonSocial = new  Paragraph("RAZÓN SOCIAL DE LA EMPRESA DE TRANSPORTE ESPECIAL:",estilosLabelsDescripcion);
		Paragraph nit = new Paragraph("900.973.521-5",estilosDescripcion);
		Paragraph labelnit = new Paragraph("NIT:"+nit,estilosLabelsDescripcion);
		Paragraph numeroContrato = new Paragraph(fuec.get(0),estilosDescripcion);
		Paragraph labelnumeroContrato = new Paragraph("CONTRATO No.: "+numeroContrato,estilosLabelsDescripcion);
		Paragraph contratante = new Paragraph(fuec.get(1),estilosDescripcion);
		Paragraph labelcontratante = new Paragraph("CONTRATANTE: "+contratante,estilosLabelsDescripcion);
		Paragraph nitCliente = new Paragraph(fuec.get(2),estilosDescripcion);
		Paragraph labelnitCliente = new Paragraph("NIT/CC: "+nitCliente,estilosLabelsDescripcion);
		Paragraph labelobjetoContrato = new Paragraph("OBJETO CONTRATO : ",estilosLabelsDescripcion);
		Paragraph labelorigenDestino = new Paragraph("ORIGEN-DESTINO : ",estilosLabelsDescripcion);
		String nombreResponsable = nombrePersonaResponsable;
		String descripcionMetada = "fuec generado el día "+java.time.LocalDate.now().toString()+" a las "+java.time.LocalTime.now().toString()+" por "+nombreResponsable;
		Paragraph labelMetadaFuec = new Paragraph(descripcionMetada+"\r\n"
				+"\r\n",estiloMetadaFuec);
		labelMetadaFuec.setAlignment(Element.ALIGN_LEFT);
		
		Paragraph razonSocial = new  Paragraph("TRANSPORTES EXCLUSIVOS TREX S.A.S",estilosDescripcion);
		Paragraph objetoContrato = new Paragraph(fuec.get(3),estilosDescripcion);
		Paragraph origenDestino = new Paragraph(fuec.get(8)+"-"+fuec.get(9),estilosDescripcion);
		
		//Font estiloTitulo = new Font(BaseFont.,15,Font.BOLD);
		
		Paragraph tituloDocumento= new Paragraph("FORMATO ÚNICO DE EXTRACTO DEL CONTRATO DEL SERVICIO PÚBLICO DE TRANSPORTE TERRESTRE AUTOMOTOR ESPECIAL",pruebaEstilo);
		Paragraph tituloDocumentoPag2= new Paragraph("INSTRUCTIVO PARA DETERMINACIÓN DEL NÚMERO CONSECUTIVO DEL FUEC",pruebaEstilo);
		
		tituloDocumento.setLeading(12);
		tituloDocumento.setAlignment(Element.ALIGN_CENTER);
		PdfPCell celdaTitulo = new PdfPCell(tituloDocumento);
		celdaTitulo.setSpaceCharRatio(3);
		
		tituloDocumentoPag2.setLeading(12);
		tituloDocumentoPag2.setAlignment(Element.ALIGN_CENTER);
		PdfPCell celdaTitulo2 = new PdfPCell(tituloDocumentoPag2);
		celdaTitulo2.setSpaceCharRatio(3);
		
		Paragraph Line1Pag2 = new Paragraph("EL Formato Único de Extracto de Contrato “FUEC” estará constituido por los siguientes números:",estiloParrafosPag2);
		Line1Pag2.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
		
		Paragraph ParrafoAPagina2 = new  Paragraph(" a. Los tres primeros dígitos de izquierda a derecha corresponderán al código de la Dirección Territorial que otorgó la"
				+ "habilitación o de aquella a la cual se hubiese trasladado la empresa de Servicio Público de Transporte Terrestre"
				+ "Automotor Especial.\r\n"
				+ "\r\n",estiloParrafosPag2);
		ParrafoAPagina2.setAlignment(Element.ALIGN_JUSTIFIED);
		
		PdfPTable tablaciudadesFuec = new PdfPTable(4);
		Paragraph tablaCiudades1 = new Paragraph("Antioquia-Choco",estiloTitulosResponsable);
		tablaCiudades1.setAlignment(Element.ALIGN_LEFT);
		Paragraph tablaCodigoCiudad1 = new Paragraph("305",estilosContenidosTtablas);
		tablaCodigoCiudad1.setAlignment(Element.ALIGN_CENTER);
		Paragraph tablaCiudades2 = new Paragraph("Huila-Caquetá",estiloTitulosResponsable);
		tablaCiudades2.setAlignment(Element.ALIGN_LEFT);
		Paragraph tablaCodigoCiudad2 = new Paragraph("441",estilosContenidosTtablas);
		tablaCodigoCiudad2.setAlignment(Element.ALIGN_CENTER);
		Paragraph fila2Ciudades1 = new Paragraph("Atlántico",estiloTitulosResponsable);
		fila2Ciudades1.setAlignment(Element.ALIGN_LEFT);
		Paragraph fila2CodigoCiudad1 = new Paragraph("208",estilosContenidosTtablas);
		fila2CodigoCiudad1.setAlignment(Element.ALIGN_CENTER);
		Paragraph fila2Ciudades2 = new Paragraph("Magdalena",estiloTitulosResponsable);
		fila2Ciudades2.setAlignment(Element.ALIGN_LEFT);
		Paragraph fila2CodigoCiudad2 = new Paragraph("247",estilosContenidosTtablas);
		fila2CodigoCiudad2.setAlignment(Element.ALIGN_CENTER);
		
		Paragraph fila3Ciudades1 = new Paragraph("Bolivar-San Andres y Providencia",estiloTitulosResponsable);
		fila3Ciudades1.setAlignment(Element.ALIGN_LEFT);
		Paragraph fila3CodigoCiudad1 = new Paragraph("213",estilosContenidosTtablas);
		fila3CodigoCiudad1.setAlignment(Element.ALIGN_CENTER);
		Paragraph fila3Ciudades2 = new Paragraph("Meta-Vaupés-Vichada",estiloTitulosResponsable);
		fila3Ciudades2.setAlignment(Element.ALIGN_LEFT);
		Paragraph fila3CodigoCiudad2 = new Paragraph("550",estilosContenidosTtablas);
		fila3CodigoCiudad2.setAlignment(Element.ALIGN_CENTER);
		
		Paragraph fila4Ciudades1 = new Paragraph("Boyacá-Casanare",estiloTitulosResponsable);
		fila4Ciudades1.setAlignment(Element.ALIGN_LEFT);
		Paragraph fila4CodigoCiudad1 = new Paragraph("415",estilosContenidosTtablas);
		fila4CodigoCiudad1.setAlignment(Element.ALIGN_CENTER);
		Paragraph fila4Ciudades2 = new Paragraph("Nariño-Putumayo",estiloTitulosResponsable);
		fila4Ciudades2.setAlignment(Element.ALIGN_LEFT);
		Paragraph fila4CodigoCiudad2 = new Paragraph("352",estilosContenidosTtablas);
		fila4CodigoCiudad2.setAlignment(Element.ALIGN_CENTER);
		
		Paragraph fila5Ciudades1 = new Paragraph("Caldas",estiloTitulosResponsable);
		fila5Ciudades1.setAlignment(Element.ALIGN_LEFT);
		Paragraph fila5CodigoCiudad1 = new Paragraph("317",estilosContenidosTtablas);
		fila5CodigoCiudad1.setAlignment(Element.ALIGN_CENTER);
		Paragraph fila5Ciudades2 = new Paragraph("N/Santander-Arauca",estiloTitulosResponsable);
		fila5Ciudades2.setAlignment(Element.ALIGN_LEFT);
		Paragraph fila5CodigoCiudad2 = new Paragraph("454",estilosContenidosTtablas);
		fila5CodigoCiudad2.setAlignment(Element.ALIGN_CENTER);
		
		Paragraph fila6Ciudades1 = new Paragraph("Cauca",estiloTitulosResponsable);
		fila6Ciudades1.setAlignment(Element.ALIGN_LEFT);
		Paragraph fila6CodigoCiudad1 = new Paragraph("319",estilosContenidosTtablas);
		fila6CodigoCiudad1.setAlignment(Element.ALIGN_CENTER);
		Paragraph fila6Ciudades2 = new Paragraph("Quindío",estiloTitulosResponsable);
		fila6Ciudades2.setAlignment(Element.ALIGN_LEFT);
		Paragraph fila6CodigoCiudad2 = new Paragraph("363",estilosContenidosTtablas);
		fila6CodigoCiudad2.setAlignment(Element.ALIGN_CENTER);
		
		Paragraph fila7Ciudades1 = new Paragraph("César",estiloTitulosResponsable);
		fila7Ciudades1.setAlignment(Element.ALIGN_LEFT);
		Paragraph fila7CodigoCiudad1 = new Paragraph("220",estilosContenidosTtablas);
		fila7CodigoCiudad1.setAlignment(Element.ALIGN_CENTER);
		Paragraph fila7Ciudades2 = new Paragraph("Risaralda",estiloTitulosResponsable);
		fila7Ciudades2.setAlignment(Element.ALIGN_LEFT);
		Paragraph fila7CodigoCiudad2 = new Paragraph("366",estilosContenidosTtablas);
		fila7CodigoCiudad2.setAlignment(Element.ALIGN_CENTER);
		
		Paragraph fila8Ciudades1 = new Paragraph("Córdoba-Sucre",estiloTitulosResponsable);
		fila8Ciudades1.setAlignment(Element.ALIGN_LEFT);
		Paragraph fila8CodigoCiudad1 = new Paragraph("223",estilosContenidosTtablas);
		fila8CodigoCiudad1.setAlignment(Element.ALIGN_CENTER);
		Paragraph fila8Ciudades2 = new Paragraph("Santander",estiloTitulosResponsable);
		fila8Ciudades2.setAlignment(Element.ALIGN_LEFT);
		Paragraph fila8CodigoCiudad2 = new Paragraph("468",estilosContenidosTtablas);
		fila8CodigoCiudad2.setAlignment(Element.ALIGN_CENTER);
		
		Paragraph fila9Ciudades1 = new Paragraph("Cundinamarca",estiloTitulosResponsable);
		fila9Ciudades1.setAlignment(Element.ALIGN_LEFT);
		Paragraph fila9CodigoCiudad1 = new Paragraph("425",estilosContenidosTtablas);
		fila9CodigoCiudad1.setAlignment(Element.ALIGN_CENTER);
		Paragraph fila9Ciudades2 = new Paragraph("Tolima",estiloTitulosResponsable);
		fila9Ciudades2.setAlignment(Element.ALIGN_LEFT);
		Paragraph fila9CodigoCiudad2 = new Paragraph("473",estilosContenidosTtablas);
		fila9CodigoCiudad2.setAlignment(Element.ALIGN_CENTER);
		
		Paragraph fila10Ciudades1 = new Paragraph("Guajira",estiloTitulosResponsable);
		fila10Ciudades1.setAlignment(Element.ALIGN_LEFT);
		Paragraph fila10CodigoCiudad1 = new Paragraph("241",estilosContenidosTtablas);
		fila10CodigoCiudad1.setAlignment(Element.ALIGN_CENTER);
		Paragraph fila10Ciudades2 = new Paragraph("Valle del Cauca",estiloTitulosResponsable);
		fila10Ciudades2.setAlignment(Element.ALIGN_LEFT);
		Paragraph fila10CodigoCiudad2 = new Paragraph("376",estilosContenidosTtablas);
		fila10CodigoCiudad2.setAlignment(Element.ALIGN_CENTER);
		
		PdfPCell columtable1 = new PdfPCell();
		columtable1.addElement(tablaCiudades1);
		PdfPCell columtable2 = new PdfPCell();
		columtable2.addElement(tablaCodigoCiudad1);
		PdfPCell columtable3 = new PdfPCell();
		columtable3.addElement(tablaCiudades2);
		PdfPCell columtable4 = new PdfPCell();
		columtable4.addElement(tablaCodigoCiudad2);	
		PdfPCell fila2column = new PdfPCell();
		fila2column.addElement(fila2Ciudades1);
		PdfPCell fila2column2 = new PdfPCell();
		fila2column2.addElement(fila2CodigoCiudad1);
		PdfPCell fila2column3 = new PdfPCell();
		fila2column3.addElement(fila2Ciudades2);
		PdfPCell fila2column4 = new PdfPCell();		
		fila2column4.addElement(fila2CodigoCiudad2);
		
		PdfPCell fila3column = new PdfPCell();
		fila3column.addElement(fila3Ciudades1);
		PdfPCell fila3column2 = new PdfPCell();
		fila3column2.addElement(fila3CodigoCiudad1);
		PdfPCell fila3column3 = new PdfPCell();
		fila3column3.addElement(fila3Ciudades2);
		PdfPCell fila3column4 = new PdfPCell();		
		fila3column4.addElement(fila3CodigoCiudad2);
		
		PdfPCell fila4column = new PdfPCell();
		fila4column.addElement(fila4Ciudades1);
		PdfPCell fila4column2 = new PdfPCell();
		fila4column2.addElement(fila4CodigoCiudad1);
		PdfPCell fila4column3 = new PdfPCell();
		fila4column3.addElement(fila4Ciudades2);
		PdfPCell fila4column4 = new PdfPCell();		
		fila4column4.addElement(fila4CodigoCiudad2);
		
		PdfPCell fila5column = new PdfPCell();
		fila5column.addElement(fila5Ciudades1);
		PdfPCell fila5column2 = new PdfPCell();
		fila5column2.addElement(fila5CodigoCiudad1);
		PdfPCell fila5column3 = new PdfPCell();
		fila5column3.addElement(fila5Ciudades2);
		PdfPCell fila5column4 = new PdfPCell();		
		fila5column4.addElement(fila5CodigoCiudad2);
		
		PdfPCell fila6column = new PdfPCell();
		fila6column.addElement(fila6Ciudades1);
		PdfPCell fila6column2 = new PdfPCell();
		fila6column2.addElement(fila6CodigoCiudad1);
		PdfPCell fila6column3 = new PdfPCell();
		fila6column3.addElement(fila6Ciudades2);
		PdfPCell fila6column4 = new PdfPCell();		
		fila6column4.addElement(fila6CodigoCiudad2);
		
		PdfPCell fila7column = new PdfPCell();
		fila7column.addElement(fila7Ciudades1);
		PdfPCell fila7column2 = new PdfPCell();
		fila7column2.addElement(fila7CodigoCiudad1);
		PdfPCell fila7column3 = new PdfPCell();
		fila7column3.addElement(fila7Ciudades2);
		PdfPCell fila7column4 = new PdfPCell();		
		fila7column4.addElement(fila7CodigoCiudad2);
		
		PdfPCell fila8column = new PdfPCell();
		fila8column.addElement(fila8Ciudades1);
		PdfPCell fila8column2 = new PdfPCell();
		fila8column2.addElement(fila8CodigoCiudad1);
		PdfPCell fila8column3 = new PdfPCell();
		fila8column3.addElement(fila8Ciudades2);
		PdfPCell fila8column4 = new PdfPCell();		
		fila8column4.addElement(fila8CodigoCiudad2);
		
		PdfPCell fila9column = new PdfPCell();
		fila9column.addElement(fila9Ciudades1);
		PdfPCell fila9column2 = new PdfPCell();
		fila9column2.addElement(fila9CodigoCiudad1);
		PdfPCell fila9column3 = new PdfPCell();
		fila9column3.addElement(fila9Ciudades2);
		PdfPCell fila9column4 = new PdfPCell();		
		fila9column4.addElement(fila9CodigoCiudad2);
		
		PdfPCell fila10column = new PdfPCell();
		fila10column.addElement(fila10Ciudades1);
		PdfPCell fila10column2 = new PdfPCell();
		fila10column2.addElement(fila10CodigoCiudad1);
		PdfPCell fila10column3 = new PdfPCell();
		fila10column3.addElement(fila10Ciudades2);
		PdfPCell fila10column4 = new PdfPCell();		
		fila10column4.addElement(fila10CodigoCiudad2);	
		tablaciudadesFuec.addCell(columtable1);
		tablaciudadesFuec.addCell(columtable2);
		tablaciudadesFuec.addCell(columtable3);
		tablaciudadesFuec.addCell(columtable4);
		tablaciudadesFuec.addCell(fila2column);
		tablaciudadesFuec.addCell(fila2column2);
		tablaciudadesFuec.addCell(fila2column3);
		tablaciudadesFuec.addCell(fila2column4);	
		tablaciudadesFuec.addCell(fila3column);
		tablaciudadesFuec.addCell(fila3column2);
		tablaciudadesFuec.addCell(fila3column3);
		tablaciudadesFuec.addCell(fila3column4);
		tablaciudadesFuec.addCell(fila4column);
		tablaciudadesFuec.addCell(fila4column2);
		tablaciudadesFuec.addCell(fila4column3);
		tablaciudadesFuec.addCell(fila4column4);	
		tablaciudadesFuec.addCell(fila5column);
		tablaciudadesFuec.addCell(fila5column2);
		tablaciudadesFuec.addCell(fila5column3);
		tablaciudadesFuec.addCell(fila5column4);	
		tablaciudadesFuec.addCell(fila6column);
		tablaciudadesFuec.addCell(fila6column2);
		tablaciudadesFuec.addCell(fila6column3);
		tablaciudadesFuec.addCell(fila6column4);	
		tablaciudadesFuec.addCell(fila7column);
		tablaciudadesFuec.addCell(fila7column2);
		tablaciudadesFuec.addCell(fila7column3);
		tablaciudadesFuec.addCell(fila7column4);	
		tablaciudadesFuec.addCell(fila8column);
		tablaciudadesFuec.addCell(fila8column2);
		tablaciudadesFuec.addCell(fila8column3);
		tablaciudadesFuec.addCell(fila8column4);
		tablaciudadesFuec.addCell(fila9column);
		tablaciudadesFuec.addCell(fila9column2);
		tablaciudadesFuec.addCell(fila9column3);
		tablaciudadesFuec.addCell(fila9column4);		
		tablaciudadesFuec.addCell(fila10column);
		tablaciudadesFuec.addCell(fila10column2);
		tablaciudadesFuec.addCell(fila10column3);
		tablaciudadesFuec.addCell(fila10column4);

		Paragraph ParrafoBPagina2 = new  Paragraph("b. Los cuatro dígitos siguientes señalarán el número de resolución mediante la cual se otorgó la habilitación de la"
				+ "Empresa. En caso que la resolución no tenga estos dígitos, los faltantes serán completados con ceros a la izquierda.\r\n"
				+ "\r\n",estiloParrafosPag2);
		ParrafoBPagina2.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
		
		Paragraph ParrafoCPagina2 = new  Paragraph("c. Los siguientes dígitos, corresponderán a los dos últimos del año en que la empresa fue habilitada.\r\n"
				+ "\r\n",estiloParrafosPag2);
		ParrafoCPagina2.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
		
		Paragraph ParrafoDPagina2 = new  Paragraph("d. A continuación, cuatro dígitos que corresponderán al año en que se expide el extracto de contrato.\r\n"
				+ "\r\n",estiloParrafosPag2);
		ParrafoDPagina2.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
		
		Paragraph ParrafoEPagina2 = new  Paragraph("e. Posteriormente, cuatro dígitos que identifican el número del contrato. La numeración debe ser consecutiva,\r\n"
				+ "establecida por cada empresa y continuará la numeración dada a los contratos de prestación del servicio celebrados\r\n"
				+ "con anterioridad a la expedición a la presente Resolución para el transporte de estudiantes, empleados, turistas,\r\n"
				+ "usuarios del servicio de salud y grupo específicos de usuarios. En el evento, que el número consecutivo del contrato\r\n"
				+ "de la empresa supere los cuatro dígitos, se tomarán los últimos cuatro dígitos del consecutivo.\r\n"
				+ "\r\n",estiloParrafosPag2);
		ParrafoEPagina2.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
		
		Paragraph ParrafoFPagina2 = new  Paragraph("f. Finalmente, los cuatro últimos dígitos corresponderán a los cuatro dígitos del numero consecutivo del extracto de\r\n"
				+ "contrato de la empresa que se expida para la ejecución de cada contrato. En el evento, que el número consecutivo\r\n"
				+ "del extracto de contrato de la empresa supere los cuatro dígitos, se tomarán los últimos cuatro dígitos del consecutivo."
				+ "\r\n",estiloParrafosPag2);
		ParrafoFPagina2.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
		
		Paragraph tituloEjemplo = new  Paragraph("Ejemplo\r\n",estiloTitulosVigenciaContrato);
		tituloEjemplo.setAlignment(Element.ALIGN_CENTER);
		
		Paragraph parrafoTituloEjemplo = new  Paragraph("Empresa habilitada por la Dirección Territorial Cundinamarca en el año 2012, con resolución de habilitación No. 0155, que "
				+ "expide el primer extracto del contrato en el año 2015, del contrato 255. El número del Formato Único de Extracto de Contrato “FUEC” será: 425015512201502550001.\r\n"
				+ "\r\n",estiloParrafosPag2);
		parrafoTituloEjemplo.setAlignment(Element.ALIGN_JUSTIFIED);
		
		PdfPTable tablaEjemplo = new PdfPTable(1);
		Paragraph textableFila1 = new Paragraph("TRANSPORTES EXCLUSIVOS TREX S.A.S Garantiza que el vehículo cumple con las políticas de la empresa.",textoTablaEjemplo);
		textableFila1.setAlignment(Element.ALIGN_LEFT);
		PdfPCell fila1TableEjemplo = new PdfPCell();
		fila1TableEjemplo.addElement(textableFila1);
		tablaEjemplo.addCell(fila1TableEjemplo);
		
		PdfPTable condicionesContratofila1a3 = new PdfPTable(2);
		Paragraph Fila1NombreContratante = new Paragraph("CONTRATANTE:"+contratante,textoTablaEjemplo);
		Fila1NombreContratante.setAlignment(Element.ALIGN_LEFT);
		Paragraph Fila1NitContratante = new Paragraph("CC./Nit:"+nitCliente,textoTablaEjemplo);
		Fila1NitContratante.setAlignment(Element.ALIGN_LEFT);
		
		Paragraph Fila2NombreRepLegal = new Paragraph("REPRESENTANTE LEGAL: PABLO JESUS MONTAÑA DUARTE",textoTablaEjemplo);
		Fila2NombreRepLegal.setAlignment(Element.ALIGN_LEFT);
		Paragraph Fila2CCRepLegal = new Paragraph("CC: 6776558",textoTablaEjemplo);
		Fila2CCRepLegal.setAlignment(Element.ALIGN_LEFT);
		
		Paragraph Fila3Direccion = new Paragraph("DIRECCIÓN: CALLE 17 # 68D - 38 MONTEVIDEO",textoTablaEjemplo);
		Fila3Direccion.setAlignment(Element.ALIGN_LEFT);
		Paragraph Fila3telefono = new Paragraph("TEL/CEL.: 4241186",textoTablaEjemplo);
		Fila3telefono.setAlignment(Element.ALIGN_LEFT);
		
		PdfPCell fila1columnContratante = new PdfPCell();
		fila1columnContratante.addElement(Fila1NombreContratante);
		PdfPCell fila1columnNitContratante = new PdfPCell();
		fila1columnNitContratante.addElement(Fila1NitContratante);
		
		PdfPCell fila2columnLegal = new PdfPCell();
		fila2columnLegal.addElement(Fila2NombreRepLegal);
		PdfPCell fila2columnCCLegal = new PdfPCell();
		fila2columnCCLegal.addElement(Fila2CCRepLegal);
		
		PdfPCell fila3columndireccion = new PdfPCell();
		fila3columndireccion.addElement(Fila3Direccion);
		PdfPCell fila3columnTel = new PdfPCell();
		fila3columnTel.addElement(Fila3telefono);
		
		condicionesContratofila1a3.addCell(fila1columnContratante);
		condicionesContratofila1a3.addCell(fila1columnNitContratante);
		condicionesContratofila1a3.addCell(fila2columnLegal);
		condicionesContratofila1a3.addCell(fila2columnCCLegal);
		condicionesContratofila1a3.addCell(fila3columndireccion);
		condicionesContratofila1a3.addCell(fila3columnTel);

		
		PdfPTable condicionesContratofila4a5 = new PdfPTable(1);
		Paragraph Fila4ObjetoContrato = new Paragraph("OBJETO DEL CONTRATO: "+objetoContrato,textoTablaEjemplo);
		Fila4ObjetoContrato.setAlignment(Element.ALIGN_LEFT);
		Paragraph Fila5OrigenDestino = new Paragraph("ORIGEN - DESTINO: "+origenDestino,textoTablaEjemplo);
		Fila5OrigenDestino.setAlignment(Element.ALIGN_LEFT);
		
		PdfPCell fila4columnObjetoContrato = new PdfPCell();
		fila4columnObjetoContrato.addElement(Fila4ObjetoContrato);
		PdfPCell fila5columnOrigenDestino = new PdfPCell();
		fila5columnOrigenDestino.addElement(Fila5OrigenDestino);
		
		condicionesContratofila4a5.addCell(fila4columnObjetoContrato);
		condicionesContratofila4a5.addCell(fila5columnOrigenDestino);
		
		PdfPTable condicionesContratofila6 = new PdfPTable(2);
		String fechaInicioFuec2 = fuec.get(10);
		Paragraph Fila6FechaInicial = new Paragraph("FECHA INICIAL:"+fechaInicioFuec2,textoTablaEjemplo);
		Fila6FechaInicial.setAlignment(Element.ALIGN_LEFT);
		String fechaFinFuec2 = fuec.get(11);
		Paragraph Fila6Vencimiento = new Paragraph("FECHA VENCIMIENTO:"+fechaFinFuec2,textoTablaEjemplo);
		Fila6Vencimiento.setAlignment(Element.ALIGN_LEFT);
		
		PdfPCell Fila6ColumnFechaInicial = new PdfPCell();
		Fila6ColumnFechaInicial.addElement(Fila6FechaInicial);
		PdfPCell fila6columnVencimiento = new PdfPCell();
		fila6columnVencimiento.addElement(Fila6Vencimiento);
		
		condicionesContratofila6.addCell(Fila6ColumnFechaInicial);
		condicionesContratofila6.addCell(fila6columnVencimiento);
		
		Resource uriLogoTrex = new ClassPathResource("logos/Logo trex1.png");
		Image Trex = Image.getInstance(uriLogoTrex.getURL());
		Trex.scalePercent(80, 60);
		
		PdfPTable tablaImagenesContrato = new PdfPTable(2);
		PdfPCell columna1 = new PdfPCell(Trex);
		columna1.setBorder(Rectangle.NO_BORDER);
		Paragraph textoPag3column2 = new Paragraph("TRANSPORTES EXCLUSIVOS TREX S.A.S\r\n"
				+ "NIT. 900.973.521-5 \r\n"
				+ "CONTRATO SERVICIO DE TRANSPORTE\r\n"
				+ "NÚMERO DE FUEC:"+numeroFuec
				+ "NÚMERO DE CONTRATO:"+numeroContrato,estilosTitulosImagenContrato);
		textoPag3column2.setAlignment(Element.ALIGN_CENTER);
		PdfPCell columna2 = new PdfPCell();
		columna2.addElement(textoPag3column2);
		columna2.setBorder(Rectangle.NO_BORDER);
		tablaImagenesContrato.addCell(columna1);
		tablaImagenesContrato.addCell(columna2);
		
		tablaImagenesContrato.setWidthPercentage(100);
		tablaImagenesContrato.setSpacingBefore(60);
		tablaImagenesContrato.setSpacingAfter(10);
		
		PdfPTable tablaFirmasContrato = new PdfPTable(2);
		PdfPCell celdafirmaLegal = new PdfPCell();
		legalImagen.setAlignment(Element.ALIGN_CENTER);
		celdafirmaLegal.addElement(legalImagen);
		celdafirmaLegal.setBorder(Rectangle.NO_BORDER);
		Paragraph infoRepresentanteLegal = new Paragraph("________________________________________\r\n"
				+ "PABLO JESUS MONTAÑA DUARTE\r\n"
				+ "C.C.: 6776558\r\n"
				+ "FIRMA CONTRATISTA",estiloFirmaRepresentante);
		Paragraph infoFirmaConductor = new Paragraph("________________________________________\r\n"
				+ contratante+"\r\n"
				+ "C.C.:"+nitCliente+"\r\n"
				+ "FIRMA CONTRATANTE",estiloFirmaRepresentante);
		PdfPCell celdafirmaContratante = new PdfPCell(infoFirmaConductor);
		celdafirmaContratante.setBorder(Rectangle.NO_BORDER);
		celdafirmaLegal.addElement(infoRepresentanteLegal);
		infoRepresentanteLegal.setAlignment(Element.ALIGN_CENTER);
		infoFirmaConductor.setAlignment(Element.ALIGN_BOTTOM);
		tablaFirmasContrato.addCell(celdafirmaLegal);
		tablaFirmasContrato.addCell(celdafirmaContratante);
		
		//Font titulo = FontFactory.getFont(F,13,Font.BOLD);
		Paragraph nombreTablaVehiculo = new Paragraph("CARACTERÍSTICAS DE VEHÍCULO",estilosTitulosTabalas);
		
		nombreTablaVehiculo.setAlignment(Element.ALIGN_CENTER);
		
		Paragraph nombreTablaVigenciaContrato = new Paragraph("VIGENCIA DEL CONTRATO",estilosTitulosTabalas);
		PdfPTable tablaVigenciaContrato = new PdfPTable(4);
		tablaVigenciaContrato.setSpacingBefore(5);
		
		
		
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-mm-dd");
		Phrase fechaInicial = new Phrase("FECHA INICIAL",estiloTitulosVigenciaContrato);
		PdfPCell cFechaInicial = new PdfPCell(fechaInicial);
		cFechaInicial.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaVigenciaContrato.addCell(cFechaInicial);
		String fechaInicioFuec = fuec.get(10);
		Date fecha1 = formatoFecha.parse(fechaInicioFuec);
		Calendar calendarioInicio = Calendar.getInstance();
		calendarioInicio.setTime(fecha1);
		
		String diaFechaInicio = String.valueOf(calendarioInicio.get(Calendar.DATE));
		Paragraph labelDiaInicio = new Paragraph("DÍA",estiloTitulosVigenciaContrato);
		labelDiaInicio.setAlignment(Element.ALIGN_CENTER);
		
		Paragraph diaInicio = new Paragraph(diaFechaInicio,estilosFechas);
		diaInicio.setAlignment(Element.ALIGN_CENTER);
		PdfPCell cdiaInicio = new PdfPCell();
		cdiaInicio.addElement(labelDiaInicio);
		cdiaInicio.addElement(diaInicio);
		tablaVigenciaContrato.addCell(cdiaInicio);
		
		String mesFechaInicio = fechaInicioFuec.substring(5, 7);
		System.out.println("mes seleccionado : "+fechaInicioFuec.substring(5, 7));
		Paragraph labelMesInicio = new Paragraph("MES",estiloTitulosVigenciaContrato);
		labelMesInicio.setAlignment(Element.ALIGN_CENTER);
		Paragraph mesInicio = new Paragraph(mesFechaInicio,estilosFechas);
		mesInicio.setAlignment(Element.ALIGN_CENTER);
		PdfPCell cMesInicio = new PdfPCell();
		cMesInicio.addElement(labelMesInicio);
		cMesInicio.addElement(mesInicio);
		tablaVigenciaContrato.addCell(cMesInicio);
		
		String añoFechaInicio = String.valueOf(calendarioInicio.get(Calendar.YEAR));
		Paragraph labelAñoInicio = new Paragraph("AÑO",estiloTitulosVigenciaContrato);
		labelAñoInicio.setAlignment(Element.ALIGN_CENTER);
		Paragraph añoInicio = new Paragraph(añoFechaInicio,estilosFechas);
		añoInicio.setAlignment(Element.ALIGN_CENTER);
		PdfPCell cAñoInicio = new PdfPCell();
		cAñoInicio.addElement(labelAñoInicio);
		cAñoInicio.addElement(añoInicio);
		tablaVigenciaContrato.addCell(cAñoInicio);
		
		
		
		Phrase fechaFinal = new Phrase("FECHA VENCIMIENTO",estiloTitulosVigenciaContrato);
		PdfPCell cFechaFinal = new PdfPCell(fechaFinal);
		cFechaFinal.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaVigenciaContrato.addCell(cFechaFinal);
		
		String fechaFinFuec = fuec.get(11);
		
		System.out.println("fecha fin : "+fechaFinFuec);
		Date fecha2 = formatoFecha.parse(fechaFinFuec);
		
		Calendar calendarioFin = Calendar.getInstance();
		calendarioFin.setTime(fecha2);
		
		
		String diaFechaFin = String.valueOf(calendarioFin.get(Calendar.DATE));
		Paragraph labelDiaFin = new Paragraph("DÍA",estiloTitulosVigenciaContrato);
		labelDiaFin.setAlignment(Element.ALIGN_CENTER);
		Paragraph diaFin = new Paragraph(diaFechaFin,estilosFechas);
		diaFin.setAlignment(Element.ALIGN_CENTER);
		PdfPCell cdiaFin = new PdfPCell();
		cdiaFin.addElement(labelDiaFin);
		cdiaFin.addElement(diaFin);
		tablaVigenciaContrato.addCell(cdiaFin);
		
		String mesFechaFin = fechaFinFuec.substring(5, 7);
		System.out.println("fecha fin : "+mesFechaFin);
		Paragraph labelMesFin = new Paragraph("MES",estiloTitulosVigenciaContrato);
		labelMesFin.setAlignment(Element.ALIGN_CENTER);
		Paragraph mesFin = new Paragraph(mesFechaFin,estilosFechas);
		mesFin.setAlignment(Element.ALIGN_CENTER);
		PdfPCell cMesFin = new PdfPCell();
		cMesFin.addElement(labelMesFin);
		cMesFin.addElement(mesFin);
		tablaVigenciaContrato.addCell(cMesFin);
		
		String añoFechaFin = String.valueOf(calendarioFin.get(Calendar.YEAR));
		Paragraph labelAñoFin = new Paragraph("AÑO",estiloTitulosVigenciaContrato);
		labelAñoFin.setAlignment(Element.ALIGN_CENTER);
		Paragraph añoFin = new Paragraph(añoFechaFin,estilosFechas);
		añoFin.setAlignment(Element.ALIGN_CENTER);
		PdfPCell cAñoFin = new PdfPCell();
		cAñoFin.addElement(labelAñoFin);
		cAñoFin.addElement(añoFin);
		tablaVigenciaContrato.addCell(cAñoFin);
		
		
		PdfPTable tablaNumeroTarjetaOperacion = new PdfPTable(2);
		
		
		Phrase nInterno = new Phrase("NÚMERO INTERNO",estiloTitulosVehiculo);
		PdfPCell cInterno = new PdfPCell(nInterno);
		cInterno.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaNumeroTarjetaOperacion.addCell(cInterno);
		
		
		Phrase nTarjetaOperacion = new Phrase("NÚMERO TARJETA DE OPERACIÓN",estiloTitulosVehiculo);
		PdfPCell cTarjetaOperacion = new PdfPCell(nTarjetaOperacion);
		cTarjetaOperacion.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaNumeroTarjetaOperacion.addCell(cTarjetaOperacion);
		
		for (Object[] tarjeta : tarjetaOperacion) 
		{
			Phrase frase = new Phrase(tarjeta[0].toString());
			PdfPCell celdaTabla = new PdfPCell(frase);
			celdaTabla.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaNumeroTarjetaOperacion.addCell(celdaTabla);
			Phrase frase1 = new Phrase(tarjeta[1].toString());
			PdfPCell celdaTabla1 = new PdfPCell(frase1);
			celdaTabla1.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaNumeroTarjetaOperacion.addCell(celdaTabla1);
		}
		
		PdfPTable tablaVehiculoFuec = new PdfPTable(4);
		
		Phrase placa = new Phrase("PLACA",estiloTitulosVehiculo);
		PdfPCell cplaca = new PdfPCell(placa);
		cplaca.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaVehiculoFuec.addCell(cplaca);
		
		Phrase modelo = new Phrase("MODELO",estiloTitulosVehiculo);
		PdfPCell cmodelo = new PdfPCell(modelo);
		cmodelo.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaVehiculoFuec.addCell(cmodelo);
		
		Phrase marca = new Phrase("MARCA",estiloTitulosVehiculo);
		PdfPCell cmarca = new PdfPCell(marca);
		cmarca.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaVehiculoFuec.addCell(cmarca);
		
		Phrase clase = new Phrase("CLASE",estiloTitulosVehiculo);
		PdfPCell cclase = new PdfPCell(clase);
		cclase.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaVehiculoFuec.addCell(cclase);
		
		for (Object[] vehiculo : vehiculoFuec)
		{
			Phrase frase = new Phrase(vehiculo[0].toString(),estilosContenidosTtablas);
			PdfPCell celdaPlaca = new PdfPCell(frase);
			celdaPlaca.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaVehiculoFuec.addCell(celdaPlaca);
			
			Phrase frase1 = new Phrase(vehiculo[1].toString(),estilosContenidosTtablas);
			PdfPCell celdaModelo = new PdfPCell(frase1);
			celdaModelo.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaVehiculoFuec.addCell(celdaModelo);
			
			Phrase frase2 = new Phrase(vehiculo[2].toString(),estilosContenidosTtablas);
			PdfPCell celdaMarca = new PdfPCell(frase2);
			celdaMarca.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaVehiculoFuec.addCell(celdaMarca);
			
			Phrase frase3 = new Phrase(vehiculo[3].toString(),estilosContenidosTtablas);
			PdfPCell celdaClase = new PdfPCell(frase3);
			celdaClase.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaVehiculoFuec.addCell(celdaClase);
		}
			
			Paragraph Parrafo1Contrato = new  Paragraph("Entre los suscritos a saber por una parte TRANSPORTES EXCLUSIVOS TREX S.A.S. con NIT: 900.973.521-5; quien para los efectos del presente contrato se llamará"
					+ "CONTRATISTA y como administrador del vehículo de placa"+placa+"; propiedad del señor(a) EDGAR ALEXANDER TORRES ROCHA, mayor de edad, vecino(a) de la ciudad de"
					+ "BOGOTA, e identificado(a) con Cedula de Ciudadanía Nº 79996857, por otra parte el (a) señor (a) o entidad: "+contratante+", Igualmente mayor de edad, vecino (a) de Bogotá,"
					+ "identificado con la cédula de ciudadanía o NIT:"+nitCliente+", quien en adelante y para los efectos del presente contrato se llamará CONTRATANTE, ambos hábiles para contratar y"
					+ "obligarse, han decidido celebrar el presente contrato de PRESTACIÓN DE SERVICIO DE TRANSPORTE, que se regirá por las siguientes CLAUSULAS. PRIMERO: OBJETO, EL"
					+ "CONTRATANTE contrata los servicios de transporte de acuerdo a su Plan de Movilización, con las siguientes condiciones:\r\n"
					+ "\r\n",textoTablaEjemplo);
			Parrafo1Contrato.setAlignment(Element.ALIGN_JUSTIFIED);
			
			Paragraph Parrafo2Contrato = new  Paragraph("EL CONTRATISTA SE OBLIGA a prestarle de manera eficaz, responsabilidad en el servicio, garantizando eficiencia y cumplimiento CLAUSULAS: SEGUNDO: OBLIGACIONES DEL"
					+ "CONTRATISTA: 2. SEGUROS: Tomará por cuenta propia, una póliza de seguros de responsabilidad civil contractual y extracontractual que les ampare contra los riesgos"
					+ "inherentes a la actividad transportadora así: 2.1. Póliza de responsabilidad civil contractual: muerte accidental, incapacidad total y permanente, incapacidad temporal, gastos"
					+ "médicos, amparo patrimonial, primeros auxilios, asistencia jurídica en proceso civil, accidentes personales 2.2. Póliza de responsabilidad civil extracontractual: daños a bienes de"
					+ "terceros, muerte o lesiones a una persona, muerte o lesiones a dos o más personas, amparo patrimonial, asistencia jurídica en proceso civil y penal, Con lo anterior le damos"
					+ "cumplimiento al Artículo 17 Del Decreto 174 de 2001, razón por la que somos obligados a tomar las pólizas 2.3. El Vehículo que el Contratista destine a la prestación de este"
					+ "servicio de transpone, debe cumplir con las condiciones Técnico Mecánicas, homologadas por el Ministerio de Transporte, y se relacionan, con identificación de Número de placa,"
					+ "marca, modelo y numero interno del vehículo, que se incorpora a este contrato 2.4. El contratante dentro de su operación interna cuenta con los supervisores de vía que vigilarán"
					+ "el cumplimiento del recorrido. Y de esta manera solucionar a la mayor brevedad posible cualquier inconveniente que se presente. CLAUSULAS: TERCERA: El vehículo que preste"
					+ "el contratista a utilizar y solicitado por el contratante es: Placa WOX800, Marca JAC, Modelo 2016, Numero Interno V048, Teléfono 3118307182, Póliza de Seguro"
					+ "Obligatorio para Accidentes de Tránsito (SOAT) No. 13943800002810, Vigente hasta el 15-01-2023, Expedida por la Compañía de Seguros SEGUROS DEL ESTADO S.A.,"
					+ "Pólizas de la Empresa de Responsabilidad Civil Contractual y Extracontractual Vigente hasta el 28-07-2022, Expedida por la Compañía de Seguros COMPANIA MUNDIAL DE"
					+ "SEGUR, la capacidad del vehículo es de 12 Pasajeros, según la Tarjeta de Operación No. 280170, Vigente hasta el 19-12-2023. Expedida por el Ministerio de Transporte,"
					+ "Contratista o en su defecto a las empresas con la que se haya realizado acuerdos, convenio de colaboración empresarial o contrato con empresas de transporte automotor por"
					+ "carretera, de acuerdo al decreto 171 y 174 de 2001. CLAUSULAS: CUARTA: EL CONTRATISTA, será el único responsable de la póliza extracontractual, revisión técnico mecánica y"
					+ "gases , por estar legalmente vinculados a la empresa de transporte de los daños con su vehículo que se de origen a terceros, bien sea por daños materiales o lesiones a personas,"
					+ "y desde ahora manifiesta que habrá rotura del principio de solidaridad con la Contratante, razón por la que, únicamente responderá el contratista, quien para tal evento estará"
					+ "respaldado con el amparo de las Pólizas de Responsabilidad Civil contractual y extracontractual. 4.1. solo en caso que el contratante debe responder es en el evento que por uso"
					+ "de los pasajeros que se transportan en el vehículo tal como: daños en cojinería, vidrios, ventanas, lámina y pintura o elementos que le sean entregados en operación para su"
					+ "comodidad en el servicio en condición de recibido de los mismos CLAUSULAS: QUINTA: EL CONTRATISTA, podrá celebrar Convenio de Colaboración Empresarial. Con el objeto de"
					+ "posibilitar una eficiente racionalización en el uso del equipo automotor y la mejor prestación del servicio; de igual manera el Contratista podrá celebrar contratos con empresas de"
					+ "transporte colectivo en el evento de alta demanda, es de anotar que en los casos aquí citados son regulados por los artículos 24 y 25 de Decreto 174 de 2001. CLAUSULAS:"
					+ "SEXTA: PRECIO. Las partes libremente acuerdan como precio del contrato de transporte que se suscribe es de Pesos moneda corriente nacional, CLAUSULAS: SÉPTIMA: FORMA"
					+ "DE PAGO: Los pagos se cancelaran de acuerdo a lo pactado entre ambas partes. CLAUSULAS: OCTAVA: OBLIGACIONES DEL CONTRATANTE: 8.1. Pagar el precio acordado del"
					+ "transporte 8.2. Acatar las condiciones de seguridad impuestas por el Contratista o Reglamentos oficiales 8.3. Supervisar el comportamiento y conservación del vehículo en las"
					+ "condiciones entregadas para el servicio 8.4. Tomar el vehículo en el día hora, y lugar convenido. CLAUSULAS: NOVENA: CAUSALES DE TERMINACIÓN: son causales de"
					+ "terminación 9.1. No cancelar el precio del transporte en el término acordado 9.2. No cumplir el contratista con las rutas y horarios señalados. 9.3. Incumplir cualquiera de las"
					+ "cláusulas aquí pactadas. CLAUSULAS: DECIMA: PENAL. las partes acuerdan que como cláusula penal fijan la suma del valor de Pago Del 10% del valor del contrato de transporte,"
					+ "que pagará la parte que incumpla el presente contrato a la parte cumplida. Para constancia de lo anterior se firma en Bogotá, a los: 13 días del Mes: Julio del año: 2022.",textoTablaEjemplo);
				Parrafo2Contrato.setAlignment(Element.ALIGN_JUSTIFIED);
				
		PdfPTable tablaConductores = new PdfPTable(5);
		
		int indice = 1;
		for (Object[] objects : conductoresFuec)
		{
			System.out.println("estado : "+objects[5].toString());
			if(Integer.valueOf(objects[5].toString()) == 1)
			{
				
				Paragraph lconductor = new Paragraph("CONDUCTOR "+indice,estiloTitulosConductor);
				lconductor.setAlignment(Element.ALIGN_CENTER);
				PdfPCell c1 = new PdfPCell();
				c1.addElement(lconductor);
				
				Paragraph labelNombre = new Paragraph("NOMBRES Y APELLIDOS",estiloTitulosConductor);
				labelNombre.setAlignment(Element.ALIGN_LEFT);
				Paragraph nombreConductor = new Paragraph(objects[0].toString()+" "+objects[1].toString(),estilosContenidosTtablas);
				nombreConductor.setAlignment(Element.ALIGN_CENTER);
				PdfPCell c2 = new PdfPCell();
				c2.addElement(labelNombre);
				c2.addElement(nombreConductor);
				
				
				Paragraph labelConductor = new Paragraph("No. Cédula",estiloTitulosConductor);
				labelConductor.setAlignment(Element.ALIGN_LEFT);
				Paragraph cedulaConductor = new Paragraph(objects[2].toString(),estilosContenidosTtablas);
				cedulaConductor.setAlignment(Element.ALIGN_CENTER);
				PdfPCell c3 = new PdfPCell();
				c3.addElement(labelConductor);
				c3.addElement(cedulaConductor);
				
				
				Paragraph labelLicencia = new Paragraph("No. LICENCIA CONDUCCIÓN",estiloTitulosConductor);
				labelLicencia.setAlignment(Element.ALIGN_LEFT);
				Paragraph licencia = new Paragraph(objects[3].toString(),estilosContenidosTtablas);
				licencia.setAlignment(Element.ALIGN_CENTER);
				PdfPCell c4 = new PdfPCell();
				c4.addElement(labelLicencia);
				c4.addElement(licencia);
				
				
				Paragraph labelVigencia = new Paragraph("VIGENCIA",estiloTitulosConductor);
				labelVigencia.setAlignment(Element.ALIGN_LEFT);
				Paragraph vigencia = new Paragraph(objects[4].toString(),estilosContenidosTtablas);
				vigencia.setAlignment(Element.ALIGN_CENTER);
				PdfPCell c5 = new PdfPCell();
				c5.addElement(labelVigencia);
				c5.addElement(vigencia);
				
				tablaConductores.addCell(c1);
				tablaConductores.addCell(c2);
				tablaConductores.addCell(c3);
				tablaConductores.addCell(c4);
				tablaConductores.addCell(c5);
				
				indice++;
			}
			
			
			
			
		}
		
		int sobrantes = 3-(indice-1);
		
		for (int iterator = 0; iterator < sobrantes; iterator++) 
		{
			Paragraph lconductor = new Paragraph("CONDUCTOR "+indice,estiloTitulosConductor);
			lconductor.setAlignment(Element.ALIGN_CENTER);
			PdfPCell c1 = new PdfPCell();
			c1.addElement(lconductor);
			
			Paragraph labelNombre = new Paragraph("NOMBRES Y APELLIDOS",estiloTitulosConductor);
			labelNombre.setAlignment(Element.ALIGN_LEFT);
			Paragraph nombreConductor = new Paragraph(" ");
			nombreConductor.setAlignment(Element.ALIGN_CENTER);
			PdfPCell c2 = new PdfPCell();
			c2.addElement(labelNombre);
			c2.addElement(nombreConductor);
			
			Paragraph labelConductor = new Paragraph("No. Cédula",estiloTitulosConductor);
			labelConductor.setAlignment(Element.ALIGN_LEFT);
			Paragraph cedulaConductor = new Paragraph(" ");
			cedulaConductor.setAlignment(Element.ALIGN_CENTER);
			PdfPCell c3 = new PdfPCell();
			c3.addElement(labelConductor);
			c3.addElement(cedulaConductor);
			
			
			Paragraph labelLicencia = new Paragraph("No. LICENCIA CONDUCCIÓN",estiloTitulosConductor);
			labelLicencia.setAlignment(Element.ALIGN_LEFT);
			Paragraph licencia = new Paragraph(" ");
			licencia.setAlignment(Element.ALIGN_CENTER);
			PdfPCell c4 = new PdfPCell();
			c4.addElement(labelLicencia);
			c4.addElement(licencia);
			
			
			Paragraph labelVigencia = new Paragraph("VIGENCIA",estiloTitulosConductor);
			labelVigencia.setAlignment(Element.ALIGN_LEFT);
			Paragraph vigencia = new Paragraph(" ");
			vigencia.setAlignment(Element.ALIGN_CENTER);
			PdfPCell c5 = new PdfPCell();
			c5.addElement(labelVigencia);
			c5.addElement(vigencia);
			
			tablaConductores.addCell(c1);
			tablaConductores.addCell(c2);
			tablaConductores.addCell(c3);
			tablaConductores.addCell(c4);
			tablaConductores.addCell(c5);
			
			indice++;
		}
		
		PdfPTable tablaResponsable = new PdfPTable(5);
		
		
		Phrase frase0 = new Phrase("RESPONSABLE DEL CONTRATANTE \n",estiloTitulosResponsable1);
		PdfPCell tituloresponsable = new PdfPCell(frase0);
		tituloresponsable.addElement(frase0);
		tituloresponsable.setHorizontalAlignment(Element.ALIGN_LEFT);
		tablaResponsable.addCell(tituloresponsable);
		
		
		
		Paragraph frase = new Paragraph("NOMBRES Y APELLIDOS",estiloTitulosResponsable);
		frase.setAlignment(Element.ALIGN_TOP);
		Paragraph frase1_0 = new Paragraph(fuec.get(4),estilosContenidosTtablas);
		frase1_0.setAlignment(Element.ALIGN_CENTER);
		PdfPCell tre = new PdfPCell();
		tre.addElement(frase);
		tre.addElement(frase1_0);
		tablaResponsable.addCell(tre);
		
		Paragraph frase1 = new Paragraph("No. Cédula",estiloTitulosResponsable);
		frase1.setAlignment(Element.ALIGN_TOP);
		Paragraph frase1_1 = new Paragraph(fuec.get(5),estilosContenidosTtablas);
		frase1_1.setAlignment(Element.ALIGN_CENTER);
		PdfPCell tre2 = new PdfPCell();
		tre2.addElement(frase1);
		tre2.addElement(frase1_1);
		tablaResponsable.addCell(tre2);
		
		
		Paragraph frase2 = new Paragraph("TELÉFONO",estiloTitulosResponsable);
		frase2.setAlignment(Element.ALIGN_TOP);
		Paragraph frase2_1 = new Paragraph(fuec.get(6),estilosContenidosTtablas);
		frase2_1.setAlignment(Element.ALIGN_CENTER);
		PdfPCell tre3 = new PdfPCell();
		tre3.addElement(frase2);
		tre3.addElement(frase2_1);
		tablaResponsable.addCell(tre3);
		
		Paragraph frase3 = new Paragraph("DIRECCIÓN",estiloTitulosResponsable);
		frase3.setAlignment(Element.ALIGN_TOP);
		Paragraph frase3_1 = new Paragraph(fuec.get(7),estilosContenidosTtablas);
		frase3_1.setAlignment(Element.ALIGN_CENTER);
		PdfPCell tre4 = new PdfPCell();
		tre4.addElement(frase3);
		tre4.addElement(frase3_1);
		tablaResponsable.addCell(tre4);
		
		
		
		PdfPTable tablaInfomacion = new PdfPTable(3);
		Paragraph labelInfo1 = new Paragraph("CALLE 17 #36D-38 MONTEVIDEO",estiloContenidoInfo);
		Paragraph labelInfo2 = new Paragraph("Teléfono: +57 424 11 68",estiloContenidoInfo);
		Paragraph labelInfo3 =new Paragraph("director@trexsas.com.co",estiloContenidoInfo);
		
		labelInfo1.setAlignment(Element.ALIGN_CENTER);
		labelInfo1.setSpacingBefore(15);
		labelInfo2.setAlignment(Element.ALIGN_CENTER);
		labelInfo3.setAlignment(Element.ALIGN_CENTER);
		PdfPCell info = new PdfPCell();
		info.addElement(labelInfo1);
		info.addElement(labelInfo2);
		info.addElement(labelInfo3);
		info.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		tablaInfomacion.addCell(info);
		
		Paragraph labelRepresentanteLegal = new Paragraph("Firma Digital Representante Legal "
				+ "Ley 23644 de 2012 Art 5: Efectos jurídicos de la "
				+ "firma electrónica. La firma electrónica tendrá la "
				+ "misma validez y efectos jurídicos que la firma.",estiloContenidoLegal);
		labelRepresentanteLegal.setAlignment(Element.ALIGN_JUSTIFIED);
		PdfPCell legal = new PdfPCell();
		legalImagen.setAlignment(Element.ALIGN_CENTER);
		legal.addElement(legalImagen);
		legal.addElement(labelRepresentanteLegal);
		legal.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaInfomacion.addCell(legal);
		
		String nombreFuec = java.time.LocalDate.now().toString()+"_"+java.time.LocalTime.now().toString()+"_"+id+"_"+cuenta+".pdf";
		String salidaQR = PropertiesReader.pathFuecs;
		System.out.println("---------------------------------------------------------------nombre de archivo -------------"+salidaQR+nombreFuec);
		QR codigo = new QR();
		String plantilla = salidaQR+nombreFuec;
		String salida = codigo.generateQR(plantilla, 120, 120);
		System.out.println("salida . "+salida);
		Resource uriQR = new ClassPathResource(salida);
		System.out.println("banderín : "+salida);
		Image qr = Image.getInstance(salida);
		qr.scalePercent(90, 90);
		qr.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph labelQR = new Paragraph();
		labelQR.setAlignment(Element.ALIGN_TOP);
		PdfPCell qR = new PdfPCell();
		qR.addElement(qr);
		qR.addElement(labelQR);
		tablaInfomacion.addCell(qR);
		
		PdfPTable tablaST = new PdfPTable(3);
		Resource uriST = new ClassPathResource("logos/LogoSuperTransporte.png");
		Image st = Image.getInstance(uriST.getURL());
		st.scalePercent(5,5);
		st.setAlignment(Element.ALIGN_LEFT);
		PdfPCell celdaST = new PdfPCell(st);
		celdaST.setHorizontalAlignment(Element.ALIGN_LEFT);
		celdaST.setBorder(Rectangle.NO_BORDER);
		
		Font estiloVigilancia = new Font(Font.FontFamily.HELVETICA,6,Font.BOLD);
		Paragraph vigiladoParagraph = new Paragraph("VIGILADOS POR LA SUPERINTENDENCIA DE PUERTOS Y TRANSPORTES - MINISTERIO DE TRANSPORTE",estiloVigilancia);
		vigiladoParagraph.setAlignment(Element.ALIGN_JUSTIFIED);
		PdfPCell celdaSTD = new PdfPCell(vigiladoParagraph);
		celdaSTD.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celdaSTD.setBorder(Rectangle.NO_BORDER);
		
		
		Resource uriVG = new ClassPathResource("logos/Logo vigia.png");
		Image vg = Image.getInstance(uriVG.getURL());
		vg.setRotationDegrees(90);
		vg.scalePercent(35,50);
		vg.setAlignment(Element.ALIGN_LEFT);
		PdfPCell celdaVG = new PdfPCell(vg);
		celdaVG.setHorizontalAlignment(Element.ALIGN_LEFT);
		celdaVG.setBorder(Rectangle.NO_BORDER);
		
		
		
		tablaST.addCell(celdaST);
		tablaST.addCell(celdaSTD);
		tablaST.addCell(celdaVG);
		tablaST.setSpacingBefore(5);
		
		float[] columnWidths = new float[]{4f, 50f, 28f};
		tablaST.setWidths(columnWidths);
		
		tablaST.setWidthPercentage(100);
		
		
		
		tablaVehiculoFuec.setWidthPercentage(100);
		tablaResponsable.setWidthPercentage(100);
		tablaNumeroTarjetaOperacion.setWidthPercentage(100);
		tablaVigenciaContrato.setWidthPercentage(100);
		tablaConductores.setWidthPercentage(100);
		tablaInfomacion.setWidthPercentage(100);
		tablaVehiculoFuec.setSpacingBefore(10);
		try {
			
			//Resource uriSalida = new ClassPathResource("dataFile.properties");
			//propiedades.load(new FileInputStream(new File(uriSalida.getURI())));
			
			//String pathFuec = propiedades.get("pathFuecs").toString();
			//Path pathPrueba = Paths.get(pathFuec);
			String salidaFuec = PropertiesReader.pathFuecs;
			Path pathPrueba = Paths.get(salidaFuec);
			
			System.out.println("---------------------------------------------------------------nombre de archivo -------------"+nombreFuec);
			Resource fuenteSalida = new ClassPathResource("trexsas/");
			String separador = System.getProperty("file.separator");
		    //private final String pathQr ="trexsas"+separador+"reportes"+separador;
		    String pathQr = nombreFuec.replace(":", "_");
		    
			PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(pathPrueba.resolve(pathQr).toString()));
			//FileOutputStream fos = new FileOutputStream(pathQr+nombreFuec);
			documento.open();
			PdfContentByte canvas = writer.getDirectContentUnder();
			Image logoMarcaAgua = Image.getInstance(uriTrexAgua.getURL());
			logoMarcaAgua.setRotationDegrees(45);
			logoMarcaAgua.setAbsolutePosition(180,200);
			logoMarcaAgua.scaleAbsolute(200,150);
			canvas.saveState();
			PdfGState state = new PdfGState();
			state.setFillOpacity(0.1f);
			canvas.setGState(state);
			canvas.addImage(logoMarcaAgua);
			canvas.restoreState();
			nombreTablaVigenciaContrato.setAlignment(Element.ALIGN_CENTER);
			documento.add(tablaImagenes);
			documento.add(tituloDocumento);
			documento.add(labelNumeroFuec);
			documento.add(labelrazonSocial);
			documento.add(razonSocial);
			documento.add(labelnit);
			documento.add(labelnumeroContrato);
			documento.add(labelcontratante);
			documento.add(labelnitCliente);
			documento.add(labelobjetoContrato);
			documento.add(objetoContrato);
			documento.add(labelorigenDestino);
			documento.add(origenDestino);
			documento.add(nombreTablaVigenciaContrato);
			documento.add(tablaVigenciaContrato);
			documento.add(nombreTablaVehiculo);
			documento.add(tablaVehiculoFuec);
			documento.add(tablaNumeroTarjetaOperacion);
			documento.add(tablaConductores);
			documento.add(tablaResponsable);
			documento.add(tablaInfomacion);
			documento.add(tablaST);
			documento.add(labelMetadaFuec);
			PdfGState opacidad = new PdfGState();
			opacidad.setFillOpacity(0.1f);
			canvas.setGState(state);
			canvas.addImage(logoMarcaAgua);
			documento.add(tituloDocumentoPag2);
			documento.add(Line1Pag2);
			documento.add(ParrafoAPagina2);
			documento.add(tablaciudadesFuec);
			documento.add(ParrafoBPagina2);
			documento.add(ParrafoCPagina2);
			documento.add(ParrafoDPagina2);
			documento.add(ParrafoEPagina2);
			documento.add(ParrafoFPagina2);
			documento.add(tituloEjemplo);
			documento.add(parrafoTituloEjemplo);
			documento.add(tablaEjemplo);
			documento.add(tablaImagenesContrato);
			documento.add(Parrafo1Contrato);
			documento.add(condicionesContratofila1a3);
			documento.add(condicionesContratofila4a5);
			documento.add(condicionesContratofila6);
			documento.add(Parrafo2Contrato);
			documento.add(tablaFirmasContrato);
			documento.close();
			return nombreFuec;
		       
		    
		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}	
}
