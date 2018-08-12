package com.morrice.ResourceAccount.foundation.authorization;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

import com.morrice.ResourceAccount.foundation.comunication.impl.RequestServiceHttp;

@Service
public class AuthorizationService {

	//TODO: Change object to interface because 
	@Autowired
	private RequestServiceHttp requestServiceHttp;
	
	private String token;
	
	@SuppressWarnings("unchecked")
	public void getToken() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Object details = authentication.getDetails();
		if (details instanceof OAuth2AuthenticationDetails) {
			OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) details;

			Map<String, Object> decodedDetails = (Map<String, Object>) oAuth2AuthenticationDetails.getDecodedDetails();

			System.out.println("My custom claim value: " + decodedDetails.get("MyClaim"));
		}
	}

	/**
	 * This method is necessary to get OauthToken to continue communication between micro services
	 * It is used only to create User, because in End Point to create User, the request don't have
	 * Token yet
	 * @return
	 */
	public String oauthInService() {
		if(token == null) {			
			return requestServiceHttp.getToken();		
		} else {
			return token;	
		}		
	}
	

}
