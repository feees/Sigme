package br.org.feees.sigme.secretariat.application;

import br.com.engenhodesoftware.util.jms.BackgroundEvent;
import br.org.feees.sigme.secretariat.domain.Mailing;

/**
 * TODO: document this type.
 *
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
public class SendMailingEvent extends BackgroundEvent {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** TODO: document this field. */
	private Mailing mailing;

	/** Constructor. */
	public SendMailingEvent(Mailing mailing) {
		this.mailing = mailing;
	}

	/** Getter for mailing. */
	public Mailing getMailing() {
		return mailing;
	}

	/** Setter for mailing. */
	public void setMailing(Mailing mailing) {
		this.mailing = mailing;
	}
}
