package ro.pub.cs.systems.pdsd.lab04.contactsmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.SyncStateContract.Constants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Fragment1 extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
		return layoutInflater.inflate(R.layout.fragment_contacts_manager, viewGroup, false);
	}
	
	private ButtonOnClickListener buttonOnClickListener = new ButtonOnClickListener();

	private class ButtonOnClickListener implements View.OnClickListener{
		@Override
		public void onClick(View v){
			switch(v.getId()) {
			case R.id.b_show:
				FragmentManager fragmentManager = getActivity().getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				Fragment2 additionalDetailsFragment = (Fragment2)fragmentManager.findFragmentById(R.id.frame2);
				if (additionalDetailsFragment == null) {
				  fragmentTransaction.add(R.id.frame2, new Fragment2());
				  ((Button)v).setText(getActivity().getResources().getString(R.string.hide_additional_fields));
				  fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
				} else {
				  fragmentTransaction.remove(additionalDetailsFragment);
				  ((Button)v).setText(getActivity().getResources().getString(R.string.show_additional_fields));
				  fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
				}
				fragmentTransaction.commit();
				break;
			case R.id.b_save:
				Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
				intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
				EditText et1 = (EditText)getActivity().findViewById(R.id.edit_name);
				String name = et1.getText().toString();
				EditText et2 = (EditText)getActivity().findViewById(R.id.edit_phone);
				String phone = et2.getText().toString();
				if (name != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
				}
				if (phone != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
				}
				ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
				intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
				getActivity().startActivityForResult(intent, 1);
				break;
				
				
			case R.id.b_cancel:
				getActivity().setResult(Activity.RESULT_CANCELED, new Intent());
				getActivity().finish();
				break;
			}
		}
		
	}	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			Button additionalDetailsButton = (Button)getActivity().findViewById(R.id.b_show);
			additionalDetailsButton.setOnClickListener(buttonOnClickListener);
			Button saveButton = (Button)getActivity().findViewById(R.id.b_save);
			saveButton.setOnClickListener(buttonOnClickListener);
			Button cancelButton = (Button)getActivity().findViewById(R.id.b_cancel);
			cancelButton.setOnClickListener(buttonOnClickListener);
			EditText phoneEditText = (EditText)getActivity().findViewById(R.id.edit_phone);
			Intent intent = getActivity().getIntent();
			if (intent != null) {
				String phone = intent.getStringExtra("tel");
				if (phone != null) {
					phoneEditText.setText(phone);
				} else {
					Activity activity = getActivity();
					Toast.makeText(activity, "eroare hardocadata", Toast.LENGTH_LONG).show();
				}
			}
	}
}

