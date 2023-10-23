package com.itefs.trexsas.controlador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itefs.trexsas.modelo.Arl;
import com.itefs.trexsas.modelo.CajaCompensacion;
import com.itefs.trexsas.modelo.Conductor;
import com.itefs.trexsas.modelo.Cuenta;
import com.itefs.trexsas.modelo.DocumentoConductor;
import com.itefs.trexsas.modelo.Eps;
import com.itefs.trexsas.modelo.FondoPensiones;
import com.itefs.trexsas.modelo.Licencia;
import com.itefs.trexsas.modelo.OrdenServicio;
import com.itefs.trexsas.modelo.POJOArregloCuenta;
import com.itefs.trexsas.modelo.POJOCuenta;
import com.itefs.trexsas.modelo.Pasajero;
import com.itefs.trexsas.modelo.Perfil;
import com.itefs.trexsas.modelo.Persona;
import com.itefs.trexsas.modelo.PersonaAutor;
import com.itefs.trexsas.modelo.Token;
import com.itefs.trexsas.modelo.UltimoIngresoCuenta;
import com.itefs.trexsas.servicio.ArlServicio;
import com.itefs.trexsas.servicio.CiudadServicio;
import com.itefs.trexsas.servicio.ConductorServicio;
import com.itefs.trexsas.servicio.ContratoServicio;
import com.itefs.trexsas.servicio.CuentaServicio;
import com.itefs.trexsas.servicio.DocumentoConductorServicio;
import com.itefs.trexsas.servicio.EncuestaServicio;
import com.itefs.trexsas.servicio.EpsServicio;
import com.itefs.trexsas.servicio.FuecServicio;
import com.itefs.trexsas.servicio.IdiomaServicio;
import com.itefs.trexsas.servicio.LicenciaServicio;
import com.itefs.trexsas.servicio.OrdenServicioServicio;
import com.itefs.trexsas.servicio.PasajeroServicio;
import com.itefs.trexsas.servicio.PerfilServicio;
import com.itefs.trexsas.servicio.PersonaServicio;
import com.itefs.trexsas.servicio.TokenServicio;
import com.itefs.trexsas.servicio.UltimoIngresoCuentaServicio;
import com.itefs.trexsas.servicio.VehiculoServicio;
import com.itefs.trexsas.utilidades.Cifrador;
import com.itefs.trexsas.utilidades.Correo;
import com.itefs.trexsas.utilidades.GeneradorCredenciales;

@RestController
@RequestMapping("/cuenta")
public class CuentaControlador {
	
	@Autowired
	private CuentaServicio cuentaServicio;
	@Autowired
	private TokenServicio tokenServicio;
	@Autowired
	private UltimoIngresoCuentaServicio ultimoIngresoCuentaServicio;
	@Autowired


	private CiudadServicio ciudadServicio;
	@Autowired
	private PersonaServicio personaServicio;
	@Autowired
	private PerfilServicio perfilServicio;
	@Autowired
	private ConductorServicio conductorServicio;
	@Autowired
	private LicenciaServicio licenciaServicio;
	@Autowired
	private DocumentoConductorServicio documentoConductorServicio;
	@Autowired
	private IdiomaServicio idiomaServicio;
	@Autowired
    private Validator validator;
	@Autowired
	private Correo correo;
	
	@Autowired
	private FuecServicio fuecServicio;
	
	@Autowired
	private OrdenServicioServicio ordenServicioServicio;
	
	@Autowired
	private EncuestaServicio encuestaServicio;
	
	@Autowired 
	private VehiculoServicio vehiculoServicio;
	
	@Autowired
	private PasajeroServicio pasajeroServicio;
	
