package com.itefs.trexsas.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.jboss.jandex.PrimitiveType.Primitive;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itefs.trexsas.modelo.Arl;
import com.itefs.trexsas.modelo.CajaCompensacion;
import com.itefs.trexsas.modelo.Conductor;
import com.itefs.trexsas.modelo.Cuenta;
import com.itefs.trexsas.modelo.DocumentoConductor;
import com.itefs.trexsas.modelo.Eps;
import com.itefs.trexsas.modelo.FondoPensiones;
import com.itefs.trexsas.modelo.Licencia;
import com.itefs.trexsas.modelo.Perfil;
import com.itefs.trexsas.modelo.Persona;
import com.itefs.trexsas.modelo.PersonaAutor;
import com.itefs.trexsas.servicio.ArlServicio;
import com.itefs.trexsas.servicio.CajaCompensacionServicio;
import com.itefs.trexsas.servicio.ConductorServicio;
import com.itefs.trexsas.servicio.CuentaServicio;
import com.itefs.trexsas.servicio.DocumentoConductorServicio;
import com.itefs.trexsas.servicio.EpsServicio;
import com.itefs.trexsas.servicio.EstadoCivilServicio;
import com.itefs.trexsas.servicio.FondoPensionesServicio;
import com.itefs.trexsas.servicio.LicenciaServicio;
import com.itefs.trexsas.servicio.PersonaServicio;
import com.itefs.trexsas.servicio.TokenServicio;

@RestController
@RequestMapping("/conductor")
public class ConductorControlador {
	
	
	@Autowired
	private ConductorServicio conductorServicio;
	@Autowired
	private LicenciaServicio licenciaServicio;
	@Autowired
	private CuentaServicio cuentaServicio;
	@Autowired
	private TokenServicio tokenServicio;
	@Autowired
	private PersonaServicio personaServicio;
	@Autowired
	private CajaCompensacionServicio cajaCompensacionServicio;
	@Autowired
	private FondoPensionesServicio fondoPensionesServicio;
	@Autowired
	private ArlServicio arlServicio;
	@Autowired
	private EpsServicio epsServicio;
	@Autowired
	private EstadoCivilServicio estadoCivilServicio;
	@Autowired
	private DocumentoConductorServicio documentoConductorServicio;
	@Autowired
    private Validator validator;
	
