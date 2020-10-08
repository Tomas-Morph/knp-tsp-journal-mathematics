package org.uma.jmetal.problem.multiobjective.menu;

import java.util.ArrayList;

//Struct que almacena informacion de los platos utilizada en el proceso de creacion del plan
public class Course
{
	private String name; //Nombre del plato
	private int repeatedDaysAgo ; //Numero de dias desde que se eligio el plato en el plan por ultima vez
	private double price; //Precio del plato
	private double amount; //Cantidad en gramos
	
	private static ArrayList<Integer> foodGroups = new ArrayList<Integer>(); //Grupos alimenticios correspondientes a los ingredientes principales del plato
	private static ArrayList<Double> nutritionalInfo = new ArrayList<Double>(); //Informacion nutricional del plato
	private static ArrayList<String> allergens = new ArrayList<String>(); //Alergenos del plato
	private static ArrayList<String> incompatibilities = new ArrayList<String>(); //Incompatibilidades alimenticias del plato
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRepeatedDaysAgo() {
		return repeatedDaysAgo;
	}
	public void setRepeatedDaysAgo(int repeatedDaysAgo) {
		this.repeatedDaysAgo = repeatedDaysAgo;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public static ArrayList<Integer> getFoodGroups() {
		return foodGroups;
	}
	public static void setFoodGroups(ArrayList<Integer> foodGroups) {
		Course.foodGroups = foodGroups;
	}
	public static ArrayList<Double> getNutritionalInfo() {
		return nutritionalInfo;
	}
	public static void setNutritionalInfo(ArrayList<Double> nutritionalInfo) {
		Course.nutritionalInfo = nutritionalInfo;
	}
	public static ArrayList<String> getAllergens() {
		return allergens;
	}
	public static void setAllergens(ArrayList<String> allergens) {
		Course.allergens = allergens;
	}
	public static ArrayList<String> getIncompatibilities() {
		return incompatibilities;
	}
	public static void setIncompatibilities(ArrayList<String> incompatibilities) {
		Course.incompatibilities = incompatibilities;
	}
	
	

	
	
	
}