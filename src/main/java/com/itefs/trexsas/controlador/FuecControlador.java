package com.itefs.trexsas.controlador;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
import com.itefs.trexsas.modelo.Contrato;
import com.itefs.trexsas.modelo.Fuec;
import com.itefs.trexsas.modelo.Pasajero;
import com.itefs.trexsas.modelo.PersonaAutor;
import com.itefs.trexsas.modelo.Vehiculo;
import com.itefs.trexsas.servicio.ArchivoServicio;
import com.itefs.trexsas.servicio.ConductorServicio;
import com.itefs.trexsas.servicio.ContratoServicio;
import com.itefs.trexsas.servicio.FuecServicio;
import com.itefs.trexsas.servicio.PasajeroServicio;
import com.itefs.trexsas.servicio.TokenServicio;
import com.itefs.trexsas.servicio.VehiculoServicio;

@RestController
@RequestMapping("/fuec")
public class FuecControlador {
	
	@Autowired
	private ConductorServicio conductorServicio;
	@Autowired
	private ContratoServicio contratoServicio;
	@Autowired
	private FuecServicio fuecServicio;
	@Autowired
	private ArchivoServicio archivoServicio;
	@Autowired
	private TokenServicio tokenServicio;
	@Autowired
	private PasajeroServicio pasajeroServicio;
	@Autowired
	private VehiculoServicio vehiculoServicio;
	
	@Autowired
    private Validator validator;
	
	
	
