package com.example.steve.nytarticlesearch.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.steve.nytarticlesearch.Models.Doc;
import com.example.steve.nytarticlesearch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleActivity extends AppCompatActivity {

    @BindView(R.id.wvArticle) WebView wvArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);


        //Bundle myBundle = getIntent().getExtras();
        Doc articles = getIntent().getParcelableExtra("articles");

        //local variables to use
        final String ArticleURL = articles.getWebUrl();


        wvArticle.setWebViewClient(new WebViewClient(){
                                       @Override
                                       public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                           view.loadUrl(url);
                                                   return true;
                                       }
                                   });

        wvArticle.loadUrl(ArticleURL);

    }
}
