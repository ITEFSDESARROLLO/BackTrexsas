package com.itefs.trexsas.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Clase;

public interface ClaseRepositorio extends JpaRepository<Clase, Integer> {

	@Query("select c.idClase,c.clase,c.estadoClase "
			+ "from Clase c "
			+ "where c.estadoClase!=2")
	Page<Object[]> listarPaginado(Pageable pageable);
	
	@Query("select c.idClase,c.clase,c.estadoClase "
			+ "from Clase c "
			+ "where c.estadoClase!=2 and c.idClase =?1 ")
	Page<Object[]> listarPaginadoCodigo(Pageable pageable, Integer codigo);
	
	@Query("select c.idClase,c.clase,c.estadoClase "
			+ "from Clase c "
			+ "where c.estadoClase = ?1 ")
	Page<Object[]> listarPaginadoEstado(Pageable pageable, Integer estado);
}
