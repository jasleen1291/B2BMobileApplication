package com.jc.b2b.admin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jc.b2b.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class FinesPending extends Fragment {

	public FinesPending() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_fines_pending, container,
				false);
	}

}
