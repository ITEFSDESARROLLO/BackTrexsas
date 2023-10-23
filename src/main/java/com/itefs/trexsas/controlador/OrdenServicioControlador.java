package com.itefs.trexsas.controlador;

import java.util.ArrayList;
import java.util.Calendar;
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

import com.itefs.trexsas.modelo.OrdenServicio;
import com.itefs.trexsas.modelo.Servicio;
import com.itefs.trexsas.servicio.ArchivoServicio;
import com.itefs.trexsas.servicio.ConductorServicio;
import com.itefs.trexsas.servicio.OrdenServicioServicio;
import com.itefs.trexsas.servicio.ServicioServicio;
import com.itefs.trexsas.servicio.TokenServicio;
import com.itefs.trexsas.utilidades.Correo;
import com.itefs.trexsas.utilidades.Whatsapp;

@RestController
@RequestMapping("/ordenservicio")
public class OrdenServicioControlador {
	
	@Autowired
	private OrdenServicioServicio ordenServicioServicio;
	@Autowired
	private ServicioServicio reservaServicio;
	@Autowired
	private ConductorServicio conductorServicio;
	@Autowired
	private TokenServicio tokenServicio;
	@Autowired
	private ArchivoServicio archivoServicio;
	@Autowired
    private Validator validator;
	@Autowired
	private Correo correo;
	
	
	@PostMapping("/w")
	public ResponseEntity<?> whatsapp(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Whatsapp wsp=new Whatsapp();
				wsp.primerW();
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
	
	@PostMapping("/generar/{id}")
	public ResponseEntity<?> prueba(@PathVariable("id") Long idOS,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				OrdenServicio os=ordenServicioServicio.obtenerPorId(idOS);
				if(os.getOrdenServicio()==null) {
					Calendar c = Calendar.getInstance();
			    	String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			        String nombre=spn+"_os"+".pdf";
					os.setOrdenServicio(nombre);
			        ordenServicioServicio.actualizar(os);
				}
				archivoServicio.crearOrdenServicio(idOS,os.getOrdenServicio());
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
	
	@PostMapping
	public ResponseEntity<?> crear(@RequestParam("accessToken") String accessToken,@RequestBody OrdenServicio ordenServicio){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Set<ConstraintViolation<OrdenServicio>> validate = validator.validate(ordenServicio);
				if(validate.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<OrdenServicio> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				Calendar c = Calendar.getInstance();
		    	String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
		        String nombre=spn+"_os"+".pdf";
		        ordenServicio.setOrdenServicio(nombre);
				ordenServicio.setEstadoOrdenServicio(1);
				ordenServicioServicio.crear(ordenServicio);
				Servicio reserva=reservaServicio.obtenerPorId(ordenServicio.getReserva().getIdReserva());
				List<Object[]>conductor=conductorServicio.correoConductor(ordenServicio.getConductor().getIdConductor());
				
				correo.enviarCorreoNuevaOrdenConductor((String)conductor.get(0)[0], (String)conductor.get(0)[1]+" "+(String)conductor.get(0)[2], reserva.getFechaInicioReserva(), 
						reserva.getDireccionOrigenReserva(), reserva.getDireccionDestinoReserva(), 
						reserva.getCliente().getRazonSocialCliente(),reserva.getPasajero().getPersona().getNombrePersona()+" "+reserva.getPasajero().getPersona().getApellidoPersona());
				response.put("mensaje", 1);
				archivoServicio.crearOrdenServicio(ordenServicio.getIdOrdenServicio(),ordenServicio.getOrdenServicio());
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
	
	@PutMapping("/editar/{mod}")
	public ResponseEntity<?> editar(@RequestParam("accessToken") String accessToken,@RequestBody OrdenServicio ordenServicio, @PathVariable("mod")Long cuenta){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				OrdenServicio orden = ordenServicioServicio.obtenerPorId(ordenServicio.getIdOrdenServicio());
				orden.setConductor(ordenServicio.getConductor());
				orden.setObservacionesOrdenServicio(ordenServicio.getObservacionesOrdenServicio());
				orden.setValorConductorOrdenServicio(ordenServicio.getValorConductorOrdenServicio());
				orden.setValorFacturarOrdenServicio(ordenServicio.getValorFacturarOrdenServicio());
				ordenServicioServicio.actualizar(orden);
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
	
	/*@PutMapping("/estado")
	public ResponseEntity<?> editarEstado(@RequestParam("accessToken") String accessToken,@RequestBody Propietario auxPropietario){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				Propietario propietario= reservaServicio.obtenerPorId(auxPropietario.getIdPropietario());
				propietario.setEstadoPropietario(auxPropietario.getEstadoPropietario());
				propietario.setActualizadoPorPropietario(autor);
				reservaServicio.actualizar(propietario);
				
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
	}*/
	
	@GetMapping
	public ResponseEntity<?> obtenerTodos(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Page<Object[]> page=ordenServicioServicio.listar(pr);
				List<HashMap<String, Object>> obs = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					HashMap<String, Object> ob = new HashMap<String, Object>();
					ob.put("idOrdenServicio", o[0]);
					ob.put("fechaInicioReserva", o[1]);
					ob.put("numeroVoucher", o[2]);
					ob.put("nombrePersona", o[3]);
					ob.put("apellidoPersona", o[4]);
					ob.put("placaVehiculo", o[5]);
					ob.put("valorConductorOrdenServicio", o[6]);
					ob.put("valorFacturarOrdenServicio", o[7]);
					ob.put("ordenServicio", o[8]);
					obs.add(ob);
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("ordenesServicio", obs);
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
	
	@GetMapping("/obtenerOrdenesServicio")
	public ResponseEntity<?> obtenerOrdenes(@RequestParam("accessToken") String accessToken)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Object[]> page=ordenServicioServicio.listarOrdenes();
				List<HashMap<String, Object>> obs = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page) {
					HashMap<String, Object> ob = new HashMap<String, Object>();
					ob.put("idOrdenServicio", o[0]);
					ob.put("fechaInicioReserva", o[1]);
					ob.put("numeroVoucher", o[2]);
					ob.put("nombrePersona", o[3]);
					ob.put("apellidoPersona", o[4]);
					ob.put("placaVehiculo", o[5]);
					ob.put("valorConductorOrdenServicio", o[6]);
					ob.put("valorFacturarOrdenServicio", o[7]);
					ob.put("ordenServicio", o[8]);
					ob.put("pasajero", o[9]+" "+o[10]);
					obs.add(ob);
				}
				response.put("ordenesServicio", obs);
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
	
	@GetMapping("/obtenerOrdenesServicioFecha/{inicio}/{fin}")
	public ResponseEntity<?> obtenerOrdenesFechas(@RequestParam("accessToken") String accessToken, @PathVariable("inicio")String inicio, @PathVariable("fin")String fin, @RequestParam("conductor")String conductor)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Object[]> page=ordenServicioServicio.listarOrdenesFecha(inicio,fin,conductor);
				List<HashMap<String, Object>> obs = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page) 
				{
					HashMap<String, Object> ob = new HashMap<String, Object>();
					ob.put("idOrdenServicio", o[0]);
					ob.put("fechaInicioReserva", o[1]);
					ob.put("numeroVoucher", o[2]);
					ob.put("nombrePersona", o[3]);
					ob.put("apellidoPersona", o[4]);
					ob.put("placaVehiculo", o[5]);
					ob.put("valorConductorOrdenServicio", o[6]);
					ob.put("valorFacturarOrdenServicio", o[7]);
					ob.put("ordenServicio", o[8]);
					obs.add(ob);
				}
				//response.put("totalResultados", page.getTotalElements());
				//response.put("totalPaginas", page.getTotalPages());
				response.put("ordenesServicio", obs);
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
	
	@GetMapping("/filtrarOrdenesServicio/{criterio}/{valor}")
	public ResponseEntity<?> filtrarOrdenesServicio(@RequestParam("accessToken") String accessToken, @RequestParam("inicio")String inicio, @RequestParam("fin")String fin
			, @PathVariable("criterio")String criterio, @PathVariable("valor")String valor)
	{
		System.out.println("en servicio filtro afiliacion");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null)
			{
				System.out.println("criterio :"+criterio);
				System.out.println("valor :"+valor);
				HashMap<String,Object> lista = new HashMap<String,Object>();
				String nombre = "";
				switch (criterio) 
				{
				case "nc":
					System.out.println("buscando por nombre conductor : ");
					nombre = valor.toUpperCase();
					lista = ordenServicioServicio.filtrarOrdenesNombreConductor(inicio, fin, nombre);
					return ResponseEntity.ok().body(lista);
				case "dc":
					System.out.println("buscando por documento conductor : ");
					lista = ordenServicioServicio.filtrarOrdenesDocumentoConductor(inicio, fin, valor);
					return ResponseEntity.ok().body(lista);
					
				case "cs":
					System.out.println("buscando por cliente : ");
					lista = ordenServicioServicio.filtrarOrdenesCliente(inicio, fin, Integer.valueOf(valor));
					return ResponseEntity.ok().body(lista);
					
				case "nps":
					System.out.println("buscando por nombre pasajero : ");
					nombre = valor.toUpperCase();
					lista = ordenServicioServicio.filtrarOrdenesNombrePasajero(inicio, fin, nombre);
					return ResponseEntity.ok().body(lista);
				case "dps":
					System.out.println("buscando por documento pasajero : ");
					lista = ordenServicioServicio.filtrarOrdenesDocumentoPasajero(inicio, fin,valor );
					return ResponseEntity.ok().body(lista);
				case "pvs":
					lista = ordenServicioServicio.filtrarOrdenesPlacaVehiculo(inicio, fin, valor);
					return ResponseEntity.ok(lista);
				case "npps":
					nombre = valor.toUpperCase();
					lista = ordenServicioServicio.filtrarOrdenesNombrePropietario(inicio, fin, nombre);
					return ResponseEntity.ok(lista);
				case "dpps":
					lista = ordenServicioServicio.filtrarOrdenesDocumentoPropietario(inicio, fin, valor);
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
	
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerPorId(@PathVariable("id") Long id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				OrdenServicio ordenServicio=ordenServicioServicio.obtenerPorId(id);
				if(ordenServicio!=null) {
					return ResponseEntity.ok().body(ordenServicio);
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
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarOrdenServicio(@PathVariable("id") Long id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				System.out.println("eliminando orden de servicio");
				ordenServicioServicio.eliminarPorid(id);
				HashMap<String, Object> respuesta = new HashMap<String, Object>();
				respuesta.put("mensaje",1);
				return ResponseEntity.ok().body(respuesta);
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
