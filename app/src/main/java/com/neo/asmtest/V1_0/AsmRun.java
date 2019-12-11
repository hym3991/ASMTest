package com.neo.asmtest.V1_0;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 用ASM怎么改
 * 怎么构造需要的类
 * 怎么获取结果
 */
public class AsmRun
{
	public static void main( String[] args )
	{
		redefinePersonClass();
	}
	
	public static void redefinePersonClass(){
		
		/**
		 * 上面看了回调方法的调用流程
		 * 那我们就尝试对类进行修改
		 * 我们知道，如果我们想对方法进行修改，那么我们需要在visitMethod方法中对方法名name进行筛选就可以类
		 * 下面尝试下，通过asm对方法进行修改，打印方法执行耗时
		 *
		 */
		
		String className = AsmTest.class.getName();
		System.out.println( "修改类名:" + className );
		
		try
		{
			InputStream inputStream = new FileInputStream( "/Users/hongyaming/study/ASMTest/app/src/main/java/com/neo/asmtest/V1_0/AsmTest.class" );
			
			//读取.class文件
			ClassReader reader = new ClassReader( inputStream );
			ClassWriter writer = new ClassWriter( reader, ClassWriter.COMPUTE_MAXS);
			//主要类ChangeVisitor
			ClassVisitor visitor = new ChangeVisitor( writer );
			reader.accept( visitor,ClassReader.EXPAND_FRAMES );
			
			//调用查看是否修改
//			Class classz = new MyClassLoader().defineClass( className,writer.toByteArray() );
//			Object pre = classz.newInstance();
//			Method method = classz.getDeclaredMethod( "test",null );
//			method.invoke( pre,null );
			
			//获取结果 输出到新到文件查看
			byte[] code = writer.toByteArray();
			FileOutputStream outputStream = new FileOutputStream( "/Users/hongyaming/study/ASMTest/app/src/main/java/com/neo/asmtest/V1_0/AsmTestOut.class" );
			outputStream.write( code );
			outputStream.close();
			
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
}
