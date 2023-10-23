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
import com.itefs.trexsas.modelo.Contrato;
import com.itefs.trexsas.modelo.Pasajero;
import com.itefs.trexsas.repositorio.ClienteRepositorio;

@Service
public class ClienteServicio {
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	
	public void crear(Cliente cliente) {
		cliente.setIdCliente(null);
		clienteRepositorio.save(cliente);
	}
		
	public void actualizar(Cliente cliente) {
		clienteRepositorio.save(cliente);
	}
	public void eliminarPorId(Cliente cl )
	{
		clienteRepositorio.save(cl);
	}

	public List<Object[]> obtenerTodos()
	{
		return clienteRepositorio.listarClientesNotificaciones();
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= clienteRepositorio.listarClientesPaginado(PageRequest.of(inicio, 10));
		return page;
	}
	
	public List<Object[]> listarClientesFacturas() {
		List<Object[]> page= clienteRepositorio.listarClientesFacturas();
		return page;
	}
	
	public List<Cliente> listarClientesOrdenes() {
		List<Cliente> page= clienteRepositorio.findAll();
		return page;
	}
	
	public Cliente obtenerPorId(int id) {
		Optional<Cliente> opPropietario=clienteRepositorio.findById(id);
		if(opPropietario.isPresent()) {
			return opPropietario.get();
		}
		return null;
	}
	
	public List<Object[]> obtenerInfoBasica() {
		return clienteRepositorio.infoBasicaClientes();
	}
	
	public List<Object[]> correoCliente(int id) {
		List<Object[]> opCorreo=clienteRepositorio.correoCliente(id);
		return opCorreo;
	}
	
