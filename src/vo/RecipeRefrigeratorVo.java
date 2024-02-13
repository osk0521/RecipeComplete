package vo;

import java.util.ArrayList;

public class RecipeRefrigeratorVo {
	private int recipeID;
	private String thumbnail;
	private String title;
	private ArrayList<String> haveIngrediNameList;
	private ArrayList<String> notHaveIngrediNameList;
	private ArrayList<Integer> haveIngrediIDlist;
	private ArrayList<Integer> notHaveIngrediIDlist;
	
	public RecipeRefrigeratorVo(int recipeID, String thumbnail, String title,  ArrayList<String> haveIngredeNameList, ArrayList<Integer> haveIngrediIDlist, ArrayList<String> notHaveIngrediNameList, ArrayList<Integer> notHaveIngrediIDlist) {
		this.recipeID = recipeID;
		this.thumbnail = thumbnail;
		this.title = title;
		this.haveIngrediNameList = haveIngredeNameList;
		this.notHaveIngrediNameList = notHaveIngrediNameList;
		this.haveIngrediIDlist = haveIngrediIDlist;
		this.notHaveIngrediIDlist = notHaveIngrediIDlist;
	}
	public int getRecipeID() {
		return recipeID;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public String getTitle() {
		return title;
	}

	public ArrayList<String> getHaveIngredeNameList() {
		return haveIngrediNameList;
	}
	public ArrayList<String> getNothaveIngredeNameList() {
		return notHaveIngrediNameList;
	}
	public ArrayList<Integer> getHaveIngrediIDlist() {
		return haveIngrediIDlist;
	}
	public ArrayList<Integer> getNotHaveIngrediIDlist() {
		return notHaveIngrediIDlist;
	}
}
