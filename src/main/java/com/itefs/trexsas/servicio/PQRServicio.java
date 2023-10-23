package com.itefs.trexsas.servicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.CorreoPQR;
import com.itefs.trexsas.modelo.PQR;
import com.itefs.trexsas.repositorio.CorreoPQRRepositorio;
import com.itefs.trexsas.repositorio.PQRRepository;

@Service
public class PQRServicio 
{

	@Autowired
	private PQRRepository pqrRepository;
	
	@Autowired
	private CorreoPQRRepositorio correoPQRRepoPqr;
	
	public void crear(PQR pqr)
	{
		pqr.setId(null);
		pqrRepository.save(pqr);
	}
	
	public List<CorreoPQR> obtenerCorreos()
	{
		return correoPQRRepoPqr.findAll();
	}
	
	public void actualizar(PQR pqr)
	{
		pqrRepository.save(pqr);
	}
	
	public PQR obtenerPorId(long id)
	{
		Optional<PQR> objetoPQR = pqrRepository.findById(id);
		if(objetoPQR.isPresent())
		{
			return objetoPQR.get();
		}else
		{
			return null;
		}
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= pqrRepository.listarPQRPaginado(PageRequest.of(inicio,10));
		return page;
	}
	
	public List<HashMap<String, Object>> listarCapacitacionesFiltradas(int inicio, String criterio, String valor) {
		System.out.println("llamando listarPasajero");
		Page<Object[]> page= pqrRepository.listarPQRPaginado(PageRequest.of(inicio, 10));
		List<HashMap<String, Object>> obs = new ArrayList<HashMap<String,Object>>();
		for (Object[] objects : page.getContent()) 
		{
			if(Integer.valueOf(objects[4].toString())!=2)
			{
				if(objects[2].toString().equals(valor))
				{
					System.out.println("identificacion 2: "+objects[1].toString());
					HashMap<String, Object> ob = new HashMap<String, Object>();
					ob.put("idPQR", objects[0]);
					ob.put("descripcionPQR", objects[1]);
					ob.put("tipoPQR", objects[2]);
					ob.put("fechaPublicacionPQR", objects[3]);
					ob.put("redactorPQR", objects[4]);
					obs.add(ob);
				}
			}
		}
		return obs;
	}
}
