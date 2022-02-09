package com.oner365.test.controller.client;

import java.util.Base64;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.BodyInserters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oner365.sys.vo.LoginUserVo;
import com.oner365.test.controller.BaseControllerTest;
import com.oner365.util.Cipher;
import com.oner365.util.Md5Util;
import com.oner365.util.RsaUtils;

/**
 * Test ClientTestController
 *
 * @author liutao
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientTestControllerTest extends BaseControllerTest {

  private static final String PATH = "/client";

  public static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAig45DTBQET96qSwuQmNSX8C2jMhMo4y0ss+dcYq6TpSUGxhA6m1V0JSrl+B1yY+g+JwjAf6rDqELi9V1U786/AQRSoMG4a1rnrYS0PPOPeBRKQ4PUaiaN8wi6C1Sd9ZDnSFE3FKgReMUFvvGYnH/8Jlm9LanK1mKGTMFnoFRkqbIN3jvfZlZNOy5F2ydNlyZN2KajzdOOibCfQFEE2p2BJ/LiLmzGlnh3G/bG/q2lohpwPotRvnVX/Sbe24EUOzUjkQqYitcHk/b1YreGqz0Iu4SCA3ASzb+HtPTph0hxeWqkhBx/pWwVYYSfAhGf/c8WN5j+F9Si2PZZmooJ7l70QIDAQAB";
  public static String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCKDjkNMFARP3qpLC5CY1JfwLaMyEyjjLSyz51xirpOlJQbGEDqbVXQlKuX4HXJj6D4nCMB/qsOoQuL1XVTvzr8BBFKgwbhrWuethLQ88494FEpDg9RqJo3zCLoLVJ31kOdIUTcUqBF4xQW+8Zicf/wmWb0tqcrWYoZMwWegVGSpsg3eO99mVk07LkXbJ02XJk3YpqPN046JsJ9AUQTanYEn8uIubMaWeHcb9sb+raWiGnA+i1G+dVf9Jt7bgRQ7NSORCpiK1weT9vVit4arPQi7hIIDcBLNv4e09OmHSHF5aqSEHH+lbBVhhJ8CEZ/9zxY3mP4X1KLY9lmaignuXvRAgMBAAECggEAE/hKuGWsr1IHZEFHz8KeP8uYnHS84UuRN+xgUfRHTuafJew0N7TpHOrkh2uonidwmYW8aqV0CQGysd+GwT6AQcQ03Bpn/G0hjCu6PQ+HXdv84XtvK9i/tiKJShyEOWF9FlWhqF0rYfCfD1QMNmFXLG6EPhHNzK9/EnYW8f6y1gujtxLqyJmwITWfrfsFWkwWzEANJ09DD12rHjyhCPDrEY8GFTPFBUX3cbmeNWiQxV+vLCs/7V6sU2hb0AI8FdHQM0yC91GLvWWpLFP4rgdd6sk1rtJKYv05CbKDGPFtJzbTUp5Fa+j5YMvgw2aJfj6cGfRqmOXx9nLu5ddgJFp3gQKBgQC85rql0xuLT1qPGnDGuPVGXZXtl7keuU9+NRcLk1R6avoKLhdYZxEBQwBOkdh7y49Jj5a0oKM0p0+mP7NOqBAiMLDoZu4KRqqaTsfUjFBisAsm3RXnAJL2hKkReF08hEdw6xvflAaZriPOZ6ABiqO5viETOhejKGJvMeaeHIJSbQKBgQC7F/afVh4U5MR50sjDjSKYZ4x5yLXbuCA4E9uM0bGihwMXb/i1MLg5LnhofP0e+uub4E98TjwCh0MdnyhYHZO7FbmK5br4ADjYevUFnQz2D/dt3g+Uc0QAgJRFAHnztpnWBYj8bg8Nv6dayGHfSxa9yE8D6GRXIFW9W3JPnS8QdQKBgBZ6wqVvLOz0IYu8EsWjRxT7mBbxIkeEt/AF81seSkRTLLQ/benIQh7LUWMIOiHpRID1hHK0Vxex/WmEVRs8Jsda0gKmFRfF9xFr5J3noL1Nw9X5I10dFWAew1vf3gCQoUvPvr5piCuLrJkofcMRM5CsDpWSKTPyX1xqLM6OB/g1AoGABIfRBgy8z762b0jGac3aH5yqTyyoErkDtrfsYh2V0py4W3HvsNuMKn5Qlh8otf5XhD/LTPRMCbeizW3UOUYGFMqy4oUroOPIfJ2Fn2wtKyw5zqRI9kxQQj7a2Ezppxgt43vg1FQJ80hH0i/2BsU3uMLH/w06mM2EpgA35Wul/x0CgYAatVZkES4dGsvOaqmb3pn3580+V/RxREkoiL3ZEEarh9Ua7DJ7//N6bDctwcqMvna75J9b7cfCy/mnWMX+vsvq3V78g/7demPhjqTU8uCVknnhx2lEn0rz4B2jtUIDsaeX26Xg2JjxXr5CmxaIT+BqeBPvTxi84V2jsLcogzIwBw==";

  @Test
  void test() {
    LoginUserVo vo = new LoginUserVo();
    vo.setUserName("admin");
    vo.setPassword("1");
    logger.info("request body decode:{}", JSON.toJSONString(vo));
    String key = Md5Util.getInstance().getMd5(String.valueOf(System.currentTimeMillis()));
    logger.info("key :{}", key);
    String body = Base64.getEncoder()
        .encodeToString(Cipher.encodeSms4(JSON.toJSONString(vo), key.substring(0, 16).getBytes()));
    Assertions.assertNotNull(body);

    logger.info("request body encode:{}", body);
    String sign = RsaUtils.buildRsaEncryptByPublicKey(key, publicKey);
    logger.info("key rsa encode:{}", sign);
    JSONObject result = client.post().uri(PATH + "/login").header("sign", sign)
        .contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(body)).exchange()
        .expectBody(JSONObject.class).returnResult().getResponseBody();
    logger.info("return result encode:{}", result);
    JSONObject r = JSON
        .parseObject(JSON.parseObject(Cipher.decodeSms4toString(Base64.getDecoder().decode(result.getString("result")),
            key.substring(0, 16).getBytes())).getString("result"));
    logger.info("return result decode:{}", r);
  }

}
