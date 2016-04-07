package com.altrovis.newprewit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.altrovis.newprewit.Bussines.Unfinished.UnfinishedAdapter;
import com.altrovis.newprewit.Bussines.Unfinished.UnfinishedEndlessScrollToMe;
import com.altrovis.newprewit.Entities.GlobalVariable;

public class FragmentToMeUnfinished extends Fragment {

    UnfinishedAdapter adapter;
    ListView listViewUnfinishedToMe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_to_me_unfinished, container, false);

        listViewUnfinishedToMe = (ListView)view.findViewById(R.id.ListViewToMeUnfinished);

        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshLayout);

        adapter = new UnfinishedAdapter(getActivity(), R.layout.item_listview_unfinished, GlobalVariable.listOfUnfinishedToMe);
        listViewUnfinishedToMe.setAdapter(adapter);
        listViewUnfinishedToMe.setOnScrollListener(new UnfinishedEndlessScrollToMe((ActivityMain) getActivity(), adapter));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GlobalVariable.LastID_UnFinished_ToMe = -1;
                adapter.clear();
                adapter = new UnfinishedAdapter(getActivity(), R.layout.item_listview_unfinished, GlobalVariable.listOfUnfinishedToMe);
                listViewUnfinishedToMe.setAdapter(adapter);
                listViewUnfinishedToMe.setOnScrollListener(new UnfinishedEndlessScrollToMe((ActivityMain) getActivity(), adapter));
                refreshLayout.setRefreshing(false);
            }
        });

        return view;
    }
}
