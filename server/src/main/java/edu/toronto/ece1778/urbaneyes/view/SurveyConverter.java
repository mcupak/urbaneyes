package edu.toronto.ece1778.urbaneyes.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import edu.toronto.ece1778.urbaneyes.data.SurveyManager;
import edu.toronto.ece1778.urbaneyes.model.Survey;

/**
 * JSF converteer for survey objects so that they can be displayed as strings.
 * 
 * @author mcupak
 * 
 */
@FacesConverter(value = "surveyConverter")
public class SurveyConverter implements Converter {

	@Inject
	private SurveyManager sem;

	public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
		if (string == null)
			return null;
		long id = Long.parseLong(string);
		return sem.getSurvey(id);
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		if (o == null)
			return null;
		Survey m = (Survey) o;
		return Long.toString(m.getId());
	}

}
