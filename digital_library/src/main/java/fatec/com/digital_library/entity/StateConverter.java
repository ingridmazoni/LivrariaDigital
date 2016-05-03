package fatec.com.digital_library.entity;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import fatec.com.digital_library.control.Loader;

@FacesConverter("stateConverter")
public class StateConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				Loader loader = (Loader) fc.getExternalContext().getApplicationMap().get("loader");
				for (int i = 0 ; i < loader.getStateList().size() ; i++) {
					if (loader.getStateList().get(i).equals(value)){
						return loader.getStateList().get(i);
					}
				}
				System.out.println("Erro");
				return null;
			} catch (NumberFormatException e) {
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((String) object));
		} else {
			return null;
		}
	}
}
