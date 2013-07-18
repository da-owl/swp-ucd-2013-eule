package com.example.swp_ucd_2013_eule.model;

/**
 * Superclass of all model classes. Necessary for the generic APIModel.
 * @author Erik
 * 
 */
public abstract class Model {

	protected Integer id;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
