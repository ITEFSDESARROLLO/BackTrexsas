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

import com.itefs.trexsas.modelo.Cliente;
import com.itefs.trexsas.modelo.Notificacion;
import com.itefs.trexsas.modelo.NotificacionIndividual;
import com.itefs.trexsas.modelo.NotificacionPerfil;
import com.itefs.trexsas.modelo.Persona;
import com.itefs.trexsas.servicio.ClienteServicio;
import com.itefs.trexsas.servicio.NotificacionServicio;
import com.itefs.trexsas.servicio.TokenServicio;
import com.itefs.trexsas.utilidades.Correo;

@RestController
@RequestMapping("/notificacion")
public class NotificacionController {
	
	@Autowired
	private NotificacionServicio notificacionServicio;
	
	@Autowired
	private ClienteServicio clienteServicio;
	
	@Autowired
	private TokenServicio tokenServicio;
	@Autowired
    private Validator validator;
	
	@Autowired
	private Correo correo;
	
	@PostMapping
	public ResponseEntity<?> crear(@RequestParam("accessToken") String accessToken,@RequestBody Notificacion notificacion){
		System.out.println("1 creando");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			System.out.println(notificacion.getTituloNotificacion());
			System.out.println(notificacion.getDescripcionNotificacion());
			System.out.println(notificacion.getTipoNotificacion());
			notificacion.setEstadoNotificacion(Long.valueOf(1));
			System.out.println("2 creando");
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				System.out.println("3 creando");
				Set<ConstraintViolation<Notificacion>> validate = validator.validate(notificacion);
				if(validate.size()>0) {
					System.out.println("3.1 creando");
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Notificacion> item : validate) {
						System.out.println("mensaje : "+item.getMessage());
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				System.out.println("3.2 creando");
				notificacion.setEstadoNotificacion(Long.valueOf(1));
				Notificacion guardada = notificacionServicio.crear(notificacion);
				List<NotificacionPerfil> perfiles = guardada.getPerfilesNotificacion();
				System.out.println("es nulo");
				System.out.println(perfiles.get(0).getId());
				for (NotificacionPerfil notificacionPerfil : perfiles)
				{
					System.out.println("notificacion perfil : "+notificacionPerfil.getId());
					notificacionPerfil.setId(null);
					notificacionPerfil.setNotificacion(guardada);
					
					notificacionServicio.crearNotificacionPerfil(notificacionPerfil);
				}
				List<HashMap<String, String>>remitentes = notificacionServicio.obtenerRemitentes(guardada.getPerfilesNotificacion());
				if(guardada.getPerfilesNotificacion().size()==1)
				{
					//String correoS = notificacionServicio.obtenerCorreoNotificacion(guardada.getPerfilesNotificacion().get(0).getPersona());
					if(guardada.getPerfilesNotificacion().get(0).getPerfil()!=8)
					{
						System.out.println("correo persona "+guardada.getPerfilesNotificacion().get(0).getPersona());
						String correoS = notificacionServicio.obtenerCorreoNotificacion(guardada.getPerfilesNotificacion().get(0).getPersona());
						System.out.println("correo s "+guardada.getPerfilesNotificacion().get(0));
						correo.enviarCorreoNotificacion(correoS, guardada.getDescripcionNotificacion(), guardada.getTituloNotificacion());
					}else
					{
						System.out.println("correo cliente "+guardada.getPerfilesNotificacion().get(0).getPersona());
						String correoS = notificacionServicio.obtenerCorreoNotificacionCliente(Integer.valueOf(guardada.getPerfilesNotificacion().get(0).getPersona().toString()));
						System.out.println("correo s "+guardada.getPerfilesNotificacion().get(0));
						correo.enviarCorreoNotificacion(correoS, guardada.getDescripcionNotificacion(), guardada.getTituloNotificacion());
					}
					
					
				}else
				{
					correo.enviarCorreosMasivos(remitentes, guardada.getDescripcionNotificacion(), guardada.getTituloNotificacion());
				}
				
				System.out.println("todo correcto");
				response.put("mensaje", 1);
				return ResponseEntity.ok().body(response);
			}else {
				System.out.println("no todo correcto");
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("3 error");
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PostMapping("/notificacionCliente/{cliente}")
	public ResponseEntity<?> crearNotificacionCliente(@RequestParam("accessToken") String accessToken,@RequestBody Notificacion notificacion, @PathVariable("cliente") Integer cliente){
		System.out.println("1 creando");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			
			System.out.println(notificacion.getTituloNotificacion());
			System.out.println(notificacion.getDescripcionNotificacion());
			System.out.println(notificacion.getTipoNotificacion());
			notificacion.setEstadoNotificacion(Long.valueOf(1));
			System.out.println("2 creando");
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				System.out.println("3 creando");
				Set<ConstraintViolation<Notificacion>> validate = validator.validate(notificacion);
				if(validate.size()>0) {
					System.out.println("3.1 creando");
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Notificacion> item : validate) {
						System.out.println("mensaje : "+item.getMessage());
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				System.out.println("3.2 creando");
				notificacion.setEstadoNotificacion(Long.valueOf(1));
				Notificacion guardada = notificacionServicio.crear(notificacion);
				List<NotificacionPerfil> perfiles = guardada.getPerfilesNotificacion();
				System.out.println("es nulo");
				System.out.println(perfiles.get(0).getId());
				Cliente cliente1 = clienteServicio.obtenerPorId(cliente);
				System.out.println("correo s "+cliente1.getCorreoCliente());
				correo.enviarCorreoNotificacion(cliente1.getCorreoCliente(), guardada.getDescripcionNotificacion(), guardada.getTituloNotificacion());
				System.out.println("todo correcto");
				response.put("mensaje", 1);
				return ResponseEntity.ok().body(response);
			}else {
				System.out.println("no todo correcto");
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("3 error");
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PostMapping("/individual/{persona}")
	public ResponseEntity<?> crearNotificacionIndividual(@RequestParam("accessToken") String accessToken,@RequestBody Notificacion notificacion, @PathVariable("persona")Long persona){
		HashMap<String, Object> response = new HashMap<String, Object>();
		
		System.out.println(notificacion==null);
		System.out.println(notificacion.getDescripcionNotificacion());
		System.out.println(notificacion.getTituloNotificacion());
		System.out.println(notificacion.getEstadoNotificacion());
		System.out.println(notificacion.getPerfilesNotificacion());
		System.out.println(notificacion.getId());
		
		try {
			
			notificacion.setEstadoNotificacion(Long.valueOf(1));
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Set<ConstraintViolation<Notificacion>> validate = validator.validate(notificacion);
				if(validate.size()>0) {
					System.out.println("3.1 creando");
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Notificacion> item : validate) {
						System.out.println("mensaje : "+item.getMessage());
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				notificacion.setEstadoNotificacion(Long.valueOf(1));
				notificacionServicio.crearIndividual(notificacion);
				response.put("mensaje", 1);
				return ResponseEntity.ok().body(response);
			}else {
				
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch(Exception ex) {
			System.out.println("3 error");
			System.out.println("3 error "+ex.toString());
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> obtenerTodos(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				
				Page<Object[]> page=notificacionServicio.listar(pr);
				List<HashMap<String, Object>> obs = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					if(!o[4].toString().equals("3"))
					{
						System.out.println("esto es "+o[0]);
						HashMap<String, Object> ob = new HashMap<String, Object>();
						ob.put("idNotificacion", o[0]);
						ob.put("tituloNotificacion", o[1]);
						ob.put("descripcionNotificacion", o[2]);
						ob.put("tipoNotificacion", o[3]);
						ob.put("estadoNotificacion", o[4]);
						obs.add(ob);
					}
					
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("notificaciones", obs);
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
	
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerNotificacion(@RequestParam("accessToken") String accessToken,@PathVariable("id") long id){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Notificacion notificacion = notificacionServicio.obtenerPorId(id);
				response.put("mensaje", 1);
				response.put("notificacion", notificacion);
				response.put("puto", "putazo");
				return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			response.put("error", ex.toString());
			System.out.println("error : "+ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/personaNotificacion/{id}")
	public ResponseEntity<?> obtenerPersonaNotificacion(@RequestParam("accessToken") String accessToken,@PathVariable("id") Long id)
	{
		System.out.println("llamando");
		System.out.println("id : "+id);
		HashMap<String, Object> response = new HashMap<String, Object>();
		/*if(tokenServicio.existeToken(accessToken)!=null) {
			//Long ider = Long.valueOf(id);
			Notificacion notificacion = notificacionServicio.obtenerPorId(id);
			System.out.println("notificacion obtenida : "+notificacion==null);
			Persona persona = notificacionServicio.obtenerNotificacionPerfil(notificacion.getId());
			
			response.put("mensaje", 1);
			response.put("persona", persona);
			return ResponseEntity.ok().body(response);
		}else {
			response.put("mensaje", 2);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}*/
		
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				long ider = Long.valueOf(id);
				Notificacion notificacion = notificacionServicio.obtenerPorId(ider);
				Persona persona = notificacionServicio.obtenerNotificacionPerfil(notificacion.getId());
				
				response.put("mensaje", 1);
				response.put("persona", persona);
				return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			System.out.println("error : "+ex.toString());
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping
	public ResponseEntity<?> editar(@RequestParam("accessToken") String accessToken,@RequestBody Notificacion notificacion){
		HashMap<String, Object> response = new HashMap<String, Object>();
		System.out.println("1 "+notificacion.getId());
		System.out.println("1 "+notificacion.getTipoNotificacion());
		System.out.println("1 "+notificacion.getDescripcionNotificacion());
		System.out.println("1 "+notificacion.getTituloNotificacion());
		//System.out.println("1 "+notificacion.getPerfilesNotificacion().get(0).getNombrePerfil()==null);
		System.out.println("1 "+notificacion.getEstadoNotificacion()==null);
		System.out.println("1 "+notificacion.toString());
		try {
			System.out.println();
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				System.out.println("2");
				Notificacion notificacionActualizada = notificacionServicio.obtenerPorId(notificacion.getId());
				notificacionActualizada.setTituloNotificacion(notificacion.getTituloNotificacion());
				notificacionActualizada.setDescripcionNotificacion(notificacion.getDescripcionNotificacion());
				notificacionActualizada.setTipoNotificacion(notificacion.getTipoNotificacion());
				notificacionActualizada.setEstadoNotificacion(notificacion.getEstadoNotificacion());
				notificacionActualizada.setId(notificacion.getId());
				notificacionActualizada.setPerfilesNotificacion(notificacion.getPerfilesNotificacion());
				System.out.println("estado :"+notificacion.getPerfilesNotificacion()==null);
				Set<ConstraintViolation<Notificacion>> validate = validator.validate(notificacionActualizada);
				if(validate.size()>0 ) {
					System.out.println("3");
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Notificacion> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
						System.out.println("info error "+item.toString());
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				System.out.println("4");
				notificacionServicio.actualizar(notificacionActualizada);
				
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
	
	@PutMapping("/individual/{persona}")
	public ResponseEntity<?> editarIndividual(@RequestParam("accessToken") String accessToken,@RequestBody Notificacion notificacion,@PathVariable("persona") Long persona){
		System.out.println("actualizando ....");
		HashMap<String, Object> response = new HashMap<String, Object>();
		System.out.println("1 "+notificacion.getId());
		System.out.println("1 "+notificacion.getTipoNotificacion());
		System.out.println("1 "+notificacion.getDescripcionNotificacion());
		System.out.println("1 "+notificacion.getTituloNotificacion());
		//System.out.println("1 "+notificacion.getPerfilesNotificacion().get(0).getNombrePerfil()==null);
		System.out.println("1 "+notificacion.getEstadoNotificacion()==null);
		System.out.println("1 "+notificacion.getId());
		//NotificacionPerfil np = notificacionServicio.traerNotificacionPerfil(notificacion.getId());
		//np.setPersona(persona);
		//System.out.println("persona : "+np.getPersona());
		try {
			System.out.println();
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				System.out.println("2");
				Notificacion notificacionActualizada = new Notificacion();
				notificacionActualizada.setTituloNotificacion(notificacion.getTituloNotificacion());
				notificacionActualizada.setDescripcionNotificacion(notificacion.getDescripcionNotificacion());
				notificacionActualizada.setTipoNotificacion(notificacion.getTipoNotificacion());
				notificacionActualizada.setEstadoNotificacion(notificacion.getEstadoNotificacion());
				notificacionActualizada.setId(notificacion.getId());
				//notificacionActualizada.setPerfilesNotificacion(notificacion.getPerfilesNotificacion());
				NotificacionIndividual ni = notificacionServicio.traerNotificacionIndividual(notificacion.getId());
				Persona per = notificacionServicio.obtenerNotificacionPerfil(notificacion.getId());
				ni.setPersona(per);
				System.out.println("estado :"+notificacion.getEstadoNotificacion());
				Set<ConstraintViolation<Notificacion>> validate = validator.validate(notificacionActualizada);
				if(validate.size()>0 ) {
					System.out.println("3");
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Notificacion> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
						System.out.println("info error "+item.toString());
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				System.out.println("4");
				notificacionServicio.actualizar(notificacionActualizada);
				notificacionServicio.actualizarNotificacionIndividual2(ni);
				System.out.println("5 "+notificacion.getId());
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
	
	@DeleteMapping
	public ResponseEntity<?> eliminar(@RequestParam("accessToken") String accessToken,@RequestParam("id") long idNotificacion){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				notificacionServicio.eliminar(idNotificacion);
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
				List<HashMap<String, Object>> lista = notificacionServicio.listarCapacitacionesFiltradas(0, criterio, valor);
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
