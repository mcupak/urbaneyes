package edu.toronto.ece1778.urbaneyes.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Location in space. Contains latitude, longitude and altitude.
 * 
 * @author mcupak
 * 
 */
@Entity
@XmlRootElement
public class Point implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@NotEmpty
	private String name;

	@NotNull
	private Double latitude;

	@NotNull
	private Double longitude;

	@NotNull
	private Float altitude;

	private String address;

	public Point() {
	}

	public Point(String name, Double latitude, Double longitude,
			Float altitude, String address) {
		super();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.address = address;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Float getAltitude() {
		return altitude;
	}

	public void setAltitude(Float altitude) {
		this.altitude = altitude;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Point [id=" + id + ", name=" + name + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", altitude=" + altitude
				+ ", address=" + address + "]";
	}

}