	@PostMapping("/generar/{id}")
	public ResponseEntity<?> prueba(@PathVariable("id") Long idF,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Fuec fuec=fuecServicio.traerPorId(id);
				if(fuec.getFuec()==null) {
					Calendar c = Calendar.getInstance();
			    	String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			        String nombre=spn+"_fuec"+".pdf";
			        fuec.setFuec(nombre);
			        fuecServicio.actualizar(fuec);
				}
				//archivoServicio.crearFuec(idF,fuec.getFuec());
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
	
	@PostMapping()
	public ResponseEntity<?> crear(@RequestParam("accessToken") String accessToken,@RequestBody Fuec fuec){
		HashMap<String, Object> response = new HashMap<String, Object>();
		System.out.println("guardando");
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				System.out.println("obejto recibido : "+fuec.toString());
				autor.setIdPersona(id);
				Contrato contrato = contratoServicio.obtenerPorId(fuec.getContrato().getIdContrato());
				contrato.setConsecutivoContrato(contrato.getConsecutivoContrato()+1);
				contrato.setContadorContrato(contrato.getContadorContrato()+1);
				contratoServicio.actualizar(contrato);
				Calendar c = Calendar.getInstance();
		    	String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
		        String nombre=spn+"_fuec"+".pdf";
				Date date = new Date();
		        ZoneId timeZone = ZoneId.systemDefault();
		        LocalDate getLocalDate = date.toInstant().atZone(timeZone).toLocalDate();
		        System.out.println("valor recibido para codigo consulta : "+"213011516"+getLocalDate.getYear()+contrato.getConsecutivoContrato());
				fuec.setCodigoFuec("213011516"+getLocalDate.getYear()+contrato.getConsecutivoContrato());
				fuec.setEstadoFuec(1);
				fuec.setFuec(null);
				System.out.println(fuec.getVehiculo()==null);
				fuec.setCupoDisponible(fuec.getVehiculo().getNumeroPasajerosVehiculo() - fuec.getPasajeroList().size());
				fuec.setCupoMaximo(fuec.getVehiculo().getNumeroPasajerosVehiculo());
				Set<ConstraintViolation<Fuec>> validateFuec = validator.validate(fuec);
				if(validateFuec.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					
					for (ConstraintViolation<Fuec> item : validateFuec) {
						System.out.println("error : "+item.getMessage());
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				System.out.println("fuec : "+fuec.toString());
				fuecServicio.crear(fuec);
				response.put("mensaje", 1);
				return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			response.put("error", ex.getLocalizedMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> listarFuec(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Page<Object[]> page=fuecServicio.listar(pr);
				List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					List<HashMap<String, Object>> Conductores = new ArrayList<HashMap<String, Object>>();
					List<Object[]> listaConductores=conductorServicio.conductoresDeFuec((Long)o[0]);
					for(Object[] ob:listaConductores) {
						HashMap<String, Object> conductor = new HashMap<String, Object>();
						conductor.put("id", ob[0]);
						conductor.put("nombrePersona", ob[1]);
						conductor.put("apellidoPersona", ob[2]);
						Conductores.add(conductor);
					}
					HashMap<String, Object> objeto = new HashMap<String, Object>();
					objeto.put("idFuec", o[0]);
					objeto.put("numeracionContrato", o[1]);
					objeto.put("razonSocialCliente", o[2]);
					objeto.put("objetoContrato", o[3]);
					
					objeto.put("ciudadOrigen", o[4]);
					objeto.put("ciudadDestino", o[5]);
					objeto.put("placaVehiculo", o[6]);
					objeto.put("fechaInicioFuec", o[7]);
					objeto.put("fechaFinFuec", o[8]);
					objeto.put("codigoFuec", o[9]);
					objeto.put("cupoMaximo", o[10]);
					objeto.put("cupoDisponible",o[11]);
					objeto.put("estado", o[12]);
					objeto.put("fuec", o[13]);
					objeto.put("conductorList", Conductores);
					lista.add(objeto);
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("fuecs", lista);
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
				Object[] o=fuecServicio.obtenerPorId(id);
				System.out.println(o.length);
				List<HashMap<String, Object>> Conductores = new ArrayList<HashMap<String, Object>>();
				List<Object[]> listaConductores=conductorServicio.conductoresDeFuec((Long)o[0]);
				System.out.println("lista :"+listaConductores.size());
				for(Object[] ob:listaConductores) {
					if(Integer.parseInt(ob[5].toString())==1)
					{
						HashMap<String, Object> conductor = new HashMap<String, Object>();
						conductor.put("id", ob[0]);
						conductor.put("nombrePersona", ob[1]);
						conductor.put("apellidoPersona", ob[2]);
						conductor.put("documentoPersona",ob[3]);
						Conductores.add(conductor);
					}
				}
				List<HashMap<String, Object>> pasajeros = fuecServicio.listaPasajeroFuec(id);
				HashMap<String, Object> objeto = new HashMap<String, Object>();
				objeto.put("idFuec", o[0]);
				objeto.put("contrato", o[1]);
				objeto.put("numeracionContrato", o[2]);
				objeto.put("razonSocialCliente", o[3]);
				objeto.put("objetoContrato", o[4]);
				
				objeto.put("ciudadOrigen", o[5]);
				objeto.put("ciudadDestino", o[6]);
				objeto.put("vehiculo", o[7]);
				objeto.put("fechaInicioFuec", o[8]);
				objeto.put("fechaFinFuec", o[9]);
				objeto.put("codigoFuec", o[10]);
				objeto.put("conductorList", Conductores);
				objeto.put("cupoMaximo", o[11]);
				objeto.put("cupoDisponible",o[12]);
				objeto.put("pasajeroList", pasajeros);
				return ResponseEntity.ok().body(objeto);
				
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
	
	@GetMapping("/infoQR/{id}")
	public ResponseEntity<?> obtenerInfoQRFuec(@PathVariable("id") Long id){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			
				Object[] o=fuecServicio.obtenerPorId(id);
				System.out.println(o.length);
				List<HashMap<String, Object>> Conductores = new ArrayList<HashMap<String, Object>>();
				List<Object[]> listaConductores=conductorServicio.conductoresDeFuec((Long)o[0]);
				System.out.println("lista :"+listaConductores.size());
				for(Object[] ob:listaConductores) {
					if(Integer.parseInt(ob[5].toString())==1)
					{
						HashMap<String, Object> conductor = new HashMap<String, Object>();
						conductor.put("id", ob[0]);
						conductor.put("nombrePersona", ob[1]);
						conductor.put("apellidoPersona", ob[2]);
						conductor.put("documentoPersona",ob[3]);
						Conductores.add(conductor);
					}
				}
				List<HashMap<String, Object>> pasajeros = fuecServicio.listaPasajeroFuec(id);
				HashMap<String, Object> objeto = new HashMap<String, Object>();
				objeto.put("idFuec", o[0]);
				objeto.put("contrato", o[1]);
				objeto.put("numeracionContrato", o[2]);
				objeto.put("razonSocialCliente", o[3]);
				objeto.put("objetoContrato", o[4]);
				objeto.put("ciudadOrigen", o[5]);
				objeto.put("ciudadDestino", o[6]);
				objeto.put("vehiculo", o[7]);
				objeto.put("fechaInicioFuec", o[8]);
				objeto.put("fechaFinFuec", o[9]);
				objeto.put("codigoFuec", o[10]);
				objeto.put("conductorList", Conductores);
				objeto.put("cupoMaximo", o[11]);
				objeto.put("cupoDisponible",o[12]);
				objeto.put("pasajeroList", pasajeros);
				return ResponseEntity.ok().body(objeto);
				
		}catch(Exception ex) {
			ex.printStackTrace();
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/anular/{id}")
	public ResponseEntity<?> anular(@PathVariable("id") Long id,@RequestParam("token") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Fuec fuec = fuecServicio.traerPorId(id);
				fuec.setEstadoFuec(0);
				fuecServicio.actualizar(fuec);
				
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
	
	@PutMapping("/agregarConductor/{conductor}/{fuec}")
	public ResponseEntity<?> agregarConductor(@PathVariable("conductor") int conductor,@PathVariable("fuec")Long fuec,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) 
			{
				Fuec fuecGuardado = fuecServicio.traerPorId(fuec);
				Conductor conductorNuevo = new Conductor();
				conductorNuevo.setIdConductor(conductor);
				fuecGuardado.addConductor(conductorNuevo);
				fuecServicio.actualizar(fuecGuardado);
				response.put("mensaje", "1");
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
	
	@PutMapping("/agregarPasajero/{pasajero}/{fuec}")
	public ResponseEntity<?> agregarPasajero(@PathVariable("pasajero") int pasajero,@PathVariable("fuec")Long fuec,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) 
			{
				Fuec fuecGuardado = fuecServicio.traerPorId(fuec);
				Pasajero pasajeroNuevo = new Pasajero();
				pasajeroNuevo.setIdPasajero(pasajero);
				fuecGuardado.addPasajero(pasajeroNuevo);
				fuecServicio.actualizar(fuecGuardado);
				response.put("mensaje", "1");
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
	
	@PutMapping("/removerConductor/{conductor}/{fuec}")
	public ResponseEntity<?> removerConductor(@PathVariable("conductor") int conductor,@PathVariable("fuec")Long fuec,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				
				Conductor conductorNuevo = new Conductor();
				conductorNuevo.setIdConductor(conductor);
				Fuec fuecG = fuecServicio.traerPorId(fuec);
				fuecG.getConductorList().remove(conductorNuevo);
				fuecServicio.eliminarConductorFuec(conductor,fuec);
				//fuecServicio.actualizar(fuecG);
				response.put("mensaje", "1");
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
	
	@PutMapping("/removerPasajero/{pasajero}/{fuec}")
	public ResponseEntity<?> removerPasajero(@PathVariable("pasajero") int pasajero,@PathVariable("fuec")Long fuec,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				
				Pasajero pasajeroNuevo = new Pasajero();
				pasajeroNuevo.setIdPasajero(pasajero);
				Fuec fuecG = fuecServicio.traerPorId(fuec);
				fuecG.getPasajeroList().remove(pasajeroNuevo);
				fuecServicio.eliminarPasajeroFuec(pasajero,fuec);
				//fuecServicio.actualizar(fuecG);
				response.put("mensaje", "1");
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
	
	
	
	@GetMapping("/filtrar")
	public ResponseEntity<?> filtrarFuec(@RequestParam("accessToken") String accessToken, @RequestParam("criterio") String criterio, @RequestParam("valor") String valor)
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
					case "ct":
						System.out.println("buscando por contrato : ");
						lista = fuecServicio.listarPorContrato(0, valor);
						return ResponseEntity.ok().body(lista);
					case "nf":
						System.out.println("buscando por placa : ");
						lista = fuecServicio.listarPorFuec(0,valor);
						return ResponseEntity.ok().body(lista);
					case "vh":
						System.out.println("buscando por placa : ");
						lista = fuecServicio.listarPorVehiculo(0, valor);
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
	
	@GetMapping("/filtrarRuta")
	public ResponseEntity<?> filtrarFuecRuta(@RequestParam("accessToken") String accessToken, @RequestParam("origen") String origen, @RequestParam("destino") String destino)
	{
		System.out.println("en servicio filtro afiliacion");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
				if(tokenServicio.existeToken(accessToken)!=null)
				{
					System.out.println("valor :"+origen);
					HashMap<String,Object> lista = new HashMap<String,Object>();
					System.out.println("buscando por placa : ");
					lista = fuecServicio.listarPorRuta(0, origen,destino);
					return ResponseEntity.ok().body(lista);
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
