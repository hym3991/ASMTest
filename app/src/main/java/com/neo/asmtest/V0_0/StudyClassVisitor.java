package com.neo.asmtest.V0_0;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypePath;

/**
 *
 */
public class StudyClassVisitor extends ClassVisitor
{
	public StudyClassVisitor( ClassVisitor classVisitor )
	{
		super( Opcodes.ASM5 , classVisitor );
	}
	
	@Override
	public void visit( int version ,
			int access ,
			String name ,
			String signature ,
			String superName ,
			String[] interfaces )
	{
		super.visit( version , access , name , signature , superName , interfaces );
		StringBuffer stringBuffer = new StringBuffer(  );
		stringBuffer.append( "\n-- version:"+version );
		stringBuffer.append( "\n-- access:"+access );
		stringBuffer.append( "\n-- name:"+name );
		stringBuffer.append( "\n-- signature:"+signature );
		stringBuffer.append( "\n-- superName:"+superName );
		for( int i = 0 ; i < interfaces.length ; i++ )
		{
			stringBuffer.append( "interfaces:"+interfaces[i] );
		}
		System.out.println( "visit>>>>>>>>>>>>>>>>>>>>>"+stringBuffer.toString() );
		System.out.println( "<<<<<<<<<<<<<<<<<<<<<<<<<<\n" );
	}
	
	@Override
	public void visitSource( String source ,
			String debug )
	{
		super.visitSource( source , debug );
		StringBuffer stringBuffer = new StringBuffer(  );
		stringBuffer.append( "\n-- source:"+source );
		stringBuffer.append( "\n-- debug:"+debug );
		System.out.println( "visitSource>>>>>>>>>>>>"+stringBuffer.toString() );
		System.out.println( "<<<<<<<<<<<<<<<<<<<<<<<<<<\n" );
	}
	
	@Override
	public void visitOuterClass( String owner ,
			String name ,
			String descriptor )
	{
		super.visitOuterClass( owner , name , descriptor );
		StringBuffer stringBuffer = new StringBuffer(  );
		stringBuffer.append( "\n-- owner:"+owner );
		stringBuffer.append( "\n-- name:"+name );
		stringBuffer.append( "\n-- descriptor:"+descriptor );
		System.out.println( "visitOuterClass>>>>>>>>>>>>"+stringBuffer.toString() );
		System.out.println( "<<<<<<<<<<<<<<<<<<<<<<<<<<\n" );
	}
	
	@Override
	public AnnotationVisitor visitAnnotation( String descriptor ,
			boolean visible )
	{
		StringBuffer stringBuffer = new StringBuffer(  );
		stringBuffer.append( "\n-- descriptor:"+descriptor );
		stringBuffer.append( "\n-- visible:"+visible );
		System.out.println( "visitAnnotation>>>>>>>>>>>>"+stringBuffer.toString() );
		System.out.println( "<<<<<<<<<<<<<<<<<<<<<<<<<<\n" );
		return super.visitAnnotation( descriptor , visible );
	}
	
	@Override
	public AnnotationVisitor visitTypeAnnotation( int typeRef ,
			TypePath typePath ,
			String descriptor ,
			boolean visible )
	{
		StringBuffer stringBuffer = new StringBuffer(  );
		stringBuffer.append( "\n-- typeRef:"+typeRef );
		stringBuffer.append( "\n-- typePath:"+typePath.toString() );
		stringBuffer.append( "\n-- descriptor:"+descriptor );
		stringBuffer.append( "\n-- visible:"+visible );
		System.out.println( "visitTypeAnnotation>>>>>>>"+stringBuffer.toString() );
		System.out.println( "<<<<<<<<<<<<<<<<<<<<<<<<<<\n" );
		return super.visitTypeAnnotation( typeRef , typePath , descriptor , visible );
	}
	
	@Override
	public void visitAttribute( Attribute attribute )
	{
		super.visitAttribute( attribute );
		StringBuffer stringBuffer = new StringBuffer(  );
		stringBuffer.append( "\n-- type:"+attribute.type );
		stringBuffer.append( "\n-- isCodeAttribute:"+attribute.isCodeAttribute() );
		System.out.println( "visitAttribute>>>>>>>>>>>>"+stringBuffer.toString() );
		System.out.println( "<<<<<<<<<<<<<<<<<<<<<<<<<<\n" );
	}
	
	@Override
	public void visitInnerClass( String name ,
			String outerName ,
			String innerName ,
			int access )
	{
		super.visitInnerClass( name , outerName , innerName , access );
		StringBuffer stringBuffer = new StringBuffer(  );
		stringBuffer.append( "\n-- name:"+name );
		stringBuffer.append( "\n-- outerName:"+outerName );
		stringBuffer.append( "\n-- innerName:"+innerName );
		stringBuffer.append( "\n-- access:"+access );
		System.out.println( "visitInnerClass>>>>>>>>>>>>"+stringBuffer.toString() );
		System.out.println( "<<<<<<<<<<<<<<<<<<<<<<<<<<\n" );
	}
	
	@Override
	public FieldVisitor visitField( int access ,
			String name ,
			String descriptor , String signature ,
			Object value )
	{
		StringBuffer stringBuffer = new StringBuffer(  );
		stringBuffer.append( "\n-- access:"+access );
		stringBuffer.append( "\n-- name:"+name );
		stringBuffer.append( "\n-- descriptor:"+descriptor );
		stringBuffer.append( "\n-- signature:"+signature );
		stringBuffer.append( "\n-- value:"+value );
		System.out.println( "visitField>>>>>>>>>>>>"+stringBuffer.toString() );
		System.out.println( "<<<<<<<<<<<<<<<<<<<<<<<<<<\n" );
		return super.visitField( access , name , descriptor , signature , value );
	}
	
	@Override
	public MethodVisitor visitMethod( int access ,
			String name ,
			String descriptor ,
			String signature ,
			String[] exceptions )
	{
		StringBuffer stringBuffer = new StringBuffer(  );
		stringBuffer.append( "\n-- access:"+access );
		stringBuffer.append( "\n-- name:"+name );
		stringBuffer.append( "\n-- descriptor:"+descriptor );
		stringBuffer.append( "\n-- signature:"+signature );
		stringBuffer.append( "\n-- exceptions:");
		if( exceptions != null ){
			for( int i = 0 ; i < exceptions.length; i++ )
			{
				stringBuffer.append( exceptions[i] +" ");
			}
		}else {
			stringBuffer.append( "null" );
		}
		System.out.println( "visitMethod>>>>>>>>>>>>"+stringBuffer.toString() );
		System.out.println( "<<<<<<<<<<<<<<<<<<<<<<<<<<\n" );
		return super.visitMethod( access , name , descriptor , signature , exceptions );
	}
	
	@Override
	public void visitEnd()
	{
		super.visitEnd();
		System.out.println( "<<<<<<<<<<<<<<visitEnd>>>>>>>>>>>>" );
		//只会调用一次 是添加属性 方法的最佳时机
	}
}
