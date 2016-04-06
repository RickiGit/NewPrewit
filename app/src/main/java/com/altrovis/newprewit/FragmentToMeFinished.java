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

public class FragmentToMeFinished extends Fragment {

    FinishedAdapter adapter;
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

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter = new FinishedAdapter(getActivity(), R.layout.item_listview_finished, GlobalVariable.listOfFinishedToMe);
                listViewFinishedToMe.setAdapter(adapter);
                listViewFinishedToMe.setOnScrollListener(new FinishedEndlessScrollToMe((ActivityMain) getActivity(), adapter));
                refreshLayout.setRefreshing(false);
            }
        });

        adapter = new FinishedAdapter(getActivity(), R.layout.item_listview_finished, GlobalVariable.listOfFinishedToMe);
        listViewFinishedToMe.setAdapter(adapter);
        listViewFinishedToMe.setOnScrollListener(new FinishedEndlessScrollToMe((ActivityMain) getActivity(), adapter));

        return view;
    }
}
