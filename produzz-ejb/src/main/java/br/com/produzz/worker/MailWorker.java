package br.com.produzz.worker;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.produzz.entity.Notificacao;
import br.com.produzz.util.Global;

public class MailWorker extends Thread {
	private static final Logger LOGGER = Logger.getLogger(MailWorker.class);

	public static int SERVER_SLEEP_TIME =  60000;

	public static final String RUNNING = "Executando";
	public static final String SLEEPING = "Aguardando";
	public static final String STOPPING = "Parando";
	public static final String IDLE = "Parado";

	private static String status = IDLE;
	private static MailWorker service = null;
	private volatile boolean running = true;	 

	private final List<Notificacao> list = new ArrayList<Notificacao>();

	public static MailWorker getInstance() throws Exception {
		if (service == null || (!service.isAlive()) || service.isInterrupted()) {
			service = new MailWorker();

		} else if (status.equalsIgnoreCase(MailWorker.STOPPING)) {
			throw new Exception("Monitor esta parando");			
		}

		return service;
	}

	public void putList(final Notificacao event) {
		list.add(event);
	}

	public boolean work() {
		boolean result = false;

		try {
			final int size = list.size();

			if (size > 0) {
				LOGGER.info("MailWorker.working() on " + size + " events");

				while (list.size() > 0) {
					Notificacao event = list.get(0);
					result = true;

					try {
						if (event != null) {
							Global.sendEmail(event.getEmail(), event.getSubject(), event.getContent());
						}

					} catch (final Exception e) {
						LOGGER.error("Falha ao enviar e-mail " + event, e);
					}

					list.remove(event);					
				}
			}

		} catch (final Exception e) {
			LOGGER.error("Falha ao obter info do servidor local", e);
		}

		return result;
	}

	public String getStatus() {
		return status;
	}

	public void terminate() {
		running = false;
		status = STOPPING;
	}

	public void run() {
		LOGGER.info("MailWorker.start()");

		try {
			running = true;

			while (running) {
				status = RUNNING;

				boolean work = work();

				if (!running) break;

				status = SLEEPING;
				if (work) LOGGER.info("MailWorker.sleep()");

				Thread.sleep(SERVER_SLEEP_TIME);
			}

			LOGGER.info("MailWorker.idle()");
			status = IDLE;
			service = null;

		} catch (final InterruptedException e) {
			LOGGER.info("MailWorker.interrupted()");
			status = IDLE;
			service = null;

		} catch (final Exception e) {
			LOGGER.error("Erro ao processar MailWorker", e);
		}
	}	
}
