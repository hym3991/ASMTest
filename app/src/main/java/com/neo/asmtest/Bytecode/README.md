# .class文件解析
 1. java -> class
 2. 各字节意义
 3. 逐个字节解析
 
 
 
 ### java -> class
 使用命令
 ``
    javac Demo.java
 ``  
 生成十六进制符号串 结果在010 Editor或者UE上浏览
 
 使用命令
 ``
    javap -verbose Demo.class 
 ``
 结果：
 ``
 Classfile /Users/hongyaming/study/ASMTest/app/src/main/java/com/neo/asmtest/Bytecode/Demo.class
   Last modified 2019-12-4; size 293 bytes
   MD5 checksum 89e28198f12d72781039f506f286c6f8
   Compiled from "Demo.java"
 public class com.neo.asmtest.Bytecode.Demo
   minor version: 0
   major version: 52
   flags: ACC_PUBLIC, ACC_SUPER
 Constant pool:
    #1 = Methodref          #4.#15         // java/lang/Object."<init>":()V
    #2 = Fieldref           #3.#16         // com/neo/asmtest/Bytecode/Demo.age:I
    #3 = Class              #17            // com/neo/asmtest/Bytecode/Demo
    #4 = Class              #18            // java/lang/Object
    #5 = Utf8               age
    #6 = Utf8               I
    #7 = Utf8               <init>
    #8 = Utf8               ()V
    #9 = Utf8               Code
   #10 = Utf8               LineNumberTable
   #11 = Utf8               getAge
   #12 = Utf8               ()I
   #13 = Utf8               SourceFile
   #14 = Utf8               Demo.java
   #15 = NameAndType        #7:#8          // "<init>":()V
   #16 = NameAndType        #5:#6          // age:I
   #17 = Utf8               com/neo/asmtest/Bytecode/Demo
   #18 = Utf8               java/lang/Object
 {
   public com.neo.asmtest.Bytecode.Demo();
     descriptor: ()V
     flags: ACC_PUBLIC
     Code:
       stack=1, locals=1, args_size=1
          0: aload_0
          1: invokespecial #1                  // Method java/lang/Object."<init>":()V
          4: return
       LineNumberTable:
         line 3: 0
 
   public int getAge();
     descriptor: ()I
     flags: ACC_PUBLIC
     Code:
       stack=1, locals=1, args_size=1
          0: aload_0
          1: getfield      #2                  // Field age:I
          4: ireturn
       LineNumberTable:
         line 8: 0
 }
 SourceFile: "Demo.java"
 
 ``
 class文件结构/如何组成的
 
 ## 解析
 