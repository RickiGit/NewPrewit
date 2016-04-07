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

public class FragmentAllFinished extends Fragment {

    FinishedAdapter adapter;
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

        listViewFinishedAll = (ListView)view.findViewById(R.id.ListViewAllFinished);
        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshLayout);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter = new FinishedAdapter(getActivity(), R.layout.item_listview_finished, GlobalVariable.listOfFinished);
                listViewFinishedAll.setAdapter(adapter);
                listViewFinishedAll.setOnScrollListener(new FinishedEndlessScrollAll((ActivityMain) getActivity(), adapter));
                refreshLayout.setRefreshing(false);
            }
        });

        adapter = new FinishedAdapter(getActivity(), R.layout.item_listview_finished, GlobalVariable.listOfFinished);
        listViewFinishedAll.setAdapter(adapter);
        listViewFinishedAll.setOnScrollListener(new FinishedEndlessScrollAll((ActivityMain) getActivity(), adapter));

        return view;
    }
}