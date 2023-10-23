package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Capacitacion;

public interface CapacitacionRepositorio extends JpaRepository<Capacitacion, Long>{
	
	@Query("select cp.id,cp.nombre,cp.descripcion,cp.fechaPublicacion,cp.fechaInscripcion,cp.fechaFinInscripcion,"
			+ "cp.fechaInicioCapacitacion,cp.fechaFinCapacitacion,cp.estado "
			+ "from Capacitacion cp "
			+ "order by cp.id desc")
	Page<Object[]> listarCapacitacionesPaginado(Pageable pageable);
	
}
