package com.neo.asmtest.V1_0;

/**
 *
 */
public class AsmTest
{
	public void test(){
		long startTime = System.currentTimeMillis();
		try
		{
			Thread.sleep( 2 * 1000 );
		}catch( Exception e ){
			e.printStackTrace();
		}
		long time = System.currentTimeMillis() - startTime;
		System.out.println( "cost:"+time );
	}
}
