package com.example.slickkwear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slickkwear.Prevalent.Prevalent;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.api.BackendRule;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.smarteist.autoimageslider.SliderView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    SliderView sliderView;
    private SliderHomeAdapter adapter;

    private FirebaseFirestore productRef;
    private Query query;

    private TextView search_back_icon;
    private EditText user_search_input;
    private LinearLayout search_interface, search_container_layout;
    private MaterialSearchView searchView;

    private View notificationBadge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        userSearch();

//        SETTING UP BOTTOM NAVIGATION BAR

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserHomeFragment())
                .commit();

        bottomNavigationView=findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.getOrCreateBadge(R.id.bottom_nav_cart).setVisible(true);
        bottomNavigationView.getOrCreateBadge(R.id.bottom_nav_cart).setNumber(Prevalent.CartItems);
//        bottomNavigationView.getOrCreateBadge(R.id.bottom_nav_help).setVisible(true);
//        bottomNavigationView.getOrCreateBadge(R.id.bottom_nav_help).setNumber(0);

//
//
//        user_search_input = (EditText) findViewById(R.id.user_search_input);
////        search_edit_text = (EditText) findViewById(R.id.search_edit_text);
//
//        search_back_icon = (TextView) findViewById(R.id.search_back_icon);
//        search_interface = (LinearLayout) findViewById(R.id.search_interface);
//        search_container_layout = (LinearLayout) findViewById(R.id.search_container_layout);

//        TextView test12 = (TextView) findViewById(R.id.search_btn);
//        search_container_layout.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//
//                        search_interface.setVisibility(View.VISIBLE);
//                        search_interface.startAnimation(inFromRightAnimation());
////                        search_interface.setVisibility(View.VISIBLE);
////                        Animation leftSwipe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_left_search_interface);
////                        search_interface.startAnimation(leftSwipe);
//                    }
//                }
//        );
//
//        search_back_icon.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        user_search_input.setText("");
//                        user_search_input.clearFocus();
//                        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
//
//                        search_interface.startAnimation(outToRightAnimation());
//                        search_interface.setVisibility(View.GONE);
////                        Animation rightSwipe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_right_search_interface);
////                        search_interface.startAnimation(rightSwipe);
//                    }
//                }
//        );

//        search_edit_text.setOnFocusChangeListener(
//                new View.OnFocusChangeListener() {
//                    @Override
//                    public void onFocusChange(View view, boolean b) {
//
////                        s
//                        Toast.makeText(MainActivity.this, "Result0" + width1, Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//        );

//        homeSliderBanner();
    }

//    private Animation inFromRightAnimation() {
//        Animation inFromRight = new TranslateAnimation( Animation.RELATIVE_TO_PARENT, +1.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
//        inFromRight.setDuration(500);
//        inFromRight.setInterpolator(new AccelerateInterpolator());
//        return inFromRight;
//    }
//
//    private Animation outToRightAnimation() {
//        Animation outToRight = new TranslateAnimation( Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, +1.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
//        outToRight.setDuration(500);
//        outToRight.setInterpolator(new AccelerateInterpolator());
//        return outToRight;
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search_menu, menu);
//
//        MenuItem item = menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);
//
//        return true;
//    }

//    private void userSearch() {
//
//        String[] list = new String[] {"one", "two", "three"};
//
////        searchView = (MaterialSearchView) findViewById(R.id.search_view);
////        searchView.setSuggestions(list);
//        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                searchView.setSuggestions(list);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                //Do some magic
//                searchView.setSuggestions(list);
//                return false;
//            }
//        });
//
//        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
//            @Override
//            public void onSearchViewShown() {
////                searchView.showSuggestions();
//                //Do some magic
//            }
//
//            @Override
//            public void onSearchViewClosed() {
//                //Do some magic
//            }
//        });
//    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId())
            {
                case R.id.bottom_nav_home:
                    selectedFragment = new UserHomeFragment();
                    break;
                case R.id.bottom_nav_category:
                    selectedFragment = new UserCategoryFragment();
                    break;
                case R.id.bottom_nav_cart:
                    selectedFragment = new UserCartFragment();
                    break;
                case R.id.bottom_nav_help:
                    selectedFragment = new UserHelpFragment();
                    break;
                case R.id.bottom_nav_account:
                    selectedFragment = new UserAccountFragment();
                    break;
            }
//            getSupportFragmentManager()
//                    .set
//
//                    .beginTransaction().replace(R.id.fragment_container, selectedFragment)
//                    .commit();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
            transaction.replace(R.id.fragment_container, selectedFragment);
            transaction.commit();

            return true;
        }
    };

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager()
                .getBackStackEntryCount() > 0) {

            super.onBackPressed();

        } else {

            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.app_name))
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                            System.exit(0);

                        }
                    }).setNegativeButton("No", null).show();

        }
    }

}