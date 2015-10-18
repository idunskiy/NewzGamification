package mobapply.newzgamification.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mobapply.newzgamification.R;
import mobapply.newzgamification.activities.MainActivity;
import mobapply.newzgamification.library.OnFragmentInteractionListener;

public class ProfileFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private TextView scoreTextView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        scoreTextView = (TextView) view.findViewById(R.id.profile_score);
        updateScore();
        return view;
    }

    public void updateScore() {
        scoreTextView.setText(getString(R.string.text_score, MainActivity.getProfileScore()));
    }

    private MainActivity getMyActivity() {
        return (MainActivity) getActivity();
    }
}
