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
import org.mobicents.protocols.ss7.isup.message.parameter.CircuitIdentificationCode;

/**
 * Start time:14:49:25 2009-09-18<br>
 * Project: mobicents-isup-stack<br>
 * 
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski
 *         </a>
 */
public class CircuitIdentificationCodeImpl extends AbstractParameter implements CircuitIdentificationCode {

	private int cic;
	/**
	 * 	
	 */
	public CircuitIdentificationCodeImpl() {
		
	}

	/* (non-Javadoc)
	 * @see org.mobicents.protocols.ss7.isup.message.parameter.CircuitIdentificationCode#getCIC()
	 */
	public int getCIC() {
		return this.cic;
	}

	/* (non-Javadoc)
	 * @see org.mobicents.protocols.ss7.isup.message.parameter.CircuitIdentificationCode#setCIC(long)
	 */
	public void setCIC(int cic) {
		this.cic = cic & 0x0FFF; //Q.763 1.2

	}

	/* (non-Javadoc)
	 * @see org.mobicents.protocols.ss7.isup.message.parameter.ISUPParameter#getCode()
	 */
	public int getCode() {
		//Its not a real parameter.
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.mobicents.protocols.ss7.isup.ISUPComponent#decodeElement(byte[])
	 */
	public int decodeElement(byte[] b) throws ParameterRangeInvalidException {
		if(b == null || b.length!=2)
		{
			throw new ParameterRangeInvalidException("byte[] must not be null or has size equal to 2.");
		}
		this.cic = (b[0] & 0xFF);
		this.cic |= ((b[1] & 0x0F) << 8) ;
		return b.length;
	}

	/* (non-Javadoc)
	 * @see org.mobicents.protocols.ss7.isup.ISUPComponent#encodeElement()
	 */
	public byte[] encodeElement() throws IOException {
		byte[] b = new byte[2];
		b[0] = (byte) this.cic;
		b[1] = (byte) ((this.cic >> 8) & 0x0F);
		return b;
	}

}