	@PostMapping
	public ResponseEntity<?> crear(@RequestParam("accessToken") String accessToken,@RequestBody Map<String,Object> map){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			System.out.println("creando nuevo conductor en edición");
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Perfil perfil=new Perfil();
				perfil.setIdPerfil(2);
				Persona autor=new Persona();
				autor.setIdPersona(id);
				ObjectMapper mapper=new ObjectMapper();
				Conductor conductor=mapper.convertValue(map.get("conductor"), Conductor.class);
				Licencia licencia=mapper.convertValue(map.get("licencia"), Licencia.class);
				conductor.setRegistradoPorConductor(autor);
				System.out.println("persona "+conductor.getPersona().getIdPersona());
				Persona persona=personaServicio.obtenerPorId(conductor.getPersona().getIdPersona());
				System.out.println("persona encontrada : "+persona.toString());
				//persona.setDocumentoUnoPersona(conductor.getPersona().getDocumentoUnoPersona());
				//persona.setDocumentoDosPersona(conductor.getPersona().getDocumentoDosPersona());
				Set<ConstraintViolation<Conductor>> validate = validator.validate(conductor);
				Set<ConstraintViolation<Licencia>> validateLicencia = validator.validate(licencia);
				if(validate.size()>0 || validateLicencia.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Conductor> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					for (ConstraintViolation<Licencia> item : validateLicencia) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				personaServicio.actualizar(persona);
				conductor.setEstadoConductor(1);
				conductorServicio.crear(conductor);
				licencia.setConductor(conductor);
				licencia.setNumeroLicencia(persona.getDocumentoPersona());
				licenciaServicio.crear(licencia);
				DocumentoConductor documentoConductor=new DocumentoConductor();
				documentoConductor.setConductor(conductor);
				documentoConductorServicio.crear(documentoConductor);
				Cuenta cuenta=cuentaServicio.obtenerPorIdPersona(conductor.getPersona().getIdPersona());
				cuenta.addPerfil(perfil);
				cuentaServicio.actualizar(cuenta);
				
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
	
	@PutMapping
	public ResponseEntity<?> editar(@RequestParam("accessToken") String accessToken,@RequestBody Map<String,Object> map){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				ObjectMapper mapper=new ObjectMapper();
				Conductor auxConductor=mapper.convertValue(map.get("conductor"), Conductor.class);
				Licencia auxLicencia=mapper.convertValue(map.get("licencia"), Licencia.class);
				Persona persona=personaServicio.obtenerPorId(auxConductor.getPersona().getIdPersona());
				personaServicio.actualizar(persona);
				Persona autor=new Persona();
				autor.setIdPersona(id);
				
				Conductor conductor=conductorServicio.obtenerPorId(auxConductor.getIdConductor());
				conductor.setRhConductor(auxConductor.getRhConductor());
				conductor.setInicioEpsConductor(auxConductor.getInicioEpsConductor());
				conductor.setFinEpsConductor(auxConductor.getFinEpsConductor());
				conductor.setInicioArlConductor(auxConductor.getInicioArlConductor());
				conductor.setFinArlConductor(auxConductor.getFinArlConductor());
				conductor.setGeneroConductor(auxConductor.getGeneroConductor());
				conductor.setCajaCompensacion(auxConductor.getCajaCompensacion());
				conductor.setFondoPensiones(auxConductor.getFondoPensiones());
				conductor.setExamenesMedicosConductor(auxConductor.getExamenesMedicosConductor());
				conductor.setPlanillaAportesConductor(auxConductor.getPlanillaAportesConductor());
				conductor.setArl(auxConductor.getArl());
				conductor.setEps(auxConductor.getEps());
				conductor.setEstadoCivil(auxConductor.getEstadoCivil());
				//conductor.setActualizadoPorConductor(autor);
				Licencia licencia=licenciaServicio.obtenerPorId(auxLicencia.getIdLicencia());
				licencia.setCategoriaLicencia(auxLicencia.getCategoriaLicencia());
				licencia.setFechaExpedicionLicencia(auxLicencia.getFechaExpedicionLicencia());
				licencia.setFechaVencimientoLicencia(auxLicencia.getFechaVencimientoLicencia());
				Set<ConstraintViolation<Conductor>> validateConductor = validator.validate(conductor);
				Set<ConstraintViolation<Licencia>> validateLicencia = validator.validate(licencia);
				if(validateConductor.size()>0 || validateLicencia.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Conductor> item : validateConductor) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					for (ConstraintViolation<Licencia> item : validateLicencia) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				conductorServicio.actualizar(conductor);
				licenciaServicio.actualizar(licencia);
				
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
	
	@PutMapping("/estado")
	public ResponseEntity<?> editarEstado(@RequestParam("accessToken") String accessToken,@RequestBody Conductor auxConductor){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Persona autor=new Persona();
				autor.setIdPersona(id);
				Conductor conductor=conductorServicio.obtenerPorId(auxConductor.getIdConductor());
				conductor.setEstadoConductor(auxConductor.getEstadoConductor());
				conductor.setActualizadoPorConductor(autor);
				conductorServicio.actualizar(conductor);
				
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
				Page<Object[]> page=conductorServicio.listar(pr);
				List<HashMap<String, Object>> conductores = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					HashMap<String, Object> conductor = new HashMap<String, Object>();
					conductor.put("idConductor", o[0]);
					conductor.put("documentoPersona", o[1]);
					conductor.put("nombrePersona", o[2]);
					conductor.put("apellidoPersona", o[3]);
					conductor.put("estadoConductor", o[4]);
					conductor.put("telefonoPersona", o[5]);
					conductor.put("correoPersona", o[6]);
					conductores.add(conductor);
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("conductores", conductores);
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
	public ResponseEntity<?> obtenerPorId(@PathVariable("id") int id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Conductor conductor=conductorServicio.obtenerPorId(id);
				if(conductor!=null) {
					return ResponseEntity.ok().body(conductor);
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
	
	@GetMapping("/obtenerPorPersona/{id}")
	public ResponseEntity<?> obtenerPorPersona(@PathVariable("id") String id,@RequestParam("accessToken") String accessToken){
		System.out.println("buscando cuenta : "+id);
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Conductor conductor=conductorServicio.obtenerConductorPorPersona(Long.valueOf(id));
				if(conductor!=null) {
					return ResponseEntity.ok().body(conductor);
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
	
	@GetMapping("conductor/{id}")
	public ResponseEntity<?> obtenerDatosUsuarioPorIdConductor(@PathVariable("id") int id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Conductor conductor=conductorServicio.obtenerPorId(id);
				if(conductor!=null) {
					HashMap<String, Object> cuenta = new HashMap<String, Object>();
					List<Object[]> consulta=cuentaServicio.obtenerBasicoPorIdPersona(conductor.getPersona().getIdPersona());
					cuenta.put("idCuenta", consulta.get(0)[0]);
					cuenta.put("usuarioCuenta", consulta.get(0)[1]);
					cuenta.put("estadoCuenta", consulta.get(0)[2]);
					cuenta.put("registradoPorCuenta", consulta.get(0)[3]);
					cuenta.put("fechaRegistroCuenta", consulta.get(0)[4]);
					cuenta.put("actualizadoPorCuenta", consulta.get(0)[5]);
					cuenta.put("fechaActualizacionCuenta", consulta.get(0)[6]);
					response.put("conductor", conductor);
					response.put("cuenta", cuenta);
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
	
	@GetMapping("noconductores")
	public ResponseEntity<?> obtenerPersonasNoConductores(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Object[]> list=conductorServicio.listarPersonasNoConductores();
				List<HashMap<String, Object>> noConductores = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:list) {
					HashMap<String, Object> noConductor = new HashMap<String, Object>();
					noConductor.put("idPersona", o[0]);
					noConductor.put("nombrePersona", o[1]);
					noConductor.put("apellidoPersona", o[2]);
					noConductor.put("documentoPersona", o[3]);
					noConductores.add(noConductor);
				}
				return ResponseEntity.ok().body(noConductores);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/infobasica")
	public ResponseEntity<?> obtenerInfoBasica(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Object[]> list=null;
				list=conductorServicio.infoBasica();
				List<HashMap<String, Object>> conductores = new ArrayList<HashMap<String, Object>>();
				System.out.println("total conductores "+list.size());
				for(Object[] o:list) {
					HashMap<String, Object> conductor = new HashMap<String, Object>();
					conductor.put("idConductor", o[0]);
					conductor.put("nombrePersona", o[1]);
					conductor.put("apellidoPersona", o[2]);
					conductor.put("documentoPersona", o[3]);
					conductores.add(conductor);
				}
				return ResponseEntity.ok().body(conductores);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	//fondo pensiones
	
	@PostMapping("/fondopensiones")
	public ResponseEntity<?> crearFondoPensiones(@RequestParam("accessToken") String accessToken,@RequestBody FondoPensiones ob){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				ob.setRegistradoPorFondoPensiones(autor);
				Set<ConstraintViolation<FondoPensiones>> validate = validator.validate(ob);
				if(validate.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<FondoPensiones> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				fondoPensionesServicio.crear(ob);
				
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
	
	@GetMapping("/fondopensiones/listar")
	public ResponseEntity<?> listarFondoPensiones(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Page<Object[]> page=fondoPensionesServicio.listar(pr);
				List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					HashMap<String, Object> objeto = new HashMap<String, Object>();
					objeto.put("idFondoPensiones", o[0]);
					objeto.put("fondoPensiones", o[1]);
					objeto.put("estadoFondoPensiones", o[2]);
					lista.add(objeto);
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("fondosPensiones", lista);
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
	
	@GetMapping("/fondopensiones/{id}")
	public ResponseEntity<?> obtenerFondoPensionesPorId(@PathVariable("id") int id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				FondoPensiones ob=fondoPensionesServicio.obtenerPorId(id);
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
	
	@GetMapping("/fondopensiones")
	public ResponseEntity<?> obtenerTodosFondoPensiones(){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			return ResponseEntity.ok().body(fondoPensionesServicio.obtenerTodos());
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping("/fondopensiones")
	public ResponseEntity<?> editarFondoPensiones(@RequestParam("accessToken") String accessToken,@RequestBody FondoPensiones auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				FondoPensiones ob=fondoPensionesServicio.obtenerPorId(auxOb.getIdFondoPensiones());
				ob.setFondoPensiones(auxOb.getFondoPensiones());
				ob.setEstadoFondoPensiones(auxOb.getEstadoFondoPensiones());
				ob.setActualizadoPorFondoPensiones(autor);
				Set<ConstraintViolation<FondoPensiones>> validate = validator.validate(ob);
				if(validate.size()>0 ) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<FondoPensiones> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					
					return ResponseEntity.ok().body(responses);
				}
				fondoPensionesServicio.actualizar(ob);
				
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
	
	@PutMapping("/fondopensiones/estado")
	public ResponseEntity<?> editarEstadoFondoPensiones(@RequestParam("accessToken") String accessToken,@RequestBody FondoPensiones auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				FondoPensiones ob=fondoPensionesServicio.obtenerPorId(auxOb.getIdFondoPensiones());
				ob.setEstadoFondoPensiones(auxOb.getEstadoFondoPensiones());
				ob.setActualizadoPorFondoPensiones(autor);
				
				fondoPensionesServicio.actualizar(ob);
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
	
	//caja compensacion
	
	@PostMapping("/cajacompensacion")
	public ResponseEntity<?> crearCajaCompensacion(@RequestParam("accessToken") String accessToken,@RequestBody CajaCompensacion ob){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				ob.setRegistradoPorCajaCompensacion(autor);
				Set<ConstraintViolation<CajaCompensacion>> validate = validator.validate(ob);
				if(validate.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<CajaCompensacion> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				cajaCompensacionServicio.crear(ob);
				
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
	
	@GetMapping("/cajacompensacion/listar")
	public ResponseEntity<?> listarCajaCompensacion(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Page<Object[]> page=cajaCompensacionServicio.listar(pr);
				List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					HashMap<String, Object> objeto = new HashMap<String, Object>();
					objeto.put("idCajaCompensacion", o[0]);
					objeto.put("cajaCompensacion", o[1]);
					objeto.put("estadoCajaCompensacion", o[2]);
					lista.add(objeto);
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("cajasCompensacion", lista);
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
	
	@GetMapping("/cajacompensacion/{id}")
	public ResponseEntity<?> obtenerCajaCompensacionPorId(@PathVariable("id") int id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				CajaCompensacion ob=cajaCompensacionServicio.obtenerPorId(id);
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
	
	@GetMapping("/cajacompensacion")
	public ResponseEntity<?> obtenerTodosCajaCompensacion(){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			return ResponseEntity.ok().body(cajaCompensacionServicio.obtenerTodos());
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping("/cajacompensacion")
	public ResponseEntity<?> editarCajaCompensacion(@RequestParam("accessToken") String accessToken,@RequestBody CajaCompensacion auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				CajaCompensacion ob=cajaCompensacionServicio.obtenerPorId(auxOb.getIdCajaCompensacion());
				ob.setCajaCompensacion(auxOb.getCajaCompensacion());
				ob.setEstadoCajaCompensacion(auxOb.getEstadoCajaCompensacion());
				ob.setActualizadoPorCajaCompensacion(autor);
				Set<ConstraintViolation<CajaCompensacion>> validate = validator.validate(ob);
				if(validate.size()>0 ) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<CajaCompensacion> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					
					return ResponseEntity.ok().body(responses);
				}
				cajaCompensacionServicio.actualizar(ob);
				
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
	
	@PutMapping("/cajacompensacion/estado")
	public ResponseEntity<?> editarEstadoCajaCompensacion(@RequestParam("accessToken") String accessToken,@RequestBody CajaCompensacion auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				CajaCompensacion ob=cajaCompensacionServicio.obtenerPorId(auxOb.getIdCajaCompensacion());
				ob.setEstadoCajaCompensacion(auxOb.getEstadoCajaCompensacion());
				ob.setActualizadoPorCajaCompensacion(autor);
				
				cajaCompensacionServicio.actualizar(ob);
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
	
	//arl
	
	@PostMapping("/arl")
	public ResponseEntity<?> crearArl(@RequestParam("accessToken") String accessToken,@RequestBody Arl ob){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				ob.setRegistradoPorArl(autor);
				Set<ConstraintViolation<Arl>> validate = validator.validate(ob);
				if(validate.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Arl> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				arlServicio.crear(ob);
				
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
	
	@GetMapping("/arl/listar")
	public ResponseEntity<?> listarArl(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Page<Object[]> page=arlServicio.listar(pr);
				List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					HashMap<String, Object> objeto = new HashMap<String, Object>();
					objeto.put("idArl", o[0]);
					objeto.put("arl", o[1]);
					objeto.put("estadoArl", o[2]);
					lista.add(objeto);
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("arls", lista);
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
	
	@GetMapping("/arl/{id}")
	public ResponseEntity<?> obtenerArlPorId(@PathVariable("id") int id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Arl ob=arlServicio.obtenerPorId(id);
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
	
	@GetMapping("/arl")
	public ResponseEntity<?> obtenerTodosArl(){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			return ResponseEntity.ok().body(arlServicio.obtenerTodos());
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping("/arl")
	public ResponseEntity<?> editarArl(@RequestParam("accessToken") String accessToken,@RequestBody Arl auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				Arl ob=arlServicio.obtenerPorId(auxOb.getIdArl());
				ob.setArl(auxOb.getArl());
				ob.setEstadoArl(auxOb.getEstadoArl());
				ob.setActualizadoPorArl(autor);
				Set<ConstraintViolation<Arl>> validate = validator.validate(ob);
				if(validate.size()>0 ) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Arl> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					
					return ResponseEntity.ok().body(responses);
				}
				arlServicio.actualizar(ob);
				
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
	
	@PutMapping("/arl/estado")
	public ResponseEntity<?> editarEstadoArl(@RequestParam("accessToken") String accessToken,@RequestBody Arl auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				Arl ob=arlServicio.obtenerPorId(auxOb.getIdArl());
				ob.setEstadoArl(auxOb.getEstadoArl());
				ob.setActualizadoPorArl(autor);
				
				arlServicio.actualizar(ob);
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
	
	//eps
	
	@PostMapping("/eps")
	public ResponseEntity<?> crearEps(@RequestParam("accessToken") String accessToken,@RequestBody Eps ob){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				ob.setRegistradoPorEps(autor);
				Set<ConstraintViolation<Eps>> validate = validator.validate(ob);
				if(validate.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Eps> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				epsServicio.crear(ob);
				
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
	
	@GetMapping("/eps/listar")
	public ResponseEntity<?> listarEps(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Page<Object[]> page=epsServicio.listar(pr);
				List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					HashMap<String, Object> objeto = new HashMap<String, Object>();
					objeto.put("idEps", o[0]);
					objeto.put("eps", o[1]);
					objeto.put("estadoEps", o[2]);
					lista.add(objeto);
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("epss", lista);
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
	
	@GetMapping("/eps/filtrar")
	public ResponseEntity<?> filtrarEps(@RequestParam("accessToken") String accessToken,@RequestParam("criterio") String criterio, @RequestParam("valor") Integer valor){
		System.out.println("en servicio filtro afiliacion");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
				if(tokenServicio.existeToken(accessToken)!=null)
				{
					System.out.println("criterio :"+criterio);
					System.out.println("valor :"+valor);
					HashMap<String,Object> lista = new HashMap<String,Object>();
					switch (criterio) 
					{
					case "ee":
						System.out.println("buscando por placa : ");
						lista = epsServicio.filtrarPorEstado(valor);
						return ResponseEntity.ok().body(lista);
					case "ce":
						System.out.println("buscando por placa : ");
						lista = epsServicio.filtrarPorCodigo(valor);
						return ResponseEntity.ok().body(lista);
				default:
					return ResponseEntity.badRequest().body("malo");
				
					}
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
	
	@GetMapping("/eps/{id}")
	public ResponseEntity<?> obteneEpsPorId(@PathVariable("id") int id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Eps ob=epsServicio.obtenerPorId(id);
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
	
	@GetMapping("/eps")
	public ResponseEntity<?> obtenerTodosEps(){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			return ResponseEntity.ok().body(epsServicio.obtenerTodos());
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping("/eps")
	public ResponseEntity<?> editarEps(@RequestParam("accessToken") String accessToken,@RequestBody Eps auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				Eps ob=epsServicio.obtenerPorId(auxOb.getIdEps());
				ob.setEps(auxOb.getEps());
				ob.setEstadoEps(auxOb.getEstadoEps());
				ob.setActualizadoPorEps(autor);
				Set<ConstraintViolation<Eps>> validate = validator.validate(ob);
				if(validate.size()>0 ) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Eps> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					
					return ResponseEntity.ok().body(responses);
				}
				epsServicio.actualizar(ob);
				
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
	
	@PutMapping("/eps/estado")
	public ResponseEntity<?> editarEstadoEps(@RequestParam("accessToken") String accessToken,@RequestBody Eps auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				Eps ob=epsServicio.obtenerPorId(auxOb.getIdEps());
				ob.setEstadoEps(auxOb.getEstadoEps());
				ob.setActualizadoPorEps(autor);
				
				epsServicio.actualizar(ob);
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
	
	//estado civil
	@GetMapping("/estadocivil")
	public ResponseEntity<?> obtenerTodosEstadoCivil(){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			return ResponseEntity.ok().body(estadoCivilServicio.obtenerTodos());
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/conductores")
	public ResponseEntity<?> traerTodos(@RequestParam("accessToken") String accessToken)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null)
			{
				List<Object[]> page=conductorServicio.obtenerTodos();
				List<HashMap<String, Object>> conductores = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page) {
					HashMap<String, Object> conductor = new HashMap<String, Object>();
					conductor.put("idPersona", o[0]);
					conductor.put("nombrePersona", o[1]);
					conductor.put("apellidoPersona", o[2]);
					conductor.put("correo", o[3]);
					conductores.add(conductor);
				}
				response.put("totalResultados", page.size());
				response.put("conductores", conductores);
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
	public ResponseEntity<?> filtrarConductores(@RequestParam("accessToken") String accessToken, @RequestParam("criterio") String criterio, @RequestParam("valor") String valor)
	{
		System.out.println("en servicio filtro afiliacion");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
				if(tokenServicio.existeToken(accessToken)!=null)
				{
					System.out.println("criterio :"+criterio);
					System.out.println("valor :"+valor);
					HashMap<String,Object> lista = new HashMap<String,Object>();

					switch (criterio) 
					{
					case "nc":
						System.out.println("buscando por placa : ");
						lista = conductorServicio.listarPorNombre(0,valor);
						return ResponseEntity.ok().body(lista);
					case "dc":
						System.out.println("buscando por placa : ");
						lista = conductorServicio.listarPorDocumento(0,valor);
						return ResponseEntity.ok().body(lista);
					case "es":
						System.out.println("buscando por placa : ");
						lista = conductorServicio.listarPorEstado(0,valor);
						return ResponseEntity.ok().body(lista);
					case "tf":
						System.out.println("buscando por placa : ");
						lista = conductorServicio.listarPorTelefono(0,valor);
						return ResponseEntity.ok().body(lista);
					case "cc":
						System.out.println("buscando por placa : ");
						lista = conductorServicio.listarPorCorreo(0,valor);
						return ResponseEntity.ok().body(lista);
				default:
					return ResponseEntity.badRequest().body("malo");
				
					}
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
	
	@GetMapping("/arl/filtrar")
	public ResponseEntity<?> filtrarArl(@RequestParam("accessToken") String accessToken,@RequestParam("criterio") String criterio, @RequestParam("valor") Integer valor){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
				if(tokenServicio.existeToken(accessToken)!=null)
				{
					System.out.println("criterio :"+criterio);
					System.out.println("valor :"+valor);
					HashMap<String,Object> lista = new HashMap<String,Object>();
					switch (criterio) 
					{
					case "ee":
						System.out.println("buscando por placa : ");
						lista = arlServicio.filtrarPorEstado(valor);
						return ResponseEntity.ok().body(lista);
					case "ce":
						System.out.println("buscando por placa : ");
						lista = arlServicio.filtrarPorCodigo(valor);
						return ResponseEntity.ok().body(lista);
				default:
					return ResponseEntity.badRequest().body("malo");
				
					}
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
	
	@GetMapping("/cajaCompensacion/filtrar")
	public ResponseEntity<?> filtrarCajaCompensacion(@RequestParam("accessToken") String accessToken,@RequestParam("criterio") String criterio, @RequestParam("valor") Integer valor){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
				if(tokenServicio.existeToken(accessToken)!=null)
				{
					System.out.println("criterio :"+criterio);
					System.out.println("valor :"+valor);
					HashMap<String,Object> lista = new HashMap<String,Object>();
					switch (criterio) 
					{
					case "ee":
						System.out.println("buscando por placa : ");
						lista = cajaCompensacionServicio.filtrarPorEstado(valor);
						return ResponseEntity.ok().body(lista);
					case "ce":
						System.out.println("buscando por placa : ");
						lista = cajaCompensacionServicio.filtrarPorCodigo(valor);
						return ResponseEntity.ok().body(lista);
				default:
					return ResponseEntity.badRequest().body("malo");
				
					}
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
	
	@GetMapping("/fondoPensiones/filtrar")
	public ResponseEntity<?> filtrarFondoPensiones(@RequestParam("accessToken") String accessToken,@RequestParam("criterio") String criterio, @RequestParam("valor") Integer valor){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
				if(tokenServicio.existeToken(accessToken)!=null)
				{
					System.out.println("criterio :"+criterio);
					System.out.println("valor :"+valor);
					HashMap<String,Object> lista = new HashMap<String,Object>();
					switch (criterio) 
					{
					case "ee":
						System.out.println("buscando por placa : ");
						lista = fondoPensionesServicio.filtrarPorEstado(valor);
						return ResponseEntity.ok().body(lista);
					case "ce":
						System.out.println("buscando por placa : ");
						lista = fondoPensionesServicio.filtrarPorCodigo(valor);
						return ResponseEntity.ok().body(lista);
				default:
					return ResponseEntity.badRequest().body("malo");
				
					}
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
	
	@DeleteMapping("/eliminarConductorSistema/{id}")
	public ResponseEntity<?> eliminarConductor(@RequestParam("accessToken") String accessToken, @PathVariable("id") Integer idConductor)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Conductor conductor= new Conductor();
				conductor.setEstadoConductor(3);
				conductor.setIdConductor(idConductor);
				conductor.setPersona(null);
				conductorServicio.actualizar(conductor);
				
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

}

