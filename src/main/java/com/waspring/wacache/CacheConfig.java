package com.waspring.wacache;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * 配置管理程序. 默认配置文件 config.properties 插件配置文件 ext/**.properties
 * 
 * @author felly
 * 
 */
public class CacheConfig {
	// /配置读取
	static {
		CacheConfig.load(CacheConfig.class.getClassLoader().getResource(".")
				.getFile());
	}

	public static String rootPath = "";

	/**
	 * 通过key获取value配置值，从\ext\下面的properties文件中获取
	 */

	public static String getConfigValue(String key) {
		return (String) paraContainer.get(key);
	}

	/**
	 * 通过key获取value,允许存在默认值从\ext\下面的properties文件中获取，含默认值
	 */

	public static String getConfigValue(String key, String defaultValue) {

		String s = (String) paraContainer.get(key);
		if (s == null) {
			return defaultValue;
		}
		return s;
	}

	/**
	 * 通过key获取value，从\ext\下面的properties文件中获取，获取配置为int类型的值
	 */

	public static int getConfigIntValue(String key) {
		try {
			return Integer.parseInt(getConfigValue(key));
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 通过key获取value，从\ext\下面的properties文件中获取，获取配置为long类型的值
	 */

	public static long getConfigLongValue(String key) {
		try {
			return Long.parseLong(getConfigValue(key));
		} catch (Exception e) {
			return -1l;
		}
	}

	/**
	 * 通过key获取value，从\ext\下面的properties文件中获取，获取配置为double类型的值
	 */

	public static double getConfigDoubleValue(String key) {
		try {
			return Double.parseDouble(getConfigValue(key));
		} catch (Exception e) {
			return -1d;
		}
	}

	/**
	 * 通过key获取value，从\ext\下面的properties文件中获取，获取配置为float类型的值
	 */
	public static float getConfigFloatValue(String key) {
		try {
			return Float.parseFloat(getConfigValue(key));
		} catch (Exception e) {
			return -1f;
		}
	}

	/**
	 * 通过key获取value，从\ext\下面的properties文件中获取，获取配置为byte类型的值
	 */
	public static byte getConfigByteValue(String key) {
		try {
			return Byte.parseByte(getConfigValue(key));
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 通过key获取value，从\ext\下面的properties文件中获取，获取配置为boolean类型的值
	 */
	public static boolean getConfigBooleanValue(String key) {
		try {
			String value = getConfigValue(key);
			if ("1".equals(value) || "true".equals(value)) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}

		return false;
	}

	// ///////制作一个容器用于做配置
	private static Map paraContainer = new HashMap();

	private static void loadPlugin(String root) throws IOException {
		rootPath = root;
		// ///搜索配置文件下面的全部
		File ext = new File(root + File.separator + "ext");
		if (!ext.exists()) {
			return;
		}

		loadDirProperties(ext);

	}

	/**
	 * 加载配置文件属性到Map
	 * 
	 * @param root
	 */
	private static void loadProperties(File f) throws IOException {
		if (f == null || !f.exists()) {
			return;
		}
		if (f.isDirectory()) {
			return;
		}
		// 加载配置文件
		System.out.println("加载配置文件：" + f.getAbsolutePath());
		Properties prs = new Properties();
		FileInputStream reader = new FileInputStream(f);
		prs.load(reader);
		Set<Map.Entry<Object, Object>> sets = prs.entrySet();

		Iterator<Map.Entry<Object, Object>> its = sets.iterator();

		while (its.hasNext()) {
			Map.Entry<Object, Object> ents = its.next();

			paraContainer.put((String) ents.getKey(), (String) ents.getValue());
		}

		reader.close();
	}

	/**
	 * 如果是目录的话加载目录
	 */
	private static void loadDirProperties(File dir) throws IOException {
		if (dir == null || !dir.exists()) {
			return;
		}
		if (!dir.isDirectory()) {
			loadProperties(dir);
			return;
		}

		File files[] = dir.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				loadDirProperties(files[i]);
			} else {
				loadProperties(files[i]);
			}
		}
	}

	/**
	 * 
	 * @param root
	 */

	public static void load(String root) {// //通用配置

		if (StringUtils.isEmpty(root)) {
			root = "";// //默认空目录
		}
		rootPath = root;
		Properties p = new Properties();
		File f = new File(root + File.separator + "config.properties");
		System.out.println("加载配置文件：" + f.getAbsolutePath());
		if (f.exists()) {
			try {
				FileInputStream reader = new FileInputStream(f);
				p.load(reader);

				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					loadPlugin(root);// ///加载插件配置
				} catch (IOException er) {
					er.printStackTrace();
				}
			}
		} else {
			try {
				loadPlugin(root);// ///加载插件配置
			} catch (IOException er) {
				er.printStackTrace();
			}
		}
	}

}
