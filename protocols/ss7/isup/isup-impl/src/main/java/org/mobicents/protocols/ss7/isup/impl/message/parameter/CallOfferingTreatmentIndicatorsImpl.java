/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
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

package org.mobicents.protocols.ss7.isup.impl.message.parameter;

import java.io.IOException;

import org.mobicents.protocols.ss7.isup.ParameterRangeInvalidException;
import org.mobicents.protocols.ss7.isup.message.parameter.CallOfferingTreatmentIndicators;

/**
 * Start time:13:33:13 2009-04-05<br>
 * Project: mobicents-isup-stack<br>
 * 
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class CallOfferingTreatmentIndicatorsImpl extends AbstractParameter implements CallOfferingTreatmentIndicators {

	private byte[] callOfferingTreatmentIndicators = null;

	public CallOfferingTreatmentIndicatorsImpl() {
		super();

	}

	public CallOfferingTreatmentIndicatorsImpl(byte[] b) throws ParameterRangeInvalidException {
		super();
		decodeElement(b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.isup.ISUPComponent#decodeElement(byte[])
	 */
	public int decodeElement(byte[] b) throws ParameterRangeInvalidException {
		if (b == null || b.length == 0) {
			throw new ParameterRangeInvalidException("byte[] must not be null and length must be greater than 0");
		}
		setCallOfferingTreatmentIndicators(b);
		return b.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.isup.ISUPComponent#encodeElement()
	 */
	public byte[] encodeElement() throws IOException {

		for (int index = 0; index < this.callOfferingTreatmentIndicators.length; index++) {
			this.callOfferingTreatmentIndicators[index] = (byte) ((this.callOfferingTreatmentIndicators[index] & 0x03) | 0x80);
		}

		this.callOfferingTreatmentIndicators[this.callOfferingTreatmentIndicators.length - 1] = (byte) ((this.callOfferingTreatmentIndicators[this.callOfferingTreatmentIndicators.length - 1]) & 0x7F);
		return this.callOfferingTreatmentIndicators;
	}

	public byte[] getCallOfferingTreatmentIndicators() {
		return callOfferingTreatmentIndicators;
	}

	public void setCallOfferingTreatmentIndicators(byte[] callOfferingTreatmentIndicators) {
		if (callOfferingTreatmentIndicators == null || callOfferingTreatmentIndicators.length == 0) {
			throw new IllegalArgumentException("byte[] must not be null and length must be greater than 0");
		}

		this.callOfferingTreatmentIndicators = callOfferingTreatmentIndicators;
	}

	public int getCallOfferingIndicator(byte b) {
		return b & 0x03;
	}

	public int getCode() {

		return _PARAMETER_CODE;
	}
}