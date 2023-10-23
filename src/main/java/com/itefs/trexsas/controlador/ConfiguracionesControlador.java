package com.itefs.trexsas.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.itefs.trexsas.modelo.Configuraciones;
import com.itefs.trexsas.modelo.Correo;
import com.itefs.trexsas.modelo.CorreoPQR;
import com.itefs.trexsas.modelo.EncuestaParametrizacion;
import com.itefs.trexsas.servicio.ConfiguracionesServicio;
import com.itefs.trexsas.servicio.CorreoServicio;
import com.itefs.trexsas.servicio.EncuestaParametroServicio;
import com.itefs.trexsas.servicio.TokenServicio;

@RestController
@RequestMapping("/configuraciones")
public class ConfiguracionesControlador {
	
	@Autowired
	private ConfiguracionesServicio configuracionesServicio;
	@Autowired
	private CorreoServicio correoServicio;
	
	@Autowired
	private EncuestaParametroServicio encuestaServicio;
	
	@Autowired
	private TokenServicio tokenServicio;
	@Autowired
    private Validator validator;
	
	
	
	
	//configuraciones
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerConfiguracionesPorId(@PathVariable("id") int id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Configuraciones ob=configuracionesServicio.obtenerPorId(1);
				if(ob!=null) {
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
	
	@GetMapping("/obtener")
	public ResponseEntity<?> obtenerConfiguracionesPQR(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<CorreoPQR> ob=correoServicio.obtenerTodosQPR();
				if(ob!=null) {
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
	
	@PutMapping
	public ResponseEntity<?> editarConfiguraciones(@RequestParam("accessToken") String accessToken,@RequestBody Configuraciones auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Configuraciones ob=configuracionesServicio.obtenerPorId(1);
				ob.setMaximoDuracionContrato(auxOb.getMaximoDuracionContrato());
				ob.setMaximoInicioContrato(auxOb.getMaximoInicioContrato());
				Set<ConstraintViolation<Configuraciones>> validate = validator.validate(ob);
				if(validate.size()>0 ) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Configuraciones> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					
					return ResponseEntity.ok().body(responses);
				}
				configuracionesServicio.actualizar(ob);
				
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
	
	
	//correo
	
	
	@PostMapping("/correo")
	public ResponseEntity<?> crearCorreo(@RequestParam("accessToken") String accessToken,@RequestBody Correo ob){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Set<ConstraintViolation<Correo>> validate = validator.validate(ob);
				if(validate.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Correo> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				correoServicio.crear(ob);
				
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
	
	@PostMapping("/correoPQR")
	public ResponseEntity<?> crearCorreoPQR(@RequestParam("accessToken") String accessToken,@RequestBody CorreoPQR ob){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Set<ConstraintViolation<CorreoPQR>> validate = validator.validate(ob);
				if(validate.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<CorreoPQR> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				correoServicio.crear(ob);
				
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
	
	@PutMapping("/correo")
	public ResponseEntity<?> editarCorreo(@RequestParam("accessToken") String accessToken,@RequestBody Correo auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Correo ob=correoServicio.obtenerPorId(1);
				ob.setCorreo(auxOb.getCorreo());
				Set<ConstraintViolation<Correo>> validate = validator.validate(ob);
				if(validate.size()>0 ) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Correo> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					
					return ResponseEntity.ok().body(responses);
				}
				correoServicio.actualizar(ob);
				
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
	
	@PutMapping("/correoPQR")
	public ResponseEntity<?> editarCorreoPQR(@RequestParam("accessToken") String accessToken,@RequestBody CorreoPQR auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				CorreoPQR ob=correoServicio.obtenerPorIdPQR(1);
				ob.setCorreo(auxOb.getCorreo());
				Set<ConstraintViolation<CorreoPQR>> validate = validator.validate(ob);
				if(validate.size()>0 ) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<CorreoPQR> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					
					return ResponseEntity.ok().body(responses);
				}
				correoServicio.actualizar(ob);
				
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
	
	@DeleteMapping("/correo/{id}")
	public ResponseEntity<?> eliminarCorreoPorId(@PathVariable("id") int id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				correoServicio.eliminar(id);
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
	
	@DeleteMapping("/correoPQR/{id}")
	public ResponseEntity<?> eliminarCorreoPQRPorId(@PathVariable("id") int id,@RequestParam("accessToken") String accessToken){
		System.out.println("elimando");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) 
			{
				correoServicio.eliminarPQR(id);
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
	
	@PostMapping("/encuestaParametro")
	public ResponseEntity<?> crearEncuestaParametrizada(@RequestParam("accessToken") String accessToken,@RequestBody EncuestaParametrizacion ob){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Set<ConstraintViolation<EncuestaParametrizacion>> validate = validator.validate(ob);
				if(validate.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<EncuestaParametrizacion> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				encuestaServicio.crearEncuestaParametro(ob);
				
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
	
	@PutMapping("/encuestaParametro")
	public ResponseEntity<?> editarEncuestaParametrizada(@RequestParam("accessToken") String accessToken,@RequestBody EncuestaParametrizacion ob){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Set<ConstraintViolation<EncuestaParametrizacion>> validate = validator.validate(ob);
				if(validate.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<EncuestaParametrizacion> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				encuestaServicio.actualizarEncuestaParametro(ob);
				
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
	
	@DeleteMapping("/encuestaParametro/{id}")
	public ResponseEntity<?> eliminarEncuestaParametro(@PathVariable("id") Long id,@RequestParam("accessToken") String accessToken){
		System.out.println("elimando");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) 
			{
				encuestaServicio.eliminarEncuesta(id);
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
	
	@GetMapping("/obtenerEncuestasParametro")
	public ResponseEntity<?> obtenerEncuestasParametro(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<EncuestaParametrizacion> ob=encuestaServicio.traerEncuestasParametrizadas();
				if(ob!=null) {
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
	
	@GetMapping("/obtenerParametro/{id}")
	public ResponseEntity<?> obtenerParametro(@RequestParam("accessToken") String accessToken, @PathParam("id") Long id){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				EncuestaParametrizacion ob=encuestaServicio.traerEncuesta(id);
				if(ob!=null) {
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
	

}
