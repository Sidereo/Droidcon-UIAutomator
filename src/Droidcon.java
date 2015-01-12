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


        System.err.println("=======================");
        System.out.println(uiDevice.getProductName());
        System.err.println("=======================");

        log = new Log(uiDevice);
        navigator = new Navigator(log);
        viewDumper = new ViewDumper();

        scanHome();
        scanActivityTwo();
        scanActivityThree();
        scanActivityFour();
    }

    private void scanHome() {
        String anchor = "home";
        log.createNewView(anchor);
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
        log.addToLogBuffer(viewDumper.scanView(anchor));
        uiDevice.pressBack();
    }

    private void scanActivityFour() {
        String anchor = "activity #4";
        log.createNewView(anchor);
        navigator.clickAndScanByText("Activity #4", anchor);
        uiDevice.pressBack();
    }
}
