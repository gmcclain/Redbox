package com.cooksys.backend.finalsss;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cooksys.shared.finalss.*;
/**
 *  Main method that consumes ObjectMessage sent from
 *  JMS Queue 
 * @author George McClain
 *
 */
@Component
@Scope("prototype")
public class Consumer implements Runnable {
	@Autowired
	private TransactionBean transactionBean;
	private Logger log = Logger.getLogger(this.getClass().getName());
	public Consumer(TransactionBean transactionBean) {
		this.transactionBean = transactionBean;
	}

	/**
	 * Reads messages from the JMS queue and checks to see if the reservations
	 * are valid. If they are, it creates a new entry in the rental table for
	 * the items in the reservation. If they aren't, the reservation is ignored.
	 * 
	 * @author George McClain
	 */
	@Override
	public void run() {
		log.info("Starting message consumer: " + this);
		log.debug("Set up the variables to span multiple try/catch blocks.");
		ActiveMQConnectionFactory connectionFactory = null;
		Connection connection = null;
		Session session = null;
		Message message = null;
		Destination destination = null;
		MessageConsumer consumer = null;
		log.debug("Create & start the connection");
		connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		try {
			connection = connectionFactory.createConnection();
			connection.start();
		} catch (JMSException e) {
			log.error("Could not start connection: " + e.getMessage());
			e.printStackTrace();
		}
		try {
			log.debug("Create a Session");
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			log.debug("Create the destination");
			destination = session.createQueue("reservations");
			log.debug("Create a MessageConsumer from the Session to the Queue"); 
			consumer = session.createConsumer(destination);
		} catch (Exception e) {
			log.error("Could not create consumer: " + e.getMessage());
		}
		try {
			while ((message = consumer.receive()) != null) {
				System.out.println("Message received!");
				if (message instanceof ObjectMessage) {
					System.out.println("Message is ObjectMessage!");
					ObjectMessage objectMessage = (ObjectMessage) message;
					Transaction transaction = (Transaction) objectMessage
							.getObject();
					System.out.println("Object cast successful!");
					/*
					 * If the transaction is in the TransactionBean, create the
					 * new rental in the rental table.
					 */
					if (transactionBean.isTransaction(transaction)) {
						System.out.println("The transaction matched!");
						transactionBean.rent(transaction);
					} else {
						System.out.println("The transaction didn't match.");
					}
				} else {
					log.error("The transaction didn't match: " + transactionBean);
				}
			}
		} catch (JMSException e1) {
			log.error("Could not create consumer: " + e1.getMessage());
			e1.printStackTrace();
		}
	}

}