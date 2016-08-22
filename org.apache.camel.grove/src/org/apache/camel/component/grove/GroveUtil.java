package org.apache.camel.component.grove;

import org.apache.camel.Exchange;

import upm_grove.GroveButton;
import upm_grove.GroveLed;
import upm_grove.GroveLight;
import upm_grove.GroveTemp;
import upm_i2clcd.Jhd1313m1;

public class GroveUtil {

	public static void readwritesensor(GroveEndpoint endpoint, Exchange exchange) {
		System.out.println("readwritesensor");
		switch (endpoint.getSensor()) {
		case "GroveButton":
			GroveButton button = new GroveButton(endpoint.getPin());
			int value = button.value();
			System.out.println("button value" + value);
			exchange.getIn().setBody(value);
			break;
		case "GroveLed":
			System.out.println("led");
			GroveLed led = new GroveLed(endpoint.getPin());
			if (Integer.parseInt(exchange.getIn().getBody().toString()) == 0)
				led.off();
			else
				led.on();
			break;
		case "GroveTemp":
			System.out.println("temp");
			GroveTemp temp = new GroveTemp(endpoint.getPin());
			int temperature = temp.value();
			System.out.println(temperature);
			exchange.getIn().setBody(temperature);
			break;
		case "Jhd1313m1":
			System.out.println("print");
			Jhd1313m1 lcd = new Jhd1313m1(endpoint.getPin());
			lcd.clear();
			lcd.setCursor(0, 0);
			lcd.write(exchange.getIn().getBody().toString());
			break;
		case "GroveLight":
			System.out.println("ldr");
			GroveLight ldr = new GroveLight(endpoint.getPin());
			int light = ldr.value(); 
			System.out.println(light);
			exchange.getIn().setBody(light);
			break;
		}
		
	}
}
