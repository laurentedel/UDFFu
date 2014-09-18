package com.laurentedel.hive.UDF;

import org.junit.Assert;
import org.apache.hadoop.io.Text;
import org.junit.Test;

public class URLDecodeTest {
  
  @Test
  public void testUDF() {
    URLDecode example = new URLDecode();
    Assert.assertEquals("hello world", example.evaluate(new Text("hello%20world")).toString());
    Assert.assertEquals("3suisses -20%", example.evaluate(new Text("3suisses%20-20%")).toString());
    Assert.assertEquals("serviette microfibre 100% polyester", example.evaluate(new Text("serviette+microfibre+100%+polyester")).toString());
    Assert.assertEquals("3 suisses matelas à très bon rapport qualité/prix<>", example.evaluate(new Text("3%20suisses%20matelas%20%C3%A0%20tr%C3%A8s%20bon%20rapport%20qualit%C3%A9/prix%3C%3E")).toString());
  }

}
