package com.itefs.trexsas.controlador;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.itefs.trexsas.modelo.Aseguradora;
import com.itefs.trexsas.modelo.Clase;
import com.itefs.trexsas.modelo.ColorVehiculo;
import com.itefs.trexsas.modelo.Conductor;
import com.itefs.trexsas.modelo.Convenio;
import com.itefs.trexsas.modelo.DocumentoVehiculo;
import com.itefs.trexsas.modelo.EmpresaConvenio;
import com.itefs.trexsas.modelo.EntidadTransito;
import com.itefs.trexsas.modelo.LineaMarca;
import com.itefs.trexsas.modelo.Marca;
import com.itefs.trexsas.modelo.POJOVehiculoExcel;
import com.itefs.trexsas.modelo.Persona;
import com.itefs.trexsas.modelo.PersonaAutor;
import com.itefs.trexsas.modelo.PolizaContractual;
import com.itefs.trexsas.modelo.PolizaExtracontractual;
import com.itefs.trexsas.modelo.PolizaTodoRiesgo;
import com.itefs.trexsas.modelo.RevisionPreventiva;
import com.itefs.trexsas.modelo.RevisionTecnicomecanica;
import com.itefs.trexsas.modelo.Soat;
import com.itefs.trexsas.modelo.TarjetaOperacion;
import com.itefs.trexsas.modelo.TipoCarroceria;
import com.itefs.trexsas.modelo.TipoCombustible;
import com.itefs.trexsas.modelo.TipoServicio;
import com.itefs.trexsas.modelo.TipoVehiculo;
import com.itefs.trexsas.modelo.Vehiculo;
import com.itefs.trexsas.servicio.AseguradoraServicio;
import com.itefs.trexsas.servicio.ClaseServicio;
import com.itefs.trexsas.servicio.ConductorServicio;
import com.itefs.trexsas.servicio.ConvenioServicio;
import com.itefs.trexsas.servicio.DocumentoVehiculoServicio;
import com.itefs.trexsas.servicio.EmpresaConvenioServicio;
import com.itefs.trexsas.servicio.MarcaServicio;
import com.itefs.trexsas.servicio.PersonaServicio;
import com.itefs.trexsas.servicio.PojoVehiculosExcel;
import com.itefs.trexsas.servicio.PolizaContractualServicio;
import com.itefs.trexsas.servicio.PolizaExtracontractualServicio;
import com.itefs.trexsas.servicio.PolizaTodoRiesgoServicio;
import com.itefs.trexsas.servicio.RevisionPreventivaServicio;
import com.itefs.trexsas.servicio.RevisionTecnicomecanicaServicio;
import com.itefs.trexsas.servicio.SoatServicio;
import com.itefs.trexsas.servicio.TarjetaOperacionServicio;
import com.itefs.trexsas.servicio.TipoCombustibleServicio;
import com.itefs.trexsas.servicio.TipoVehiculoServicio;
import com.itefs.trexsas.servicio.TokenServicio;
import com.itefs.trexsas.servicio.VehiculoServicio;

@RestController
@RequestMapping("/vehiculo")
public class VehiculoControlador {
	
	@Autowired
	private VehiculoServicio vehiculoServicio;
	
	
	@Autowired
	private ConductorServicio conductorServicio;
	@Autowired
	private RevisionTecnicomecanicaServicio revisionTecnicomecanicaServicio;
	@Autowired
	private PolizaContractualServicio polizaContractualServicio;
	@Autowired
	private PolizaExtracontractualServicio polizaExtracontractualServicio;
	@Autowired
	private PolizaTodoRiesgoServicio polizaTodoRiesgoService;
	@Autowired
	private SoatServicio soatServicio;
	@Autowired
	private TarjetaOperacionServicio tarjetaOperacionServicio;
	@Autowired
	private RevisionPreventivaServicio revisionPreventivaServicio;
	@Autowired
	private DocumentoVehiculoServicio documentoVehiculoServicio;
	@Autowired
	private ConvenioServicio convenioServicio;
	@Autowired
	private TipoCombustibleServicio tipoCombustibleServicio;
	@Autowired
	private TipoVehiculoServicio tipoVehiculoServicio;
	@Autowired
	private MarcaServicio marcaServicio;
	@Autowired
	private ClaseServicio claseServicio;
	@Autowired
	private AseguradoraServicio aseguradoraServicio;
	@Autowired
	private EmpresaConvenioServicio empresaConvenioServicio;
	@Autowired
	private TokenServicio tokenServicio;
	
	@Autowired
	private PersonaServicio personaServicio;
	
