package com.itefs.trexsas.controlador;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itefs.trexsas.modelo.Conductor;
import com.itefs.trexsas.modelo.Cuenta;
import com.itefs.trexsas.modelo.Persona;
import com.itefs.trexsas.modelo.PojoExcel;
import com.itefs.trexsas.modelo.Servicio;
import com.itefs.trexsas.servicio.ArchivoServicio;
import com.itefs.trexsas.servicio.ClienteServicio;
import com.itefs.trexsas.servicio.ConductorServicio;
import com.itefs.trexsas.servicio.CuentaServicio;
import com.itefs.trexsas.servicio.OrdenServicioServicio;
import com.itefs.trexsas.servicio.PasajeroServicio;
import com.itefs.trexsas.servicio.PersonaServicio;
import com.itefs.trexsas.servicio.ServicioServicio;
import com.itefs.trexsas.servicio.TokenServicio;
import com.itefs.trexsas.utilidades.Correo;
import com.itefs.trexsas.utilidades.GeneradorToken;

@RestController
@RequestMapping("/reserva")
public class ServicioControlador {
	
	@Autowired
	private ServicioServicio reservaServicio;
	@Autowired
	private PasajeroServicio pasajeroServicio;
	
	@Autowired
	private PersonaServicio personaServicio;
	
	@Autowired
	private ClienteServicio clienteServicio;;
	@Autowired
	private ConductorServicio conductorServicio;
	@Autowired
	private CuentaServicio cuentaServicio;	
	@Autowired
	private ArchivoServicio archivoServicio;
	@Autowired
	private TokenServicio tokenServicio;
	
	@Autowired
	private OrdenServicioServicio ordenServicioServicio;
	
	@Autowired
    private Validator validator;
	
	@Autowired
	private Correo correo;
	
