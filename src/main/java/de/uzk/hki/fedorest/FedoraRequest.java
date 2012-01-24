package de.uzk.hki.fedorest;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class FedoraRequest {
	
	public static enum HttpMethod {
		GET, POST, PUT, DELETE
	}
	
	private Client client;
	private String baseUrl;
	private MultivaluedMap<String, String> params;
	private HttpMethod httpMethod;
	private String method;

	public FedoraRequest(Client client, String baseUrl, String method, HttpMethod httpMethod) {
		this.client = client;
		this.baseUrl = baseUrl;
		this.method = method;
		this.params = new MultivaluedMapImpl();
		this.httpMethod = httpMethod;
	}
	
	public FedoraRequest param(String name, String value) {
		try {
			this.params.putSingle(URLEncoder.encode(name,"UTF-8"), URLEncoder.encode(value,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public String execute() throws FedoraException {
		return execute(null);
	}
	
	public String execute(Object content) throws FedoraException {
		
		String tempMethod = new String(method);
		
		while (tempMethod.contains("$")) {
			String key = tempMethod.substring(tempMethod.indexOf('{')+1, tempMethod.indexOf('}'));
			if(params.containsKey(key)) {
				tempMethod = tempMethod.replaceAll("\\$\\{"+key+"\\}", params.get(key).get(0));
				params.remove(key);
			} else {
				throw new IllegalStateException("lacking obligatory paramter \""+key+"\" for method \""+method+"\"");
			}
		}
		
		String requestUrl = baseUrl + tempMethod;
		WebResource resource = client.resource(requestUrl);
		resource = resource.queryParams(this.params);
		
		String result = null;
		
		try {		
			switch(httpMethod) {
				case GET:
					result = resource.get(String.class);
					break;
				case POST:
					result = resource.post(String.class,content);
					break;
				case PUT:
					result = resource.put(String.class,content);
					break;
				case DELETE:
					result = resource.delete(String.class);
					break;
				default:
					throw new IllegalArgumentException("Method not supported");
			}
		} catch(UniformInterfaceException e) {
			throw new FedoraException("<<<Fedora says:\n" + e.getResponse().getEntity(String.class) + "\n>>>", e);
		}
		
		return result;
		
	}

}
