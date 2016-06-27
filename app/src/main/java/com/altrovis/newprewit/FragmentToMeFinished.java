package com.altrovis.newprewit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.altrovis.newprewit.Bussines.Finished.FinishedAdapter;
import com.altrovis.newprewit.Bussines.Finished.FinishedEndlessScrollToMe;
import com.altrovis.newprewit.Entities.GlobalVariable;
import com.altrovis.newprewit.Entities.WorkItem;

import java.util.ArrayList;

public class FragmentToMeFinished extends Fragment {

    ListView listViewFinishedToMe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_to_me_finished, container, false);

        listViewFinishedToMe = (ListView)view.findViewById(R.id.ListViewToMeFinished);

        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshLayout);

        if (GlobalVariable.listOfWorkItemFinishedToMe == null) {
            GlobalVariable.listOfWorkItemFinishedToMe = new ArrayList<WorkItem>();
        }

        GlobalVariable.finishedAdapterToMe = new FinishedAdapter(getActivity(), R.layout.item_listview_finished, GlobalVariable.listOfWorkItemFinishedToMe);
        listViewFinishedToMe.setAdapter(GlobalVariable.finishedAdapterToMe);
        listViewFinishedToMe.setOnScrollListener(new FinishedEndlessScrollToMe((ActivityMain) getActivity(), GlobalVariable.finishedAdapterToMe));

        if(GlobalVariable.listOfFinishedToMe.size() == 0){
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    GlobalVariable.LastID_Finished_ToMe = -1;
                    GlobalVariable.All_FinishedToMe_Retrieved = false;
                    GlobalVariable.finishedAdapterToMe.clear();
                    listViewFinishedToMe.setAdapter(GlobalVariable.finishedAdapterToMe);
                    listViewFinishedToMe.setOnScrollListener(new FinishedEndlessScrollToMe((ActivityMain) getActivity(), GlobalVariable.finishedAdapterToMe));
                    refreshLayout.setRefreshing(false);
                }
            });
        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GlobalVariable.LastID_Finished_ToMe = -1;
                GlobalVariable.All_FinishedToMe_Retrieved = false;
                GlobalVariable.finishedAdapterToMe.clear();
                listViewFinishedToMe.setAdapter(GlobalVariable.finishedAdapterToMe);
                listViewFinishedToMe.setOnScrollListener(new FinishedEndlessScrollToMe((ActivityMain) getActivity(), GlobalVariable.finishedAdapterToMe));
                refreshLayout.setRefreshing(false);
            }
        });

        return view;
    }
}
