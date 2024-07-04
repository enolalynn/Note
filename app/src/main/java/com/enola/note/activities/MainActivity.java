package com.enola.note.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.enola.note.AppDatabase;
import com.enola.note.R;
import com.enola.note.adapters.NoteAdapter;
import com.enola.note.adapters.NoteItemClickListener;
import com.enola.note.dao.NoteDao;
import com.enola.note.databinding.ActivityMainBinding;
import com.enola.note.entities.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteItemClickListener {
    private ActivityMainBinding binding;
    private NoteDao noteDao;
    private NoteAdapter noteAdapter;
    private List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDatabase();
        initListener();

    }

    private void initDatabase() {
        noteDao = AppDatabase.getInstance(this).getNoteDao();
        noteAdapter = new NoteAdapter();
        noteAdapter.setOnNoteItemClickListener(this);
        binding.rvNotes.setAdapter(noteAdapter);
        binding.rvNotes.setLayoutManager(new LinearLayoutManager(this));
        refreshNotes();
    }

    private void refreshNotes() {
        notes = noteDao.getAllNotes();
        noteAdapter.setNotes(notes);
    }

    private void initListener() {
        binding.fabAddNote.setOnClickListener(v->{
            Intent intent = new Intent(this, EditNoteActivity.class);
            startActivityForResult(intent,123);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(resultCode == 123 && data != null){
                //add new note

                Note note;
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    note = data.getSerializableExtra("note",Note.class);
                }else{
                    note = (Note) data.getSerializableExtra("note");
                }
                noteDao.addNote(note);
                refreshNotes();
                Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == 234) {
                //update existing note
                if(data != null){
                    Note note;
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                        note = data.getSerializableExtra("note", Note.class);
                    }else {
                        note = (Note) data.getSerializableExtra("note");
                    }
                    noteDao.updateNote(note);
                    Toast.makeText(this,"Note saved!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Delete note success!", Toast.LENGTH_SHORT).show();
                }
                refreshNotes();
            }
        }
    }

    @Override
    public void onItemClicked(Note note) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra("note", note);
        startActivityForResult(intent,234);
    }
}