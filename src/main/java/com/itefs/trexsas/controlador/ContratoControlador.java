package com.itefs.trexsas.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itefs.trexsas.modelo.Conductor;
import com.itefs.trexsas.modelo.Contrato;
import com.itefs.trexsas.modelo.Fuec;
import com.itefs.trexsas.modelo.POJOContratos;
import com.itefs.trexsas.modelo.Pasajero;
import com.itefs.trexsas.modelo.PersonaAutor;
import com.itefs.trexsas.servicio.ContratoServicio;
import com.itefs.trexsas.servicio.TokenServicio;

@RestController
@RequestMapping("/contrato")
public class ContratoControlador 
{
	                            
	@Autowired
	private ContratoServicio contratoServicio;
	
	@Autowired
	private TokenServicio tokenServicio;
	
	@Autowired
    private Validator validator;
	@PostMapping
	public ResponseEntity<?> crear(@RequestParam("accessToken") String accessToken,@RequestBody Contrato contrato){
		HashMap<String, Object> response = new HashMap<String, Object>();
		System.out.println("contrato : "+contrato.toString());
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				int totales = contratoServicio.totalContratos();
				contrato.setNoContrato(totales+1);
				Set<ConstraintViolation<Contrato>> validateContrato = validator.validate(contrato);
				if(validateContrato.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					
					for (ConstraintViolation<Contrato> item : validateContrato) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }	
					
					return ResponseEntity.ok().body(responses);
				}
				contrato.setConsecutivoContrato(Long.valueOf(contrato.getNumeracionContrato()));
				
				contrato.setContadorContrato(1);
				
