package org.ankur.sensordemo;

import org.apache.camel.CamelContext;
import org.apache.camel.component.grove.GroveComponent;
import org.apache.camel.component.kura.KuraRouter;
import org.osgi.framework.BundleContext;

public class Activator extends KuraRouter {

	private static BundleContext context;
	private GroveComponent groveComponent;

	static BundleContext getBundleContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		System.out.println("starting iotdemo");
		Activator.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		super.stop(bundleContext);
		Activator.context = null;
	}

	@Override
	protected CamelContext createCamelContext() {
		setContext(super.createCamelContext());
		System.out.println("createcamelcontext");
		groveComponent = new GroveComponent();
		groveComponent.setCamelContext(getContext());
		return getContext();
	}

	@Override
	public void configure() throws Exception {
		System.out.println("configure");
		
		from("timer:ticktock?delay=5000&period=1000").to("grove:GroveButton?pin=4").to("grove:GroveLed?pin=3");
		
		from("timer:ticktock?delay=5000&period=5000").to("grove:GroveTemp?pin=0").to("grove:Jhd1313m1?pin=0");
		
//		from("timer:ticktock?delay=5000&period=1000").to("grove:GroveLight?pin=0").to("grove:Jhd1313m1?pin=0");
	}

}
