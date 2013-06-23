package de.fuberlin.swp.ucd.net.client.model;


import java.util.List;

/**
 * 
 * @author Erik
 *
 */
public class Forest extends Model {
	
	private Integer points;
	
	private Integer level;
	
	private List<ForestItem> items;
	
	private List<Forest> friends;
	
	public Forest() {
		
	}

	public Forest(Integer id, Integer points, Integer level) {
		this.id = id;
		this.points = points;
		this.level = level;
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

	public List<ForestItem> getItems() {
		return this.items;
	}
	
	public ForestItem addItem(ForestItem item) {
		// check if their categories match
		if(this.items.add(item)){
			return item;
		}
		return null;
	}
	
	public List<Forest> getFriends() {
		return this.friends;
	}	
	
}
