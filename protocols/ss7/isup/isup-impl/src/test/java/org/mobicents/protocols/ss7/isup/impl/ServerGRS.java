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

/**
 * 
 */
package org.mobicents.protocols.ss7.isup.impl;

import java.io.IOException;

import org.mobicents.protocols.ss7.isup.ISUPClientTransaction;
import org.mobicents.protocols.ss7.isup.ISUPListener;
import org.mobicents.protocols.ss7.isup.ISUPMessageFactory;
import org.mobicents.protocols.ss7.isup.ISUPParameterFactory;
import org.mobicents.protocols.ss7.isup.ISUPProvider;
import org.mobicents.protocols.ss7.isup.ISUPServerTransaction;
import org.mobicents.protocols.ss7.isup.ISUPStack;
import org.mobicents.protocols.ss7.isup.ParameterRangeInvalidException;
import org.mobicents.protocols.ss7.isup.TransactionAlredyExistsException;
import org.mobicents.protocols.ss7.isup.message.CircuitGroupResetAckMessage;
import org.mobicents.protocols.ss7.isup.message.CircuitGroupResetMessage;
import org.mobicents.protocols.ss7.isup.message.ISUPMessage;

/**
 * @author baranowb
 * 
 */
public class ServerGRS implements ISUPListener {

	private ISUPStack isupStack;
	private ISUPProvider provider;
	private ISUPMessageFactory factory;
	private ISUPParameterFactory parameterFactory;

	private boolean _RCV_GRS, _SND_GRA, _RCV_TX_TERM;
	private boolean passed = true;
	private StringBuilder status = new StringBuilder();
	private ISUPServerTransaction stx;

	public ServerGRS(ISUPStack isupStack) {
		super();
		this.isupStack = isupStack;
		this.provider = this.isupStack.getIsupProvider();
		this.factory = this.provider.getMessageFactory();
		this.parameterFactory = this.provider.getParameterFactory();
	}

	public void start() throws IllegalArgumentException, TransactionAlredyExistsException, ParameterRangeInvalidException, IOException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.isup.ISUPListener#onMessage(org.mobicents
	 * .protocols.ss7.isup.message.ISUPMessage)
	 */
	public void onMessage(ISUPMessage message) {
		try {
			switch (message.getMessageType().getCode()) {
			case CircuitGroupResetMessage.MESSAGE_CODE:
				if (_RCV_GRS) {
					passed = false;
					status.append("Received IAM message more than once!\n");
				}

				_RCV_GRS = true;
				this.stx = this.provider.createServerTransaction(message);
				// send ACM

				CircuitGroupResetAckMessage gra = this.factory.createGRA();
				gra.setCircuitIdentificationCode(message.getCircuitIdentificationCode());
				CircuitGroupResetMessage grs = (CircuitGroupResetMessage) message;

				gra.setRangeAndStatus(grs.getRangeAndStatus());

				this.stx.sendAnswer(gra);
				_SND_GRA = true;

				break;

			default:
				passed = false;
				status.append("Received unexpected message, code: " + message.getMessageType().getCode() + "\n");
			}
		} catch (Exception e) {
			passed = false;
			e.printStackTrace();
			status.append("Failed due to exception: ").append(e).append("\n");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.isup.ISUPListener#onTransactionEnded(org.
	 * mobicents.protocols.ss7.isup.ISUPClientTransaction)
	 */
	public void onTransactionEnded(ISUPClientTransaction tx) {
		passed = false;
		status.append("Received CTX TERM !\n");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.isup.ISUPListener#onTransactionEnded(org.
	 * mobicents.protocols.ss7.isup.ISUPServerTransaction)
	 */
	public void onTransactionEnded(ISUPServerTransaction tx) {
		if (_RCV_TX_TERM) {
			passed = false;
			status.append("Received STX TERM more than once!\n");
		}
		_RCV_TX_TERM = true;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.isup.ISUPListener#onTransactionTimeout(org
	 * .mobicents.protocols.ss7.isup.ISUPClientTransaction)
	 */
	public void onTransactionTimeout(ISUPClientTransaction tx) {
		passed = false;
		status.append("Received CTX Timeout !\n");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.isup.ISUPListener#onTransactionTimeout(org
	 * .mobicents.protocols.ss7.isup.ISUPServerTransaction)
	 */
	public void onTransactionTimeout(ISUPServerTransaction tx) {
		passed = false;
		status.append("Received STX Timeout !\n");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.protocols.ss7.isup.ISUPListener#onTransportDown()
	 */
	public void onTransportDown() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.protocols.ss7.isup.ISUPListener#onTransportUp()
	 */
	public void onTransportUp() {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the passed
	 */
	public boolean isPassed() {
		return passed && _SND_GRA && _RCV_GRS && _RCV_TX_TERM;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		status.append("GRS[" + _RCV_GRS + "]").append("\n");
		status.append("GRA[" + _SND_GRA + "]").append("\n");

		status.append("TX TERM[" + _RCV_TX_TERM + "]").append("\n");
		return status.toString();
	}

}
