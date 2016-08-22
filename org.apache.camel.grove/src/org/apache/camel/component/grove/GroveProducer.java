package org.apache.camel.component.grove;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;

public class GroveProducer extends DefaultProducer {

	private GroveEndpoint endpoint;

	public GroveProducer(Endpoint endpoint) {
		super(endpoint);
		this.endpoint = (GroveEndpoint) endpoint;
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		GroveUtil.readwritesensor(endpoint, exchange);
	}

	@Override
	public GroveEndpoint getEndpoint() {
		return (GroveEndpoint) super.getEndpoint();
	}
}
