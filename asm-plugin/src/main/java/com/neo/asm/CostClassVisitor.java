package com.neo.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 *
 */
public class CostClassVisitor extends ClassVisitor
{
	public CostClassVisitor( ClassVisitor classVisitor )
	{
		super( Opcodes.ASM5 , classVisitor );
	}
	
	@Override
	public MethodVisitor visitMethod( int access ,
			String name ,
			String descriptor ,
			String signature ,
			String[] exceptions )
	{
		MethodVisitor methodVisitor = super.visitMethod( access , name , descriptor , signature , exceptions );
		return new CostAdviceAdapter(Opcodes.ASM4, methodVisitor, access, name, descriptor);
	}
}
