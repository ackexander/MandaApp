package dev.manda.nu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

public class YakatanaActivity extends Activity {

	public static double[] INGREDIENTS_AMOUNT = { 166.667, 0.333, 1, 0.167, 0.333, 0.333, 0.667, 3, 0.333, 0.333, 0.333, 0.667, 0.333 };
	public static double ROUND = 0.5;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yakatana);
	}

	public void generateYakatanaRecipe(View view) {
		TableRow recipeTable = (TableRow) findViewById(R.id.recipeTable);
		recipeTable.setVisibility(View.VISIBLE);
		try {
			displayRecipe();
		} catch (Exception e) {
			recipeTable.setVisibility(View.INVISIBLE);
		}
	}

	private void displayRecipe()
			throws NumberFormatException {
		double[] recipe = generateRecipe();
		((TextView) findViewById(R.id.meatText)).setText(getString(R.string.meat, recipe[0] + "g"));
		((TextView) findViewById(R.id.onionText)).setText(getString(R.string.meat, recipe[1] + " lökar"));
	}

	private double[] generateRecipe()
			throws NumberFormatException {
		double[] recipe = new double[INGREDIENTS_AMOUNT.length];
		EditText persons = (EditText) findViewById(R.id.numberOfPersonsEditText);
		int nrOfPersons = Integer.parseInt(persons.getText().toString());
		for (int i = 0; i < INGREDIENTS_AMOUNT.length; i++) {
			recipe[i] = ROUND * Math.round((nrOfPersons * INGREDIENTS_AMOUNT[i]) / ROUND);
		}
		return recipe;
	}
}
