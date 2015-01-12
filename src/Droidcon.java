import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import tools.Log;
import tools.Navigator;
import tools.ViewDumper;

public class Droidcon extends UiAutomatorTestCase {

    private UiDevice uiDevice;
    private Navigator navigator;
    private Log log;
    private ViewDumper viewDumper;

    public void testDroidcon() throws UiObjectNotFoundException {

        uiDevice = UiDevice.getInstance();

        log = new Log(uiDevice);
        navigator = new Navigator(log);
        viewDumper = new ViewDumper();

        scanHome();
        scanActivityTwo();
        scanActivityThree();
        scanActivityFour();

        log.printAllLog();

        uiDevice.pressHome();
    }

    private void scanHome() {
        String anchor = "home";
        log.createNewView(anchor);
        navigator.takeScreenshot(anchor);
        log.addToLogBuffer(viewDumper.scanView(anchor));
    }

    private void scanActivityTwo() {
        String anchor = "activity #2";
        log.createNewView(anchor);
        navigator.clickAndScanById("com.sidereo.droidcon:id/main_button_activity_two", anchor);
        uiDevice.pressBack();
    }

    private void scanActivityThree() {
        String anchor = "activity #3";
        log.createNewView(anchor);
        navigator.clickByText("Activity #3");
        navigator.takeScreenshot(anchor);
        log.addToLogBuffer(viewDumper.scanView(anchor));

        anchor = "activity #3 - dialog";
        log.createNewView(anchor);
        navigator.clickById("com.sidereo.droidcon:id/three_show_dialog_button");
        navigator.takeScreenshot(anchor);
        log.addToLogBuffer(viewDumper.scanView(anchor));
        uiDevice.pressBack();

        uiDevice.pressBack();
    }

    private void scanActivityFour() {
        String anchor = "activity #4";
        log.createNewView(anchor);
        navigator.clickAndScanByText("Activity #4", anchor);

        anchor = "activity #4 - menu";
        navigator.clickByIndexAndClass(0, "android.widget.ImageView");
        log.createNewView(anchor);
        log.addToLogBuffer(viewDumper.scanView(anchor));

        scanActivityFive();

        uiDevice.pressBack();
    }

    private void scanActivityFive() {
        String anchor = "activity #5";
        log.createNewView(anchor);
        navigator.clickAndScanByIndexAndClass(0, "android.widget.TextView", anchor);

        clickAndScanOnDialogByIdWithAnchor("activity #5 - dialog #1", "com.sidereo.droidcon:id/three_show_dialog_one");
        clickAndScanOnDialogByIdWithAnchor("activity #5 - dialog #2", "com.sidereo.droidcon:id/three_show_dialog_two");
        clickAndScanOnDialogByIdWithAnchor("activity #5 - dialog #3", "com.sidereo.droidcon:id/three_show_dialog_three");
        clickAndScanOnDialogByIdWithAnchor("activity #5 - dialog #4", "com.sidereo.droidcon:id/three_show_dialog_four");
        clickAndScanOnDialogByIdWithAnchor("activity #5 - dialog #5", "com.sidereo.droidcon:id/three_show_dialog_five");
        clickAndScanOnDialogByIdWithAnchor("activity #5 - dialog #6", "com.sidereo.droidcon:id/three_show_dialog_six");
        clickAndScanOnDialogByIdWithAnchor("activity #5 - dialog #7", "com.sidereo.droidcon:id/three_show_dialog_seven");
        clickAndScanOnDialogByIdWithAnchor("activity #5 - dialog #8", "com.sidereo.droidcon:id/three_show_dialog_height");
        uiDevice.pressBack();
    }

    private void clickAndScanOnDialogByIdWithAnchor(String anchor, String id) {
        log.createNewView(anchor);
        navigator.clickAndScanById(id, anchor);
        uiDevice.pressBack();
    }
}
