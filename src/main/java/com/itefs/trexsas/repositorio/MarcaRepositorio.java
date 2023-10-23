package com.itefs.trexsas.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Marca;

public interface MarcaRepositorio extends JpaRepository<Marca, Integer> {

	@Query("select c.idMarca,c.marca,c.estadoMarca "
			+ "from Marca c "
			+ "where c.estadoMarca!=2")
	Page<Object[]> listarPaginado(Pageable pageable);
	
	@Query("select c.idMarca,c.marca,c.estadoMarca "
			+ "from Marca c "
			+ "where c.estadoMarca!=2 and c.idMarca = ?1 ")
	Page<Object[]> listarPaginadoCodigo(Pageable pageable, Integer codigo);
	
	@Query("select c.idMarca,c.marca,c.estadoMarca "
			+ "from Marca c "
			+ "where c.estadoMarca!=2 and c.estadoMarca = ?1 ")
	Page<Object[]> listarPaginadoEstado(Pageable pageable, Integer estado);
}
