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

public class FragmentByMeUnfinished extends Fragment {

    UnfinishedAdapter adapter;
    ListView listViewUnfinishedByMe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_by_me_unfinished, container, false);

        listViewUnfinishedByMe = (ListView) view.findViewById(R.id.ListViewByMeUnfinished);
        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshLayout);

        adapter = new UnfinishedAdapter(getActivity(), R.layout.item_listview_unfinished, GlobalVariable.listOfUnfinishedByMe);
        listViewUnfinishedByMe.setAdapter(adapter);
        listViewUnfinishedByMe.setOnScrollListener(new UnfinishedEndlessScrollByMe((ActivityMain) getActivity(), adapter));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GlobalVariable.LastID_UnFinished_ByMe = -1;
                adapter.clear();
                adapter = new UnfinishedAdapter(getActivity(), R.layout.item_listview_unfinished, GlobalVariable.listOfUnfinishedByMe);
                listViewUnfinishedByMe.setAdapter(adapter);
                listViewUnfinishedByMe.setOnScrollListener(new UnfinishedEndlessScrollByMe((ActivityMain) getActivity(), adapter));
                refreshLayout.setRefreshing(false);
            }
        });

        listViewUnfinishedByMe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WorkItem workItem = GlobalVariable.listOfUnfinishedByMe.get(position);
                GlobalFunction.showDialog(view, workItem);
            }
        });

        return view;
    }

}
