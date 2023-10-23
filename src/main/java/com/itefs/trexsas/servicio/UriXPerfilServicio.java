package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.UriXPerfil;
import com.itefs.trexsas.repositorio.UriXPerfilRepositorio;

@Service
public class UriXPerfilServicio {
	
	@Autowired
	private UriXPerfilRepositorio uriXPerfilRepositorio;
	
	public void crear(UriXPerfil uriXPerfil) {
		uriXPerfil.setIdUriXPerfil(null);
		uriXPerfilRepositorio.save(uriXPerfil);
	}
	
	public void actualizar(UriXPerfil uriXPerfil) {
		uriXPerfilRepositorio.save(uriXPerfil);
	}
	
	public List<UriXPerfil> obtenerTodos() {
		return uriXPerfilRepositorio.findAll();
	}
	
	public UriXPerfil obtenerPorId(long id) {
		Optional<UriXPerfil> opUXP=uriXPerfilRepositorio.findById(id);
		if(opUXP.isPresent()) {
			return opUXP.get();
		}
		return null;
	}
	
	public void eliminar(UriXPerfil uriXPerfil) {
		uriXPerfilRepositorio.delete(uriXPerfil);
	}
	
	public List<HashMap<String, Object>> listarURL()
	{
		
		List<Object[]> listadoUrl = uriXPerfilRepositorio.listarUrl();
		Integer idPadre = Integer.valueOf(String.valueOf(listadoUrl.get(0)[0]));
		List<HashMap<String, Object>> urisHija = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> uriPadre = new HashMap<String, Object>();
		List<HashMap<String, Object>> listadoURLSalida = new ArrayList<HashMap<String,Object>>();
		for (int i = 0; i < listadoUrl.size(); i++) 
		{
			System.out.println("url padre : "+listadoUrl.get(i)[0]+" "+listadoUrl.get(i)[1]+" "+listadoUrl.get(i)[2]+" "+listadoUrl.get(i)[3]+" "+listadoUrl.get(i)[4]+" "+listadoUrl.get(i)[5]);
			HashMap<String, Object> uriHija = new HashMap<String, Object>();
			if(idPadre == Integer.valueOf(String.valueOf(listadoUrl.get(i)[0]))) {
				uriHija.put("idUriHija", listadoUrl.get(i)[3]);
				if(String.valueOf(listadoUrl.get(i)[4]).equals("/"))
				{
					uriHija.put("descripcionUriHija", String.valueOf(listadoUrl.get(i)[4]).replace("/", "listar"));
				}else {
					uriHija.put("descripcionUriHija", String.valueOf(listadoUrl.get(i)[4]).replace("/", ""));
				}
				uriHija.put("permiso", false);
				uriHija.put("disable", String.valueOf(listadoUrl.get(i)[4]).replace("/", "").equals("editar")==true?true:false);
				urisHija.add(uriHija);
			}else {
				int indiceAnterior = 0;
				if(i>0) {
					indiceAnterior = i-1;
				}else {
					indiceAnterior = i;
				}
				uriHija.put("permiso", false);
				uriHija.put("idUriHija", listadoUrl.get(indiceAnterior)[3]);
				if(String.valueOf(listadoUrl.get(i-1)[4]).equals("/"))
				{
					uriHija.put("descripcionUriHija", String.valueOf(listadoUrl.get(indiceAnterior)[4]).replace("/", "listar"));
				}else {
					uriHija.put("descripcionUriHija", String.valueOf(listadoUrl.get(indiceAnterior)[4]).replace("/", ""));
				}
				uriHija.put("disable", String.valueOf(listadoUrl.get(indiceAnterior)[4]).replace("/", "").equals("editar")==true?true:false);
				uriPadre.put("idUriPadre", idPadre);
				uriPadre.put("descripcionUriPadre", String.valueOf(listadoUrl.get(indiceAnterior)[1]).replace("/",""));
				uriPadre.put("urisHijas", urisHija);
				listadoURLSalida.add(uriPadre);
				System.out.println("URL : "+uriPadre);
				idPadre = Integer.valueOf(String.valueOf(listadoUrl.get(i)[0]));
				urisHija = new ArrayList<HashMap<String,Object>>();
				uriPadre = new HashMap<String, Object>();
				uriHija.put("idUriHija", listadoUrl.get(i)[3]);
				if(String.valueOf(listadoUrl.get(i)[4]).equals("/"))
				{
					uriHija.put("descripcionUriHija", String.valueOf(listadoUrl.get(i)[4]).replace("/", "listar"));
				}else {
					uriHija.put("descripcionUriHija", String.valueOf(listadoUrl.get(i)[4]).replace("/", ""));
				}
				uriHija.put("disable", String.valueOf(listadoUrl.get(i)[4]).replace("/", "").equals("editar")==true?true:false);
				uriHija.put("permiso", false);
				urisHija.add(uriHija);
			}
		}
		
		return listadoURLSalida;
	}
	
