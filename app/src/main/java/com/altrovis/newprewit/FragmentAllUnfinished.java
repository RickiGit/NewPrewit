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

    UnfinishedAdapter adapter;
    ListView listViewUnfinishedAll;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_unfinished, container, false);

        listViewUnfinishedAll = (ListView) view.findViewById(R.id.ListViewAllUnfinished);
        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshLayout);

        adapter = new UnfinishedAdapter(getActivity(), R.layout.item_listview_unfinished, GlobalVariable.listOfUnfinishedAll);
        listViewUnfinishedAll.setAdapter(adapter);
        listViewUnfinishedAll.setOnScrollListener(new UnfinishedEndlessScrollAll((ActivityMain) getActivity(), adapter));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GlobalVariable.LastID_UnFinished_All = -1;
                GlobalVariable.All_UnFinished_Retrieved = false;
                adapter.clear();
                //adapter = new UnfinishedAdapter(getActivity(), R.layout.item_listview_unfinished, GlobalVariable.listOfUnfinishedAll);
                listViewUnfinishedAll.setAdapter(adapter);
                listViewUnfinishedAll.setOnScrollListener(new UnfinishedEndlessScrollAll((ActivityMain) getActivity(), adapter));
                refreshLayout.setRefreshing(false);

            }
        });

        listViewUnfinishedAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WorkItem workItem = GlobalVariable.listOfUnfinishedAll.get(position);
                GlobalFunction.showDialog(view, workItem);
            }
        });

        return view;
    }

}
