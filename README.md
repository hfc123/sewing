**English** | [简体中文](README_CN.md)

# loading plugin
...
implementation 'com.github.hfc123.sewing:sewinglib:v1.0.3'
...
...
classpath "com.github.hfc123.sewing:sewing-plugin:v1.0.3"
...
# Function
-Assembly and disassembly of Android split packages
# Using
 ...
 plugins {
 id 'com.android.application'
 id 'sewing'
 }
 ...
 ...
  //Set parameters
  sewing{
  enabled true
  newFilePath "D:\\workspace2\\githubdownLoad\\Sewing2\\files\\new.apk"
  oldFilePath "D:\\workspace2\\githubdownLoad\\Sewing2\\files\\old.apk"
  patchFilePath "D:\\workspace2\\githubdownLoad\\Sewing2\\files\\patch.patch"
  //uploadApkUrl
  }
  //Generate split package
  makepatch{
  newFilePath="D:\\workspace2\\githubdownLoad\\Sewing2\\files\\new.apk"
  oldFilePath="D:\\workspace2\\githubdownLoad\\Sewing2\\files\\old.apk"
  patchFilePath="D:\\workspace2\\githubdownLoad\\Sewing2\\files\\patch.patch"
  }
  //Assembly and disassembly of packages
  patchTask{
  newFilePath2="D:\\workspace2\\githubdownLoad\\Sewing2\\files\\new.apk"
  oldFilePath ="D:\\workspace2\\githubdownLoad\\Sewing2\\files\\old.apk"
  patchFilePath ="D:\\workspace2\\githubdownLoad\\Sewing2\\files\\patch.patch"
  }
  //Upload split package
  uploadTask{
  uploadUrl = "uploadurl"
  uploadApkUrl ="uploadApkUrl"
  patchFilePath = "D:\\workspace2\\githubdownLoad\\Sewing2\\files\\patch.patch"
  }
 ...
-how to patch in app
 use this function
  ...
            String newfilepath;
            String oldfilepath;
            String patchpath;
            PachAsyncTask pachAsyncTask =  new PachAsyncTask(newfilepath,oldfilepath,patchpath);
            pachAsyncTask.setPatchTaskLisener(new PatchTaskLisener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onPatching() {

            }

            @Override
            public void onComplete() {

            }
        });
        pachAsyncTask.execute();
  ...