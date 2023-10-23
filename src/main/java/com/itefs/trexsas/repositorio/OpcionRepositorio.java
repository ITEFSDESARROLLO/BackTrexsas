package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Opcion;

public interface OpcionRepositorio extends JpaRepository<Opcion, Long>
{
	@Query("select o from Opcion o where o.pregunta.id = ?1")
	List<Opcion> findByPregunta(Long pregunta);
}
