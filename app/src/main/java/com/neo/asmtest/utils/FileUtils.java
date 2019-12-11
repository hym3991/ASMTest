package com.neo.asmtest.utils;

import java.io.FileOutputStream;

/**
 *
 */
public class FileUtils
{
	public static void writeTo(byte[] bytes,String filePath){
		try
		{
			FileOutputStream outputStream = new FileOutputStream( "/Users/hongyaming/study/ASMTest/app/src/main/java/com/neo/asmtest/V2_0/PersonOut.class" );
			outputStream.write( bytes );
			outputStream.close();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
}
