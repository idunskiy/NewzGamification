package mobapply.newzgamification.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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

import java.util.Collection;
import java.util.Collections;
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
public class FavoriteArticlesFragment extends Fragment implements AbsListView.OnItemClickListener {

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
    private String TAG  =  "FavoriteArticlesFragment";

    // TODO: Rename and change types of parameters
    public static FavoriteArticlesFragment newInstance() {
        FavoriteArticlesFragment fragment = new FavoriteArticlesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FavoriteArticlesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allnews_list, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        NetworkHandler.getNewsList(new Response.Listener<List<NewsItem>>() {
            @Override
            public void onResponse(List<NewsItem> response) {
                if (response != null && !response.isEmpty()) {
                    // TODO write normal logic
                    Collections.shuffle(response);
                    NewsListAdapter adapter = new NewsListAdapter(getActivity(), R.layout.fragment_newsitem, R.id.news_title, response);
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
        Log.d(TAG, "onItemClick() called with: " + "parent = [" + parent
                + "], view = [" + view + "], position = [" + position + "], id = [" + id + "]");
        String url = ((NewsItem) mListView.getAdapter().getItem(position)).getUrl();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
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
