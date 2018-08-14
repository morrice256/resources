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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.morrice.ResourceAccount.foundation.comunication.IRequestService;
import com.morrice.ResourceAccount.foundation.exceptions.ComunicationRestException;
import com.morrice.ResourceAccount.foundation.model.IModel;

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
		HttpEntity<T> dataToPost = new HttpEntity<>(data);
		return this.requestExternal(iModel, endpoint, HttpMethod.POST, dataToPost);
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
			logger.error("Request External Fail: ", e);
			throw new ComunicationRestException();
		}		
	} 
	
	private <T extends IModel> ResponseEntity<T> requestExternal(Class<T> iModel, String endpoint, HttpMethod method, HttpEntity<T> data) throws ComunicationRestException{
		try{
			StringBuilder url = new StringBuilder(baseAuthUrl);
			url.append(endpoint);
			
			ResponseEntity<T> response = restTemplate.exchange(url.toString(), method,  data, iModel);
			return response;
		} catch (HttpServerErrorException e) {
			logger.error("Request External Fail: ", e);
			throw new ComunicationRestException();
		} catch (Exception e) {
			logger.error("Request External Fail: ", e);
			throw new ComunicationRestException();
		}
	}

}
