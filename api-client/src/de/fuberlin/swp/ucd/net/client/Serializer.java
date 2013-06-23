package de.fuberlin.swp.ucd.net.client;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import de.fuberlin.swp.ucd.net.client.model.Model;

public class Serializer<T extends Model> {
	
	public T deserialize(T model, JSONObject json) {
		try {
			for (Field field : model.getClass().getFields()) {
				if(field.getType() != List.class) {
					/**
					 * deserialize "simple" fields
					 */
					this.invokeSet(model, field.getName(), json.get(field.getName()));
				}else{
					/**
					 * serialize list fields through calling the api
					 */
				}
			}
		} catch (JSONException e) {
			// TODO: handle exception
		}	
		return model;
	}
	
	public JSONObject serialize(T model) {
		JSONObject json = new JSONObject();
		try {			
			for (Field field : model.getClass().getFields()) {
				if(field.getType() != List.class) {
					/**
					 * serialize "simple" fields
					 */
					Object o = this.invokeGet(model, field.getName());
					json.put(field.getName(), o);
				}else{
					/**
					 * serialize list fields through calling the api
					 */
					
				}
			}
		} catch (JSONException e) {
			// TODO: handle exception
		}
		return json;
	}
	
	private Object invokeGet(T model, String property) {
		try {
			String name = property.substring(0, 1).toUpperCase();
			name += property.substring(1);
			return model.getClass().getMethod("get" + name).invoke(model);
		} catch (NoSuchMethodException e) {
			// TODO: handle exception
		} catch (InvocationTargetException e) {
			// TODO: handle exception
		} catch (IllegalAccessException e) {
			// TODO: handle exception
		}
		return null;
	}
	
	private void invokeSet(T model, String property, Object value) {
		try {
			String name = property.substring(0, 1).toUpperCase();
			name += property.substring(1);
			model.getClass().getMethod("set" + name).invoke(model, value);
		}		
		catch (NoSuchMethodException e) {
			// TODO: handle exception
		} catch (InvocationTargetException e) {
			// TODO: handle exception
		} catch (IllegalAccessException e) {
			// TODO: handle exception
		} 
	}
	
}
