package org.uma.jmetal.problem.multiobjective.menu;

import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.solutionattribute.impl.NumberOfViolatedConstraints;
import org.uma.jmetal.util.solutionattribute.impl.OverallConstraintViolation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ...
 */
@SuppressWarnings("serial")
public class MultiobjectiveMenu extends AbstractIntegerProblem {

	// PROBLEMAS:
	// 1. Ingesta recomendada recommendedIntakeValues es para todo el plan de menú
	
	public OverallConstraintViolation<IntegerSolution> overallConstraintViolationDegree;
	public NumberOfViolatedConstraints<IntegerSolution> numberOfViolatedConstraints;
	
	public final static int i_max = Integer.MAX_VALUE;
	public final static int i_min = 0;
	
	
	private static int numberOfdishes = 3; // numero de platos (primer plato, segund, postre)
	private static int numberOfDays = 5; // días del menú a generar
	private static int numberOfNutrients = 21;
	private static int numberOfAllergens = 7;
	private static int numberOfIncompatibilities = 5;
	private static int numberOfFoodGroups = 10;

	private static int numberOfVariables = numberOfdishes * numberOfDays;
	private static int numberOfObjectives = 2;
	private static int numberOfConstraints = numberOfNutrients;

	// Vectores de platos(Courses): First courses · Second courses · Desserts 
	private static ArrayList<Course> firstCourses;
	private static ArrayList<Course> secondCourses;
	private static ArrayList<Course> desserts;

	// Ingesta recomendada al dia, grados de penalizaciones, grupos alimenticios
	// existentes, Vector de compatibilidad de platos
	public static ArrayList<Double[]> recommendedIntakeValues;
	public static ArrayList<Double> penalties;
	public static Double[][][] dishesCompatibility;

	// plan alimenticio total: grupos alimenticios, informacion nutricional,
	// cumplimiento de requisitos nutricionales
	// public static ArrayList<Integer> groupAlimPlan = new ArrayList<Integer>();
	//public static ArrayList<Double> infoNutriPlan;

	// public static ArrayList< Pair <Integer,Double>> indFactibilidad = new
	// ArrayList< Pair <Integer,Double>>();
	//public static ArrayList<double[]> indFeasibility;

	/** Constructor */
	public MultiobjectiveMenu(String primerosplatos, String segundosplatos, String postres) throws IOException {

		
		firstCourses = set_VectoresPlatos(primerosplatos);
		secondCourses = set_VectoresPlatos(segundosplatos);
		desserts = set_VectoresPlatos(postres);
		
		recommendedIntakeValues = setRecommendedIntakeValues();
		penalties = setPenaltiesValue();
		dishesCompatibility = setVectorCompatibilidad();

		
		setNumberOfVariables(numberOfVariables);
		setNumberOfObjectives(numberOfObjectives);
		setNumberOfConstraints(numberOfConstraints);
		setName("MenusIntegerProblem");

		List<Integer> lowerLimit = new ArrayList<>(numberOfVariables);
		List<Integer> upperLimit = new ArrayList<>(numberOfVariables);

		for (int i = 0; i < numberOfDays; i++) {
			for (int j = 0; j < numberOfdishes; j++) {
				lowerLimit.add(0);
			}
			upperLimit.add(18);
			upperLimit.add(33);
			upperLimit.add(13);
		}
		setLowerLimit(lowerLimit);
		setUpperLimit(upperLimit);
		
		overallConstraintViolationDegree = new OverallConstraintViolation<IntegerSolution>() ;
	    numberOfViolatedConstraints = new NumberOfViolatedConstraints<IntegerSolution>() ;
	}
	
	/*------------------------------------------*/
	/*---------- INGESTA RECOMENDADA -----------*/
	/*------------------------------------------*/

