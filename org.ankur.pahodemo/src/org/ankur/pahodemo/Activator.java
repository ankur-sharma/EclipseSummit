package org.ankur.pahodemo;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.kura.KuraRouter;
import org.apache.camel.component.paho.PahoComponent;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.osgi.framework.BundleContext;

public class Activator extends KuraRouter {

	private static final String MQTT_SERVER = "localhost";
	private static final String BROKER_URL = "tcp://" + MQTT_SERVER + ":1883";
	private static final String CLIENT_ID = "Edison";
	private static final String LDR_TOPIC = "Light";

	private PahoComponent pahoComponent;
	
	private static BundleContext context;

	static BundleContext getBundleContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		Activator.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		super.stop(bundleContext);
		Activator.context = null;
	}
	
	@Override
	protected CamelContext createCamelContext() {
		setContext(super.createCamelContext());
		pahoComponent = new PahoComponent();
		pahoComponent.setBrokerUrl(BROKER_URL);
		pahoComponent.setClientId(CLIENT_ID);
		pahoComponent.setConnectOptions(new MqttConnectOptions());
		pahoComponent.setCamelContext(getContext());

		return getContext();
	}

	@Override
	public void configure() throws Exception {
		System.out.println("creating context and sending message");
		Endpoint ldrEndpoint = pahoComponent.createEndpoint("paho:"	+ LDR_TOPIC);
		
		from("timer:ticktock?delay=5000&period=5000").to("grove:GroveLight?pin=3").filter().method(new MessageFilter(), "filter").process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				System.out.println("processor");
				String message = exchange.getIn().getBody().toString();
				System.out.println(message);
				exchange.getIn().setBody(message.getBytes(), byte[].class);
			}
		}).to("paho:ldr/queue?brokerUrl=tcp://mqttserver:1883");

	}
	
	public static class MessageFilter {

		public MessageFilter() {
			System.out.println("Filter created");
		}

		public boolean filter(Exchange exchange) {
			Object object = exchange.getIn().getBody();
			System.out.println(object);
			int light = Integer.parseInt(object.toString());
			if (light > 20)
				return true;
			return false;
		}
	}

}
