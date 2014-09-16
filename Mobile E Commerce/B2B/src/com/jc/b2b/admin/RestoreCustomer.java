package com.jc.b2b.admin;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.jc.b2b.R;
import com.jc.b2b.utils.B2BUtils;
import com.jc.b2b.utils.InsertForm;

public class RestoreCustomer extends Fragment {
	ArrayList<String> email = new ArrayList<String>();

	ArrayList<String> id = new ArrayList<String>();
	ListView lv;
	Button bt;
	CheckedTextView cv;

	ArrayAdapter<String> adapter;

	public RestoreCustomer() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		email.clear();
		id.clear();
		View v= inflater.inflate(R.layout.fragment_restore_customer, container,
				false);
		bt = (Button) v.findViewById(R.id.update);
		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				List<NameValuePair> params = new LinkedList<NameValuePair>();
				params.add(new BasicNameValuePair("status", "activated"));
				boolean status=false;
				for(int i=0;i<lv.getChildCount();i++)
				{
					if(lv.isItemChecked(i))
					{
						status=true;
						params.add(new BasicNameValuePair("id",id.get(i)));
					}
				}
				if(!status)
				{
					Toast.makeText(getActivity(), "No item selected", Toast.LENGTH_LONG)
					.show();
				}
				else
				{
				new InsertForm(getActivity(),params).execute("http://10.0.2.2:8080/B2B/setCustomerStatus.jsp");
				email.clear();

				id.clear();
				List<NameValuePair> param = new LinkedList<NameValuePair>();
				param.add(new BasicNameValuePair("opt", "deac"));
				param.add(new BasicNameValuePair("usertype", "customer"));

				GetDataArray gda = new GetDataArray(getActivity(), param);
				gda.execute("http://10.0.2.2:8080/B2B/getCustomerList.jsp");
				}
			}
		});
		cv = (CheckedTextView) v.findViewById(R.id.checkList);
		cv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (cv.isChecked()) {
					cv.setChecked(false);
					if (lv.getChildCount() > 0) {
						for (int i = 0; i < lv.getChildCount(); i++) {
							lv.setItemChecked(i, false);
						}
					}
				} else {
					cv.setChecked(true);
					if (lv.getChildCount() > 0) {
						for (int i = 0; i < lv.getChildCount(); i++) {
							lv.setItemChecked(i, true);
						}
					}
				}

			}
		});
		lv = (ListView) v.findViewById(R.id.list);
		lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		List<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new BasicNameValuePair("opt", "deac"));
		params.add(new BasicNameValuePair("usertype", "customer"));

		GetDataArray gda = new GetDataArray(getActivity(), params);
		gda.execute("http://10.0.2.2:8080/B2B/getCustomerList.jsp");

		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_multiple_choice, email);
		lv.setAdapter(adapter);

		//
		// lv.getChildAt(0).setSelected(true);


		return v;
	}

	public class GetDataArray extends AsyncTask<String, String, String> {
		ProgressDialog progress;
		Activity activity;
		List<NameValuePair> params;
		JSONArray result;

		public GetDataArray(Activity activity, List<NameValuePair> params) {
			super();
			this.activity = activity;
			this.params = params;
			result = new JSONArray();
		}

		@Override
		protected String doInBackground(String... param) {
			try {

				result = B2BUtils.submitSimpleFormA(param[0], params);
				for (int i = 0; i < result.length(); i++) {
					JSONObject tem = result.getJSONObject(i);
					email.add(tem.getString("email") + "("
							+ tem.getString("username") + ")");
					id.add(tem.getString("masterid"));

				}
			} catch (Exception e) {
				Log.d("Log", e.toString());
			}
			return null;

		}

		@Override
		protected void onPostExecute(String result) {
			if (progress.isShowing()) {
				progress.dismiss();
			}
			adapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			progress = new ProgressDialog(activity);
			progress.setTitle("Fetching data...");
			progress.show();
			super.onPreExecute();
		}

	}

}
