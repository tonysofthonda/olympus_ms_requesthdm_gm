package com.honda.olympus.ms.requesthdm_gm.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.honda.olympus.ms.requesthdm_gm.domain.JsonMTOC;
import com.honda.olympus.ms.requesthdm_gm.property.Properties;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class TranslatorClient 
{
	
	private String urlmtoc;
	private RestTemplateFactory rtFactory;
	
	
	@Autowired
	public TranslatorClient(RestTemplateFactory rtFactory, Properties props) {
		this.rtFactory = rtFactory;
		log.debug("# urlmtoc: {}", urlmtoc = props.getUrlmtoc());
	}
	
	
	public ResponseEntity<String> sendRequest(JsonMTOC jsonMTOC) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<JsonMTOC> httpEntity = new HttpEntity<>(jsonMTOC, headers);
		return rtFactory.getRestTemplate().exchange(urlmtoc, HttpMethod.POST, httpEntity, String.class);
	}
	
}
