package com.itefs.trexsas.controlador;


import java.util.HashMap;
import java.util.List;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.itefs.trexsas.modelo.EncuestaRespondida;

import com.itefs.trexsas.modelo.RespuestaEncuesta;
import com.itefs.trexsas.servicio.RespuestaEncuestasServicio;
import com.itefs.trexsas.servicio.TokenServicio;


@RestController
@RequestMapping("/encuestaRespuesta")
public class EncuestaRespondidaController 
{
	@Autowired
	private RespuestaEncuestasServicio respuestaEncuestasServicio;
	
	@Autowired
    private Validator validator;
	
	@Autowired
	private TokenServicio tokenServicio;
	
	@PostMapping
	public ResponseEntity<?> responderEncuesta(@RequestParam("accessToken") String accessToken,@RequestBody EncuestaRespondida respuesta)
	{
		System.out.println("1 creando");
		HashMap<String, Object> response = new HashMap<String, Object>();
		System.out.println("1"+respuesta.toString());
		//System.out.println("2"+encuesta.getPreguntasEncuesta().get(0).toString());
		//System.out.println("3"+encuesta.getPerfilUsuario());
		System.out.println("4");
		System.out.println("5");
		try {
			System.out.println("2 creando");
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				System.out.println("3 creando");
				validator.validate(respuesta);
				/*if(validate.size()>0) {
					System.out.println("3.1 creando");
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Encuesta> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
						System.out.println("error : "+item.getMessage());
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}*/
				System.out.println("3.2 creando");
				System.out.println(respuesta.toString());
				respuesta.setId(null);
				String fechaActual = java.time.LocalDate.now().toString();
				respuesta.setFechaRespuesta(fechaActual);
				EncuestaRespondida guardada = respuestaEncuestasServicio.responderEncuesta(respuesta);
				
				System.out.println("encuesta guardada "+guardada.toString());
				List<RespuestaEncuesta> respuestasPreguntas = guardada.getRespuestaEncuesta();
				System.out.println("existencia preguntas"+respuestasPreguntas.size());
				System.out.println("existencia preguntas :"+respuestasPreguntas==null);
				for (RespuestaEncuesta pregunta : respuestasPreguntas)
				{
					pregunta.setEncuestaRespondida(guardada);
					System.out.println("existencia opciones : "+respuestasPreguntas.get(0).toString());
					System.out.println("todo correcto pregunta");
					pregunta.setId(null);
					respuestaEncuestasServicio.guardarRespuesEncuesta(pregunta);
					//System.out.println(preguntaGuardada.toString());
					
					
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
			System.out.println("3 error");
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("calificacionesConductor/{id}")
	public ResponseEntity<?> buscarEncuestasConductor(@RequestParam("accessToken") String accessToken,@PathVariable("id")String id)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			System.out.println("2 creando"+accessToken);
			Long idToken=tokenServicio.existeToken(accessToken);
			if(idToken!=null) {
				HashMap<String, Object> salida = respuestaEncuestasServicio.obtenerCalificacionesConductor(id);
				System.out.println("todo correcto");
				response.put("mensaje", salida);
				return ResponseEntity.ok().body(response);
			}else {
				System.out.println("no todo correcto");
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch(Exception ex) {
			System.out.println("3 error "+ex.getMessage());
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
}
