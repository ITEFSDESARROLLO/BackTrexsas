package com.itefs.trexsas.repositorio;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.TipoCombustible;

public interface TipoCombustibleRepositorio extends JpaRepository<TipoCombustible, Integer> {
	
	@Query("select c.idTipoCombustible,c.tipoCombustible,c.estadoTipoCombustible "
			+ "from TipoCombustible c "
			+ "where c.estadoTipoCombustible!=2")
	Page<Object[]> listarPaginado(Pageable pageable);
	
	@Query("select c.idTipoCombustible,c.tipoCombustible,c.estadoTipoCombustible "
			+ "from TipoCombustible c "
			+ "where c.estadoTipoCombustible!=2 and c.idTipoCombustible = ?1 ")
	Page<Object[]> listarPaginadoCodigo(Pageable pageable, Integer tipo);
	
	@Query("select c.idTipoCombustible,c.tipoCombustible,c.estadoTipoCombustible "
			+ "from TipoCombustible c "
			+ "where c.estadoTipoCombustible!=2 and c.estadoTipoCombustible = ?1 ")
	Page<Object[]> listarPaginadoEstado(Pageable pageable, Integer estado);
	
}
