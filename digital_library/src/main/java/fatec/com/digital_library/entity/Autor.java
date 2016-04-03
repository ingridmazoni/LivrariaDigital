package fatec.com.digital_library.entity;

import java.util.Date;

public class Autor {

	private String name;
	private Date birthDate;
	private Date deathDate;
	private String biography;
	private String countryOfBirth;
	private String stateOfBirth;
	private String cityOfBirth;
	private String countryOfDeath;
	private String stateOfDeath;
	private String cityOfDeath;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getCountryOfBirth() {
		return countryOfBirth;
	}

	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}

	public String getStateOfBirth() {
		return stateOfBirth;
	}

	public void setStateOfBirth(String stateOfBirth) {
		this.stateOfBirth = stateOfBirth;
	}

	public String getCityOfBirth() {
		return cityOfBirth;
	}

	public void setCityOfBirth(String cityOfBirth) {
		this.cityOfBirth = cityOfBirth;
	}

	public String getCountryOfDeath() {
		return countryOfDeath;
	}

	public void setCountryOfDeath(String countryOfDeath) {
		this.countryOfDeath = countryOfDeath;
	}

	public String getStateOfDeath() {
		return stateOfDeath;
	}

	public void setStateOfDeath(String stateOfDeath) {
		this.stateOfDeath = stateOfDeath;
	}

	public String getCityOfDeath() {
		return cityOfDeath;
	}

	public void setCityOfDeath(String cityOfDeath) {
		this.cityOfDeath = cityOfDeath;
	}

}
