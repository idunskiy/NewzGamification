package mobapply.newzgamification.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.Profile;
import com.facebook.ProfileTracker;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import mobapply.newzgamification.R;
import mobapply.newzgamification.activities.MainActivity;
import mobapply.newzgamification.library.OnFragmentInteractionListener;
import mobapply.newzgamification.network.VolleySingleton;
public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    private OnFragmentInteractionListener mListener;
    private ImageView image_facebook;
    private ProfileTracker mProfileTracker;
    private TextView profile_email;
    private TextView profile_password;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
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

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        image_facebook = (ImageView)view.findViewById(R.id.image_facebook);
        profile_email = (TextView)view.findViewById(R.id.profile_email);
        profile_password = (TextView)view.findViewById(R.id.profile_password);
        scoreTextView = (TextView) view.findViewById(R.id.profile_score);
        updateScore();
        callbackManager = CallbackManager.Factory.create();
//        mProfileTracker = new ProfileTracker() {
//            @Override
//            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
//                profile_email.setText(newProfile.getFirstName());
//                profile_password.setText(newProfile.getLastName());
//                Glide.with(getActivity()).load(newProfile.getProfilePictureUri(100,100)).into(image_facebook);
//            }
//        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // On AccessToken changes fetch the new profile which fires the event on
                // the ProfileTracker if the profile is different
                Profile.fetchProfileForCurrentAccessToken();
            }
        };

        Profile.fetchProfileForCurrentAccessToken();
        setProfile(Profile.getCurrentProfile());

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri.toString());
        }
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


    private void setProfile(Profile profile) {
        profile_email.setText(profile.getFirstName());
        profile_password.setText(profile.getLastName());
        Glide.with(getActivity()).load(profile.getProfilePictureUri(200,200)).into(image_facebook);
    }

    public void updateScore() {
        scoreTextView.setText(getString(R.string.text_score, MainActivity.getProfileScore()));
    }

}
