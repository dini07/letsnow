package com.cremamobile.filemanager.service;

public class CremaFileManager {

    private static class LoaderClass {
        private static final CremaFileManager INSTANCE = new CremaFileManager();
    }

    private CremaFileManager() {
//        if (LoaderClass.INSTANCE != null) {
//            throw new IllegalStateException("Already instantiated");
//        }
    }

    public static CremaFileManager getInstance() {
        return LoaderClass.INSTANCE;
    }
    
    
}
