package com.neo.asmtest.V2_0;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 *
 */
public class PersonVisitor extends ClassVisitor
{
	public PersonVisitor( ClassVisitor classVisitor )
	{
		super( Opcodes.ASM5 , classVisitor );
	}
	
	//有几个方法就回调几次
	@Override
	public MethodVisitor visitMethod( int access ,
			String name ,
			String descriptor ,
			String signature ,
			String[] exceptions )
	{
		if( name.equals( "<init>" ) ){
			//修改类中属性的字节码
			//添加age属性
			visitField( Opcodes.ACC_PUBLIC,"age", Type.getDescriptor(int.class),null,null );
		}
		return super.visitMethod( access , name , descriptor , signature , exceptions );
	}
}
