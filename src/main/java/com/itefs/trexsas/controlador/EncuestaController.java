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

import com.itefs.trexsas.modelo.Encuesta;
import com.itefs.trexsas.modelo.EncuestaParametrizacion;
import com.itefs.trexsas.modelo.Opcion;
import com.itefs.trexsas.modelo.Pregunta;
import com.itefs.trexsas.modelo.TipoParametro;
import com.itefs.trexsas.servicio.EncuestaParametroServicio;
import com.itefs.trexsas.servicio.EncuestaServicio;
import com.itefs.trexsas.servicio.RespuestaEncuestasServicio;
import com.itefs.trexsas.servicio.TokenServicio;

@RestController
@RequestMapping("/encuesta")
public class EncuestaController {
	
	@Autowired
	private EncuestaServicio encuestaServicio;
	
	@Autowired
	private RespuestaEncuestasServicio respuestaEncuestasServicio;
	
	@Autowired
	private TokenServicio tokenServicio;
	@Autowired
    private Validator validator;
	
	@Autowired
	private EncuestaParametroServicio encuestaParametroServicio;
	
	@PostMapping
	public ResponseEntity<?> crear(@RequestParam("accessToken") String accessToken,@RequestBody Encuesta encuesta){
		System.out.println("1 creando");
		HashMap<String, Object> response = new HashMap<String, Object>();
		System.out.println("1"+encuesta.toString());
		//System.out.println("2"+encuesta.getPreguntasEncuesta().get(0).toString());
		//System.out.println("3"+encuesta.getPerfilUsuario());
		System.out.println("4");
		System.out.println("5");
		try {
			System.out.println("2 creando");
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				System.out.println("3 creando");
				Set<ConstraintViolation<Encuesta>> validate = validator.validate(encuesta);
				if(validate.size()>0) {
					System.out.println("3.1 creando");
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Encuesta> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
						System.out.println("error : "+item.getMessage());
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				System.out.println("3.2 creando");
				System.out.println(encuesta.toString());
				System.out.println("guardando");
				Encuesta encuestaSave = new Encuesta();
				encuestaSave.setFechaPublicacion(encuesta.getFechaPublicacion());
				encuestaSave.setNombreEncuesta(encuesta.getNombreEncuesta());
				encuestaSave.setPerfilUsuario(encuesta.getPerfilUsuario());
				encuestaSave.setId(null);
				Encuesta guardada = encuestaServicio.crear(encuestaSave);
				System.out.println("guardada . "+guardada.toString());
				System.out.println("id encuesta : "+guardada.getId());
				System.out.println("encuesta guardada "+guardada.toString());
				List<Pregunta> preguntas = encuesta.getPreguntasEncuesta();
				System.out.println("existencia preguntas"+preguntas.size());
				System.out.println("existencia preguntas :"+preguntas==null);
				for (Pregunta pregunta : preguntas)
				{
					Pregunta preguntaSave = new Pregunta();
					preguntaSave.setEncuesta(guardada);
					preguntaSave.setDescripcion(pregunta.getDescripcion());
					//preguntaSave.set
					List<Opcion> opciones = pregunta.getOpcionesPregunta();
					System.out.println("existencia opciones : "+preguntas.get(0).toString());
					System.out.println("existencia opciones : "+opciones==null);
					System.out.println("todo correcto pregunta");
					Pregunta preguntaGuardada = encuestaServicio.guardarPreguntas(preguntaSave);
					System.out.println(preguntaGuardada.toString());
					for (Opcion opcion : opciones)
					{
						Opcion opcionSave = new Opcion();
						opcionSave.setDescripcion(opcion.getDescripcion());
						opcionSave.setPregunta(preguntaGuardada);
						
						System.out.println("existencia opcion");
						System.out.println("existencia "+opcion==null);
						System.out.println("opcion : "+opcion.toString());
						encuestaServicio.guardarOpciones(opcionSave);
						System.out.println("todo correcto opcion");
					}
					
					
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
			System.out.println("error : "+ex.getLocalizedMessage());
			
			System.out.println("3 error");
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PostMapping("/agregarPregunta/{encuesta}")
	public ResponseEntity<?> crearPregunta(@RequestParam("accessToken") String accessToken,@PathVariable("encuesta") Long encuesta
			,@RequestBody Pregunta pregunta){
		System.out.println("1 creando");
		HashMap<String, Object> response = new HashMap<String, Object>();
		System.out.println("1");
		//System.out.println("2"+encuesta.getPreguntasEncuesta().get(0).toString());
		//System.out.println("3"+encuesta.getPerfilUsuario());
		System.out.println("4");
		System.out.println("5");
		try {
			System.out.println("2 creando");
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				System.out.println("3 creando");
				
				
					Encuesta guardada = encuestaServicio.obtenerPorId(encuesta);
					Pregunta preguntaSave = new Pregunta();
					preguntaSave.setEncuesta(guardada);
					preguntaSave.setDescripcion(pregunta.getDescripcion());
					List<Opcion> opciones = pregunta.getOpcionesPregunta();
					System.out.println("existencia opciones : "+preguntaSave.getOpcionesPregunta() !=null);
					System.out.println("existencia opciones : "+opciones==null);
					System.out.println("todo correcto pregunta");
					Pregunta preguntaGuardada = encuestaServicio.guardarPreguntas(preguntaSave);
					System.out.println(preguntaGuardada.toString());
					for (Opcion opcion : opciones)
					{
						Opcion opcionSave = new Opcion();
						opcionSave.setDescripcion(opcion.getDescripcion());
						opcionSave.setPregunta(preguntaGuardada);
						
						System.out.println("existencia opcion");
						System.out.println("existencia "+opcion==null);
						System.out.println("opcion : "+opcion.toString());
						encuestaServicio.guardarOpciones(opcionSave);
						System.out.println("todo correcto opcion");
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
			System.out.println("error : "+ex.getLocalizedMessage());
			
			System.out.println("3 error");
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PostMapping("/agregarOpcion/{pregunta}")
	public ResponseEntity<?> crearOpcion(@RequestParam("accessToken") String accessToken,@PathVariable("pregunta") Long pregunta
			,@RequestBody Opcion opcion){
		System.out.println("1 creando");
		HashMap<String, Object> response = new HashMap<String, Object>();
		System.out.println("1");
		//System.out.println("2"+encuesta.getPreguntasEncuesta().get(0).toString());
		//System.out.println("3"+encuesta.getPerfilUsuario());
		System.out.println("4");
		System.out.println("5");
		try {
			System.out.println("2 creando");
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				System.out.println("3 creando");
						Pregunta preguntaGuardada = encuestaServicio.buscarPregunta(pregunta);
						Opcion opcionSave = new Opcion();
						opcionSave.setDescripcion(opcion.getDescripcion());
						opcionSave.setPregunta(preguntaGuardada);
						preguntaGuardada.agregarOpcion(opcionSave);
						System.out.println("existencia opcion");
						System.out.println("existencia "+opcion==null);
						System.out.println("opcion : "+opcion.toString());
						encuestaServicio.guardarOpciones(opcionSave);
						System.out.println("todo correcto opcion");
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
			System.out.println("error : "+ex.getLocalizedMessage());
			
			System.out.println("3 error");
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> obtenerTodos(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				
				Page<Object[]> page=encuestaServicio.listar(pr);
				List<HashMap<String, Object>> obs = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					if(o[0]!=null)
					{
						System.out.println("esto es "+o[0]);
						HashMap<String, Object> ob = new HashMap<String, Object>();
						ob.put("idEncuesta", o[0]);
						ob.put("descripcionEncuesta", o[1]);
						ob.put("perfilUsuario", o[2]);
						obs.add(ob);
					}
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("encuestas", obs);
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
	
	@GetMapping("traerEncuestas")
	public ResponseEntity<?> traerEncuestas(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				
				List<Object[]> page=encuestaServicio.listarEncuestas();
				List<HashMap<String, Object>> obs = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page) {
					if(o[0]!=null)
					{
						System.out.println("esto es "+o[0]);
						HashMap<String, Object> ob = new HashMap<String, Object>();
						ob.put("idEncuesta", o[0]);
						ob.put("descripcionEncuesta", o[1]);
						ob.put("perfilUsuario", o[2]);
						obs.add(ob);
					}
				}
				
				response.put("encuestas", obs);
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
	public ResponseEntity<?> editar(@RequestParam("accessToken") String accessToken,@RequestBody Encuesta encuesta){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				System.out.println("1");
				//System.out.println("1 pregunta encuesnta"+encuesta.getPreguntasEncuesta()== null);
				//System.out.println("1 pregunta encuesnta"+encuesta.getPreguntasEncuesta().get(1).toString());
				System.out.println("2 : "+encuesta.toString());
				Encuesta encuestaActualizado = encuestaServicio.obtenerPorId(encuesta.getId());
				//encuestaActualizado.getPreguntasEncuesta().clear();
				//encuestaActualizado.setId(encuesta.getId());
				encuestaActualizado.setNombreEncuesta(encuesta.getNombreEncuesta());
				//encuestaActualizado.setFechaPublicacion(encuesta.getFechaPublicacion());
				encuestaActualizado.setPerfilUsuario(encuesta.getPerfilUsuario());
				//encuestaActualizado.setPreguntasEncuesta(encuesta.getPreguntasEncuesta());
				Set<ConstraintViolation<Encuesta>> validate = validator.validate(encuestaActualizado);
				if(validate.size()>0 )
				{
					System.out.println("2");
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Encuesta> item : validate) {
						System.out.println("3");
						HashMap<String, Object> responseError = new HashMap<String, Object>();
						System.out.println("3 "+item.toString());
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				System.out.println("4");
				encuestaServicio.actualizar2(encuestaActualizado);
				
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
	
	@GetMapping("/verEncuesta/{id}")
	public ResponseEntity<?> obtenerPorId(@PathVariable("id") Long id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				
					Encuesta encuesta=encuestaServicio.obtenerPorId(id);
					if(encuesta!=null)
					{
						System.out.println("encuesnta : "+encuesta.getNombreEncuesta());
						return ResponseEntity.ok().body(encuesta);
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
	
	@DeleteMapping("eliminar/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") Long id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Encuesta encuesta=encuestaServicio.obtenerPorId(id);
				if(encuesta!=null)
				{
					System.out.println("encuesnta : "+encuesta.getNombreEncuesta());
					encuestaServicio.eliminar(id);
					response.put("mensaje", 1);
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
	
	@GetMapping("/preguntasEncuesta/{encuesta}")
	public ResponseEntity<?> buscarPreguntasEncuesta(@PathVariable("encuesta") Long encuesta,@RequestParam("accessToken") String accessToken)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Pregunta> preguntas=encuestaServicio.buscarPreguntasEncuesta(encuesta);
				if(preguntas!=null)
				{
					System.out.println("preguntas : "+preguntas.size());
					return ResponseEntity.ok().body(preguntas);
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
	
	@DeleteMapping("/eliminarPregunta/{pregunta}")
	public ResponseEntity<?> eliminarPregunta(@PathVariable("pregunta") Long id,@RequestParam("accessToken") String accessToken)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Pregunta pregunta=encuestaServicio.buscarPregunta(id);
				if(pregunta!=null)
				{
					System.out.println("encuesnta : "+pregunta.getDescripcion());
					encuestaServicio.eliminarPregunta(pregunta.getId());
					response.put("mensaje",1);
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
	
	@DeleteMapping("/eliminarOpcion/{opcion}")
	public ResponseEntity<?> eliminarOpcion(@PathVariable("opcion") Long id,@RequestParam("accessToken") String accessToken)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Opcion opcion=encuestaServicio.buscarOpcion(id);
				if(opcion!=null)
				{
					System.out.println("encuesnta : "+opcion.getDescripcion());
					encuestaServicio.eliminarOpcion(opcion.getId());
					response.put("mensaje",1);
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
	
	@PutMapping("/editarPregunta")
	public ResponseEntity<?> editarPregunta(@RequestParam("accessToken") String accessToken,@RequestBody Pregunta pregunta)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				System.out.println("1");
				System.out.println("2 : "+pregunta.toString());
				System.out.println("3 : "+pregunta.getOpcionesPregunta().size());
				Pregunta preguntaNUeva = encuestaServicio.buscarPregunta(pregunta.getId());
				//preguntaNUeva.setId(preguntaNUeva.getId());
				preguntaNUeva.setDescripcion(pregunta.getDescripcion());
				preguntaNUeva.setOpcionesPregunta(pregunta.getOpcionesPregunta());
				Set<ConstraintViolation<Pregunta>> validate = validator.validate(preguntaNUeva);
				if(validate.size()>0 )
				{
					System.out.println("2");
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Pregunta> item : validate) {
						System.out.println("3");
						HashMap<String, Object> responseError = new HashMap<String, Object>();
						System.out.println("3 "+item.toString());
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				System.out.println("4");
				encuestaServicio.editarPregunta(preguntaNUeva);
				
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
	
	@PutMapping("/editarOpcion")
	public ResponseEntity<?> editarPregunta(@RequestParam("accessToken") String accessToken,@RequestBody Opcion opcion)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				System.out.println("1");
				System.out.println("2 : "+opcion.toString());
				Opcion opcionNueva = encuestaServicio.buscarOpcion(opcion.getId());
				System.out.println("opción nueva : "+opcionNueva.toString());
				//opcionNueva.setId(opcionNueva.getId());
				opcionNueva.setDescripcion(opcion.getDescripcion());
				//opcionNueva.setPregunta(opcion.getPregunta());;
				Set<ConstraintViolation<Opcion>> validate = validator.validate(opcionNueva);
				if(validate.size()>0 )
				{
					System.out.println("2");
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Opcion> item : validate) {
						System.out.println("3");
						HashMap<String, Object> responseError = new HashMap<String, Object>();
						System.out.println("3 "+item.toString());
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				System.out.println("4");
				encuestaServicio.editarOpcion(opcionNueva);
				
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
	
	
	
	
	@GetMapping("encuestaUsuario/{id}/{id2}")
	public ResponseEntity<?> obtenerEncuestaParaUsuario(@PathVariable("id") Long id,@PathVariable("id2") Long id2){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
				boolean encuestaRespondida = respuestaEncuestasServicio.verificarExistenciaRespuestaEncuesta(id, id2);
				if(!encuestaRespondida)
				{
					Encuesta encuesta=encuestaServicio.obtenerPorId(id);
					if(encuesta!=null)
					{
						System.out.println("encuesnta : "+encuesta.getNombreEncuesta());
						return ResponseEntity.ok().body(encuesta);
					}else {
					response.put("mensaje", 3);
					return ResponseEntity.ok().body(response);
					}
			}else
			{
				response.put("mensaje", 4);
				
				return ResponseEntity.ok().body(response);
			}	
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("crearEncuestaParametro/{encuesta}/{tipo}/")
	public ResponseEntity<?> parametrizarEncuesta(@RequestParam("accessToken") String accessToken,@PathVariable("encuesta") Long encuesta,@PathVariable("tipo") Long tipo)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				System.out.println("1");
				Encuesta encuestaParametro = encuestaServicio.obtenerPorId(encuesta);
				EncuestaParametrizacion eParametro = new EncuestaParametrizacion();
				TipoParametro tp =encuestaParametroServicio.buscarPorId(id);
				eParametro.setEncuesta(encuestaParametro);
				eParametro.setTipoEncuesta(tp.getId());
				
				eParametro.setDescripcion(tp.getParametro());
				encuestaParametroServicio.crearEncuestaParametro(eParametro);
				System.out.println("4");
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
	
	@PutMapping("editarEncuestaParametro/{encuesta}/{id}")
	public ResponseEntity<?> editarParametroEncuesta(@RequestParam("accessToken") String accessToken,@PathVariable("encuesta") Long encuesta,@PathVariable("id") Long parametro)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				System.out.println("1");
				EncuestaParametrizacion eParametro = encuestaParametroServicio.traerEncuesta(parametro);
				Encuesta encuestaNueva = encuestaServicio.obtenerPorId(encuesta);
				eParametro.setEncuesta(encuestaNueva);
				encuestaParametroServicio.actualizarEncuestaParametro(eParametro);
				System.out.println("4");
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
	
	@DeleteMapping("eliminarEncuestaParametro/{parametro}")
	public ResponseEntity<?> eliminarEncuestaParametro(@RequestParam("accessToken") String accessToken,@PathVariable("id") Long parametro)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				System.out.println("1");
				encuestaParametroServicio.eliminarEncuesta(parametro);
				System.out.println("4");
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
	
	@GetMapping("traerEncuestasParametrizadas")
	public ResponseEntity<?> obtenerEncuestasParametrizadas(@RequestParam("accessToken") String accessToken){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				System.out.println("1");
				List<EncuestaParametrizacion> lista = encuestaParametroServicio.traerEncuestasParametrizadas();
				System.out.println("4");
				response.put("mensaje", 1);
				response.put("parametros", lista);
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
	
	@GetMapping("traerTipos")
	public ResponseEntity<?> traerTipos(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				
				List<Object[]> page=encuestaServicio.listarEncuestas();
				List<HashMap<String, Object>> obs = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page) {
					if(o[0]!=null)
					{
						System.out.println("esto es "+o[0]);
						HashMap<String, Object> ob = new HashMap<String, Object>();
						ob.put("idEncuesta", o[0]);
						ob.put("descripcionEncuesta", o[1]);
						ob.put("perfilUsuario", o[2]);
						obs.add(ob);
					}
				}
				
				response.put("encuestas", obs);
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
	
	

}