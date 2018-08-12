package com.morrice.ResourceAccount.foundation.comunication.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.morrice.ResourceAccount.foundation.comunication.IRequestService;
import com.morrice.ResourceAccount.foundation.exceptions.ComunicationRestException;
import com.morrice.ResourceAccount.foundation.model.IModel;
import com.morrice.ResourceAccount.foundation.model.TokenResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is used to communicate with others APPS or MicroServices
 * It is implements a Interface Request Service (Contract)
 * And profile Communication-Http used to identify what class will be
 * used to Framework in autowired  
 * @author morrice256
 *
 */
@Service
@Profile({"Communication-Http"})
@Slf4j
public class RequestServiceHttp implements IRequestService{

	private static Logger logger = LoggerFactory.getLogger(RequestServiceHttp.class);
	
	@Value("${endpoint.base.auth}")
	private String baseAuthUrl;
	
	@Value("${endpoint.credential.auth.granttype}")
	private String grantType;
	
	@Value("${endpoint.credential.auth.username}")
	private String username;
	
	@Value("${endpoint.credential.auth.password}")
	private String password;
	
	@Value("${endpoint.credential.auth.clientid}")
	private String clientId;
	
	@Value("${endpoint.partial.auth.oauth2}")
	private String oauthUrl;
	
	@Value("${endpoint.credential.auth.basic}")
	private String basicAuth;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	/**
	 * This method is responsible to send messages another APPS or MicroServices
	 * TODO: Implement method to process returns and gives back object wait in to response.
	 * @param iModel
	 * @param endpoint
	 * @param Generic Object will by send to post
	 * @return
	 * @throws ComunicationRestException 
	 */
	public <T extends IModel> ResponseEntity<T> postMessage(Class<T> iModel, String endpoint, T data) throws ComunicationRestException {
		HttpHeaders httpHeaders = new HttpHeaders();
		
		StringBuilder bearerToken = new StringBuilder("Bearer ");
		bearerToken.append(getToken());
		
		httpHeaders.set("Authorization", bearerToken.toString());
		return this.postMessage(iModel, endpoint, data, httpHeaders);
	} 
	
	/**
	 * This method is responsible to send messages another APPS or MicroServices
	 * Is necessary past token in this method
	 * TODO: Implement method to process returns and gives back object wait in to response.
	 * @param iModel
	 * @param endpoint
	 * @param Generic Object will by send to post
	 * @param String
	 * @return
	 * @throws ComunicationRestException 
	 */
	public <T extends IModel> ResponseEntity<T> postMessage(Class<T> iModel, String endpoint, T data, String token) throws ComunicationRestException {
		HttpHeaders httpHeaders = new HttpHeaders();
		
		StringBuilder bearerToken = new StringBuilder("Bearer ");
		bearerToken.append(token);
		
		httpHeaders.set("Authorization", bearerToken.toString());
		return this.postMessage(iModel, endpoint, data, httpHeaders);
	} 
	
	/**
	 * This method is responsible to effectively communication with another APPS or MicroServices
	 * TODO: Implement method to process returns and gives back object wait in to response.
	 * @param iModel
	 * @param endpoint
	 * @param Generic Object will by send to post
	 * @param httpHeaders 
	 * @return
	 * @throws ComunicationRestException 
	 */
	public <T extends IModel> ResponseEntity<T> postMessage(Class<T> iModel, 
			String endpoint, T data, HttpHeaders httpHeaders) throws ComunicationRestException {
		
		try{
			HttpEntity<T> dataToPost = new HttpEntity<>(data, httpHeaders);
			return this.requestExternal(iModel, endpoint, HttpMethod.POST, dataToPost);
			
		}catch (Exception e) {
			logger.error("Request External Error: ", e);
			throw new ComunicationRestException();
		}		
	} 
	
	private <T extends IModel> ResponseEntity<T> requestExternal(Class<T> iModel, String endpoint, HttpMethod method, HttpEntity<T> data){
		StringBuilder url = new StringBuilder(baseAuthUrl);
		url.append(endpoint);
		
		ResponseEntity<T> response = restTemplate.exchange(url.toString(), method,  data, iModel);
		return response;
	}

	public String getToken() {
		StringBuilder url = new StringBuilder(baseAuthUrl);
		url.append(oauthUrl);
		
		StringBuilder basicAuthToken = new StringBuilder("Basic ");
		basicAuthToken.append(basicAuth);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", basicAuthToken.toString());
		
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();     
		body.add("grant_type", grantType);
		body.add("username", username);
		body.add("password", password);
		body.add("client_id", clientId);
		
		HttpEntity<?> request = new HttpEntity<Object>(body, httpHeaders);
		ResponseEntity<TokenResponse> response = restTemplate.exchange(url.toString(), HttpMethod.POST, request, TokenResponse.class);
		
		return response.getBody().getAccesstoken();
	}	

}
