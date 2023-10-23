package com.itefs.trexsas.controlador;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itefs.trexsas.modelo.Fuec;
import com.itefs.trexsas.modelo.Persona;
import com.itefs.trexsas.servicio.ArchivoServicio;
import com.itefs.trexsas.servicio.FuecServicio;
import com.itefs.trexsas.servicio.PersonaServicio;
import com.itefs.trexsas.servicio.TokenServicio;
import com.itefs.trexsas.utilidades.Cifrador;
import com.itefs.trexsas.utilidades.PropertiesReader;

@RestController
@RequestMapping("/archivo")
public class ArchivoControlador {
	
	@Autowired
	private ArchivoServicio archivoServicio;
	
	@Autowired
	private FuecServicio fuecServicio;
	
	@Autowired
	private TokenServicio tokenServicio;
	@Autowired
	private PersonaServicio personaServicio;
	
	
	private final String separador = System.getProperty("file.separator");
    private final String afiliacion ="trexsas"+separador+"documentos"+separador;
    private final String reportes ="trexsas"+separador+"reportes"+separador;
    private final String pruebas ="C:/Users/ITEFS-FRONT END/Documents/pruebaJar/";
    private final Path pathVehiculo = Paths.get(afiliacion+"vehiculo");
    private final Path pathLicenciaConduccion = Paths.get(afiliacion+"licencia_conduccion");
    private final Path pathCedula = Paths.get(afiliacion+"cedula");
    private final Path pathConvenio = Paths.get(afiliacion+"convenio");
    private final Path pathTarjetaOperacion = Paths.get(afiliacion+"tarjeta_operacion");
    private final Path pathSoat = Paths.get(afiliacion+"soat");
    private final Path pathRevisionTecnicomecanica = Paths.get(afiliacion+"revision_tecnicomecanica");
    private final Path pathPolizaContractual = Paths.get(afiliacion+"poliza_contractual");
    private final Path pathPolizaTodoRiesgo = Paths.get(afiliacion+"poliza_todo_riesgo");
    private final Path pathPolizaExtracontractual = Paths.get(afiliacion+"poliza_extracontractual");
    private final Path pathRevisionPreventiva = Paths.get(afiliacion+"revision_preventiva");
    private final Path pathTarjetaPropiedad = Paths.get(afiliacion+"tarjeta_propiedad");
    private final Path pathFotoPersona = Paths.get(afiliacion+"foto_persona");
    private final Path pathPlanillaAportes = Paths.get(afiliacion+"planilla_aportes");
    private final Path pathExamenesMedicos = Paths.get(afiliacion+"examenes_medicos");
    private final Path pathLogoCliente = Paths.get(afiliacion+"logo_cliente");
    //private final Path pathFuec = Paths.get(reportes+"fuec");
    private final Path pathFuec = Paths.get(pruebas);
    private final Path pathCO = Paths.get(reportes+"ocasional");
    private final Path pathOS = Paths.get(reportes+"orden_servicio");
    private final Path pathFac = Paths.get(reportes+"factura");
    private final Path pathCC = Paths.get(reportes+"cuenta_cobro");
    private final Path pathPruebas = Paths.get("trexsas"+separador+"prueba"+separador);
    
    @PostMapping("/cifrar")
	public ResponseEntity<?> cifrar(@RequestParam("palabra") String palabra ){
		
		HashMap<String, String> response = new HashMap<String, String>();
		Cifrador cifrador=new Cifrador();
			response.put("mensaje", cifrador.cifrar(palabra));
            return ResponseEntity.badRequest().body(response);
        
	}
    
	
	
