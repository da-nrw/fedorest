package de.uzk.hki.fedorest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;


public class Fedora {
	
	private Client client;
	private String fedoraUrl;

	public Fedora(String fedoraUrl, String fedoraUser, String fedoraPassword) {
		
		this.fedoraUrl = fedoraUrl;
		
		client = Client.create();
		client.setFollowRedirects(true);
		client.addFilter(new HTTPBasicAuthFilter(fedoraUser, fedoraPassword));
		
	}
	
	public FedoraRequest ingest() {
		return new FedoraRequest(client,fedoraUrl,"/objects/${pid}",FedoraRequest.HttpMethod.POST);
	}

	public FedoraRequest modifyObject() {
		return new FedoraRequest(client,fedoraUrl,"/objects/${pid}",FedoraRequest.HttpMethod.PUT);
	}

	public FedoraRequest purgeObject() {
		return new FedoraRequest(client,fedoraUrl,"/objects/${pid}",FedoraRequest.HttpMethod.DELETE);
	}

	public FedoraRequest addRelationship() {
		return new FedoraRequest(client,fedoraUrl,"/objects/${pid}/relationships/new",FedoraRequest.HttpMethod.POST);
	}

	public FedoraRequest purgeRelationship() {
		return new FedoraRequest(client,fedoraUrl,"/objects/${pid}/relationships",FedoraRequest.HttpMethod.DELETE);
	}

	public FedoraRequest addDatastream() {
		return new FedoraRequest(client,fedoraUrl,"/objects/${pid}/datastreams/${dsID}",FedoraRequest.HttpMethod.POST);
	}

	public FedoraRequest modifyDatastream() {
		return new FedoraRequest(client,fedoraUrl,"/objects/${pid}/datastreams/${dsID}",FedoraRequest.HttpMethod.PUT);
	}

	public FedoraRequest purgeDatastream() {
		return new FedoraRequest(client,fedoraUrl,"/objects/${pid}/datastreams/${dsID}",FedoraRequest.HttpMethod.DELETE);
	}

}
