package autiboiz.collectiontestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
    }

    public void addPage(){
        Intent intent = new Intent(this, Add.class);
        startActivity(intent);
    }

}
