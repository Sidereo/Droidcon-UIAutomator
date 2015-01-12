package tools;

import com.android.uiautomator.core.UiDevice;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Log {

    private LinkedHashSet<String> logs = new LinkedHashSet<String>();
	private final UiDevice uiDevice;

	public Log(UiDevice uiDevice) {
		this.uiDevice = uiDevice;
	}

	public void createNewView(String path) {
		System.out.println(path.replace("-", "->"));
		System.out.flush();
		logs.add(path);
	}

	public void addToLogBuffer(ArrayList<String> viewLog) {
        logs.addAll(viewLog);
	}

	public void addStringToBuffer(String string) {
		logs.add(string);
	}

	public void printAllLog() {
		System.out.println("========================VIEW TEXTS 1========================");
		System.out.flush();
		for (String log : logs) {
			System.out.println(log);
            System.out.flush();
		}
		System.out.println("========================VIEW TEXTS 2========================");
		System.out.flush();
	}
}
