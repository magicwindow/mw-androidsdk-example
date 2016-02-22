/**
 * 
 */
package com.magicwindow.deeplink.utils;

import cn.salesuite.saf.eventbus.EventBus;

/**
 * @author Tony Shen
 *
 */
public final class EventBusManager {
	
	private static final EventBus BUS = new EventBus();
	
	public static EventBus getInstance() {
		return BUS;
	}
	
	private EventBusManager() {
	}
}
