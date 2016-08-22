package org.apache.camel.grove;

import java.text.MessageFormat;

import mraa.Platform;
import mraa.mraa;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static {
		System.out.println("Attempting to load libraries from "
				+ System.getProperty("java.library.path"));
		System.loadLibrary("mraajava");
        System.loadLibrary("javaupm_grove");
        System.loadLibrary("javaupm_i2clcd");
        System.out.println("Libraries Loaded");
	}
	
	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		Platform platform = mraa.getPlatformType();
		if (platform != Platform.INTEL_GALILEO_GEN1
				&& platform != Platform.INTEL_GALILEO_GEN2
				&& platform != Platform.INTEL_EDISON_FAB_C) {
			throw new BundleException(MessageFormat.format("Unsupported platform {0}.", platform));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
