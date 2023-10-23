package com.itefs.trexsas.utilidades;

public class GeneradorCredenciales 
{
	public String generarUsuario(String nombres, String apellidos)
	{
		String[] arregloNombres = nombres.split(" ");
		String[] arregloApellidos = nombres.split(" ");
		String inicialSegundoApellido = "";
		String primerApellido = "";
		String usuario = "";
		if(arregloApellidos.length==2)
		{
			inicialSegundoApellido = arregloApellidos[1].substring(0,1);
			primerApellido = arregloApellidos[0];
			usuario = nombres.substring(0,1)+primerApellido+inicialSegundoApellido;
		}else
		{
			primerApellido = arregloApellidos[0];
			usuario = nombres.substring(0,1)+primerApellido;
		}
		
		
		return usuario;
	}
	
	public String generarContraseña()
	{
		char[] letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
		int tamañoLetras = letras.length;
		String password = "";
		for (int i = 0; i < 15; i++) 
		{
			int indiceArreglo = (int)(Math.random()*tamañoLetras+1)-1;
			password = password + letras[indiceArreglo];
		}
		return password;
	}

}
