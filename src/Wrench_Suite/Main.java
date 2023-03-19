package Wrench_Suite;

import java.util.ArrayList;
import java.util.List;
import org.testng.TestNG;

public class Main {

	public static void main(String[] args) {
		TestNG testNG = new TestNG();
		List<String> file = new ArrayList<String>();
		file.add("C:\\Selenium\\XML\\Mom.xml");
		testNG.setTestSuites(file);
		testNG.run();

	}

}
