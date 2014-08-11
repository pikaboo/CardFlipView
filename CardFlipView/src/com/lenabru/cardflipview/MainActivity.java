package com.lenabru.cardflipview;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	private boolean showingBack;
	private FragmentLeft left = new FragmentLeft();
	private FragmentRight right = new FragmentRight();
	private Context context;
	private Handler handler;
	private FlipAnimation flipAnimation;
	private FlipAnimation backFlip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = this;
		handler = new Handler(getMainLooper());

		getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, right, "fragmentRight").commit();
		getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, left, "fragmentLeft").commit();
		findViewById(R.id.flip).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flipAnimation = new FlipAnimation(left.getView(), right.getView());
				backFlip = new FlipAnimation(left.getView(), right.getView());
				handler.removeCallbacks(rotate);
				handler.postDelayed(rotate, 100);
			}

		});
	}

	private Runnable rotate = new Runnable() {

		@Override
		public void run() {
			if (!showingBack) {
				left.getView().startAnimation(flipAnimation);
				right.getView().startAnimation(flipAnimation);
				Toast.makeText(context, "flip", Toast.LENGTH_LONG).show();
				showingBack = true;
			} else {
				showingBack = false;
				backFlip.reverse();
				Toast.makeText(context, "backflip", Toast.LENGTH_LONG).show();
				left.getView().startAnimation(backFlip);
				right.getView().startAnimation(backFlip);

			}
		}
	};

}
