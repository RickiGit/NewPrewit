package com.altrovis.newprewit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.altrovis.newprewit.Bussines.AddNewWorkItem.AddNewWorkItemAsyncTask;
import com.altrovis.newprewit.Bussines.AddNewWorkItem.GetAllProjectMembersAsyncTask;
import com.altrovis.newprewit.Bussines.AddNewWorkItem.GetAllProjectsAsyncTask;
import com.altrovis.newprewit.Bussines.CustomImageViewCircle;
import com.altrovis.newprewit.Bussines.Logout.LogoutAsyncTask;
import com.altrovis.newprewit.Bussines.Logout.LogoutAsyncTask;
import com.altrovis.newprewit.Entities.GlobalVariable;
import com.altrovis.newprewit.Entities.Project;
import com.altrovis.newprewit.Entities.ProjectMember;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayAdapter<ProjectMember> spEmployeeAdapter;
    private List<ProjectMember> listEmployee;
    private ProjectMember selectedEmployee;

    private ArrayAdapter<Project> spProjectAdapter;
    private List<Project> listProject;
    private Project selectedProject;

    private Spinner spEmployee, spProject;
    private EditText etDeskription;
    private String description;

    private SpinnerEmployeeAdapter employeeAdapter;
    private SpinnerProjectAdapter projectAdapter;

    private MaterialDialog dialog;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("Prewit");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listEmployee = new ArrayList<>();
        spEmployeeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listEmployee);

        listProject = new ArrayList<>();
        spProjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listProject);

        employeeAdapter = new SpinnerEmployeeAdapter();
        projectAdapter = new SpinnerProjectAdapter();

        this.setFloactingActionButton();

        FragmentToMeUnfinished fragment = new FragmentToMeUnfinished();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        ImageView imageViewDrawer = (ImageView)header.findViewById(R.id.ImageViewUserDrawer);
        TextView textViewNickName = (TextView)header.findViewById(R.id.TextViewNickName);

        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        String nickname = preferences.getString("nickName", "");
        String urlProfile = preferences.getString("urlProfile","");
        username = preferences.getString("username","");

        Picasso.with(this)
                .load(urlProfile)
                .resize(120, 120)
                .placeholder(R.drawable.caps)
                .error(R.drawable.caps)
                .centerCrop()
                .transform(new CustomImageViewCircle())
                .into(imageViewDrawer);

        textViewNickName.setText(nickname);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_to_me_unfinished) {
            FragmentToMeUnfinished fragment = new FragmentToMeUnfinished();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
        }else if (id == R.id.nav_by_me_unfinished) {
            FragmentByMeUnfinished fragment = new FragmentByMeUnfinished();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
        }else if(id == R.id.nav_all_unfinished){
            FragmentAllUnfinished fragment = new FragmentAllUnfinished();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
        }else if(id == R.id.nav_all_finished){
            FragmentAllFinished fragment = new FragmentAllFinished();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
        }else if(id == R.id.nav_to_me_finished) {
            FragmentToMeFinished fragment = new FragmentToMeFinished();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
        }else if(id == R.id.nav_by_me_finished){
            FragmentByMeFinished fragment = new FragmentByMeFinished();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
        }else if(id == R.id.nav_sign_out){
            new LogoutAsyncTask(this).execute();
        }
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class SpinnerEmployeeAdapter implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedEmployee = listEmployee.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public class SpinnerProjectAdapter implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedProject = listProject.get(position);

            new GetAllProjectMembersAsyncTask(view.getContext(), selectedProject.getID(), spEmployeeAdapter,
                    dialog).execute();

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private void setFloactingActionButton(){

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialog = new MaterialDialog.Builder(view.getContext()).title("Add New Work Item")
                        .customView(R.layout.dialog_add_workitem, true)
                        .positiveText("Ok")
                        .negativeText("Cancel")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                int projectID = selectedProject.getID();
                                int assignedByID = GetAssignedByID(username);
                                int assignedToID = selectedEmployee.getID();

                                new AddNewWorkItemAsyncTask(dialog.getContext(), description, projectID, assignedByID,
                                        assignedToID, dialog).execute();

                            }
                        })
                        .show();

                new GetAllProjectsAsyncTask(view.getContext(), spProjectAdapter, dialog).execute();

                spProject = (Spinner) dialog.getCustomView().findViewById(R.id.spinner_project);
                spProject.setAdapter(spProjectAdapter);
                spProject.setOnItemSelectedListener(projectAdapter);

                spEmployee = (Spinner) dialog.getCustomView().findViewById(R.id.spinner_employee);
                spEmployee.setAdapter(spEmployeeAdapter);
                spEmployee.setOnItemSelectedListener(employeeAdapter);

                etDeskription = (EditText) dialog.getCustomView().findViewById(R.id.editText_description);

                etDeskription.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        description = s.toString();
                    }
                    @Override
                    public void afterTextChanged(Editable s) {}
                });

            }
        });

    }

    private int GetAssignedByID(String username){

        for (int i = 0; i < GlobalVariable.listOfProjectMembers.size(); i++) {
            ProjectMember member = GlobalVariable.listOfProjectMembers.get(i);
            if(member.getUsername().equalsIgnoreCase(username)){
                return member.getID();
            }
        }

        return 0;
    }
}
