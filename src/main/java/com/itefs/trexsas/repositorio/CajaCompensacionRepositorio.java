package com.itefs.trexsas.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.CajaCompensacion;

public interface CajaCompensacionRepositorio extends JpaRepository<CajaCompensacion, Integer> {

	@Query("select c.idCajaCompensacion,c.cajaCompensacion,c.estadoCajaCompensacion "
			+ "from CajaCompensacion c "
			+ "where c.estadoCajaCompensacion!=2")
	Page<Object[]> listarPaginado(Pageable pageable);
	
	@Query("select c.idCajaCompensacion,c.cajaCompensacion,c.estadoCajaCompensacion "
			+ "from CajaCompensacion c "
			+ "where c.estadoCajaCompensacion!=2 and c.idCajaCompensacion = ?1 ")
	Page<Object[]> listarPaginadoCodigo(Pageable pageable, Integer codigo);
	
	@Query("select c.idCajaCompensacion,c.cajaCompensacion,c.estadoCajaCompensacion "
			+ "from CajaCompensacion c "
			+ "where c.estadoCajaCompensacion!=2 and c.estadoCajaCompensacion = ?1")
	Page<Object[]> listarPaginadoEstado(Pageable pageable, Integer estado);
}