	@PostMapping("/vehiculoFrente")
	public ResponseEntity<?> crearArchivosVehiculoFotoFrente(@RequestParam("fotoFrenteAfiliacion") MultipartFile fotoFrenteAfiliacion){
		
		HashMap<String, String> response = new HashMap<String, String>();
		try{
			if(fotoFrenteAfiliacion.isEmpty())
			{
				response.put("mensaje", "falta uno o varios archivos");
	            return ResponseEntity.ok().body(response);
			}
			Calendar c = Calendar.getInstance();
			String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			String nfotoFrenteAfiliacion=spn+"_ff"+"."+FilenameUtils.getExtension(fotoFrenteAfiliacion.getOriginalFilename());
			String salidaVehiculo = PropertiesReader.vehiculo;
			Path salida = Paths.get(salidaVehiculo);
			archivoServicio.crear(fotoFrenteAfiliacion,salida,nfotoFrenteAfiliacion);
			response.put("fotoFrenteAfiliacion", nfotoFrenteAfiliacion);
            return ResponseEntity.ok().body(response);
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PutMapping("/vehiculoFrente/{nombre}")
	public ResponseEntity<?> editarArchivoParteFrenteVehiculo(@RequestParam("accessToken") String accessToken,@PathVariable String nombre,@RequestParam("fotoFrenteAfiliacion") MultipartFile file){
		System.out.println("creando archivo de parte trasera");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				if(file.isEmpty()) {
					response.put("mensaje", "archivo vacio");
		            return ResponseEntity.ok().body(response);
				}
				String salidaVehiculo = PropertiesReader.vehiculo;
				Path salida = Paths.get(salidaVehiculo);
				archivoServicio.eliminar(nombre, salida);
				archivoServicio.crear(file,salida,nombre);
				response.put("fotoFrenteAfiliacion", nombre);
	            return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			} 
        }catch (IOException ex){
        	ex.printStackTrace();
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	ex.printStackTrace();
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PostMapping("/vehiculoLateral")
	public ResponseEntity<?> crearArchivosVehiculoFotoLateral(@RequestParam("fotoLadoAfiliacion") MultipartFile fotoLadoAfiliacion){
		
		HashMap<String, String> response = new HashMap<String, String>();
		try{
			if(fotoLadoAfiliacion.isEmpty()){
				response.put("mensaje", "falta uno o varios archivos");
	            return ResponseEntity.ok().body(response);
			}
			Calendar c = Calendar.getInstance();
			String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			String nfotoLadoAfiliacion=spn+"_fl"+"."+FilenameUtils.getExtension(fotoLadoAfiliacion.getOriginalFilename());
			String salidaVehiculo = PropertiesReader.vehiculo;
			Path salida = Paths.get(salidaVehiculo);
			archivoServicio.crear(fotoLadoAfiliacion,salida,nfotoLadoAfiliacion);
			response.put("fotoLadoAfiliacion", nfotoLadoAfiliacion);
            return ResponseEntity.ok().body(response);
        }catch (IOException ex){
        	response.put("error", ex.toString());
        	
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	ex.printStackTrace();
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PutMapping("/vehiculoLateral/{nombre}")
	public ResponseEntity<?> editarArchivoParteLateralVehiculo(@RequestParam("accessToken") String accessToken,@PathVariable String nombre,@RequestParam("fotoLadoAfiliacion") MultipartFile file){
		System.out.println("creando archivo de parte trasera");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				if(file.isEmpty()) {
					response.put("mensaje", "archivo vacio");
		            return ResponseEntity.ok().body(response);
				}
				String salidaVehiculo = PropertiesReader.vehiculo;
				Path salida = Paths.get(salidaVehiculo);
				archivoServicio.eliminar(nombre, salida);
				archivoServicio.crear(file,salida,nombre);
				response.put("fotoLadoAfiliacion", nombre);
	            return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			} 
        }catch (IOException ex){
        	ex.printStackTrace();
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	ex.printStackTrace();
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PostMapping("/vehiculoTrasera")
	public ResponseEntity<?> crearArchivosVehiculoFotoTrasera(@RequestParam("fotoTraseraAfiliacion") MultipartFile fotoTraseraAfiliacion){
		
		HashMap<String, String> response = new HashMap<String, String>();
		try{
			if(fotoTraseraAfiliacion.isEmpty()) {
				response.put("mensaje", "falta uno o varios archivos");
	            return ResponseEntity.ok().body(response);
			}
			Calendar c = Calendar.getInstance();
			String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			String nfotoTraseraAfiliacion=spn+"_ft"+"."+FilenameUtils.getExtension(fotoTraseraAfiliacion.getOriginalFilename());
			String salidaVehiculo = PropertiesReader.vehiculo;
			Path salida = Paths.get(salidaVehiculo);
			archivoServicio.crear(fotoTraseraAfiliacion,salida,nfotoTraseraAfiliacion);
			response.put("fotoTraseraAfiliacion", nfotoTraseraAfiliacion);
            return ResponseEntity.ok().body(response);
        }catch (IOException ex){
        	ex.printStackTrace();
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	ex.printStackTrace();
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PutMapping("/vehiculoTrasera/{nombre}")
	public ResponseEntity<?> editarArchivoParteTraseraVehicula(@RequestParam("accessToken") String accessToken,@PathVariable String nombre,@RequestParam("fotoTraseraAfiliacion") MultipartFile file){
		System.out.println("creando archivo de parte trasera");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				if(file.isEmpty()) {
					response.put("mensaje", "archivo vacio");
		            return ResponseEntity.ok().body(response);
				}
				String salidaVehiculo = PropertiesReader.vehiculo;
				Path salida = Paths.get(salidaVehiculo);
				archivoServicio.eliminar(nombre, salida);
				archivoServicio.crear(file,salida,nombre);
				response.put("fotoTraseraAfiliacion", nombre);
	            return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			} 
        }catch (IOException ex){
        	ex.printStackTrace();
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	ex.printStackTrace();
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PostMapping("/licenciaconduccion")
	public ResponseEntity<?> crearArchivosLicenciaConduccion(@RequestParam("licenciaUnoAfiliacion") MultipartFile licenciaUnoAfiliacion){
		
		HashMap<String, String> response = new HashMap<String, String>();
		try{
			if(licenciaUnoAfiliacion.isEmpty()) {
				response.put("mensaje", "falta uno o varios archivos");
	            return ResponseEntity.ok().body(response);
			}
			Calendar c = Calendar.getInstance();
			String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			String nlicenciaUnoAfiliacion=spn+"_li1"+"."+FilenameUtils.getExtension(licenciaUnoAfiliacion.getOriginalFilename());
			String salidaPath = PropertiesReader.licencia_conduccion;
			Path salida = Paths.get(salidaPath);
			archivoServicio.crear(licenciaUnoAfiliacion,salida,nlicenciaUnoAfiliacion);
			response.put("licenciaUnoAfiliacion", nlicenciaUnoAfiliacion);
            return ResponseEntity.ok().body(response);
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PostMapping("/cedula")
	public ResponseEntity<?> crearArchivosCedula(@RequestParam("documentoUnoAfiliacion") MultipartFile documentoUnoAfiliacion){
		
		HashMap<String, String> response = new HashMap<String, String>();
		try{
			if(documentoUnoAfiliacion.isEmpty()) {
				response.put("mensaje", "falta uno o varios archivos");
	            return ResponseEntity.ok().body(response);
			}
			Calendar c = Calendar.getInstance();
			String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			String ndocumentoUnoAfiliacion=spn+"_doc1"+"."+FilenameUtils.getExtension(documentoUnoAfiliacion.getOriginalFilename());
			String salidaPath = PropertiesReader.cedula;
			Path salida = Paths.get(salidaPath);
			archivoServicio.crear(documentoUnoAfiliacion,salida,ndocumentoUnoAfiliacion);
			response.put("documentoUnoAfiliacion",ndocumentoUnoAfiliacion);
            return ResponseEntity.ok().body(response);
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PostMapping("/convenio")
	public ResponseEntity<?> crearArchivosConvenio(@RequestParam("convenioAfiliacion") MultipartFile convenioAfiliacion){
		
		HashMap<String, String> response = new HashMap<String, String>();
		try{
			if(convenioAfiliacion.isEmpty()) {
				response.put("mensaje", "falta el archivo");
	            return ResponseEntity.ok().body(response);
			}
			Calendar c = Calendar.getInstance();
			String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			String nconvenioAfiliacion=spn+"_cv"+"."+FilenameUtils.getExtension(convenioAfiliacion.getOriginalFilename());
			String salidaPath = PropertiesReader.convenio;
			Path salida = Paths.get(salidaPath);
			archivoServicio.crear(convenioAfiliacion,salida,nconvenioAfiliacion);
			response.put("convenioAfiliacion", nconvenioAfiliacion);
			
            return ResponseEntity.ok().body(response);
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PostMapping("/tarjetaoperacion")
	public ResponseEntity<?> crearArchivosTarjetaOperacion(@RequestParam("tarjetaOperacionUnoAfiliacion") MultipartFile tarjetaOperacionUnoAfiliacion)
	{
		
		HashMap<String, String> response = new HashMap<String, String>();
		try{
			if(tarjetaOperacionUnoAfiliacion.isEmpty()) {
				response.put("mensaje", "faltan archivos");
	            return ResponseEntity.ok().body(response);
			}
			Calendar c = Calendar.getInstance();
			String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			String ntarjetaOperacionUnoAfiliacion=spn+"_to1"+"."+FilenameUtils.getExtension(tarjetaOperacionUnoAfiliacion.getOriginalFilename());
			String salidaPath = PropertiesReader.tarjeta_operacion;
			Path salida = Paths.get(salidaPath);
			archivoServicio.crear(tarjetaOperacionUnoAfiliacion,salida,ntarjetaOperacionUnoAfiliacion);
			response.put("tarjetaOperacionUnoAfiliacion",ntarjetaOperacionUnoAfiliacion);
			
            return ResponseEntity.ok().body(response);
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PostMapping("/soat")
	public ResponseEntity<?> crearArchivosSoat(@RequestParam("soatAfiliacion") MultipartFile soatAfiliacion){
		
		HashMap<String, String> response = new HashMap<String, String>();
		try{
			if(soatAfiliacion.isEmpty()) {
				response.put("mensaje", "falta el archivo");
	            return ResponseEntity.ok().body(response);
			}
			Calendar c = Calendar.getInstance();
			String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			String nsoatAfiliacion=spn+"_so"+"."+FilenameUtils.getExtension(soatAfiliacion.getOriginalFilename());
			String salidaPath = PropertiesReader.soat;
			Path salida = Paths.get(salidaPath);
			archivoServicio.crear(soatAfiliacion,salida,nsoatAfiliacion);
			response.put("soatAfiliacion", nsoatAfiliacion);
			
            return ResponseEntity.ok().body(response);
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PostMapping("/revisiontecnicomecanica")
	public ResponseEntity<?> crearArchivosRevisiontecnicomecanica(@RequestParam("tecnicomecanicaAfiliacion") MultipartFile tecnicomecanicaAfiliacion){
		
		HashMap<String, String> response = new HashMap<String, String>();
		try{
			if(tecnicomecanicaAfiliacion.isEmpty()) {
				response.put("mensaje", "falta el archivo");
	            return ResponseEntity.ok().body(response);
			}
			Calendar c = Calendar.getInstance();
			String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			String ntecnicomecanicaAfiliacion=spn+"_tm"+"."+FilenameUtils.getExtension(tecnicomecanicaAfiliacion.getOriginalFilename());
			String salidaPath = PropertiesReader.revision_tecnicomecanica;
			Path salida = Paths.get(salidaPath);
			archivoServicio.crear(tecnicomecanicaAfiliacion,salida,ntecnicomecanicaAfiliacion);
			response.put("tecnicomecanicaAfiliacion", ntecnicomecanicaAfiliacion); 
			return ResponseEntity.ok().body(response);
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PostMapping("/polizacontractual")
	public ResponseEntity<?> crearArchivosPolizaContractual(@RequestParam("contractualAfiliacion") MultipartFile contractualAfiliacion){
		
		HashMap<String, String> response = new HashMap<String, String>();
		try{
			if(contractualAfiliacion.isEmpty()) {
				response.put("mensaje", "falta el archivo");
	            return ResponseEntity.ok().body(response);
			}
			Calendar c = Calendar.getInstance();
			String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			String ncontractualAfiliacion=spn+"_pc"+"."+FilenameUtils.getExtension(contractualAfiliacion.getOriginalFilename());
			String salidaPath = PropertiesReader.poliza_contractual;
			Path salida = Paths.get(salidaPath);
			archivoServicio.crear(contractualAfiliacion,salida,ncontractualAfiliacion);
			response.put("contractualAfiliacion", ncontractualAfiliacion);
			return ResponseEntity.ok().body(response);
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PostMapping("/polizaTodoRiesgo")
	public ResponseEntity<?> crearArchivosPolizaTodoRiesgo(@RequestParam("polizaTodoRiesgo") MultipartFile polizaTodoRiesgo){
		
		HashMap<String, String> response = new HashMap<String, String>();
		try{
			if(polizaTodoRiesgo.isEmpty()) {
				response.put("mensaje", "falta el archivo");
	            return ResponseEntity.ok().body(response);
			}
			Calendar c = Calendar.getInstance();
			String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			String nPolizaTodoRiesgo=spn+"_pc"+"."+FilenameUtils.getExtension(polizaTodoRiesgo.getOriginalFilename());
			String salidaPath = PropertiesReader.poliza_todoriesgo;
			Path salida = Paths.get(salidaPath);
			archivoServicio.crear(polizaTodoRiesgo,salida,nPolizaTodoRiesgo);
			response.put("archivoPolizaTodoRiesgo", nPolizaTodoRiesgo);
			return ResponseEntity.ok().body(response);
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PostMapping("/polizaextracontractual")
	public ResponseEntity<?> crearArchivosPolizaExtracontractual(@RequestParam("extracontractualAfiliacion") MultipartFile extracontractualAfiliacion){
		
		HashMap<String, String> response = new HashMap<String, String>();
		try{
			if(extracontractualAfiliacion.isEmpty()) {
				response.put("mensaje", "falta el archivo");
	            return ResponseEntity.ok().body(response);
			}
			Calendar c = Calendar.getInstance();
			String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			String nextracontractualAfiliacion=spn+"_pec"+"."+FilenameUtils.getExtension(extracontractualAfiliacion.getOriginalFilename());
			String salidaPath = PropertiesReader.poliza_extracontractual;
			Path salida = Paths.get(salidaPath);
			archivoServicio.crear(extracontractualAfiliacion,salida,nextracontractualAfiliacion);
			response.put("extracontractualAfiliacion", nextracontractualAfiliacion);
			return ResponseEntity.ok().body(response);
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PostMapping("/revisionpreventiva")
	public ResponseEntity<?> crearArchivosRevisionPreventiva(@RequestParam("preventivaAfiliacion") MultipartFile preventivaAfiliacion){
		
		HashMap<String, String> response = new HashMap<String, String>();
		try{
			if(preventivaAfiliacion.isEmpty()) {
				response.put("mensaje", "falta el archivo");
	            return ResponseEntity.ok().body(response);
			}
			Calendar c = Calendar.getInstance();
			String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			String npreventivaAfiliacion=spn+"_rp"+"."+FilenameUtils.getExtension(preventivaAfiliacion.getOriginalFilename());
			String salidaPath = PropertiesReader.revision_preventiva;
			Path salida = Paths.get(salidaPath);
			archivoServicio.crear(preventivaAfiliacion,salida,npreventivaAfiliacion);
			response.put("preventivaAfiliacion", npreventivaAfiliacion);
			return ResponseEntity.ok().body(response);
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PostMapping("/tarjetapropiedad")
	public ResponseEntity<?> crearArchivosTarjetaPropiedad(@RequestParam("tarjetaPropiedadUnoAfiliacion") MultipartFile tarjetaPropiedadUnoAfiliacion){
		
		HashMap<String, String> response = new HashMap<String, String>();
		try{
			if(tarjetaPropiedadUnoAfiliacion.isEmpty()) {
				response.put("mensaje", "faltan archivos");
	            return ResponseEntity.ok().body(response);
			}
			Calendar c = Calendar.getInstance();
			String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			String ntarjetaPropiedadUnoAfiliacion=spn+"_tp1"+"."+FilenameUtils.getExtension(tarjetaPropiedadUnoAfiliacion.getOriginalFilename());
			String salidaPath = PropertiesReader.tarjeta_propietadad;
			Path salida = Paths.get(salidaPath);
			archivoServicio.crear(tarjetaPropiedadUnoAfiliacion,salida,ntarjetaPropiedadUnoAfiliacion);
			response.put("tarjetaPropiedadUnoAfiliacion", ntarjetaPropiedadUnoAfiliacion);
            return ResponseEntity.ok().body(response);
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PostMapping("/foto")
	public ResponseEntity<?> crearArchivosFoto(@RequestParam("fotoPersona") MultipartFile fotoPersona){
		
		HashMap<String, String> response = new HashMap<String, String>();
		try{
			if(fotoPersona.isEmpty()) {
				response.put("mensaje", "falta el archivo");
	            return ResponseEntity.ok().body(response);
			}
			Calendar c = Calendar.getInstance();
			String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			String nfotoPersona=spn+"_f"+"."+FilenameUtils.getExtension(fotoPersona.getOriginalFilename());
			String salidaPath = PropertiesReader.foto_persona;
			Path salida = Paths.get(salidaPath);
			archivoServicio.crear(fotoPersona,salida,nfotoPersona);
			response.put("fotoPersona", nfotoPersona);
			
            return ResponseEntity.ok().body(response);
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PostMapping("/planillaaportes")
	public ResponseEntity<?> crearArchivosPlanillaAportes(@RequestParam("planillaAportes") MultipartFile planillaAportes){
		
		HashMap<String, String> response = new HashMap<String, String>();
		try{
			if(planillaAportes.isEmpty()) {
				response.put("mensaje", "falta el archivo");
	            return ResponseEntity.ok().body(response);
			}
			Calendar c = Calendar.getInstance();
			String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			String nPlanillaAportes=spn+"_pa"+"."+FilenameUtils.getExtension(planillaAportes.getOriginalFilename());
			String salidaPath = PropertiesReader.planilla_aportes;
			Path salida = Paths.get(salidaPath);
			archivoServicio.crear(planillaAportes,salida,nPlanillaAportes);
			response.put("planillaAportes", nPlanillaAportes);
			
            return ResponseEntity.ok().body(response);
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PostMapping("/examenesmedicos")
	public ResponseEntity<?> crearArchivosExamenesMedicos(@RequestParam("examenesMedicos") MultipartFile examenesMedicos){
		
		HashMap<String, String> response = new HashMap<String, String>();
		try{
			if(examenesMedicos.isEmpty()) {
				response.put("mensaje", "falta el archivo");
	            return ResponseEntity.ok().body(response);
			}
			Calendar c = Calendar.getInstance();
			String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			String nExamenesMedicos=spn+"_em"+"."+FilenameUtils.getExtension(examenesMedicos.getOriginalFilename());
			String salidaPath = PropertiesReader.examenes_medicos;
			Path salida = Paths.get(salidaPath);
			archivoServicio.crear(examenesMedicos,salida,nExamenesMedicos);
			response.put("examenesMedicos", nExamenesMedicos);
			
            return ResponseEntity.ok().body(response);
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PostMapping("/logocliente")
	public ResponseEntity<?> crearArchivosCliente(@RequestParam("logoCliente") MultipartFile logoCliente){
		
		HashMap<String, String> response = new HashMap<String, String>();
		try{
			if(logoCliente.isEmpty()) {
				response.put("mensaje", "falta el archivo");
	            return ResponseEntity.ok().body(response);
			}
			Calendar c = Calendar.getInstance();
			String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			String nLogoCliente=spn+"_lc"+"."+FilenameUtils.getExtension(logoCliente.getOriginalFilename());
			String salidaPath = PropertiesReader.logo_cliente;
			Path salida = Paths.get(salidaPath);
			archivoServicio.crear(logoCliente,salida,nLogoCliente);
			response.put("logoCliente", nLogoCliente);
			
            return ResponseEntity.ok().body(response);
        }catch (IOException ex){
        	ex.printStackTrace();
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	ex.printStackTrace();
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	//gets
	
	@GetMapping(value="/vehiculo/{nombre}")
	public ResponseEntity<?> obtenerArchivoVehiculo(@RequestParam("accessToken") String accessToken,@PathVariable String nombre){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				
				String salidaFuec = PropertiesReader.vehiculo;
				Path pathPrueba = Paths.get(salidaFuec);
				//Resource resource=archivoServicio.obtener(nombre,pathVehiculo);
				Resource resource=archivoServicio.obtener(nombre,pathPrueba);
				if(resource.exists())
				{
					
					String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
					return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
	            }else{
	                response.put("mensaje", 3);
	                return ResponseEntity.ok().body(response);
	            }
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@GetMapping(value="/licenciaconduccion/{nombre}")
	public ResponseEntity<?> obtenerArchivoLicenciaConduccion(@RequestParam("accessToken") String accessToken,@PathVariable String nombre){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				String salidaFuec = PropertiesReader.licencia_conduccion;
				Path pathPrueba = Paths.get(salidaFuec);
				//Resource resource=archivoServicio.obtener(nombre,pathLicenciaConduccion);
				Resource resource=archivoServicio.obtener(nombre,pathPrueba);
				if(resource.exists())
				{
					String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
					return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
	            }else{
	                response.put("mensaje", 3);
	                return ResponseEntity.ok().body(response);
	            }
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@GetMapping(value="/cedula/{nombre}")
	public ResponseEntity<?> obtenerArchivoCedula(@RequestParam("accessToken") String accessToken,@PathVariable String nombre){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				
				String salidaFuec = PropertiesReader.cedula;
				Path pathPrueba = Paths.get(salidaFuec);
				//Resource resource=archivoServicio.obtener(nombre,pathCedula);
				Resource resource=archivoServicio.obtener(nombre,pathPrueba);
				if(resource.exists())
				{
					String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
					return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
	            }else{
	                response.put("mensaje", 3);
	                return ResponseEntity.ok().body(response);
	            }
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	//@GetMapping(value="/cedulaUsuario/{nombre}",produces = {"application/pdf"})
	@GetMapping(value="/cedulaUsuario/{nombre}")
	public ResponseEntity<?> descargarCedulaUsuario(@RequestParam("accessToken") String accessToken,@PathVariable String nombre)
	{
		System.out.println("nombre recibido : "+nombre);
		System.out.println("path : "+this.pathCedula);
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				
				String salidaFuec = PropertiesReader.cedula;
				Path pathCedula = Paths.get(salidaFuec);
				//Resource resource=archivoServicio.obtener(nombre,pathCedula);
				Resource resource=archivoServicio.obtener(nombre,pathCedula);
				if(resource.exists())
				{
					String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
					return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
	            }else{
	                response.put("mensaje", 3);
	                return ResponseEntity.ok().body(response);
	            }
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}

	@GetMapping(value="/convenio/{nombre}")
	public ResponseEntity<?> obtenerArchivoConvenio(@RequestParam("accessToken") String accessToken,@PathVariable String nombre){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			
			if(tokenServicio.existeToken(accessToken)!=null) {
				String salidaFuec = PropertiesReader.convenio;
				Path pathPrueba = Paths.get(salidaFuec);
				//Resource resource=archivoServicio.obtener(nombre,pathConvenio);
				Resource resource=archivoServicio.obtener(nombre,pathPrueba);
				if(resource.exists())
				{
					String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
					return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
	            }else{
	                response.put("mensaje", 3);
	                return ResponseEntity.ok().body(response);
	            }
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}

	@GetMapping(value="/tarjetaoperacion/{nombre}")
	public ResponseEntity<?> obtenerArchivoTarjetaOperacion(@RequestParam("accessToken") String accessToken,@PathVariable String nombre){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				String salidaFuec = PropertiesReader.tarjeta_operacion;
				Path pathPrueba = Paths.get(salidaFuec);
				//Resource resource=archivoServicio.obtener(nombre,pathTarjetaOperacion);
				Resource resource=archivoServicio.obtener(nombre,pathPrueba);
				if(resource.exists())
				{
					String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
					return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
	            }else{
	                response.put("mensaje", 3);
	                return ResponseEntity.ok().body(response);
	            }
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}

	@GetMapping(value="/soat/{nombre}")
	public ResponseEntity<?> obtenerArchivoSoat(@RequestParam("accessToken") String accessToken,@PathVariable String nombre){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				String salidaFuec = PropertiesReader.soat;
				Path pathPrueba = Paths.get(salidaFuec);
				//Resource resource=archivoServicio.obtener(nombre,pathSoat);
				Resource resource=archivoServicio.obtener(nombre,pathPrueba);
				if(resource.exists())
				{
					String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
					return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
	            }else{
	                response.put("mensaje", 3);
	                return ResponseEntity.ok().body(response);
	            }
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}

	@GetMapping(value="/revisiontecnicomecanica/{nombre}")
	public ResponseEntity<?> obtenerArchivoRevisiontecnicomecanica(@RequestParam("accessToken") String accessToken,@PathVariable String nombre){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				String salidaFuec = PropertiesReader.revision_tecnicomecanica;
				Path pathPrueba = Paths.get(salidaFuec);
				//Resource resource=archivoServicio.obtener(nombre,pathRevisionTecnicomecanica);
				Resource resource=archivoServicio.obtener(nombre,pathPrueba);
				if(resource.exists())
				{
					String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
					return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
	            }else{
	                response.put("mensaje", 3);
	                return ResponseEntity.ok().body(response);
	            }
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@GetMapping(value="/polizacontractual/{nombre}")
	public ResponseEntity<?> obtenerArchivoPolizaContractual(@RequestParam("accessToken") String accessToken,@PathVariable String nombre){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {

				String salidaFuec = PropertiesReader.poliza_contractual;
				Path pathPrueba = Paths.get(salidaFuec);
				//Resource resource=archivoServicio.obtener(nombre,pathPolizaContractual);
				Resource resource=archivoServicio.obtener(nombre,pathPrueba);
				if(resource.exists())
				{
					String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
					return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
	            }else{
	                response.put("mensaje", 3);
	                return ResponseEntity.ok().body(response);
	            }
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@GetMapping(value="/polizaTodoRiesgo/{nombre}")
	public ResponseEntity<?> obtenerArchivoPolizaTodoRiesgo(@RequestParam("accessToken") String accessToken,@PathVariable String nombre){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {

				String salidaFuec = PropertiesReader.poliza_todoriesgo;
				Path pathPrueba = Paths.get(salidaFuec);
				//Resource resource=archivoServicio.obtener(nombre,pathPolizaTodoRiesgo);
				Resource resource=archivoServicio.obtener(nombre,pathPrueba);
				if(resource.exists())
				{
					String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
					return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
	            }else{
	                response.put("mensaje", 3);
	                return ResponseEntity.ok().body(response);
	            }
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@GetMapping(value="/polizaextracontractual/{nombre}")
	public ResponseEntity<?> obtenerArchivoPolizaExtracontractual(@RequestParam("accessToken") String accessToken,@PathVariable String nombre){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				String salidaFuec = PropertiesReader.poliza_extracontractual;
				Path pathPrueba = Paths.get(salidaFuec);
				//Resource resource=archivoServicio.obtener(nombre,pathPolizaExtracontractual);
				Resource resource=archivoServicio.obtener(nombre,pathPrueba);
				if(resource.exists())
				{
					String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
					return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
	            }else{
	                response.put("mensaje", 3);
	                return ResponseEntity.ok().body(response);
	            }
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@GetMapping(value="/revisionpreventiva/{nombre}")
	public ResponseEntity<?> obtenerArchivoRevisionPreventiva(@RequestParam("accessToken") String accessToken,@PathVariable String nombre){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				String salidaFuec = PropertiesReader.revision_preventiva;
				Path pathPrueba = Paths.get(salidaFuec);
				//Resource resource=archivoServicio.obtener(nombre,pathRevisionPreventiva);
				Resource resource=archivoServicio.obtener(nombre,pathPrueba);
				if(resource.exists())
				{
					String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
					return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
	            }else{
	                response.put("mensaje", 3);
	                return ResponseEntity.ok().body(response);
	            }
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@GetMapping(value="/tarjetapropiedad/{nombre}")
	public ResponseEntity<?> obtenerArchivoTarjetaPropiedad(@RequestParam("accessToken") String accessToken,@PathVariable String nombre){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				String salidaFuec = PropertiesReader.tarjeta_operacion;
				Path pathPrueba = Paths.get(salidaFuec);
				//Resource resource=archivoServicio.obtener(nombre,pathTarjetaPropiedad);
				Resource resource=archivoServicio.obtener(nombre,pathPrueba);
				if(resource.exists())
				{
					String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
					return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
	            }else{
	                response.put("mensaje", 3);
	                return ResponseEntity.ok().body(response);
	            }
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}

	@GetMapping(value="/planillaaportes/{nombre}")
	public ResponseEntity<?> obtenerArchivoPlanillaAportes(@RequestParam("accessToken") String accessToken,@PathVariable String nombre){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				String salidaFuec = PropertiesReader.planilla_aportes;
				Path pathPrueba = Paths.get(salidaFuec);
				//Resource resource=archivoServicio.obtener(nombre,pathPlanillaAportes);
				Resource resource=archivoServicio.obtener(nombre,pathPrueba);
				if(resource.exists())
				{
					String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
					return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
	            }else{
	                response.put("mensaje", 3);
	                return ResponseEntity.ok().body(response);
	            }
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@GetMapping(value="/examenesmedicos/{nombre}")
	public ResponseEntity<?> obtenerArchivoExamenesMedicos(@RequestParam("accessToken") String accessToken,@PathVariable String nombre){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				String salidaFuec = PropertiesReader.examenes_medicos;
				Path pathPrueba = Paths.get(salidaFuec);
				//Resource resource=archivoServicio.obtener(nombre,pathExamenesMedicos);
				Resource resource=archivoServicio.obtener(nombre,pathPrueba);
				if(resource.exists())
				{
					String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
					return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
	            }else{
	                response.put("mensaje", 3);
	                return ResponseEntity.ok().body(response);
	            }
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@GetMapping(value="/foto/{nombre}")
	public ResponseEntity<?> obtenerArchivoFoto(@RequestParam("accessToken") String accessToken,@PathVariable String nombre){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				String salidaFuec = PropertiesReader.foto_persona;
				Path pathPrueba = Paths.get(salidaFuec);
				//Resource resource=archivoServicio.obtener(nombre,pathFotoPersona);
				Resource resource=archivoServicio.obtener(nombre,pathPrueba);
				if(resource.exists())
				{
					String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
					return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
	            }else{
	                response.put("mensaje", 3);
	                return ResponseEntity.ok().body(response);
	            }
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@GetMapping(value="/logocliente/{nombre}")
	public ResponseEntity<?> obtenerLogoCliente(@RequestParam("accessToken") String accessToken,@PathVariable String nombre){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				String salidaFuec = PropertiesReader.logo_cliente;
				Path pathPrueba = Paths.get(salidaFuec);
				//Resource resource=archivoServicio.obtener(nombre,pathLogoCliente);
				Resource resource=archivoServicio.obtener(nombre,pathPrueba);
				if(resource.exists())
				{
					String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
					return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
	            }else{
	                response.put("mensaje", 3);
	                return ResponseEntity.ok().body(response);
	            }
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@GetMapping(value="/fuec/{nombre}")
	public ResponseEntity<?> obtenerFuec(@PathVariable String nombre){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			String salidaFuec = PropertiesReader.pathFuecs;
			Path pathPrueba = Paths.get(salidaFuec);
			//Resource resource=archivoServicio.obtener(nombre,pathFuec);
			Resource resource=archivoServicio.obtener(nombre,pathPrueba);
			if(resource.exists())
			{
				String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
				return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
            }else{
                response.put("mensaje", 3);
                return ResponseEntity.ok().body(response);
            }
			
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@GetMapping(value="/ordenservicio/{nombre}")
	public ResponseEntity<?> obtenerOrdenServicio(@PathVariable String nombre){
		
		System.out.println("nombre archivo recibido : "+nombre);
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			String salidaFuec = PropertiesReader.orden_servicio;
			Path pathPrueba = Paths.get(salidaFuec);
			//Resource resource=archivoServicio.obtener(nombre,pathOS);
			Resource resource=archivoServicio.obtener(nombre,pathPrueba);
			if(resource.exists())
			{
				String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
				return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
            }else{
                response.put("mensaje", 3);
                return ResponseEntity.ok().body(response);
            }
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@GetMapping(value="/ocasional/{nombre}")
	public ResponseEntity<?> obtenerContratoOcasional(@RequestParam("accessToken") String accessToken,@PathVariable String nombre){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				String salidaFuec = PropertiesReader.pathFuecs;
				Path pathPrueba = Paths.get(salidaFuec);
				//Resource resource=archivoServicio.obtener(nombre,pathCO);
				Resource resource=archivoServicio.obtener(nombre,pathPrueba);
				if(resource.exists())
				{
					String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
					return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
	            }else{
	                response.put("mensaje", 3);
	                return ResponseEntity.ok().body(response);
	            }
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@GetMapping(value="/factura/{nombre}")
	public ResponseEntity<?> obtenerFactura(@RequestParam("accessToken") String accessToken,@PathVariable String nombre){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				String salidaFuec = PropertiesReader.factura;
				Path pathPrueba = Paths.get(salidaFuec);
				//Resource resource=archivoServicio.obtener(nombre,pathFac);
				Resource resource=archivoServicio.obtener(nombre,pathPrueba);
				if(resource.exists())
				{
					String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
					return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
	            }else{
	                response.put("mensaje", 3);
	                return ResponseEntity.ok().body(response);
	            }
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@GetMapping(value="/cuentacobro/{nombre}")
	public ResponseEntity<?> obtenerCuentaCobro(@RequestParam("accessToken") String accessToken,@PathVariable String nombre){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				String salidaFuec = PropertiesReader.cuenta_cobro;
				Path pathPrueba = Paths.get(salidaFuec);
				//Resource resource=archivoServicio.obtener(nombre,pathCC);
				Resource resource=archivoServicio.obtener(nombre,pathPrueba);
				if(resource.exists())
				{
					String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
					return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
	            }else{
	                response.put("mensaje", 3);
	                return ResponseEntity.ok().body(response);
	            }
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}

	
	//puts
	
	@PutMapping("/vehiculo/{nombre}")
	public ResponseEntity<?> editarArchivoVehiculo(@RequestParam("accessToken") String accessToken,@PathVariable String nombre,@RequestParam("archivoVehiculo") MultipartFile file){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				if(file.isEmpty()) {
					response.put("mensaje", "archivo vacio");
		            return ResponseEntity.ok().body(response);
				}
				String salidaVehiculo = PropertiesReader.vehiculo;
				Path salida = Paths.get(salidaVehiculo);
				archivoServicio.eliminar(nombre, salida);
				archivoServicio.crear(file,salida,nombre);
				response.put("archivoVehiculo", nombre);
	            return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PutMapping("/licenciaconduccion/{nombre}")
	public ResponseEntity<?> editarArchivoLicenciaConduccion(@RequestParam("accessToken") String accessToken,@PathVariable String nombre,@RequestParam("archivoLicenciaConduccion") MultipartFile file){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				if(file.isEmpty()) {
					response.put("mensaje", "archivo vacio");
		            return ResponseEntity.ok().body(response);
				}
				String salidaPath = PropertiesReader.vehiculo;
				Path salida = Paths.get(salidaPath);
				archivoServicio.eliminar(nombre, salida);
				Calendar c = Calendar.getInstance();
				String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
				System.out.println("extensión original : "+FilenameUtils.getExtension(file.getOriginalFilename()));
				String nombreNuevo = spn+"_doc1"+"."+FilenameUtils.getExtension(file.getOriginalFilename());
				
				archivoServicio.crear(file,salida,nombreNuevo);
				response.put("archivoLicenciaConduccion", nombreNuevo);
	            return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}

	@PutMapping("/cedula/{nombre}/{persona}")
	public ResponseEntity<?> editarArchivoCedula(@RequestParam("accessToken") String accessToken,@PathVariable String nombre,@PathVariable String persona,@RequestParam("archivoCedula") MultipartFile file){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				if(file.isEmpty()) {
					response.put("mensaje", "archivo vacio");
		            return ResponseEntity.ok().body(response);
				}
				Persona personaGuardada = personaServicio.obtenerPorId(Long.valueOf(persona));
				String salidaPath = PropertiesReader.cedula;
				Path salida = Paths.get(salidaPath);
				archivoServicio.eliminar(nombre, salida);
				Calendar c = Calendar.getInstance();
				String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
				System.out.println("extensión original : "+FilenameUtils.getExtension(file.getOriginalFilename()));
				String nombreNuevo = spn+"_doc1"+"."+FilenameUtils.getExtension(file.getOriginalFilename());
				
				archivoServicio.crear(file,salida,nombreNuevo);
				response.put("archivoCedula", nombreNuevo);
	            return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PutMapping("/convenio/{nombre}")
	public ResponseEntity<?> editarArchivosConvenio(@RequestParam("accessToken") String accessToken,@PathVariable String nombre,@RequestParam("archivoConvenio") MultipartFile file){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				if(file.isEmpty()) {
					response.put("mensaje", "falta el archivo");
		            return ResponseEntity.ok().body(response);
				}
				String salidaPath = PropertiesReader.convenio;
				Path salida = Paths.get(salidaPath);
				archivoServicio.eliminar(nombre, salida);
				archivoServicio.crear(file,salida,nombre);
				response.put("archivoConvenio", nombre);
	            return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}

	@PutMapping("/tarjetaoperacion/{nombre}")
	public ResponseEntity<?> editarArchivoTarjetaOperacion(@RequestParam("accessToken") String accessToken,@PathVariable String nombre,@RequestParam("archivoTarjetaOperacion") MultipartFile file){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				if(file.isEmpty()) {
					response.put("mensaje", "archivo vacio");
		            return ResponseEntity.ok().body(response);
				}
				String salidaPath = PropertiesReader.tarjeta_operacion;
				Path salida = Paths.get(salidaPath);
				archivoServicio.eliminar(nombre, salida);
				archivoServicio.crear(file,salida,nombre);
				response.put("archivoTarjetaOperacion", nombre);
	            return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			} 
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PutMapping("/soat/{nombre}")
	public ResponseEntity<?> editarArchivoSoat(@RequestParam("accessToken") String accessToken,@PathVariable String nombre,@RequestParam("archivoSoat") MultipartFile file){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				if(file.isEmpty()) {
					response.put("mensaje", "archivo vacio");
		            return ResponseEntity.ok().body(response);
				}
				String salidaPath = PropertiesReader.soat;
				Path salida = Paths.get(salidaPath);
				archivoServicio.eliminar(nombre, salida);
				archivoServicio.crear(file,salida,nombre);
				response.put("archivoSoat", nombre);
	            return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			} 
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}

	@PutMapping("/revisiontecnicomecanica/{nombre}")
	public ResponseEntity<?> editarArchivoRevisiontecnicomecanica(@RequestParam("accessToken") String accessToken,@PathVariable String nombre,@RequestParam("archivoRevisionTecnicomecanica") MultipartFile file){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				if(file.isEmpty()) {
					response.put("mensaje", "archivo vacio");
		            return ResponseEntity.ok().body(response);
				}
				String salidaPath = PropertiesReader.vehiculo;
				Path salida = Paths.get(salidaPath);
				archivoServicio.eliminar(nombre, salida);
				archivoServicio.crear(file,salida,nombre);
				response.put("archivoRevisionTecnicomecanica", nombre);
	            return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			} 
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PutMapping("/polizacontractual/{nombre}")
	public ResponseEntity<?> editarArchivoPolizaContractual(@RequestParam("accessToken") String accessToken,@PathVariable String nombre,@RequestParam("archivoPolizaContractual") MultipartFile file){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				if(file.isEmpty()) {
					response.put("mensaje", "archivo vacio");
		            return ResponseEntity.ok().body(response);
				}
				String salidaPath = PropertiesReader.vehiculo;
				Path salida = Paths.get(salidaPath);
				archivoServicio.eliminar(nombre, salida);
				archivoServicio.crear(file,salida,nombre);
				response.put("archivoPolizaContractual", nombre);
	            return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			} 
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PutMapping("/polizaTodoRiesgo/{nombre}")
	public ResponseEntity<?> editarArchivoPolizaTodoRiesgo(@RequestParam("accessToken") String accessToken,@PathVariable String nombre,@RequestParam("archivoPolizaTodoRiesgo") MultipartFile file){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				if(file.isEmpty()) {
					response.put("mensaje", "archivo vacio");
		            return ResponseEntity.ok().body(response);
				}
				String salidaPath = PropertiesReader.vehiculo;
				Path salida = Paths.get(salidaPath);
				archivoServicio.eliminar(nombre, salida);
				archivoServicio.crear(file,salida,nombre);
				response.put("archivoPolizaTodoRiesgo", nombre);
	            return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			} 
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PutMapping("/polizaextracontractual/{nombre}")
	public ResponseEntity<?> editarArchivoPolizaExtracontractual(@RequestParam("accessToken") String accessToken,@PathVariable String nombre,@RequestParam("archivoPolizaExtracontractual") MultipartFile file){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				if(file.isEmpty()) {
					response.put("mensaje", "archivo vacio");
		            return ResponseEntity.ok().body(response);
				}
				String salidaPath = PropertiesReader.vehiculo;
				Path salida = Paths.get(salidaPath);
				archivoServicio.eliminar(nombre,salida);
				archivoServicio.crear(file,salida,nombre);
				response.put("archivoPolizaExtracontractual", nombre);
	            return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			} 
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PutMapping("/revisionpreventiva/{nombre}")
	public ResponseEntity<?> editarArchivoRevisionPreventiva(@RequestParam("accessToken") String accessToken,@PathVariable String nombre,@RequestParam("archivoRevisionPreventiva") MultipartFile file){
		System.out.println("cambiando revision preventiva : "+nombre);
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				if(file.isEmpty()) {
					response.put("mensaje", "archivo vacio");
		            return ResponseEntity.ok().body(response);
				}
				String salidaPath = PropertiesReader.vehiculo;
				Path salida = Paths.get(salidaPath);
				System.out.println("cambiando revision preventiva : 1");
				archivoServicio.eliminar(nombre, salida);
				System.out.println("cambiando revision preventiva : 2");
				archivoServicio.crear(file,salida,nombre);
				response.put("archivoRevisionPreventiva", nombre);
	            return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			} 
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PutMapping("/tarjetapropiedad/{nombre}")
	public ResponseEntity<?> editarArchivoTarjetaPropiedad(@RequestParam("accessToken") String accessToken,@PathVariable String nombre,@RequestParam("archivoTarjetaPropiedad") MultipartFile file){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				if(file.isEmpty()) {
					response.put("mensaje", "archivo vacio");
		            return ResponseEntity.ok().body(response);
				}
				String salidaPath = PropertiesReader.vehiculo;
				Path salida = Paths.get(salidaPath);
				archivoServicio.eliminar(nombre,salida);
				archivoServicio.crear(file,salida,nombre);
				response.put("archivoTarjetaPropiedad", nombre);
	            return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			} 
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PutMapping("/foto/{nombre}")
	public ResponseEntity<?> editarArchivoFoto(@RequestParam("accessToken") String accessToken,@PathVariable String nombre,@RequestParam("fotoPersona") MultipartFile file){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				if(file.isEmpty()) {
					response.put("mensaje", "archivo vacio");
		            return ResponseEntity.ok().body(response);
				}
				String salidaPath = PropertiesReader.vehiculo;
				Path salida = Paths.get(salidaPath);
				archivoServicio.eliminar(nombre, salida);
				Calendar c = Calendar.getInstance();
				String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
				System.out.println("extensión original : "+FilenameUtils.getExtension(file.getOriginalFilename()));
				String nombreNuevo = spn+"_doc1"+"."+FilenameUtils.getExtension(file.getOriginalFilename());
				
				archivoServicio.crear(file,salida,nombreNuevo);
				response.put("fotoPersona", nombreNuevo);
	            return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}

	@PutMapping("/planillaaportes/{nombre}")
	public ResponseEntity<?> editarArchivoPlanillaAportes(@RequestParam("accessToken") String accessToken,@PathVariable String nombre,@RequestParam("planillaAportes") MultipartFile file){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				if(file.isEmpty()) {
					response.put("mensaje", "archivo vacio");
		            return ResponseEntity.ok().body(response);
				}
				String salidaPath = PropertiesReader.vehiculo;
				Path salida = Paths.get(salidaPath);
				archivoServicio.eliminar(nombre, salida);
				Calendar c = Calendar.getInstance();
				String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
				System.out.println("extensión original : "+FilenameUtils.getExtension(file.getOriginalFilename()));
				String nombreNuevo = spn+"_doc1"+"."+FilenameUtils.getExtension(file.getOriginalFilename());
				
				archivoServicio.crear(file,salida,nombreNuevo);
				response.put("planillaAportes", nombreNuevo);
	            return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PutMapping("/examenesmedicos/{nombre}")
	public ResponseEntity<?> editarArchivoExamenesMedicos(@RequestParam("accessToken") String accessToken,@PathVariable String nombre,@RequestParam("examenesMedicos") MultipartFile file){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				if(file.isEmpty()) {
					response.put("mensaje", "archivo vacio");
		            return ResponseEntity.ok().body(response);
				}
				String salidaPath = PropertiesReader.vehiculo;
				Path salida = Paths.get(salidaPath);
				archivoServicio.eliminar(nombre, salida);
				Calendar c = Calendar.getInstance();
				String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
				System.out.println("extensión original : "+FilenameUtils.getExtension(file.getOriginalFilename()));
				String nombreNuevo = spn+"_doc1"+"."+FilenameUtils.getExtension(file.getOriginalFilename());
				archivoServicio.crear(file,salida,nombreNuevo);
				response.put("examenesMedicos", nombreNuevo);
	            return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PutMapping("/logocliente/{nombre}")
	public ResponseEntity<?> editarArchivoLogoCliente(@RequestParam("accessToken") String accessToken,@PathVariable String nombre,@RequestParam("logoCliente") MultipartFile file){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				if(file.isEmpty()) {
					response.put("mensaje", "archivo vacio");
		            return ResponseEntity.ok().body(response);
				}
				String salidaPath = PropertiesReader.vehiculo;
				Path salida = Paths.get(salidaPath);
				archivoServicio.eliminar(nombre, salida);
				archivoServicio.crear(file,salida,nombre);
				response.put("logoCliente", nombre);
	            return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (IOException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	ex.printStackTrace();
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	
	@GetMapping(value="/fotoimagen/{nombre}")
	public ResponseEntity<?> obtenerFoto(@RequestParam("accessToken") String accessToken,@PathVariable String nombre){
		System.out.println("nombre : "+nombre);
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			if(tokenServicio.existeToken(accessToken)!=null) {
				String salidaFuec = PropertiesReader.foto_persona;
				Path pathPrueba = Paths.get(salidaFuec);
				//Resource resource=archivoServicio.obtener(nombre,pathFotoPersona);
				Resource resource=archivoServicio.obtener(nombre,pathPrueba);
				if(resource.exists())
				{
					String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
					return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
	            }else{
	                response.put("mensaje", 3);
	                return ResponseEntity.ok().body(response);
	            }
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@GetMapping("/descargarFuec/{nombre}")
	public ResponseEntity<?> obtenerFoto(@PathVariable String nombre){
		System.out.println("nombre : "+nombre);
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			String salidaFuec = PropertiesReader.pathFuecs;
			Path pathPrueba = Paths.get(salidaFuec);
			//Resource resource=archivoServicio.obtener(nombre,pathPrueba);
			Resource resource=archivoServicio.obtener(nombre,pathPrueba);
			if(resource.exists())
			{
				String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
				return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
            }else{
                response.put("mensaje", 3);
                return ResponseEntity.ok().body(response);
            }
			
        }catch (MalformedURLException ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@PostMapping("/generar/{fuec}/{cuenta}")
	public ResponseEntity<?> generarPdf(@PathVariable("fuec") Long fuec, @PathVariable("cuenta") Long cuenta){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			
			System.out.println("cuenta : "+cuenta);
			String nombreArchivo = archivoServicio.pruebaItext(fuec,cuenta);
			System.out.println("nombre archivo : "+nombreArchivo);
			Fuec fuecObj = fuecServicio.traerPorId(fuec);
			fuecObj.setFuec(nombreArchivo.replace(":", "_"));
			fuecServicio.actualizar(fuecObj);
			response.put("mensaje", nombreArchivo.replace(":", "_"));
			return ResponseEntity.ok().body(response);
			
        }catch (Exception ex){
        	ex.printStackTrace();
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	@GetMapping("/sumarNumeros")
	public ResponseEntity<?> obtenerArchivo()
	{
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try{
			
				String var_1= "0001";
				int var11 = Integer.valueOf(var_1) + 1;
				System.out.println("valor final");
				response.put("total", var11);
				return ResponseEntity.ok().body(response);
			
        }catch (Exception ex){
        	response.put("error", ex.toString());
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	
}



