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
				Toast.makeText(MainActivity.this, "输入为空", Toast.LENGTH_LONG).show();
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
			//如果是整数则不显示小数点后面的0
			
			editText.setText(resultString);
			editText.setSelection(resultString.length());
		}
		
	}
	
	//计算算式
	private Double calculate(String calcString)
	{
		//顺序获取数字的字符串
		String[]numberStrings=calcString.split("[\\+\\-\\×\\÷]");
		
		Vector<String> operators=new Vector<String>();
		Vector<Double> numbersVector=new Vector<Double>();
		//数值字符串转成Double
		for (String string : numberStrings) {
			numbersVector.add(Double.parseDouble(string));
		}
		
		//顺序获取操作符
		for (char object : calcString.toCharArray()) {
			if (object=='+'||object=='-'||object=='×'||object=='÷') {
				operators.add(Character.toString(object));
			}
		}
		
		//先乘除

		for (int i = 0; i < operators.size(); i++) {
			if(operators.get(i).equals("×"))
			{
				numbersVector.set(i,numbersVector.get(i)*numbersVector.get(i+1));//把运算结果填充到numberVector的i位置
				numbersVector.remove(i+1);//删除第i+1个已经计算过了的数值
				operators.remove(i);//删除当前已经运算过了的运算符
				i--;//当前运算符已经删除，重置下标位置
			}
			else if (operators.get(i).equals("÷")) {
				numbersVector.set(i,numbersVector.get(i)/numbersVector.get(i+1));
				numbersVector.remove(i+1);
				operators.remove(i);
				i--;
			}
			
		}
		
		//后加减
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
