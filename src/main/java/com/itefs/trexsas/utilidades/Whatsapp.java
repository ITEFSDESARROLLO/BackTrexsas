package com.itefs.trexsas.utilidades;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class Whatsapp {

	// Find your Account Sid and Token at twilio.com/user/account
	  public static final String ACCOUNT_SID = "ACbde720955882fbb4fcd1d98117d6358a";
	  public static final String AUTH_TOKEN = "d3bbcdc7811cb3f0f90831070cb630d7";

	  
	  //www.twilio.com/referral/6Mv2Rc
	  public void primerW() {
	    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber("whatsapp:+57 322 3030597"),
                new PhoneNumber("whatsapp:+14155238886"),
                "funciono!!!")
            .create();

        System.out.println(message.getSid());
	  }
	  
	  public static void main(String[] args) {
		  Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	        Message message = Message.creator(new PhoneNumber("whatsapp:+573223030597"),new PhoneNumber("whatsapp:+14155238886"),"funciono!!!").create();
	        System.out.println(message.getSid());
	  }
}
