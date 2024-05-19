package com.example.syndicat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Video extends AppCompatActivity {
  WebView webView;
  String html;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video);
        webView=findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        html="<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/SIsPc0S34vo?si=4ccs_90WJx8NQj6a\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>";
        webView.loadData(html,"text/html",null);
        imageButton=(ImageButton)findViewById(R.id.backButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Video.this,Home.class);
                startActivity(intent);
            }
        });


    }
}