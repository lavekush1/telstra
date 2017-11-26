package com.lavekush.telstra.activity;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.lavekush.telstra.R;
import com.lavekush.telstra.fragment.ItemFragment;
import com.lavekush.telstra.vo.RowItem;

/**
 * Main class contain the a single for displaying the list item with swipe-to-refresh functionality
 */
public class HomeActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //setting up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("List Items");
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //adding fragment to activity
        addFragment(R.id.container_fragment, ItemFragment.newInstance(null), "ItemFragment");
    }


    /**
     * For adding fragment on view
     *
     * @param containerViewId - Resource ID
     * @param fragment - Fragment need to be added on container
     * @param fragmentTag - TAG for the fragment (Useful once we poping it)
     */
    protected void addFragment(@IdRes int containerViewId,
                               @NonNull Fragment fragment,
                               @NonNull String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commit();
    }


    @Override
    public void onListFragmentInteraction(RowItem item) {
        Toast.makeText(getApplicationContext(), "You clicked on: " +
                ""+ item.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
