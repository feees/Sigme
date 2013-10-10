package br.com.engenhodesoftware.sigme.core.view;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.engenhodesoftware.sigme.core.domain.InvalidTaxCodeException;
import br.com.engenhodesoftware.sigme.core.domain.Spiritist;

/**
 * TODO: document this type.
 *
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@FacesValidator("sigmeTaxCodeValidator")
public class TaxCodeValidator implements Validator {
	/** @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object) */
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		// If no value was supplied, do not validate.
		if (value == null) return;
		
		// Attempts to set the tax code in a blank Spiritist object.
		try {
			Spiritist spiritist = new Spiritist();
			spiritist.setTaxCode(value.toString());
		}
		
		// If the tax code is invalid, a domain exception will be thrown. Add a message to the UI component and throws a validator exception instead.
		catch (InvalidTaxCodeException e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, I18n.getString("facesValidator.sigmeTaxCodeValidator.invalidTaxCode.summary"), I18n.getString("facesValidator.sigmeTaxCodeValidator.invalidTaxCode.detail"));
			context.addMessage(component.getId(), msg);
			throw new ValidatorException(msg);
		}
	}
}
