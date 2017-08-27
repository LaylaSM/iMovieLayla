package intermediate.iak.laylasm.imovie;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private final String API_Key = "9351b653885866a95fcef04c4f0c7426";
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerViewAdapter adapter;
    private String FilmCategory;
    private SearchView mnSearch;
    private ItemObject a;

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    private CharSequence mDrawerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        layoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        requestJsonObject(0);

        mNavItems.add(new NavItem("Popular", "Popular Movies", R.drawable.icon));
        mNavItems.add(new NavItem("Top Rated", "Favorite Movies", R.drawable.icon));
        mNavItems.add(new NavItem("Up Coming", "The Up Coming Movies", R.drawable.icon));
        mNavItems.add(new NavItem("About", "", R.drawable.icon));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main);

        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adp = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adp);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        requestJsonObject(0);
                        mDrawerLayout.closeDrawers();
                        break;
                    case 1:
                        requestJsonObject(1);
                        mDrawerLayout.closeDrawers();
                        break;
                    case 2:
                        requestJsonObject(2);
                        mDrawerLayout.closeDrawers();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext().getApplicationContext(),
                                "iMovie.\n" + "Layla S.M.",
                                Toast.LENGTH_LONG).show();
                        break;
                }
                mDrawerList.setItemChecked(position, true);
            }
        });
    }

    private void requestJsonObject(int i) {
        if (i == 0) {
            setTitle("Popular Movie");
            FilmCategory = "popular";
        } else if (i == 1) {
            setTitle("Top Rated Movie");
            FilmCategory = "top_rated";
        } else if (i == 2) {
            setTitle("Coming Soon");
            FilmCategory = "upcoming";
        }
        String FullURL = "http://api.themoviedb.org/3/movie/" +
                FilmCategory +
                "?api_key=" + API_Key;
        MyParsingGson(FullURL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        mnSearch = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mnSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplication(), "Searching", Toast.LENGTH_LONG).show();
                String url = "";
                url = "http://api.themoviedb.org/3/search/movie?" +
                        FilmCategory +
                        "&query=" + query +
                        "&api_key=" + API_Key;
                MyParsingGson(url);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnPopuler:
                requestJsonObject(0);
                break;
            case R.id.mnFavorit:
                requestJsonObject(1);
                break;
            case R.id.mnComing:
                requestJsonObject(2);
                break;
            case R.id.mnAbout:
                Toast.makeText(getApplicationContext().getApplicationContext(),
                        "    Final Project\n" + "iMovie Layla S.M.",
                        Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void MyParsingGson(String url) {
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            ;

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response " + response);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                a = mGson.fromJson(response, ItemObject.class);
                adapter = new RecyclerViewAdapter(MainActivity.this, a.results);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(), "Error Response", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
}