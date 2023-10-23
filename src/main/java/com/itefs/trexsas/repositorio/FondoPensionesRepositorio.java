package com.itefs.trexsas.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.FondoPensiones;

public interface FondoPensionesRepositorio extends JpaRepository<FondoPensiones, Integer> {

	@Query("select c.idFondoPensiones,c.fondoPensiones,c.estadoFondoPensiones "
			+ "from FondoPensiones c "
			+ "where c.estadoFondoPensiones!=2")
	Page<Object[]> listarPaginado(Pageable pageable);
	
	@Query("select c.idFondoPensiones,c.fondoPensiones,c.estadoFondoPensiones "
			+ "from FondoPensiones c "
			+ "where c.estadoFondoPensiones!=2 and c.idFondoPensiones = ?1 ")
	Page<Object[]> listarPaginadoCodigo(Pageable pageable,Integer codigo);
	
	@Query("select c.idFondoPensiones,c.fondoPensiones,c.estadoFondoPensiones "
			+ "from FondoPensiones c "
			+ "where c.estadoFondoPensiones!=2 and c.estadoFondoPensiones = ?1 ")
	Page<Object[]> listarPaginadoEstado(Pageable pageable, Integer estado);
}