	@PostMapping("")
	public ResponseEntity<?> crear(@RequestParam("accessToken") String accessToken,@RequestBody Servicio reserva, @RequestParam("modificador")String modificador){
		System.out.println("1 creando");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			System.out.println("2 creando: "+reserva.getFechaInicioReserva());
			//System.out.println("reservas : "reserva.setAccion(modificador);!=null);
			//reserva.setAccion("creación");
			System.out.println("reservas : "+reserva.getCliente()!=null);
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				System.out.println("3 creando");
				Set<ConstraintViolation<Servicio>> validate = validator.validate(reserva);
				if(validate.size()>0) {
					System.out.println("3.1 creando");
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Servicio> item : validate) {
						System.out.println("mensaje : "+item.getPropertyPath());
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				System.out.println("3.2 creando");
				reserva.setEstadoReserva(1);
				reserva.setAccion("creación de reserva");
				Persona persona = personaServicio.obtenerPorId(id);
				reserva.setModificado(persona);
				reserva.setFechaModificado(java.time.LocalDate.now().toString());
				
				Servicio guardado = reservaServicio.crear(reserva);
				List<Object[]>pasajero=pasajeroServicio.correoPasajero(reserva.getPasajero().getIdPasajero());
				List<Object[]>cliente=clienteServicio.correoCliente(reserva.getCliente().getIdCliente());
				//correo.enviarCorreoNuevaReservaCliente((String)cliente.get(0)[0], (String)pasajero.get(0)[1]+" "+(String)pasajero.get(0)[2], reserva.getFechaInicioReserva(), reserva.getDireccionOrigenReserva(), reserva.getDireccionDestinoReserva(), (String)cliente.get(0)[1]);
				//correo.enviarCorreoNuevaReservaPasajero((String)pasajero.get(0)[0], (String)pasajero.get(0)[1]+" "+(String)pasajero.get(0)[2], reserva.getFechaInicioReserva(), reserva.getDireccionOrigenReserva(), reserva.getDireccionDestinoReserva(), (String)cliente.get(0)[1]);
				List<Conductor> listaConductores = conductorServicio.buscarTodos();
				for (Conductor conductor : listaConductores)
				{
					System.out.println("conductor : "+conductor.toString());
				}
				System.out.println("es nulo?");
				System.out.println(reserva.toString());
				System.out.println("es nulo?");
				System.out.println(listaConductores);
				//correo.enviarCorreosServicioNuevo(listaConductores, guardado);
				System.out.println("todo correcto");
				response.put("mensaje", 1);
				return ResponseEntity.ok().body(response);
			}else {
				System.out.println("no todo correcto");
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}/*catch(AddressException ex) {
			ex.printStackTrace();
			System.out.println("1 error");
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}catch(MessagingException ex) {
			ex.printStackTrace();
			System.out.println("2 error");
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}*/catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("3 error +: ");
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping
	public ResponseEntity<?> editar(@RequestParam("accessToken") String accessToken,@RequestBody Servicio auxReserva,@RequestParam("modificador")String modificador){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				Servicio reserva=reservaServicio.obtenerPorId(auxReserva.getIdReserva());
				reserva.setFechaInicioReserva(auxReserva.getFechaInicioReserva());
				reserva.setNumeroVoucher(auxReserva.getNumeroVoucher());
				reserva.setLongitudOrigenReserva(auxReserva.getLongitudOrigenReserva());
				reserva.setLongitudDestinoReserva(auxReserva.getLongitudDestinoReserva());
				reserva.setLatitudOrigenReserva(auxReserva.getLatitudOrigenReserva());
				reserva.setLatitudDestinoReserva(auxReserva.getLatitudDestinoReserva());
				reserva.setDireccionOrigenReserva(auxReserva.getDireccionOrigenReserva());
				reserva.setDireccionDestinoReserva(auxReserva.getDireccionDestinoReserva());
				reserva.setCliente(auxReserva.getCliente());
				reserva.setPasajero(auxReserva.getPasajero());
				reserva.setFechaModificado(java.time.LocalDate.now().toString());
				Persona persona = new Persona();
				persona.setIdPersona(Long.valueOf(modificador));
				reserva.setModificado(persona);
				reserva.setAccion("edición de reserva");
				Set<ConstraintViolation<Servicio>> validate = validator.validate(reserva);
				if(validate.size()>0 ) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Servicio> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				reservaServicio.actualizar(reserva);
				List<Object[]>idOSyIdCdeReserva=reservaServicio.idOSyIdCdeReserva(reserva.getIdReserva());
				List<Object[]>pasajero=pasajeroServicio.correoPasajero(reserva.getPasajero().getIdPasajero());
				List<Object[]>cliente=clienteServicio.correoCliente(reserva.getCliente().getIdCliente());
				correo.enviarCorreoEditarReservaCliente((String)cliente.get(0)[0], (String)pasajero.get(0)[1]+" "+(String)pasajero.get(0)[2], reserva.getFechaInicioReserva(), reserva.getDireccionOrigenReserva(), reserva.getDireccionDestinoReserva(), (String)cliente.get(0)[1]);
				correo.enviarCorreoEditarReservaPasajero((String)pasajero.get(0)[0], (String)pasajero.get(0)[1]+" "+(String)pasajero.get(0)[2], reserva.getFechaInicioReserva(), reserva.getDireccionOrigenReserva(), reserva.getDireccionDestinoReserva(), (String)cliente.get(0)[1]);
				if(idOSyIdCdeReserva.size()>0) {
					List<Object[]>conductor=conductorServicio.correoConductor((int)idOSyIdCdeReserva.get(0)[1]);
					correo.enviarCorreoEditarOrdenConductor((String)conductor.get(0)[0], 
							(String)conductor.get(0)[1]+" "+(String)conductor.get(0)[2], 
							reserva.getFechaInicioReserva(), 
							reserva.getDireccionOrigenReserva(), 
							reserva.getDireccionDestinoReserva(), 
							(String)cliente.get(0)[1],
							(String)pasajero.get(0)[1]+" "+(String)pasajero.get(0)[2]);
					
					archivoServicio.crearOrdenServicio((Long) idOSyIdCdeReserva.get(0)[0],(String)idOSyIdCdeReserva.get(0)[2]);
				}
				
				response.put("mensaje", 1);
				return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch(AddressException ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}catch(MessagingException ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping("/estado")
	public ResponseEntity<?> editarEstado(@RequestParam("accessToken") String accessToken,@RequestBody Servicio auxReserva, @RequestParam("modificador")String modificador){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Servicio reserva= reservaServicio.obtenerPorId(auxReserva.getIdReserva());
				reserva.setFechaModificado(java.time.LocalDate.now().toString());
				Persona persona = new Persona();
				persona.setIdPersona(Long.valueOf(modificador));
				reserva.setModificado(persona);
				reserva.setEstadoReserva(auxReserva.getEstadoReserva());
				reserva.setAccion("edición de reserva");
				reservaServicio.actualizar(reserva);
				if(reserva.getEstadoReserva()==2)
				{
					correo.enviarCorreoCancelacionConductor(reserva.getPasajero().getPersona().getCorreoPersona(), reserva.getFechaInicioReserva(), reserva.getHoraInicioReserva());
				}else if(reserva.getEstadoReserva()==5)
				{
					System.out.println("terminando el viaje");
					Long idOrdenServicio = ordenServicioServicio.obtenerIdOrdenServicio(reserva.getIdReserva());
					System.out.println("orden de servicio : "+idOrdenServicio);
					correo.enviarCorreoTerminacionServicio(reserva.getPasajero().getPersona().getCorreoPersona(), 0,idOrdenServicio);
				}
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
	
	@PutMapping("/cancelar/{id}")
	public ResponseEntity<?> cancelarReserva(@RequestParam("accessToken") String accessToken,@PathVariable("id") Long id, @RequestParam("modificador")String modificador){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long idToken=tokenServicio.existeToken(accessToken);
			if(idToken!=null) {
				Servicio reserva= reservaServicio.obtenerPorId(id);
				reserva.setEstadoReserva(2);
				reserva.setFechaModificado(java.time.LocalDate.now().toString());
				Persona persona = new Persona();
				persona.setIdPersona(Long.valueOf(modificador));
				reserva.setModificado(persona);
				reserva.setAccion("edición de reserva");
				reservaServicio.actualizar(reserva);
				correo.enviarCorreoCancelacionConductor(reserva.getPasajero().getPersona().getCorreoPersona(), reserva.getFechaInicioReserva(), reserva.getHoraInicioReserva());
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
	
	@PutMapping("/terminar/{id}")
	public ResponseEntity<?> terminarReserva(@RequestParam("accessToken") String accessToken,@PathVariable("id") Long id, @RequestParam("modificador")String modificador){
		HashMap<String, Object> response = new HashMap<String, Object>();
		System.out.println("terminando : ");
		try {
			
			Long idToken=tokenServicio.existeToken(accessToken);
			if(idToken!=null) {
				Servicio reserva= reservaServicio.obtenerPorId(id);
				reserva.setEstadoReserva(5);
				reserva.setFechaModificado(java.time.LocalDate.now().toString());
				Persona persona = new Persona();
				persona.setIdPersona(Long.valueOf(modificador));
				reserva.setModificado(persona);
				reserva.setAccion("edición de reserva");
				reservaServicio.actualizar(reserva);
				//correo.enviarCorreoTerminacionServicio(reserva.getPasajero().getPersona().getCorreoPersona(), 0);
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
	public ResponseEntity<?> obtenerTodos(@RequestParam("accessToken") String accessToken){
		GeneradorToken gt = new GeneradorToken();
		gt.generarToken2("sasa");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				
				List<Object[]> page=reservaServicio.listar();
				List<HashMap<String, Object>> obs = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page) {
					System.out.println("esto es "+o[0]);
					HashMap<String, Object> ob = new HashMap<String, Object>();
					ob.put("idReserva", o[0]);
					ob.put("nombrePasajero", o[1]);
					ob.put("apellidoPasajero", o[2]);
					ob.put("fechaInicioReserva", o[3]);
					ob.put("direccionOrigenReserva", o[4]);
					ob.put("direccionDestinoReserva", o[5]);
					ob.put("distanciaReserva", o[6]);
					ob.put("duracionReserva", o[7]);
					ob.put("estadoReserva", o[8]);
					ob.put("nombreConductor", o[9]);
					ob.put("apellidoConductor", o[10]);
					ob.put("placaVehiculo", o[11]);
					ob.put("idOrdenServicio", o[12]);
					ob.put("hora", o[13]);
					ob.put("observaciones", o[14]);
					ob.put("cliente", o[15]);
					obs.add(ob);
				}
				response.put("reservas", obs);
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
	
	@GetMapping("/obtenerPorConductor/{id}")
	public ResponseEntity<?> obtenerTodosConductor(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr,@PathVariable("id")Integer id){
		HashMap<String, Object> response = new HashMap<String, Object>();
		System.out.println("1 : ");
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				System.out.println("2 : ");
				//Long valor = Long.valueOf(id);
				Cuenta cuenta = cuentaServicio.obtenerPorId(id);
				System.out.println("cuenta : "+cuenta.getPersona().getDocumentoPersona());
				Page<Object[]> page=reservaServicio.listarPorConductor(pr,cuenta.getPersona().getDocumentoPersona());
				System.out.println("id recibido : "+page.getSize());
				List<HashMap<String, Object>> obs = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					System.out.println("esto es "+o[0]);
					HashMap<String, Object> ob = new HashMap<String, Object>();
					ob.put("idReserva", o[0]);
					ob.put("nombrePasajero", o[1]);
					ob.put("apellidoPasajero", o[2]);
					ob.put("fechaInicioReserva", o[3]);
					ob.put("direccionOrigenReserva", o[4]);
					ob.put("direccionDestinoReserva", o[5]);
					ob.put("estadoReserva", o[8]);
					ob.put("idOrdenServicio", o[12]);
					obs.add(ob);
				}
				System.out.println("totales : "+obs.size());
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("reservas", obs);
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
	
	@GetMapping("/obtenerReservasDisponibles")
	public ResponseEntity<?> obtenerReservasDisponibles(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		System.out.println("1 : ");
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				System.out.println("2 : ");
				Page<Object[]> page=reservaServicio.listarPorDisponibles(pr);
				System.out.println("id recibido : "+page.getSize());
				List<HashMap<String, Object>> obs = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					System.out.println("esto es "+o[0]);
					HashMap<String, Object> ob = new HashMap<String, Object>();
					ob.put("idReserva", o[0]);
					ob.put("nombrePasajero", o[1]);
					ob.put("apellidoPasajero", o[2]);
					ob.put("fechaInicioReserva", o[3]);
					ob.put("direccionOrigenReserva", o[4]);
					ob.put("direccionDestinoReserva", o[5]);
					ob.put("estadoReserva", o[8]);
					ob.put("idOrdenServicio", o[12]);
					obs.add(ob);
				}
				System.out.println("totales : "+obs.size());
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("reservas", obs);
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
	public ResponseEntity<?> obtenerPorId(@PathVariable("id") Long id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Servicio reserva=reservaServicio.obtenerPorId(id);
				if(reserva!=null)
				{
					return ResponseEntity.ok().body(reserva);
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
	
	/**
	 * Servicio que devuelve las reservas que cumplan con un criterio de búsqueda o filtro
	 * @param accessToken: token generado por el sistema cuando un usuario ingresa
	 * @param criterio : cadena de caracteres que representa el criterio por el cual se va a filtrar la búsqueda de reservas
	 * es - buscar por estado
	 * nv - buscar por número de vouche
	 * fsr - buscar por fecha de solicitud de reserva
	 * dor - buscar por dirección origen reserva
	 * ddr - buscar por dirección destino reserva
	 * idp - buscar por id del pasajero
	 * @return cuerpo Http con todas las reservas que cumplen con el criterio seleccionado
	 */
	
	//@GetMapping("/filtrar/{criterio}/{valor}")
	@GetMapping("/filtrar")
	public ResponseEntity<?> filtrarReservas(@RequestParam("accessToken") String accessToken, @RequestParam("criterio") String criterio, @RequestParam("valor") String valor)
	//public ResponseEntity<?> filtrarReservas(@RequestParam("accessToken") String accessToken, @PathVariable("criterio") String criterio, @PathVariable("valor") String valor)
	{
		System.out.println("en servicio filtro");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null)
			{
				System.out.println("criterio :"+criterio);
				System.out.println("valor :"+valor);
				HashMap<String,Object> lista = new HashMap<String,Object>();
				switch (criterio) 
				{
				case "es":
					int estado = Integer.parseInt(valor);
					lista = reservaServicio.listarPorEstado(0,estado);
					return ResponseEntity.ok().body(lista);
					
				case "nv":
					lista = reservaServicio.listarPorNumeroVoucher(0,valor);
					return ResponseEntity.ok().body(lista);
					
				case "idp":
					lista = reservaServicio.listarPorIdPasajero(0,valor);
					return ResponseEntity.ok().body(lista);
				case "dor":
					lista = reservaServicio.listarPorOrigen(0,valor);
					return ResponseEntity.ok(lista);
				case "ddr":
					lista = reservaServicio.listarPorDestino(0,valor);
					return ResponseEntity.ok(lista);
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
	
	@PostMapping("editarReserva")
	public ResponseEntity<?> editarReserva(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr, @RequestBody Servicio reserva)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				
				Page<Object[]> page=reservaServicio.editarReserva(pr,reserva);
				List<HashMap<String, Object>> obs = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					HashMap<String, Object> ob = new HashMap<String, Object>();
					ob.put("idReserva", o[0]);
					ob.put("nombrePasajero", o[1]);
					ob.put("apellidoPasajero", o[2]);
					ob.put("fechaInicioReserva", o[3]);
					ob.put("direccionOrigenReserva", o[4]);
					ob.put("direccionDestinoReserva", o[5]);
					ob.put("distanciaReserva", o[6]);
					ob.put("duracionReserva", o[7]);
					ob.put("estadoReserva", o[8]);
					ob.put("nombreConductor", o[9]);
					ob.put("apellidoConductor", o[10]);
					ob.put("placaVehiculo", o[11]);
					ob.put("idOrdenServicio", o[12]);
					obs.add(ob);
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("reservas", obs);
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
	
	@GetMapping("/traerDireccionesOrigen")
	public ResponseEntity<?> obtenerDireccionesOrigen(@RequestParam("accessToken") String accessToken)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try 
		{
			if(tokenServicio.existeToken(accessToken)!=null)
			{
				List<String> direccionesOrigen = reservaServicio.listaDireccionesOrigenReservas();
				return ResponseEntity.ok(direccionesOrigen);
			}else
			{
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch (Exception e)
		{
			response.put("error", e.toString());
			return ResponseEntity.badRequest().body(response);
		}
		
	}
	
	@GetMapping("/traerDireccionesDestino")
	public ResponseEntity<?> obtenerDireccionesDestino(@RequestParam("accessToken") String accessToken)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try 
		{
			if(tokenServicio.existeToken(accessToken)!=null)
			{
				List<String> direccionesDestino = reservaServicio.listaDireccionesDestinoReservas();
				return ResponseEntity.ok(direccionesDestino);
			}else
			{
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch (Exception e)
		{
			response.put("error", e.toString());
			return ResponseEntity.badRequest().body(response);
		}
		
	}
	
	@GetMapping("/traerPasajerosContrato/{contrato}")
	public ResponseEntity<?> traerPasajerosContrato(@PathVariable("contrato")Long contrato,@RequestParam("accessToken") String accessToken)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try 
		{
			if(tokenServicio.existeToken(accessToken)!=null)
			{
				response = reservaServicio.listarPacientesContrato(contrato);
				return ResponseEntity.ok(response);
			}else
			{
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch (Exception e)
		{
			response.put("error", e.toString());
			return ResponseEntity.badRequest().body(response);
		}
		
	}
	
	@PostMapping("/cancelarReserva")
	public ResponseEntity<?> cambiarEstadoReserva(@RequestParam("accessToken") String accessToken, @RequestParam("id") long id, @RequestParam("estado") int estadoReserva)
	{
		reservaServicio.cambiarEstadoReserva(id, estadoReserva);
		List<Servicio> listaReservas = reservaServicio.obtenerTodos();
		return ResponseEntity.ok(listaReservas);
	}
	
	
	@PostMapping("/registrarReservasMasivas")
	public ResponseEntity<?> guardarReservasMasivas(@RequestParam("accessToken") String accessToken, @RequestBody Object[] reservas)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		
		
		try 
		{
			
			if(tokenServicio.existeToken(accessToken)!=null)
			{
				
				return ResponseEntity.ok("funcionando");
			}else
			{
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch (Exception e)
		{
			response.put("error", e.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/reservasPasajero")
	public ResponseEntity<?> obtenerReservasPasajero(@RequestParam("accessToken") String accessToken,@RequestParam("usuario")String usuario){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				
				List<Object[]> page=reservaServicio.listarReservasPasajero(usuario);
				List<HashMap<String, Object>> obs = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page) {
					System.out.println("esto es "+o[0]);
					HashMap<String, Object> ob = new HashMap<String, Object>();
					ob.put("idReserva", o[0]);
					ob.put("fechaInicioReserva", o[3]);
					ob.put("direccionOrigenReserva", o[4]);
					ob.put("direccionDestinoReserva", o[5]);
					ob.put("estadoReserva", o[8]);
					ob.put("nombreConductor", o[9]);
					ob.put("apellidoConductor", o[10]);
					ob.put("placaVehiculo", o[11]);
					ob.put("idOrdenServicio", o[12]);
					obs.add(ob);
				}
				response.put("reservas", obs);
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
	
	@PostMapping("/serviciosexcel")
	public ResponseEntity<?> obtenerServiciosExcel(@RequestParam("accessToken") String accessToken,@RequestBody PojoExcel reservas){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				
				List<HashMap<String, Object>> page=reservaServicio.datosExcel(reservas.getDatosServicios());
				
				
				response.put("servicios", page);
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
