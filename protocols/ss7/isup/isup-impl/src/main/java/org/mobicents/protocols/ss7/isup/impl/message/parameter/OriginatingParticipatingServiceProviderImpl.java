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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.mobicents.protocols.ss7.isup.ParameterRangeInvalidException;
import org.mobicents.protocols.ss7.isup.message.parameter.OriginatingParticipatingServiceProvider;

/**
 * Start time:13:58:48 2009-04-04<br>
 * Project: mobicents-isup-stack<br>
 * 
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski
 *         </a>
 */
public class OriginatingParticipatingServiceProviderImpl extends AbstractNumber implements OriginatingParticipatingServiceProvider{

	
	// FIXME: shoudl we add max octets ?
	private int opspLengthIndicator;

	public OriginatingParticipatingServiceProviderImpl() {

	}

	public OriginatingParticipatingServiceProviderImpl(byte[] representation) throws ParameterRangeInvalidException {
		super(representation);
		
	}

	public OriginatingParticipatingServiceProviderImpl(ByteArrayInputStream bis) throws ParameterRangeInvalidException {
		super(bis);
		
	}

	public OriginatingParticipatingServiceProviderImpl(String address) {
		super(address);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.isup.ISUPComponent#decodeElement(byte[])
	 */
	public int decodeElement(byte[] b) throws ParameterRangeInvalidException {
		return super.decodeElement(b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.isup.ISUPComponent#encodeElement()
	 */
	public byte[] encodeElement() throws IOException {
		return super.encodeElement();
	}

	@Override
	public int decodeHeader(ByteArrayInputStream bis) throws ParameterRangeInvalidException {
		if (bis.available() == 0) {
			throw new ParameterRangeInvalidException("No more data to read.");
		}
		int b = bis.read() & 0xff;

		this.oddFlag = (b & 0x80) >> 7;
		this.opspLengthIndicator = b & 0x0F;
		return 1;
	}

	@Override
	public int encodeHeader(ByteArrayOutputStream bos) {
		int b = 0;
		// Even is 000000000 == 0
		boolean isOdd = this.oddFlag == _FLAG_ODD;
		if (isOdd)
			b |= 0x80;
		b |= this.opspLengthIndicator & 0x0F;
		bos.write(b);
		return 1;
	}

	@Override
	public int decodeBody(ByteArrayInputStream bis) throws ParameterRangeInvalidException {

		return 0;
	}

	@Override
	public int encodeBody(ByteArrayOutputStream bos){

		return 0;
	}

	@Override
	public int decodeDigits(ByteArrayInputStream bis) throws ParameterRangeInvalidException {
		if (this.opspLengthIndicator > 0) {
			if (bis.available() == 0) {
				throw new ParameterRangeInvalidException("No more data to read.");
			}
			return super.decodeDigits(bis, this.opspLengthIndicator);
		} else {
			return 0;
		}
	}

	@Override
	public int encodeDigits(ByteArrayOutputStream bos) {
		if (this.opspLengthIndicator > 0) {
			return super.encodeDigits(bos);
		} else {
			return 0;
		}

	}

	public int getOpspLengthIndicator() {
		return opspLengthIndicator;
	}

	@Override
	public void setAddress(String address) {
		// TODO Auto-generated method stub
		super.setAddress(address);
		int l = super.address.length();
		this.opspLengthIndicator = l / 2 + l % 2;
		if (opspLengthIndicator > 4) {
			throw new IllegalArgumentException("Maximum octets for this parameter in digits part is 4.");
			// FIXME: add check for digit (max 7 ?)
		}
		if (this.opspLengthIndicator == 4 && !isOddFlag()) {
			throw new IllegalArgumentException("maximum allowed number of digits is 7.");
		}
	}

	public int getCode() {

		return _PARAMETER_CODE;
	}
}
