package com.itefs.trexsas.repositorio;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Cliente;

public interface ClienteRepositorio extends JpaRepository <Cliente, Integer> {
	
	@Query("select c.idCuenta,cl.razonSocialCliente,cl.nitCliente,cl.direccionCliente,cl.celularUnoCliente,cl.finalizarServicioCliente,cl.logoCliente,c.estadoCuenta,cl.idCliente "
			+ "from Cuenta c join c.cliente cl where c.estadoCuenta!=2 "
			+ "order by c.idCuenta desc")
	Page<Object[]> listarClientesPaginado(Pageable pageable);
	
	@Query("select c.idCuenta,cl.razonSocialCliente,cl.nitCliente,cl.direccionCliente,cl.celularUnoCliente,cl.finalizarServicioCliente,cl.logoCliente,c.estadoCuenta,cl.idCliente "
			+ "from Cuenta c join c.cliente cl where c.estadoCuenta!=2 "
			+ "order by c.idCuenta desc")
	List<Object[]> listarClientesFacturas();
	
	@Query("select c.idCliente,c.razonSocialCliente,c.nitCliente "
			+ "from Cuenta cu "
			+ "join cu.cliente c "
			+ "where cu.estadoCuenta=1")
	List<Object[]> infoBasicaClientes();
	
	@Query("select cl.correoCliente, cl.razonSocialCliente from Cliente cl where cl.idCliente = ?1")
	List<Object[]> correoCliente(int id);
	
	Cliente findByRazonSocialCliente(String razonSocialCliente);
	
	@Query("select c.idCuenta,cl.razonSocialCliente,cl.nitCliente,cl.direccionCliente,cl.celularUnoCliente,cl.finalizarServicioCliente,cl.logoCliente,c.estadoCuenta,cl.idCliente "
			+ "from Cuenta c join c.cliente cl where c.estadoCuenta!=2 "
			+ "order by c.idCuenta desc")
	List<Cliente> listarClientesFiltro();
	
	@Query(nativeQuery=true, value="select c.id_cliente, c.razon_social_cliente, c.correo_cliente "
			+ "from cliente c")
	List<Object[]> listarClientesNotificaciones();
	
	@Query("select c.idCuenta,cl.razonSocialCliente,cl.nitCliente,cl.direccionCliente,cl.celularUnoCliente,cl.finalizarServicioCliente,cl.logoCliente,c.estadoCuenta,cl.idCliente "
			+ "from Cuenta c join c.cliente cl "
			+ "where c.estadoCuenta!=2 and cl.razonSocialCliente = ?1 "
			+ "order by c.idCuenta desc")
	Page<Object[]> listarPorRazonSocialCliente(Pageable pageable, String razonSocialCliente);
	
	@Query("select c.idCuenta,cl.razonSocialCliente,cl.nitCliente,cl.direccionCliente,cl.celularUnoCliente,cl.finalizarServicioCliente,cl.logoCliente,c.estadoCuenta,cl.idCliente "
			+ "from Cuenta c join c.cliente cl "
			+ "where c.estadoCuenta!=2 and cl.nitCliente = ?1 "
			+ "order by c.idCuenta desc")
	Page<Object[]> listarPorNitCliente(Pageable pageable, String nitCliente);
	
	@Query("select c.idCuenta,cl.razonSocialCliente,cl.nitCliente,cl.direccionCliente,cl.celularUnoCliente,cl.finalizarServicioCliente,cl.logoCliente,c.estadoCuenta,cl.idCliente "
			+ "from Cuenta c join c.cliente cl "
			+ "where c.estadoCuenta!=2 and cl.direccionCliente = ?1 "
			+ "order by c.idCuenta desc")
	Page<Object[]> listarPorDireccionCliente(Pageable pageable, String direccionCliente);
	
	@Query("select c.idCuenta,cl.razonSocialCliente,cl.nitCliente,cl.direccionCliente,cl.celularUnoCliente,cl.finalizarServicioCliente,cl.logoCliente,c.estadoCuenta,cl.idCliente "
			+ "from Cuenta c join c.cliente cl "
			+ "where c.estadoCuenta!=2 and cl.finalizarServicioCliente = ?1 "
			+ "order by c.idCuenta desc")
	Page<Object[]> listarPorTipoFinalizacion(Pageable pageable, boolean tipo);
	
	@Query("select c.idCuenta,cl.razonSocialCliente,cl.nitCliente,cl.direccionCliente,cl.celularUnoCliente,cl.finalizarServicioCliente,cl.logoCliente,c.estadoCuenta,cl.idCliente "
			+ "from Cuenta c join c.cliente cl "
			+ "where c.estadoCuenta!=2 and cl.celularUnoCliente = ?1 "
			+ "order by c.idCuenta desc")
	Page<Object[]> listarPorTelefono(Pageable pageable, String telefono);
	
	@Query("select c.idCuenta,cl.razonSocialCliente,cl.nitCliente,cl.direccionCliente,cl.celularUnoCliente,cl.finalizarServicioCliente,cl.logoCliente,c.estadoCuenta,cl.idCliente "
			+ "from Cuenta c join c.cliente cl "
			+ "where c.estadoCuenta = ?1 "
			+ "order by c.idCuenta desc")
	Page<Object[]> listarPorEstado(Pageable pageable, Integer estado);
}