	public static ArrayList<Double[]> setRecommendedIntakeValues() {
		
		ArrayList<Double[]> recommendedIntakeValues = new ArrayList<Double[]>();
		// Double [][] ingRec = new Double[21][2];
		// range (min and max) values ​​of recommended food intake
		double rangMin = 1.5; // 1.5 - 2
		double rangMax = 2.0; // 2 - 1.7
		
		// Cantidad de nutrientes recomedados (por almuerzo)
		double ingR_acFol = 135.0;	recommendedIntakeValues.add(new Double[] { ingR_acFol, ingR_acFol });	// acido folico
		double ingR_cal = 585.0;	recommendedIntakeValues.add(new Double[] { ingR_cal, ingR_cal }); 		// calcio
		double ingR_en = 1012.0;	recommendedIntakeValues.add(new Double[] { ingR_en, ingR_en }); 		// energia
		double ingR_fos = 562.5;	recommendedIntakeValues.add(new Double[] { ingR_fos, ingR_fos }); 		// fosforo
		double ingR_gra = 31.72;	recommendedIntakeValues.add(new Double[] { ingR_gra, ingR_gra }); 		// grasa
		double ingR_hie = 8.55;		recommendedIntakeValues.add(new Double[] { ingR_hie, ingR_hie }); 		// hierro
		double ingR_mag = 112.5;	recommendedIntakeValues.add(new Double[] { ingR_mag, ingR_mag }); 		// magnesio	
		double ingR_pot = 2025.0;	recommendedIntakeValues.add(new Double[] { ingR_pot, ingR_pot }); 		// potasio
		double ingR_pro = 27.08;	recommendedIntakeValues.add(new Double[] { ingR_pro, ingR_pro }); 		// proteinas
		double ingR_sel = 25.75;	recommendedIntakeValues.add(new Double[] { ingR_sel, ingR_sel }); 		// selenio
		double ingR_sod = 870.0;	recommendedIntakeValues.add(new Double[] { ingR_sod, ingR_sod }); 		// sodio
		double ingR_vA = 450.0;		recommendedIntakeValues.add(new Double[] { ingR_vA, ingR_vA }); 		// vitA
		double ingR_vB1 = 0.41;		recommendedIntakeValues.add(new Double[] { ingR_vB1, ingR_vB1 }); 		// vitB1
		double ingR_vB2 = 0.63;		recommendedIntakeValues.add(new Double[] { ingR_vB2, ingR_vB2 }); 		// vitB2
		double ingR_vB6 = 0.54;		recommendedIntakeValues.add(new Double[] { ingR_vB6, ingR_vB6 }); 		// vitB6
		double ingR_vB12 = 2.28;	recommendedIntakeValues.add(new Double[] { ingR_vB12, ingR_vB12 }); 	// vitB12
		double ingR_vC = 27.0;		recommendedIntakeValues.add(new Double[] { ingR_vC, ingR_vC }); 		// vitC
		double ingR_vD = 4.65;		recommendedIntakeValues.add(new Double[] { ingR_vD, ingR_vD }); 		// vitD
		double ingR_vE = 6.3;		recommendedIntakeValues.add(new Double[] { ingR_vE, ingR_vE }); 		// vitE
		double ingR_yod = 67.5;		recommendedIntakeValues.add(new Double[] { ingR_yod, ingR_yod }); 		// yodo
		double ingR_zin = 6.75;		recommendedIntakeValues.add(new Double[] { ingR_zin, ingR_zin }); 		// zinc

		for (int i = 0; i < recommendedIntakeValues.size(); i++) {
			double first = (recommendedIntakeValues.get(i)[0] - (recommendedIntakeValues.get(i)[0] / rangMin)) * numberOfDays;
			double second = (recommendedIntakeValues.get(i)[1] * rangMax) * numberOfDays;

			recommendedIntakeValues.set(i, new Double[] { first, second });
		}

		return recommendedIntakeValues;
	}
	
