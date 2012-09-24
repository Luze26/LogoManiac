package logomaniac.main;

import java.util.TimerTask;

import android.os.Handler;
import android.widget.TextSwitcher;

public class Util_SwitcherText extends TimerTask {

	private TextSwitcher top_info;
	private String first;
	private String second;
	private boolean state;
	private final Handler handler;
	
	public Util_SwitcherText(TextSwitcher top_info2, String first2, String string) {
		top_info = top_info2;
		first = first2;
		second = string;
		state = true;
		handler = new Handler();
	}

	@Override
	public void run() {
		handler.post(new Runnable() {
			public void run() {
				if(state) {
					top_info.setText(second);
				} else {
					top_info.setText(first);
				}
				state = !state;
			}
		});
	}

	public void setFirst(String first2) {
		first = first2;		
	}

	public void setSecond(String second2) {
		second = second2;
	}
	
}