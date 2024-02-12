package com.hfc.sewing

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class SewingPlugin implements Plugin<Project> {

  @Override void apply(Project project) {
    def hasApp = project.plugins.withType(AppPlugin)
    def hasLib = project.plugins.withType(LibraryPlugin)
    if (!hasApp && !hasLib) {
      throw new IllegalStateException("'android' or 'android-library' plugin required.")
    }

    final def log = project.logger
    final def variants
    if (hasApp) {
      variants = project.android.applicationVariants
    } else {
      variants = project.android.libraryVariants
    }
    // 在 Android Gradle 插件中，构建 APK 的任务通常是 assembleDebug 或 assembleRelease
    // 你可以根据你的构建类型选择合适的任务名
    def assembleTask = project.tasks.findByName('assembleDebug') ?project.tasks.findByName('assembleDebug'): project.tasks.findByName('assembleRelease')

    project.extensions.create('sewing', SewingExtension)
  // register DiffTask
    def makepatchTask =  project.getTasks().register("makepatch", DiffTask.class){
      newFilePath = project.sewing.newFilePath
      oldFilePath = project.sewing.oldFilePath
      patchFilePath = project.sewing.patchFilePath
    }
    //register patchTask & uploadTask
    def patchTask =  project.getTasks().register("patchTask", PatchTask.class)
    def uploadTask =  project.getTasks().register("uploadTask", UploadTask.class)


    // Add makepatchTask to the build lifecycle to ensure execution after compiling APK
    if (project.sewing.enabled){
      project.afterEvaluate {
        makepatchTask.get().doTaskAction()
      }
    }
//    project.getTasks().register("testmakepatch", DiffTask.class){
//      newFilePath = "D:\\workspace2\\githubdownLoad\\Sewing2\\files\\new.apk"
//      oldFilePath = "D:\\workspace2\\githubdownLoad\\Sewing2\\files\\old.apk"
//      patchFilePath = "D:\\workspace2\\githubdownLoad\\Sewing2\\files\\patch.patch"
//      uploadUrl = "http://192.168.84.204/updateTest/"
//    }
    project.repositories {
      google()
      mavenCentral()
      jcenter()
    }
    project.dependencies {
      implementation 'io.sigpipe:jbsdiff:1.0'
    }

    variants.all { variant ->
     if (!project.sewing.enabled) {
        log.debug("Sewing is not disabled.")
        return;
      }
    }

  }
}
