package com.zumania;

import com.zumania.R;
import com.component.CustomImageButton;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ExitDialog extends AlertDialog implements OnClickListener {

	protected ExitDialog(Context context, MyDialogListener listener) {
		super(context);
		this.listener = listener;
	}
	private CustomImageButton exitcancel;
	private CustomImageButton exitok;

	private MyDialogListener listener;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exit_alert);
		exitcancel = (CustomImageButton) findViewById(R.id.exitcancel);
		exitok = (CustomImageButton) findViewById(R.id.exitok);
		exitcancel.setButtonImages(R.drawable.no_button, 0, R.drawable.no_button_pressed, 0);
		exitok.setButtonImages(R.drawable.yes_button, 0, R.drawable.yes_button_pressed, 0);
		exitcancel.setOnClickListener(this);
		exitok.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		
		if(view == exitok){
			listener.onOkClick();
			dismiss();
			if(ZUMAnia.bExitDlg != true)
				ZUMAnia.activity.destroyApp();
			ZUMAnia.bExitDlg = false;
		} else if(view == exitcancel){
			
			listener.onCancelClick();
			dismiss();
			
		}
	}
}
