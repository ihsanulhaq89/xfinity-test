package com.xfinity.xfinityapp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xfinity.xfinityapp.R;
import com.xfinity.xfinityapp.models.Icon;
import com.xfinity.xfinityapp.models.RelatedTopic;
import com.xfinity.xfinityapp.util.BundleConstants;
import com.xfinity.xfinityapp.util.EventConstants;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageView imageView;
    private TextView description;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FloatingActionButton floatingActionButton;
    private RelatedTopic data;
    private int index;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        imageView = (ImageView) view.findViewById(R.id.image);
        description = (TextView) view.findViewById(R.id.detail);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDetailFragmentInteraction(uri);

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

    public void update(RelatedTopic data, int index) {
        if (data == null) {
            description.setText(getString(R.string.placeholder));
            imageView.setImageResource(R.drawable.placeholder);
            floatingActionButton.setVisibility(View.GONE);
            return;
        }
        this.data = data;
        this.index = index;

        Icon icon = data.getIcon();
        if (icon == null) {
            icon = data.getIconFromDB();
        }
        if (icon != null && !icon.getURL().isEmpty()) {
            Picasso.with(getActivity()).load(icon.getURL())
                    .placeholder(R.drawable.placeholder).into(imageView);

        } else {
            imageView.setImageResource(R.drawable.placeholder);
        }


        description.setText(data.getDescription());
        setFabIcon();

    }

    private void setFabIcon() {
        int resourceId = data.getFavorite() ? R.drawable.ic_action_toggle_star : R.drawable.ic_action_toggle_star_outline;
        floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), resourceId));
        floatingActionButton.setVisibility(View.VISIBLE);
    }

    private void sendMessage() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleConstants.B_DATA, data);
        bundle.putInt(BundleConstants.B_INDEX, index);
        Intent intent = new Intent(EventConstants.BROADCAST_FAVORITE);
        intent.putExtras(bundle);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    @Override
    public void onClick(View view) {
        data.setFavorite(!data.getFavorite());
        setFabIcon();
        data.save();
        sendMessage();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onDetailFragmentInteraction(Uri uri);
    }

}
