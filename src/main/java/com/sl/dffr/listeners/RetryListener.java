package com.sl.dffr.listeners;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class RetryListener implements IAnnotationTransformer {
	@SuppressWarnings("rawtypes")
	public void transform(ITestAnnotation testannotation, Class testClass, Constructor testConstructor,
			Method testMethod) {
		String fileName = "RetryFailedTC";
		String packageName = findFile(fileName + ".java", new File(System.getProperty("user.dir")));

		try {
			testannotation.setRetryAnalyzer(Class.forName(packageName + fileName));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private static String packageName = null;

	private static String findFile(String name, File file) {
		File[] list = file.listFiles();

		if (list != null) {
			for (File fil : list) {
				if (fil.isDirectory()) {
					findFile(name, fil);
				} else if (name.equalsIgnoreCase(fil.getName())) {
					packageName = fil.getParentFile().toString();
					packageName = packageName.substring(packageName.indexOf("com"));
					packageName = packageName.replace("\\", ".");
					packageName = packageName + ".";
				}
			}
		}
		return packageName;
	}
}