	/*-------------------------------------*/
	/*---------- PENALIZACIONES -----------*/
	/*-------------------------------------*/
//	setRepeatedDaysPenalties
//	setFoodTypePenalties
//	setCourseTypePenalties
	public static ArrayList<Double> setPenaltiesValue() {
		
		ArrayList<Double> penalties = new ArrayList<Double>();
		double p_otros = 0.1;		penalties.add(p_otros); 	// otros
		double p_carnes = 3; 		penalties.add(p_carnes); 	// carnes
		double p_cereales = 0.3;	penalties.add(p_cereales); 	// cereales
		double p_frutas = 0.1;		penalties.add(p_frutas); 	// frutas
		double p_lacteos = 0.3;		penalties.add(p_lacteos); 	// lacteos
		double p_legumbres = 0.3;	penalties.add(p_legumbres);	// legumbres
		double p_marisco = 2;		penalties.add(p_marisco); 	// mariscos
		double p_pasta = 1.5;		penalties.add(p_pasta); 	// pastas
		double p_pescado = 0.5;		penalties.add(p_pescado); 	// pescados
		double p_verdura = 0.1;		penalties.add(p_verdura); 	// verduras
		
		double p_1d = 3;			penalties.add(p_1d); 		// 1 dia
		double p_2d = 2.5;			penalties.add(p_2d); 		// 2 dias
		double p_3d = 1.8;			penalties.add(p_3d); 		// 3 dias
		double p_4d = 1;			penalties.add(p_4d); 		// 4 dias
		double p_5d = 0.2;			penalties.add(p_5d); 		// 5 dias
		
		double p_pp = 8;			penalties.add(p_pp); 		// primer platos
		double p_sp = 10;			penalties.add(p_sp); 		// segundo plato
		double p_p = 2;				penalties.add(p_p); 		// postre
		
		return penalties;
	}
	

	/** Evaluate() method */
	@Override
	public void evaluate(IntegerSolution solution) {
		
		double costFitness			= evaluatePrice(solution);
		double RepetitionFitness 	= evaluateRepetition(solution);

		System.out.println("========== Dentro de evaluate =====================");
		System.out.println("solution : "+solution.toString() + "  costFitness : "+costFitness+ "  RepetitionFitness : "+RepetitionFitness);
		System.out.println(solution.toString());

		solution.setObjective(0, costFitness);
		solution.setObjective(1, RepetitionFitness);
		
		this.evaluateConstraints(solution);
	}
	
	
	/** EvaluateConstraints() method */
	public void evaluateConstraints(IntegerSolution solution) {

		// Calcular la cantidad total de nutrientes del individuo
		ArrayList<Double> infoNutriPlan = setInfoNutriPlan(solution);
		double[] constraints = nutrientConstraintViolationDegree(infoNutriPlan);		 
		
		
         double overallConstraintViolation = 0.0;
         int violatedConstraints = 0; 
		 
		 for (int i = 0; i < constraints.length; i++) {
				 overallConstraintViolation += constraints[i];
				 violatedConstraints++;
		 }
		 
		 overallConstraintViolationDegree.setAttribute(solution, overallConstraintViolation);
		 numberOfViolatedConstraints.setAttribute(solution, violatedConstraints);
			
		System.out.println("========== Dentro de evaluateConstraints =====================");
		System.out.println("constraints : "+Arrays.toString(constraints));

	}

	private double evaluatePrice(IntegerSolution solution) {
		double precioTotal = 0;
		for (int i = 0; i < numberOfDays; i++) {
			int x = i * numberOfdishes;
			precioTotal += firstCourses.get(solution.getVariableValue(x)).getPrice()
					+ secondCourses.get(solution.getVariableValue(x + 1)).getPrice()
					+ desserts.get(solution.getVariableValue(x + 2)).getPrice();
		}
		return precioTotal;
	}
	
