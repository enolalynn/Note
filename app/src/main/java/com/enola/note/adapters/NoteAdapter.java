package com.enola.note.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enola.note.databinding.ItemNoteBinding;
import com.enola.note.entities.Note;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes;
    private NoteItemClickListener listener;

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.binding.getRoot().setOnClickListener((v -> {
            listener.onItemClicked(note);
        }));
        holder.binding.tvTitle.setText(note.getTitle());
        holder.binding.tvDescription.setText(note.getDescription());
        var created = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(note.getCreated());
        holder.binding.tvCreated.setText(created);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        this.notifyDataSetChanged();
    }

    public void setOnNoteItemClickListener(NoteItemClickListener listener) {
        this.listener = listener;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        public  ItemNoteBinding binding;
        public NoteViewHolder(ItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