	@Autowired
    private Validator validator;
	
	
	@PostMapping("crearVehiculoConvenio")
	public ResponseEntity<?> crearVehiculoConvenio(@RequestParam("accessToken") String accessToken,@RequestBody Map<String,Object> map){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Persona autor=new Persona();
				autor.setIdPersona(id);
				ObjectMapper mapper=new ObjectMapper();
				Vehiculo vehiculo=mapper.convertValue(map.get("vehiculo"), Vehiculo.class);
				TarjetaOperacion tarjetaOperacion=mapper.convertValue(map.get("tarjetaOperacion"), TarjetaOperacion.class);
				RevisionPreventiva revisionPreventiva=mapper.convertValue(map.get("revisionPreventiva"), RevisionPreventiva.class);
				RevisionTecnicomecanica revisionTecnicomecanica=mapper.convertValue(map.get("revisionTecnicomecanica"), RevisionTecnicomecanica.class);
				PolizaExtracontractual polizaExtracontractual=mapper.convertValue(map.get("polizaExtracontractual"), PolizaExtracontractual.class);
				PolizaContractual polizaContractual=mapper.convertValue(map.get("polizaContractual"), PolizaContractual.class);
				PolizaTodoRiesgo polizaTodoRiesgo = mapper.convertValue(map.get("polizaTodoRiesgo"), PolizaTodoRiesgo.class);
				Soat soat=mapper.convertValue(map.get("soat"), Soat.class);
				Convenio convenio=null;
				Short pcp=1;
				vehiculo.setDisponibilidad(pcp);
				vehiculo.setEstadoVehiculo(1);
				vehiculo.setRegistradoPorVehiculo(autor);
				
				Set<ConstraintViolation<Convenio>> validateConvenio = null;
				if(map.get("convenio")!=null) {
					convenio=mapper.convertValue(map.get("convenio"), Convenio.class);
					convenio.setRegistradoPorConvenio(autor);
					validateConvenio = validator.validate(convenio);
				}else
				{
					System.out.println("no caghada");
				}
				Set<ConstraintViolation<Vehiculo>> validateVehiculo = validator.validate(vehiculo);
				Set<ConstraintViolation<TarjetaOperacion>> validateTarjetaOperacion = validator.validate(tarjetaOperacion);
				Set<ConstraintViolation<RevisionPreventiva>> validateRevisionPreventiva = validator.validate(revisionPreventiva);
				Set<ConstraintViolation<RevisionTecnicomecanica>> validateRevisionTecnicomecanica = validator.validate(revisionTecnicomecanica);
				Set<ConstraintViolation<PolizaExtracontractual>> validatePolizaExtracontractual = validator.validate(polizaExtracontractual);
				Set<ConstraintViolation<PolizaContractual>> validatePolizaContractual = validator.validate(polizaContractual);
				Set<ConstraintViolation<Soat>> validateSoat = validator.validate(soat);
				vehiculo.setAccion("creación de reserva");
				Persona persona = personaServicio.obtenerPorId(id);
				vehiculo.setModificado(persona);
				vehiculo.setFechaModificado(java.time.LocalDate.now().toString());
				vehiculoServicio.crear(vehiculo);
				soat.setVehiculo(vehiculo);
				soatServicio.crear(soat);
				revisionPreventiva.setVehiculo(vehiculo);
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date currentDate = dateFormat.parse(revisionPreventiva.getFechaInicioRevisionPreventiva());
		        System.out.println(dateFormat.format(currentDate));
		        Calendar c = Calendar.getInstance();
		        c.setTime(currentDate);
		        c.add(Calendar.DATE, 60);
		        Date currentDatePlusOne = c.getTime();
		        System.out.println(dateFormat.format(currentDatePlusOne));
		        String fechaVencimientoPreventiva = dateFormat.format(currentDatePlusOne);
		        System.out.println("fechaString : "+fechaVencimientoPreventiva);
				revisionPreventiva.setFechaVencimientoRevisionPreventiva(fechaVencimientoPreventiva);
				
				if(vehiculo.getTipoVehiculo().getIdTipoVehiculo()!=1)
				{
					tarjetaOperacion.setVehiculo(vehiculo);
					tarjetaOperacionServicio.crear(tarjetaOperacion);
				}
				
				revisionPreventivaServicio.crear(revisionPreventiva);
				revisionTecnicomecanica.setVehiculo(vehiculo);
				revisionTecnicomecanicaServicio.crear(revisionTecnicomecanica);
				polizaContractual.setVehiculo(vehiculo);
				polizaContractualServicio.crear(polizaContractual);
				polizaExtracontractual.setVehiculo(vehiculo);
				polizaExtracontractualServicio.crear(polizaExtracontractual);
				polizaTodoRiesgo.setVehiculo(vehiculo);
				polizaTodoRiesgoService.guardarPolizaTodoRiesgo(polizaTodoRiesgo);
				DocumentoVehiculo documentoVehiculo=new DocumentoVehiculo();
				documentoVehiculo.setVehiculo(vehiculo);
				documentoVehiculoServicio.crear(documentoVehiculo);
				if(convenio!=null) {
					convenio.setVehiculo(vehiculo);
					convenioServicio.crear(convenio);
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
	
	
	
	
	@PostMapping("crearVehiculoAfiliacion")
	public ResponseEntity<?> crear(@RequestParam("accessToken") String accessToken,@RequestBody Map<String,Object> map){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				System.out.println("1");
				Persona autor=new Persona();
				autor.setIdPersona(id);
				ObjectMapper mapper=new ObjectMapper();
				Vehiculo vehiculo=mapper.convertValue(map.get("vehiculo"), Vehiculo.class);
				TarjetaOperacion tarjetaOperacion=mapper.convertValue(map.get("tarjetaOperacion"), TarjetaOperacion.class);
				RevisionPreventiva revisionPreventiva=mapper.convertValue(map.get("revisionPreventiva"), RevisionPreventiva.class);
				RevisionTecnicomecanica revisionTecnicomecanica=mapper.convertValue(map.get("revisionTecnicomecanica"), RevisionTecnicomecanica.class);
				PolizaExtracontractual polizaExtracontractual=mapper.convertValue(map.get("polizaExtracontractual"), PolizaExtracontractual.class);
				PolizaContractual polizaContractual=mapper.convertValue(map.get("polizaContractual"), PolizaContractual.class);
				PolizaTodoRiesgo polizaTodoRiesgo = mapper.convertValue(map.get("polizaTodoRiesgo"), PolizaTodoRiesgo.class);
				Soat soat=mapper.convertValue(map.get("soat"), Soat.class);
				Convenio convenio=null;
				Short pcp=1;
				vehiculo.setDisponibilidad(pcp);
				vehiculo.setEstadoVehiculo(1);
				vehiculo.setRegistradoPorVehiculo(autor);
				Set<ConstraintViolation<Convenio>> validateConvenio = null;
				vehiculo.setEnConvenioVehiculo(Short.valueOf("0"));
				System.out.println("tipo vehiculo : "+vehiculo.getTipoServicio());
				
				Set<ConstraintViolation<Vehiculo>> validateVehiculo = validator.validate(vehiculo);
				Set<ConstraintViolation<TarjetaOperacion>> validateTarjetaOperacion = validator.validate(tarjetaOperacion);
				Set<ConstraintViolation<RevisionPreventiva>> validateRevisionPreventiva = validator.validate(revisionPreventiva);
				Set<ConstraintViolation<RevisionTecnicomecanica>> validateRevisionTecnicomecanica = validator.validate(revisionTecnicomecanica);
				Set<ConstraintViolation<PolizaExtracontractual>> validatePolizaExtracontractual = validator.validate(polizaExtracontractual);
				Set<ConstraintViolation<PolizaContractual>> validatePolizaContractual = validator.validate(polizaContractual);
				Set<ConstraintViolation<Soat>> validateSoat = validator.validate(soat);
				System.out.println("9");
				vehiculo.setAccion("creación de reserva");
				System.out.println("10");
				Persona persona = personaServicio.obtenerPorId(id);
				System.out.println("11");
				vehiculo.setModificado(persona);
				vehiculo.setFechaModificado(java.time.LocalDate.now().toString());
				System.out.println("12");
				vehiculoServicio.crear(vehiculo);
				soat.setVehiculo(vehiculo);
				System.out.println("13");
				soatServicio.crear(soat);
				tarjetaOperacion.setVehiculo(vehiculo);
				System.out.println("14");
				tarjetaOperacionServicio.crear(tarjetaOperacion);
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date currentDate = dateFormat.parse(revisionPreventiva.getFechaInicioRevisionPreventiva());
		        System.out.println(dateFormat.format(currentDate));
		        Calendar c = Calendar.getInstance();
		        c.setTime(currentDate);
		        c.add(Calendar.DATE, 60);
		        Date currentDatePlusOne = c.getTime();
		        System.out.println(dateFormat.format(currentDatePlusOne));
		        String fechaVencimientoPreventiva = dateFormat.format(currentDatePlusOne);
		        System.out.println("fechaString : "+fechaVencimientoPreventiva);
				revisionPreventiva.setFechaVencimientoRevisionPreventiva(fechaVencimientoPreventiva);
				
				
				revisionPreventiva.setVehiculo(vehiculo);
				System.out.println("15");
				revisionPreventivaServicio.crear(revisionPreventiva);
				revisionTecnicomecanica.setVehiculo(vehiculo);
				System.out.println("16");
				revisionTecnicomecanicaServicio.crear(revisionTecnicomecanica);
				polizaContractual.setVehiculo(vehiculo);
				System.out.println("17");
				polizaContractualServicio.crear(polizaContractual);
				polizaExtracontractual.setVehiculo(vehiculo);
				polizaTodoRiesgo.setVehiculo(vehiculo);
				polizaTodoRiesgoService.guardarPolizaTodoRiesgo(polizaTodoRiesgo);
				System.out.println("18");
				polizaExtracontractualServicio.crear(polizaExtracontractual);
				DocumentoVehiculo documentoVehiculo=new DocumentoVehiculo();
				documentoVehiculo.setVehiculo(vehiculo);
				System.out.println("19");
				documentoVehiculoServicio.crear(documentoVehiculo);
				
				System.out.println("20");
				response.put("mensaje", 1);
				return ResponseEntity.ok().body(response);
			}else {
				System.out.println("21");
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch(Exception ex) {
			System.out.println("22");
			ex.printStackTrace();
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PostMapping("/conductor")
	public ResponseEntity<?> agregarConductor(@RequestBody Map<String, String> map,@RequestParam("accessToken") String accessToken, @RequestParam("modificador")String modificador){
		HashMap<String, Object> response = new HashMap<String, Object>();
		
		try {
			System.out.println("agregando conductor");
			if(tokenServicio.existeToken(accessToken)!=null) {
				Integer idVehiculo=Integer.parseInt(map.get("idVehiculo"));
				Integer idConductor=Integer.parseInt(map.get("idConductor"));
				Vehiculo vehiculo=vehiculoServicio.obtenerPorId(idVehiculo);
				Conductor conductor=new Conductor();
				conductor.setIdConductor(idConductor);
				vehiculo.addConductor(conductor);
				vehiculo.setAccion("agregó conductor");
				Persona persona = new Persona();
				persona.setIdPersona(Long.valueOf(modificador));
				vehiculo.setModificado(persona);
				vehiculo.setFechaModificado(java.time.LocalDate.now().toString());
				vehiculoServicio.actualizar(vehiculo);
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
	
	@PutMapping
	public ResponseEntity<?> editar(@RequestParam("accessToken") String accessToken,@RequestBody Map<String,Object> map,@RequestParam("modificador")String modificador){
		HashMap<String, Object> response = new HashMap<String, Object>();
		System.out.println("modificador : "+modificador);
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Persona autor=new Persona();
				autor.setIdPersona(id);
				ObjectMapper mapper=new ObjectMapper();
				Vehiculo auxVehiculo=mapper.convertValue(map.get("vehiculo"), Vehiculo.class);
				TarjetaOperacion auxTarjetaOperacion=mapper.convertValue(map.get("tarjetaOperacion"), TarjetaOperacion.class);
				RevisionPreventiva auxRevisionPreventiva=mapper.convertValue(map.get("revisionPreventiva"), RevisionPreventiva.class);
				RevisionTecnicomecanica auxRevisionTecnicomecanica=mapper.convertValue(map.get("revisionTecnicomecanica"), RevisionTecnicomecanica.class);
				PolizaExtracontractual auxPolizaExtracontractual=mapper.convertValue(map.get("polizaExtracontractual"), PolizaExtracontractual.class);
				PolizaContractual auxPolizaContractual=mapper.convertValue(map.get("polizaContractual"), PolizaContractual.class);
				PolizaTodoRiesgo auxPolizaTodoRiesgo = mapper.convertValue(map.get("polizaTodoRiesgo"), PolizaTodoRiesgo.class);
				Soat auxSoat=mapper.convertValue(map.get("soat"), Soat.class);
				Vehiculo vehiculo=vehiculoServicio.obtenerPorId(auxVehiculo.getIdVehiculo());
				vehiculo.setCodigoInternoVehiculo(auxVehiculo.getCodigoInternoVehiculo());
				vehiculo.setPlacaVehiculo(auxVehiculo.getPlacaVehiculo());
				vehiculo.setEstadoVehiculo(auxVehiculo.getEstadoVehiculo());
				vehiculo.setModelo(auxVehiculo.getModelo());
				vehiculo.setCilindraje(auxVehiculo.getCilindraje());
				vehiculo.setNumeroPasajerosVehiculo(auxVehiculo.getNumeroPasajerosVehiculo());
				vehiculo.setColorVehiculo(auxVehiculo.getColorVehiculo());
				vehiculo.setActualizadoPorVehiculo(autor);
				vehiculo.setNumeroVin(auxVehiculo.getNumeroVin());
				vehiculo.setEnConvenioVehiculo(auxVehiculo.getEnConvenioVehiculo());
				vehiculo.setClase(auxVehiculo.getClase());
				vehiculo.setMarca(auxVehiculo.getMarca());
				vehiculo.setTipoCombustible(auxVehiculo.getTipoCombustible());
				vehiculo.setTipoVehiculo(auxVehiculo.getTipoVehiculo());
				vehiculo.setPropietario(auxVehiculo.getPropietario());
				vehiculo.setCarroceriaVehiculo(auxVehiculo.getCarroceriaVehiculo());
				vehiculo.setColorVehiculo(auxVehiculo.getColorVehiculo());
				vehiculo.setTipoServicio(auxVehiculo.getTipoServicio());
				vehiculo.setEntidadTransito(auxVehiculo.getEntidadTransito());
				vehiculo.setNumeroMotorVehiculo(auxVehiculo.getNumeroMotorVehiculo());
				vehiculo.setChasisVehiculo(auxVehiculo.getChasisVehiculo());
				vehiculo.setLinea(auxVehiculo.getLinea());
				vehiculo.setNumeroLicenciaTransito(auxVehiculo.getNumeroLicenciaTransito());
				vehiculo.setFechaMatricula(auxVehiculo.getFechaMatricula());
				vehiculo.setTraseraVehiculo(auxVehiculo.getTraseraVehiculo());
				vehiculo.setFrenteVehiculo(auxVehiculo.getFrenteVehiculo());
				vehiculo.setLadoVehiculo(auxVehiculo.getLadoVehiculo());
				vehiculo.setTarjetaPropiedadUnoVehiculo(auxVehiculo.getTarjetaPropiedadUnoVehiculo());
				vehiculo.setNumeroSerie(auxVehiculo.getNumeroSerie());
				vehiculo.setEntidadTransito(vehiculo.getEntidadTransito());
				Soat soat=soatServicio.obtenerPorId(auxSoat.getIdSoat());
				
				soat.setFechaInicioSoat(auxSoat.getFechaInicioSoat());
				soat.setFechaVencimientoSoat(auxSoat.getFechaVencimientoSoat());
				soat.setNumeroSoat(auxSoat.getNumeroSoat());
				soat.setAseguradora(auxSoat.getAseguradora());
				
				TarjetaOperacion tarjetaOperacion=tarjetaOperacionServicio.obtenerPorId(auxTarjetaOperacion.getIdTarjetaOperacion());
				tarjetaOperacion.setNumeroTarjetaOperacion(auxTarjetaOperacion.getNumeroTarjetaOperacion());
				tarjetaOperacion.setFechaExpedicionTarjetaOperacion(auxTarjetaOperacion.getFechaExpedicionTarjetaOperacion());
				tarjetaOperacion.setFechaVencimientoTarjetaOperacion(auxTarjetaOperacion.getFechaVencimientoTarjetaOperacion());
				tarjetaOperacion.setUnoTarjetaOperacion(auxTarjetaOperacion.getUnoTarjetaOperacion());
				
				RevisionPreventiva revisionPreventiva=revisionPreventivaServicio.obtenerPorId(auxRevisionPreventiva.getIdRevisionPreventiva());
				revisionPreventiva.setFechaInicioRevisionPreventiva(auxRevisionPreventiva.getFechaInicioRevisionPreventiva());
				revisionPreventiva.setFechaVencimientoRevisionPreventiva(auxRevisionPreventiva.getFechaVencimientoRevisionPreventiva());
				revisionPreventiva.setRevisionPreventiva(auxRevisionPreventiva.getRevisionPreventiva());
				
				RevisionTecnicomecanica revisionTecnicomecanica= revisionTecnicomecanicaServicio.obtenerPorId(auxRevisionTecnicomecanica.getIdRevisionTecnicomecanica());
				revisionTecnicomecanica.setFechaRevisionTecnicomecanica(auxRevisionTecnicomecanica.getFechaRevisionTecnicomecanica());
				revisionTecnicomecanica.setFechaVencimientoRevisionTecnicomecanica(auxRevisionTecnicomecanica.getFechaVencimientoRevisionTecnicomecanica());
				revisionTecnicomecanica.setRevisionTecnicomecanica(auxRevisionTecnicomecanica.getRevisionTecnicomecanica());
				revisionTecnicomecanica.setNumeroRevisionTecnicoMecanica(auxRevisionTecnicomecanica.getNumeroRevisionTecnicoMecanica());
				
				PolizaContractual polizaContractual=polizaContractualServicio.obtenerPorId(auxPolizaContractual.getIdPolizaContractual());
				polizaContractual.setFechaInicioPolizaContractual(auxPolizaContractual.getFechaInicioPolizaContractual());
				polizaContractual.setFechaVencimientoPolizaContractual(auxPolizaContractual.getFechaVencimientoPolizaContractual());
				polizaContractual.setNumeroPolizaContractual(auxPolizaContractual.getNumeroPolizaContractual());
				polizaContractual.setAseguradora(auxPolizaContractual.getAseguradora());
				polizaContractual.setPolizaContractual(auxPolizaContractual.getPolizaContractual());
				
				PolizaExtracontractual polizaExtracontractual=polizaExtracontractualServicio.obtenerPorId(auxPolizaExtracontractual.getIdPolizaExtracontractual());
				polizaExtracontractual.setFechaInicioPolizaExtracontractual(auxPolizaExtracontractual.getFechaInicioPolizaExtracontractual());
				polizaExtracontractual.setFechaVencimientoPolizaExtracontractual(auxPolizaExtracontractual.getFechaVencimientoPolizaExtracontractual());
				polizaExtracontractual.setNumeroPolizaExtracontractual(auxPolizaExtracontractual.getNumeroPolizaExtracontractual());
				
				
				
				PolizaTodoRiesgo polizaTodoRiesgo = polizaTodoRiesgoService.obtenerPorId(auxPolizaTodoRiesgo.getId());
				polizaTodoRiesgo.setAseguradora(auxPolizaTodoRiesgo.getAseguradora());
				polizaTodoRiesgo.setNumeroPoliza(auxPolizaTodoRiesgo.getNumeroPoliza());
				polizaTodoRiesgo.setFechaInicio(auxPolizaTodoRiesgo.getFechaInicio());
				polizaTodoRiesgo.setFechaFin(auxPolizaTodoRiesgo.getFechaFin());
				
				
				polizaExtracontractual.setAseguradora(auxPolizaExtracontractual.getAseguradora());
				polizaExtracontractual.setPolizaExtracontractual(auxPolizaExtracontractual.getPolizaExtracontractual());
				Convenio auxConvenio=null;
				Set<ConstraintViolation<Convenio>> validateConvenio = null;
				if(map.get("convenio")!=null && auxVehiculo.getEnConvenioVehiculo()==1) {
					auxConvenio=mapper.convertValue(map.get("convenio"), Convenio.class);
					Convenio convenio=convenioServicio.obtenerPorId(auxConvenio.getIdConvenio());
					convenio.setFechaFinConvenio(auxConvenio.getFechaFinConvenio());
					convenio.setFechaInicioConvenio(auxConvenio.getFechaInicioConvenio());
					convenio.setLicenciaConvenio(auxConvenio.getLicenciaConvenio());
					convenio.setConvenio(auxConvenio.getConvenio());
					convenio.setEmpresaConvenio(auxConvenio.getEmpresaConvenio());
					convenio.setActualizadoPorConvenio(autor);
					validateConvenio = validator.validate(convenio);
					if(validateConvenio.size()>0 ) {
						List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
						for (ConstraintViolation<Convenio> item : validateConvenio) {
							HashMap<String, Object> responseError = new HashMap<String, Object>();
					        responseError.put("mensaje", item.getMessage());
							responses.add(responseError);
					    }
						return ResponseEntity.ok().body(responses);
					}
					convenio.setFechaInicioConvenio(auxConvenio.getFechaInicioConvenio());
					convenio.setFechaFinConvenio(auxConvenio.getFechaFinConvenio());
					convenio.setLicenciaConvenio(auxConvenio.getLicenciaConvenio());
					convenio.setEmpresaConvenio(auxConvenio.getEmpresaConvenio());
					
					convenioServicio.actualizar(convenio);
				}
				
				
				Set<ConstraintViolation<Vehiculo>> validateVehiculo = validator.validate(vehiculo);
				Set<ConstraintViolation<TarjetaOperacion>> validateTarjetaOperacion = validator.validate(tarjetaOperacion);
				Set<ConstraintViolation<RevisionPreventiva>> validateRevisionPreventiva = validator.validate(revisionPreventiva);
				Set<ConstraintViolation<RevisionTecnicomecanica>> validateRevisionTecnicomecanica = validator.validate(revisionTecnicomecanica);
				Set<ConstraintViolation<PolizaExtracontractual>> validatePolizaExtracontractual = validator.validate(polizaExtracontractual);
				Set<ConstraintViolation<PolizaContractual>> validatePolizaContractual = validator.validate(polizaContractual);
				Set<ConstraintViolation<Soat>> validateSoat = validator.validate(auxSoat);
				
				if(validateSoat.size()>0 || validateVehiculo.size()>0 || validateTarjetaOperacion.size()>0 || validateRevisionPreventiva.size()>0 
						|| validateRevisionTecnicomecanica.size()>0 || validatePolizaExtracontractual.size()>0 || validatePolizaContractual.size()>0 ||
						validatePolizaContractual.size()>0) 
				{
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Soat> item : validateSoat) {
						System.out.println("1");
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					for (ConstraintViolation<Vehiculo> item : validateVehiculo) {
						System.out.println("2");
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					for (ConstraintViolation<TarjetaOperacion> item : validateTarjetaOperacion) {
						System.out.println("3");
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					for (ConstraintViolation<RevisionPreventiva> item : validateRevisionPreventiva) {
						System.out.println("4");
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					for (ConstraintViolation<RevisionTecnicomecanica> item : validateRevisionTecnicomecanica) {
						System.out.println("5");
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					for (ConstraintViolation<PolizaExtracontractual> item : validatePolizaExtracontractual) {
						System.out.println("6");
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					for (ConstraintViolation<PolizaContractual> item : validatePolizaContractual) {
						System.out.println("7");
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				vehiculo.setAccion("editó vehículo");
				Persona persona = new Persona();
				persona.setIdPersona(Long.valueOf(modificador));
				vehiculo.setModificado(persona);
				vehiculo.setFechaModificado(java.time.LocalDate.now().toString());
				//vehiculoServicio.actualizar(vehiculo);
				soatServicio.actualizar(soat);
				tarjetaOperacionServicio.actualizar(tarjetaOperacion);
				revisionPreventivaServicio.actualizar(revisionPreventiva);
				revisionTecnicomecanicaServicio.actualizar(revisionTecnicomecanica);
				polizaContractualServicio.actualizar(polizaContractual);
				polizaExtracontractualServicio.actualizar(polizaExtracontractual);
				polizaTodoRiesgoService.actualizarPolizaTodoRiesgo(polizaTodoRiesgo);
				vehiculoServicio.actualizar(vehiculo);
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
	public ResponseEntity<?> editarEstado(@RequestParam("accessToken") String accessToken,@RequestBody Vehiculo auxVehiculo, @RequestParam("modificador")String modificador){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Persona autor=new Persona();
				autor.setIdPersona(id);
				Vehiculo vehiculo=vehiculoServicio.obtenerPorId(auxVehiculo.getIdVehiculo());
				vehiculo.setEstadoVehiculo(auxVehiculo.getEstadoVehiculo());
				vehiculo.setActualizadoPorVehiculo(autor);
				vehiculo.setAccion("cambió estado");
				Persona persona = new Persona();
				persona.setIdPersona(Long.valueOf(modificador));
				vehiculo.setModificado(persona);
				vehiculo.setFechaModificado(java.time.LocalDate.now().toString());
				vehiculoServicio.actualizar(vehiculo);
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
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Object[]> page;
				List<Integer> permisos=tokenServicio.permisoListarVehiculo(accessToken);
				List<HashMap<String, Object>> vehiculos = new ArrayList<HashMap<String, Object>>();
				if(permisos.contains(1)) {
					page=vehiculoServicio.listar();
					response.put("rol", "todos");
				}else {
					if(permisos.contains(4)) {
						response.put("rol", "propietario");
						page=vehiculoServicio.listarPropietario(accessToken);
					}else {
						response.put("rol", "conductor");
						page=vehiculoServicio.listarConductor(accessToken);
					}
				}
				System.out.println("tamaño de arreglo : "+page.size());
				vehiculos = vehiculoServicio.todos();
				/*
				 * for(Object[] o:page) {
					HashMap<String, Object> vehiculo = new HashMap<String, Object>();
					vehiculo.put("idVehiculo", o[0]);
					vehiculo.put("codigoInternoVehiculo", o[1]);
					vehiculo.put("placaVehiculo", o[2]);
					vehiculo.put("clase", o[3]);
					vehiculo.put("estadoVehiculo", o[4]);
					vehiculo.put("nombrePersona", o[5]);
					vehiculo.put("apellidoPersona", o[6]);
					vehiculo.put("numeroPasajerosVehiculo", o[7]);
					vehiculos.add(vehiculo);
				}
				 */
				
				response.put("vehiculos", vehiculos);
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
	
	@PostMapping("/traerExcel")
	public ResponseEntity<?> obtenerTodosExcel(@RequestParam("accessToken") String accessToken, @RequestBody PojoVehiculosExcel listadoIDS)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<HashMap<String, Object>> vehiculos = new ArrayList<HashMap<String, Object>>();
				vehiculos = vehiculoServicio.todosExcel(listadoIDS);
				response.put("vehiculos", vehiculos);
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
	
	@GetMapping("/todos")
	public ResponseEntity<?> todos(){
		List<HashMap<String,Object>>vehiculos = vehiculoServicio.todos();
		return ResponseEntity.ok().body(vehiculos);
	}
	
	
	
	@GetMapping("/obtenerParaFuec")
	public ResponseEntity<?> obtenerTodosFuec(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Object[]> page;
				List<Integer> permisos=tokenServicio.permisoListarVehiculo(accessToken);
				List<HashMap<String, Object>> vehiculos = new ArrayList<HashMap<String, Object>>();
				if(permisos.contains(1)) {
					page=vehiculoServicio.listar2();
					response.put("rol", "todos");
				}else {
					if(permisos.contains(4)) {
						response.put("rol", "propietario");
						page=vehiculoServicio.listarPropietario2(accessToken);
					}else {
						response.put("rol", "conductor");
						page=vehiculoServicio.listarConductor2(accessToken);
					}
				}
				
				for(Object[] o:page) {
					HashMap<String, Object> vehiculo = new HashMap<String, Object>();
					if(Integer.parseInt(o[4].toString())==1)
					{
						vehiculo.put("idVehiculo", o[0]);
						vehiculo.put("codigoInternoVehiculo", o[1]);
						vehiculo.put("placaVehiculo", o[2]);
						vehiculo.put("clase", o[3]);
						vehiculo.put("estadoVehiculo", o[4]);
						vehiculo.put("nombrePersona", o[5]);
						vehiculo.put("apellidoPersona", o[6]);
						vehiculo.put("numeroPasajerosVehiculo", o[7]);
						vehiculos.add(vehiculo);
					}
				}
				response.put("vehiculos", vehiculos);
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
	
	
	@GetMapping("traerLinea/{marca}")
	public ResponseEntity<?> obtenerLineas(@RequestParam("accessToken") String accessToken,@PathVariable("marca") Integer marca)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) 
			{
				List<LineaMarca> lineasMarca = vehiculoServicio.obtenerLineas(marca);
				response.put("lineas",lineasMarca);
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
	
	@GetMapping("/infobasica")
	public ResponseEntity<?> obtenerInfoBasica(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Object[]> list=null;
				list=vehiculoServicio.infoBasicaSalud();
				List<HashMap<String, Object>> vehiculos = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:list) {
					List<HashMap<String, Object>> conductores = new ArrayList<HashMap<String, Object>>();
					HashMap<String, Object> vehiculo = new HashMap<String, Object>();
					List<Object[]> listaConductores=conductorServicio.infoBasicaDeVehiculo((int)o[0]);
					for(Object[] ob:listaConductores) {
						HashMap<String, Object> conductor = new HashMap<String, Object>();
						conductor.put("idConductor", ob[0]);
						conductor.put("nombrePersona", ob[1]);
						conductor.put("apellidoPersona", ob[2]);
						conductor.put("documentoPersona", ob[3]);
						conductores.add(conductor);
					}
					vehiculo.put("idVehiculo", o[0]);
					vehiculo.put("placaVehiculo", o[1]);
					vehiculo.put("codigoInternoVehiculo", o[2]);
					vehiculo.put("nombrePersona", o[3]);
					vehiculo.put("apellidoPersona", o[4]);
					vehiculo.put("clase", o[5]);
					vehiculo.put("conductorList", conductores);
					vehiculos.add(vehiculo);
				}
				return ResponseEntity.ok().body(vehiculos);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/infobasicaturismo")
	public ResponseEntity<?> obtenerInfoBasicaTurismo(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Object[]> list=null;
				if(tokenServicio.permisoListarVehiculo(accessToken).size()>0) {
					list=vehiculoServicio.infoBasicaTurismo();
					List<HashMap<String, Object>> vehiculos = new ArrayList<HashMap<String, Object>>();
					for(Object[] o:list) {
						HashMap<String, Object> vehiculo = new HashMap<String, Object>();
						vehiculo.put("idVehiculo", o[0]);
						vehiculo.put("placaVehiculo", o[1]);
						vehiculo.put("codigoInternoVehiculo", o[2]);
						vehiculo.put("nombrePersona", o[3]);
						vehiculo.put("apellidoPersona", o[4]);
						vehiculo.put("clase", o[5]);
						vehiculos.add(vehiculo);
					}
					return ResponseEntity.ok().body(vehiculos);
				}else {
					if(tokenServicio.esPropietario(accessToken)) {
						list=vehiculoServicio.infoBasicaDePropietario(accessToken);
						List<HashMap<String, Object>> vehiculos = new ArrayList<HashMap<String, Object>>();
						for(Object[] o:list) {
							HashMap<String, Object> vehiculo = new HashMap<String, Object>();
							vehiculo.put("idVehiculo", o[0]);
							vehiculo.put("placaVehiculo", o[1]);
							vehiculo.put("codigoInternoVehiculo", o[2]);
							vehiculo.put("nombrePersona", o[3]);
							vehiculo.put("apellidoPersona", o[4]);
							vehiculo.put("clase", o[5]);
							vehiculos.add(vehiculo);
						}
						return ResponseEntity.ok().body(vehiculos);
					}else {
						response.put("mensaje", 2);
						return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
					}
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
	
	
	@GetMapping("placa")
	public ResponseEntity<?> obtenerPlacas(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<String> page=vehiculoServicio.obtenerPlacas();
				List<HashMap<String, Object>> objetos = new ArrayList<HashMap<String, Object>>();
				for(String o:page) {
					HashMap<String, Object> vehiculo = new HashMap<String, Object>();
					vehiculo.put("placaVehiculo", o);
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
	
	@GetMapping("/coloresVehiculos")
	public ResponseEntity<?> obtenerColoresVehiculos(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) 
			{
				List<ColorVehiculo> colores = vehiculoServicio.obtenerColoresVehiculos();
				response.put("colores",colores);
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
	
	@GetMapping("/tipoServicio")
	public ResponseEntity<?> obtenerTiposServicio(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) 
			{
				List<TipoServicio> tiposServicio = vehiculoServicio.obtenerTiposServicio();
				response.put("tiposServicio",tiposServicio);
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
	
	@GetMapping("/tipoCarroceria")
	public ResponseEntity<?> obtenerTiposCarroceria(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) 
			{
				List<TipoCarroceria> tiposCarroceria = vehiculoServicio.obtenerTiposCarrocerias();
				response.put("tiposCarroceria",tiposCarroceria);
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
	
	@GetMapping("/entidadesTransito")
	public ResponseEntity<?> obtenerEntidadesTransito(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) 
			{
				List<EntidadTransito> entidadesTransitos = vehiculoServicio.obtenerEntidadesTransito();
				response.put("entidadesTransito",entidadesTransitos);
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
				System.out.println("trayendo vehiculo 1");
				Vehiculo vehiculo = vehiculoServicio.obtenerPorId(id);
				response.put("vehiculo", vehiculo);
				System.out.println("trayendo vehiculo 2");
				//return ResponseEntity.ok().body(vehiculoServicio.obtenerPorId(id));
				return ResponseEntity.ok().body(vehiculo);
			}else {
				System.out.println("trayendo vehiculo 3");
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			System.out.println("trayendo vehiuclo 4");
			System.out.println(ex.getLocalizedMessage()); 
			ex.printStackTrace();
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	/**
	 * Método que se encarga de traer un vehículo para crear fuec
	 * @param id llave primaria del vehículo en la base de datos
	 * @param accessToken llave de autenticación de un usuario logeado en el sistema
	 * @return ResponseEntity que guarda un cuerpo con la respuesta de la petición
	 */
	@GetMapping("/vehiculoParaFuec/{id}")
	public ResponseEntity<?> obtenerVehiculoPorId(@PathVariable("id") int id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Vehiculo vehiculoSistema = vehiculoServicio.obtenerPorId(id);
				/*List<Conductor> conductoresVehiculo = vehiculoSistema.getConductorList();
				List<Conductor> conductoresHabilitados = new ArrayList<Conductor>();
				for (Conductor conductor : conductoresVehiculo) 
				{
					if(conductor.getEstadoConductor()==1)
					{
						conductoresHabilitados.add(conductor);
					}
				}
				vehiculoSistema.setConductorList(null);
				vehiculoSistema.setConductorList(conductoresHabilitados);*/
				response.put("Vehiculo", vehiculoSistema);
				return ResponseEntity.ok().body(vehiculoServicio.obtenerPorId(id));
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
	
	@DeleteMapping("/conductor/{idVehiculo}/{idConductor}")
	public ResponseEntity<?> quitarConductor(@PathVariable("idVehiculo") int idVehiculo,@PathVariable("idConductor") int idConductor,@RequestParam("accessToken") String accessToken,
			@RequestParam("modificador")String modificador){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Vehiculo vehiculo=vehiculoServicio.obtenerPorId(idVehiculo);
				Conductor conductor=conductorServicio.obtenerPorId(idConductor);
				vehiculo.removeConductor(conductor);
				vehiculo.setAccion("quitó conductor");
				Persona persona = new Persona();
				persona.setIdPersona(Long.valueOf(modificador));
				vehiculo.setModificado(persona);
				vehiculo.setFechaModificado(java.time.LocalDate.now().toString());
				vehiculoServicio.actualizar(vehiculo);
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
	
	//tipo combustible
	
	@PostMapping("/tipocombustible")
	public ResponseEntity<?> crearTipoCombustible(@RequestParam("accessToken") String accessToken,@RequestBody TipoCombustible ob){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				ob.setRegistradoPorTipoCombustible(autor);
				Set<ConstraintViolation<TipoCombustible>> validate = validator.validate(ob);
				if(validate.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<TipoCombustible> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				tipoCombustibleServicio.crear(ob);
				
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
	
	@GetMapping("/tipocombustible/listar")
	public ResponseEntity<?> listarTipoCombustible(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Page<Object[]> page=tipoCombustibleServicio.listar(pr);
				List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					HashMap<String, Object> objeto = new HashMap<String, Object>();
					objeto.put("idTipoCombustible", o[0]);
					objeto.put("tipoCombustible", o[1]);
					objeto.put("estadoTipoCombustible", o[2]);
					lista.add(objeto);
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("tiposCombustible", lista);
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
	
	@GetMapping("/tipocombustible/{id}")
	public ResponseEntity<?> obtenerTipoCombustiblePorId(@PathVariable("id") int id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				TipoCombustible ob=tipoCombustibleServicio.obtenerPorId(id);
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
	
	@GetMapping("/tipocombustible")
	public ResponseEntity<?> obtenerTodosTipoCombustible(){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			return ResponseEntity.ok().body(tipoCombustibleServicio.obtenerTodos());
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping("/tipocombustible")
	public ResponseEntity<?> editarTipoCombustible(@RequestParam("accessToken") String accessToken,@RequestBody TipoCombustible auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				TipoCombustible ob=tipoCombustibleServicio.obtenerPorId(auxOb.getIdTipoCombustible());
				ob.setTipoCombustible(auxOb.getTipoCombustible());
				ob.setEstadoTipoCombustible(auxOb.getEstadoTipoCombustible());
				ob.setActualizadoPorTipoCombustible(autor);
				Set<ConstraintViolation<TipoCombustible>> validate = validator.validate(ob);
				if(validate.size()>0 ) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<TipoCombustible> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					
					return ResponseEntity.ok().body(responses);
				}
				tipoCombustibleServicio.actualizar(ob);
				
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
	
	@PutMapping("/tipocombustible/estado")
	public ResponseEntity<?> editarEstadoTipoCombustible(@RequestParam("accessToken") String accessToken,@RequestBody TipoCombustible auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				TipoCombustible ob=tipoCombustibleServicio.obtenerPorId(auxOb.getIdTipoCombustible());
				ob.setEstadoTipoCombustible(auxOb.getEstadoTipoCombustible());
				ob.setActualizadoPorTipoCombustible(autor);
				
				tipoCombustibleServicio.actualizar(ob);
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
	
	//------------------------tipo vehiculo
	
	@PostMapping("/tipovehiculo")
	public ResponseEntity<?> crearTipoVehiculo(@RequestParam("accessToken") String accessToken,@RequestBody TipoVehiculo ob){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				ob.setRegistradoPorTipoVehiculo(autor);
				Set<ConstraintViolation<TipoVehiculo>> validate = validator.validate(ob);
				if(validate.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<TipoVehiculo> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				tipoVehiculoServicio.crear(ob);
				
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
	
	@GetMapping("/tipovehiculo/listar")
	public ResponseEntity<?> listarTipoVehiculo(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Page<Object[]> page=tipoVehiculoServicio.listar(pr);
				List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					HashMap<String, Object> objeto = new HashMap<String, Object>();
					objeto.put("idTipoVehiculo", o[0]);
					objeto.put("tipoVehiculo", o[1]);
					objeto.put("estadoTipoVehiculo", o[2]);
					lista.add(objeto);
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("tiposVehiculo", lista);
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
	
	@GetMapping("/tipovehiculo/filtro")
	public ResponseEntity<?> listarTipoVehiculoFiltro(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Object[]> page=tipoVehiculoServicio.listarFiltro();
				List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page) {
					HashMap<String, Object> objeto = new HashMap<String, Object>();
					objeto.put("idTipoVehiculo", o[0]);
					objeto.put("tipoVehiculo", o[1]);
					lista.add(objeto);
				}
				response.put("tiposVehiculo", lista);
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
	
	@GetMapping("/tipovehiculo/{id}")
	public ResponseEntity<?> obtenerTipoVehiculoPorId(@PathVariable("id") int id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				TipoVehiculo ob=tipoVehiculoServicio.obtenerPorId(id);
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
	
	@GetMapping("/tipovehiculo")
	public ResponseEntity<?> obtenerTodosTipoVehiculo(){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			return ResponseEntity.ok().body(tipoVehiculoServicio.obtenerTodos());
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping("/tipovehiculo")
	public ResponseEntity<?> editarTipoVehiculo(@RequestParam("accessToken") String accessToken,@RequestBody TipoVehiculo auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				TipoVehiculo ob=tipoVehiculoServicio.obtenerPorId(auxOb.getIdTipoVehiculo());
				ob.setTipoVehiculo(auxOb.getTipoVehiculo());
				ob.setEstadoTipoVehiculo(auxOb.getEstadoTipoVehiculo());
				ob.setActualizadoPorTipoVehiculo(autor);
				Set<ConstraintViolation<TipoVehiculo>> validate = validator.validate(ob);
				if(validate.size()>0 ) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					
					for (ConstraintViolation<TipoVehiculo> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					
					return ResponseEntity.ok().body(responses);
				}
				tipoVehiculoServicio.actualizar(ob);
				
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
	
	@PutMapping("/tipovehiculo/estado")
	public ResponseEntity<?> editarEstadoTipoVehiculo(@RequestParam("accessToken") String accessToken,@RequestBody TipoVehiculo auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				TipoVehiculo ob=tipoVehiculoServicio.obtenerPorId(auxOb.getIdTipoVehiculo());
				ob.setEstadoTipoVehiculo(auxOb.getEstadoTipoVehiculo());
				ob.setActualizadoPorTipoVehiculo(autor);
				
				tipoVehiculoServicio.actualizar(ob);
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
	
	//marca vehiculo
	
	@PostMapping("/marca")
	public ResponseEntity<?> crearMarca(@RequestParam("accessToken") String accessToken,@RequestBody Marca ob){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				ob.setRegistradoPorMarca(autor);
				Set<ConstraintViolation<Marca>> validate = validator.validate(ob);
				if(validate.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Marca> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				marcaServicio.crear(ob);
				
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
	
	@GetMapping("/marca/listar")
	public ResponseEntity<?> listarMarca(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Page<Object[]> page=marcaServicio.listar(pr);
				List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					HashMap<String, Object> objeto = new HashMap<String, Object>();
					objeto.put("idMarca", o[0]);
					objeto.put("marca", o[1]);
					objeto.put("estadoMarca", o[2]);
					lista.add(objeto);
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("marcas", lista);
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
	
	@GetMapping("/marca/{id}")
	public ResponseEntity<?> obtenerMarcaPorId(@PathVariable("id") int id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Marca ob=marcaServicio.obtenerPorId(id);
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
	
	@GetMapping("/marca")
	public ResponseEntity<?> obtenerTodosMarca(){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			return ResponseEntity.ok().body(marcaServicio.obtenerTodos());
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping("/marca")
	public ResponseEntity<?> editarMarca(@RequestParam("accessToken") String accessToken,@RequestBody Marca auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				Marca ob=marcaServicio.obtenerPorId(auxOb.getIdMarca());
				ob.setMarca(auxOb.getMarca());
				ob.setEstadoMarca(auxOb.getEstadoMarca());
				ob.setActualizadoPorMarca(autor);
				Set<ConstraintViolation<Marca>> validate = validator.validate(ob);
				if(validate.size()>0 ) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					
					for (ConstraintViolation<Marca> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					
					return ResponseEntity.ok().body(responses);
				}
				marcaServicio.actualizar(ob);
				
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
	
	@PutMapping("/marca/estado")
	public ResponseEntity<?> editarEstadoMarca(@RequestParam("accessToken") String accessToken,@RequestBody Marca auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				Marca ob=marcaServicio.obtenerPorId(auxOb.getIdMarca());
				ob.setEstadoMarca(auxOb.getEstadoMarca());
				ob.setActualizadoPorMarca(autor);
				
				marcaServicio.actualizar(ob);
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
	
	//--------------------------------------clase vehiculo
	@PostMapping("/clase")
	public ResponseEntity<?> crearClase(@RequestParam("accessToken") String accessToken,@RequestBody Clase ob){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				ob.setRegistradoPorClase(autor);
				Set<ConstraintViolation<Clase>> validate = validator.validate(ob);
				if(validate.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Clase> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				claseServicio.crear(ob);
				
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
	
	@GetMapping("/clase/listar")
	public ResponseEntity<?> listarClase(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Page<Object[]> page=claseServicio.listar(pr);
				List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					HashMap<String, Object> objeto = new HashMap<String, Object>();
					objeto.put("idClase", o[0]);
					objeto.put("clase", o[1]);
					objeto.put("estadoClase", o[2]);
					lista.add(objeto);
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("clases", lista);
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
	
	@GetMapping("/clase/{id}")
	public ResponseEntity<?> obtenerClasePorId(@PathVariable("id") int id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Clase ob=claseServicio.obtenerPorId(id);
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
	
	@GetMapping("/clase")
	public ResponseEntity<?> obtenerTodosClase(){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			return ResponseEntity.ok().body(claseServicio.obtenerTodos());
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping("/clase")
	public ResponseEntity<?> editarClase(@RequestParam("accessToken") String accessToken,@RequestBody Clase auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				Clase ob=claseServicio.obtenerPorId(auxOb.getIdClase());
				ob.setClase(auxOb.getClase());
				ob.setEstadoClase(auxOb.getEstadoClase());
				ob.setActualizadoPorClase(autor);
				Set<ConstraintViolation<Clase>> validate = validator.validate(ob);
				if(validate.size()>0 ) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					
					for (ConstraintViolation<Clase> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					
					return ResponseEntity.ok().body(responses);
				}
				claseServicio.actualizar(ob);
				
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
	
	@PutMapping("/clase/estado")
	public ResponseEntity<?> editarEstadoClase(@RequestParam("accessToken") String accessToken,@RequestBody Clase auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				Clase ob=claseServicio.obtenerPorId(auxOb.getIdClase());
				ob.setEstadoClase(auxOb.getEstadoClase());
				ob.setActualizadoPorClase(autor);
				
				claseServicio.actualizar(ob);
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
	
	//------------------------------------aseguradora vehiculo
	
	@PostMapping("/aseguradora")
	public ResponseEntity<?> crearAseguradora(@RequestParam("accessToken") String accessToken,@RequestBody Aseguradora ob){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				ob.setRegistradoPorAseguradora(autor);
				Set<ConstraintViolation<Aseguradora>> validate = validator.validate(ob);
				if(validate.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Aseguradora> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				aseguradoraServicio.crear(ob);
				
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
	
	@GetMapping("/aseguradora/listar")
	public ResponseEntity<?> listarAseguradora(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Page<Object[]> page=aseguradoraServicio.listar(pr);
				List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					HashMap<String, Object> objeto = new HashMap<String, Object>();
					objeto.put("idAseguradora", o[0]);
					objeto.put("nombreAseguradora", o[1]);
					objeto.put("estadoAseguradora", o[2]);
					lista.add(objeto);
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("aseguradoras", lista);
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
	
	@GetMapping("/aseguradora/{id}")
	public ResponseEntity<?> obtenerAseguradoraPorId(@PathVariable("id") int id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Aseguradora ob=aseguradoraServicio.obtenerPorId(id);
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
	
	@GetMapping("/aseguradora")
	public ResponseEntity<?> obtenerTodosAseguradora(){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			return ResponseEntity.ok().body(aseguradoraServicio.obtenerTodos());
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping("/aseguradora")
	public ResponseEntity<?> editarAseguradora(@RequestParam("accessToken") String accessToken,@RequestBody Aseguradora auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				Aseguradora ob=aseguradoraServicio.obtenerPorId(auxOb.getIdAseguradora());
				ob.setNombreAseguradora(auxOb.getNombreAseguradora());
				ob.setEstadoAseguradora(auxOb.getEstadoAseguradora());
				ob.setActualizadoPorAseguradora(autor);
				Set<ConstraintViolation<Aseguradora>> validate = validator.validate(ob);
				if(validate.size()>0 ) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					
					for (ConstraintViolation<Aseguradora> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					
					return ResponseEntity.ok().body(responses);
				}
				aseguradoraServicio.actualizar(ob);
				
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
	
	@PutMapping("/aseguradora/estado")
	public ResponseEntity<?> editarEstadoAseguradora(@RequestParam("accessToken") String accessToken,@RequestBody Aseguradora auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				Aseguradora ob=aseguradoraServicio.obtenerPorId(auxOb.getIdAseguradora());
				ob.setEstadoAseguradora(auxOb.getEstadoAseguradora());
				ob.setActualizadoPorAseguradora(autor);
				
				aseguradoraServicio.actualizar(ob);
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
	
	//----------------------------------empresa Convenio vehiculo
	
	@PostMapping("/empresaconvenio")
	public ResponseEntity<?> crearEmpresaConvenio(@RequestParam("accessToken") String accessToken,@RequestBody EmpresaConvenio ob){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				ob.setRegistradoPorEmpresaConvenio(autor);
				Set<ConstraintViolation<EmpresaConvenio>> validate = validator.validate(ob);
				if(validate.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<EmpresaConvenio> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				empresaConvenioServicio.crear(ob);
				
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
	
	@GetMapping("/empresaconvenio/{id}")
	public ResponseEntity<?> obtenerEmpresaConvenioPorId(@PathVariable("id") int id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				EmpresaConvenio ob=empresaConvenioServicio.obtenerPorId(id);
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
	
	@GetMapping("/empresaconvenio/listar")
	public ResponseEntity<?> listarEmpresaConvenio(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Page<Object[]> page=empresaConvenioServicio.listar(pr);
				List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					HashMap<String, Object> objeto = new HashMap<String, Object>();
					objeto.put("idEmpresaConvenio", o[0]);
					objeto.put("nombreEmpresaConvenio", o[1]);
					objeto.put("nitEmpresaConvenio", o[2]);
					objeto.put("estadoEmpresaConvenio", o[3]);
					lista.add(objeto);
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("empresasConvenio", lista);
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
	
	@GetMapping("/empresaconvenio")
	public ResponseEntity<?> obtenerTodosEmpresaConvenio(){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			return ResponseEntity.ok().body(empresaConvenioServicio.obtenerTodos());
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping("/empresaconvenio")
	public ResponseEntity<?> editarEmpresaConvenio(@RequestParam("accessToken") String accessToken,@RequestBody EmpresaConvenio auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				EmpresaConvenio ob=empresaConvenioServicio.obtenerPorId(auxOb.getIdEmpresaConvenio());
				ob.setNombreEmpresaConvenio(auxOb.getNombreEmpresaConvenio());
				ob.setNitEmpresaConvenio(auxOb.getNitEmpresaConvenio());
				ob.setEstadoEmpresaConvenio(auxOb.getEstadoEmpresaConvenio());
				ob.setActualizadoPorEmpresaConvenio(autor);
				Set<ConstraintViolation<EmpresaConvenio>> validate = validator.validate(ob);
				if(validate.size()>0 ) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					
					for (ConstraintViolation<EmpresaConvenio> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					
					return ResponseEntity.ok().body(responses);
				}
				empresaConvenioServicio.actualizar(ob);
				
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
	
	@PutMapping("/empresaconvenio/estado")
	public ResponseEntity<?> editarEstadoEmpresaConvenio(@RequestParam("accessToken") String accessToken,@RequestBody EmpresaConvenio auxOb){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				EmpresaConvenio ob=empresaConvenioServicio.obtenerPorId(auxOb.getIdEmpresaConvenio());
				ob.setEstadoEmpresaConvenio(auxOb.getEstadoEmpresaConvenio());
				ob.setActualizadoPorEmpresaConvenio(autor);
				
				empresaConvenioServicio.actualizar(ob);
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
	
	//----------------------------------Convenio vehiculo
	
	@PostMapping("/convenio")
	public ResponseEntity<?> crearConvenio(@RequestParam("accessToken") String accessToken,@RequestBody Convenio ob){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Persona autor=new Persona();
				autor.setIdPersona(id);
				ob.setRegistradoPorConvenio(autor);
				Set<ConstraintViolation<Convenio>> validate = validator.validate(ob);
				if(validate.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Convenio> item : validate) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				convenioServicio.crear(ob);
				
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
	public ResponseEntity<?> filtrarVehiculos(@RequestParam("accessToken") String accessToken, @RequestParam("criterio") String criterio, @RequestParam("valor") String valor)
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
					case "np":
						System.out.println("buscando por placa : ");
						lista = vehiculoServicio.listarPorNombre(0,valor);
						return ResponseEntity.ok().body(lista);
					case "cd":
						System.out.println("buscando por placa : ");
						lista = vehiculoServicio.listarPorCodigo(0,valor);
						return ResponseEntity.ok().body(lista);
					case "pv":
						System.out.println("buscando por placa : ");
						lista = vehiculoServicio.listarPorPlaca(0,valor);
						return ResponseEntity.ok().body(lista);
					case "cv":
						System.out.println("buscando por placa : ");
						lista = vehiculoServicio.listarPorTipoVehiculo(0,valor);
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
	
	@GetMapping("/tipoCombustible/filtrar")
	public ResponseEntity<?> filtrarTipoCombustible(@RequestParam("accessToken") String accessToken,@RequestParam("criterio") String criterio, @RequestParam("valor") Integer valor){
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
						lista = tipoCombustibleServicio.filtrarPorEstado(valor);
						return ResponseEntity.ok().body(lista);
					case "ce":
						System.out.println("buscando por placa : ");
						lista = tipoCombustibleServicio.filtrarPorCodigo(valor);
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
	
	@GetMapping("/tipoVehiculo/filtrar")
	public ResponseEntity<?> filtrarTipoVehiculo(@RequestParam("accessToken") String accessToken,@RequestParam("criterio") String criterio, @RequestParam("valor") Integer valor){
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
						lista = tipoVehiculoServicio.filtrarPorEstado(valor);
						return ResponseEntity.ok().body(lista);
					case "ce":
						System.out.println("buscando por placa : ");
						lista = tipoVehiculoServicio.filtrarPorCodigo(valor);
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
	
	@GetMapping("/marca/filtrar")
	public ResponseEntity<?> filtrarMarca(@RequestParam("accessToken") String accessToken,@RequestParam("criterio") String criterio, @RequestParam("valor") Integer valor){
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
						lista = marcaServicio.filtrarPorEstado(valor);
						return ResponseEntity.ok().body(lista);
					case "ce":
						System.out.println("buscando por placa : ");
						lista = marcaServicio.filtrarPorCodigo(valor);
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
	
	@GetMapping("/aseguradora/filtrar")
	public ResponseEntity<?> filtrarAseguradora(@RequestParam("accessToken") String accessToken,@RequestParam("criterio") String criterio, @RequestParam("valor") Integer valor){
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
						lista = aseguradoraServicio.filtrarPorEstado(valor);
						return ResponseEntity.ok().body(lista);
					case "ce":
						System.out.println("buscando por placa : ");
						lista = aseguradoraServicio.filtrarPorCodigo(valor);
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
	
	@GetMapping("/empresaConvenio/filtrar")
	public ResponseEntity<?> filtrarEmpresaConvenio(@RequestParam("accessToken") String accessToken,@RequestParam("criterio") String criterio, @RequestParam("valor") String valor){
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
						lista = empresaConvenioServicio.filtrarPorEstado(Integer.valueOf(valor));
						return ResponseEntity.ok().body(lista);
					case "ce":
						System.out.println("buscando por placa : ");
						lista = empresaConvenioServicio.filtrarPorCodigo(Integer.valueOf(valor));
						return ResponseEntity.ok().body(lista);
					case "nit":
						System.out.println("buscando por placa : ");
						lista = empresaConvenioServicio.filtrarPorNit(valor);
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
	
	@GetMapping("/clase/filtrar")
	public ResponseEntity<?> filtrarClase(@RequestParam("accessToken") String accessToken,@RequestParam("criterio") String criterio, @RequestParam("valor") Integer valor){
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
						lista = claseServicio.filtrarPorEstado(valor);
						return ResponseEntity.ok().body(lista);
					case "ce":
						System.out.println("buscando por placa : ");
						lista = claseServicio.filtrarPorCodigo(valor);
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
	@PostMapping("/vehiculosExcel")
	public ResponseEntity<?> crearVehiculosExcel(@RequestBody POJOVehiculoExcel vehiculos, @RequestParam("accessToken") String accessToken)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Persona autor=new Persona();
				autor.setIdPersona(id);
				List<Vehiculo> vehiculosPojo = vehiculos.getVehiculosExcel();
				Persona persona = personaServicio.obtenerPorId(id);
				for (Vehiculo vehiculo : vehiculosPojo) {
					vehiculo.setAccion("creación de reserva");
					vehiculo.setModificado(persona);
					vehiculo.setFechaModificado(java.time.LocalDate.now().toString());
					vehiculo.setFechaRegistroVehiculo(java.time.LocalDate.now().toString());
					vehiculo.setRegistradoPorVehiculo(persona);
					
					
					vehiculoServicio.crear(vehiculo);
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Soat soat = new Soat();
					soat.setVehiculo(vehiculo);
					soatServicio.crear(soat);
					TarjetaOperacion tarjetaOperacion = new TarjetaOperacion();
					tarjetaOperacion.setVehiculo(vehiculo);
					tarjetaOperacionServicio.crear(tarjetaOperacion);
					RevisionPreventiva revisionPreventiva = new RevisionPreventiva();
					revisionPreventiva.setVehiculo(vehiculo);
					revisionPreventivaServicio.crear(revisionPreventiva);
					RevisionTecnicomecanica revisionTecnicomecanica = new RevisionTecnicomecanica();
					revisionTecnicomecanica.setVehiculo(vehiculo);
					revisionTecnicomecanicaServicio.crear(revisionTecnicomecanica);
					PolizaContractual polizaContractual = new PolizaContractual();
					polizaContractual.setVehiculo(vehiculo);
					polizaContractualServicio.crear(polizaContractual);
					PolizaExtracontractual polizaExtracontractual = new PolizaExtracontractual();
					polizaExtracontractual.setVehiculo(vehiculo);
					polizaExtracontractualServicio.crear(polizaExtracontractual);
					PolizaTodoRiesgo polizaTodoRiesgo = new PolizaTodoRiesgo();
					polizaTodoRiesgo.setVehiculo(vehiculo);
					polizaTodoRiesgoService.guardarPolizaTodoRiesgo(polizaTodoRiesgo);
					DocumentoVehiculo documentoVehiculo=new DocumentoVehiculo();
					documentoVehiculo.setVehiculo(vehiculo);
					documentoVehiculoServicio.crear(documentoVehiculo);
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

}