				contratoServicio.crear(contrato);
				response.put("mensaje", 1);
				return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/listar")
	public ResponseEntity<?> listarContratos(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				
				List<HashMap<String, Object>> lista = contratoServicio.listar();
				response.put("contratos", lista);
				return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			ex.printStackTrace();
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/infobasica")
	public ResponseEntity<?> listarContratosInfoBasica(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				
				List<HashMap<String, Object>> lista = contratoServicio.listar();
				
				response.put("contratos", lista);
				return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			ex.printStackTrace();
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/listarPasajerosContrato/{contrato}")
	public ResponseEntity<?>  obtenerPasajerosContrato(@PathVariable("contrato") Long id,@RequestParam("accessToken") String accessToken)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) 
			{
				List<HashMap<String, Object>> lista = contratoServicio.traerPasajerosContrato(id);	
				response.put("pasajeros", lista);
				return ResponseEntity.ok().body(response);
			}else 
			{
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
			response.put("error", e.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/obtenerContrato/{id}")
	public ResponseEntity<?> obtenerContratoPorId(@PathVariable("id") int id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				response.put("mensaje", 1);
				Contrato ob=contratoServicio.obtenerPorId(id);
				response.put("contrato", ob);
				if(ob!=null) {
					return ResponseEntity.ok().body(response);
				}else {
					response.put("mensaje", 3);
					return ResponseEntity.ok().body(response);
				}
				
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/obtenerContratoEditar/{id}")
	public ResponseEntity<?> obtenerContratoEditarPorId(@PathVariable("id") int id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				response.put("mensaje", 1);
				HashMap<String, Object> ob=contratoServicio.obtenerContratoPorId(id);
				
				if(ob!=null) {
					ob.put("mensaje", 1);
					return ResponseEntity.ok().body(ob);
				}else {
					response.put("mensaje", 3);
					return ResponseEntity.ok().body(response);
				}
				
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	
	
	@PutMapping("/editarContrato")
	public ResponseEntity<?> editarContrato(@RequestParam("accessToken") String accessToken,@RequestBody Contrato contrato){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			System.out.println(contrato.toString());
			if(id!=null) {
				Contrato contratoNuevo=contratoServicio.obtenerPorId(contrato.getIdContrato());
				contratoNuevo.setCliente(contrato.getCliente());
				contratoNuevo.setTipoContrato(contrato.getTipoContrato());
				contratoNuevo.setFechaInicioContrato(contrato.getFechaInicioContrato());
				contratoNuevo.setFechaFinContrato(contrato.getFechaFinContrato());
				contratoNuevo.setNumeracionContrato(contrato.getNumeracionContrato());
				contratoNuevo.setEstadoContrato(contrato.getEstadoContrato());
				contratoNuevo.setValorContrato(contrato.getValorContrato());
				contratoNuevo.setResponsable(contrato.getResponsable());
				contratoNuevo.setDocumentoResponsable(contrato.getDocumentoResponsable());
				contratoNuevo.setTelefonoResponsable(contrato.getTelefonoResponsable());
				contratoNuevo.setDireccionResponsable(contrato.getTelefonoResponsable());
				contratoNuevo.setObjetoContrato(contrato.getObjetoContrato());
				contratoNuevo.setCiudad(contrato.getCiudad());
				contratoNuevo.setDocumentoResponsable(contrato.getDocumentoResponsable());
				System.out.println(contratoNuevo.toString());
				System.out.println(contratoNuevo.getCiudad().getCiudad());
				System.out.println(contratoNuevo.getTipoContrato().getTipo());
				Set<ConstraintViolation<Contrato>> validateContrato = validator.validate(contratoNuevo);
				contratoNuevo.setPasajeroList(contrato.getPasajeroList());
				List<Pasajero> pasajeros = contratoNuevo.getPasajeroList();
				if(validateContrato.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Contrato> item : validateContrato) {
						System.out.println("error : "+item.toString());
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				contratoServicio.actualizar(contratoNuevo,pasajeros);
				response.put("mensaje", 1);
				return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/cambiarEstado/{contrato}")
	public ResponseEntity<?> editarEstadoPermanente(@RequestParam("token") String token,@PathVariable("contrato") Long contrato){
		System.out.println("llamanado");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(token);
			if(id!=null) {
				Contrato contratoPermanente=contratoServicio.obtenerPorId(contrato);
				contratoPermanente.setEstadoContrato(0);
				contratoServicio.actualizar(contratoPermanente);
				response.put("mensaje", 1);
				return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}	
	
	@GetMapping("/cambiarEstadoHabilitado/{contrato}")
	public ResponseEntity<?> editarEstadoPermanenteHabilitado(@RequestParam("token") String accessToken,@PathVariable("contrato") Long contrato){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Contrato contratoPermanente=contratoServicio.obtenerPorId(contrato);
				contratoPermanente.setEstadoContrato(1);
				contratoServicio.actualizar(contratoPermanente);
				response.put("mensaje", 1);
				return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}	
	
	@GetMapping("/agregarPasajeroContrato/{pasajero}/{contrato}")
	public ResponseEntity<?> agregarPasajeroContrato(@PathVariable("pasajero") Integer pasajero,@PathVariable("contrato")Long contrato,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) 
			{
				Contrato contratoGuardado = contratoServicio.obtenerPorId(contrato);
				Pasajero pasajeroGuardado = new Pasajero();
				pasajeroGuardado.setIdPasajero(pasajero);
				contratoGuardado.addPasajero(pasajeroGuardado);
				contratoServicio.actualizar(contratoGuardado);
				response.put("mensaje", "1");
				return ResponseEntity.ok().body(response);
				
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			ex.printStackTrace();
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@DeleteMapping("/eliminarPasajeroContrato/{pasajero}/{contrato}")
	public ResponseEntity<?> eliminarPasajeroContrato(@PathVariable("pasajero") Integer pasajero,@PathVariable("contrato")Long contrato,@RequestParam("accessToken") String accessToken){
		System.out.println("eliminando pasajero contrato : "+pasajero+" - "+contrato);
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) 
			{
				contratoServicio.eliminarPasajeroContrato(pasajero, contrato);
				System.out.println("eliminado: ");
				response.put("mensaje", "1");
				return ResponseEntity.ok().body(response);
				
			}else {
				System.out.println("error 1 : ");
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			System.out.println("error 2 : ");
			ex.printStackTrace();
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PostMapping("/crearContratosMasivos")
	public ResponseEntity<?> crearContratosMasivos(@RequestParam("accessToken") String accessToken,@RequestBody POJOContratos contratosMasivos)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		System.out.println("creando contratos masivos : ");
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) 
			{
				List<Contrato> contratos = contratosMasivos.getContratos();
				contratoServicio.crearMasivos(contratos, id);
				response.put("mensaje", 1);
				return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
		
	

}
