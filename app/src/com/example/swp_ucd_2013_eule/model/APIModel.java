package com.example.swp_ucd_2013_eule.model;

import java.io.IOException;
import java.util.List;

import android.content.Context;

import com.example.swp_ucd_2013_eule.net.APIException;
import com.example.swp_ucd_2013_eule.net.ApiClient;
import com.example.swp_ucd_2013_eule.net.HttpJsonClient.Response;


public class APIModel<T extends Model, P extends Model> {
	
	private Class<T> type;
	
	private ApiClient api;
	
	private Context ctx;
	
	public APIModel(Class<T> type, Context ctx) {
		this.type = type;
		this.api = ApiClient.getInstance();
		this.api.setServer("10.0.2.2:8080");
		this.api.setAuthToken("c515f179da3f768d6802709fbd98aa5c8e60d9a1");
		this.ctx = ctx;
	}
	
	/**
	 * get single model
	 * GET http://server/[model]/[id]/
	 */
	public T get(T model) throws APIException {
		try {
			Serializer<T> serializer = new Serializer<T>();
			Response response = api.get(ctx, this.buildEndpoint(model, model.getId()));
			return serializer.deserialize(model, response);
		} catch (IOException e) {
			throw new APIException();
		}
	}
	
	/**
	 * get single model
	 * GET http://server/[model]/[id]/
	 */
	public T get(T model, Integer id) throws APIException {
		try {
			Serializer<T> serializer = new Serializer<T>();
			Response response = api.get(ctx, this.buildEndpoint(model, id));
			return serializer.deserialize(model, response);
		} catch (IOException e) {
			throw new APIException();
		}
	}	
	
	/**
	 * save single model
	 * PUT http://server/[model]/[id]/
	 */
	public T save(T model) throws APIException {
		try {
			Serializer<T> serializer = new Serializer<T>();
			Response response = api.post(ctx, this.buildEndpoint(model), serializer.serialize(model));
			return serializer.deserialize(model, response);
		} catch (Exception e) {
			throw new APIException(e.getMessage());
		}
	}
	
	/**
	 * add model to parent model
	 * PUT http://server/[parent]/[id]/[model]/[id]/
	 */
	public T addToParent(T model, P parent, String relation) throws APIException {
		try {
			Serializer<T> serializer = new Serializer<T>();
			Response response = api.post(ctx, this.buildEndpoint(model, parent, relation), serializer.serialize(model));
			return serializer.deserialize(model, response);
		} catch (Exception e) {
			throw new APIException(e.getMessage());
		}
	}
	
	/**
	 * remove model from parent model
	 * DELETE http://server/[parent]/[id]/[model]/[id]/
	 */
	public T deleteFromParent(T model, T parent) throws APIException {
		return null;
	}
	
	/**
	 * get all
	 * GET http://server/[model]/
	 */
	public List<T> getAll() throws APIException {
		return null;
	}
	
	/**
	 * get by parent model
	 * GET http://server/[parent]/[id]/[model]/
	 */
	public List<T> getAllByParent(P parent, T skeleton, String relation) throws APIException {
		try {
			Serializer<T> serializer = new Serializer<T>();
			Response response = api.get(ctx, this.buildEndpoint(null, parent, relation));
			
			return serializer.deserializeList(skeleton, response, relation);
		} catch (Exception e) {
			throw new APIException(e.getMessage());
		}
	}
	
	private String buildEndpoint(T model){
		return "/" + this.type.getSimpleName().toLowerCase() + "s" + "/" + model.getId() + "/";
	}
	
	private String buildEndpoint(T model, Integer id){
		return "/" + this.type.getSimpleName().toLowerCase() + "s" + "/" + id + "/";
	}
	
	private String buildEndpointForParent(P model, Integer id){
		return "/" + model.getClass().getSimpleName().toLowerCase() + "s" + "/" + id + "/";
	}
	
	private String buildEndpoint(T model, P parent, String relation){
		if(model == null) {
			return this.buildEndpointForParent(parent, parent.getId()) + relation + "/";
		}
		return this.buildEndpointForParent(parent, parent.getId()) + relation + "/" + model.getId() + "/";
	}

}
