package org.mobicents.slee.resource.diameter.rf;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.slee.EventTypeID;
import javax.slee.facilities.EventLookupFacility;
import javax.slee.resource.FireableEventType;

import net.java.slee.resource.diameter.rf.events.RfAccountingAnswer;

import org.jdiameter.api.Message;

/**
 * 
 * Caches event IDs for the Diameter Rf RAs.
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class EventIDCache {


  private static final String RF_PACKAGE_PREFIX = "net.java.slee.resource.diameter.rf.events.";

  public static Map<Integer, String> eventNames = new ConcurrentHashMap<Integer, String>();

  static {
    Map<Integer, String> eventsTemp = new HashMap<Integer, String>();
    eventsTemp.put(RfAccountingAnswer.commandCode, RF_PACKAGE_PREFIX + "RfAccounting");
    eventNames = Collections.unmodifiableMap(eventsTemp);
  }


  private static final String VENDOR  = "java.net";
  private static final String VERSION = "0.8";

  private ConcurrentHashMap<String, FireableEventType> eventIds = new ConcurrentHashMap<String, FireableEventType>();

  public EventIDCache() {
  }

  /**
   * 
   * @param eventLookupFacility
   * @param message
   * @return
   */
  public FireableEventType getEventId(EventLookupFacility eventLookupFacility, Message message) {

    FireableEventType eventID = null;

    // Error is always the same.
    if (message.isError()) {
      //eventID = getEventId(eventLookupFacility, ERROR_ANSWER);
      //TODO: x?
    }
    else {
      final int commandCode = message.getCommandCode();
      final boolean isRequest = message.isRequest();

      String eventName = eventNames.get(commandCode);

      if(eventName != null) {
        eventID = getEventId(eventLookupFacility, eventName + (isRequest ? "Request" : "Answer"));
      }
    }

    return eventID;
  }

  /**
   * 
   * @param eventLookupFacility
   * @param eventName
   * @return
   */
  private FireableEventType getEventId(EventLookupFacility eventLookupFacility, String eventName) {

    FireableEventType eventType = eventIds.get(eventName);
    if (eventType == null) {
      try {
        eventType = eventLookupFacility.getFireableEventType(new EventTypeID(eventName,VENDOR,VERSION));
      }
      catch (Throwable e) {
        e.printStackTrace();
      }
      if (eventType != null) {
        eventIds.put(eventName, eventType);
      }
    }
    return eventType;
  }

}