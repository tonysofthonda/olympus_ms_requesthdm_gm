package com.honda.olympus.ms.requesthdm_gm.client;

import com.honda.olympus.ms.requesthdm_gm.domain.MTOCTranslator;
import com.honda.olympus.ms.requesthdm_gm.domain.MtocTranslatorResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.honda.olympus.ms.requesthdm_gm.domain.JsonMTOC;
import com.honda.olympus.ms.requesthdm_gm.property.Properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;


@Slf4j
@Component
public class TranslatorClient {

    private String urlmtoc;
    private RestTemplateFactory rtFactory;

    @Value("mtoc.header.hondaHeaderTypemessageId")
    private String hondaHeaderTypemessageId;

    @Value("mtoc.header.hondaHeaderTypecollectedTimestamp")
    private String hondaHeaderTypecollectedTimestamp;

    @Value("mtoc.header.hondaHeaderTypebusinessId")
    private String hondaHeaderTypebusinessId;

    @Value("mtoc.header.hondaHeaderTypesiteId")
    private String hondaHeaderTypesiteId;


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

    public ResponseEntity<MtocTranslatorResponse> sendRequest(MTOCTranslator mtocTranslator) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.add("hondaHeaderTypemessageId", hondaHeaderTypemessageId);
        headers.add("hondaHeaderTypecollectedTimestamp", hondaHeaderTypecollectedTimestamp);
        headers.add("hondaHeaderTypebusinessId", hondaHeaderTypebusinessId);
        headers.add("hondaHeaderTypesiteId", hondaHeaderTypesiteId);

        HttpEntity<MTOCTranslator> httpEntity = new HttpEntity<>(mtocTranslator, headers);
        return rtFactory.getRestTemplate().exchange(urlmtoc, HttpMethod.GET, httpEntity, MtocTranslatorResponse.class);
    }

}
