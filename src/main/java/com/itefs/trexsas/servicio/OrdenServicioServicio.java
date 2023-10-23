package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Conductor;
import com.itefs.trexsas.modelo.OrdenServicio;
import com.itefs.trexsas.repositorio.ConductorRepositorio;
import com.itefs.trexsas.repositorio.OrdenServicioRepositorio;

@Service
public class OrdenServicioServicio {
	
	@Autowired
	private OrdenServicioRepositorio ordenServicioRepositorio;
	
	@Autowired
	private ConductorRepositorio conductorRepositorio;
	
	
	public void crear(OrdenServicio ordenServicio) {
		ordenServicio.setIdOrdenServicio(null);
		ordenServicioRepositorio.save(ordenServicio);
	}
	
	public void actualizar(OrdenServicio ordenServicio) {
		ordenServicioRepositorio.save(ordenServicio);
	}
	
	public List<OrdenServicio> obtenerTodos() {
		return ordenServicioRepositorio.findAll();
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= ordenServicioRepositorio.listarOrdenesServicioPaginado(PageRequest.of(inicio, 10));
		return page;
	}
	
	public List<Object[]> listarOrdenes() {
		List<Object[]> page= ordenServicioRepositorio.listarOrdenesServicio();
		return page;
	}
	
	public List<Object[]> listarOrdenesFecha(String inicio, String fin, String conductor) 
	{
		List<Conductor> listaConductores = conductorRepositorio.findAll();
		Long idConductor = Long.valueOf(0);
		for (Conductor conductor2 : listaConductores)
		{
			String nombreConductor = conductor2.getPersona().getNombrePersona()+" "+conductor2.getPersona().getApellidoPersona();
			if(nombreConductor.equals(conductor))
			{
				idConductor = conductor2.getPersona().getIdPersona();
			}
		}
		List<Object[]> page= ordenServicioRepositorio.listarOrdenesServicioFechas(inicio, fin, idConductor);
		return page;
	}
	
	public HashMap<String, Object> filtrarOrdenesNombreConductor(String inicio, String fin, String conductor)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		List<Object[]> page=ordenServicioRepositorio.listarOrdenesServicioNombreConductor(inicio,fin);
		List<HashMap<String, Object>> obs = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page) 
		{
			String nombre = (String)o[3]+" "+(String)o[4];
			if(nombre.equals(conductor))
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
		}
		response.put("ordenesServicio", obs);
		return response;
	}
	
	public HashMap<String, Object> filtrarOrdenesDocumentoConductor(String inicio, String fin, String conductor)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		List<Object[]> page=ordenServicioRepositorio.listarOrdenesDocumentoConductor(inicio,fin,conductor);
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
		response.put("ordenesServicio", obs);
		return response;
	}
	
	public HashMap<String, Object> filtrarOrdenesCliente(String inicio, String fin, Integer cliente)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		List<Object[]> page=ordenServicioRepositorio.listarOrdenesServicioCliente(inicio,fin,cliente);
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
		response.put("ordenesServicio", obs);
		return response;
	}
	
	public HashMap<String, Object> filtrarOrdenesDocumentoPasajero(String inicio, String fin, Integer cliente)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		List<Object[]> page=ordenServicioRepositorio.listarOrdenesServicioCliente(inicio,fin,cliente);
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
		response.put("ordenesServicio", obs);
		return response;
	}
	
	public HashMap<String, Object> filtrarOrdenesNombrePasajero(String inicio, String fin, String pasajero)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		List<Object[]> page=ordenServicioRepositorio.listarOrdenesServicioNombrePasajero(inicio,fin);
		List<HashMap<String, Object>> obs = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page) 
		{	
			String nombre = (String)o[9]+" "+(String)o[10];
			if(nombre.equals(pasajero))
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
			
		}
		response.put("ordenesServicio", obs);
		return response;
	}
	
	public HashMap<String, Object> filtrarOrdenesDocumentoPasajero(String inicio, String fin, String pasajero)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		List<Object[]> page=ordenServicioRepositorio.listarOrdenesDocumentoPasajero(inicio,fin,pasajero);
		List<HashMap<String, Object>> obs = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page) 
		{
			String nombre = (String)o[3]+" "+(String)o[4];
			
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
		response.put("ordenesServicio", obs);
		return response;
	}
	
	public HashMap<String, Object> filtrarOrdenesPlacaVehiculo(String inicio, String fin, String placa)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		List<Object[]> page=ordenServicioRepositorio.listarOrdenesPlacaVehiculo(inicio,fin,placa);
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
		response.put("ordenesServicio", obs);
		return response;
	}
	
	public HashMap<String, Object> filtrarOrdenesNombrePropietario(String inicio, String fin, String propietario)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		List<Object[]> page=ordenServicioRepositorio.listarOrdenesServicioNombrePropietario(inicio,fin);
		List<HashMap<String, Object>> obs = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page) 
		{	
			String nombre = (String)o[9]+" "+(String)o[10];
			if(nombre.equals(propietario))
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
			
		}
		response.put("ordenesServicio", obs);
		return response;
	}
	
	public HashMap<String, Object> filtrarOrdenesDocumentoPropietario(String inicio, String fin, String propietario)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		List<Object[]> page=ordenServicioRepositorio.listarOrdenesDocumentoPropietario(inicio,fin,propietario);
		List<HashMap<String, Object>> obs = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page) 
		{
			String nombre = (String)o[3]+" "+(String)o[4];
			
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
		response.put("ordenesServicio", obs);
		return response;
	}
	
	public OrdenServicio obtenerPorId(Long id) {
		Optional<OrdenServicio> op=ordenServicioRepositorio.findById(id);
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}
	
	public Long obtenerIdOrdenServicio(Long reserva)
	{
		System.out.println("buscando orden de servicio");
		return ordenServicioRepositorio.obtenerIdOrdenServicio(reserva);
	}
	
	public void eliminar(OrdenServicio ordenServicio) {
		ordenServicioRepositorio.delete(ordenServicio);
	}
	
	public void eliminarPorid(Long ordenServicio) {
		ordenServicioRepositorio.deleteById(ordenServicio);
	}
	
	public void eliminar(Integer conductor)
	{
		OrdenServicio ordenServicio = ordenServicioRepositorio.traerOrdenServicioConductor(conductor);
		if(ordenServicio!=null)
		{
			ordenServicioRepositorio.delete(ordenServicio);
		}
		
	}
	
	public List<Object[]> pdf(Long id) {
		return ordenServicioRepositorio.datosPdf(id);
	}
	
	
	public List<Object[]> infoBasica() {
		List<Object[]> list= ordenServicioRepositorio.obtenerInfoBasica();
		return list;
	}

}
