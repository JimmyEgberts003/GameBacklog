package com.egberts.jimmy.gamebacklog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddGame extends AppCompatActivity {

    public TextInputLayout titleTextInput;
    public TextInputLayout platformTextInput;
    public TextInputLayout notesTextInput;
    public Spinner statusSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        titleTextInput = findViewById(R.id.titleTextInput);
        platformTextInput = findViewById(R.id.platformTextInput);
        notesTextInput = findViewById(R.id.notesTextInput);
        statusSpinner = findViewById(R.id.statusSpinner);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(1).setChecked(true);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleTextInput.getEditText().getText().toString();
                String platform = platformTextInput.getEditText().getText().toString();

                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(platform)) {
                    String notes = notesTextInput.toString();

                    Game game = new Game(title, platform, notes, statusSpinner.getSelectedItem().toString(), SimpleDateFormat.getDateInstance().format(new Date()));
                    Intent intent = new Intent();
                    intent.putExtra("INSERT_GAME", game);
                    setResult(RESULT_OK, intent);
                    finish();

                } else if (TextUtils.isEmpty(title) && TextUtils.isEmpty(platform)) {
                    titleTextInput.setError("Please enter a title.");
                    platformTextInput.setError("Please enter a platform.");
                } else if (TextUtils.isEmpty(platform) && !TextUtils.isEmpty(title)) {
                    platformTextInput.setError("Please enter a platform.");
                    titleTextInput.setError(null);
                } else {
                    titleTextInput.setError("Please enter a title.");
                    platformTextInput.setError(null);
                }
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.navigation_overview) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            } else {
                return false;
            }
        }
    };
}