	public HashMap<String, Object> listarPorTelefono(int inicio,String telefono)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
			System.out.println("id propietario : ");
			Page<Object[]> page= clienteRepositorio.listarPorTelefono(PageRequest.of(inicio, 10),telefono);
			System.out.println("tamaño  : "+page.getSize());
			List<HashMap<String, Object>> clientes = new ArrayList<HashMap<String, Object>>();
			for(Object[] o:page.getContent()) {
				HashMap<String, Object> cliente = new HashMap<String, Object>();
				cliente.put("idCuenta", o[0]);
				cliente.put("idCliente", o[8]);
				cliente.put("razonSocialCliente", o[1]);
				cliente.put("nitCliente", o[2]);
				cliente.put("direccionCliente", o[3]);
				cliente.put("celularUnoCliente", o[4]);
				cliente.put("finalizarServicioCliente", o[5]);
				cliente.put("logoCliente", o[6]);
				cliente.put("estadoCuenta", o[7]);
				clientes.add(cliente);
			}
			response.put("totalResultados", page.getTotalElements());
			response.put("totalPaginas", page.getTotalPages());
			response.put("clientes", clientes);
		return response;
	}
	
	public HashMap<String, Object> listarPorRazonSocialCliente(int inicio,String razonSocialCliente)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
			System.out.println("id propietario : ");
			Page<Object[]> page= clienteRepositorio.listarPorRazonSocialCliente(PageRequest.of(inicio, 10),razonSocialCliente);
			System.out.println("tamaño  : "+page.getSize());
			List<HashMap<String, Object>> clientes = new ArrayList<HashMap<String, Object>>();
			for(Object[] o:page.getContent()) {
				HashMap<String, Object> cliente = new HashMap<String, Object>();
				cliente.put("idCuenta", o[0]);
				cliente.put("idCliente", o[8]);
				cliente.put("razonSocialCliente", o[1]);
				cliente.put("nitCliente", o[2]);
				cliente.put("direccionCliente", o[3]);
				cliente.put("celularUnoCliente", o[4]);
				cliente.put("finalizarServicioCliente", o[5]);
				cliente.put("logoCliente", o[6]);
				cliente.put("estadoCuenta", o[7]);
				clientes.add(cliente);
			}
			response.put("totalResultados", page.getTotalElements());
			response.put("totalPaginas", page.getTotalPages());
			response.put("clientes", clientes);
		return response;
	}
	
	public HashMap<String, Object> listarPorNitCliente(int inicio,String nit)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
			System.out.println("id propietario : ");
			Page<Object[]> page= clienteRepositorio.listarPorNitCliente(PageRequest.of(inicio, 10),nit);
			System.out.println("tamaño  : "+page.getSize());
			List<HashMap<String, Object>> clientes = new ArrayList<HashMap<String, Object>>();
			for(Object[] o:page.getContent()) {
				HashMap<String, Object> cliente = new HashMap<String, Object>();
				cliente.put("idCuenta", o[0]);
				cliente.put("idCliente", o[8]);
				cliente.put("razonSocialCliente", o[1]);
				cliente.put("nitCliente", o[2]);
				cliente.put("direccionCliente", o[3]);
				cliente.put("celularUnoCliente", o[4]);
				cliente.put("finalizarServicioCliente", o[5]);
				cliente.put("logoCliente", o[6]);
				cliente.put("estadoCuenta", o[7]);
				clientes.add(cliente);
			}
			response.put("totalResultados", page.getTotalElements());
			response.put("totalPaginas", page.getTotalPages());
			response.put("clientes", clientes);
		return response;
	}
	
	public HashMap<String, Object> listarPorTipoFinalizacion(int inicio,String tipo)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
			System.out.println("id propietario : ");
			Page<Object[]> page= clienteRepositorio.listarPorTipoFinalizacion(PageRequest.of(inicio, 10),Boolean.valueOf(tipo));
			System.out.println("tamaño  : "+page.getSize());
			List<HashMap<String, Object>> clientes = new ArrayList<HashMap<String, Object>>();
			for(Object[] o:page.getContent()) {
				HashMap<String, Object> cliente = new HashMap<String, Object>();
				cliente.put("idCuenta", o[0]);
				cliente.put("idCliente", o[8]);
				cliente.put("razonSocialCliente", o[1]);
				cliente.put("nitCliente", o[2]);
				cliente.put("direccionCliente", o[3]);
				cliente.put("celularUnoCliente", o[4]);
				cliente.put("finalizarServicioCliente", o[5]);
				cliente.put("logoCliente", o[6]);
				cliente.put("estadoCuenta", o[7]);
				clientes.add(cliente);
			}
			response.put("totalResultados", page.getTotalElements());
			response.put("totalPaginas", page.getTotalPages());
			response.put("clientes", clientes);
		return response;
	}
	
	public HashMap<String, Object> listarPorDireccion(int inicio,String direccion)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
			System.out.println("id propietario : ");
			Page<Object[]> page= clienteRepositorio.listarPorDireccionCliente(PageRequest.of(inicio, 10),direccion);
			System.out.println("tamaño  : "+page.getSize());
			List<HashMap<String, Object>> clientes = new ArrayList<HashMap<String, Object>>();
			for(Object[] o:page.getContent()) {
				HashMap<String, Object> cliente = new HashMap<String, Object>();
				cliente.put("idCuenta", o[0]);
				cliente.put("idCliente", o[8]);
				cliente.put("razonSocialCliente", o[1]);
				cliente.put("nitCliente", o[2]);
				cliente.put("direccionCliente", o[3]);
				cliente.put("celularUnoCliente", o[4]);
				cliente.put("finalizarServicioCliente", o[5]);
				cliente.put("logoCliente", o[6]);
				cliente.put("estadoCuenta", o[7]);
				clientes.add(cliente);
			}
			response.put("totalResultados", page.getTotalElements());
			response.put("totalPaginas", page.getTotalPages());
			response.put("clientes", clientes);
		return response;
	}
	
	public HashMap<String, Object> listarPorEstado(int inicio,String estado)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
			System.out.println("id propietario : ");
			Page<Object[]> page= clienteRepositorio.listarPorEstado(PageRequest.of(inicio, 10),Integer.valueOf(estado));
			System.out.println("tamaño  : "+page.getSize());
			List<HashMap<String, Object>> clientes = new ArrayList<HashMap<String, Object>>();
			for(Object[] o:page.getContent()) {
				HashMap<String, Object> cliente = new HashMap<String, Object>();
				cliente.put("idCuenta", o[0]);
				cliente.put("idCliente", o[8]);
				cliente.put("razonSocialCliente", o[1]);
				cliente.put("nitCliente", o[2]);
				cliente.put("direccionCliente", o[3]);
				cliente.put("celularUnoCliente", o[4]);
				cliente.put("finalizarServicioCliente", o[5]);
				cliente.put("logoCliente", o[6]);
				cliente.put("estadoCuenta", o[7]);
				clientes.add(cliente);
			}
			response.put("totalResultados", page.getTotalElements());
			response.put("totalPaginas", page.getTotalPages());
			response.put("clientes", clientes);
		return response;
	}

}
