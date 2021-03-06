package com.altrovis.newprewit;

import android.app.DatePickerDialog;
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
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.altrovis.newprewit.Bussines.AddNewWorkItem.AddNewWorkItemAsyncTask;
import com.altrovis.newprewit.Bussines.AddNewWorkItem.GetAllProjectMembersAsyncTask;
import com.altrovis.newprewit.Bussines.AddNewWorkItem.GetAllProjectsAsyncTask;
import com.altrovis.newprewit.Bussines.CustomImageViewCircle;
import com.altrovis.newprewit.Bussines.Logout.LogoutAsyncTask;
import com.altrovis.newprewit.Bussines.Notification.RegisterDeviceGCMAsyncTask;
import com.altrovis.newprewit.Bussines.Notification.UnregisterDeviceAsyncTask;
import com.altrovis.newprewit.Entities.GlobalVariable;
import com.altrovis.newprewit.Entities.Project;
import com.altrovis.newprewit.Entities.ProjectMember;
import com.altrovis.newprewit.Entities.WorkItem;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
    private EditText etDeskription, editTextDeadline;
    private String description, deadline;

    private SpinnerEmployeeAdapter employeeAdapter;
    private SpinnerProjectAdapter projectAdapter;

    private MaterialDialog dialog;
    private String username;

    android.support.v7.app.ActionBar actionBar;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set Action Bar
        actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("To Me Unfinished");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Set List Spinner New Workitem
        listEmployee = new ArrayList<ProjectMember>();
        spEmployeeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listEmployee);

        listProject = new ArrayList<>();
        spProjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listProject);

        if(listEmployee.size() == 0){
            employeeAdapter = new SpinnerEmployeeAdapter();
        }

        if(listProject.size() == 0) {
            projectAdapter = new SpinnerProjectAdapter();
        }

        GlobalVariable.activityMain = this;

        this.setFloactingActionButton();

        // Default Fragment (Unfinished To Me)
        FragmentToMeUnfinished fragment = new FragmentToMeUnfinished();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();

        // Set Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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

        String registrationID = preferences.getString("registrationID", "");
        if(registrationID.length() == 0)
        {
            RegisterDeviceGCMAsyncTask registerDeviceGCMTask = new RegisterDeviceGCMAsyncTask(this);
            registerDeviceGCMTask.execute();
        }
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
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, new FragmentToMeUnfinished());
            fragmentTransaction.commit();

            actionBar = getSupportActionBar();
            if(actionBar != null){
                actionBar.setTitle("To Me Unfinished");
            }
            GlobalVariable.fragmentFrom = true;
        }else if (id == R.id.nav_by_me_unfinished) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, new FragmentByMeUnfinished());
            fragmentTransaction.commit();

            actionBar = getSupportActionBar();
            if(actionBar != null){
                actionBar.setTitle("By Me Unfinished");
            }
            GlobalVariable.fragmentFrom = false;
        }else if(id == R.id.nav_all_unfinished){
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, new FragmentAllUnfinished());
            fragmentTransaction.commit();

            actionBar = getSupportActionBar();
            if(actionBar != null){
                actionBar.setTitle("All Unfinished");
            }
            GlobalVariable.fragmentFrom = false;
        }else if(id == R.id.nav_all_finished){
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, new FragmentAllFinished());
            fragmentTransaction.commit();

            actionBar = getSupportActionBar();
            if(actionBar != null){
                actionBar.setTitle("All Finished");
            }
            GlobalVariable.fragmentFrom = false;
        }else if(id == R.id.nav_to_me_finished) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, new FragmentToMeFinished());
            fragmentTransaction.commit();

            actionBar = getSupportActionBar();
            if(actionBar != null){
                actionBar.setTitle("To Me Finished");
            }
            GlobalVariable.fragmentFrom = false;
        }else if(id == R.id.nav_by_me_finished){
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, new FragmentByMeFinished());
            fragmentTransaction.commit();

            actionBar = getSupportActionBar();
            if(actionBar != null){
                actionBar.setTitle("By Me Finished");
            }
            GlobalVariable.fragmentFrom = false;
        }else if(id == R.id.nav_sign_out){
            new UnregisterDeviceAsyncTask(this).execute();
            new LogoutAsyncTask(this).execute();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class SpinnerEmployeeAdapter implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(listEmployee.size() != 0){
                selectedEmployee = listEmployee.get(position);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


    public class SpinnerProjectAdapter implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if(listProject.size() != 0){
                selectedProject = listProject.get(position);
                if(selectedProject != null || selectedProject.getID() > 0){
                    new GetAllProjectMembersAsyncTask(view.getContext(), selectedProject.getID(), spEmployeeAdapter, dialog).execute();
                }
            }
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

                                if(editTextDeadline.getText().toString().equals("")){
                                    Toast.makeText(ActivityMain.this, "Harap masukan deadline workitem", Toast.LENGTH_LONG).show();
                                }else{
                                    new AddNewWorkItemAsyncTask(dialog.getContext(), description, projectID, deadline, assignedByID,
                                            assignedToID, dialog).execute();
                                }

                            }
                        }).show();

                Collections.sort(listEmployee, new Comparator<ProjectMember>(){
                    public int compare(ProjectMember emp1, ProjectMember emp2) {
                        return emp1.getNickname().compareToIgnoreCase(emp2.getNickname());
                    }
                });

                spProjectAdapter.clear();
                spEmployeeAdapter.clear();

                spProject = (Spinner) dialog.getCustomView().findViewById(R.id.spinner_project);
                spProject.setAdapter(spProjectAdapter);
                spProject.setOnItemSelectedListener(projectAdapter);

                spEmployee = (Spinner) dialog.getCustomView().findViewById(R.id.spinner_employee);
                spEmployee.setAdapter(spEmployeeAdapter);
                spEmployee.setOnItemSelectedListener(employeeAdapter);

                etDeskription = (EditText) dialog.getCustomView().findViewById(R.id.editText_description);
                editTextDeadline = (EditText) dialog.getCustomView().findViewById(R.id.EditTextDeadline);

                editTextDeadline.setInputType(InputType.TYPE_NULL);
                editTextDeadline.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            Calendar currentDate = Calendar.getInstance();
                            int year = currentDate.get(Calendar.YEAR);
                            int month = currentDate.get(Calendar.MONTH);
                            int day = currentDate.get(Calendar.DAY_OF_MONTH);

                            DatePickerDialog datePicker = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                                    Calendar c = Calendar.getInstance();
                                    c.set(selectedyear, selectedmonth, selectedday);

                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                    String tanggal = sdf.format(c.getTime());
                                    editTextDeadline.setText(tanggal);
                                }
                            }, year, month, day);
                            datePicker.show();
                        }
                        return false;
                    }
                });

                editTextDeadline.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        deadline = s.toString();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

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

                if(projectAdapter != null && employeeAdapter != null){
                    if(spProject != null && spEmployee != null){
                        new GetAllProjectsAsyncTask(view.getContext(), spProjectAdapter, dialog).execute();
                    }
                }

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