	public List<HashMap<String, Object>> listarURLPerfil(Integer idPerfil)
	{
		
		List<Object[]> listadoUrl = uriXPerfilRepositorio.listarUrlPorPerfil(idPerfil);
		Integer idPadre = Integer.valueOf(String.valueOf(listadoUrl.get(0)[0]));
		List<HashMap<String, Object>> urisHija = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> uriPadre = new HashMap<String, Object>();
		List<HashMap<String, Object>> listadoURLSalida = new ArrayList<HashMap<String,Object>>();
		for (int i = 0; i < listadoUrl.size(); i++) 
		{
			System.out.println("url padre : "+listadoUrl.get(i)[0]+" "+listadoUrl.get(i)[1]+" "+listadoUrl.get(i)[2]+" "+listadoUrl.get(i)[3]+" "+listadoUrl.get(i)[4]+" "+listadoUrl.get(i)[5]);
			HashMap<String, Object> uriHija = new HashMap<String, Object>();
			if(idPadre == Integer.valueOf(String.valueOf(listadoUrl.get(i)[0]))) {
				uriHija.put("idUriHija", listadoUrl.get(i)[3]);
				if(String.valueOf(listadoUrl.get(i)[4]).equals("/"))
				{
					uriHija.put("descripcionUriHija", String.valueOf(listadoUrl.get(i)[4]).replace("/", "listar"));
				}else {
					uriHija.put("descripcionUriHija", String.valueOf(listadoUrl.get(i)[4]).replace("/", ""));
				}
				uriHija.put("permiso", String.valueOf(listadoUrl.get(i)[7]).equals("1")==true?true:false);
				uriHija.put("disable", String.valueOf(listadoUrl.get(i)[4]).replace("/", "").equals("editar")==true?true:false);
				uriHija.put("uxp", listadoUrl.get(i)[8]);
				urisHija.add(uriHija);
			}else {
				int indiceAnterior = 0;
				if(i>0) {
					indiceAnterior = i-1;
				}else {
					indiceAnterior = i;
				}
				uriHija.put("permiso", String.valueOf(listadoUrl.get(indiceAnterior)[7]).equals("1")==true?true:false);
				uriHija.put("uxp", listadoUrl.get(indiceAnterior)[8]);
				uriHija.put("idUriHija", listadoUrl.get(indiceAnterior)[3]);
				if(String.valueOf(listadoUrl.get(i-1)[4]).equals("/"))
				{
					uriHija.put("descripcionUriHija", String.valueOf(listadoUrl.get(indiceAnterior)[4]).replace("/", "listar"));
				}else {
					uriHija.put("descripcionUriHija", String.valueOf(listadoUrl.get(indiceAnterior)[4]).replace("/", ""));
				}
				uriHija.put("disable", String.valueOf(listadoUrl.get(indiceAnterior)[4]).replace("/", "").equals("editar")==true?true:false);
				uriPadre.put("idUriPadre", idPadre);
				uriPadre.put("descripcionUriPadre", String.valueOf(listadoUrl.get(indiceAnterior)[1]).replace("/",""));
				uriPadre.put("urisHijas", urisHija);
				listadoURLSalida.add(uriPadre);
				System.out.println("URL : "+uriPadre);
				idPadre = Integer.valueOf(String.valueOf(listadoUrl.get(i)[0]));
				urisHija = new ArrayList<HashMap<String,Object>>();
				uriPadre = new HashMap<String, Object>();
				uriHija.put("permiso", String.valueOf(listadoUrl.get(i)[7]).equals("1")==true?true:false);
				uriHija.put("uxp", listadoUrl.get(i)[8]);
				uriHija.put("idUriHija", listadoUrl.get(i)[3]);
				if(String.valueOf(listadoUrl.get(i)[4]).equals("/"))
				{
					uriHija.put("descripcionUriHija", String.valueOf(listadoUrl.get(i)[4]).replace("/", "listar"));
				}else {
					uriHija.put("descripcionUriHija", String.valueOf(listadoUrl.get(i)[4]).replace("/", ""));
				}
				uriHija.put("disable", String.valueOf(listadoUrl.get(i)[4]).replace("/", "").equals("editar")==true?true:false);
				uriHija.put("permiso", String.valueOf(listadoUrl.get(i)[7]).equals("1")==true?true:false);
				uriHija.put("uxp", listadoUrl.get(i)[8]);
				urisHija.add(uriHija);
			}
		}
		
		return listadoURLSalida;
	}

}