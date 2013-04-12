package edu.toronto.ece1778.urbaneyes.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import edu.toronto.ece1778.urbaneyes.data.UserManager;
import edu.toronto.ece1778.urbaneyes.model.User;

/**
 * JSF converter of user objects so that they can be properly displayed.
 * 
 * @author mcupak
 * 
 */
@FacesConverter(value = "userConverter")
public class UserConverter implements Converter {

	@Inject
	private UserManager sem;

	public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
		if (string == null)
			return null;
		long id = Long.parseLong(string);
		return sem.getUser(id);
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		if (o == null)
			return null;
		User m = (User) o;
		return Long.toString(m.getId());
	}

}
