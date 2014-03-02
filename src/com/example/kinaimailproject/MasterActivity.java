package com.example.kinaimailproject;

import com.example.kinaimailproject.FormActivity.ButtonClickListener;

import android.os.Bundle;
import android.preference.Preference;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class MasterActivity extends Activity {

	//表示ラベル
	TextView startTime = null;
	TextView endTime = null;
	TextView name1 = null;

	//ぷリファレンスファイル名
	private static final String FILE_NAME = "MasterActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_master);


		//初期値設定

		//プリファレンスオブジェクト取得
		SharedPreferences preference = getSharedPreferences(FILE_NAME, MODE_PRIVATE);

		//初期値設定
		startTime = (TextView)findViewById(R.id.label_startTime);
		startTime.setText(preference.getString("startTime", "20:00"));
		endTime = (TextView)findViewById(R.id.label_endTime);
		endTime.setText(preference.getString("endTime", "21:00"));
//		name1 = (TextView)findViewById(R.id.editText1);
//		name1.setText(preference.getString("name1", ""));



		//--------------------------------------
		//開始時間ダイアログ設定

		//ボタンオブジェクトの取得
		Button dafaultStartButton  = (Button)findViewById(R.id.defaultStartTime);
		//開始時間ボタンに情報をセット
		dafaultStartButton.setTag("dafaultStartButton");
		//リスナーの起動
		dafaultStartButton.setOnClickListener(new ButtonClickListener());


		//--------------------------------------
		//開始時間ダイアログ設定

		//ボタンオブジェクトの取得
		Button dafaultEndButton  = (Button)findViewById(R.id.defaultEndTime);
		//開始時間ボタンに情報をセット
		dafaultEndButton.setTag("dafaultEndButton");
		//リスナーの起動
		dafaultEndButton.setOnClickListener(new ButtonClickListener());

		//--------------------------------------
		//テキスト設定

		//テキストオブジェクトの取得
		EditText name1  = (EditText)findViewById(R.id.editText1);
		//開始時間ボタンに情報をセット
		name1.setTag("name1");
		//デフォルトをセット
		name1.setText(preference.getString("name1", ""));

		//テキストオブジェクトの取得
		EditText name2  = (EditText)findViewById(R.id.editText2);
		//開始時間ボタンに情報をセット
		name2.setTag("name2");
		//デフォルトをセット
		name2.setText(preference.getString("name2", ""));

		//テキストオブジェクトの取得
		EditText name3  = (EditText)findViewById(R.id.editText3);
		//開始時間ボタンに情報をセット
		name3.setTag("name2");
		//デフォルトをセット
		name3.setText(preference.getString("name3", ""));

		//--------------------------------------
		//保存ボタン設定

		//ボタンオブジェクトの取得
		Button saveButton  = (Button)findViewById(R.id.saveButton);
		//開始時間ボタンに情報をセット
		saveButton.setTag("saveButton");
		//リスナーの起動
		saveButton.setOnClickListener(new ButtonClickListener());
	}

	//リスナー定義
	class ButtonClickListener implements OnClickListener{

		//OnClick時イベント
		public void onClick(View v){
			//タグの名称を取得
			String tag = (String)v.getTag();

			if(tag.equals("dafaultStartButton")){
				showStartTimePickerDialog();
			}
			if(tag.equals("dafaultEndButton")){
				showEndTimePickerDialog();
			}
			if(tag.equals("saveButton")){
				existSave();
				goToForm();
			}
		}

	}

	public void goToForm(){
		//インテントの設定
		Intent intent = new Intent(MasterActivity.this, FormActivity.class);

		//次のアクティビティを起動
		startActivity(intent);
	}


	private void existSave(){
//		SharedPreferences sp = getPreferences(MODE_PRIVATE);
//		Editor e = sp.edit();
//		e.commit();
		//ぷリファレンスオブジェクト取得
		SharedPreferences preference = getSharedPreferences(FILE_NAME, MODE_PRIVATE);

		//プリファレンスの変種用オブジェクト取得
		SharedPreferences.Editor editor = preference.edit();

		//開始時間を取得
		TextView label1 = (TextView) findViewById(R.id.label_startTime);
		String str = label1.getText().toString();
		//終了時間を取得
		TextView label2 = (TextView)findViewById(R.id.label_endTime);
		String str2 = label2.getText().toString();
		//メンバー１を取得
		TextView label3 = (TextView)findViewById(R.id.editText1);
		String str3 = label3.getText().toString();
		//メンバー2を取得
		TextView label4 = (TextView)findViewById(R.id.editText2);
		String str4 = label4.getText().toString();
		//メンバー3を取得
		TextView label5 = (TextView)findViewById(R.id.editText3);
		String str5 = label5.getText().toString();


		//プリファレンスファイルに保存
		editor.putString("startTime",str);
		editor.putString("endTime",str2);
		editor.putString("name1",str3);
		editor.putString("name2",str4);
		editor.putString("name3",str5);
	editor.commit();
	}


	//開始時刻ダイアログ
	public void showStartTimePickerDialog(){

		TimePickerDialog dialog = new TimePickerDialog(MasterActivity.this
				, new TimePickerDialog.OnTimeSetListener(){
			public void onTimeSet(TimePicker picker,int hour,int min){
				startTime.setText(hour + ":" + min);
			}
		}
		,0
		,0
		,true
		);
		dialog.show();
	}

	//終了時刻ダイアログ
	public void showEndTimePickerDialog(){

		TimePickerDialog dialog = new TimePickerDialog(MasterActivity.this
				, new TimePickerDialog.OnTimeSetListener(){
			public void onTimeSet(TimePicker picker,int hour,int min){
				endTime.setText(hour + ":" + min);
			}
		}
		,0
		,0
		,true
		);
		dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_master, menu);
		return true;
	}

}
