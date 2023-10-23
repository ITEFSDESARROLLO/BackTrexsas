package com.itefs.trexsas.utilidades;

import org.apache.commons.codec.digest.DigestUtils;

public class Cifrador {
	
	public String cifrar(String clave){
        String cifrada=DigestUtils.sha256Hex(clave);
        return cifrada;
    }
}
