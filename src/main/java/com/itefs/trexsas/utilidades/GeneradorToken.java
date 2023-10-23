package com.itefs.trexsas.utilidades;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.SecretKey;

import com.itefs.trexsas.modelo.Cuenta;
import com.itefs.trexsas.servicio.CuentaServicio;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class GeneradorToken {
	
	
	/*public String generarToken(){
        long tiempo=System.currentTimeMillis();
        String jwt= Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "clave")
                .setIssuedAt(new Date(tiempo))
                .compact();
        return jwt;
    }
    
    public String generarToken(String palabra){
        long tiempo=System.currentTimeMillis();
        String jwt= Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "clave")
                .setSubject(palabra)
                .setIssuedAt(new Date(tiempo))
                .compact();
        return jwt;
    }*/

    public String generarToken(){
        long tiempo=System.currentTimeMillis();
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String jwt= Jwts.builder()
                .signWith(key,SignatureAlgorithm.HS256)
                .setIssuedAt(new Date(tiempo))
                .compact();
        return jwt;
    }
    
    public String generarTokenQR(String datoQR){
        long tiempo=System.currentTimeMillis();
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String jwt= Jwts.builder()
                .signWith(key,SignatureAlgorithm.HS256)
                .setIssuedAt(new Date(tiempo))
                .claim("data",datoQR)
                .compact();
        return jwt;
    }
    
    public String generarToken(String palabra){
        long tiempo=System.currentTimeMillis();
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String jwt= Jwts.builder()
                .signWith(key,SignatureAlgorithm.HS256)
                .setSubject(palabra)
                .setIssuedAt(new Date(tiempo))
                .compact();
        return jwt;
    }
    
    public String generarToken4(String palabra,Long persona,Integer cuenta){
        long tiempo=System.currentTimeMillis();
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String jwt= Jwts.builder()
                .signWith(key,SignatureAlgorithm.HS256)
                .setSubject(palabra)
                .claim("persona", persona)
                .claim("cuenta", cuenta)
                .setIssuedAt(new Date(tiempo))
                .compact();
        return jwt;
    }
    
    public void generarToken2(String palabra)
    {
    	String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInBlcnNvbmEiOjEsImlhdCI6MTYzMTAyNjU1MX0.8dhbUMdjzCAmZTOqZ0DdYkovYnM54TyaDTs5LpevShw";
    	String[] chunks = accessToken.split("\\.");
		
		Base64.Decoder decoder = Base64.getDecoder();

		String header = new String(decoder.decode(chunks[0]));
		String payload = new String(decoder.decode(chunks[1]));
		CuentaServicio cuentaServicio = new CuentaServicio();
		System.out.println("header : "+header);
		System.out.println("payload : "+payload);
		
		String cadena1 = payload.replace('{', ' ').replace('}', ' ');
		String[] cadena2 = cadena1.split(",");
		HashMap<String, String> contenidoToken = new HashMap<String, String>();
		for (String string : cadena2) 
		{
			String[] cadena3 = string.split(":");
			
			System.out.println("persona : "+cadena3[1].toString());
			
			contenidoToken.put(cadena3[0],cadena3[1]);
		}
		
		//System.out.println("contenido tabla : "+contenidoToken.get("persona").getClass().getTypeName());
		
		
		
    	//Cuenta cuenta = cuentaServicio.obtenerPorUsuario(palabra);
		//Cuenta cuenta = cuentaServicio.obtenerPorIdPersona(Long.valueOf(23));
    	//System.out.println("cuenta "+cuenta.getUsuarioCuenta());
    	//Claims claims = ne;
    	//System.out.println(claims.getIssuer());
        
    		/*System.out.println("en login 2");
            long tiempo=System.currentTimeMillis();
            SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            String jwt= Jwts.builder()
                    .signWith(key,SignatureAlgorithm.HS256)
                    .setSubject(palabra)
                    .claim("acceso1", cuenta.getIdCuenta())
                    .claim("acceso2", cuenta.getPersona().getIdPersona()==null?cuenta.getCliente().getIdCliente():cuenta.getPersona().getIdPersona())
                    .setIssuedAt(new Date(tiempo))
                    .compact();*/
         
    	
    	
    }
}
