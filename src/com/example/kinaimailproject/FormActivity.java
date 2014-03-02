package com.example.kinaimailproject;

import java.util.Calendar;

import com.example.kinaimailproject.common.SendMail;
import com.example.kinaimailproject.common.Utils;
import com.example.kinaimailproject.dto.SendMailDto;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FormActivity extends Activity {

	//表示ラベル
	//日付
	TextView date = null;
	//開始時間
	TextView startLabel = null;
	//終了時間
	TextView endLabel = null;
	//メンバー（各チェックボックスの横のラベル）
	CheckBox name1 = null;
	CheckBox name2 = null;
	CheckBox name3 = null;

	Button dateButton = null;

	SendMailDto sendMailDto;

	//プリファレンスファイル名
	private static final String FILE_NAME = "MasterActivity";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form);

		//---------------------------------
		//画面初期表示の設定

		//プリファレンスオブジェクトの取得
		SharedPreferences preference = getSharedPreferences(FILE_NAME, MODE_PRIVATE);

		//初期値設定
		initialize(preference);

		//ボタンは名前が登録されていない場合は非表示

		disabledCheckBox(name1,preference.getString("name1", ""));
		disabledCheckBox(name2,preference.getString("name2", ""));
		disabledCheckBox(name3,preference.getString("name3", ""));
		//--------------------------------------
		//各ボタンのイベント定義
		//--------------------------------------

		//--------------------------------------
		//日付選択ボタン

		//ボタンオブジェクトの取得
		dateButton  = (Button)findViewById(R.id.dateButton);
		//日付ボタンに情報をセット
		dateButton.setTag("dateButton");
		//リスナーの起動
		dateButton.setOnClickListener(new ButtonClickListener());

		//---------------------------------------
		//マスタボタン

		//マスタボタンオブジェクトの取得
		Button masterButton = (Button)findViewById(R.id.masterButton);
		//タグに情報をセット
		masterButton.setTag("masterButton");
		//リスナーの起動
		masterButton.setOnClickListener(new ButtonClickListener());



		//------------------------------------------
		//追加ボタン

		//追加ボタンオブジェクトの取得
		Button addButton = (Button)findViewById(R.id.addButton);
		//タグに情報をセット
		addButton.setTag("addButton");
		//リスナー起動
		addButton.setOnClickListener(new ButtonClickListener());

		//---------------------------------------
		//チェックボックスイベント（ここだけ書き方変えてるよ）

		//チェックボックスの取得
		final CheckBox onOffCheckBox = (CheckBox)findViewById(R.id.checkBox4);
		//リスナーの起動
		onOffCheckBox.setOnClickListener(new View.OnClickListener() {
			//チェックぼっくすがクリックされた時のハンドラ
			@Override
			public void onClick(View v) {
				if(onOffCheckBox.isChecked()){

					name1.setChecked(true);
					name2.setChecked(true);
					name3.setChecked(true);
				}else{
					name1.setChecked(false);
					name2.setChecked(false);
					name3.setChecked(false);
				}

			}
		});





		//---------------------------------------
		//メール送信

		//メール送信ボタンオブジェクトの取得
		Button sendMailButton = (Button)findViewById(R.id.sendMailButton);
		//タグに情報をセット
		sendMailButton.setTag("sendMailButton");
		//リスナーの起動
		sendMailButton.setOnClickListener(new ButtonClickListener());

	}

	//ボタンリスナー定義
	class ButtonClickListener implements OnClickListener{

		//OnClick時イベント
		public void onClick(View v){
			//タグの名称を取得
			String tag = (String)v.getTag();

			if(tag.equals("dateButton")){
				showDatePickerDialog();
			}
			if(tag.equals("masterButton")){
				goToMasterPage();
			}
			if(tag.equals("sendMailButton")){
				confirmSendMail();
			}
			if(tag.equals("addButton")){
				addForm(v);
			}
		}

	}

	public void goToMasterPage(){
		//インテントの設定
		Intent intent = new Intent(FormActivity.this, MasterActivity.class);

		//次のアクティビティを起動
		startActivity(intent);
	}

	//TODO カレンダー取得メソッド(ダイアログ系は後で別クラスにしようか）
	public void showDatePickerDialog(){
		//カレンダー取得
		Calendar cal = Calendar.getInstance();

		//ダイアログの取得
		DatePickerDialog dialog = new DatePickerDialog(FormActivity.this,
				new DatePickerDialog.OnDateSetListener(){
					public void onDateSet(DatePicker datePicker,
							int year,int month,int day){
						date.setText(year+"/"+month+"/"+day);
						//TODO テスト
						dateButton.setText(year+"/"+month+"/"+day);
					}
		}
		, cal.get(Calendar.YEAR)
		, cal.get(Calendar.MONTH)
		, cal.get(Calendar.DAY_OF_MONTH)
		);
		dialog.show();
	}


	//フォームの追加
	private void addForm(View v){

		LinearLayout layout  = (LinearLayout)findViewById(R.id.linearLayout2_2);

		TextView newText = (TextView)getLayoutInflater().inflate(R.layout.sub_form, null);
		layout.addView(newText);

		setContentView(layout);

	}




	//メール送信確認ダイアログ
	private void confirmSendMail(){

		//ダイアログの取得
		AlertDialog.Builder dialog =
			new AlertDialog.Builder(FormActivity.this);

		//メッセージの設定
		dialog.setTitle("メール送信確認");
		dialog.setMessage("メールを送信しますか");

		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				//TODO メール送信処理

				//Dtoに値をセット
				sendMailDto = setDto();

				//メール送信
				SendMail sendMail = new SendMail();
				sendMail.sendMail(sendMailDto);

				//トーストにて送信済み

			}

		});

		dialog.setNegativeButton("NG", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 何もしない

			}
			//なにもしない
		});
		dialog.show();
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_form, menu);
		return true;
	}





	private void initialize(SharedPreferences preference){

		//日付の取得
		date = (TextView)findViewById(R.id.label_dialogtext);
		date.setText(Utils.getStrToday());

		//開始時間の取得
		startLabel = (TextView)findViewById(R.id.label_startTime);
		startLabel.setText(preference.getString("startTime", "ねえし"));

		//終了時間の取得
		endLabel = (TextView)findViewById(R.id.label_endTime);
		endLabel.setText(preference.getString("endTime", "ねえし"));

		//名前１の取得
		name1 =  (CheckBox)findViewById(R.id.checkBox1);
		name1.setText(preference.getString("name1", ""));

		//名前２の取得
		name2 =  (CheckBox)findViewById(R.id.checkBox2);
		name2.setText(preference.getString("name2", ""));

		//名前３の取得
		name3 =  (CheckBox)findViewById(R.id.checkBox3);
		name3.setText(preference.getString("name3", ""));
	}


	//------------------------------------------------------
	//名前のないチェックボックスを非表示にする
	//------------------------------------------------------


	private void disabledCheckBox(CheckBox checkBox, String str){

		if(Utils.isEmpty(str)){
			checkBox.setVisibility(CheckBox.INVISIBLE);
		}
	}

	//Dtoに値をセット
	private SendMailDto setDto(){

		SendMailDto sendMailDto = new SendMailDto();
		//日付
		sendMailDto.setDate(dateButton.getText().toString());

		return sendMailDto;
	}




}