package com.neo.asm

import com.android.build.api.transform.*
import com.android.build.gradle.AppExtension
import org.apache.commons.codec.digest.DigestUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

class AsmPlugin extends Transform implements Plugin<Project>{

    @Override
    void apply(Project project) {
       project.extensions.getByType(AppExtension).registerTransform(this)
    }

    @Override
    String getName() {
        return "asmplugin"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context,
                   Collection<TransformInput> inputs,
                   Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider,
                   boolean isIncremental) throws IOException, TransformException, InterruptedException {

        println('//=============Begin Asm Visit=================//')
        def startTime = System.currentTimeMillis()
        inputs.each { TransformInput input ->
            input.directoryInputs.each { DirectoryInput directoryInput ->

                //遍历class并被ASM操作
                doAsmChange(directoryInput)

                def dest = outputProvider.getContentLocation(
                        directoryInput.name,
                        directoryInput.contentTypes,
                        directoryInput.scopes,
                        Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)

            }

            input.jarInputs.each { JarInput jarInput ->
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith('jar')){
                    jarName = jarName.substring(0, jarName.length() - 4)
                }

                def dest = outputProvider.getContentLocation(
                        jarName,
                        jarInput.contentTypes,
                        jarInput.scopes,
                        Format.JAR)
                FileUtils.copyFile(jarInput.file, dest)
            }
        }
        def cost = (System.currentTimeMillis() - startTime) / 1000
        println "plugin cost $cost secs"
        println '//=============End Asm Visit=================//'
    }

    static void doAsmChange(DirectoryInput directoryInput){
        if (directoryInput.file.isDirectory()){
            directoryInput.file.eachFileRecurse { File file ->
                def name = file.name
                if (name.endsWith(".class") && !name.startsWith("R\$") &&
                        "R.class" != name && "BuildConfig.class" != name){
                    println name + ' is changing...'

                    ClassReader classReader = new ClassReader(file.bytes)
                    ClassWriter classWriter = new ClassWriter(classReader,ClassWriter.COMPUTE_MAXS)
                    ClassVisitor classVisitor = CostClassVisitor(classWriter)
                    classReader.accept(classVisitor,ClassReader.EXPAND_FRAMES)

                    byte[] code = classWriter.toByteArray()
                    FileOutputStream fos = new FileOutputStream(
                            file.parentFile.absolutePath + File.separator + name)
                    fos.write(code)
                    fos.close()
                }
            }
        }
    }
}