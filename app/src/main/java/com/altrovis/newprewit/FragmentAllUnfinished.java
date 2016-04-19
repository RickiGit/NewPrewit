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
import com.altrovis.newprewit.Bussines.Unfinished.UnfinishedEndlessScrollAll;
import com.altrovis.newprewit.Entities.GlobalVariable;
import com.altrovis.newprewit.Entities.WorkItem;

public class FragmentAllUnfinished extends Fragment {

    ListView listViewUnfinishedAll;
    SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all_unfinished, container, false);

        listViewUnfinishedAll = (ListView) view.findViewById(R.id.ListViewAllUnfinished);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshLayout);

        GlobalVariable.unfinishedAdapterAll = new UnfinishedAdapter(getActivity(), R.layout.item_listview_unfinished, GlobalVariable.listOfUnfinishedAll);
        listViewUnfinishedAll.setAdapter(GlobalVariable.unfinishedAdapterAll);
        listViewUnfinishedAll.setOnScrollListener(new UnfinishedEndlessScrollAll((ActivityMain) getActivity(), GlobalVariable.unfinishedAdapterAll));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GlobalVariable.LastID_UnFinished_All = -1;
                GlobalVariable.All_UnFinished_Retrieved = false;
                GlobalVariable.unfinishedAdapterAll.clear();
                listViewUnfinishedAll.setAdapter(GlobalVariable.unfinishedAdapterAll);
                listViewUnfinishedAll.setOnScrollListener(new UnfinishedEndlessScrollAll((ActivityMain) getActivity(), GlobalVariable.unfinishedAdapterAll));
                refreshLayout.setRefreshing(false);
            }
        });

        listViewUnfinishedAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WorkItem workItem = GlobalVariable.listOfWorkItemUnfinishedAll.get(position);
                GlobalFunction.showDialog(view, workItem);
            }
        });

        return view;
    }
}
