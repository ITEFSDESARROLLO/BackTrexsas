package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.LineaMarca;

public interface LineaMarcaRepository extends JpaRepository<LineaMarca,Long>{

	@Query("select lm From LineaMarca lm where marca = ?1")
	List<LineaMarca> traerLineas(Integer marca);
}
