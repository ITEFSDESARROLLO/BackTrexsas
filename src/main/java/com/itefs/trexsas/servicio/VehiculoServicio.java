package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Clase;
import com.itefs.trexsas.modelo.ColorVehiculo;
import com.itefs.trexsas.modelo.EntidadTransito;
import com.itefs.trexsas.modelo.LineaMarca;
import com.itefs.trexsas.modelo.Persona;
import com.itefs.trexsas.modelo.Propietario;
import com.itefs.trexsas.modelo.TipoCarroceria;
import com.itefs.trexsas.modelo.TipoServicio;
import com.itefs.trexsas.modelo.Vehiculo;
import com.itefs.trexsas.modelo.VehiculoConductor;
import com.itefs.trexsas.repositorio.ColorVehiculoRepository;
import com.itefs.trexsas.repositorio.EntidadTransitoRepository;
import com.itefs.trexsas.repositorio.LineaMarcaRepository;
import com.itefs.trexsas.repositorio.PersonaRepositorio;
import com.itefs.trexsas.repositorio.PropietarioRepositorio;
import com.itefs.trexsas.repositorio.TipoCarroceriaRepository;
import com.itefs.trexsas.repositorio.TiposServicioRepository;
import com.itefs.trexsas.repositorio.VehiculoConductorRepositorio;
import com.itefs.trexsas.repositorio.VehiculoRepositorio;

@Service
public class VehiculoServicio {
	
	@Autowired
	private VehiculoRepositorio vehiculoRepositorio;
	
	@Autowired
	private PersonaRepositorio personaRepositorio;
	
	@Autowired 
	private PropietarioRepositorio propietarioRepositorio;
	
	@Autowired
	private TiposServicioRepository tss;
	
	@Autowired
	private TipoCarroceriaRepository tcs;
	
	@Autowired
	private ColorVehiculoRepository cvs;
	
	@Autowired
	private EntidadTransitoRepository ets;
	
	@Autowired
	private LineaMarcaRepository lmr;
	
	@Autowired
	private VehiculoConductorRepositorio vcr;
	
	
	public void crear(Vehiculo vehiculo) {
		vehiculo.setIdVehiculo(null);
		Vehiculo guardado = vehiculoRepositorio.save(vehiculo);
		System.out.println("guardado : "+guardado.getPlacaVehiculo());
	}
	
	public void actualizar(Vehiculo vehiculo) {
		vehiculoRepositorio.save(vehiculo);
	}
	
	public List<Vehiculo> obtenerTodos() {
		return vehiculoRepositorio.findAll();
	}
	
	public Vehiculo obtenerPorId(int id) {
		List<Vehiculo> vehiculos = vehiculoRepositorio.findAll();
		Vehiculo vehiculoSalida = null;
		for (Vehiculo vehiculo : vehiculos)
		{
			if(vehiculo.getIdVehiculo() == id)
			{
				vehiculoSalida = vehiculo;
				break;
			}
		}
		return vehiculoSalida;
		
	}
	
	public List<HashMap<String,Object>> todos()
	{
		List<Vehiculo> vehiculoSistema = vehiculoRepositorio.findAll();
		List<HashMap<String,Object>> vehiculos = new ArrayList<HashMap<String,Object>>();
		for(Vehiculo o:vehiculoSistema) {
			HashMap<String, Object> vehiculo = new HashMap<String, Object>();
			vehiculo.put("idVehiculo", o.getIdVehiculo());
			vehiculo.put("codigoInternoVehiculo", o.getCodigoInternoVehiculo());
			vehiculo.put("placaVehiculo", o.getPlacaVehiculo());
			if(o.getClase() == null)
			{
				Clase clase = new Clase();
				o.setClase(clase);
			}
			vehiculo.put("clase", o.getClase());
			vehiculo.put("estadoVehiculo", o.getEstadoVehiculo());
			vehiculo.put("nombrePersona", o.getPropietario().getNombrePersona());
			vehiculo.put("apellidoPersona", o.getPropietario().getApellidoPersona());
			vehiculo.put("numeroPasajerosVehiculo", o.getNumeroPasajerosVehiculo());
			vehiculos.add(vehiculo);
		}
		return vehiculos;
	}
	
