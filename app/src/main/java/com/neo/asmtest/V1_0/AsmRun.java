package com.neo.asmtest.V1_0;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 *
 */
public class AsmRun
{
	public static void main( String[] args )
	{
		redefinePersonClass();
	}
	
	public static void redefinePersonClass(){
		
		String className = AsmTest.class.getName();
		System.out.println( "修改类名:" + className );
		
		try
		{
			//如果是通过gradle插件的形式,可以使用Transform获取路径
			InputStream inputStream = new FileInputStream( "/Users/hongyaming/study/ASMTest/app/src/main/java/com/neo/asmtest/V1_0/AsmTest.class" );
			
			//读取.class文件
			ClassReader reader = new ClassReader( inputStream );
			ClassWriter writer = new ClassWriter( reader, ClassWriter.COMPUTE_MAXS);
			ClassVisitor visitor = new ChangeVisitor( writer );
			reader.accept( visitor,ClassReader.EXPAND_FRAMES );
			
			//调用查看是否修改
			Class classz = new MyClassLoader().defineClass( className,writer.toByteArray() );
			Object pre = classz.newInstance();
			Method method = classz.getDeclaredMethod( "test",null );
			method.invoke( pre,null );
			
			//获取结果 输出到新到文件查看
			byte[] code = writer.toByteArray();
			FileOutputStream outputStream = new FileOutputStream( "/Users/hongyaming/study/ASMTest/app/src/main/java/com/neo/asmtest/V1_0/AsmTestOut.class" );
			outputStream.write( code );
			outputStream.close();
			
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	static public class MyClassLoader extends ClassLoader {
		public MyClassLoader() {
			super(null);
		}
		
		public final Class<?> defineClass(String name, byte[] b) {
			return super.defineClass(name, b, 0, b.length);
		}
	}
}
