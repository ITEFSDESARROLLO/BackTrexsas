package com.itefs.trexsas.repositorio;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.TipoVehiculo;

public interface TipoVehiculoRepositorio extends JpaRepository<TipoVehiculo, Integer> {
	
	@Query("select c.idTipoVehiculo,c.tipoVehiculo,c.estadoTipoVehiculo "
			+ "from TipoVehiculo c "
			+ "where c.estadoTipoVehiculo!=2")
	Page<Object[]> listarPaginado(Pageable pageable);
	
	@Query("select c.idTipoVehiculo,c.tipoVehiculo "
			+ "from TipoVehiculo c "
			+ "where c.estadoTipoVehiculo!=2")
	List<Object[]> listarFiltro();
	
	@Query("select c.idTipoVehiculo,c.tipoVehiculo,c.estadoTipoVehiculo "
			+ "from TipoVehiculo c "
			+ "where c.estadoTipoVehiculo!=2 and c.idTipoVehiculo = ?1 ")
	Page<Object[]> listarPaginadoCodigoVehiculo(Pageable pageable, Integer tipo);
	
	@Query("select c.idTipoVehiculo,c.tipoVehiculo,c.estadoTipoVehiculo "
			+ "from TipoVehiculo c "
			+ "where c.estadoTipoVehiculo!=2 and c.estadoTipoVehiculo=?1 ")
	Page<Object[]> listarPaginadoEstadoVehiculo(Pageable pageable, Integer estado);
	
}
