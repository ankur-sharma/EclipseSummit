package org.apache.camel.component.grove;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriPath;

public class GroveEndpoint extends DefaultEndpoint {

	@UriPath
	@Metadata(required = "true")
	private String grovePath;

	@UriParam
	private String clientId = "camel-" + System.nanoTime();

	@UriParam(defaultValue = "groveled")
	private String sensor;

	@UriParam
	private int pin;

	public GroveEndpoint(String sensor, String pin) {
		this.sensor = sensor;
		this.pin = Integer.parseInt(pin);
	}
	
	@Override
	protected String createEndpointUri() {
		return "grove:"+sensor+"?pin="+pin;
	}

	@Override
	public Producer createProducer() throws Exception {
		System.out.println("producer");
		return new GroveProducer(this);
	}

	@Override
	public Consumer createConsumer(Processor processor) throws Exception {
		System.out.println("consumer");
		return new GroveConsumer(this, processor);
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public boolean isLenientProperties() {
		return true;
	}

	public String getSensor() {
		return sensor;
	}

	public int getPin() {
		return pin;
	}

}
