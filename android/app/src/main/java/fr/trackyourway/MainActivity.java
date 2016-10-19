package fr.trackyourway;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import fr.trackyourway.runner.RunnerActivity;
import fr.trackyourway.viewer.ViewerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * Runner
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
         * Viewer
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
