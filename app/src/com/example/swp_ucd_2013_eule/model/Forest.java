package com.example.swp_ucd_2013_eule.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a forest which each users owns.
 * @author erik
 *
 */
public class Forest extends Model {

	private Integer user;

	private Integer points;

	private Integer level;

	private Integer levelProgessPoints;

	private Float pointProgress;

	private List<UserForestItem> userforestitems = new LinkedList<UserForestItem>();

	private List<Forest> friends;

	private List<Statistic> statistics = new LinkedList<Statistic>();

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
		return this.userforestitems;
	}

	public void setUserforestitems(UserForestItem[] useritems) {
		for (UserForestItem item : useritems) {
			this.userforestitems.add(item);
		}
	}

	public void setUserforestitems(List<UserForestItem> useritems) {
		this.userforestitems = new LinkedList<UserForestItem>(useritems);
	}

	public boolean addItem(UserForestItem item) {
		// check if their categories match
		if (canAdd(item)) {
			return this.userforestitems.add(item);
		}
		return false;
	}

	public boolean addItem(UserForestItem item, int x, int y) {
		// check if their categories match
		if (canAdd(item)) {
			return this.userforestitems.add(item);
		}
		return false;
	}

	public List<Forest> getFriends() {
		return this.friends;
	}

	public void setFriends(List<Forest> friends) {
		this.friends = friends;
	}

	public List<Statistic> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<Statistic> statistics) {
		this.statistics = statistics;
	}
	
	/**
	 * Checks if an item can be placed in the forest.
	 * @param item
	 * @return
	 */
	private boolean canAdd(UserForestItem item) {
		// TODO: to be implemented (check geometrics. maybe code is already
		// there ... somewhere ... :)
		return true;
	}

	public Integer getLevelProgessPoints() {
		return levelProgessPoints;
	}

	public void setLevelProgessPoints(Integer levelProgessPoints) {
		this.levelProgessPoints = levelProgessPoints;
	}

	public Float getPointProgress() {
		return pointProgress;
	}

	public void setPointProgress(Float pointProgress) {
		this.pointProgress = pointProgress;
	}

	public void setPointProgress(Double pointProgress) {
		this.pointProgress = new Float(pointProgress.floatValue());
	}

	@Override
	public String toString() {
		return "Forest [id=" + id + "user=" + user + ", points=" + points
				+ ", level=" + level + ", levelProgessPoints="
				+ levelProgessPoints + ", pointProgress=" + pointProgress
				+ ", userforestitems=" + userforestitems + ", friends="
				+ friends + ", statistics=" + statistics + "]";
	}

}
