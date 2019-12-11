package com.neo.asmtest.V1_0;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 *
 */
public class ChangeAdapter extends AdviceAdapter
{
	private String methodName = null;
	
	protected ChangeAdapter( int api ,
			MethodVisitor methodVisitor ,
			int access ,
			String name ,
			String descriptor )
	{
		super( api , methodVisitor , access , name , descriptor );
		methodName = name;
	}
	
	private int startTimeId = -1;
	//方法开头织入代码
	@Override
	protected void onMethodEnter()
	{
		/**
		 * 方法访问指令
		 * 访问类型指令的操作码  方法所有者的名称 方法名 方法描述 方法所有者类是否是接口
		 */
		//mv.visitMethodInsn( INVOKESTATIC , "java/lang/System" , "currentTimeMillis" , "()J" , false );
		/**
		 * 访问局部变量
		 * 访问局部变量的操作码 局部变量的索引
		 * var1
		 */
		//mv.visitVarInsn( LSTORE , 1 );
		//上述代码的意思就是将方法返回值赋值给局部保量，让它进行保存
		
		//其他写法
		//构造指定类型的局部变量
		startTimeId = newLocal( Type.LONG_TYPE );
		mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
		/**
		 * 单个操作数int 访问指令
		 * startTimeId
		 */
		mv.visitIntInsn(LSTORE, startTimeId);
		
		mv.visitMethodInsn( INVOKESTATIC , "java/lang/System" , "currentTimeMillis" , "()J" , false );
		mv.visitVarInsn( LSTORE , 1001 );
		
		super.onMethodEnter();
	}
	
	//方法结束织入代码
	@Override
	protected void onMethodExit( int opcode )
	{
		super.onMethodExit( opcode );
		
		//System.currentTimeMillis()
		//mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
		// - var1
		//mv.visitVarInsn(LLOAD, 1);
		// =
		//mv.visitInsn(LSUB);
		//var2
		//mv.visitVarInsn(LSTORE, 2);
		
//		mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//		mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
//		mv.visitInsn(DUP);
//		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
//		mv.visitLdcInsn("== method cost time = ");
//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
//		mv.visitVarInsn(LLOAD, 2);
//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
//		mv.visitLdcInsn(" ==");
//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
		
		mv.visitMethodInsn( INVOKESTATIC , "java/lang/System" , "currentTimeMillis" , "()J" , false );
		mv.visitVarInsn( LLOAD , 1001 );
		mv.visitInsn( LSUB );
		mv.visitVarInsn( LSTORE , 2001 );
		mv.visitFieldInsn( GETSTATIC , "java/lang/System" , "out" , "Ljava/io/PrintStream;" );
		mv.visitTypeInsn( NEW , "java/lang/StringBuilder" );
		mv.visitInsn( DUP );
		mv.visitMethodInsn( INVOKESPECIAL , "java/lang/StringBuilder" , "<init>" , "()V" , false );
		mv.visitLdcInsn( "cost:" );
		mv.visitMethodInsn( INVOKEVIRTUAL , "java/lang/StringBuilder" , "append" , "(Ljava/lang/String;)Ljava/lang/StringBuilder;" , false );
		mv.visitVarInsn( LLOAD , 2001 );
		mv.visitMethodInsn( INVOKEVIRTUAL , "java/lang/StringBuilder" , "append" , "(J)Ljava/lang/StringBuilder;" , false );
		mv.visitMethodInsn( INVOKEVIRTUAL , "java/lang/StringBuilder" , "toString" , "()Ljava/lang/String;" , false );
		mv.visitMethodInsn( INVOKEVIRTUAL , "java/io/PrintStream" , "println" , "(Ljava/lang/String;)V" , false );
		
	}
}
