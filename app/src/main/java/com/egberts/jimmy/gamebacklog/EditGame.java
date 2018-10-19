package com.egberts.jimmy.gamebacklog;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditGame extends AppCompatActivity {

    public TextInputLayout titleTextInput;
    public TextInputLayout platformTextInput;
    public TextInputLayout notesTextInput;
    public Spinner statusSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);

        titleTextInput = findViewById(R.id.titleTextInput);
        platformTextInput = findViewById(R.id.platformTextInput);
        notesTextInput = findViewById(R.id.notesTextInput);
        statusSpinner = findViewById(R.id.statusSpinner);

        final Game selectedGame = getIntent().getParcelableExtra("SELECTED_GAME");

        System.out.println(selectedGame.getTitle());
        titleTextInput.getEditText().setText(selectedGame.getTitle());
        notesTextInput.getEditText().setText(selectedGame.getNotes());
        platformTextInput.getEditText().setText(selectedGame.getPlatform());

        statusSpinner.setSelection(((ArrayAdapter<String>) statusSpinner.getAdapter()).getPosition(selectedGame.getStatus()));

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleTextInput.getEditText().getText().toString();
                String platform = platformTextInput.toString();

                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(platform)) {

                    final String date = SimpleDateFormat.getDateInstance().format(new Date());
                    selectedGame.setDate(date);
                    selectedGame.setNotes(notesTextInput.getEditText().getText().toString());
                    selectedGame.setPlatform(platformTextInput.getEditText().getText().toString());
                    selectedGame.setStatus(statusSpinner.getSelectedItem().toString());
                    selectedGame.setTitle(titleTextInput.getEditText().getText().toString());

                    List<Game> gameList = new ArrayList<>();
                    gameList.add(selectedGame);

                    Intent intent = new Intent();
                    intent.putExtra("UPDATE_GAME", selectedGame);
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
}