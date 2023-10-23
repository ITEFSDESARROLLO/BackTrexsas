package com.itefs.trexsas.controlador;

import java.util.ArrayList;
import java.util.Calendar;
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


import com.itefs.trexsas.modelo.Factura;
import com.itefs.trexsas.servicio.ArchivoServicio;
import com.itefs.trexsas.servicio.ClienteServicio;
import com.itefs.trexsas.servicio.FacturaServicio;
import com.itefs.trexsas.servicio.TokenServicio;
import com.itefs.trexsas.utilidades.Correo;

@RestController
@RequestMapping("/factura")
public class FacturaControlador {
	
	@Autowired
	private FacturaServicio facturaServicio;
	@Autowired
	private ClienteServicio clienteServicio;
	@Autowired
	private ArchivoServicio archivoServicio;
	@Autowired
	private TokenServicio tokenServicio;
	@Autowired
    private Validator validator;
	@Autowired
	private Correo correo;
	
	@PostMapping("/generar/{id}")
	public ResponseEntity<?> prueba(@PathVariable("id") Long idf,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Factura fac=facturaServicio.obtenerPorId(idf);
				if(fac.getFactura()==null) {
					Calendar c = Calendar.getInstance();
			    	String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			        String nombre=spn+"_fac"+".pdf";
					fac.setFactura(nombre);
			        facturaServicio.actualizar(fac);
				}
				archivoServicio.crearFactura(idf,fac.getFactura());
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
	public ResponseEntity<?> crear(@RequestParam("accessToken") String accessToken,@RequestBody Factura factura){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Set<ConstraintViolation<Factura>> validate = validator.validate(factura);
				if(validate.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Factura> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				Calendar c = Calendar.getInstance();
		    	String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
		        String nombre=spn+"_fac"+".pdf";
		        factura.setFactura(nombre);
				factura.setEstadoFactura(1);
				facturaServicio.crear(factura);
				List<Object[]>cliente=clienteServicio.correoCliente(factura.getCliente().getIdCliente());
				correo.enviarCorreoNuevaFacturaCliente((String)cliente.get(0)[0], (String)cliente.get(0)[1], factura.getIdFactura(),
						factura.getTotalFactura() ,factura.getFechaInicioFactura(),factura.getFechaFinFactura(),factura.getFechaFactura(),
						factura.getConceptoFactura());
				archivoServicio.crearFactura(factura.getIdFactura(),nombre);
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
	
	@PutMapping
	public ResponseEntity<?> editar(@RequestParam("accessToken") String accessToken,@RequestBody Factura auxFactura){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				Factura factura=facturaServicio.obtenerPorId(auxFactura.getIdFactura());
				factura.setFechaInicioFactura(auxFactura.getFechaInicioFactura());
				factura.setFechaFinFactura(auxFactura.getFechaFinFactura());
				factura.setFechaFactura(auxFactura.getFechaFactura());
				factura.setTotalFactura(auxFactura.getTotalFactura());
				factura.setConceptoFactura(auxFactura.getConceptoFactura());
				
				Set<ConstraintViolation<Factura>> validate = validator.validate(factura);
				if(validate.size()>0 ) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Factura> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				if(factura.getFactura()==null) {
					Calendar c = Calendar.getInstance();
			    	String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			        String nombre=spn+"_fac"+".pdf";
			        factura.setFactura(nombre);
				}
				facturaServicio.actualizar(factura);
				
				List<Object[]>cliente=clienteServicio.correoCliente(factura.getCliente().getIdCliente());
				correo.enviarCorreoEditarFacturaCliente((String)cliente.get(0)[0], (String)cliente.get(0)[1], factura.getIdFactura(),
						factura.getTotalFactura() ,factura.getFechaInicioFactura(),factura.getFechaFinFactura(),factura.getFechaFactura(),
						factura.getConceptoFactura());
				archivoServicio.crearFactura(factura.getIdFactura(),factura.getFactura());
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
	public ResponseEntity<?> editarEstado(@RequestParam("accessToken") String accessToken,@RequestBody Factura auxFactura){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Factura factura= facturaServicio.obtenerPorId(auxFactura.getIdFactura());
				factura.setEstadoFactura(auxFactura.getEstadoFactura());
				facturaServicio.actualizar(factura);
				
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
				Page<Object[]> page=facturaServicio.listar(pr);
				List<HashMap<String, Object>> obs = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					HashMap<String, Object> ob = new HashMap<String, Object>();
					ob.put("idFactura", o[0]);
					ob.put("fechaFactura", o[1]);
					ob.put("razonSocialCliente", o[2]);
					ob.put("fechaInicioFactura", o[3]);
					ob.put("fechaFinFactura", o[4]);
					ob.put("totalFactura", o[5]);
					ob.put("factura", o[6]);
					obs.add(ob);
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("facturas", obs);
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
	
	@GetMapping("/inforeservascumplidas")
	public ResponseEntity<?> obtenerInfoReservasCumplidas(@RequestParam("accessToken") String accessToken,
			@RequestParam("fechaInicio") String fi,@RequestParam("fechaFinal") String ff,
			@RequestParam("idCliente") Integer idCli){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Long total=facturaServicio.obtenerTotalEntreFechas(fi,ff,idCli);
				System.out.println("total : "+total);
				List<Object[]> conReservas=facturaServicio.obtenerOsyReservasEntreFechas(fi,ff,idCli);
				System.out.println("reservas : "+conReservas.size());
				List<HashMap<String, Object>> obs = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:conReservas) {
					HashMap<String, Object> ob = new HashMap<String, Object>();
					ob.put("idOrdenServicio", o[0]);
					ob.put("fechaInicioReserva", o[1]);
					ob.put("valorFacturar", o[2]);
					ob.put("valorConductor", o[3]);
					ob.put("nombrePasajero", o[4]);
					ob.put("apellidoPasajero", o[5]);
					ob.put("numeroVoucher", o[6]);
					ob.put("nombreConductor", o[7]);
					ob.put("apellidoConductor", o[8]);
					ob.put("placaVehiculo", o[9]);
					ob.put("estadoReserva", o[10]);
					obs.add(ob);
				}
				if(total!=null)
					response.put("total", total);
				else
					response.put("total", 0);
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
				Factura factura=facturaServicio.obtenerPorId(id);
				if(factura!=null) {
					return ResponseEntity.ok().body(factura);
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
				List<HashMap<String, Object>> lista = facturaServicio.listarFacturasFiltradas(0, criterio, valor);
				response.put("facturasFiltradas", lista);
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
