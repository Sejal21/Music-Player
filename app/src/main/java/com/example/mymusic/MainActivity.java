package com.example.mymusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView mainList,l;
    TextView t,t5;
    ImageView i ;
    int pos;
    final String[] listContent = {"One Direction - Little Things", "Perfect - Ed Sheeran","Youth - Troye Sivan","Death Bed(Coffee on your head)"};
    final int[] resID = {R.raw.littlethings,R.raw.perfect,R.raw.youth,R.raw.deathbed};
    final int[] images = {R.drawable.oned,R.drawable.perfect,R.drawable.youth,R.drawable.download};
     String[] favouriteList;

     int favPos = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainList = findViewById(R.id.listView);
        mainList.setVisibility(View.INVISIBLE);
        t = findViewById(R.id.textView);
        t.setVisibility(View.INVISIBLE);
        l = findViewById(R.id.listview2);
        l.setVisibility(View.INVISIBLE);
        t5 = findViewById(R.id.textView5);
        t5.setVisibility(View.INVISIBLE);
        i = findViewById(R.id.imageView2);
        i.setVisibility(View.INVISIBLE);
        favouriteList = new String[]{"", "", "", ""};
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.listviewfile,R.id.label,listContent);
        mainList.setAdapter(adapter);
        ArrayAdapter adapter1 = new ArrayAdapter(this,R.layout.listviewfile,R.id.label,favouriteList);
        l.setAdapter(adapter1);
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(),Songs.class);
                i.putExtra("song",String.valueOf(resID[position]));
                i.putExtra("nextsong",String.valueOf(resID[position+1]));
                i.putExtra("prevsong",String.valueOf(resID[position-1]));
                i.putExtra("image",String.valueOf(images[position]));
                i.putExtra("nextimage",String.valueOf(images[position+1]));
                i.putExtra("previmage",String.valueOf(images[position-1]));
                i.putExtra("namesong",listContent[position]);
                i.putExtra("nextnamesong",listContent[position+1]);
                i.putExtra("prevnamesong",listContent[position-1]);
                startActivity(i);
            }
        });
           registerForContextMenu(mainList);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.favourites, menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.icon_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.musicList:{
                t.setVisibility(View.VISIBLE);
               mainList.setVisibility(View.VISIBLE);
            }
            break;
            case R.id.favourite:{
                l.setVisibility(View.VISIBLE);
                t5.setVisibility(View.VISIBLE);
                i.setVisibility(View.VISIBLE);
            }
            break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addFavourites:{
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int index = info.position;
                Toast.makeText(this, ""+index, Toast.LENGTH_SHORT).show();
                favouriteList[favPos++] = listContent[index];
                ArrayAdapter adapter1 = new ArrayAdapter(this,R.layout.listviewfile,R.id.label,favouriteList);
                l.setAdapter(adapter1);

            }
            break;
        }
        return super.onContextItemSelected(item);
    }
}