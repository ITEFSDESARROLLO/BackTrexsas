package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itefs.trexsas.modelo.Cuenta;
import com.itefs.trexsas.modelo.Pasajero;
import com.itefs.trexsas.modelo.Persona;
import com.itefs.trexsas.modelo.Servicio;
import com.itefs.trexsas.repositorio.ClienteRepositorio;
import com.itefs.trexsas.repositorio.CuentaRepositorio;
import com.itefs.trexsas.repositorio.PasajeroRepositorio;
import com.itefs.trexsas.repositorio.PersonaRepositorio;
import com.itefs.trexsas.repositorio.ServicioRepositorio;

@Service
public class ServicioServicio {
	
	@Autowired
	private ServicioRepositorio reservaRepositorio;
	@Autowired
	private PersonaRepositorio personaRepositorio;
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	@Autowired
	private PasajeroRepositorio pasajeroRespositorio;
	@Autowired
	private CuentaRepositorio cuentaRepositorio;
	
	
	public Servicio crear(Servicio reserva) {
		reserva.setIdReserva(null);
		return reservaRepositorio.save(reserva);
	}
	
	public void actualizar(Servicio reserva) {
		reservaRepositorio.save(reserva);
	}
	
	public List<Servicio> obtenerTodos() {
		return reservaRepositorio.findAll();
	}
	
	public List<Object[]> listar()
	{
		List<Object[]> page= reservaRepositorio.listarReservas();
		return page;
	}
	
	public List<Object[]> listarReservasPasajero(String usuario) throws Exception
	{
		Optional<Cuenta> cuenta = cuentaRepositorio.encontrarPorUsername(usuario);
		if(cuenta.isPresent())
		{
			List<Object[]> page= reservaRepositorio.listarReservasPasajero(cuenta.get().getPersona().getDocumentoPersona());
			return page;
		}else
		{
			throw new Exception();
		}
		
	}
	
	public Page<Object[]> listarPorConductor(int inicio,String documento) 
	{
		System.out.println("documento : "+documento);
		System.out.println("3 : ");
		Integer idConductor = reservaRepositorio.obtenerIdConductor(documento);
		System.out.println("4 : ");
		System.out.println("reds : "+idConductor);
		Page<Object[]> page= reservaRepositorio.listarReservasPaginadoPorConductor(PageRequest.of(inicio, 10),idConductor);
		return page;
	}
	
	public Page<Object[]> listarPorDisponibles(Integer inicio) 
	{
		Page<Object[]> page= reservaRepositorio.listarReservasDisponibles(PageRequest.of(inicio, 10));
		return page;
	}
	
	public Servicio obtenerPorId(Long id) {
		Optional<Servicio> op=reservaRepositorio.findById(id);
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}
	
	public void eliminar(Servicio reserva) {
		reservaRepositorio.delete(reserva);
	}
	
	public void eliminarById(long id) {
		reservaRepositorio.deleteById(id);
	}
	
	
	public List<Object[]> infoBasica() {
		List<Object[]> list= reservaRepositorio.obtenerInfoBasica();
		return list;
	}
	
	public List<Object[]> idOSyIdCdeReserva(Long id) {
		List<Object[]> list= reservaRepositorio.idOSyIdCdeReserva(id);
		return list;
	}
	
	public List<Servicio> buscarPorEstadoDeReserva(int id)
	{
		List<Servicio> list= reservaRepositorio.findByEstadoReserva(id);
		return list;
	}
	
	public List<Servicio> buscarPorNummeroVoucher(String voucher)
	{
		System.out.println("trayendo reservas desde el servicio por nv");
		List<Servicio> list= reservaRepositorio.findByNumeroVoucher(voucher);
		return list;
	}
	
