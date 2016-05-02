package com.altrovis.newprewit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.altrovis.newprewit.Bussines.GlobalFunction;
import com.altrovis.newprewit.Bussines.Unfinished.UnfinishedAdapter;
import com.altrovis.newprewit.Bussines.Unfinished.UnfinishedAsyncTaskToMe;
import com.altrovis.newprewit.Bussines.Unfinished.UnfinishedEndlessScrollToMe;
import com.altrovis.newprewit.Entities.GlobalVariable;
import com.altrovis.newprewit.Entities.WorkItem;

public class FragmentToMeUnfinished extends Fragment {

    ListView listViewUnfinishedToMe;
    SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_to_me_unfinished, container, false);

        listViewUnfinishedToMe = (ListView) view.findViewById(R.id.ListViewToMeUnfinished);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshLayout);

        // Get List Workitem
        GlobalVariable.unfinishedAdapterToMe = new UnfinishedAdapter(getActivity(), R.layout.item_listview_unfinished, GlobalVariable.listOfWorkItemUnfinishedToMe);
        listViewUnfinishedToMe.setAdapter(GlobalVariable.unfinishedAdapterToMe);
        listViewUnfinishedToMe.setOnScrollListener(new UnfinishedEndlessScrollToMe((ActivityMain) getActivity(), GlobalVariable.unfinishedAdapterToMe));

        // Refresh List Workitem
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GlobalVariable.LastID_UnFinished_ToMe = -1;
                GlobalVariable.All_UnFinishedToMe_Retrieved = false;
                GlobalVariable.unfinishedAdapterToMe.clear();

                listViewUnfinishedToMe.setAdapter(GlobalVariable.unfinishedAdapterToMe);
                listViewUnfinishedToMe.setOnScrollListener(new UnfinishedEndlessScrollToMe((ActivityMain) getActivity(), GlobalVariable.unfinishedAdapterToMe));
                refreshLayout.setRefreshing(false);
            }
        });

        // Get Workitem Listview
        listViewUnfinishedToMe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(GlobalVariable.listOfWorkItemUnfinishedToMe.size() != 0){
                    WorkItem workItem = GlobalVariable.listOfWorkItemUnfinishedToMe.get(position);
                    GlobalFunction.showDialog(view, workItem);
                }
            }
        });

        return view;
    }
}