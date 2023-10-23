package com.itefs.trexsas.utilidades;


import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Conductor;
import com.itefs.trexsas.modelo.Servicio;

@Service
public class Correo {

    @Autowired
    private JavaMailSender mailSender;
    
    private final String olvidoClave = "Olvidaste tu clave TREX.SAS";
    private final String solicitudAfiliacion = "Solicitud de vinculación a TREX.SAS";
    private final String reserva = "Reserva de transporte con TREX.SAS";
    //private final String viajeProgramadoConductor = "Programacion Viaje";
        
    @Async
    public void enviarCorreoRecuperacion(String destinatario, String sujeto, String link) throws MessagingException,AddressException{
        
        String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                            "\n" +
                            "<!--Copia desde aquí-->\n" +
                            "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                            "	<tr>\n" +
                            "		<td style=\"background-color: #ecf0f1\">\n" +
                            "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                            "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">Hola "+sujeto+"!</h2>\n" +
                            "				<p style=\"margin: 2px; font-size: 15px\">\n" +
                            "					has solicitado el servicio de recuperar el acceso de tu cuenta TREX.SAS por olvido de contraseña.<br>\n" +
                            "					en el siguiente enlace podras restaurar tu clave.</p><br><br>\n" +
                            "				<div style=\"width: 100%; text-align: center\">\n" +
                            "					<a style=\"text-decoration: none; border-radius: 5px; padding: 11px 23px; color: white; background-color: #3498db;\" href=\""+link+"\">Restaurar</a>	\n" +
                            "				</div>\n" +
                            "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                            "			</div>\n" +
                            "		</td>\n" +
                            "	</tr>\n" +
                            "</table>\n" +
                            "</body>";
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message, true);
        helper.setSubject(olvidoClave);
        helper.setTo(destinatario);
        helper.setText(cuerpoMensaje, true);
        mailSender.send(message);
    }
    
    @Async
    public void enviarCorreoConfirmacionSubidaConductores(String destinatario, String sujeto) throws MessagingException,AddressException{
        
        String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                            "\n" +
                            "<!--Copia desde aquí-->\n" +
                            "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                            "	<tr>\n" +
                            "		<td style=\"background-color: #ecf0f1\">\n" +
                            "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                            "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">Hola "+sujeto+"!</h2>\n" +
                            "				<p style=\"margin: 2px; font-size: 15px\">\n" +
                            "					LA SUBIDA DE LOS CONDUCTORES HA SIDO EXITOSA<br>\n" +
                            "					ES NECESARIO LEGALIZAR LOS PAPELES CORRESPONDIENTES AL USUARIO Y AL CONDUCTOR</p><br><br>\n" +
                            "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                            "			</div>\n" +
                            "		</td>\n" +
                            "	</tr>\n" +
                            "</table>\n" +
                            "</body>";
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message, true);
        helper.setSubject("SUBIDA DE DATOS");
        helper.setTo(destinatario);
        helper.setText(cuerpoMensaje, true);
        mailSender.send(message);
    }
    
    @Async
    public void enviarCorreoAfiliacionExitosa(String destinatario, String sujeto, String usuario, String clave, String placa) throws MessagingException,AddressException{
        
        String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                            "\n" +
                            "<!--Copia desde aquí-->\n" +
                            "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                            "	<tr>\n" +
                            "		<td style=\"background-color: #ecf0f1\">\n" +
                            "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                            "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">Hola "+sujeto+"!</h2>\n" +
                            "				<p style=\"margin: 2px; font-size: 15px\">\n" +
                            "					tu solicitud de vinculación a la empresa Trex para el vehiculo de matricula: "+placa+" se ha completado de manera exitosa. Estas son tus credenciales<br>\n" +
                            "					Usuario: "+usuario+".<br>\n" +
                            "					Clave: "+clave+".<br>\n" +
                            "					Con ellas podras ingresar al sistema TREX SAS.</p><br><br>\n" +
                            "				<div style=\"width: 100%; text-align: center\">\n" +
                            "					<a style=\"text-decoration: none; border-radius: 5px; padding: 11px 23px; color: white; background-color: #3498db;\" href=\"http://181.143.139.108:4200\">Ir a TREX SAS</a>	\n" +
                            "				</div>\n" +
                            "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                            "			</div>\n" +
                            "		</td>\n" +
                            "	</tr>\n" +
                            "</table>\n" +
                            "</body>";
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message, true);
        helper.setSubject(solicitudAfiliacion);
        helper.setTo(destinatario);
        helper.setText(cuerpoMensaje, true);
        mailSender.send(message);
    }
    
    @Async
    public void enviarCorreoAfiliacionConObservacion(String destinatario, String sujeto, String placa, String observacion) throws MessagingException,AddressException{
        
        String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                            "\n" +
                            "<!--Copia desde aquí-->\n" +
                            "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                            "	<tr>\n" +
                            "		<td style=\"background-color: #ecf0f1\">\n" +
                            "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                            "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">Hola "+sujeto+"!</h2>\n" +
                            "				<p style=\"margin: 2px; font-size: 15px\">\n" +
                            "					tu solicitud de vinculación a la empresa Trex para el vehiculo de matricula: "+placa+" no se ha podido completar, aqui te presentamos la siguiente observacion.<br>\n" +
                            "					observacion: "+observacion+".<br>\n" +
                            "					por favor responde lo mas pronto a la solicitud por medio de la siguiente direccion de correo electronico.<br>\n" +
                            "					correo: analista@trexsas.com.co</p><br><br>\n" +
                            "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                            "			</div>\n" +
                            "		</td>\n" +
                            "	</tr>\n" +
                            "</table>\n" +
                            "</body>";
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message, true);
        helper.setSubject(solicitudAfiliacion);
        helper.setTo(destinatario);
        helper.setText(cuerpoMensaje, true);
        mailSender.send(message);
    }
    
    @Async
    public void enviarCorreoAvisoVencimientoPapelesConductor(String destinatario, String papelVencido, String vencimiento) throws MessagingException,AddressException{
        
    	String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                "\n" +
                "<!--Copia desde aquí-->\n" +
                "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                "	<tr>\n" +
                "		<td style=\"background-color: #ecf0f1\">\n" +
                "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">SEÑOR CONDUCTOR</h2>\n" +
                "				<p style=\"margin: 2px; font-size: 15px\">\n" +
                 					"MEDIANTE EL PRESENTE CORREO LE INFORMAMOS QUE<br>\\n "
                 					+ "EL VENCIMIENTO DE SU "+papelVencido+" es el día "+vencimiento+"<br>\n"+
                "					correo: analista@trexsas.com.co</p><br><br>\n" +
                "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                "			</div>\n" +
                "		</td>\n" +
                "	</tr>\n" +
                "</table>\n" +
                "</body>";
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message, true);
        helper.setSubject("RENOVACIÓN PAPELES");
        helper.setTo(destinatario);
        helper.setText(cuerpoMensaje, true);
        mailSender.send(message);
    }
    
    @Async
    public void enviarCorreoAvisoPapelesVehiculo(String destinatario, String papelVencido, String placa, String vencimiento) throws MessagingException,AddressException{
        
    	String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                "\n" +
                "<!--Copia desde aquí-->\n" +
                "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                "	<tr>\n" +
                "		<td style=\"background-color: #ecf0f1\">\n" +
                "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">AVISO DE BLOQUEO EN EL SISTEMA</h2>\n" +
                "				<p style=\"margin: 2px; font-size: 15px\">\n"
                					+"SEÑOR USUARIO, MEDIANTE EL PRESENTE CORREO LE INFORMAMOS QUE: "
                					+ "EL DOCUMENTO "+papelVencido+
                "					DEL VEHÍCULO CON PLACAS "+placa+"<br>\n" +
                "					ESTÁ A PUNTO DE VENCERSE EL DÍA "+vencimiento+" .<br>\n"+
                "					LE SOLICITAMOS QUE REALICE LA RENOVACIÓN DE ESTE DOCUMENTO ANTES DE LA FECHA INDICADA<br>\n" +
                "					correo: analista@trexsas.com.co</p><br><br>\n" +
                "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                "			</div>\n" +
                "		</td>\n" +
                "	</tr>\n" +
                "</table>\n" +
                "</body>";
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message, true);
        helper.setSubject("RENOVACION PAPELES");
        helper.setTo(destinatario);
        helper.setText(cuerpoMensaje, true);
        mailSender.send(message);
    }
    
    
    @Async
    public void enviarCorreoAvisoBloqueoVencimientoPapelesConductor(String destinatario, String papelVencido, String vencimiento) throws MessagingException,AddressException{
        
        String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                            "\n" +
                            "<!--Copia desde aquí-->\n" +
                            "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                            "	<tr>\n" +
                            "		<td style=\"background-color: #ecf0f1\">\n" +
                            "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                            "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">SEÑOR CONDUCTOR</h2>\n" +
                            "				<p style=\"margin: 2px; font-size: 15px\">\n" +
                             					"MEDIANTE EL PRESENTE CORREO LE INFORMAMOS QUE<br>\\n "
                             					+ "DEBIDO AL VENCIMIENTO DE SU "+papelVencido+" el día "+vencimiento+"<br>\n"+
                            "					su cuenta será bloqueada del sistema, con lo cual, por el momento, no seguiremos<br>\n"
                            					+ "con sus servicios hasta tanto no haya renovado su" +papelVencido+".<br>\n"+
                            "					correo: analista@trexsas.com.co</p><br><br>\n" +
                            "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                            "			</div>\n" +
                            "		</td>\n" +
                            "	</tr>\n" +
                            "</table>\n" +
                            "</body>";
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message, true);
        helper.setSubject("RENOVACIÓN PAPELES");
        helper.setTo(destinatario);
        helper.setText(cuerpoMensaje, true);
        mailSender.send(message);
    }
    
    @Async
    public void enviarCorreoAvisoBloqueoVehiculoVencimientoPapeles(String destinatario, String papelVencido, String placa) throws MessagingException,AddressException{
        
        String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                            "\n" +
                            "<!--Copia desde aquí-->\n" +
                            "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                            "	<tr>\n" +
                            "		<td style=\"background-color: #ecf0f1\">\n" +
                            "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                            "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">AVISO DE BLOQUEO EN EL SISTEMA</h2>\n" +
                            "				<p style=\"margin: 2px; font-size: 15px\">\n"
                            					+"SEÑOR USUARIO, MEDIANTE EL PRESENTE CORREO LE INFORMAMOS QUE: "
                            					+ "DEBIDO AL VENCIMIENTO DE "+papelVencido+
                            "					DEL VEHÍCULO CON PLACAS "+placa+"<br>\n" +
                            "					ESTE SERÁ BLOQUEADO EN EL SISTEMA, LO CUAL QUIERE DECIR QUE NO SERÁ TENIDO EN CUENTA.<br>\n"
                            					+ "PARA FUTUROS SERVICIOS, HASTA QUE HAYA RENOVADO EL DOCUMENTO.<br>\n" +
                            "					LE SOLICITAMOS QUE REALICE LA RENOVACIÓN LO MÁS PRONTO POSIBLE PARA SEGUIR CONTANDO CON SU COLABORACIÓN<br>\n" +
                            "					correo: analista@trexsas.com.co</p><br><br>\n" +
                            "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                            "			</div>\n" +
                            "		</td>\n" +
                            "	</tr>\n" +
                            "</table>\n" +
                            "</body>";
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message, true);
        helper.setSubject("VENCIMIENTO DE PAPELES");
        helper.setTo(destinatario);
        helper.setText(cuerpoMensaje, true);
        mailSender.send(message);
    }
    
    @Async
    public void enviarCorreoAvisoBloqueoVehiculoVencimientoLicencia(String destinatario, String papelVencido) throws MessagingException,AddressException{
        
        String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                            "\n" +
                            "<!--Copia desde aquí-->\n" +
                            "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                            "	<tr>\n" +
                            "		<td style=\"background-color: #ecf0f1\">\n" +
                            "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                            "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">AVISO DE BLOQUEO EN EL SISTEMA</h2>\n" +
                            "				<p style=\"margin: 2px; font-size: 15px\">\n"
                            					+"SEÑOR USUARIO, MEDIANTE EL PRESENTE CORREO LE INFORMAMOS QUE: "
                            					+ "DEBIDO AL VENCIMIENTO DE "+papelVencido+
                            "					ESTE SERÁ BLOQUEADO EN EL SISTEMA, LO CUAL QUIERE DECIR QUE NO SERÁ TENIDO EN CUENTA.<br>\n"
                            					+ "PARA FUTUROS SERVICIOS, HASTA QUE HAYA RENOVADO EL DOCUMENTO.<br>\n" +
                            "					LE SOLICITAMOS QUE REALICE LA RENOVACIÓN LO MÁS PRONTO POSIBLE PARA SEGUIR CONTANDO CON SU COLABORACIÓN<br>\n" +
                            "					correo: analista@trexsas.com.co</p><br><br>\n" +
                            "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                            "			</div>\n" +
                            "		</td>\n" +
                            "	</tr>\n" +
                            "</table>\n" +
                            "</body>";
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message, true);
        helper.setSubject("VENCIMIENTO LICENCIA");
        helper.setTo(destinatario);
        helper.setText(cuerpoMensaje, true);
        mailSender.send(message);
    }
    
    @Async
    public void enviarCorreoNuevoServicioConductores(List<Conductor> listaConductores, Servicio reserva)throws Exception
    {
    	System.out.println("reserva : "+reserva.getPasajero().getPersona().getApellidoPersona());
    	List<Conductor> destinos = listaConductores;
    	Servicio reservaNueva = reserva; 
    	String observacion = reserva.getObservacionesReserva().equals("")?"sin observaciones":reserva.getObservacionesReserva();
    	for (Conductor conductor : destinos) 
    	{
    		String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                    "\n" +
                    "<!--Copia desde aquí-->\n" +
                    "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                    "	<tr>\n" +
                    "		<td style=\"background-color: #ecf0f1\">\n" +
                    "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                    "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">Hola "+conductor.getPersona().getNombrePersona()+" "+conductor.getPersona().getApellidoPersona()+"!</h2>\n" +
                    "				<p style=\"margin: 2px; font-size: 15px\">\n" +
                    "					Se ha registrado una nueva reserva en el sistema con la siguiente información:<br>\n"
                    + 						"<ol>"
                    						+ "<li>Día: "+reservaNueva.getFechaInicioReserva()+" </li>"
                    						+ "<li>Punto de Partida:"+reservaNueva.getDireccionOrigenReserva()+"</li>"
                    						+ "<li>Punto de Llegada:"+reservaNueva.getDireccionDestinoReserva()+"</li>"
                    						+ "<li>Hora: "+reservaNueva.getHoraInicioReserva()+"</li>"
                    						+ "<li>Paciente:"+reservaNueva.getPasajero().getPersona().getNombrePersona()+" "+reservaNueva.getPasajero().getPersona().getApellidoPersona()+"</li>"
                    + 						"</ol>no se ha podido completar, aqui te presentamos la siguiente observacion.<br>\n" +
                    "					observacion: "+observacion+".<br>\n" +
                    "					por favor responde lo mas pronto a la solicitud por medio de la siguiente direccion de correo electronico.<br>\n" +
                    "					correo: analista@trexsas.com.co</p><br><br>\n" +
                    "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                    "			</div>\n" +
                    "		</td>\n" +
                    "	</tr>\n" +
                    "</table>\n" +
                    "</body>";
    		MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper= new MimeMessageHelper(message, true);
            helper.setSubject(solicitudAfiliacion);
            helper.setTo(conductor.getPersona().getCorreoPersona());
            helper.setText(cuerpoMensaje, true);
            mailSender.send(message);
		}
    }
    
    @Async
    public void enviarCorreoCreacionAfiliacion(String destinatario, String sujeto, String placa, long id) throws MessagingException,AddressException{
        String link="http://181.143.139.108:4200/vinculacion/estado/";
        String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                            "\n" +
                            "<!--Copia desde aquí-->\n" +
                            "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                            "	<tr>\n" +
                            "		<td style=\"background-color: #ecf0f1\">\n" +
                            "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                            "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">Hola "+sujeto+"!</h2>\n" +
                            "				<p style=\"margin: 2px; font-size: 15px\">\n" +
                            "					Tu solicitud de vinculación a la empresa Trex para el vehiculo de matricula: "+placa+" fue enviada satisfactoriamente.<br>\n" +
                            "					En el siguiente enlace podras consultar el estado de la Afiliacion.<br>\n" +
                            "					</p><br><br>\n" +
                            "				<div style=\"width: 100%; text-align: center\">\n" +
                            "					<a style=\"text-decoration: none; border-radius: 5px; padding: 11px 23px; color: white; background-color: #3498db;\" href=\""+link+id+"\">Ver Estado</a>	\n" +
                            "				</div>\n" +
                            "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                            "			</div>\n" +
                            "		</td>\n" +
                            "	</tr>\n" +
                            "</table>\n" +
                            "</body>";
       
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message, true);
        helper.setSubject(solicitudAfiliacion);
        helper.setTo(destinatario);
        helper.setText(cuerpoMensaje, true);
        mailSender.send(message);
    }
    
    @Async
	public void enviarCorreoNuevaReservaPasajero(String destinatario, String sujeto, String fecha, String origen, String destino,String cliente) throws MessagingException,AddressException{
	        
        String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                            "\n" +
                            "<!--Copia desde aquí-->\n" +
                            "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                            "	<tr>\n" +
                            "		<td style=\"background-color: #ecf0f1\">\n" +
                            "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                            "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">Hola "+sujeto+" ("+cliente+")!</h2>\n" +
                            "				<p style=\"margin: 2px; font-size: 15px\">\n" +
                            "					Se ha generado una nueva reserva de transporte con la empresa Trex SAS para la fecha: "+fecha+", con origen: "+origen+", y destino: "+destino+".<br>\n" +
                            "					Por favor asiste puntualmente al lugar de origen.<br>\n" +
                            "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                            "			</div>\n" +
                            "		</td>\n" +
                            "	</tr>\n" +
                            "</table>\n" +
                            "</body>";
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message, true);
        helper.setSubject(reserva);
        helper.setTo(destinatario);
        helper.setText(cuerpoMensaje, true);
        mailSender.send(message);
    }
    
    @Async
	public void enviarCorreoEditarReservaPasajero(String destinatario, String sujeto, String fecha, String origen, String destino,String cliente) throws MessagingException,AddressException{
	        
        String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                            "\n" +
                            "<!--Copia desde aquí-->\n" +
                            "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                            "	<tr>\n" +
                            "		<td style=\"background-color: #ecf0f1\">\n" +
                            "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                            "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">Hola "+sujeto+" ("+cliente+")!</h2>\n" +
                            "				<p style=\"margin: 2px; font-size: 15px\">\n" +
                            "					Se ha generado una modificacion a la reserva de transporte con la empresa Trex SAS, ahora sera en la fecha: "+fecha+", con origen: "+origen+", y destino: "+destino+".<br>\n" +
                            "					Por favor asiste puntualmente al lugar de origen.<br>\n" +
                            "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                            "			</div>\n" +
                            "		</td>\n" +
                            "	</tr>\n" +
                            "</table>\n" +
                            "</body>";
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message, true);
        helper.setSubject(reserva);
        helper.setTo(destinatario);
        helper.setText(cuerpoMensaje, true);
        mailSender.send(message);
    }
	
	@Async
	public void enviarCorreoNuevaReservaCliente(String destinatario, String sujeto, String fecha, String origen, String destino,String cliente) throws MessagingException,AddressException{
        
        String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                            "\n" +
                            "<!--Copia desde aquí-->\n" +
                            "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                            "	<tr>\n" +
                            "		<td style=\"background-color: #ecf0f1\">\n" +
                            "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                            "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">Hola "+cliente+"!</h2>\n" +
                            "				<p style=\"margin: 2px; font-size: 15px\">\n" +
                            "					Se ha generado una nueva reserva de transporte para el paciente "+sujeto+" con la empresa Trex SAS para la fecha: "+fecha+", con origen: "+origen+", y destino: "+destino+".<br>\n" +
                            "					Por favor asistir puntualmente al lugar de origen.<br>\n" +
                            "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                            "			</div>\n" +
                            "		</td>\n" +
                            "	</tr>\n" +
                            "</table>\n" +
                            "</body>";
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message, true);
        helper.setSubject(reserva);
        helper.setTo(destinatario);
        helper.setText(cuerpoMensaje, true);
        mailSender.send(message);
    }
	
	@Async
	public void enviarCorreoEditarReservaCliente(String destinatario, String sujeto, String fecha, String origen, String destino,String cliente) throws MessagingException,AddressException{
        
        String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                            "\n" +
                            "<!--Copia desde aquí-->\n" +
                            "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                            "	<tr>\n" +
                            "		<td style=\"background-color: #ecf0f1\">\n" +
                            "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                            "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">Hola "+cliente+"!</h2>\n" +
                            "				<p style=\"margin: 2px; font-size: 15px\">\n" +
                            "					Se ha generado una modificacion a la reserva de transporte para el paciente "+sujeto+" con la empresa Trex SAS, ahora sera en la fecha: "+fecha+", con origen: "+origen+", y destino: "+destino+".<br>\n" +
                            "					Por favor asistir puntualmente al lugar de origen.<br>\n" +
                            "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                            "			</div>\n" +
                            "		</td>\n" +
                            "	</tr>\n" +
                            "</table>\n" +
                            "</body>";
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message, true);
        helper.setSubject(reserva);
        helper.setTo(destinatario);
        helper.setText(cuerpoMensaje, true);
        mailSender.send(message);
    }
	
	@Async
	public void enviarCorreoNuevaOrdenConductor(String destinatario, String conductor, String fecha, String origen, String destino,String cliente,String paciente) throws MessagingException,AddressException{
        
        String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                            "\n" +
                            "<!--Copia desde aquí-->\n" +
                            "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                            "	<tr>\n" +
                            "		<td style=\"background-color: #ecf0f1\">\n" +
                            "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                            "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">Hola "+conductor+"!</h2>\n" +
                            "				<p style=\"margin: 2px; font-size: 15px\">\n" +
                            "					Se ha generado una nueva orden de servicio para el paciente "+paciente+" de la empresa "+cliente+" para la fecha: "+fecha+", con origen: "+origen+", y destino: "+destino+".<br>\n" +
                            "					Por favor asistir puntualmente al lugar de origen.<br>\n" +
                            "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                            "			</div>\n" +
                            "		</td>\n" +
                            "	</tr>\n" +
                            "</table>\n" +
                            "</body>";
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message, true);
        helper.setSubject(reserva);
        helper.setTo(destinatario);
        helper.setText(cuerpoMensaje, true);
        mailSender.send(message);
    }
	
	@Async
	public void enviarCorreoEditarOrdenConductor(String destinatario, String conductor, String fecha, String origen, String destino,String cliente,String paciente) throws MessagingException,AddressException{
        
        String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                            "\n" +
                            "<!--Copia desde aquí-->\n" +
                            "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                            "	<tr>\n" +
                            "		<td style=\"background-color: #ecf0f1\">\n" +
                            "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                            "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">Hola "+conductor+"!</h2>\n" +
                            "				<p style=\"margin: 2px; font-size: 15px\">\n" +
                            "					Se ha generado una modificacion a la orden de servicio para el paciente "+paciente+" de la empresa "+cliente+", ahora sera en la fecha: "+fecha+", con origen: "+origen+", y destino: "+destino+".<br>\n" +
                            "					Por favor asistir puntualmente al lugar de origen.<br>\n" +
                            "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                            "			</div>\n" +
                            "		</td>\n" +
                            "	</tr>\n" +
                            "</table>\n" +
                            "</body>";
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message, true);
        helper.setSubject(reserva);
        helper.setTo(destinatario);
        helper.setText(cuerpoMensaje, true);
        mailSender.send(message);
    }
	
	@Async
	public void enviarCorreoNuevaFacturaCliente(String destinatario, String cliente, Long id,Long total, String fi,String ff,String fp,String concepto) throws MessagingException,AddressException{
        
        String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                            "\n" +
                            "<!--Copia desde aquí-->\n" +
                            "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                            "	<tr>\n" +
                            "		<td style=\"background-color: #ecf0f1\">\n" +
                            "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                            "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">Hola "+cliente+"!</h2>\n" +
                            "				<p style=\"margin: 2px; font-size: 15px\">\n" +
                            "					Se ha generado una nueva factura con consecutivo unico: "+id+", con un total de: "+total+", por concepto de: "+concepto+" entre el periodo: "+fi+" - "+ff+", por los servicios prestados por cuenta de TREX SAS, con fecha de pago maxima: "+fp+".<br>\n" +
                            "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                            "			</div>\n" +
                            "		</td>\n" +
                            "	</tr>\n" +
                            "</table>\n" +
                            "</body>";
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message, true);
        helper.setSubject(reserva);
        helper.setTo(destinatario);
        helper.setText(cuerpoMensaje, true);
        mailSender.send(message);
    }
	
	@Async
	public void enviarCorreoEditarFacturaCliente(String destinatario, String cliente, Long id,Long total, String fi,String ff,String fp,String concepto) throws MessagingException,AddressException{
        
        String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                            "\n" +
                            "<!--Copia desde aquí-->\n" +
                            "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                            "	<tr>\n" +
                            "		<td style=\"background-color: #ecf0f1\">\n" +
                            "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                            "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">Hola "+cliente+"!</h2>\n" +
                            "				<p style=\"margin: 2px; font-size: 15px\">\n" +
                            "					Se ha modificado la factura con consecutivo unico: "+id+", ahora sera con un total de: "+total+", por concepto de: "+concepto+", entre el periodo: "+fi+" - "+ff+", por los servicios prestados por cuenta de TREX SAS, con fecha de pago maxima: "+fp+".<br>\n" +
                            "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                            "			</div>\n" +
                            "		</td>\n" +
                            "	</tr>\n" +
                            "</table>\n" +
                            "</body>";
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message, true);
        helper.setSubject(reserva);
        helper.setTo(destinatario);
        helper.setText(cuerpoMensaje, true);
        mailSender.send(message);
    }
	
	@Async
	public void enviarCorreoPQR(String destinatario,long pqr) throws MessagingException,AddressException{
	        
		String tipoPQR = "";
		switch (Integer.valueOf(String.valueOf(pqr)))
		{
		case 1:
			tipoPQR ="PETICIÓN";
			break;
		case 2:
			tipoPQR ="QUEJA";
			break;
		case 3:
			tipoPQR ="RECLAMO";
			break;
		case 4:
			tipoPQR ="COMENTARIO";
			break;

		default:
			break;
		}
        String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                            "\n" +
                            "<!--Copia desde aquí-->\n" +
                            "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                            "	<tr>\n" +
                            "		<td style=\"background-color: #ecf0f1\">\n" +
                            "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                            "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">HOLA SEÑOR USUARIO</h2>\n" +
                            "				<p style=\"margin: 2px; font-size: 15px\">\n" +
                            "					Se ha registrado un nuevo PQR de tipo"+tipoPQR+".<br>\n" +
                            "					Importante revisar en el sistema y atender cualquier novedad.<br>\n" +
                            "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                            "			</div>\n" +
                            "		</td>\n" +
                            "	</tr>\n" +
                            "</table>\n" +
                            "</body>";
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message, true);
        helper.setSubject(reserva);
        helper.setTo(destinatario);
        helper.setText(cuerpoMensaje, true);
        mailSender.send(message);
    }
	
	@Async
	public void enviarCorreoEncuesta() throws MessagingException,AddressException{
        System.out.println("enviando correo");
        String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                            "\n" +
                            "<!--Copia desde aquí-->\n" +
                            "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                            "	<tr>\n" +
                            "		<td style=\"background-color: #ecf0f1\">\n" +
                            "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                            "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">Hola!</h2>\n" +
                            "				<p style=\"margin: 2px; font-size: 15px\">\n" +
                            "					Se ha generado una nueva reserva de transporte para el paciente" +
                            "					Por favor asistir puntualmente al lugar de origen.<br>\n" +
                            				" <a href=\"http://localhost:4200/encuesta/40\" type=\"button\" class=\"btn btn-info\">encuesta de satisfacción</a> "+
                            "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                            "			</div>\n" +
                            "		</td>\n" +
                            "	</tr>\n" +
                            "</table>\n" +
                            "</body>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message, true);
        helper.setSubject(reserva);
        helper.setTo("bustosjuanpablo195@gmail.com");
        helper.setText(cuerpoMensaje, true);
        mailSender.send(message);
    }
	
	@Async
	public void enviarCorreoCancelacionConductor(String destinatario,String fechaReserva, String horaReserva) throws MessagingException,AddressException{
        String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                            "\n" +
                            "<!--Copia desde aquí-->\n" +
                            "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                            "	<tr>\n" +
                            "		<td style=\"background-color: #ecf0f1\">\n" +
                            "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                            "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">HOLA SEÑOR USUARIO</h2>\n" +
                            "				<p style=\"margin: 2px; font-size: 15px\">\n"
                            + "Debido a inconvenientes el servicio para el día "+fechaReserva+" a las "+horaReserva+" ha sido cancelado. "+
                               "En estos momentos estamos buscando un nuevo conductor para que atienda su servicio para el día y hora antes nombrados"+
                            "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                            "			</div>\n" +
                            "		</td>\n" +
                            "	</tr>\n" +
                            "</table>\n" +
                            "</body>";
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message, true);
        helper.setSubject(reserva);
        helper.setTo(destinatario);
        helper.setText(cuerpoMensaje, true);
        mailSender.send(message);
    }
	
	@Async
	public void enviarCorreoTerminacionServicio(String destinatario,long pqr, long ordenServicio) throws MessagingException,AddressException{
		
		String idEncuestaOriginal = "10000000";
        String idEncuestaCdoficada = Base64.getEncoder().encodeToString(idEncuestaOriginal.getBytes());
		
        String idOrdenservicioOriginal = String.valueOf(ordenServicio);
        String idOrdenServicioCodificada = Base64.getEncoder().encodeToString(idOrdenservicioOriginal.getBytes());
        
        String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                            "\n" +
                            "<!--Copia desde aquí-->\n" +
                            "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                            "	<tr>\n" +
                            "		<td style=\"background-color: #ecf0f1\">\n" +
                            "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                            "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">HOLA SEÑOR PASAJERO</h2>\n" +
                            "				<p style=\"margin: 2px; font-size: 15px\">\n" +
                            "					Señor usuario su servicio a terminado<br>"
                            + 					"por favor llene una encuesta de satisfacción,<br>"
                            + 					"esto con el fin de mejorar nuestro servicio <br>\n"
                            + "<a href=\"http://localhost:4200/encuesta?id="+idEncuestaCdoficada+"&id2="+idOrdenServicioCodificada+"\">encuesta de satisfacción</a>"+
                            "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                            "			</div>\n" +
                            "		</td>\n" +
                            "	</tr>\n" +
                            "</table>\n" +
                            "</body>";
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message, true);
        helper.setSubject(reserva);
        helper.setTo(destinatario);
        helper.setText(cuerpoMensaje, true);
        mailSender.send(message);
    }
    
    /*public void enviarCorreoConductorViaje(String destinatario, String contenido){
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");
        
        Session sesion = Session.getDefaultInstance(propiedad);
        MimeMessage mail = new MimeMessage(sesion);
        try {
            mail.setFrom(new InternetAddress (correoEnvia));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            mail.setSubject(viajeProgramadoConductor);
            mail.setContent(contenido,"text/html");
            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(correoEnvia,contrasena);
            transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transporte.close();
            System.out.println("correo enviado exitosamente");
        } catch (AddressException ex) {
            System.out.println("el correo no existe: "+ex.getMessage());
        } catch (MessagingException ex) {
            System.out.println("no se envio el correo: "+ex.getMessage());
        }
    }*/
	
	@Async
	public void enviarCorreoNuevoServicioConductores(List<Conductor> listaConductores, String fechaInicioReserva,
			String nombrePasajero, String direccionOrigenReserva, String direccionDestinoReserva, String horaInicioReserva, String observaciones) throws MessagingException 
	{
		System.out.println("reserva : "+nombrePasajero);
		System.out.println("reserva : "+fechaInicioReserva);
		System.out.println("reserva : "+direccionDestinoReserva);
		System.out.println("reserva : "+direccionOrigenReserva);
		System.out.println("reserva : "+horaInicioReserva);
		
    	List<Conductor> destinos = listaConductores;
    	//Reserva reservaNueva = reserva; 
    	String observacion = observaciones.equals("")?"sin observaciones":observaciones;
    	for (Conductor conductor : destinos) 
    	{
    		String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                    "\n" +
                    "<!--Copia desde aquí-->\n" +
                    "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                    "	<tr>\n" +
                    "		<td style=\"background-color: #ecf0f1\">\n" +
                    "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                    "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">Hola "+conductor.getPersona().getNombrePersona()+" "+conductor.getPersona().getApellidoPersona()+"!</h2>\n" +
                    "				<p style=\"margin: 2px; font-size: 15px\">\n" +
                    "					Se ha registrado una nueva reserva en el sistema con la siguiente información:<br>\n"
                    + 						"<ol>"
                    						+ "<li>Día: "+fechaInicioReserva+" </li>"
                    						+ "<li>Punto de Partida:"+direccionOrigenReserva+"</li>"
                    						+ "<li>Punto de Llegada:"+direccionDestinoReserva+"</li>"
                    						+ "<li>Hora: "+horaInicioReserva+"</li>"
                    						+ "<li>Paciente:"+nombrePasajero+"</li>"
                    + 						"</ol>no se ha podido completar, aqui te presentamos la siguiente observacion.<br>\n" +
                    "					observacion: "+observacion+".<br>\n" +
                    "					por favor responde lo mas pronto a la solicitud por medio de la siguiente direccion de correo electronico.<br>\n" +
                    "					correo: analista@trexsas.com.co</p><br><br>\n" +
                    "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                    "			</div>\n" +
                    "		</td>\n" +
                    "	</tr>\n" +
                    "</table>\n" +
                    "</body>";
    		MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper= new MimeMessageHelper(message, true);
            helper.setSubject(solicitudAfiliacion);
            helper.setTo(conductor.getPersona().getCorreoPersona());
            helper.setText(cuerpoMensaje, true);
            mailSender.send(message);
		}
	}

	
	@Async
	//@Scheduled(fixedRate = 1)
	public void enviarCorreosServicioNuevo(List<Conductor> listaConductores, Servicio reserva) throws MessagingException
	{
		System.out.println("remitente : "+listaConductores.size());
		
		
			for (Conductor remitente : listaConductores) 
	    	{	
	    		System.out.println("2. ");
	    		
	    		String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
	                    "\n" +
	                    "<!--Copia desde aquí-->\n" +
	                    "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
	                    "	<tr>\n" +
	                    "		<td style=\"background-color: #ecf0f1\">\n" +
	                    "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
	                    "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">Hola "+remitente.getPersona().getNombrePersona()+" "+remitente.getPersona().getApellidoPersona()+"!</h2>\n" +
	                    "				<p style=\"margin: 2px; font-size: 15px\">\n" +
	                    "					Se ha registrado una nueva reserva en el sistema con la siguiente información:<br>\n"
	                    + 						"<ol>"
							                    + "<li>Día: "+reserva.getFechaInicioReserva()+" </li>"
												+ "<li>Punto de Partida:"+reserva.getDireccionOrigenReserva()+"</li>"
												+ "<li>Punto de Llegada:"+reserva.getLatitudDestinoReserva()+"</li>"
												+ "<li>Hora: "+reserva.getHoraInicioReserva()+"</li>"
												+ "<li>Paciente:"+reserva.getFechaInicioReserva()+"</li>"
	                    + 						"</ol><br>\n" +
	                    "					observacion: <br>\n" +reserva.getObservacionesReserva()==""?"sin observaciones":reserva.getObservacionesReserva()+
	                    "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
	                    "			</div>\n" +
	                    "		</td>\n" +
	                    "	</tr>\n" +
	                    "</table>\n" +
	                    "</body>";
	    		System.out.println("3. ");
	    		System.out.println("creado : "+mailSender==null);
	    		MimeMessage message = mailSender.createMimeMessage();
	    		System.out.println("4. ");
	    		MimeMessageHelper helper= new MimeMessageHelper(message, true);
	    		System.out.println("5. ");
	    		helper.setSubject("NUEVO SERVICO EN EL SISTEMA");
	    		System.out.println("6. ");
	    		helper.setTo(remitente.getPersona().getCorreoPersona());
	    		System.out.println("7. ");
	            helper.setText(cuerpoMensaje, true);
	            System.out.println("8. ");
	            mailSender.send(message);
			}

		
    }
	
	@Async
	//@Scheduled(fixedRate = 1)
	public void enviarCorreosMasivos(List<HashMap<String, String>> remitentes, String descripcion, String titulo) throws MessagingException
	{
		System.out.println("remitente : "+remitentes.size());
		
		
			for (HashMap<String, String> remitente : remitentes) 
	    	{
				System.out.println("enviando a "+remitente.get("correo"));
	    		String dirigido = "";
	    		if(remitente.get("perfil").equals("Pasajero"))
	    		{
	    			dirigido = "a todos los pasajeros";
	    		}else if(remitente.get("perfil").equals("Conductor"))
	    		{
	    			dirigido = "a todos los conductores";
	    		}else if(remitente.get("perfil").equals("Propietario"))
	    		{
	    			dirigido = "a todos los propietarios";
	    		}else if(remitente.get("perfil").equals("Cliente"))
	    		{
	    			dirigido = "a todos nuestros clientes";
	    		}
	    		
	    		System.out.println("2. "+dirigido);
	    		
	    		String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
	                    "\n" +
	                    "<!--Copia desde aquí-->\n" +
	                    "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n"
	                    + "<h1>"+titulo+"</h1>"+
	                    "	<tr>\n" +
	                    "		<td style=\"background-color: #ecf0f1\">\n" +
	                    "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
	                    "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">"+dirigido+" les informamos que: !</h2>\n" +
	                    "				<p style=\"margin: 2px; font-size: 15px\">\n"+
	                    						descripcion+
	                    "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
	                    "			</div>\n" +
	                    "		</td>\n" +
	                    "	</tr>\n" +
	                    "</table>\n" +
	                    "</body>";
	    		System.out.println("3. ");
	    		System.out.println("creado : "+mailSender==null);
	    		MimeMessage message = mailSender.createMimeMessage();
	    		System.out.println("4. ");
	    		MimeMessageHelper helper= new MimeMessageHelper(message, true);
	    		System.out.println("5. ");
	    		helper.setSubject(titulo);
	    		System.out.println("6. ");
	    		helper.setTo(remitente.get("correo"));
	    		System.out.println("7. ");
	            helper.setText(cuerpoMensaje, true);
	            System.out.println("8. ");
	            mailSender.send(message);
			}

		
    }
	
	@Async
	//@Scheduled(fixedRate = 1)
	public void enviarCorreoNotificacion(String remitente, String descripcion, String titulo) throws MessagingException
	{
		System.out.println("remitente : "+remitente);
	    		System.out.println("2. ");
	    		
	    		String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
	                    "\n" +
	                    "<!--Copia desde aquí-->\n" +
	                    "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n"
	                    + "<h1>"+titulo+"</h1>"+
	                    "	<tr>\n" +
	                    "		<td style=\"background-color: #ecf0f1\">\n" +
	                    "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
	                    "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">Señor Usuario les informamos que: !</h2>\n" +
	                    "				<p style=\"margin: 2px; font-size: 15px\">\n"+
	                    						descripcion+
	                    "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
	                    "			</div>\n" +
	                    "		</td>\n" +
	                    "	</tr>\n" +
	                    "</table>\n" +
	                    "</body>";
	    		System.out.println("3. ");
	    		System.out.println("creado : "+mailSender==null);
	    		MimeMessage message = mailSender.createMimeMessage();
	    		System.out.println("4. ");
	    		MimeMessageHelper helper= new MimeMessageHelper(message, true);
	    		System.out.println("5. ");
	    		helper.setSubject(titulo);
	    		System.out.println("6. ");
	    		helper.setTo(remitente);
	    		System.out.println("7. ");
	            helper.setText(cuerpoMensaje, true);
	            System.out.println("8. ");
	            mailSender.send(message);
    }
	
	@Async
	public void enviarCorreoNuevoUsuario(String usuario, String password, String remitente) throws MessagingException
	{
		System.out.println("remitente : "+remitente);
		System.out.println("2. ");
		
		String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                "\n" +
                "<!--Copia desde aquí-->\n" +
                "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n"
                + "<h1>Credenciales de Ingreso Al Sistema</h1>"+
                "	<tr>\n" +
                "		<td style=\"background-color: #ecf0f1\">\n" +
                "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">Señor Usuario les informamos que: !</h2>\n" +
                "				<p style=\"margin: 2px; font-size: 15px\">\n"+
                					"Señor Usuario, sus credenciales de ingreso son las siguientes:\n <br>"+
                					"usuario : "+usuario+"<br>\n"+
                					"password : "+password+"<br>\n"+
                "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                " <a href=\"http://localhost:4200\" type=\"button\" class=\"btn btn-info\">INGRESAR</a> "+
                "			</div>\n" +
                "		</td>\n" +
                "	</tr>\n" +
                "</table>\n" +
                "</body>";
		System.out.println("3. ");
		System.out.println("creado : "+mailSender==null);
		MimeMessage message = mailSender.createMimeMessage();
		System.out.println("4. ");
		MimeMessageHelper helper= new MimeMessageHelper(message, true);
		System.out.println("5. ");
		helper.setSubject("Credenciales de Acceso");
		System.out.println("6. ");
		helper.setTo(remitente);
		System.out.println("7. ");
        helper.setText(cuerpoMensaje, true);
        System.out.println("8. ");
        mailSender.send(message);
	}
	@Async
	public void enviarCorreoNuevaContraseña(String password, String remitente) throws MessagingException
	{
		System.out.println("remitente : "+remitente);
		System.out.println("2. ");
		
		String cuerpoMensaje="<body style=\"background-color: #ecf0f1 \">\n" +
                "\n" +
                "<!--Copia desde aquí-->\n" +
                "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n"
                + "<h1>Credenciales de Ingreso Al Sistema</h1>"+
                "	<tr>\n" +
                "		<td style=\"background-color: #ecf0f1\">\n" +
                "			<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                "				<h2 style=\"color: #e67e22; margin: 0 0 7px\">Señor Usuario les informamos que: !</h2>\n" +
                "				<p style=\"margin: 2px; font-size: 15px\">\n"+
                					"Señor Usuario, su nueva contraseña de ingreso en la siguiente:\n <br>"+
                					"<h4>password : "+password+"</h4>\n"+
                "				<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">@TREX SAS 2020</p>\n" +
                " <a href=\"http://localhost:4200\" type=\"button\" class=\"btn btn-info\">INGRESAR</a> "+
                "			</div>\n" +
                "		</td>\n" +
                "	</tr>\n" +
                "</table>\n" +
                "</body>";
		System.out.println("3. ");
		System.out.println("creado : "+mailSender==null);
		MimeMessage message = mailSender.createMimeMessage();
		System.out.println("4. ");
		MimeMessageHelper helper= new MimeMessageHelper(message, true);
		System.out.println("5. ");
		helper.setSubject("Credenciales de Acceso");
		System.out.println("6. ");
		helper.setTo(remitente);
		System.out.println("7. ");
        helper.setText(cuerpoMensaje, true);
        System.out.println("8. ");
        mailSender.send(message);
	}
	
}
