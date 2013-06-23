package de.fuberlin.swp.ucd.net.client;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import de.fuberlin.swp.ucd.net.client.HttpJsonClient.Response;
import de.fuberlin.swp.ucd.net.client.model.Forest;

public class APIManager<T> {
	
	private ApiClient client;
	
	private Forest forest;
	
	private Context ctx;
	
	private boolean invalid = false;
	
	private Integer id;
	
	public APIManager(Integer id){
		this.id = id;
	}
	
	public Forest get() {
		if(this.invalid) {
			try {
				Response response = client.get(this.ctx, "/forest/" + id);
				// this.forest = this.deserialize(response.getJsonResponse());
				this.invalid = false;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}				
		return this.forest;
	}
	
	public Forest save() {
		if(this.invalid) {
			try {
				// Response response = client.post(this.ctx, "/forest/" + id, this.serialize(this.forest));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}		
		return this.forest;
	}
	
	public void invalidate() {
		this.invalid = true;
	}
	
	
	
	

}
