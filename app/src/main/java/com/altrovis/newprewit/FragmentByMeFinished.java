package com.altrovis.newprewit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.altrovis.newprewit.Bussines.Unfinished.UnfinishedAdapter;
import com.altrovis.newprewit.Bussines.Unfinished.UnfinishedEndlessScrollByMe;
import com.altrovis.newprewit.Entities.GlobalVariable;

public class FragmentByMeFinished extends Fragment {

    UnfinishedAdapter adapter;
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

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter = new UnfinishedAdapter(getActivity(), R.layout.item_listview_unfinished, GlobalVariable.listOfUnfinishedByMe);
                listViewFinishedByMe.setAdapter(adapter);
                listViewFinishedByMe.setOnScrollListener(new UnfinishedEndlessScrollByMe((ActivityMain) getActivity(), adapter));
                refreshLayout.setRefreshing(false);
            }
        });

        adapter = new UnfinishedAdapter(getActivity(), R.layout.item_listview_unfinished, GlobalVariable.listOfUnfinishedByMe);
        listViewFinishedByMe.setAdapter(adapter);
        listViewFinishedByMe.setOnScrollListener(new UnfinishedEndlessScrollByMe((ActivityMain) getActivity(), adapter));

        return view;
    }
}