	public List<HashMap<String,Object>> todosExcel(PojoVehiculosExcel listadoIdsVehiculos)
	{
		List<Vehiculo> vehiculoSistema = vehiculoRepositorio.findAll();
		List<HashMap<String,Object>> vehiculos = new ArrayList<HashMap<String,Object>>();
		
		List<Integer> idsVehiculos = listadoIdsVehiculos.getIdsVehiculosExcel();
		for (Integer id : idsVehiculos) 
		{
			for(Vehiculo o:vehiculoSistema){
				if(o!=null)
				{
					if(o.getIdVehiculo() == id)
					{
						HashMap<String, Object> vehiculo = new HashMap<String, Object>();
						vehiculo.put("Codigo Interno", o.getCodigoInternoVehiculo());
						vehiculo.put("Placa", o.getPlacaVehiculo());
						if(o.getClase() == null)
						{
							Clase clase = new Clase();
							o.setClase(clase);
						}
						vehiculo.put("Clase Vehículo", o.getClase().getClase());
						String estadoVehiculo = "";
						if(o.getEstadoVehiculo()==0)
						{
							estadoVehiculo = "DESHABILITADO";
						}else if(o.getEstadoVehiculo() ==1)
						{
							estadoVehiculo = "HABILITADO";
						}else if(o.getEstadoVehiculo() ==2)
						{
							estadoVehiculo = "ELIMINADO";
						}else if(o.getEstadoVehiculo() ==6)
						{
							estadoVehiculo = "POR LEGALIZAR";
						}
						vehiculo.put("estadoVehiculo", estadoVehiculo);
						vehiculo.put("Propietario", o.getPropietario().getNombrePersona()+" "+o.getPropietario().getApellidoPersona());
						vehiculo.put("Cupo Vehículo", o.getNumeroPasajerosVehiculo());
						vehiculos.add(vehiculo);
						int indice = vehiculoSistema.indexOf(o);
						vehiculoSistema.set(indice,null);
						break;
					}
				}
			}
		}
		
		
		return vehiculos;
	}
	
	
	public List<Object[]> listar() {
		List<Object[]> page= vehiculoRepositorio.listarVehiculosPaginados();
		return page;
	}
	public List<Object[]> listar2() {
		List<Object[]> page= vehiculoRepositorio.listarVehiculosPaginados2();
		return page;
	}
	
	public List<Object[]> listarPropietario(String token) {
		List<Object[]> page= vehiculoRepositorio.listarVehiculosPropietarioPaginados(token);
		return page;
	}
	
	public List<Object[]> listarPropietario2(String token) {
		List<Object[]> page= vehiculoRepositorio.listarVehiculosPropietarioPaginados2(token);
		return page;
	}
	
	public List<Object[]> listarConductor(String token) {
		List<Object[]> page= vehiculoRepositorio.listarVehiculosConductorPaginados(token);
		return page;
	}
	
	public List<Object[]> listarConductor2(String token) {
		List<Object[]> page= vehiculoRepositorio.listarVehiculosConductorPaginados2(token);
		return page;
	}
	
	public List<Object[]> infoBasicaDePropietario(String token) {
		List<Object[]> list= vehiculoRepositorio.obtenerInfoBasicaDePropietario(token);
		return list;
	}
	
	public List<Object[]> infoBasicaSalud() {
		List<Object[]> list= vehiculoRepositorio.obtenerInfoBasicaSalud();
		return list;
	}
	
	public List<Object[]> infoBasicaTurismo() {
		List<Object[]> list= vehiculoRepositorio.obtenerInfoBasicaTurismo();
		return list;
	}
	
	public List<String> obtenerPlacas() {
		return vehiculoRepositorio.obtenerPlacas();
	}
	
	public void eliminar(Vehiculo vehiculo) {
		vehiculoRepositorio.delete(vehiculo);
	}
	
	public void eliminar(Integer conductor)
	{
		VehiculoConductor vConductor = vcr.traerPorConductor(conductor);
		if(vConductor!=null)
		{
			vcr.delete(vConductor);
		}
		
	}
	
