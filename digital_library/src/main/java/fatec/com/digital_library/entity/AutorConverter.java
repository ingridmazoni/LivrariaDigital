package fatec.com.digital_library.entity;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import fatec.com.digital_library.control.Loader;

@FacesConverter("autorConverter")
public class AutorConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				Loader loader = (Loader) fc.getExternalContext().getApplicationMap().get("loader");
				for (int i = 0 ; i < loader.getAutorList().size(); i++) {
					if (loader.getAutorList().get(i).getName().equals(value)) {
						return loader.getAutorList().get(i);
					}
				}
				return null;
			} catch (NumberFormatException e) {
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid autor."));
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((Autor) object).getName());
		} else {
			return null;
		}
	}
}
