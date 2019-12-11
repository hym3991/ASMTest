package com.neo.asmtest.V0_0;

import com.neo.asmtest.utils.FileUtils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.objectweb.asm.ClassReader.EXPAND_FRAMES;

/**
 * ASM是什么
 * 有哪些重要类与api
 * 调用的流程是什么
 */
public class AsmRun
{
	public static void main( String[] args )
	{
		String className = Study.class.getName();
		System.out.println( "修改类名:" + className );
		
		/**
		 * 1.什么是ASM
		 * ASM是一款字节码操作与分析的开源框架，同类型的还有AspectJ/javassist。
		 * 可以通过二进制形式（内存）修改已有class或者动态生成class。
		 * 它提供了许多api用于字节码转换构建与分析。
		 * ASM操作基于指令级别，提供了多种修改和分析API，小而快速，强大
		 *
		 * 2.有哪些重要类与api：
		 * ClassReader : 可以方便地让我们对class文件进行读取与解析，解析到某一个结构就会通知到ClassVisitor的相应方法，比如解析到类方法时，就会回调ClassVisitor.visitMethod方法.
		 * ClassVisitor: 可以通过更改ClassVisitor中相应结构方法返回值，实现对类的代码切入，比如更改ClassVisitor.visitMethod()方法的返回值MethodVisitor实例
		 * ClassWriter: 通过toByteArray()方法，得到class文件的字节码内容，最后通过文件流写入方式覆盖掉原先的内容，实现class文件的改写
		 * MethodVisitor & AdviceAdapter: MethodVisitor 是一个抽象类，当 ASM 的 ClassReader 读取到 Method 时就转入MethodVisitor 接口处理。
		 * AdviceAdapter 是 MethodVisitor 的子类，使用 AdviceAdapter 可以更方便的修改方法的字节码
		 *
		 * Log.d( "asm","study" ); -> ASM api的什么样的呢？使用插件asm-bytecode-outlin
		 *
		 * mv.visitLdcInsn( "asm" );
		 * mv.visitLdcInsn( "study" );
		 * mv.visitMethodInsn( INVOKESTATIC , "android/util/Log" , "d" , "(Ljava/lang/String;Ljava/lang/String;)I" , false );
		 * mv.visitInsn( POP );
		 *
		 * 上面的代码翻译成指令集就是
		 * 加载常量"asm"入栈
		 * 加载常量"study"入栈
		 * 执行Log的静态方法d
		 * 方法调用出栈
		 * 也可以通过javap指令看下对应的jvm汇编指令
		 *
		 * 3.调用的流程是什么
		 * ASM通过访问者模式依次遍历class字节码中的各个部分，并不断的通过回调的方式通知上层（这有点像SAX解析xml的过程），上层可在业务关心的某个访问点，修改原有逻辑
		 * 调用顺序看log
		 *
		 * 在Android体系下我们通过Gradle Transform工具，在java代码编译成.class文件之后，.class优化为.dex文件前将代码织入。 使用Transform需要开发一个自定义的gradle plugin
		 * 在一次transform过程中，Gradle会将本地工程中编译的代码、jar包 / aar包 / 依赖的三方库中的代码，作为输入源交由我们的插件处理，这也就是说ASM同样可以对工程外部的类进行修改或织入。
		 * 如果我们需要在指定的类，指定的方法中织入代码，需要编写相应的过滤条件，这也是相比于AspectJ而言不太方便的地方，AspectJ可通过声明切面注解完成精准的织入。
		 *
		 * 根据log得到结果 ClassVisitor 回调流程是根据.class文件按照字节码顺序进行回调，大体遵循
		 * 类基本信息
		 * 变量信息
		 * 方法
		 *
		 * 其中会为将静态常量创建构造方法
		 */
		
		try
		{
			//简单例子
			InputStream inputStream = new FileInputStream( "/Users/hongyaming/study/ASMTest/app/src/main/java/com/neo/asmtest/V0_0/Study.class" );
			
			//创建ClassReader，传入class字节码的输入流
			ClassReader classReader = new ClassReader( inputStream );
			
			//创建ClassWriter，绑定classReader
			ClassWriter classWriter = new ClassWriter( classReader,ClassWriter.COMPUTE_MAXS );
			
			//创建自定义的LifecycleClassVisitor，并绑定classWriter
			ClassVisitor classVisitor = new StudyClassVisitor(classWriter);
			
			//接受一个实现了 ClassVisitor接口的对象实例作为参数，然后依次调用 ClassVisitor接口的各个方法
			classReader.accept(classVisitor, EXPAND_FRAMES);
			
			//toByteArray方法会将最终修改的字节码以 byte 数组形式返回。
			byte[] code = classWriter.toByteArray();
			
			//输出文件到本地
			FileUtils.writeTo( code,"/Users/hongyaming/study/ASMTest/app/src/main/java/com/neo/asmtest/V0_0/Study_Out.class" );
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		
	}
}
