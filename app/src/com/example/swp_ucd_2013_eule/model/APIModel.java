package com.example.swp_ucd_2013_eule.model;

import java.io.IOException;
import java.util.List;

import android.util.Log;

import com.example.swp_ucd_2013_eule.net.APIException;
import com.example.swp_ucd_2013_eule.net.ApiClient;
import com.example.swp_ucd_2013_eule.net.HttpJsonClient.Response;

/**
 * Provides CRUD operations for all model classes. If instantiated with a specific type it provides type-safe operations.
 * @author erik
 *
 * @param <T>
 * @param <P>
 */
public class APIModel<T extends Model, P extends Model> {

	private Class<T> type;

	private ApiClient api;

	public APIModel(Class<T> type) {
		this.type = type;
		this.api = ApiClient.getInstance();
	}

	/**
	 * Get single model GET http://server/[model]/[id]/
	 * @param model
	 * @return
	 * @throws APIException
	 */
	public T get(T model) throws APIException {
		try {
			Log.d("APIModel get(model) id:", model.getId().toString());
			Serializer<T> serializer = new Serializer<T>();
			Response response = api
					.get(this.buildEndpoint(model, model.getId()));
			return serializer.deserialize(model, response);
		} catch (IOException e) {
			throw new APIException();
		}
	}

	/**
	 * Get single model GET http://server/[model]/[id]/
	 * @param model
	 * @param id
	 * @return
	 * @throws APIException
	 */
	public T get(T model, Integer id) throws APIException {
		try {
			Serializer<T> serializer = new Serializer<T>();
			Response response = api.get(this.buildEndpoint(model, id));
			return serializer.deserialize(model, response);
		} catch (IOException e) {
			throw new APIException();
		}
	}


	/**
	 * Save single model PUT http://server/[model]/[id]/
	 * @param model
	 * @return
	 * @throws APIException
	 */
	public T save(T model) throws APIException {
		try {
			Serializer<T> serializer = new Serializer<T>();

			Response response;
			if (model.getId() == null || model.getId() < 1) {
				response = api.post(this.buildEndpoint(),
						serializer.serialize(model));
			} else {
				response = api.put(this.buildEndpoint(model),
						serializer.serialize(model));
			}

			// Response response = api.post(this.buildEndpoint(model),
			// serializer.serialize(model));
			return serializer.deserialize(model, response);
		} catch (Exception e) {
			throw new APIException(e.getMessage());
		}
	}

	/**
	 * Add model to parent model PUT http://server/[parent]/[id]/[model]/[id]/
	 * @param model
	 * @param parent
	 * @param relation
	 * @return
	 * @throws APIException
	 */
	public T addToParent(T model, P parent, String relation)
			throws APIException {
		try {
			Serializer<T> serializer = new Serializer<T>();
			Response response = api.put(
					this.buildEndpoint(model, parent, relation),
					serializer.serialize(model));
			return serializer.deserialize(model, response);
		} catch (Exception e) {
			throw new APIException(e.getMessage());
		}
	}

	/**
	 * Remove model from parent model DELETE
	 * http://server/[parent]/[id]/[model]/[id]/ 
	 * @param model
	 * @param parent
	 * @return
	 * @throws APIException
	 */
	public T deleteFromParent(T model, T parent) throws APIException {
		return null;
	}

	/**
	 * Get all GET http://server/[model]/
	 * @param model
	 * @return
	 * @throws APIException
	 */
	public List<T> getAll(T model) throws APIException {
		try {
			Serializer<T> serializer = new Serializer<T>();
			Response response = api.get(this.buildEndpoint());
			return serializer.deserializeList(model, response);
		} catch (Exception e) {
			throw new APIException(e.getMessage());
		}
	}

	/**
	 * Get by parent model GET http://server/[parent]/[id]/[model]/
	 * @param parent
	 * @param skeleton
	 * @param relation
	 * @return
	 * @throws APIException
	 */
	public List<T> getAllByParent(P parent, T skeleton, String relation)
			throws APIException {
		try {
			Serializer<T> serializer = new Serializer<T>();
			Response response = api.get(this.buildEndpoint(null, parent,
					relation));

			return serializer.deserializeList(skeleton, response, relation);
		} catch (Exception e) {
			throw new APIException(e.getMessage());
		}
	}
	
	/**
	 * Build endpoint (API-URL) based on class name.
	 * @return
	 */
	private String buildEndpoint() {
		return "/" + this.type.getSimpleName().toLowerCase() + "s" + "/";
	}
	
	/**
	 * Build endpoint (API-URL) based on class name and ID.
	 * @param model
	 * @return
	 */
	private String buildEndpoint(T model) {
		return "/" + this.type.getSimpleName().toLowerCase() + "s" + "/"
				+ model.getId() + "/";
	}
	
	/**
	 * Build endpoint (API-URL) based on class name and a specific ID.
	 * @param model
	 * @return
	 */
	private String buildEndpoint(T model, Integer id) {
		return "/" + this.type.getSimpleName().toLowerCase() + "s" + "/" + id
				+ "/";
	}
	
	/**
	 * Build endpoint (API-URL) based on class name, its parent and a specific ID.
	 * @param model
	 * @param id
	 * @return
	 */
	private String buildEndpointForParent(P model, Integer id) {
		return "/" + model.getClass().getSimpleName().toLowerCase() + "s" + "/"
				+ id + "/";
	}
	
	/**
	 * Build endpoint (API-URL) based on class name, its parent and the relation (collectin name)
	 * @param model
	 * @param parent
	 * @param relation
	 * @return
	 */
	private String buildEndpoint(T model, P parent, String relation) {
		if (model == null) {
			return this.buildEndpointForParent(parent, parent.getId())
					+ relation + "/";
		}
		return this.buildEndpointForParent(parent, parent.getId()) + relation
				+ "/" + model.getId() + "/";
	}

}
