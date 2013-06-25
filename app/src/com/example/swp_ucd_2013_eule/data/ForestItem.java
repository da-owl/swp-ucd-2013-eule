package com.example.swp_ucd_2013_eule.data;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.swp_ucd_2013_eule.R;
import com.example.swp_ucd_2013_eule.model.MyForest;
import com.example.swp_ucd_2013_eule.model.UserForestItem;

public class ForestItem {

	private String mName;
	private String mDescription;
	private int mPrice;
	private ForestItemType mType;
	private int mAmount;
	private int mLevel;
	private boolean mMoveable = false;
	private int mImageId;

	private static ForestItem[] examples;

	public enum ForestItemType {
		STANDARD, SPECIAL
	};

	public static ForestItem[] getExamples() {
		if (examples != null) {
			return examples;
		}

		int imgTree = R.drawable.item_tree;
		int imgFir = R.drawable.item_fir;
		int imgFrog = R.drawable.item_gordan;
		int imgBush = R.drawable.item_bush;
		int imgPlants = R.drawable.item_plants;
		int imgAnimals = R.drawable.item_animals;
		int imgClothes = R.drawable.item_clothes;

		ForestItem fir = new ForestItem(
				ForestItemType.STANDARD,
				imgFir,
				"Fichte",
				"Dies ist eine Fichte.\nZum Erlangen\nmuss man mindestens\neine Waldgröße von 85 m² erreicht haben.");
		fir.setPrice(100);
		fir.setLevel(17);

		ForestItem tree = new ForestItem(ForestItemType.STANDARD, imgTree,
				"Laubbaum",
				"Dies ist ein Laubbaum.\nEs ist ein Startgegenstand.");
		tree.setPrice(15);
		tree.setLevel(1);

		ForestItem bush = new ForestItem(ForestItemType.STANDARD, imgBush,
				"Busch", "Dies ist ein Busch.\nEr ist ein Startgegenstand.");
		bush.setPrice(95);
		bush.setLevel(1);

		ForestItem gordon = new ForestItem(
				ForestItemType.SPECIAL,
				imgFrog,
				"Gordon der Frosch",
				"SPEZIALGEGENSTAND!\nDieser Gegenstand\nist nicht zu kaufen!\nMan erlangt ihn fuer\nherrausragendes Fahren!",
				true);

		ForestItem flower = new ForestItem(
				ForestItemType.STANDARD,
				imgPlants,
				"Blume",
				"Dies ist eine Tuple.\nZum Erlangen\nmuss man mindestens\neine Waldgröße von 130 m² erreicht haben.");
		flower.setPrice(20);
		flower.setLevel(26);

		ForestItem bird = new ForestItem(
				ForestItemType.STANDARD,
				imgAnimals,
				"Vogel",
				"Dies ist ein Vogel.\nZum Erlangen\nmuss man mindestens\neine Waldgröße von 130 m² erreicht haben.");
		bird.setPrice(20);
		bird.setLevel(26);

		ForestItem dress = new ForestItem(
				ForestItemType.STANDARD,
				imgClothes,
				"Kleid",
				"Dies ist ein Kleid.\nZum Erlangen\nmuss man mindestens\neine Waldgröße von 130 m² erreicht haben.");
		dress.setPrice(35);
		dress.setLevel(26);

		examples = new ForestItem[] { fir, tree, bush, gordon, flower, bird,
				dress };

		// call once, to update amounts
		UserForestItem.getExamples();

		return examples;
	}

	public ForestItem(ForestItemType type, int imageID, String name,
			String description) {
		mType = type;
		mImageId = imageID;
		setName(name);
		setDescription(description);
	}

	public ForestItem(ForestItemType type, int imageID, String name,
			String description, boolean moveable) {
		mType = type;
		mImageId = imageID;
		setName(name);
		setDescription(description);
		mMoveable = moveable;
	}

	public Bitmap getImage(Context ctx) {
		Resources r = ctx.getResources();
		return BitmapFactory.decodeResource(r, mImageId);
	}

	public void setImage(int imageID) {
		mImageId = imageID;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		this.mDescription = description;
	}

	public int getPrice() {
		return mPrice;
	}

	public void setPrice(int price) {
		this.mPrice = price;
	}

	public boolean isStandardItem() {
		return mType == ForestItemType.STANDARD;
	}

	public boolean isSpecialItem() {
		return mType == ForestItemType.SPECIAL;
	}

	public int getAmount() {
		return mAmount;
	}

	public void setAmount(int amount) {
		this.mAmount = amount;
	}

	public void incAmount() {
		this.mAmount++;
	}

	public int getLevel() {
		return mLevel;
	}

	public void setLevel(int level) {
		this.mLevel = level;
	}

	public boolean isMoveable() {
		return mMoveable;
	}

}
