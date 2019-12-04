package com.neo.asmtest.V1_0;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 *
 */
public class ChangeVisitor extends ClassVisitor
{
	public ChangeVisitor( ClassVisitor classVisitor )
	{
		super( Opcodes.ASM5 , classVisitor );
	}
	
	@Override
	public MethodVisitor visitMethod(
			int access ,
			String name ,
			String descriptor ,
			String signature ,
			String[] exceptions )
	{
		MethodVisitor methodVisitor = super.visitMethod( access , name , descriptor , signature , exceptions );
		//过滤构造方法 也可以过滤其他方法
		if( name.equals( "<init>" ) ){
			return methodVisitor;
		}
		
		return new ChangeAdapter(Opcodes.ASM4, methodVisitor, access, name, descriptor);
	}
}
