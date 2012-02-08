package dev.manda.nu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

public class YakatanaActivity extends Activity {

	public static double[] INGREDIENTS_AMOUNT = { 166.667, 0.333, 1, 0.167, 0.333, 0.333, 0.667, 1, 0.333, 0.333, 0.333, 0.667, 0.333 };
	public static double ROUND = 0.25;

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
		// Set text for TOTAL ingredients
		((TextView) findViewById(R.id.meatText)).setText(getString(R.string.meat, recipe[0] + "g"));
		((TextView) findViewById(R.id.onionText)).setText(getString(R.string.onion, recipe[1] + " lökar"));
		((TextView) findViewById(R.id.garlicText)).setText(getString(R.string.garlic, recipe[2] + " klyftor"));
		((TextView) findViewById(R.id.chiliSauceText)).setText(getString(R.string.chilisauce, recipe[3] + " flaska"));
		((TextView) findViewById(R.id.chiliText)).setText(getString(R.string.chili, recipe[4] + " tsk"));
		((TextView) findViewById(R.id.pepperText)).setText(getString(R.string.pepper, recipe[5] + " tsk"));
		((TextView) findViewById(R.id.saltText)).setText(getString(R.string.salt, recipe[6] + " tsk"));
		((TextView) findViewById(R.id.tabascoText)).setText(getString(R.string.tabasco, recipe[7] + " lite"));
		((TextView) findViewById(R.id.oreganoText)).setText(getString(R.string.oregano, recipe[8] + " tsk"));
		((TextView) findViewById(R.id.waterText)).setText(getString(R.string.water, recipe[9] + " dl"));
		((TextView) findViewById(R.id.sourMilkText)).setText(getString(R.string.sourmilk, recipe[10] + " dl"));
		((TextView) findViewById(R.id.fraicheText)).setText(getString(R.string.fraiche, recipe[11] + " dl"));
		((TextView) findViewById(R.id.philCheseseText)).setText(getString(R.string.philCheese, recipe[12] + " paket"));
		// Set text for SAUCE ingredients
		double sauceGarlic = ROUND * Math.round((recipe[6] / 2) / ROUND);
		double sauceOregano = ROUND * Math.round((recipe[2] / 3) / ROUND);
		((TextView) findViewById(R.id.sauceSourMilkText)).setText(getString(R.string.sourmilk, recipe[10] + " dl"));
		((TextView) findViewById(R.id.sauceFraicheText)).setText(getString(R.string.fraiche, recipe[11] + " dl"));
		((TextView) findViewById(R.id.saucePhilCheeseText)).setText(getString(R.string.philCheese, recipe[12] + " paket\t"));
		((TextView) findViewById(R.id.sauceSaltText)).setText(getString(R.string.salt, sauceOregano + " tsk"));
		((TextView) findViewById(R.id.sauceGarlicText)).setText(getString(R.string.garlic, sauceGarlic + " klyftor"));
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
