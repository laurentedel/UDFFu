package com.laurentedel.hive.UDF;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
import java.net.URLDecoder;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * URLDecode
 * This is supposed to decode multiple wrong Strings 
 * as strings terminated by % for example
 * 
 * author: Laurent Edel
 * date: 17-09-2014
 * version:0.1
 */

@Description(
       name = "URLDecode",
       value = "_FUNC_(str) - Returns urldecoded string",
       extended = "Example:\n" +
       "  > SELECT URLDecode(keyword) FROM hits;"
       )

public class URLDecode extends UDF {
  
  public static ScriptEngineManager factory = new ScriptEngineManager();
  public static ScriptEngine engine = factory.getEngineByName("JavaScript");
  
  public Text evaluate(Text s) {
  Text to_value = new Text();
  if(s != null) {
    
    try {
      String tmp = (String) engine.eval("decodeURIComponent('" + s.toString().replaceAll("([^\\\\])'", "$1\\\\'") + "')");
      to_value.set(tmp);
    } catch (ScriptException e) {
      try {
        to_value.set(URLDecoder.decode(s.toString(), "UTF-8"));
      } catch ( Exception e1) {
        try {
          to_value.set(URLDecoder.decode(s.toString().replace("%","%25"), "UTF-8").replace("+", " ").replace("%20", " ")
              .replace("%3C","<").replace("%3E",">").replace("%C3%A9","é").replace("%C3%A0","à").replace("%C3%A8","è")
              .replace("%22","\"").replace("%C3%B4", "ô").replace("%C2%AE","®").replace("%C2%B0", "°")
              .replace("%C3%87","Ç").replace("%C3%AA","ê").replace("%E2%80%99","’").replace("%E2%82%AC","€").replace("%27","'")
              .replace("%C3%89","É").replace("%C2%B2","²").replace("%C3%Ae","î").replace("%2F","/"));
          
        } catch (Exception e2) {
          // decoding not possible, returning string as is
          to_value = new Text(s);
        }
        
      }
    };
  
  }
  return to_value;
  }
}
