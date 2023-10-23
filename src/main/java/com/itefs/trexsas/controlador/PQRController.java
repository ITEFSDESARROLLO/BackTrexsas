package com.itefs.trexsas.controlador;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itefs.trexsas.modelo.PQR;
import com.itefs.trexsas.servicio.PQRServicio;
import com.itefs.trexsas.servicio.TokenServicio;
import com.itefs.trexsas.utilidades.Correo;

@RestController
@RequestMapping("/pqr")
public class PQRController {
	
	@Autowired
	private PQRServicio pqrServicio;
	
	@Autowired
	private Correo correoUtilidad;
	
	@Autowired
	private TokenServicio tokenServicio;
	@Autowired
    private Validator validator;
	
	@PostMapping
	public ResponseEntity<?> crear(@RequestParam("accessToken") String accessToken,@RequestBody PQR pqr){
		System.out.println("1 creando"+pqr.getFechaPublicacion());
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			System.out.println("2 creando");
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) 
			{
				System.out.println("3 creando");
				Set<ConstraintViolation<PQR>> validate = validator.validate(pqr);
				if(validate.size()>0) {
					System.out.println("3.1 creando");
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<PQR> item : validate) {
						System.out.println("3.2 creando"+item.getMessage());
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				System.out.println("3.2 creando");
				pqrServicio.crear(pqr);
				
				System.out.println("todo correcto");
				response.put("mensaje", 1);
				this.enviarCorreos(pqr.getTipo());
				//this.enc
				return ResponseEntity.ok().body(response);
			}else {
				System.out.println("no todo correcto");
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch(Exception ex) {
			System.out.println("3 error");
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@Async
	private void enviarCorreos(long tipo)
	{
		pqrServicio.obtenerCorreos();
		
			try {
				/*for (CorreoPQR correoPQR : correos) {
					System.out.println("correo :"+correoPQR.getCorreo());
				correoUtilidad.enviarCorreoPQR(correoPQR.getCorreo(), tipo);
				}*/
				correoUtilidad.enviarCorreoEncuesta();
				//whatsappUtilidad.primerW();
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
	
	@GetMapping
	public ResponseEntity<?> obtenerTodos(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		
		
		
		String[] chunks = accessToken.split("\\.");
		
		Base64.Decoder decoder = Base64.getDecoder();

		String header = new String(decoder.decode(chunks[0]));
		String payload = new String(decoder.decode(chunks[1]));
		
		System.out.println("header : "+header);
		System.out.println("payload : "+payload);
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		System.out.println("1 ********");
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				System.out.println("2 ********");
				Page<Object[]> page=pqrServicio.listar(pr);
				List<HashMap<String, Object>> obs = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					System.out.println("3 ********");
					System.out.println("esto es "+o[1]);
					HashMap<String, Object> ob = new HashMap<String, Object>();
					ob.put("idPQR", o[0]);
					ob.put("descripcionPQR", o[1]);
					ob.put("tipoPQR", o[2]);
					ob.put("fechaPublicacionPQR", o[3]);
					ob.put("redactorPQR", o[4]);
					obs.add(ob);
				}
				System.out.println("4 ********");
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("pqr", obs);
				return ResponseEntity.ok().body(response);
			}else {
				System.out.println("5 ********");
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			System.out.println("6 ********");
			System.out.println("1 ********"+ex.toString());
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping
	public ResponseEntity<?> editar(@RequestParam("accessToken") String accessToken,@RequestBody PQR pqr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				PQR pqrActualizado = new PQR();
				pqrActualizado.setId(pqr.getId());
				pqrActualizado.setTipo(pqr.getTipo());
				pqrActualizado.setDescripcion(pqr.getDescripcion());
				pqrActualizado.setFechaPublicacion(pqr.getFechaPublicacion());
				pqrActualizado.setRedactor(pqr.getRedactor());
				
				Set<ConstraintViolation<PQR>> validate = validator.validate(pqrActualizado);
				if(validate.size()>0 ) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<PQR> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				pqrServicio.actualizar(pqrActualizado);
				
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
	
	
	@GetMapping("/filtrar")
	public ResponseEntity<?> filtrarFacturas(@RequestParam("accessToken") String accessToken, @RequestParam("criterio") String criterio, @RequestParam("valor") String valor)
	//public ResponseEntity<?> filtrarReservas(@RequestParam("accessToken") String accessToken, @PathVariable("criterio") String criterio, @PathVariable("valor") String valor)
	{
		System.out.println("en servicio filtro");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null)
			{
				System.out.println("criterio :"+criterio);
				System.out.println("valor :"+valor);
				List<HashMap<String, Object>> lista = pqrServicio.listarCapacitacionesFiltradas(0, criterio, valor);
				response.put("capacitacionesFiltradas", lista);
				return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			
		}catch (Exception e)
		{
			response.put("error", e.toString());
			return ResponseEntity.badRequest().body(response);
		}
		
	}

}
