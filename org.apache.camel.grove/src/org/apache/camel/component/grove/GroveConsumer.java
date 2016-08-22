package org.apache.camel.component.grove;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;

import upm_grove.GroveButton;
import upm_grove.GroveLed;

public class GroveConsumer extends DefaultConsumer {

	private GroveEndpoint endpoint;
	private Processor processor;

	public GroveConsumer(Endpoint endpoint, Processor processor) {
		super(endpoint, processor);
		this.endpoint = (GroveEndpoint) endpoint;
		this.processor = processor;
	}

	@Override
	protected void doStart() throws Exception {
		super.doStart();
		System.out.println("dostarting");
		Exchange exchange = endpoint.createExchange();
		GroveUtil.readwritesensor(endpoint, exchange);
		getProcessor().process(exchange);
	}

	@Override
	protected void doStop() throws Exception {
		super.doStop();
		System.out.println("dostop");
	}
}
