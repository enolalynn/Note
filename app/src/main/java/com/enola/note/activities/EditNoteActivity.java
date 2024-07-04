package com.enola.note.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.enola.note.AppDatabase;
import com.enola.note.R;
import com.enola.note.dao.NoteDao;
import com.enola.note.databinding.ActivityEditNoteBinding;
import com.enola.note.entities.Note;

import java.util.Date;

public class EditNoteActivity extends AppCompatActivity {
    private ActivityEditNoteBinding binding;
    private Note note;
    private NoteDao noteDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDatabase();
        updateOrCreate();
        initListener();

    }

    private void initDatabase() {
        noteDao = AppDatabase.getInstance(this).getNoteDao();
    }

    private void updateOrCreate() {
        Intent intent = getIntent();
        if(intent != null){
            //update note
            note = (Note) intent.getSerializableExtra("note");
            if(note != null){
                binding.etTitle.setText(note.getTitle());
                binding.etDescription.setText(note.getDescription());
                binding.btSaveNote.setText("Update");
                binding.btDeleteNote.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initListener() {
        binding.btDeleteNote.setOnClickListener(v -> {
            noteDao.deleteNote(note);
            setResult(RESULT_OK);
            finish();
        });
        binding.btSaveNote.setOnClickListener(v -> {
            String title = binding.etTitle.getText().toString();
            String desc = binding.etDescription.getText().toString();
            if (!title.isEmpty() && !desc.isEmpty()) {
                if (note == null) {
                    note = new Note(title, desc, new Date());
                } else {
                    note.setTitle(title);
                    note.setDescription(desc);
                    note.setCreated(new Date());
                }
                Intent intent = new Intent();
                intent.putExtra("note", note);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, "Fill in the blanks ...", Toast.LENGTH_SHORT).show();
            }
        });

    }
}