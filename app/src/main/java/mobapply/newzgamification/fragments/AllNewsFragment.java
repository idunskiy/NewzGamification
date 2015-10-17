package mobapply.newzgamification.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.android.volley.Response;

import java.util.List;

import mobapply.newzgamification.R;
import mobapply.newzgamification.adapters.NewsListAdapter;
import mobapply.newzgamification.fragments.dummy.DummyContent;
import mobapply.newzgamification.library.OnFragmentInteractionListener;
import mobapply.newzgamification.model.NewsItem;
import mobapply.newzgamification.network.NetworkHandler;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class AllNewsFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;
    private String TAG = "AllNewsFragment";

    // TODO: Rename and change types of parameters
    public static AllNewsFragment newInstance() {
        AllNewsFragment fragment = new AllNewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AllNewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: Change Adapter to display your content
        mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allnews_list, container, false);
        view.setBackgroundColor(0xFFFF0000);
        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        NetworkHandler.getNewsList(new Response.Listener<List<NewsItem>>() {
            @Override
            public void onResponse(List<NewsItem> response) {
                Log.i(TAG + " tag", response.toString());
                if (response != null && !response.isEmpty()) {
                    NewsListAdapter adapter = new NewsListAdapter(getActivity(), R.layout.fragment_newsitem, R.id.news_title, response);
                    Log.i(TAG + " tag", adapter.toString());
                    Log.i(TAG + " tag",Integer.toString(adapter.getCount()));
                    mListView.setAdapter(adapter);
                }
            }
        }, getActivity());
        mListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }



}