	private double evaluateRepetition(IntegerSolution solution) {
		double valPP, valSP, valP, valTabla, valGAFirst;
		double valTotal = 0;

		double numPP = penalties.get(15); // p_pp = 8 // primer platos
		double numSP = penalties.get(16); // p_sp = 10 // segundo plato
		double numP = penalties.get(17); // p_p = 2 // postre
		
		// Vector que guarda los grupos alimenticios pertenecientes a los platos elegidos
		ArrayList<Integer> grupsAlimentSolution = new ArrayList<Integer>();
		
		// Vector que guarda los ga pertenecientes al menu de la iteracion anterior
		ArrayList<Integer> grupsAlimentSolutionPrevious = new ArrayList<Integer>();
		
		// ctor que guarda los ga de los últimos 5 días
		ArrayList<ArrayList<Integer>> grupsAlimentSolutionLast5Day = new ArrayList<ArrayList<Integer>>();
		
		int x = 0;
		
		for (int i = 0; i < numberOfDays; i++) {
			x = i * numberOfdishes;
			
			// PRIMER PLATO
			// Numero de dias desde que se repitio el plato seleccionado
			valPP = setValorPP(solution.getVariableValue(x));
			for (int j = 0; j < firstCourses.get(solution.getVariableValue(x)).getFoodGroups().size(); j++) 
			{
				// comprueba si ya habia aparecido en el menu el grupo alimenticio j, si no lo añde al vector gaElegidos
				if (gaElegidosPorIteracion(grupsAlimentSolution,
					firstCourses.get(solution.getVariableValue(x)).getFoodGroups().get(j)))
					grupsAlimentSolution.add(firstCourses.get(solution.getVariableValue(x)).getFoodGroups().get(j));
			}

			// SEGUNDO PLATO
			valSP = setValorSP(solution.getVariableValue(x + 1));
			for (int k = 0; k < secondCourses.get(solution.getVariableValue(x + 1)).getFoodGroups().size(); k++) {
				if (gaElegidosPorIteracion(grupsAlimentSolution,
					secondCourses.get(solution.getVariableValue(x + 1)).getFoodGroups().get(k)))
					grupsAlimentSolution.add(secondCourses.get(solution.getVariableValue(x + 1)).getFoodGroups().get(k));
			}

			// POSTRE
			valP = setValorP(solution.getVariableValue(x + 2));
			for (int l = 0; l < desserts.get(solution.getVariableValue(x + 2)).getFoodGroups().size(); l++) {
				if (gaElegidosPorIteracion(grupsAlimentSolution,
					desserts.get(solution.getVariableValue(x + 2)).getFoodGroups().get(l)))
					grupsAlimentSolution.add(desserts.get(solution.getVariableValue(x + 2)).getFoodGroups().get(l));
			}

			
			valTabla = dishesCompatibility[solution.getVariableValue(x)][solution.getVariableValue(x + 1)][solution.getVariableValue(x + 2)]; 
			
			// Obtener el valor total del numero de dias desde que se repitieron grupos alimenticios
			valGAFirst = set_ValorGAFirstAlternativa(grupsAlimentSolutionLast5Day, grupsAlimentSolution); 
			valTotal += valTabla + (numPP / valPP) + (numSP / valSP) + (numP / valP) + valGAFirst;
			
			// Suma los valores de platos y grupos alimenticios elegidos para el siguiente dia
			sumValorPP(); 
			sumValorSP();
			sumValorP();

			set_ultimos5GA(grupsAlimentSolutionLast5Day, grupsAlimentSolution);

			grupsAlimentSolutionPrevious = new ArrayList<>(grupsAlimentSolution);
			grupsAlimentSolution.clear();

		}
		grupsAlimentSolutionLast5Day.clear();
		grupsAlimentSolutionPrevious.clear();

		return valTotal;
	}



	public static double set_penalizacionVC(ArrayList<Integer> foodGroups, Boolean[] selectedFoodGroups) {
		double resultado = 0.0;
		for (int i = 0; i < foodGroups.size(); i++) {
			if (selectedFoodGroups[foodGroups.get(i)] == true) {
				switch (foodGroups.get(i)) {
				case 0:
					resultado += penalties.get(0);
					break;
				case 1:
					resultado += penalties.get(1);
					break;
				case 2:
					resultado += penalties.get(2);
					break;
				case 3:
					resultado += penalties.get(3);
					break;
				case 4:
					resultado += penalties.get(4);
					break;
				case 5:
					resultado += penalties.get(5);
					break;
				case 6:
					resultado += penalties.get(6);
					break;
				case 7:
					resultado += penalties.get(7);
					break;
				case 8:
					resultado += penalties.get(8);
					break;
				case 9:
					resultado += penalties.get(9);
					break;
				}
			}
		}
		return resultado;
	}

	
	public static Double[][][] setVectorCompatibilidad() {
	
		Double[][][] dishesCompatibility = new Double[firstCourses.size()][secondCourses.size()][desserts.size()];
		Boolean[] SelectedFoodGroups = new Boolean[numberOfFoodGroups];
		
		
		for (int y = 0; y < firstCourses.size(); y++)
			for (int x = 0; x < secondCourses.size(); x++)
				for (int z = 0; z < desserts.size(); z++) {
					
					for (int i = 0; i < firstCourses.get(y).getFoodGroups().size(); i++) {
						SelectedFoodGroups[firstCourses.get(y).getFoodGroups().get(i)] = true;
					}
					dishesCompatibility[y][x][z] = set_penalizacionVC(secondCourses.get(x).getFoodGroups(), SelectedFoodGroups);
					
					for (int i = 0; i < secondCourses.get(x).getFoodGroups().size(); i++) {
						SelectedFoodGroups[secondCourses.get(x).getFoodGroups().get(i)] = true;
					}
					dishesCompatibility[y][x][z] += set_penalizacionVC(desserts.get(z).getFoodGroups(), SelectedFoodGroups);
				}
		return dishesCompatibility;
	}

