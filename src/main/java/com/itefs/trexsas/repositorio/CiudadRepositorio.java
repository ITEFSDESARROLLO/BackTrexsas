package com.itefs.trexsas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itefs.trexsas.modelo.Ciudad;

public interface CiudadRepositorio extends JpaRepository<Ciudad, Integer> 
{
	Ciudad findByCiudad(String ciudad);
}
