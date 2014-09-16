package com.jc.b2b;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link DeleteCategory.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link DeleteCategory#newInstance} factory method
 * to create an instance of this fragment.
 * 
 */
public class DeleteCategory extends Fragment {
	

	public DeleteCategory() {
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
		return inflater.inflate(R.layout.fragment_delete_category, container,
				false);
	}

	
	

}
