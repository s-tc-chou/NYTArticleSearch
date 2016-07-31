package com.example.steve.nytarticlesearch.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.steve.nytarticlesearch.Interfaces.RetrofitInterface;
import com.example.steve.nytarticlesearch.Models.Doc;
import com.example.steve.nytarticlesearch.Models.Response;
import com.example.steve.nytarticlesearch.Models.RetrofitResponse;
import com.example.steve.nytarticlesearch.Models.editOptions;
import com.example.steve.nytarticlesearch.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity implements editOptionFragment.onEditFinishedListener{

    //retrofit results
    List<Doc> docs;

    static final String API_KEY = "d463a57f4e3345a285f68315e0a7db00";
    static final String BASE_URL = "https://api.nytimes.com";

    //butterknife binds
    @BindView(R.id.etQuery)     EditText etQuery;
    @BindView(R.id.gvResults)   GridView gvResults;
    @BindView(R.id.btnSearch)   Button btnSearch;
    @BindView(R.id.toolbar)     Toolbar toolbar;

    ArticleArrayAdapter adapter;

    //bundled fragment variables
    private editOptions settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initialize();
    }

    //Base Activity helper functions -------------------------------------------
    //set up basic variables we'll be using as well as the adapter's onclicklistener.
    public void initialize() {

        //adapter initialization
        docs = new ArrayList<>();
        adapter = new ArticleArrayAdapter(this,docs);
        gvResults.setAdapter(adapter);

        //setting variables
        settings = new editOptions();

        //setup hook for listener click
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //create intent to display article
                Intent i = new Intent(getApplicationContext(),ArticleActivity.class);

                //get article to display
                Doc article = docs.get(position);
                //pass in article to intent
                i.putExtra("articles", article);

                //launch intent if network is available.
                if (isNetworkAvailable() && isOnline()) {
                    startActivity(i);
                }
                else {
                    showOfflineAlert();
                }
            }
        });
    }

    public editOptions getCurrentSettings() {return settings;}

    //Toolbar functions -----------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //setting button pressed
        if (id == R.id.action_settings) {

            //launch fragment
            showSettingDialog();

            //Toast.makeText(this,"setting pressed", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Fragment functions ---------------------------------

    //launch the settings dialog
    private void showSettingDialog() {
        FragmentManager fm = getSupportFragmentManager();
        editOptionFragment editFragment = new editOptionFragment();
        editFragment.setStyle(editFragment.STYLE_NORMAL, android.R.style.Theme_Holo);
        editFragment.show(fm, "fragment_edit_options");
    }


    //Retrieve data from fragment.
    @Override
    public void onEditFinish(editOptions editedSettings) {
        this.settings = editedSettings;
        String query = etQuery.getText().toString();
    }

    //launches when search button is pressed.
    public void onArticleSearch(View view) {
        String query = etQuery.getText().toString();
        adapter.clear();
        RetrieveQuery(query);
    }


    //Retrofit functions ------------------------------------------
    //Retrofit implementation to retrieve query
    private void RetrieveQuery(String query) {

        //retrieve news API only if there's internet.
        if (isNetworkAvailable() && isOnline()) {

            docs = new ArrayList<Doc>();
            //http logging-------------------------------
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);
            //End logging ----------------------------------

            Retrofit retrofitAdapter = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())  //this piece can be disabled?
                    .build();

            RetrofitInterface apiService = retrofitAdapter.create(RetrofitInterface.class);
            Call<RetrofitResponse> call;

            //use extra query filters if settings enabled
            if (settings.isSettingsApplied()) {

                DecimalFormat formatter = new DecimalFormat("00");
                //Note: month in calendar is 0 based, so we add 1 here for the API.
                String date = String.valueOf(settings.getYear()) +String.valueOf(formatter.format(settings.getMonth()+1)) + String.valueOf(settings.getDay());
                String sortOrder = settings.getSortOrder();
                int beginDate = Integer.parseInt(date);

                //check if we have query filters: if not use alternative call as we cannot pass in blank fq parameter to api.
                if(settings.isArts() || settings.isFashionStyle() || settings.isSports()) {
                    String filter = createFilter();//settings.getFilter();
                    if (settings.getUseBeginDate()) {
                        call = apiService.getResponse(API_KEY, query, filter, sortOrder, beginDate);
                    } else {
                        call = apiService.getResponse(API_KEY, query, filter, sortOrder);
                    }
                }
                else {
                    if (settings.getUseBeginDate()) {
                        call = apiService.getResponse(API_KEY, query, sortOrder, beginDate);
                    } else {
                        call = apiService.getResponse(API_KEY, query, sortOrder);
                    }
                }
            }
            //no settings have been enabled, default calls.
            else {
                call = apiService.getResponse(API_KEY, query);

            }

            //fire off the call.
            call.enqueue(new Callback<RetrofitResponse>() {
                @Override
                public void onResponse(Call<RetrofitResponse> call, retrofit2.Response<RetrofitResponse> response) {
                    docs = response.body().getResponse().getDocs();
                    adapter.addAll(docs);
                }

                @Override
                public void onFailure(Call<RetrofitResponse> call, Throwable t) {
                    Log.e("RetrieveQuery()", "Retrofit callback failed");
                }
            });
        }
        else {
            showOfflineAlert();
        }
    }


    private String createFilter()
    {
        String newsDesk = "news_desk:(";
        String ARTS = "\"Arts\"";
        String FASHION_STYLE = "\"Fashion %26 Style\"";
        String SPORTS= "\"Sports\"";

        if (settings.isArts())
        {
            newsDesk += ARTS;
        }
        if (settings.isFashionStyle())
        {
            newsDesk += FASHION_STYLE;
        }
        if (settings.isSports())
        {
            newsDesk += SPORTS;
        }

        newsDesk += ")";

        return newsDesk;
    }

    //Network functions ------------------------
    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }

    //display message box if offline.
    public void showOfflineAlert()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext());
        builder1.setMessage("Need internet connection to view articles. ");
        builder1.setCancelable(true);

        builder1.setNeutralButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


}
