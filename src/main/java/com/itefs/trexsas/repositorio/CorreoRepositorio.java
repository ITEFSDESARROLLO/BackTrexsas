package com.itefs.trexsas.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Correo;

public interface CorreoRepositorio extends JpaRepository<Correo, Integer> {

	@Query("select c.idClase,c.clase,c.estadoClase "
			+ "from Clase c ")
	Page<Object[]> listarPaginado(Pageable pageable);
}
