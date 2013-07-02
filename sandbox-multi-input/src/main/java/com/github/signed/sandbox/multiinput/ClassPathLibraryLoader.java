package com.github.signed.sandbox.multiinput;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class ClassPathLibraryLoader {

    private static final String[] HID_LIB_NAMES = {
	        "/native/linux/libhidapi-jni-64.so",
	        "/native/linux/libhidapi-jni-32.so",
	        "/native/mac/libhidapi-jni-64.jnilib",
	        "/native/mac/libhidapi-jni-32.jnilib",
	        "/native/win/hidapi-jni-64.dll",
	        "/native/win/hidapi-jni-32.dll"
	};


	  
	public static boolean loadNativeHIDLibrary()
        {

		  boolean isHIDLibLoaded = false;
    	  for(String path : HID_LIB_NAMES)
          {
              isHIDLibLoaded = loadLibrary(path);

              if (isHIDLibLoaded) {
		        	break;
		        }
        }
    	  
    	return isHIDLibLoaded;  
    }

    public static void loadLinux32Bit(){
        loadLibrary("/native/linux/libhidapi-jni-32.so");
    }

    public static void loadLinux64Bit(){
        loadLibrary("/native/linux/libhidapi-jni-64.so");
    }

    private static boolean loadLibrary(String path) {
        try {
            File fileOut = moveNativeLibrary(path);
            Runtime.getRuntime().load(fileOut.toString());
            return true;

        } catch (Exception e) {
              return false;
        }
    }

    private static File moveNativeLibrary(String path) throws IOException {
        InputStream in = ClassPathLibraryLoader.class.getResourceAsStream(path);
        if (in != null) {
            try {
                // always write to different location
                String tempName = path.substring(path.lastIndexOf('/') + 1);
                File fileOut = File.createTempFile(tempName.substring(0, tempName.lastIndexOf('.')), tempName.substring(tempName.lastIndexOf('.'), tempName.length()));
                fileOut.deleteOnExit();

                OutputStream out = new FileOutputStream(fileOut);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0){
                    out.write(buf, 0, len);
                }

                out.close();
                return fileOut;

            } finally {
                in.close();
            }
        }

        throw new RuntimeException("could not move native library");
    }

}
