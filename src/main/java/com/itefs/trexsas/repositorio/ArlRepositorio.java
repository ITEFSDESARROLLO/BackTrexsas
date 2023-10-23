package com.itefs.trexsas.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Arl;

public interface ArlRepositorio extends JpaRepository<Arl, Integer> {

	@Query("select c.idArl,c.arl,c.estadoArl "
			+ "from Arl c "
			+ "where c.estadoArl!=2")
	Page<Object[]> listarPaginado(Pageable pageable);
	
	@Query("select c.idArl,c.arl,c.estadoArl "
			+ "from Arl c "
			+ "where c.estadoArl!=2 and c.idArl = ?1 ")
	Page<Object[]> listarPaginadoCodigo(Pageable pageable, Integer codigo);
	
	@Query("select c.idArl,c.arl,c.estadoArl "
			+ "from Arl c "
			+ "where c.estadoArl!=2 and c.estadoArl = ?1 ")
	Page<Object[]> listarPaginadoEstado(Pageable pageable, Integer codigo);
	
	
}
