package org.apache.camel.component.grove;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.UriEndpointComponent;

public class GroveComponent extends UriEndpointComponent{

	public GroveComponent() {
		super(GroveEndpoint.class);
	}

	@Override
	protected Endpoint createEndpoint(String uri, String path,
			Map<String, Object> parameters) throws Exception {
		System.out.println(uri);
		System.out.println(path);
		System.out.println(parameters);
		GroveEndpoint endpoint = new GroveEndpoint(path, (String) parameters.get("pin") );
		System.out.println(endpoint);
		return endpoint;
	}

}
