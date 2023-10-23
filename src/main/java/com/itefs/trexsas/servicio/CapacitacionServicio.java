package com.itefs.trexsas.servicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Capacitacion;
import com.itefs.trexsas.repositorio.CapacitacionRepositorio;

@Service
public class CapacitacionServicio {

	@Autowired
	private CapacitacionRepositorio capacitacionRepositorio;
	
	
	public void crear(Capacitacion capacitacion)
	{
		capacitacion.setId(null);
		capacitacionRepositorio.save(capacitacion);
	}
	
	public void actualizar(Capacitacion capacitacion)
	{
		capacitacionRepositorio.save(capacitacion);
	}
	
	public void eliminar(long id)
	{
		Optional<Capacitacion> objetoCapacitacion = capacitacionRepositorio.findById(id);
		if(objetoCapacitacion.isPresent())
		{
			Capacitacion capacitacion = objetoCapacitacion.get();
			capacitacion.setEstado(3);
			capacitacionRepositorio.save(capacitacion);
		}
		
	}
	
	public Capacitacion obtenerPorId(long id)
	{
		Optional<Capacitacion> objetoCapacitacion = capacitacionRepositorio.findById(id);
		if(objetoCapacitacion.isPresent())
		{
			return objetoCapacitacion.get();
		}else
		{
			return null;
		}
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= capacitacionRepositorio.listarCapacitacionesPaginado(PageRequest.of(inicio,10));
		return page;
	}
	
	public List<HashMap<String, Object>> listarCapacitacionesFiltradas(int inicio, String criterio, String valor) {
		System.out.println("llamando listarPasajero");
		Page<Object[]> page= capacitacionRepositorio.listarCapacitacionesPaginado(PageRequest.of(inicio, 10));
		List<HashMap<String, Object>> obs = new ArrayList<HashMap<String,Object>>();
		int indiceBusquedaArreglo = 0;
		if(criterio.equals("nc"))
		{
			indiceBusquedaArreglo = 1;
		}else if(criterio.equals("fp"))
		{
			indiceBusquedaArreglo = 2;
		}else if(criterio.equals("fii"))
		{
			indiceBusquedaArreglo = 3;
		}else if(criterio.equals("ffi"))
		{
			indiceBusquedaArreglo = 4;
		}else if(criterio.equals("fi"))
		{
			indiceBusquedaArreglo = 5;
		}else if(criterio.equals("ff"))
		{
			indiceBusquedaArreglo = 6;
		}else if(criterio.equals("es"))
		{
			indiceBusquedaArreglo = 7;
		}
		
		System.out.println("indice "+indiceBusquedaArreglo);
		for (Object[] objects : page.getContent()) 
		{
			System.out.println("identificacion 2: "+objects[1].toString());
			System.out.println("identificacion 2: "+objects[1].toString().equals("2021-06-24"));
			//System.out.println("identificacion : "+objects[indiceBusquedaArreglo]);
			/*
			 * en caso de que la columna sea diferente a 7, buscar por estado, buscará haciendo
			 * la verificación si la factura se encuentra en 1, osea vigente
			 */
			if(indiceBusquedaArreglo!=7)
			{
				/*
				 *se verifica que la factura esté vigente, es decir, que
				 *tenga el estado en 1, si lo tiene en 2 no se tiene en cuenta a menos
				 */
				if(Integer.valueOf(objects[7].toString())!=2)
				{
					if(objects[indiceBusquedaArreglo].toString().equals(valor))
					{
						System.out.println("identificacion 1: "+objects[indiceBusquedaArreglo]);
						System.out.println("identificacion 2: "+objects[1].toString());
						HashMap<String, Object> ob = new HashMap<String, Object>();
						ob.put("iCapacitacion", objects[0]);
						ob.put("nombreCapacitacion", objects[1]);
						ob.put("fechaPublicacioon", objects[2]);
						ob.put("fechaInicioInscripcion", objects[3]);
						ob.put("fechaFinInscripcion", objects[4]);
						//ob.put("factura", objects[5]);
						ob.put("fechaInicioCapacitacion", objects[5]);
						ob.put("fechaFinCapacitacion", objects[6]);
						ob.put("estado",objects[7]);
						obs.add(ob);
					}
				}
			}else
			{
				if(objects[indiceBusquedaArreglo].toString().equals(valor))
				{
					System.out.println("identificacion : "+objects[indiceBusquedaArreglo]);
					HashMap<String, Object> ob = new HashMap<String, Object>();
					ob.put("iCapacitacion", objects[0]);
					ob.put("nombreCapacitacion", objects[1]);
					ob.put("fechaPublicacioon", objects[2]);
					ob.put("fechaInicioInscripcion", objects[3]);
					ob.put("fechaFinInscripcion", objects[4]);
					//ob.put("factura", objects[5]);
					ob.put("fechaInicioCapacitacion", objects[5]);
					ob.put("fechaFinCapacitacion", objects[6]);
					ob.put("estado",objects[7]);
					obs.add(ob);
				}
			}
			
			
		}
		return obs;
	}
}
