package com.onursercanyilmaz.kitapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView contact_button, empty_image;
    TextView empty_library;

    MyDatabaseHelper myDB ;
    ArrayList<String> book_id, book_title, book_author, book_pages ;

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Intent starterIntent = getIntent();


        recyclerView = findViewById( R.id.recyclerView );
        empty_image = findViewById( R.id.imageView3 );
        empty_library = findViewById( R.id.textView );
        contact_button = findViewById( R.id.contact_button );
        add_button = findViewById( R.id.add_button );
        add_button.setOnClickListener( new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent =  new Intent( MainActivity.this, AddActivity.class );
               startActivity( intent );

           }
       } );

        contact_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent( MainActivity.this, ContactActivity.class );
                startActivity( intent );
            }
        } );

        myDB = new MyDatabaseHelper( MainActivity.this );
        book_id = new ArrayList<>(  );
        book_title = new ArrayList<>(  );
        book_author = new ArrayList<>(  );
        book_pages = new ArrayList<>(  );

        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this, this,
                book_id,book_title,book_author,book_pages);
        recyclerView.setAdapter(customAdapter);

        recyclerView.setLayoutManager( new LinearLayoutManager( MainActivity.this ) );




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if(requestCode == 1)
        {
            recreate();
        }
    }


    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0)
        {
          //  Toast.makeText( this,"EMPTY LIBRARY!", Toast.LENGTH_LONG ).show();

            empty_image.setVisibility( View.VISIBLE );
            empty_library.setVisibility( View.VISIBLE );
        }else {
            while (cursor.moveToNext()){
                book_id.add(cursor.getString( 0 ));
                book_title.add(cursor.getString( 1 ));
                book_author.add(cursor.getString( 2 ));
                book_pages.add(cursor.getString( 3 ));

            }
            empty_image.setVisibility( View.GONE );
            empty_library.setVisibility( View.GONE );
        }
    }


}