	@Autowired
	private ContratoServicio contratoService;
	
	
	@PostMapping
	public ResponseEntity<?> crear(@RequestParam("accessToken") String accessToken,@RequestBody Map<String,Object> map,@RequestParam("perfil")Integer perfil){
		System.out.println("llamando :");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				ObjectMapper mapper=new ObjectMapper();
				Cuenta cuenta=mapper.convertValue(map.get("cuenta"), Cuenta.class);
				Persona persona=mapper.convertValue(map.get("persona"), Persona.class);
				Perfil perfil1=new Perfil();
				perfil1.setIdPerfil(perfil);
				
				System.out.println("persona : "+persona.toString());
				Cifrador cifrador=new Cifrador();
				personaServicio.crear(persona);
				Persona autor=new Persona();
				
				GeneradorCredenciales credenciales = new GeneradorCredenciales();
				String usuario = credenciales.generarUsuario(persona.getNombrePersona(), persona.getApellidoPersona());
				String password = credenciales.generarContraseña();
				cuenta.setUsuarioCuenta(usuario);
				autor.setIdPersona(id);
				cuenta.setClaveCuenta(cifrador.cifrar(password));
				cuenta.setPersona(persona);
				cuenta.addPerfil(perfil1);
				cuenta.setRegistradoPorCuenta(autor);
				cuenta.setEstadoCuenta(1);
				cuenta.setFechaRegistroCuenta(String.valueOf(java.time.LocalDate.now()));
				
				Set<ConstraintViolation<Cuenta>> validateCuenta = validator.validate(cuenta);
				Set<ConstraintViolation<Persona>> validatePersona = validator.validate(persona);
				if(validateCuenta.size()>0 || validatePersona.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Cuenta> item : validateCuenta) {
						
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					for (ConstraintViolation<Persona> item : validatePersona) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				
				if(perfil1.getIdPerfil() == 21)
				{
					System.out.println("Es pasajero");
					System.out.println("s");
					PersonaAutor autorP=new PersonaAutor();
					autorP.setIdPersona(autor.getIdPersona());
					Pasajero pasajero = new Pasajero();
					pasajero.setFechaRegistroPasajero(java.time.LocalDateTime.now().toString());
					pasajero.setPersona(persona);
					pasajero.setActualizadoPorPasajero(autorP);
					pasajero.setEstadoPasajero(1);
					pasajeroServicio.crear(pasajero);
					
					System.out.println("pasajero creadop");
				}
				
				cuentaServicio.crear(cuenta);
				if(cuenta.getPerfilList().get(0).getIdPerfil()==5 || cuenta.getPerfilList().get(0).getIdPerfil()==1 || cuenta.getPerfilList().get(0).getIdPerfil()==12)
				{
					correo.enviarCorreoNuevoUsuario(usuario, password, persona.getCorreoPersona());
				}
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
	
	@PostMapping("crearCuentaConductor/{perfil}")
	public ResponseEntity<?> crearCuentaConductor(@RequestParam("accessToken") String accessToken,@RequestBody Map<String,Object> map,@RequestParam("perfil")Integer perfil){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				ObjectMapper mapper=new ObjectMapper();
				//1A
				Cuenta cuenta=mapper.convertValue(map.get("cuenta"), Cuenta.class);
				//2A
				Persona persona=mapper.convertValue(map.get("persona"), Persona.class);
				//3A
				Perfil perfil1=new Perfil();
				perfil1.setIdPerfil(perfil);
				//3B
				Conductor conductor=mapper.convertValue(map.get("conductor"), Conductor.class);
				
				
				if(conductor.getArl().getIdArl() != null)
				{	System.out.println("con arl");
					String inicioArl = conductor.getInicioArlConductor()==""?"0000-00-00":conductor.getInicioArlConductor();
					conductor.setInicioArlConductor(inicioArl);
					String finArl = conductor.getFinArlConductor()==""?"0000-00-00":conductor.getFinArlConductor();
					conductor.setFinArlConductor(finArl);
				}else
				{
					System.out.println("sin arl");
					conductor.setInicioArlConductor(null);
					conductor.setFinArlConductor(null);
					conductor.setArl(null);
				}
				
				if(conductor.getEps().getIdEps() !=null)
				{
					System.out.println("con eps");
					String inicioEps = conductor.getInicioEpsConductor()==""?"0000-00-00":conductor.getInicioEpsConductor();
					conductor.setInicioEpsConductor(inicioEps);
					String finEps = conductor.getFinEpsConductor()==""?"0000-00-00":conductor.getFinEpsConductor();
					conductor.setFinEpsConductor(finEps);
				}else
				{
					System.out.println("sin eps");
					conductor.setFinEpsConductor(null);
					conductor.setInicioEpsConductor(null);
					conductor.setEps(null);
				}
				
				if(conductor.getCajaCompensacion().getIdCajaCompensacion()==null)
				{
					conductor.setCajaCompensacion(null);
				}
				
				if(conductor.getFondoPensiones().getIdFondoPensiones()==null)
				{
					conductor.setFondoPensiones(null);
				}
				
				
				
				
				//4B
				Licencia licencia=mapper.convertValue(map.get("licencia"), Licencia.class);
				
				
				
				//4A
				Persona personaGuardada = personaServicio.crear(persona);
				System.out.println("persona creada : "+personaGuardada);
				personaGuardada.toString();
				Persona autor=new Persona();
				//5A 6A
				Cifrador cifrador=new Cifrador();
				GeneradorCredenciales credenciales = new GeneradorCredenciales();
				String usuario = credenciales.generarUsuario(persona.getNombrePersona(), persona.getApellidoPersona());
				String password = credenciales.generarContraseña();
				//7A 8A
				cuenta.setUsuarioCuenta(usuario);
				cuenta.setClaveCuenta(cifrador.cifrar(password));
				//9A
				autor.setIdPersona(id);
				//10A
				cuenta.setPersona(personaGuardada);
				//11A
				cuenta.addPerfil(perfil1);
				//12A
				cuenta.setRegistradoPorCuenta(autor);
				//13A
				cuenta.setEstadoCuenta(1);
				cuenta.setFechaRegistroCuenta(String.valueOf(java.time.LocalDate.now()));
				//14A 15A
				Set<ConstraintViolation<Cuenta>> validateCuenta = validator.validate(cuenta);
				Set<ConstraintViolation<Persona>> validatePersona = validator.validate(persona);
				if(validateCuenta.size()>0 || validatePersona.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Cuenta> item : validateCuenta) {
						
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					for (ConstraintViolation<Persona> item : validatePersona) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				//16A
				cuentaServicio.crear(cuenta);
				//7B 8B
				Set<ConstraintViolation<Conductor>> validate = validator.validate(conductor);
				Set<ConstraintViolation<Licencia>> validateLicencia = validator.validate(licencia);
				if(validateLicencia.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Licencia> item : validateLicencia) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				//10B
				
				
				conductor.setPersona(personaGuardada);
				conductor.setEstadoConductor(1);
				Conductor guardado = conductorServicio.crearConductor(conductor);
				//11B
				licencia.setConductor(guardado);
				//12B
				licencia.setNumeroLicencia(persona.getDocumentoPersona());
				licenciaServicio.crear(licencia);
				//13B
				DocumentoConductor documentoConductor=new DocumentoConductor();
				documentoConductor.setConductor(guardado);
				//15B
				documentoConductorServicio.crear(documentoConductor);
				correo.enviarCorreoNuevoUsuario(usuario, password, persona.getCorreoPersona());
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
	
	@PostMapping("/recuperacionclave")
	public ResponseEntity<?> recuperarClave(@RequestBody Map<String, String> map){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			String accessToken=map.get("accessToken");
			String clave=map.get("password");
			Token token = tokenServicio.obtenerPorToken(accessToken);
			if(token==null) {
				response.put("error", 2);
				return ResponseEntity.ok().body(response);
			}
			Cifrador cifrador=new Cifrador();
			token.getCuenta().setClaveCuenta(cifrador.cifrar(clave));;
			tokenServicio.actualizarToken(token);
			tokenServicio.eliminarToken(token);
			response.put("mensaje", 1);
			return ResponseEntity.ok().body(response);
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PostMapping("/perfil")
	public ResponseEntity<?> agregarPefil(@RequestBody Map<String, String> map,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		System.out.println("colocando perfil");
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Integer idCuenta=Integer.parseInt(map.get("idCuenta"));
				System.out.println("colocando perfil : "+idCuenta);
				Integer idPerfil=Integer.parseInt(map.get("idPerfil"));
				System.out.println("colocando perfil : "+idPerfil);
				Cuenta cuenta=cuentaServicio.obtenerPorId(idCuenta);
				Perfil perfil=new Perfil();
				perfil.setIdPerfil(idPerfil);
				cuenta.addPerfil(perfil);
				if(idPerfil == 21)
				{
					System.out.println("es pasajero, creando perfil : ");
					Pasajero pasajero = new Pasajero();
					pasajero.setEstadoPasajero(1);
					pasajero.setPersona(cuenta.getPersona());
					pasajero.setFechaRegistroPasajero(String.valueOf(java.time.LocalDate.now()));
					pasajeroServicio.crear(pasajero);
					cuentaServicio.actualizar(cuenta);
					response.put("mensaje", 1);
					return ResponseEntity.ok().body(response);
				}
				cuentaServicio.actualizar(cuenta);
				response.put("mensaje", 1);
				return ResponseEntity.ok().body(response);
				
			}
			response.put("mensaje", 2);
			return ResponseEntity.ok().body(response);
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
				Page<Object[]> page=cuentaServicio.listar(pr);
				List<HashMap<String, Object>> usuarios = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					HashMap<String, Object> cuenta = new HashMap<String, Object>();
					List<Object[]> perfilList=perfilServicio.obtenerBasicoPorIdCuenta((int)o[0]);
					List<HashMap<String, Object>> perfiles = new ArrayList<HashMap<String, Object>>();
					for(Object[] p:perfilList)
					{
						HashMap<String, Object> perfil = new HashMap<String, Object>();
						perfil.put("idPerfil", p[0]);
						perfil.put("nombrePerfil", p[1]);
						perfiles.add(perfil);
					}
					cuenta.put("idCuenta", o[0]);
					cuenta.put("documentoPersona", o[1]);
					cuenta.put("nombrePersona", o[2]);
					cuenta.put("apellidoPersona", o[3]);
					cuenta.put("usuarioCuenta", o[4]);
					cuenta.put("celularUnoPersona", o[5]);
					cuenta.put("estadoCuenta", o[6]);
					cuenta.put("correoPersona", o[7]);
					cuenta.put("perfilList", perfiles);
					usuarios.add(cuenta);
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("usuarios", usuarios);
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
	
	@GetMapping("/usuariosFiltro")
	public ResponseEntity<?> obtenerTodosFiltro(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Object[]> page=cuentaServicio.listarTodos();
				List<HashMap<String, Object>> usuarios = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page) {
					HashMap<String, Object> cuenta = new HashMap<String, Object>();
					List<Object[]> perfilList=perfilServicio.obtenerBasicoPorIdCuenta((int)o[0]);
					List<HashMap<String, Object>> perfiles = new ArrayList<HashMap<String, Object>>();
					for(Object[] p:perfilList)
					{
						HashMap<String, Object> perfil = new HashMap<String, Object>();
						perfil.put("idPerfil", p[0]);
						perfil.put("nombrePerfil", p[1]);
						perfiles.add(perfil);
					}
					cuenta.put("idCuenta", o[0]);
					cuenta.put("documentoPersona", o[1]);
					cuenta.put("nombrePersona", o[2]);
					cuenta.put("apellidoPersona", o[3]);
					cuenta.put("usuarioCuenta", o[4]);
					cuenta.put("celularUnoPersona", o[5]);
					cuenta.put("estadoCuenta", o[6]);
					cuenta.put("correoPersona", o[7]);
					cuenta.put("perfilList", perfiles);
					usuarios.add(cuenta);
				}
				response.put("usuarios", usuarios);
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
		boolean esConductor = false;
		System.out.println("trayendo cuenta : "+id);
		try {
			if(tokenServicio.existeToken(accessToken)!=null) 
			{
				Cuenta cuenta=cuentaServicio.obtenerPorId(id);
				cuenta.setClaveCuenta(null);
				List<Object[]> perfilList=perfilServicio.obtenerBasicoPorIdCuenta(cuenta.getIdCuenta());
				List<HashMap<String, Object>> perfiles = new ArrayList<HashMap<String, Object>>();
				for(Object[] p:perfilList) {
					HashMap<String, Object> perfil = new HashMap<String, Object>();
					perfil.put("idPerfil", p[0]);
					perfil.put("nombrePerfil", p[1]);
					perfil.put("observacionesPerfil", p[2]);
					perfiles.add(perfil);
					if((Integer)p[0]==2)
					{
						System.out.println("el usuario es conductor");
						esConductor = true;
					}
				}
				if(esConductor)
				{
					System.out.println("guardando conductor");
					Conductor conductor = conductorServicio.obtenerConductorPorPersona(Long.valueOf(cuenta.getPersona().getIdPersona()));
					response.put("conductor", conductor);
				}
				
				response.put("cuenta", cuenta);
				response.put("perfilList", perfiles);
				
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
	
	@GetMapping("/traerCuenta/{id}")
	public ResponseEntity<?> traerCuenta(@PathVariable("id")Integer id, @RequestParam("accessToken")String accessToken)
	{
		return null;
	}
	
	@GetMapping("persona/{id}")
	public ResponseEntity<?> obtenerDatosUsuarioPorIdPersona(@PathVariable("id") Long id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Persona persona=personaServicio.obtenerPorId(id);
				if(persona!=null && persona.getCuenta()!=null) {
					HashMap<String, Object> cuenta = new HashMap<String, Object>();
					List<Object[]> consulta=cuentaServicio.obtenerBasicoPorIdPersona(id);
					cuenta.put("idCuenta", consulta.get(0)[0]);
					cuenta.put("usuarioCuenta", consulta.get(0)[1]);
					cuenta.put("estadoCuenta", consulta.get(0)[2]);
					cuenta.put("registradoPorCuenta", consulta.get(0)[3]);
					cuenta.put("fechaRegistroCuenta", consulta.get(0)[4]);
					cuenta.put("fechaActualizacionCuenta", consulta.get(0)[6]);
					cuenta.put("actualizadoPorCuenta", consulta.get(0)[5]);
					response.put("persona", persona);
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
	
	@GetMapping("cedula")
	public ResponseEntity<?> obtenerCedulas(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<String> page=cuentaServicio.obtenerCedulas();
				List<HashMap<String, Object>> objetos = new ArrayList<HashMap<String, Object>>();
				for(String o:page) {
					HashMap<String, Object> vehiculo = new HashMap<String, Object>();
					vehiculo.put("documentoPersona", o);
					objetos.add(vehiculo);
				}
				return ResponseEntity.ok().body(objetos);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("usuariocuenta")
	public ResponseEntity<?> obtenerUsernamesEmpiecenPor(@RequestParam("palabra") String palabra,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<String> list=cuentaServicio.usuariosEmpiecenPor(palabra);
				List<HashMap<String, Object>> usuarios = new ArrayList<HashMap<String, Object>>();
				for(String o:list) {
					HashMap<String, Object> usuario = new HashMap<String, Object>();
					usuario.put("usuarioCuenta", o);
					usuarios.add(usuario);
				}
				return ResponseEntity.ok().body(usuarios);
				
				
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/nombre")
	public ResponseEntity<?> nombreusuario(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Object[]> list=cuentaServicio.nombreDeToken(accessToken);
				response.put("nombrePersona", list.get(0)[0]);
				response.put("apellidoPersona", list.get(0)[1]);
				response.put("documentoPersona", list.get(0)[2]);
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
	public ResponseEntity<?> editar(@RequestParam("accessToken") String accessToken,@RequestBody Cuenta auxCuenta){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				Cuenta cuenta=cuentaServicio.obtenerPorId(auxCuenta.getIdCuenta());
				
				Persona autor=new Persona();
				autor.setIdPersona(id);
				//cuenta.setActualizadoPorCuenta(autor);
				cuenta.setUsuarioCuenta(auxCuenta.getUsuarioCuenta());
				cuenta.setEstadoCuenta(auxCuenta.getEstadoCuenta());
				cuenta.getPersona().setNombrePersona(auxCuenta.getPersona().getNombrePersona());
				cuenta.getPersona().setApellidoPersona(auxCuenta.getPersona().getApellidoPersona());
				cuenta.getPersona().setTipoDocPersona(auxCuenta.getPersona().getTipoDocPersona());
				cuenta.getPersona().setDocumentoPersona(auxCuenta.getPersona().getDocumentoPersona());
			//	cuenta.getPersona().setCiudadPersona(auxCuenta.getPersona().getCiudadPersona());
				cuenta.getPersona().setDireccionPersona(auxCuenta.getPersona().getDireccionPersona());
				cuenta.getPersona().setObservacionesPersona(auxCuenta.getPersona().getObservacionesPersona());
				cuenta.getPersona().setFechaNacimientoPersona(auxCuenta.getPersona().getFechaNacimientoPersona());
				cuenta.getPersona().setCorreoPersona(auxCuenta.getPersona().getCorreoPersona());
				cuenta.getPersona().setCelularUnoPersona(auxCuenta.getPersona().getCelularUnoPersona());
				cuenta.getPersona().setCelularDosPersona(auxCuenta.getPersona().getCelularDosPersona());
				cuenta.getPersona().setTelefonoPersona(auxCuenta.getPersona().getTelefonoPersona());
			//	cuenta.getPersona().setCiudadExpedicionPersona(auxCuenta.getPersona().getCiudadExpedicionPersona());
				cuenta.getPersona().setFotoPersona(auxCuenta.getPersona().getFotoPersona());
				cuenta.getPersona().setTipoPersona(auxCuenta.getPersona().getTipoPersona());
				cuenta.setFechaActualizacionCuenta(String.valueOf(java.time.LocalDate.now()));
				Persona persona=cuenta.getPersona();
				Set<ConstraintViolation<Cuenta>> validateCuenta = validator.validate(cuenta);
				Set<ConstraintViolation<Persona>> validatePersona = validator.validate(persona);
				if(validateCuenta.size()>0 || validatePersona.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Cuenta> item : validateCuenta) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					for (ConstraintViolation<Persona> item : validatePersona) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				if(auxCuenta.getClaveCuenta()!=null) {
					Cifrador cifrador=new Cifrador();
					cuenta.setClaveCuenta(cifrador.cifrar(auxCuenta.getClaveCuenta()));
				}
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
	
	@PutMapping("/editarCuentaConductor")
	public ResponseEntity<?> editarCuentaConductor(@RequestParam("accessToken") String accessToken,@RequestBody Map<String,Object> map){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				ObjectMapper mapper = new ObjectMapper();
				Cuenta cuentaMap = mapper.convertValue(map.get("cuenta"), Cuenta.class);	
				Conductor conductorMap=mapper.convertValue(map.get("conductor"), Conductor.class);
				Licencia licenciaMap=mapper.convertValue(map.get("licencia"), Licencia.class);
				System.out.println("cuentaMap recibida : "+cuentaMap.toString());
				Cuenta cuenta=cuentaServicio.obtenerPorId(cuentaMap.getIdCuenta());
				System.out.println("cuenta recibida : "+cuenta.toString());
				Persona personaMap = mapper.convertValue(map.get("persona"), Persona.class);
				Persona autor=new Persona();
				Date fecha = new Date();
				LocalDate añoActual = LocalDate.now();
		        String fechaActualizacion = añoActual+" "+fecha.getHours()+":"+fecha.getMinutes()+":"+fecha.getSeconds();
		        System.out.println("fecha en la que se actualiza: "+fechaActualizacion);
		        cuenta.setFechaActualizacionCuenta(fechaActualizacion);
		        autor.setIdPersona(id);
				cuenta.setActualizadoPorCuenta(autor);
				cuenta.setUsuarioCuenta(cuentaMap.getUsuarioCuenta());
				cuenta.setEstadoCuenta(cuentaMap.getEstadoCuenta());
				cuenta.getPersona().setNombrePersona(cuentaMap.getPersona().getNombrePersona());
				cuenta.getPersona().setApellidoPersona(cuentaMap.getPersona().getApellidoPersona());
				cuenta.getPersona().setTipoDocPersona(cuentaMap.getPersona().getTipoDocPersona());
				cuenta.getPersona().setDocumentoPersona(cuentaMap.getPersona().getDocumentoPersona());
				//cuenta.getPersona().setCiudadPersona(cuentaMap.getPersona().getCiudadPersona());
				cuenta.getPersona().setDireccionPersona(cuentaMap.getPersona().getDireccionPersona());
				cuenta.getPersona().setObservacionesPersona(cuentaMap.getPersona().getObservacionesPersona());
				cuenta.getPersona().setFechaNacimientoPersona(cuentaMap.getPersona().getFechaNacimientoPersona());
				cuenta.getPersona().setCorreoPersona(cuentaMap.getPersona().getCorreoPersona());
				cuenta.getPersona().setCelularUnoPersona(cuentaMap.getPersona().getCelularUnoPersona());
				cuenta.getPersona().setCelularDosPersona(cuentaMap.getPersona().getCelularDosPersona());
				cuenta.getPersona().setTelefonoPersona(cuentaMap.getPersona().getTelefonoPersona());
				//cuenta.getPersona().setCiudadExpedicionPersona(cuentaMap.getPersona().getCiudadExpedicionPersona());
				cuenta.getPersona().setFotoPersona(cuentaMap.getPersona().getFotoPersona());
				cuenta.getPersona().setCuenta(cuentaMap);
				System.out.println("idcuentamap"+cuentaMap);
				Conductor conductor=conductorServicio.obtenerPorId(conductorMap.getIdConductor());
				conductor.setRhConductor(conductorMap.getRhConductor());
				conductor.setInicioEpsConductor(conductorMap.getInicioEpsConductor());
				conductor.setFinEpsConductor(conductorMap.getFinEpsConductor());
				conductor.setInicioArlConductor(conductorMap.getInicioArlConductor());
				conductor.setFinArlConductor(conductorMap.getFinArlConductor());
				conductor.setGeneroConductor(conductorMap.getGeneroConductor());
				conductor.setCajaCompensacion(conductorMap.getCajaCompensacion());
				conductor.setFondoPensiones(conductorMap.getFondoPensiones());
				conductor.setExamenesMedicosConductor(conductorMap.getExamenesMedicosConductor());
				conductor.setPlanillaAportesConductor(conductorMap.getPlanillaAportesConductor());
				conductor.setArl(conductorMap.getArl());
				conductor.setEps(conductorMap.getEps());
				conductor.setEstadoCivil(conductorMap.getEstadoCivil());
				System.out.println("autor : "+autor.getNombrePersona());
				conductor.setActualizadoPorConductor(autor);
				Licencia licencia=licenciaServicio.obtenerPorId(licenciaMap.getIdLicencia());
				licencia.setCategoriaLicencia(licenciaMap.getCategoriaLicencia());
				licencia.setFechaExpedicionLicencia(licenciaMap.getFechaExpedicionLicencia());
				licencia.setFechaVencimientoLicencia(licenciaMap.getFechaVencimientoLicencia());
				licencia.setDocumentoUnoLicencia(licenciaMap.getDocumentoUnoLicencia());
				Persona persona=cuenta.getPersona();
				Set<ConstraintViolation<Cuenta>> validateCuenta = validator.validate(cuenta);
				Set<ConstraintViolation<Persona>> validatePersona = validator.validate(persona);
				if(validateCuenta.size()>0 || validatePersona.size()>0) {
					System.out.println("problemas 1");
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Cuenta> item : validateCuenta) {
						System.out.println("problemas 2");
						HashMap<String, Object> responseError = new HashMap<String, Object>();
						System.out.println("problema puntual : "+item.getMessage());
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					for (ConstraintViolation<Persona> item : validatePersona) {
						System.out.println("problemas 3");
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				if(cuentaMap.getClaveCuenta()!=null) {
					Cifrador cifrador=new Cifrador();
					cuenta.setClaveCuenta(cifrador.cifrar(cuentaMap.getClaveCuenta()));
				}
				Set<ConstraintViolation<Conductor>> validateConductor = validator.validate(conductor);
				Set<ConstraintViolation<Licencia>> validateLicencia = validator.validate(licencia);
				if(validateConductor.size()>0 || validateLicencia.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					System.out.println("error revise pues");
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
				System.out.println("cuenta recibida : "+cuenta.toString());
				System.out.println("cuenta recibida : "+cuenta);
				cuentaServicio.actualizar(cuenta);
				System.out.println("cuenta recibida : "+cuenta.toString());
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
	public ResponseEntity<?> editarEstado(@RequestParam("accessToken") String accessToken,@RequestBody Cuenta auxCuenta){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Cuenta cuenta=cuentaServicio.obtenerPorId(auxCuenta.getIdCuenta());
				Persona autor=new Persona();
				autor.setIdPersona(id);
				cuenta.setActualizadoPorCuenta(autor);
				cuenta.setEstadoCuenta(auxCuenta.getEstadoCuenta());
				cuentaServicio.actualizar(cuenta);
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
	
	@DeleteMapping("/perfil/{idCuenta}/{idPerfil}")
	public ResponseEntity<?> quitarPerfil(@PathVariable("idCuenta") int idCuenta,@PathVariable("idPerfil") int idPerfil,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Cuenta cuenta=cuentaServicio.obtenerPorId(idCuenta);
				Perfil perfil=perfilServicio.obtenerPorId(idPerfil);
				if(idPerfil==2)
				{
					System.out.println("es conductor");
					System.out.println("1");
					Conductor conductor = conductorServicio.obtenerConductorPorPersona(cuenta.getPersona().getIdPersona());
					System.out.println("2");
					documentoConductorServicio.eliminarPorConductor(conductor.getIdConductor());
					System.out.println("3");
					fuecServicio.eliminarConductorFuec(conductor.getIdConductor());
					System.out.println("4");
					licenciaServicio.eliminar(conductor.getIdConductor());
					System.out.println("5");
					ordenServicioServicio.eliminar(conductor.getIdConductor());
					System.out.println("6");
					encuestaServicio.eliminarRespuesta(conductor.getIdConductor());
					System.out.println("7");
					vehiculoServicio.eliminar(conductor.getIdConductor());
					conductor.setPersona(null);
					conductorServicio.eliminar(conductor);
				}else if(idPerfil == 21)
				{
					System.out.println("es pasajero");
					System.out.println("1");
					Pasajero pasajero = pasajeroServicio.traerPasajeroPorPersona(cuenta.getPersona().getIdPersona());
					System.out.println("2");
					contratoService.eliminarPasajeroContrato(pasajero.getIdPasajero());
					System.out.println("3");
					pasajeroServicio.eliminarPasajero(pasajero);
				}
				cuenta.removePerfil(perfil);
				cuentaServicio.actualizar(cuenta);
				cuentaServicio.quitarPerfil(idCuenta, idPerfil);
				
				response.put("mensaje", 1);
				return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			ex.getLocalizedMessage();
			ex.printStackTrace();
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	//idioma
	@GetMapping("/idioma")
	public ResponseEntity<?> obtenerTodosIdioma(){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			return ResponseEntity.ok().body(idiomaServicio.obtenerTodos());
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	//ciudad
	@GetMapping("/ciudad")
	public ResponseEntity<?> obtenerTodosCiudad(){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			return ResponseEntity.ok().body(ciudadServicio.obtenerTodosCiudad());
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping("/ultimoingreso/listar")
	public ResponseEntity<?> listarEps(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Page<Object[]> page=ultimoIngresoCuentaServicio.listar(pr);
				List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					HashMap<String, Object> objeto = new HashMap<String, Object>();
					objeto.put("idUltimoIngresoCuenta", o[0]);
					objeto.put("nombrePersona", o[1]);
					objeto.put("apellidoPersona", o[2]);
					objeto.put("ipUltimoIngresoCuenta", o[3]);
					objeto.put("fechaUltimoIngresoCuenta", o[4]);
					lista.add(objeto);
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("ultimosIngresos", lista);
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
	
	@GetMapping("/ultimoingreso/{id}")
	public ResponseEntity<?> obteneEpsPorId(@PathVariable("id") long id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				UltimoIngresoCuenta ob=ultimoIngresoCuentaServicio.obtenerPorId(id);
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
	
	@GetMapping("/filtrar")
	public ResponseEntity<?> filtrarCuentasUsuario(@RequestParam("accessToken") String accessToken, @RequestParam("criterio") String criterio, @RequestParam("valor") String valor)
	//public ResponseEntity<?> filtrarReservas(@RequestParam("accessToken") String accessToken, @PathVariable("criterio") String criterio, @PathVariable("valor") String valor)
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
					case "du":
						System.out.println("buscando por documento usuario : ");
						lista = cuentaServicio.filtrarCuentaPorDocumento(valor);
						return ResponseEntity.ok().body(lista);
					case "cu":
						System.out.println("buscando por correo usuario : ");
						lista = cuentaServicio.filtrarCuentaPorCorreo(valor);
						return ResponseEntity.ok().body(lista);
					case "cus":
						System.out.println("buscando por cuenta : ");
						lista = cuentaServicio.filtrarCuentaPorCuenta(valor);
						return ResponseEntity.ok().body(lista);
					case "ec":
						System.out.println("buscando por estado cuenta : ");
						lista = cuentaServicio.filtrarCuentaPorEstado(Integer.valueOf(valor));
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
	
	@PostMapping("/crearConductoresMasivo")
	public ResponseEntity<?> crearConductoresCuenta(@RequestParam("accessToken") String accessToken,@RequestBody POJOArregloCuenta arreglo){
		System.out.println("llamando :");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				cuentaServicio.crearCuentasMasivas(arreglo.getArreglo(), id);
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
	
	@GetMapping("/cambiarContraseña/{usuario}/{cuenta}")
	public ResponseEntity<?> cambiarContraseñaUsuario(@RequestParam("accessToken")String accessToken,@PathVariable("usuario")Long usuario,
			@PathVariable("cuenta")Integer cuenta){
		System.out.println("llamando :");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				Cuenta cuentaUsuario = cuentaServicio.obtenerPorId(cuenta);
				GeneradorCredenciales generador = new GeneradorCredenciales();
				String nuevaContraseña = generador.generarContraseña();
				Cifrador cifrador = new Cifrador();
				String contraseñaCifrada = cifrador.cifrar(nuevaContraseña);
				cuentaUsuario.setClaveCuenta(contraseñaCifrada);
				cuentaServicio.actualizar(cuentaUsuario);
				Persona persona = personaServicio.obtenerPorId(usuario);
				correo.enviarCorreoNuevaContraseña(nuevaContraseña,persona.getCorreoPersona());
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
