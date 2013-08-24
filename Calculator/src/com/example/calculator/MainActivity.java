package com.example.calculator;

import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText editText = null;

	private NumBtnClickListener numBtnClickListener = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		numBtnClickListener = new NumBtnClickListener();
		editText = (EditText) findViewById(R.id.editText1);

		initBtn();

	}

	private void initBtn() {
		// TODO Auto-generated method stub
		findViewById(R.id.num_0).setOnClickListener(numBtnClickListener);
		findViewById(R.id.num_1).setOnClickListener(numBtnClickListener);
		findViewById(R.id.num_2).setOnClickListener(numBtnClickListener);
		findViewById(R.id.num_3).setOnClickListener(numBtnClickListener);
		findViewById(R.id.num_4).setOnClickListener(numBtnClickListener);
		findViewById(R.id.num_5).setOnClickListener(numBtnClickListener);
		findViewById(R.id.num_6).setOnClickListener(numBtnClickListener);
		findViewById(R.id.num_7).setOnClickListener(numBtnClickListener);
		findViewById(R.id.num_8).setOnClickListener(numBtnClickListener);
		findViewById(R.id.num_9).setOnClickListener(numBtnClickListener);

		findViewById(R.id.point).setOnClickListener(numBtnClickListener);
		findViewById(R.id.op_add).setOnClickListener(numBtnClickListener);
		findViewById(R.id.op_div).setOnClickListener(numBtnClickListener);
		findViewById(R.id.op_mul).setOnClickListener(numBtnClickListener);
		findViewById(R.id.op_sub).setOnClickListener(numBtnClickListener);

		findViewById(R.id.op_equ).setOnClickListener(new EqualBtnClickListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class NumBtnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Button btn = (Button) v;
			String string = editText.getText().toString()
					+ btn.getText().toString();
			editText.setText(string);
			editText.setSelection(string.length());
		}
	}

	public class EqualBtnClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String string=editText.getText().toString();
			
			if (string.equals("")) {
				Toast.makeText(MainActivity.this, "����Ϊ��", Toast.LENGTH_LONG).show();
				return;
			}
			Double result=calculate(string);

			String resultString="";
			if (result%1.0==0.0) {
				resultString=result.longValue()+"";
			}
			else {
				resultString=result+"";
			}
			//�������������ʾС��������0
			
			editText.setText(resultString);
			editText.setSelection(resultString.length());
		}
		
	}
	
	//������ʽ
	private Double calculate(String calcString)
	{
		//˳���ȡ���ֵ��ַ���
		String[]numberStrings=calcString.split("[\\+\\-\\��\\��]");
		
		Vector<String> operators=new Vector<String>();
		Vector<Double> numbersVector=new Vector<Double>();
		//��ֵ�ַ���ת��Double
		for (String string : numberStrings) {
			numbersVector.add(Double.parseDouble(string));
		}
		
		//˳���ȡ������
		for (char object : calcString.toCharArray()) {
			if (object=='+'||object=='-'||object=='��'||object=='��') {
				operators.add(Character.toString(object));
			}
		}
		
		//�ȳ˳�

		for (int i = 0; i < operators.size(); i++) {
			if(operators.get(i).equals("��"))
			{
				numbersVector.set(i,numbersVector.get(i)*numbersVector.get(i+1));//����������䵽numberVector��iλ��
				numbersVector.remove(i+1);//ɾ����i+1���Ѿ�������˵���ֵ
				operators.remove(i);//ɾ����ǰ�Ѿ�������˵������
				i--;//��ǰ������Ѿ�ɾ���������±�λ��
			}
			else if (operators.get(i).equals("��")) {
				numbersVector.set(i,numbersVector.get(i)/numbersVector.get(i+1));
				numbersVector.remove(i+1);
				operators.remove(i);
				i--;
			}
			
		}
		
		//��Ӽ�
		for (int i = 0; i < operators.size(); i++) {
			if (operators.get(i).equals("+")) {
				numbersVector.set(i,numbersVector.get(i)+numbersVector.get(i+1));
				numbersVector.remove(i+1);
				operators.remove(i);
				i--;
			}
			
			else if (operators.get(i).equals("-")) {
				numbersVector.set(i,numbersVector.get(i)-numbersVector.get(i+1));
				numbersVector.remove(i+1);
				operators.remove(i);
				i--;
			}
		}
		return numbersVector.get(0);
	}
}
