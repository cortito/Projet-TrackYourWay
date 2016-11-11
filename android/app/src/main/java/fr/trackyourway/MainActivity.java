package fr.trackyourway;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

import fr.trackyourway.activity.runner.RunnerActivity;
import fr.trackyourway.activity.viewer.ViewerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the correct flag
        Button buttonLanguage = (Button) findViewById(R.id.language);
        if (Locale.getDefault().toString().contains("fr")) {
            buttonLanguage.setBackgroundResource(R.drawable.en);
        } else if (Locale.getDefault().toString().contains("en")) {
            buttonLanguage.setBackgroundResource(R.drawable.fr);
        }
        /**
         * Switch language
         */

        buttonLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String languageToLoad = Locale.getDefault().toString().contains("fr") ? "en" : "fr";

                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.setLocale(locale);
                getApplicationContext().getResources().updateConfiguration(config, null);

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        /**
         * Runner Activity
         */
        Button buttonRunner = (Button) findViewById(R.id.runner);
        buttonRunner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RunnerActivity.class);
                startActivity(intent);
            }
        });

        /**
         * Viewer Activity
         */
        Button buttonViewer = (Button) findViewById(R.id.viewer);
        buttonViewer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewerActivity.class);
                startActivity(intent);
            }
        });

    }
}
