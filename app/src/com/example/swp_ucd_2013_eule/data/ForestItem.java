package com.example.swp_ucd_2013_eule.data;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.swp_ucd_2013_eule.R;

public class ForestItem {
	private Bitmap mImage;
	private int mImageWidth;
	private int mImageHeight;
	private String mName;
	private String mDescription;
	private int mPrice;
	private ForestItemType mType;
	private int mAmount;

	private static ForestItem[] examples;

	public enum ForestItemType {
		STANDARD, SPECIAL
	};

	public static ForestItem[] getExamples(Context ctx) {
		if (examples != null) {
			return examples;
		}
		Resources r = ctx.getResources();
		Bitmap imgTree = BitmapFactory.decodeResource(r, R.drawable.item_tree);
		Bitmap imgFir = BitmapFactory.decodeResource(r, R.drawable.item_fir);
		Bitmap imgFrog = BitmapFactory
				.decodeResource(r, R.drawable.item_gordan);
		Bitmap imgBush = BitmapFactory.decodeResource(r, R.drawable.item_bush);
		Bitmap imgPlants = BitmapFactory.decodeResource(r,
				R.drawable.item_plants);
		Bitmap imgAnimals = BitmapFactory.decodeResource(r,
				R.drawable.item_animals);
		Bitmap imgClothes = BitmapFactory.decodeResource(r,
				R.drawable.item_clothes);

		ForestItem fir = new ForestItem(
				ForestItemType.STANDARD,
				imgFir,
				"Fichte",
				"Dies ist eine Fichte.\nZum Erlangen\nmuss man mindestens\ndas Level 5 erreicht haben.");
		fir.setPrice(10);

		ForestItem tree = new ForestItem(ForestItemType.STANDARD, imgTree,
				"Laubbaum",
				"Dies ist ein Laubbaum.\nEs ist ein Startgegenstand.");
		tree.setPrice(15);

		ForestItem bush = new ForestItem(ForestItemType.STANDARD, imgBush,
				"Busch", "Dies ist ein Busch.\nEr ist ein Startgegenstand.");
		bush.setPrice(15);

		ForestItem gordon = new ForestItem(
				ForestItemType.SPECIAL,
				imgFrog,
				"Gordon der Frosch",
				"SPEZIALGEGENSTAND!\nDieser Gegenstand\nist nicht zu kaufen!\nMan erlangt ihn fuer\nherrausragendes Fahren!");

		ForestItem flower = new ForestItem(
				ForestItemType.STANDARD,
				imgPlants,
				"Blume",
				"Dies ist eine Tuple.\nZum Erlangen\nmuss man mindestens\ndas Level 15 erreicht haben.");
		flower.setPrice(20);

		ForestItem bird = new ForestItem(
				ForestItemType.STANDARD,
				imgAnimals,
				"Vogel",
				"Dies ist ein Vogel.\nZum Erlangen\nmuss man mindestens\ndas Level 15 erreicht haben.");
		bird.setPrice(20);

		ForestItem dress = new ForestItem(
				ForestItemType.STANDARD,
				imgClothes,
				"Kleid",
				"Dies ist ein Kleid.\nZum Erlangen\nmuss man mindestens\ndas Level 15 erreicht haben.");
		dress.setPrice(35);

		examples = new ForestItem[] { fir, tree, bush, gordon, flower, bird,
				dress };

		// call once, to update amounts
		UserForestItem.getExamples(ctx);

		return examples;
	}

	public ForestItem(ForestItemType type, Bitmap bitmap, String name,
			String description) {
		mType = type;
		setImage(bitmap);
		setName(name);
		setDescription(description);
	}

	public Bitmap getImage() {
		return mImage;
	}

	public void setImage(Bitmap image) {
		this.mImage = image;
		this.mImageHeight = image.getHeight();
		this.mImageWidth = image.getWidth();
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

	public int getImageHeight() {
		return mImageHeight;
	}

	public int getImageWidth() {
		return mImageWidth;
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

}
