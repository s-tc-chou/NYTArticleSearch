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
import android.widget.AbsListView;
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

    //misc variables
    private int mCurrentPage = 0;

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

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {

                customLoadMoreDataFromApi(mCurrentPage);
                //Toast.makeText(getApplicationContext(),"load more " + page + " " + totalItemsCount, Toast.LENGTH_SHORT).show();
                mCurrentPage++;
                return false;
            }
        });
    }

    //endless scrolling pieces--------------------------------------------------

    public abstract class EndlessScrollListener implements AbsListView.OnScrollListener{
        // The minimum number of items to have below your current scroll position
        // before loading more.
        private int visibleThreshold = 10;
        // The current offset index of data you have loaded
        private int currentPage = 0;
        // The total number of items in the dataset after the last load
        private int previousTotalItemCount = 0;
        // True if we are still waiting for the last set of data to load.
        private boolean loading = true;
        // Sets the starting page index
        private int startingPageIndex = 0;

        public EndlessScrollListener() {
        }

        public EndlessScrollListener(int visibleThreshold) {
            this.visibleThreshold = visibleThreshold;
        }

        public EndlessScrollListener(int visibleThreshold, int startPage) {
            this.visibleThreshold = visibleThreshold;
            this.startingPageIndex = startPage;
            this.currentPage = startPage;
        }

        // This happens many times a second during a scroll, so be wary of the code you place here.
        // We are given a few useful parameters to help us work out if we need to load some more data,
        // but first we check if we are waiting for the previous load to finish.
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
        {
            // If the total item count is zero and the previous isn't, assume the
            // list is invalidated and should be reset back to initial state
            if (totalItemCount < previousTotalItemCount) {
                this.currentPage = this.startingPageIndex;
                this.previousTotalItemCount = totalItemCount;
                if (totalItemCount == 0) { this.loading = true; }
            }
            // If it's still loading, we check to see if the dataset count has
            // changed, if so we conclude it has finished loading and update the current page
            // number and total item count.
            if (loading && (totalItemCount > previousTotalItemCount)) {
                loading = false;
                previousTotalItemCount = totalItemCount;
                currentPage++;
            }

            // If it isn't currently loading, we check to see if we have breached
            // the visibleThreshold and need to reload more data.
            // If we do need to reload some more data, we execute onLoadMore to fetch the data.
            if (!loading && (firstVisibleItem + visibleItemCount + visibleThreshold) >= totalItemCount ) {
                loading = onLoadMore(currentPage + 1, totalItemCount);
            }
        }

        // Defines the process for actually loading more data based on page
        // Returns true if more data is being loaded; returns false if there is no more data to load.
        public abstract boolean onLoadMore(int page, int totalItemsCount);

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            // Don't take any action on changed
        }
    }


    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
        //mCurrentPage holds the current page count.
        mCurrentPage = offset;
        String query = etQuery.getText().toString();
        RetrieveQuery(query);

        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
    }

    //getter for setting pieces.
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
    }

    //launches when search button is pressed.
    public void onArticleSearch(View view) {
        String query = etQuery.getText().toString();

        if (query.equals("") || query.equals(null)) {
            showNoTextAlert();
        }
        else {
            mCurrentPage = 0;
            adapter.clear();
            RetrieveQuery(query);
        }
    }


    //Retrofit functions ------------------------------------------
    //Retrofit implementation to retrieve query
    private void RetrieveQuery(String query) {

        //retrieve news API only if there's internet.
        if (isNetworkAvailable() && isOnline()) {

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
                        call = apiService.getResponse(API_KEY, query, mCurrentPage, filter, sortOrder, beginDate);
                    } else {
                        call = apiService.getResponse(API_KEY, query, mCurrentPage, filter, sortOrder);
                    }
                }
                else {
                    if (settings.getUseBeginDate()) {
                        call = apiService.getResponse(API_KEY, query, mCurrentPage, sortOrder, beginDate);
                    } else {
                        call = apiService.getResponse(API_KEY, query, mCurrentPage, sortOrder);
                    }
                }
            }
            //no settings have been enabled, default calls.
            else {
                call = apiService.getResponse(API_KEY, query, mCurrentPage);

            }

            //fire off the call.
            call.enqueue(new Callback<RetrofitResponse>() {
                @Override
                public void onResponse(Call<RetrofitResponse> call, retrofit2.Response<RetrofitResponse> response) {
                    List<Doc> newDocs = response.body().getResponse().getDocs();

                    //only add to array if it's not a dupe
                    if (!duplicateCheck(newDocs))
                    {
                        adapter.addAll(newDocs);
                        adapter.notifyDataSetChanged();

                    }
                }

                @Override
                public void onFailure(Call<RetrofitResponse> call, Throwable t) {
                    Log.e("RetrieveQuery()", "Retrofit callback failed");
                }
            });
            adapter.notifyDataSetChanged();

        }
        else {
            showOfflineAlert();
        }
    }

    private boolean duplicateCheck(List<Doc>newDocs)
    {
        boolean isDupe = false;

        //look for just 1 duplicate.  If the record is already in the document list, ignore the whole batch.

        String testTitle = "Not Duplicate";

        if (newDocs.size() > 0)
        {
            testTitle = newDocs.get(0).getHeadline().getMain();
        }

        for (int i = 0; i < docs.size(); i++)
        {
            String docTitle = docs.get(i).getHeadline().getMain();
            if (docTitle.equals(testTitle))
            {
                isDupe = true;
                break;
            }
        }

        return isDupe;
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

    public void showNoTextAlert()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(SearchActivity.this);
        builder1.setMessage("Please enter a search term. ");
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
