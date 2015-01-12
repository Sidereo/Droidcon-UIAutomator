package tools;

import android.os.Environment;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ViewDumper extends UiAutomatorTestCase {

    public ViewDumper() {
    }

    private NodeList getNodeList() {
        File xmlFile = dumpWindow();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("node");
            return nodeList;
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private File dumpWindow() {
        final File dumpFolder = new File(Environment.getDataDirectory(), "local/tmp");
        final File dumpFile = new File(dumpFolder, "uidump.xml");


        dumpFolder.mkdirs();

        dumpFile.delete();
        System.err.println(dumpFile.getPath());
        try {
            UiDevice.getInstance().dumpWindowHierarchy("uidump.xml");
        } catch (Exception e) {
            e.printStackTrace();
            dumpFile.delete();
        }
        return dumpFile;
    }

    public ArrayList<String> dumpView(NodeList nodeList) {
        ArrayList<String> dump = new ArrayList<String>();


        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                if (element.getAttribute("text").length() > 0) {
                    dump.add("text:" + element.getAttribute("text"));
                }
            }
        }
        return dump;
    }

    public String getTextByIndexAndClass(int index, String viewClass, NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            if (element.getAttribute("index").equals(Integer.toString(index))
                    && element.getAttribute("class").equals(viewClass)) {
                return element.getAttribute("text");
            }
        }
        return null;
    }

    public ArrayList<String> scanView(String anchor) {
        NodeList nodeList = getNodeList();
        return dumpView(nodeList);
    }
}
