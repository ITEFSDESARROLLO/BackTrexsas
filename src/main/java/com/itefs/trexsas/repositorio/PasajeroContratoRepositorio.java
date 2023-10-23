package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.PasajeroContrato;

public interface PasajeroContratoRepositorio extends JpaRepository<PasajeroContrato, Long> {

	@Query("select pc From PasajeroContrato pc where pc.contrato = ?1 ")
	List<PasajeroContrato> listarPasajeros(Long contrato);
}
