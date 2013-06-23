package com.example.swp_ucd_2013_eule.model;


import java.util.List;

/**
 * 
 * @author Erik
 *
 */
public class Forest extends Model {
	
	private Integer user;
	
	private Integer points;
	
	private Integer level;
	
	private List<UserForestItem> useritems;
	
	private List<Forest> friends;
	
	public Forest() {
		
	}
	
	public Forest(Integer id) {
		this.id = id;
	}

	public Forest(Integer id, Integer points, Integer level) {
		this.id = id;
		this.points = points;
		this.level = level;
	}
	
	
	
	public Integer getUser() {
		return user;
	}

	public void setUser(Integer user) {
		this.user = user;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public List<UserForestItem> getUserforestitems() {
		return this.useritems;
	}
	
	public void setUserforestitems(List<UserForestItem> useritems) {
		this.useritems = useritems;
	}

	public boolean addItem(UserForestItem item) {
		// check if their categories match
		if(canAdd(item)){			
			return this.useritems.add(item);
		}
		return false;
	}
	
	public boolean addItem(UserForestItem item, int x, int y) {
		// check if their categories match
		if(canAdd(item)){			
			return this.useritems.add(item);
		}
		return false;
	}
	
	public List<Forest> getFriends() {
		return this.friends;
	}	
	
	public void setFriends(List<Forest> friends) {
		this.friends = friends;
	}

	private boolean canAdd(UserForestItem item){
		// TODO: to be implemented (check geometrics. maybe code is already there ... somewhere ... :)
		return true;		
	}
	
}
