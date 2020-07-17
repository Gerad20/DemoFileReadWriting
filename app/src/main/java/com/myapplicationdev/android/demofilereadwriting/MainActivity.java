package com.myapplicationdev.android.demofilereadwriting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.os.Environment;
import android.view.View;
import android.widget.Button;


import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    String folderLocation ;

    Button write;
    Button read;
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        write = findViewById(R.id.btnWrite);
        read = findViewById(R.id.btnRead);
        display = findViewById(R.id.textDisplay);


        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {

        } else {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }


        folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyFolder";

        File folder = new File(folderLocation);
        if (folder.exists() == false){
            boolean result = folder.mkdir();
            if (result == true){
                Log.d("File Read/Write", "Folder created");
            }
        }

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyFolder";
                    File targetFile = new File(folderLocation,"data.txt");
                    FileWriter writer = new FileWriter(targetFile,true);
                    writer.write("Hello World" + "\n");
                    writer.flush();
                    writer.close();
                    Toast.makeText(getApplicationContext(),"Created the tex",Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"Unable to create text",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String folderLocation= Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyFolder";
                File targetFile= new File(folderLocation, "data.txt");
                if (targetFile.exists() == true){String data ="";try {
                    FileReader reader = new FileReader(targetFile);
                    BufferedReader br= new BufferedReader(reader);
                    String line = br.readLine();
                    while (line != null){
                        data += line + "\n";
                        line = br.readLine();

                    }
                    display.setText(data);
                    br.close();
                    reader.close();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Failed to read!", Toast.LENGTH_LONG).show();e.printStackTrace();
                }
                Log.d("content", data);
                }

            }
        });
    }
}

