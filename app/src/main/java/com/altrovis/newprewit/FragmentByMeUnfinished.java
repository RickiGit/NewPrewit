package com.altrovis.newprewit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.altrovis.newprewit.Bussines.GlobalFunction;
import com.altrovis.newprewit.Bussines.Unfinished.UnfinishedAdapter;
import com.altrovis.newprewit.Bussines.Unfinished.UnfinishedEndlessScrollByMe;
import com.altrovis.newprewit.Entities.GlobalVariable;
import com.altrovis.newprewit.Entities.WorkItem;

import java.util.ArrayList;

public class FragmentByMeUnfinished extends Fragment {

    ListView listViewUnfinishedByMe;
    SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_by_me_unfinished, container, false);

        listViewUnfinishedByMe = (ListView) view.findViewById(R.id.ListViewByMeUnfinished);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshLayout);

        if (GlobalVariable.listOfWorkItemUnfinishedByMe == null) {
            GlobalVariable.listOfWorkItemUnfinishedByMe = new ArrayList<WorkItem>();
        }

        GlobalVariable.unfinishedAdapterByMe = new UnfinishedAdapter(getActivity(), R.layout.item_listview_unfinished, GlobalVariable.listOfWorkItemUnfinishedByMe);
        listViewUnfinishedByMe.setAdapter(GlobalVariable.unfinishedAdapterByMe);
        listViewUnfinishedByMe.setOnScrollListener(new UnfinishedEndlessScrollByMe((ActivityMain) getActivity(), GlobalVariable.unfinishedAdapterByMe, refreshLayout));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GlobalVariable.LastID_UnFinished_ByMe = -1;
                GlobalVariable.All_UnFinishedByMe_Retrieved = false;
                GlobalVariable.unfinishedAdapterByMe.clear();

                listViewUnfinishedByMe.setAdapter(GlobalVariable.unfinishedAdapterByMe);
                listViewUnfinishedByMe.setOnScrollListener(new UnfinishedEndlessScrollByMe((ActivityMain) getActivity(), GlobalVariable.unfinishedAdapterByMe, refreshLayout));
                refreshLayout.setRefreshing(false);
            }
        });

        listViewUnfinishedByMe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(GlobalVariable.listOfWorkItemUnfinishedByMe.size() != 0){
                    WorkItem workItem = GlobalVariable.listOfWorkItemUnfinishedByMe.get(position);
                    GlobalFunction.showDialog(view, workItem);
                }
            }
        });

        return view;
    }
}
