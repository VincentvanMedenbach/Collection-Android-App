package autiboiz.collectiontestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static autiboiz.collectiontestapp.R.drawable.customborder;

public class MainPage extends AppCompatActivity {
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        ll = findViewById(R.id.gamesContainer);
        retrieveGames();
    }

    public void addPage(View view){
        Intent intent = new Intent(this, Add.class);
        startActivity(intent);
    }

    public void retrieveGames() {
        JSONObject objects = retrieveTestData();

        try {
            JSONArray games = objects.getJSONArray("Games");
            for( int counter = 0; counter < games.length(); counter++ ){
                JSONObject item = games.getJSONObject(counter);
                ll.addView(constructGameComponent(item));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        ll.invalidate();

    }

    public JSONObject retrieveTestData(){
        JSONObject outerObject = new JSONObject();
        JSONArray items = new JSONArray();
        JSONObject gameItem = new JSONObject();
        JSONObject gameItem2 = new JSONObject();
        try {
            gameItem.put("key", "1010210");
            gameItem.put("console", "xbox");
            gameItem.put("gameName", "halo");
            gameItem.put("gameBarCode", "0124124");
            gameItem.put("gameDescription", "Halo is set in the twenty-sixth century");
            gameItem2.put("key", "1412210");
            gameItem2.put("console", "switch");
            gameItem2.put("gameName", "nintendog");
            gameItem2.put("gameBarCode", "1424124124");
            gameItem2.put("gameDescription", "woef");

            items.put(gameItem);
            items.put(gameItem2);
            outerObject.put("Games", items);
        }
        catch(JSONException e) {
            throw new RuntimeException(e);
        }
        return outerObject;
        //TODO ADD DATABASE RETRIEVING
    }

    public LinearLayout constructGameComponent(JSONObject item){

        LinearLayout container = new LinearLayout(this);
        TextView gameName = new TextView(this);
        TextView console = new TextView(this);
        TextView gameDescription = new TextView(this);

        container.setOrientation(LinearLayout.VERTICAL);
        container.setBackgroundResource(customborder);

        try {
            gameName.setText(item.getString("gameName"));
            console.setText(item.getString("console"));
            gameDescription.setText(item.getString("gameDescription"));

            container.addView(gameName);
            container.addView(console);
            container.addView(gameDescription);
        }

        catch (JSONException e) {
            e.printStackTrace();
        }

        return container;

    }

}
