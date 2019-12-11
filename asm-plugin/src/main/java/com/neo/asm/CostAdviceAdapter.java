package com.neo.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 *
 */
public class CostAdviceAdapter extends AdviceAdapter
{
	private boolean inject = false;
	
	protected CostAdviceAdapter( int api ,
			MethodVisitor methodVisitor ,
			int access ,
			String name ,
			String descriptor )
	{
		super( api , methodVisitor , access , name , descriptor );
	}
	
	@Override
	public AnnotationVisitor visitAnnotation( String descriptor ,
			boolean visible )
	{
		if ( Type.getDescriptor(Cost.class).equals(descriptor)) {
			inject = true;
		}
		return super.visitAnnotation( descriptor , visible );
	}
	
	@Override
	protected void onMethodEnter()
	{
		super.onMethodEnter();
		if( inject ){
			mv.visitMethodInsn( INVOKESTATIC , "java/lang/System" , "currentTimeMillis" , "()J" , false );
			mv.visitVarInsn( LSTORE , 1001 );
		}
	}
	
	@Override
	protected void onMethodExit( int opcode )
	{
		super.onMethodExit( opcode );
		if( inject ){
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
}
