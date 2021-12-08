package com.example.quickcapture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder>{


    Context context;
    RealmResults<Note> noteslist;

    public myAdapter(@NonNull Context context, RealmResults<Note> noteslist) {
        this.context = context;
        this.noteslist = noteslist;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titleOutput;
        TextView descriptionOutput;
        TextView timeOutput;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleOutput = itemView.findViewById(R.id.titleOutput);
            this.descriptionOutput=itemView.findViewById(R.id.descriptionOutput);
            this.timeOutput=itemView.findViewById(R.id.timeOutput);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
Note note=noteslist.get(position);
holder.titleOutput.setText(note.getTitle());
holder.descriptionOutput.setText(note.getDescription());
String formatedTime= DateFormat.getDateTimeInstance().format(note.createdTime);
holder.timeOutput.setText(formatedTime);

holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View view) {
        PopupMenu menu=new PopupMenu(context,view);
        menu.getMenu().add("Delete");
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle().equals("Delete")){
                    Realm realm=Realm.getDefaultInstance();
                    realm.beginTransaction();
                    note.deleteFromRealm();
                    realm.commitTransaction();
                    Toast.makeText(context,"DELETED",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        menu.show();
        return true;
    }
});
    }

    @Override
    public int getItemCount() {
        return noteslist.size();
    }


}
