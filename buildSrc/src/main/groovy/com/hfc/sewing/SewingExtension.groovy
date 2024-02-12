package com.hfc.sewing

class SewingExtension {
   String newFilePath
   String newFilePath2
   String oldFilePath
   String patchFilePath
   String uploadApkUrl

  String getUploadApkUrl() {
    return uploadApkUrl
  }

  void setUploadApkUrl(String uploadApkUrl) {
    this.uploadApkUrl = uploadApkUrl
  }

  def enabled = true

  def setEnabled(boolean enabled) {
    this.enabled = enabled
  }

  def getEnabled() {
    return enabled;
  }

  String getNewFilePath() {
    return newFilePath
  }

  void setNewFilePath(String newFilePath) {
    this.newFilePath = newFilePath
  }

  String getNewFilePath2() {
    return newFilePath2
  }

  void setNewFilePath2(String newFilePath2) {
    this.newFilePath2 = newFilePath2
  }

  String getOldFilePath() {
    return oldFilePath
  }

  void setOldFilePath(String oldFilePath) {
    this.oldFilePath = oldFilePath
  }

  String getPatchFilePath() {
    return patchFilePath
  }

  void setPatchFilePath(String patchFilePath) {
    this.patchFilePath = patchFilePath
  }
}