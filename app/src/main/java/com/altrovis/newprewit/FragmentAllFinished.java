package com.altrovis.newprewit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.altrovis.newprewit.Bussines.Finished.FinishedAdapter;
import com.altrovis.newprewit.Bussines.Finished.FinishedEndlessScrollAll;
import com.altrovis.newprewit.Entities.GlobalVariable;
import com.altrovis.newprewit.Entities.WorkItem;

import java.util.ArrayList;

public class FragmentAllFinished extends Fragment {

    ListView listViewFinishedAll;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_finished, container, false);

        listViewFinishedAll = (ListView) view.findViewById(R.id.ListViewAllFinished);
        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshLayout);

        if (GlobalVariable.listOfWorkItemFinishedAll == null) {
            GlobalVariable.listOfWorkItemFinishedAll = new ArrayList<WorkItem>();
        }

        GlobalVariable.finishedAdapterAll = new FinishedAdapter(getActivity(), R.layout.item_listview_finished, GlobalVariable.listOfWorkItemFinishedAll);
        listViewFinishedAll.setAdapter(GlobalVariable.finishedAdapterAll);
        listViewFinishedAll.setOnScrollListener(new FinishedEndlessScrollAll((ActivityMain) getActivity(), GlobalVariable.finishedAdapterAll, refreshLayout));

        if (GlobalVariable.listOfFinished.size() == 0) {
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    GlobalVariable.LastID_Finished_All = -1;
                    GlobalVariable.All_Finished_Retrieved = false;
                    GlobalVariable.finishedAdapterAll.clear();
                    listViewFinishedAll.setAdapter(GlobalVariable.finishedAdapterAll);
                    listViewFinishedAll.setOnScrollListener(new FinishedEndlessScrollAll((ActivityMain) getActivity(), GlobalVariable.finishedAdapterAll, refreshLayout));
                    refreshLayout.setRefreshing(false);
                }
            });
        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GlobalVariable.LastID_Finished_All = -1;
                GlobalVariable.All_Finished_Retrieved = false;
                GlobalVariable.finishedAdapterAll.clear();
                listViewFinishedAll.setAdapter(GlobalVariable.finishedAdapterAll);
                listViewFinishedAll.setOnScrollListener(new FinishedEndlessScrollAll((ActivityMain) getActivity(), GlobalVariable.finishedAdapterAll, refreshLayout));
                refreshLayout.setRefreshing(false);
            }
        });

        return view;
    }
}