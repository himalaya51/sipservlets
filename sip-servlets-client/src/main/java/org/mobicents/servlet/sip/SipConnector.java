/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.mobicents.servlet.sip;

import java.io.Serializable;

/**
 * Represents a Mobicents Sip Servlets SIP connector and its various attributes
 * 
 * @author jean.deruelle@gmail.com
 * 
 */
public class SipConnector implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * the sip stack signaling transport
	 */
	private String transport;

	/**
	 * the sip stack listening port
	 */
	private int port;
	/*
	 * IP address for this protocol handler.
	 */
	private String ipAddress;
	/*
	 * use Stun
	 */
	private boolean useStun;
	/*
	 * Stun Server Address
	 */
	private String stunServerAddress;
	/*
	 * Stun Server Port
	 */
	private int stunServerPort;

	/*
	 * These settings staticServerAddress, staticServerPort will override all
	 * stun settings and will put the server address in the Via/RR/Contact
	 * headers
	 */
	private String staticServerAddress;
	private int staticServerPort;
	private boolean useStaticAddress;

	// sip stack attributes defined by the server.xml
	private String sipStackPropertiesFileLocation;
	
	
	/**
	 * @return the Transport
	 */
	public String getTransport() {
		return transport;
	}

	/**
	 * @param transport
	 *            the transport to set
	 */
	public void setTransport(String transport) {
		this.transport = transport;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 * @throws Exception
	 */
	public void setPort(int port) {
		this.port = port;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @return the stunServerAddress
	 */
	public String getStunServerAddress() {
		return stunServerAddress;
	}

	/**
	 * @param stunServerAddress
	 *            the stunServerAddress to set
	 */
	public void setStunServerAddress(String stunServerAddress) {
		this.stunServerAddress = stunServerAddress;
	}

	/**
	 * @return the stunServerPort
	 */
	public int getStunServerPort() {
		return stunServerPort;
	}

	/**
	 * @param stunServerPort
	 *            the stunServerPort to set
	 */
	public void setStunServerPort(int stunServerPort) {
		this.stunServerPort = stunServerPort;
	}

	/**
	 * @return the useStun
	 */
	public boolean isUseStun() {
		return useStun;
	}

	/**
	 * @param useStun
	 *            the useStun to set
	 */
	public void setUseStun(boolean useStun) {
		this.useStun = useStun;
	}

	public String getStaticServerAddress() {
		return staticServerAddress;
	}

	public void setStaticServerAddress(String staticServerAddress) {
		this.staticServerAddress = staticServerAddress;
	}

	public int getStaticServerPort() {
		return staticServerPort;
	}

	public void setStaticServerPort(int staticServerPort) {
		this.staticServerPort = staticServerPort;
	}

	public boolean isUseStaticAddress() {
		return useStaticAddress;
	}

	public void setUseStaticAddress(boolean useStaticAddress) {
		this.useStaticAddress = useStaticAddress;
	}

	/**
	 * @param sipStackPropertiesFile the sipStackPropertiesFile to set
	 */
	public void setSipStackPropertiesFileLocation(String sipStackPropertiesFile) {
		this.sipStackPropertiesFileLocation = sipStackPropertiesFile;
	}

	/**
	 * @return the sipStackPropertiesFile
	 */
	public String getSipStackPropertiesFileLocation() {
		return sipStackPropertiesFileLocation;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result + port;
		result = prime * result
				+ ((transport == null) ? 0 : transport.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SipConnector other = (SipConnector) obj;
		if (ipAddress == null) {
			if (other.ipAddress != null) {
				return false;
			}
		} else if (!ipAddress.equals(other.ipAddress)) {
			return false;
		}
		if (port != other.port) {
			return false;
		}
		if (transport == null) {
			if (other.transport != null) {
				return false;
			}
		} else if (!transport.equals(other.transport)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SipConnector [ipAddress=" + ipAddress + ", port=" + port				
				+ ", transport=" + transport + "]";
	}
}