package com.itefs.trexsas.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Aseguradora;

public interface AseguradoraRepositorio extends JpaRepository<Aseguradora, Integer> {

	@Query("select c.idAseguradora,c.nombreAseguradora,c.estadoAseguradora "
			+ "from Aseguradora c "
			+ "where c.estadoAseguradora!=2")
	Page<Object[]> listarPaginado(Pageable pageable);
	
	@Query("select c.idAseguradora,c.nombreAseguradora,c.estadoAseguradora "
			+ "from Aseguradora c "
			+ "where c.estadoAseguradora!=2 and c.idAseguradora = ?1 ")
	Page<Object[]> listarPaginadoCodigo(Pageable pageable, Integer codigo);
	
	@Query("select c.idAseguradora,c.nombreAseguradora,c.estadoAseguradora "
			+ "from Aseguradora c "
			+ "where c.estadoAseguradora!=2 and c.estadoAseguradora =?1 ")
	Page<Object[]> listarPaginadoEstado(Pageable pageable, Integer estado);
}
