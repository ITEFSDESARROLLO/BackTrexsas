package com.itefs.trexsas.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Eps;

public interface EpsRepositorio extends JpaRepository<Eps, Integer> {

	@Query("select c.idEps,c.eps,c.estadoEps "
			+ "from Eps c "
			+ "where c.estadoEps!=2")
	Page<Object[]> listarPaginado(Pageable pageable);
	
	@Query("select c.idEps,c.eps,c.estadoEps "
			+ "from Eps c "
			+ "where c.estadoEps!=2 and c.idEps =?1 ")
	Page<Object[]> listarPaginadoCodigo(Pageable pageable, Integer codigo);
	
	@Query("select c.idEps,c.eps,c.estadoEps "
			+ "from Eps c "
			+ "where c.estadoEps!=2 and c.estadoEps =?1 ")
	Page<Object[]> listarPaginadoEstado(Pageable pageable, Integer estado);
}
