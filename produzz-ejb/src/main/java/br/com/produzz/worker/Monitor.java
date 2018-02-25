package br.com.produzz.worker;

import org.apache.log4j.Logger;

import br.com.produzz.entity.Notificacao;

public class Monitor {
	private static final Logger LOGGER = Logger.getLogger(Monitor.class);

	private MailWorker worker;

	public void start() {
		try {
			setWorker(MailWorker.getInstance());

			if (!getWorker().isAlive()) {
				getWorker().start();
			}

		} catch (final Exception e) {
			LOGGER.error(e);
		}
	}

	public void put(Notificacao event) {
		try {
			getWorker().putList(event);

		} catch (final Exception e) {
			LOGGER.error(e);
		}
	}

	public void stop() {
		try {
			setWorker(MailWorker.getInstance());

			if (getWorker().isAlive()) {
				getWorker().terminate();
				getWorker().interrupt();
			}

		} catch (final Exception e) {
			LOGGER.error(e);
		}
	}

	public void execute() {
		try {
			setWorker(MailWorker.getInstance());		

			if (getWorker().isAlive()) {
				getWorker().terminate();
				getWorker().interrupt();
				setWorker(MailWorker.getInstance());			
				getWorker().start();
			}

		} catch (final Exception e) {
			LOGGER.error(e);
		}
	}

	public String getStatus() {
		String result;
		if (getWorker() != null) {
			result = getWorker().getStatus();

		} else {
			result = MailWorker.IDLE;
		}

		return result;
	}

	public MailWorker getWorker() {
		return worker;
	}

	public void setWorker(final MailWorker worker) {
		this.worker = worker;
	}
}
