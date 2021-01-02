package com.skaveesh.dcb.service;

import com.skaveesh.dcb.annotation.EnableCircuitBreakerScan;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * The type Downstream service.
 */
@Service
public class DownstreamService {

  /**
   * Instantiates a new Res 4 j service.
   */
  public DownstreamService() {
  }

  /**
   * Response from this API call will not have a delay
   *
   * @return the json object
   */
  @EnableCircuitBreakerScan
  public JSONObject callTest() {
    JSONObject result;

    try {

      String serviceUrl = "http://localhost:3000/test";
      RestTemplate restTemplate = new RestTemplate();
      result = restTemplate.getForObject(serviceUrl, JSONObject.class);

    } catch (HttpClientErrorException ex) {
      throw new RuntimeException("external API error");
    } catch (Exception ex) {
      throw new RuntimeException("server error");
    }

    JSONObject payloadJSONObject = new JSONObject();
    payloadJSONObject.putAll(result);
    return payloadJSONObject;
  }

  /**
   * Response from this API call will have over 2 seconds of a delay
   *
   * @return the json object
   */
  @EnableCircuitBreakerScan
  public JSONObject callTestSlow() {
    JSONObject result;

    try {

      String serviceUrl = "http://localhost:3000/testslow";
      RestTemplate restTemplate = new RestTemplate();
      result = restTemplate.getForObject(serviceUrl, JSONObject.class);

    } catch (HttpClientErrorException ex) {
      throw new RuntimeException("external API error");
    } catch (Exception ex) {
      throw new RuntimeException("server error");
    }

    JSONObject payloadJSONObject = new JSONObject();
    payloadJSONObject.putAll(result);
    return payloadJSONObject;
  }

  /**
   * Response from this API call will have an error
   *
   * @return the json object
   */
  @EnableCircuitBreakerScan
  public JSONObject callTestError() {
    JSONObject result;

    try {

      String serviceUrl = "http://localhost:3000/testerror";
      RestTemplate restTemplate = new RestTemplate();
      result = restTemplate.getForObject(serviceUrl, JSONObject.class);

    } catch (HttpClientErrorException ex) {
      throw new RuntimeException("external API error");
    } catch (Exception ex) {
      throw new RuntimeException("server error");
    }

    JSONObject payloadJSONObject = new JSONObject();
    payloadJSONObject.putAll(result);
    return payloadJSONObject;
  }

}
