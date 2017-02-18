package com.waspring.wacache.factory.ser;

import java.io.IOException;

public class SerializeUtil {
 
	/**
	 * 序列化
	 * 
	 * @param v
	 * @return
	 * @throws IOException
	 */
	public static byte[] serialize(Object v) throws IOException {
		SerializeTranscoder vr = null;
	 
			vr = new ObjectsTranscoder<java.io.Serializable>();
		 

		return vr.serialize(v);
	}

	/**
	 * 反序列化
	 */

	public static Object unserialize(byte[] buf) throws IOException,
			java.lang.ClassNotFoundException {
		ObjectsTranscoder<java.io.Serializable> vr = new ObjectsTranscoder<java.io.Serializable>();

		return vr.deserialize(buf);
	}

 
}
