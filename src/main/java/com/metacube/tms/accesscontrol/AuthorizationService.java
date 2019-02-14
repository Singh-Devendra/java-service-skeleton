package com.metacube.tms.accesscontrol;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.metacube.tms.config.ServiceConfig;

@Service
public class AuthorizationService {

  private final ServiceConfig config;

  @Autowired
  public AuthorizationService(ServiceConfig config) {
    this.config = config;
  }

  public JSONObject authorize(Object subjectID, Object actionId, Object resourceID, Object resourceType) {

    JSONObject gsonResponse = new JSONObject();
    String test = "{" + "\"Request\": {" + "\"AccessSubject\": {" + "\"Attribute\": [" + "{"
        + "\"AttributeId\": \"subject-id\"," + "\"Value\": 4" + "}," + "{" + "\"AttributeId\": \"employee_role\","
        + "\"Value\": \"domain_admin\"" + "}" + "]" + "}," + "\"Action\": {" + "\"Attribute\": [" + "{"
        + "\"AttributeId\": \"action-id\"," + "\"Value\": \"read\"" + "}" + "]" + "}," + "\"Resource\": {"
        + "\"Attribute\": [" + "{" + "\"AttributeId\": \"resource-id\"," + "\"Value\": 4" + "}," + "{"
        + "\"AttributeId\": \"http://wso2.org/claims/resourceType\"," + "\"Value\": \"groups\"" + "}" + "]" + "},"
        + "\"Environment\": {" + "\"Attribute\": [" + "{" + "\"AttributeId\": \"application_name\","
        + "\"Value\": 'wrs'" + "}," + "{" + "\"AttributeId\": \"WRS\"," + "\"Value\": \"Metacube\"" + "}" + "]" + "}"
        + "" + "}" + "}";

    try {

      JSONObject o = new JSONObject(test);

      StringEntity params = new StringEntity(o.toString());
      URI uri = new URIBuilder().setScheme(config.getScheme()).setHost(config.getHost()).setPath(config.getPath())
          .build();
      HttpPost httppost = new HttpPost(uri);
      httppost.setHeader("Accept", "application/json");
      httppost.setHeader("Content-type", "application/json");
      httppost.setHeader("Authorization", "Basic YWRtaW46YWRtaW4jMTIz");
      httppost.setEntity(params);
      SSLContextBuilder builder = new SSLContextBuilder();
      builder.loadTrustMaterial(null, (chain, authType) -> true);
      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(), NoopHostnameVerifier.INSTANCE);
      CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
      CloseableHttpResponse res = httpClient.execute(httppost);
      HttpEntity entity = res.getEntity();

      if (entity != null) {
        String stringres = EntityUtils.toString(entity);
        gsonResponse = new JSONObject(stringres);
        gsonResponse.getJSONArray("Response");
      }
    } catch (JSONException | NoSuchAlgorithmException | KeyStoreException | IOException | URISyntaxException | KeyManagementException e) {
      throw new RuntimeException("Error connecting with authorization service", e);
    }

    return gsonResponse;
  }
}