package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Cliente;
import com.itefs.trexsas.modelo.Factura;
import com.itefs.trexsas.repositorio.ClienteRepositorio;
import com.itefs.trexsas.repositorio.FacturaRepositorio;

@Service
public class FacturaServicio {
	
	@Autowired
	private FacturaRepositorio facturaRepositorio;
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	
	public void crear(Factura factura) {
		factura.setIdFactura(null);
		facturaRepositorio.save(factura);
	}
	
	public void actualizar(Factura factura) {
		facturaRepositorio.save(factura);
	}
	
	public List<Factura> obtenerTodos() {
		return facturaRepositorio.findAll();
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= facturaRepositorio.listarFacturasPaginado(PageRequest.of(inicio, 10));
		return page;
	}
	
	public Factura obtenerPorId(Long id) {
		Optional<Factura> op=facturaRepositorio.findById(id);
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}
	
	public void eliminar(Factura factura) {
		facturaRepositorio.delete(factura);
	}
	
	//consulta los datos necesarios para la creacion del pdf
	public List<Object[]> pdf(Long id) {
		return facturaRepositorio.datosPdf(id);
	}

	//consulta el total de las ordenes de servicio que se han hecho respecto a una fecha inicial, una fecha final y un cliente
	public Long obtenerTotalEntreFechas(String fi,String ff, int idCli) {
		return facturaRepositorio.obtenerTotalEntreFechas(fi,ff,idCli);
	}
	//consulta datos de las reservas y ordenes de servicio que se han hecho respecto a una fecha inicial, una fecha final y un cliente
	public List<Object[]> obtenerOsyReservasEntreFechas(String fi,String ff, int idCli) {
		return facturaRepositorio.obtenerOsyReservasEntreFechas(fi,ff,idCli);
	}
	
	public List<Cliente> obtenerClientesFiltro()
	{
		return clienteRepositorio.findAll();
	}
	
	public List<HashMap<String, Object>> listarFacturasFiltradas(int inicio, String criterio, String valor) {
		System.out.println("llamando listarPasajero");
		Page<Object[]> page= facturaRepositorio.listarFacturasFiltrado(PageRequest.of(inicio, 10));
		List<HashMap<String, Object>> obs = new ArrayList<HashMap<String,Object>>();
		int indiceBusquedaArreglo = 0;
		if(criterio.equals("es"))
		{
			indiceBusquedaArreglo = 6;
		}else if(criterio.equals("cli"))
		{
			indiceBusquedaArreglo = 7;
		}else if(criterio.equals("fi"))
		{
			indiceBusquedaArreglo = 2;
		}else if(criterio.equals("ff"))
		{
			indiceBusquedaArreglo = 1;
		}
		System.out.println("indice "+indiceBusquedaArreglo);
		for (Object[] objects : page.getContent()) 
		{
			System.out.println("identificacion 2: "+objects[1].toString());
			System.out.println("identificacion 2: "+objects[1].toString().equals("2021-06-24"));
			//System.out.println("identificacion : "+objects[indiceBusquedaArreglo]);
			/*
			 * en caso de que la columna sea diferente a 6, buscar por estado, buscará haciendo
			 * la verificación si la factura se encuentra en 1, osea vigente
			 */
			if(indiceBusquedaArreglo!=6)
			{
				/*
				 *se verifica que la factura esté vigente, es decir, que
				 *tenga el estado en 1, si lo tiene en 2 no se tiene en cuenta a menos
				 */
				if(Integer.valueOf(objects[6].toString())!=2)
				{
					if(objects[indiceBusquedaArreglo].toString().equals(valor))
					{
						System.out.println("identificacion 1: "+objects[indiceBusquedaArreglo]);
						System.out.println("identificacion 2: "+objects[1].toString());
						HashMap<String, Object> ob = new HashMap<String, Object>();
						ob.put("idFactura", objects[0]);
						ob.put("fechaFinFactura", objects[1]);
						ob.put("fechaInicioFactura", objects[2]);
						ob.put("fechaFactura", objects[3]);
						ob.put("totalFactura", objects[4]);
						//ob.put("factura", objects[5]);
						ob.put("conceptoFactura", objects[5]);
						ob.put("estadoFactura", objects[6]);
						ob.put("cliente",objects[7]);
						obs.add(ob);
					}
				}
			}else
			{
				if(objects[indiceBusquedaArreglo].toString().equals(valor))
				{
					System.out.println("identificacion : "+objects[indiceBusquedaArreglo]);
					HashMap<String, Object> ob = new HashMap<String, Object>();
					ob.put("idFactura", objects[0]);
					ob.put("fechaFinFactura", objects[1]);
					ob.put("fechaInicioFactura", objects[2]);
					ob.put("fechaFactura", objects[3]);
					ob.put("totalFactura", objects[4]);
					//ob.put("factura", objects[5]);
					ob.put("conceptoFactura", objects[5]);
					ob.put("estadoFactura", objects[6]);
					ob.put("cliente",objects[7]);
					obs.add(ob);
				}
			}
			
			
		}
		return obs;
	}
	

}
