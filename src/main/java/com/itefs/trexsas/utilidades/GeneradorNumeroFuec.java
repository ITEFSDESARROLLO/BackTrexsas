package com.itefs.trexsas.utilidades;

public class GeneradorNumeroFuec 
{
	private final String CONSECUTIVO_TERRITORIAL = "213";
	private final String CONSECUTIVO_HABILITACION = "0115";
	private final String AÑO_ACTUAL = String.valueOf(java.time.LocalDate.now().getYear());
	
	private final String AÑO_HABILITACION = String.valueOf(16);
	
	public String generarNumeroFuec(int consecutivoInternoCotrato, int totalFuecs)
	{
		String consecutivoContrato = "";
		String tFuecs = "";
		
		if(consecutivoInternoCotrato < 10)
		{
			consecutivoContrato = "000"+String.valueOf(consecutivoInternoCotrato);
		}else if(consecutivoInternoCotrato >=10 && consecutivoInternoCotrato <=99)
		{
			consecutivoContrato = "00"+String.valueOf(consecutivoInternoCotrato);
		}else if(consecutivoInternoCotrato >=100 && consecutivoInternoCotrato <=999)
		{
			consecutivoContrato = "0"+String.valueOf(consecutivoInternoCotrato);
		}else
		{
			consecutivoContrato = String.valueOf(consecutivoInternoCotrato);
		}
		
		if(totalFuecs < 10)
		{
			tFuecs = "000"+String.valueOf(totalFuecs);
		}else if(totalFuecs >=10 && totalFuecs <=99)
		{
			tFuecs = "00"+String.valueOf(totalFuecs);
		}else if(totalFuecs >=100 && totalFuecs <=999)
		{
			consecutivoContrato = "0"+String.valueOf(totalFuecs);
		}else
		{
			tFuecs = String.valueOf(totalFuecs);
		}
		
		System.out.println("consecutivo : "+consecutivoContrato);
		
		System.out.println("total fuecs : "+tFuecs);
		
		String numeroFuec = CONSECUTIVO_TERRITORIAL+CONSECUTIVO_HABILITACION+AÑO_HABILITACION+AÑO_ACTUAL+consecutivoContrato+tFuecs;
		
		return numeroFuec;
		
		
	}

}