	public static ArrayList<Course> set_VectoresPlatos(String c_filename) {
	// <name>,<price>,<amount>,<allergens>,<incompatibilities>,<amount of nutrients>,<food groups>, 	
	// Esquema
		// <name> = name of the course.
		// <price> = price of the course.
		// <amount> = amount in grams.
		// <allergens> = <<cereal>, <nuts>, <legumes>, <shellfish>, <fish>, <egg protein>, <dairy protein>>. Each of the above fields takes the value 0 if the course does not include the allergen, and the value 1 if it does.
		// <incompatibilities> = <<caeliac>, <diabetes>, <semi-vegetarianism>, <vegetarianism>, <veganism>>. Each of the aforementioned fields takes the value 0 if the course is not incompatible, and the value 1 if it is.
		// <amount of nutrients> = <<folic acid (µg)>, <calcium> (mg), <energy (kcal)>, <phosphorus (mg)>, <total fat (g)>, <iron (mg)>, <magnesium (mg)>, <potassium (mg)>, <proteins (g)>, <selenium (µg)>, <sodium (mg)>, <vit A (µg)>, <vit B1 (mg)>, <vit B2 (mg)>, <vit B6 (mg)>, <vit B12 (µg)>, <vit C (mg)>, <vit D (µg)>, <vit E (mg)>, <iodine (µg)>, <zinc (mg)>>. Each of the above fields has to be specified as a decimal number indicating the amount of the nutrient in question by considering its corresponding unit of measure.
		// <food groups> = <<others (0)>, <meat (1)>, <cereal (2)>, <fruit (3)>, <dairy (4)>, <legume (5)>, <shellfish (6)>, <pasta (7)>, <fish (8)>, <vegetable (9)>>. Only those food groups that the course in question contains have to be specified through their corresponding integer numbers.
	// Ejemplo
		// <name> = Crema de champinones,
		// <price> = 0.355664,
		// <amount> = 191.59999999999999,
		// <allergens> = [0,0,0,0,0,0,1],
		// <incompatibilities> = [0,1,0,0,1],
		// <amount of nutrients> = [34.608, 43.694, 127.619, 187.271, 9.6956, 1.58817, 23.951, 734.982, 3.39078, 12.9872, 20.569, 56.836, 0.156861, 0.735133, 0.15566, 0.07175, 6.70775, 0.200785, 6.5774, 0.55282, 5.15607,],
		// <food groups> = [1, 5]

		ArrayList<Course> ipVector = new ArrayList<Course>();
		int cnt = 0;
		int i = 0;
		int ini = 0;
		int fin = 0;
		String line;

		try (FileReader in = new FileReader(c_filename); BufferedReader br = new BufferedReader(in);) {
			while ((line = br.readLine()) != null) {
				String[] vectorLine = line.split(",");
				Course ip = new Course();
				ip.setName(vectorLine[0]); // Nombre del plato

				ip.setPrice(Double.parseDouble(vectorLine[1])); // Precio
				ip.setAmount(Double.parseDouble(vectorLine[2])); // Cantidad
				ip.setRepeatedDaysAgo(i_max); // Dias desde que se repitio

				ini = 3;
				fin = ini + numberOfAllergens;
				for (i = ini; i < fin; i++) {
					ip.getAllergens().add(vectorLine[i]);
				} // Alergenos
				ini = fin;
				fin = ini + numberOfIncompatibilities;
				for (i = ini; i < fin; i++) {
					ip.getIncompatibilities().add(vectorLine[i]);
				} // Incompatibilidades
				ini = fin;
				fin = ini + numberOfNutrients;
				for (i = ini; i < fin; i++) {
					ip.getNutritionalInfo().add(Double.parseDouble((vectorLine[i])));
				} // Informacion nutricional

				if (vectorLine.length >= fin) {
					ini = fin;
					fin = vectorLine.length;
					for (i = ini; i < fin; i++) {
						ip.getFoodGroups().add(Integer.parseInt(vectorLine[i]));
					} // grupos nutricional
				}

				ipVector.add(cnt, ip);
				cnt++;
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// // System.out.println(e);
		} catch (IOException e) {
			e.printStackTrace();
			// // System.out.println(e);
		}
		return ipVector;
	}

	/*--------------------------------------------------*/
	/*---------- SET INFORMACION NUTRICIONAL -----------*/
	/*--------------------------------------------------*/
	public static ArrayList<Double> setInfoNutriPlan(IntegerSolution solution) {
		
		ArrayList<Double> infoNutriPlan = new ArrayList<Double>();
		
		for (int i = 0; i < numberOfNutrients; i++) {
			infoNutriPlan.add(i, 0.0);
		}
		
		
		int x;
		for (int i = 0; i < numberOfDays; i++) {
			x = i * numberOfdishes;
			for (int j = 0; j < infoNutriPlan.size(); j++) {
				double value = firstCourses.get(solution.getVariableValue(x)).getNutritionalInfo().get(j)
							 + secondCourses.get(solution.getVariableValue(x + 1)).getNutritionalInfo().get(j)
							 + desserts.get(solution.getVariableValue(x + 2)).getNutritionalInfo().get(j);
				double act = infoNutriPlan.get(j) + value;

				infoNutriPlan.set(j, (act));
				
			}

		}
		return infoNutriPlan;
	}

	/*-----------------------------------------------------------*/
	/*---------- COMPROBAR FACTIBILIDAD DEL INDIVIDUO -----------*/
	/*-----------------------------------------------------------*/
	public static double[] nutrientConstraintViolationDegree(ArrayList<Double> infoNutriPlan) {
		
		double[] ConstraintsViolationDegree = new double[numberOfConstraints];
		
		for( int i = 0; i < infoNutriPlan.size(); i++) {
			if((infoNutriPlan.get(i) >= recommendedIntakeValues.get(i)[0]) && (infoNutriPlan.get(i) <= recommendedIntakeValues.get(i)[1])) {
				ConstraintsViolationDegree[i] = 0;
			}
			else if(infoNutriPlan.get(i) < recommendedIntakeValues.get(i)[0]) {
				ConstraintsViolationDegree[i] = recommendedIntakeValues.get(i)[0] - infoNutriPlan.get(i);
			}
			else if(infoNutriPlan.get(i) > recommendedIntakeValues.get(i)[1]) {
				ConstraintsViolationDegree[i] = infoNutriPlan.get(i) - recommendedIntakeValues.get(i)[1];
			}
		}
		
		return ConstraintsViolationDegree;
	}

	/*----------------------------------------------------------------------*/
	/*---------- METODOS PARA EL CALCULO DEL GRADO DE REPETICION -----------*/
	/*----------------------------------------------------------------------*/

	public static boolean gaElegidosPorIteracion(ArrayList<Integer> vec, int valor) {
		boolean resultado = true;
		for (int i = 0; i < vec.size(); i++) {
			if (vec.get(i) == valor) {
				resultado = false;
			}
		}
		return resultado;
	}

	public static int setValorPP(int id) {
		int valor = i_max; // Tengo que retornar el numero de dias desde que se eligio el plato por ultima
							// vez
		if (firstCourses.get(id).getRepeatedDaysAgo() != i_max) // Si el numero de dias es i_max, significa que nunca se ha
															// elegido, por lo que retorno 0
		{
			valor = firstCourses.get(id).getRepeatedDaysAgo(); // Si el numero de dias es distinto a i_max, retorno el valor
															// y reseteo el numero de dias a 0
		}
		firstCourses.get(id).setRepeatedDaysAgo(0);

		return valor;
	}

	public static int setValorSP(int id) {
		int valor = i_max;
		if (secondCourses.get(id).getRepeatedDaysAgo() != i_max) {
			valor = secondCourses.get(id).getRepeatedDaysAgo();
		}
		secondCourses.get(id).setRepeatedDaysAgo(0);

		return valor;
	}

	public static int setValorP(int id) {
		int valor = i_max;
		if (desserts.get(id).getRepeatedDaysAgo() != i_max) {
			valor = desserts.get(id).getRepeatedDaysAgo();
		}
		desserts.get(id).setRepeatedDaysAgo(0);

		return valor;
	}

	public static void sumValorPP() {
		for (int i = 0; i < firstCourses.size(); i++) {
			if (firstCourses.get(i).getRepeatedDaysAgo() != i_max) {
				firstCourses.get(i).setRepeatedDaysAgo(firstCourses.get(i).getRepeatedDaysAgo() + 1);
			}
		}
	}

	public static void sumValorSP() {
		for (int i = 0; i < secondCourses.size(); i++) {
			if (secondCourses.get(i).getRepeatedDaysAgo() != i_max) {
				secondCourses.get(i).setRepeatedDaysAgo(secondCourses.get(i).getRepeatedDaysAgo() + 1);
			}
		}
	}

	public static void sumValorP() {
		for (int i = 0; i < desserts.size(); i++) {
			if (desserts.get(i).getRepeatedDaysAgo() != i_max) {
				desserts.get(i).setRepeatedDaysAgo(desserts.get(i).getRepeatedDaysAgo() + 1);
			}
		}
	}

//	public static void sumValorGA() {
//		
//		for (int i = 0; i < foodGroups.length; i++) {
//			if (foodGroups[i][0] != i_max) {
//				foodGroups[i][0]++;
//			}
//			foodGroups[i][1] = i_max;
//		}
//	}

	// METODO PARA ANIADIR LOS GA DE UN DIA EN EL Vector DE GAS DE LOS ULTIMOS 5
	// DIAS
	public static void set_ultimos5GA(ArrayList<ArrayList<Integer>> ultimos5GA, ArrayList<Integer> vec) {
		if (ultimos5GA.size() < 5) {
			ultimos5GA.add(vec);
		} else {

			ultimos5GA.remove(ultimos5GA.get(0));
			ultimos5GA.add(vec);
		}
	}

	public static double set_ValorGAFirstAlternativa(ArrayList<ArrayList<Integer>> ultimos5GA, ArrayList<Integer> vec) {
		/*
		 * 0 Otros, 1 Carne, 2 Cereal, 3 Fruta, 4 Lacteo, 5 Legumbre, 6 Marisco, 7
		 * Pasta, 8 Pescado, 9 Verdura
		 */
		double[] penalizacionPorGA = { penalties.get(0), penalties.get(1), penalties.get(2),
				penalties.get(3), penalties.get(4), penalties.get(5), penalties.get(6),
				penalties.get(7), penalties.get(8), penalties.get(9) };
		double[] penalizacionPorDias = { penalties.get(10), penalties.get(11), penalties.get(12),
				penalties.get(13), penalties.get(14) };
		boolean[] pen = { false, false, false, false, false };
		double resultado = 0;

		if (ultimos5GA.size() > 0) {
			for (int i = 0; i < vec.size(); i++) {
				for (int j = 0; j < ultimos5GA.size(); j++) {
					for (int k = 0; k < ultimos5GA.get(j).size(); k++) {
						if (vec.get(i) == ultimos5GA.get(j).get(k)) {
							pen[j] = true;
							resultado += penalizacionPorGA[vec.get(i)];
						}
					}
				}
			}
			for (int x = 0; x < 5; x++) {
				if (pen[x]) {
					resultado += penalizacionPorDias[x];
					pen[x] = false;
				}
			}
		}

		return resultado;
	}



}
