package com.shu.studentmanager.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.NavController.OnDestinationChangedListener;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.shu.studentmanager.R;
import com.shu.studentmanager.StudentManagerApplication;
import com.shu.studentmanager.constant.RequestConstant;
import com.shu.studentmanager.databinding.ActivityMainBinding;
import com.shu.studentmanager.fragment.ScoreManageFragment;

public class MainActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActivityMainBinding binding;
    private String user_kind_string;
    Handler handler_main_activity;

    public Handler getHandler_main_activity() {
        return handler_main_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();

        Log.d(TAG, "onCreate: "+bundle.getString("UserKind"));
        user_kind_string = bundle.getString("UserKind");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);

        Menu menu = navigationView.getMenu();
        if(user_kind_string.equals("student")){
            Log.d(TAG, "onCreate: "+user_kind_string);
            menu.removeGroup(R.id.teacher_menu_group);
            menu.removeGroup(R.id.admin_menu_group);
        } else if(user_kind_string.equals("teacher")){
            StudentManagerApplication application =(StudentManagerApplication) getApplication();
            if(application.getName().equals("admin")){
                menu.removeGroup(R.id.student_menu_group);
                menu.removeGroup(R.id.teacher_menu_group);
            } else{
                menu.removeGroup(R.id.student_menu_group);
                menu.removeGroup(R.id.admin_menu_group);
            }
        }
        NavHostFragment navHostFragment=(NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(navigationView,navController);

        NavigationUI.onNavDestinationSelected(menu.getItem(0),navController);

        navController.addOnDestinationChangedListener( new NavController.OnDestinationChangedListener() {
                    @Override
                    public void onDestinationChanged(
                            @NonNull NavController controller,
                            @NonNull NavDestination destination,
                            @Nullable Bundle arguments
                    ) {
                        toolbar.setTitle(destination.getLabel());
//                        Toast.makeText(getApplicationContext(), destination.getLabel(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        initUi();

        handler_main_activity = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == RequestConstant.REQUEST_SUCCESS){
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.move_linear_openclass), "操作成功", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else if(msg.what == RequestConstant.REQUEST_FAILURE){
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.move_linear_openclass), "操作失败，请联系管理员", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        };
    }
    private void initUi(){
        StudentManagerApplication application =(StudentManagerApplication) getApplication();
        View header = binding.navView.getHeaderView(0);
        TextView text_user_name = header.findViewById(R.id.user_name_text);
        TextView text_user_id = header.findViewById(R.id.user_id_text);
        TextView text_current_term = header.findViewById(R.id.current_term_text);
        text_user_name.setText(application.getName());
        text_user_id.setText(application.getId());
        text_current_term.setText(application.getCurrentTerm());
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}