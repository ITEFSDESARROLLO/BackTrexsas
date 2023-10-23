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

import com.itefs.trexsas.modelo.Capacitacion;
import com.itefs.trexsas.servicio.CapacitacionServicio;
import com.itefs.trexsas.servicio.TokenServicio;

@RestController
@RequestMapping("/capacitacion")
public class CapacitacionControlador 
{
	@Autowired
	private CapacitacionServicio capacitacionServicio;
	@Autowired
	private TokenServicio tokenServicio;
	@Autowired
    private Validator validator;
	
	@PostMapping
	public ResponseEntity<?> crear(@RequestParam("accessToken") String accessToken,@RequestBody Capacitacion capacitacion){
		System.out.println("1 creando");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			System.out.println("2 creando");
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				System.out.println("3 creando");
				Set<ConstraintViolation<Capacitacion>> validate = validator.validate(capacitacion);
				if(validate.size()>0) {
					System.out.println("3.1 creando");
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Capacitacion> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				System.out.println("3.2 creando");
				capacitacion.setEstado(1);
				capacitacionServicio.crear(capacitacion);
				System.out.println("todo correcto");
				response.put("mensaje", 1);
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
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") Long idC,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				capacitacionServicio.eliminar(idC);
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
	
	@GetMapping
	public ResponseEntity<?> obtenerTodos(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				
				Page<Object[]> page=capacitacionServicio.listar(pr);
				List<HashMap<String, Object>> obs = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					if(!o[8].toString().equals("3"))
					{
						System.out.println("esto es "+o[8]);
						HashMap<String, Object> ob = new HashMap<String, Object>();
						ob.put("idCapacitacion", o[0]);
						ob.put("nombreCapacitacion", o[1]);
						ob.put("descripcionCapacitacion", o[2]);
						ob.put("fechaPublicacion", o[3]);
						ob.put("fechaInicioInscripcion", o[4]);
						ob.put("fechaFinInscripcion", o[5]);
						ob.put("fechaInicioCapacitacion", o[6]);
						ob.put("fechaFinCapacitacion", o[7]);
						obs.add(ob);
					}
					
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("capacitaciones", obs);
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
	
	@PutMapping
	public ResponseEntity<?> editar(@RequestParam("accessToken") String accessToken,@RequestBody Capacitacion capacitacion){
		System.out.println("editando capacitacion : "+capacitacion.getEstado());
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				Capacitacion capacitacionActualizado = new Capacitacion();
				capacitacionActualizado.setNombre(capacitacion.getNombre());
				capacitacionActualizado.setFechaPublicacion(capacitacion.getFechaPublicacion());
				capacitacionActualizado.setFechaInscripcion(capacitacion.getFechaInscripcion());
				capacitacionActualizado.setFechaFinInscripcion(capacitacion.getFechaFinInscripcion());
				capacitacionActualizado.setFechaInicioCapacitacion(capacitacion.getFechaInicioCapacitacion());
				capacitacionActualizado.setFechaFinCapacitacion(capacitacion.getFechaFinCapacitacion());
				capacitacionActualizado.setId(capacitacion.getId());
				capacitacionActualizado.setDescripcion(capacitacion.getDescripcion());
				capacitacionActualizado.setEstado(capacitacion.getEstado());
				
				System.out.println(capacitacion.getFechaFinInscripcion());
				System.out.println(capacitacion.getFechaFinCapacitacion());
				Set<ConstraintViolation<Capacitacion>> validate = validator.validate(capacitacionActualizado);
				if(validate.size()>0 ) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Capacitacion> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				capacitacionServicio.actualizar(capacitacionActualizado);
				
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
				List<HashMap<String, Object>> lista = capacitacionServicio.listarCapacitacionesFiltradas(0, criterio, valor);
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
	
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerPorId(@PathVariable("id") Long id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Capacitacion capacitacion=capacitacionServicio.obtenerPorId(id);
				if(capacitacion!=null)
				{
					return ResponseEntity.ok().body(capacitacion);
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
