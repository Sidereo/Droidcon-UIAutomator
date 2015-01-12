package tools;

import android.graphics.Rect;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;

import java.io.File;

public class Navigator {

	private final ViewDumper viewDumper;
    private Log log;

    public Navigator(Log log) {
        this.log = log;
        this.viewDumper = new ViewDumper();
    }

	public void takeScreenshot(String path) {
		final File screenFile = new File("/data/local/tmp/", path + ".png");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		UiDevice.getInstance().takeScreenshot(screenFile);
	}

	public void scrollAndScanAll(String anchor) {
		scrollTopWindow();
		while (scrollInView()) {
			log.addToLogBuffer(viewDumper.scanView(anchor));
		}
		log.addToLogBuffer(viewDumper.scanView(anchor));
	}

	public void scrollTopWindow() {
		try {
			UiScrollable scrollableView = new UiScrollable(new UiSelector().scrollable(true));
			while (scrollableView.scrollBackward());
		} catch (UiObjectNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void clickByText(String text) {
		UiObject object = new UiObject(new UiSelector().text(text));
		clickOnObject(object);
	}

	public void swipeLeft() {
		try {
			UiScrollable scrollableView = new UiScrollable(new UiSelector().scrollable(true));
			scrollableView.setAsHorizontalList();
			scrollableView.scrollBackward();
		} catch (UiObjectNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void swipeRight() {
		try {
			UiScrollable scrollableView = new UiScrollable(new UiSelector().scrollable(true));
			scrollableView.setAsHorizontalList();
			scrollableView.scrollForward();
		} catch (UiObjectNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void clickOnObjectById(String id) {
		UiObject object = new UiObject(new UiSelector().childSelector(new UiSelector().resourceId(id)));
		clickOnObject(object);
	}

	public void clickOnObject(UiObject object) {
		try {
			object.clickAndWaitForNewWindow();
		} catch (UiObjectNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void longClickOnObject(UiObject object) {
		try {
			Rect object_rect = object.getBounds();
			UiDevice.getInstance().swipe(object_rect.centerX(), object_rect.centerY(), object_rect.centerX(), object_rect.centerY(), 100);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (UiObjectNotFoundException e) {
			e.printStackTrace();
		}
	}

    public void forceShowKeyboard() {
        UiObject anEditText = new UiObject(new UiSelector().className("android.widget.EditText").instance(0));
        clickOnObject(anEditText);
    }

    public UiSelector getSelectorById(String id) {
		return new UiSelector().resourceId(id);
	}

	public void clickOnListViewByClassAndInstance(String classView, int instance) {
		clickOnObject(new UiObject(getChildByClass("android.widget.ListView").childSelector(getChildByClass(classView).instance(instance))));
	}

	public void clickAndScanOnListViewByClassAndInstance(String classView, int instance, String path) {
		clickOnListViewByClassAndInstance(classView, instance);
		takeScreenshot(path);
	}

	public UiSelector getChildByClass(String classView) {
		return new UiSelector().className(classView);
	}

	public UiSelector getChildByIndex(int index) {
		return new UiSelector().index(index);
	}

	public UiObject getChildByText(String text) {
		return new UiObject(new UiSelector().text(text));
	}

	public void clickAndScanByText(String text, String path) {
		clickOnObject(getChildByText(text));
		takeScreenshot(path);
		log.addToLogBuffer(viewDumper.scanView(path));
	}

	public void clickOnListViewChildByIndex(int index) {
		UiObject listItem = new UiObject(getChildByClass("android.widget.ListView").childSelector(getChildByIndex(index)));
		clickOnObject(listItem);
	}

	public void clickAndScanOnListViewChildByIndex(int index, String path) {
		clickOnListViewChildByIndex(index);
		takeScreenshot(path);
		log.addToLogBuffer(viewDumper.scanView(path));
	}

	public void longClickOnListViewChildByIndex(int index) {
		UiObject listItem = new UiObject(getChildByClass("android.widget.ListView").childSelector(getChildByIndex(index)));
		longClickOnObject(listItem);
	}

	public void longClickAndScanOnListViewChildByIndex(int index, String path) {
		longClickOnListViewChildByIndex(index);

		takeScreenshot(path);
		log.addToLogBuffer(viewDumper.scanView(path));
	}

	public boolean scrollInView() {
		try {
			UiScrollable scrollableView = new UiScrollable(new UiSelector().scrollable(true));
			if (!scrollableView.scrollForward()) {
				return false;
			}
		} catch (UiObjectNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void clickByIndexAndClass(int index, String viewClass) {
		UiObject object = new UiObject(new UiSelector().className(viewClass).index(index));
		clickOnObject(object);
	}

	public void clickAndScanByIndexAndClass(int index, String viewClass, String path) {
		clickByIndexAndClass(index, viewClass);
		takeScreenshot(path);
        log.addToLogBuffer(viewDumper.scanView(path));
	}

	public void clickById(String id) {
		UiObject object = new UiObject(getSelectorById(id));
		clickOnObject(object);
	}

	public void clickAndScanById(String id, String anchor) {
		clickById(id);
		takeScreenshot(anchor);
        log.addToLogBuffer(viewDumper.scanView(anchor));
	}

	public void clickAndScanByIdAndIndex(String id, int index, String path) {
		UiObject object = new UiObject(new UiSelector().resourceId(id).childSelector(new UiSelector().index(index)));
		clickOnObject(object);
		takeScreenshot(path);
		log.addToLogBuffer(viewDumper.scanView(path));
	}

	public UiObject getObjectByIdAndClass(String id, String classView) {
		return new UiObject(new UiSelector().resourceId(id).childSelector(new UiSelector().className(classView)));
	}

	public UiObject getObjectByIdAndClass(String id, String classView, int instance) {
		return new UiObject(new UiSelector().resourceId(id).childSelector(new UiSelector().className(classView).instance(instance)));
	}
}
