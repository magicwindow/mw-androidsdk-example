package cn.salesuite.mlogcat.helper;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import android.text.TextUtils;

import cn.salesuite.mlogcat.utils.ArrayUtil;
import cn.salesuite.saf.utils.SAFUtils;


/**
 * Helper functions for running processes.
 * @author nolan
 *
 */
public class RuntimeHelper {
    
	/**
	 * Exec the arguments, using root if necessary.
	 * @param args
	 */
	public static Process exec(List<String> args) throws IOException {
		// since JellyBean, sudo is required to read other apps' logs
		if (SAFUtils.isJellyBeanOrHigher()
				&& !SuperUserHelper.isFailedToObtainRoot()) {
			Process process = Runtime.getRuntime().exec("su");
			
			PrintStream outputStream = null;
			try {
				outputStream = new PrintStream(new BufferedOutputStream(process.getOutputStream(), 8192));
				outputStream.println(TextUtils.join(" ", args));
				outputStream.flush();
			} finally {
				if (outputStream != null) {
					outputStream.close();
				}
			}
			
			return process;
		}
		return Runtime.getRuntime().exec(ArrayUtil.toArray(args, String.class));
	}
	
	public static void destroy(Process process) {
	    // if we're in JellyBean, then we need to kill the process as root, which requires all this
	    // extra UnixProcess logic
	    if (SAFUtils.isJellyBeanOrHigher()
	            && !SuperUserHelper.isFailedToObtainRoot()) {
	       SuperUserHelper.destroy(process);
	    } else {
	        process.destroy();
	    }
	}
	
}