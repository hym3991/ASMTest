package com.neo.asmtest.utils;

/**
 *
 */
public class MyClassLoader extends ClassLoader
{
	public MyClassLoader() {
		super(null);
	}
	
	public final Class<?> defineClass(String name, byte[] b) {
		return super.defineClass(name, b, 0, b.length);
	}
}