	public HashMap<String, Object> listarPorCodigo(int inicio,String codigo)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page= vehiculoRepositorio.listarPorCodigo(PageRequest.of(inicio, 10),codigo);
		List<HashMap<String, Object>> vehiculos = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> vehiculo = new HashMap<String, Object>();
			vehiculo.put("idVehiculo", o[0]);
			vehiculo.put("codigoInternoVehiculo", o[1]);
			vehiculo.put("placaVehiculo", o[2]);
			vehiculo.put("clase", o[3]);
			vehiculo.put("estadoVehiculo", o[4]);
			vehiculo.put("nombrePersona", o[5]);
			vehiculo.put("apellidoPersona", o[6]);
			vehiculos.add(vehiculo);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("vehiculos", vehiculos);
		return response;
	}
	
	public HashMap<String, Object> listarPorPlaca(int inicio,String placa)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page= vehiculoRepositorio.listarPorPlaca(PageRequest.of(inicio, 10),placa);
		List<HashMap<String, Object>> vehiculos = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> vehiculo = new HashMap<String, Object>();
			vehiculo.put("idVehiculo", o[0]);
			vehiculo.put("codigoInternoVehiculo", o[1]);
			vehiculo.put("placaVehiculo", o[2]);
			vehiculo.put("clase", o[3]);
			vehiculo.put("estadoVehiculo", o[4]);
			vehiculo.put("nombrePersona", o[5]);
			vehiculo.put("apellidoPersona", o[6]);
			vehiculos.add(vehiculo);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("vehiculos", vehiculos);
		return response;
	}
	
	public HashMap<String, Object> listarPorTipoVehiculo(int inicio,String tipo)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Integer tipoBusqueda = Integer.valueOf(tipo);
		Page<Object[]> page= vehiculoRepositorio.listarPorTipo(PageRequest.of(inicio, 10),tipoBusqueda);
		List<HashMap<String, Object>> vehiculos = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> vehiculo = new HashMap<String, Object>();
			vehiculo.put("idVehiculo", o[0]);
			vehiculo.put("codigoInternoVehiculo", o[1]);
			vehiculo.put("placaVehiculo", o[2]);
			vehiculo.put("clase", o[3]);
			vehiculo.put("estadoVehiculo", o[4]);
			vehiculo.put("nombrePersona", o[5]);
			vehiculo.put("apellidoPersona", o[6]);
			vehiculos.add(vehiculo);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("vehiculos", vehiculos);
		return response;
	}
	
	public HashMap<String, Object> listarPorNombre(int inicio,String nombre)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		//List<Persona> persona = personaRepositorio.findAll();
		List<Persona> personas = personaRepositorio.findAll();
		String nombrePersonaObtenida = "";
		Long documentoPersona = Long.valueOf("0");
		System.out.println("tamaño "+personas.size());
		for (Persona persona: personas)
		{
			nombrePersonaObtenida = persona.getNombrePersona()+" "+persona.getApellidoPersona();
			System.out.println("persona : "+nombrePersonaObtenida);
			System.out.println("persona : "+nombrePersonaObtenida.equals(nombre));
			;
			System.out.println(nombrePersonaObtenida);
			if(nombrePersonaObtenida.toUpperCase().equals(nombre.toUpperCase()))
			{
				System.out.println("encontrado : "+nombrePersonaObtenida);
				documentoPersona = persona.getIdPersona();
				System.out.println("documento : "+documentoPersona);
				break;
			}
		}
		if(documentoPersona!=Long.valueOf("0"))
		{
			Propietario idPropietario = propietarioRepositorio.obtenerPropietario(documentoPersona);
			System.out.println("id propietario : "+idPropietario.getIdPropietario());
			Page<Object[]> page= vehiculoRepositorio.listarPorDocumento(PageRequest.of(inicio, 10),idPropietario.getIdPropietario());
			List<HashMap<String, Object>> vehiculos = new ArrayList<HashMap<String, Object>>();
			for(Object[] o:page.getContent()) {
				HashMap<String, Object> vehiculo = new HashMap<String, Object>();
				vehiculo.put("idVehiculo", o[0]);
				vehiculo.put("codigoInternoVehiculo", o[1]);
				vehiculo.put("placaVehiculo", o[2]);
				vehiculo.put("clase", o[3]);
				vehiculo.put("estadoVehiculo", o[4]);
				vehiculo.put("nombrePersona", o[5]);
				vehiculo.put("apellidoPersona", o[6]);
				vehiculos.add(vehiculo);
			}
			response.put("totalResultados", page.getTotalElements());
			response.put("totalPaginas", page.getTotalPages());
			response.put("vehiculos", vehiculos);
		}else
		{
			response.put("mensaje","error");
		}
		return response;
	}
	
	public List<TipoCarroceria> obtenerTiposCarrocerias()
	{
		return tcs.findAll();
	}
	
	public List<TipoServicio> obtenerTiposServicio()
	{
		return tss.findAll();
	}
	
	public List<ColorVehiculo> obtenerColoresVehiculos()
	{
		return cvs.findAll();
	}
	
	public List<EntidadTransito> obtenerEntidadesTransito()
	{
		return ets.findAll();
	}
	
	public List<LineaMarca> obtenerLineas(Integer marca)
	{
		return lmr.traerLineas(marca);
	}

}
