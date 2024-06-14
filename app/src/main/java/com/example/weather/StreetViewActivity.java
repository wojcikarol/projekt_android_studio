package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class StreetViewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_view);

        webView = findViewById(R.id.webView);

        webView.getSettings().setDomStorageEnabled(true);

        String streetViewUrl = getIntent().getStringExtra("STREET_VIEW_URL");

        if (streetViewUrl != null && !streetViewUrl.isEmpty()) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(request.getUrl().toString());
                    return false;
                }
            });
            webView.loadUrl(streetViewUrl);
        } else {
            Toast.makeText(this, "Brak danych o wybranym miejscu", Toast.LENGTH_SHORT).show();
        }
    }
}
