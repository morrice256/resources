package com.morrice.ResourceAccount.foundation.authorization;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

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
	

}
