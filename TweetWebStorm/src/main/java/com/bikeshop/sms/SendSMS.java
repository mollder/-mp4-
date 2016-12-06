package com.bikeshop.sms;

import java.util.HashMap;
import org.json.simple.JSONObject;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

public class SendSMS
{
    public SendSMS(String a)
    {
    	String api_key = "NCS5842EAE4D24B6";
        String api_secret = "5B594ADA59F9532CFC2E3208205B4799";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", "01093546552");
        params.put("from", "01093546552");
        params.put("type", "SMS");
        params.put("text", a);
        params.put("app_version", "test app 1.2"); // application name and version

        try {
        	JSONObject obj = (JSONObject) coolsms.send(params);
        	System.out.println(obj.toString());
        } catch (CoolsmsException e) {
        	System.out.println(e.getMessage());
        	System.out.println(e.getCode());
        }
    }
}