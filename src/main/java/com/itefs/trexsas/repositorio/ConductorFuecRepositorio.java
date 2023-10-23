package com.itefs.trexsas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.ConductorFuec;

public interface ConductorFuecRepositorio extends JpaRepository<ConductorFuec, Long>{
	@Query("delete from ConductorFuec cf where cf.conductor= ?1 and cf.fuec = ?2 ")
	void deleteConductor(Integer conductor, Long fuec);
}
