package com.altrovis.newprewit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.altrovis.newprewit.Bussines.Finished.FinishedAdapter;
import com.altrovis.newprewit.Bussines.Finished.FinishedEndlessScrollByMe;
import com.altrovis.newprewit.Entities.GlobalVariable;
import com.altrovis.newprewit.Entities.WorkItem;

import java.util.ArrayList;

public class FragmentByMeFinished extends Fragment {

    ListView listViewFinishedByMe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_by_me_finished, container, false);

        listViewFinishedByMe = (ListView) view.findViewById(R.id.ListViewByMeFinished);
        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshLayout);

        if (GlobalVariable.listOfWorkItemFinishedByMe == null) {
            GlobalVariable.listOfWorkItemFinishedByMe = new ArrayList<WorkItem>();
        }

        GlobalVariable.finishedAdapterByMe = new FinishedAdapter(getActivity(), R.layout.item_listview_finished, GlobalVariable.listOfWorkItemFinishedByMe);
        listViewFinishedByMe.setAdapter(GlobalVariable.finishedAdapterByMe);
        listViewFinishedByMe.setOnScrollListener(new FinishedEndlessScrollByMe((ActivityMain) getActivity(), GlobalVariable.finishedAdapterByMe));

        if(GlobalVariable.listOfFinishedByMe.size() == 0){
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    GlobalVariable.LastID_Finished_ByMe = -1;
                    GlobalVariable.All_FinishedByMe_Retrieved = false;
                    GlobalVariable.finishedAdapterByMe.clear();
                    listViewFinishedByMe.setAdapter(GlobalVariable.finishedAdapterByMe);
                    listViewFinishedByMe.setOnScrollListener(new FinishedEndlessScrollByMe((ActivityMain) getActivity(), GlobalVariable.finishedAdapterByMe));
                    refreshLayout.setRefreshing(false);
                }
            });
        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GlobalVariable.LastID_Finished_ByMe = -1;
                GlobalVariable.All_FinishedByMe_Retrieved = false;
                GlobalVariable.finishedAdapterByMe.clear();
                listViewFinishedByMe.setAdapter(GlobalVariable.finishedAdapterByMe);
                listViewFinishedByMe.setOnScrollListener(new FinishedEndlessScrollByMe((ActivityMain) getActivity(), GlobalVariable.finishedAdapterByMe));
                refreshLayout.setRefreshing(false);
            }
        });

        return view;
    }
}