	public HashMap<String,Object> listarPorNumeroVoucher(int inicio, String voucher) 
	{
		System.out.println("buscando por voucher");
		long total = reservaRepositorio.count();
		Page<Object[]> page= reservaRepositorio.listarReservasPaginadoVoucher(PageRequest.of(inicio, (int)total));
		System.out.println(page.getSize());
		List<HashMap<String, Object>> obs = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> response = new HashMap<String, Object>();
		for (Object[] objects : page.getContent()) 
		{
			System.out.println("encontrado : "+objects[9].toString());
			if(objects[9].equals(voucher))
			{
				System.out.println("encontrado : "+objects[9].toString());
				HashMap<String, Object> ob = new HashMap<String, Object>();
				ob.put("idReserva", objects[0]);
				ob.put("nombrePasajero", objects[1]);
				ob.put("apellidoPasajero", objects[2]);
				ob.put("fechaInicioReserva", objects[3]);
				ob.put("direccionOrigenReserva", objects[4]);
				ob.put("direccionDestinoReserva", objects[5]);
				ob.put("distanciaReserva", objects[6]);
				ob.put("duracionReserva", objects[7]);
				ob.put("estadoReserva", objects[8]);
				ob.put("numeroVoucher", objects[9]);
				ob.put("nombreConductor", objects[10]);
				ob.put("apellidoConductor", objects[11]);
				ob.put("placaVehiculo", objects[12]);
				ob.put("idOrdenServicio", objects[13]);
				obs.add(ob);
			}
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("reservas",obs);
		return response;
	}
	
	public HashMap<String,Object> listarPorIdPasajero(int inicio, String idPasajero) {
		System.out.println("llamando listarPasajero");
		Page<Object[]> page= reservaRepositorio.listarReservasPaginadoDocumentoPasajero(PageRequest.of(inicio, 10));
		List<HashMap<String, Object>> obs = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> response = new HashMap<String, Object>();
		String pasajero = this.buscaridPasajero(idPasajero);
		for (Object[] objects : page.getContent()) 
		{
			System.out.println("identificacion : "+objects[9]);
			if(objects[9].toString().equals(pasajero))
			{
				HashMap<String, Object> ob = new HashMap<String, Object>();
				ob.put("idReserva", objects[0]);
				ob.put("nombrePasajero", objects[1]);
				ob.put("apellidoPasajero", objects[2]);
				ob.put("fechaInicioReserva", objects[3]);
				ob.put("direccionOrigenReserva", objects[4]);
				ob.put("direccionDestinoReserva", objects[5]);
				ob.put("distanciaReserva", objects[6]);
				ob.put("duracionReserva", objects[7]);
				ob.put("estadoReserva", objects[8]);
				ob.put("idPasajero", objects[9]);
				ob.put("nombreConductor", objects[10]);
				ob.put("apellidoConductor", objects[11]);
				ob.put("placaVehiculo", objects[12]);
				ob.put("idOrdenServicio", objects[13]);
				obs.add(ob);
			}
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		System.out.println("total de paginas :"+page.getTotalPages());
		response.put("reservas",obs);
		return response;
	}
	
	public HashMap<String,Object> listarPorEstado(int inicio, int estado) {
		System.out.println("llamando listarPasajero");
		Page<Object[]> page= reservaRepositorio.listarReservasPaginadoEstado(PageRequest.of(inicio, 10));
		List<HashMap<String, Object>> obs = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> response = new HashMap<String, Object>();
		for (Object[] objects : page.getContent()) 
		{
			System.out.println("identificacion : "+objects[9]);
			if(Integer.parseInt(objects[9].toString())==estado)
			{
				HashMap<String, Object> ob = new HashMap<String, Object>();
				ob.put("idReserva", objects[0]);
				ob.put("nombrePasajero", objects[1]);
				ob.put("apellidoPasajero", objects[2]);
				ob.put("fechaInicioReserva", objects[3]);
				ob.put("direccionOrigenReserva", objects[4]);
				ob.put("direccionDestinoReserva", objects[5]);
				ob.put("distanciaReserva", objects[6]);
				ob.put("duracionReserva", objects[7]);
				ob.put("estadoReserva", objects[8]);
				ob.put("estado", objects[9]);
				ob.put("nombreConductor", objects[10]);
				ob.put("apellidoConductor", objects[11]);
				ob.put("placaVehiculo", objects[12]);
				ob.put("idOrdenServicio", objects[13]);
				obs.add(ob);
			}
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		System.out.println("total de paginas :"+page.getTotalPages());
		response.put("reservas",obs);
		return response;
	}
	
	public HashMap<String,Object> listarPacientesContrato(Long contrato) {
		System.out.println("llamando listarPasajero");
		List<Object[]> page= pasajeroRespositorio.listarPasajeros(contrato);
		List<HashMap<String, Object>> obs = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> response = new HashMap<String, Object>();
		for (Object[] objects : page) 
		{
			//System.out.println("identificacion : "+objects[9]);
			HashMap<String, Object> ob = new HashMap<String, Object>();
			ob.put("idPasajero", objects[0]);
			ob.put("nombrePersona", objects[1]);
			ob.put("apellidoPersona", objects[2]);
			ob.put("documentoPersona", objects[3]);
			obs.add(ob);
			
		}
		response.put("pasajeros",obs);
		return response;
	}
	
	public HashMap<String,Object> listarPorOrigen(int inicio, String origen) {
		System.out.println("llamando listarPasajero");
		Page<Object[]> page= reservaRepositorio.listarReservasPaginado(PageRequest.of(inicio, 10));
		List<HashMap<String, Object>> obs = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> response = new HashMap<String, Object>();
		for (Object[] objects : page.getContent()) 
		{
			System.out.println("identificacion : "+objects[4]);
			if(objects[4].toString().equals(origen))
			{
				HashMap<String, Object> ob = new HashMap<String, Object>();
				ob.put("idReserva", objects[0]);
				ob.put("nombrePasajero", objects[1]);
				ob.put("apellidoPasajero", objects[2]);
				ob.put("fechaInicioReserva", objects[3]);
				ob.put("direccionOrigenReserva", objects[4]);
				ob.put("direccionDestinoReserva", objects[5]);
				ob.put("distanciaReserva", objects[6]);
				ob.put("duracionReserva", objects[7]);
				ob.put("estadoReserva", objects[8]);
				ob.put("nombreConductor", objects[9]);
				ob.put("apellidoConductor", objects[10]);
				ob.put("placaVehiculo", objects[11]);
				ob.put("idOrdenServicio", objects[12]);
				obs.add(ob);
			}
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		System.out.println("total de paginas :"+page.getTotalPages());
		response.put("reservas",obs);
		return response;
	}
	
	public HashMap<String,Object> listarPorDestino(int inicio, String destino) {
		System.out.println("llamando listarPasajero");
		Page<Object[]> page= reservaRepositorio.listarReservasPaginado(PageRequest.of(inicio, 10));
		List<HashMap<String, Object>> obs = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> response = new HashMap<String, Object>();
		for (Object[] objects : page.getContent()) 
		{
			System.out.println("identificacion : "+objects[5]);
			if(objects[5].toString().equals(destino))
			{
				HashMap<String, Object> ob = new HashMap<String, Object>();
				ob.put("idReserva", objects[0]);
				ob.put("nombrePasajero", objects[1]);
				ob.put("apellidoPasajero", objects[2]);
				ob.put("fechaInicioReserva", objects[3]);
				ob.put("direccionOrigenReserva", objects[4]);
				ob.put("direccionDestinoReserva", objects[5]);
				ob.put("distanciaReserva", objects[6]);
				ob.put("duracionReserva", objects[7]);
				ob.put("estadoReserva", objects[8]);
				ob.put("nombreConductor", objects[9]);
				ob.put("apellidoConductor", objects[10]);
				ob.put("placaVehiculo", objects[11]);
				ob.put("idOrdenServicio", objects[12]);
				obs.add(ob);
			}
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		System.out.println("total de paginas :"+page.getTotalPages());
		response.put("reservas",obs);
		return response;
	}
	
	public List<Servicio> buscarPorFechaReserva(String fecha){
		List<Servicio> listaReservas = reservaRepositorio.findAll();
		List<Servicio> listaReservasPorFecha = new ArrayList<Servicio>();
		String fechaReserva = "";
		for(Servicio reserva : listaReservas)
		{
			fechaReserva = reserva.getFechaSolicitudReserva();
			if(fechaReserva.equals(fecha))
			{
				listaReservasPorFecha.add(reserva);
			}
		}
		
		return listaReservasPorFecha;
	}
	
	public String buscaridPasajero(String id)
	{
		System.out.println("buscando pasajero");
		Persona objetoPersona = personaRepositorio.findByDocumentoPersona(id);
		if(objetoPersona!=null)
		{
			System.out.println("se encontró a la persona"+objetoPersona.getDocumentoPersona());
			Pasajero objetoPasajero = pasajeroRespositorio.findByPersona(objetoPersona);
			if(objetoPasajero!=null)
			{
				System.out.println("se encontró pasajero");
				return String.valueOf(objetoPasajero.getIdPasajero());
			}else
			{
				System.out.println("no se encontró al pasajero");
				return null;
				
			}
		}else
		{
			System.out.println("no se encontró a la persona");
			return null;
			
		}
	}
	
	public List<Servicio> buscarPorOrigen(String origen)
	{
		List<Servicio> list= reservaRepositorio.findByDireccionOrigenReserva(origen);
		return list;
	}
	
	public List<Servicio> buscarPorDestino(String destino)
	{
		List<Servicio> list= reservaRepositorio.findByDireccionDestinoReserva(destino);
		return list;
	}
	
	public List<Servicio> guardarReservasExcel(Object[] lista)
	{
		Object[] listaParaGuardar = lista;
		ObjectMapper mapper = new ObjectMapper();
		try
		{
			for (Object object : listaParaGuardar) 
			{
				List<String> valores = new ArrayList<String>();
				String stringJSON = mapper.writeValueAsString(object).replace("{", "").replace("}", "");
				String parts[] = stringJSON.split(",");
				for(String part : parts){
		            System.out.println("parte : "+part);
		            //split the employee data by : to get id and name
		            String empdata[] = part.split(":");
		            String strName = empdata[1];
		            
		            //add to map
		            valores.add(strName);
		        }
				Servicio reserva = new Servicio();
				reserva.setCliente(clienteRepositorio.findByRazonSocialCliente(valores.get(0)));
				//Persona persona = personaRepositorio.findByNombrePersonaAndApellidoPersona(stringJSON, stringJSON)
				//reserva.setPasajero(personaRepositorio.);
			}
		}catch(Exception e)
		{
			
		}
		
		
        
        //split the String by a comma
       
        
        //iterate the parts and add them to a map
        
        //System.out.println("String to HashMap: " +valores);
        //System.out.println("String to HashMap: " + valores.get(0));
		return reservaRepositorio.findAll();
 	}
	
	public List<String> listaDireccionesOrigenReservas()
	{
		return reservaRepositorio.listarDireccionesReservasOrigen();
	}
	
	public List<String> listaDireccionesDestinoReservas()
	{
		return reservaRepositorio.listarDireccionesReservasDestino();
	}
	
	
	public void cambiarEstadoReserva(long id, int estado)
	{
		Optional<Servicio> objetoReserva = reservaRepositorio.findById(id);
		if(objetoReserva.isPresent())
		{
			Servicio reserva = objetoReserva.get();
			reserva.setEstadoReserva(estado);
		}else
		{
			
		}
	}
	
	public Page<Object[]> editarReserva(int inicio,Servicio reserva)
	{
		reservaRepositorio.save(reserva);
		Page<Object[]> page= reservaRepositorio.listarReservasPaginado(PageRequest.of(inicio, 10));
		return page;
	}
	
	public List<HashMap<String, Object>> datosExcel(List<Long> identificadoresServicios)
	{
		List<Long> identificadores = identificadoresServicios;
		List<Object[]> serviciosSistema = reservaRepositorio.traerServiciosExcel();
		List<HashMap<String, Object>> serviciosSalida = new ArrayList<>();
		int indice = 0;
		for (Long servicioId : identificadores)
		{
			for (Object[] objects : serviciosSistema)
			{
				if(objects != null)
				{
					if(Long.valueOf(String.valueOf(objects[0]))==servicioId)
					{
						HashMap<String, Object> servicio = new HashMap<>();
						servicio.put("Fecha Inicio Reserva",objects[1] );
						servicio.put("Hora",objects[13]);
						servicio.put("Direccion Origen Reserva", objects[2]);
						servicio.put("Direccion Destino Reserva", objects[3]);
						servicio.put("Estado", objects[4]);
						servicio.put("Observaciones", objects[5]);
						servicio.put("Cliente", objects[6]);
						servicio.put("Valor Orden De Servicio", objects[7]);
						servicio.put("Valor Conductor", objects[8]);
						servicio.put("Placa", objects[9]);
						servicio.put("Pasajero ", objects[10]);
						servicio.put("Conductor ", objects[12]);
						serviciosSalida.add(servicio);
						indice = serviciosSistema.indexOf(objects);
						serviciosSistema.set(indice,null);
						break;
					}
				}
			}
		}
		return serviciosSalida;
	}
}
