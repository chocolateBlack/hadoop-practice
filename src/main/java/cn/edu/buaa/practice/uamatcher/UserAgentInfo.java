/**
 * 
 */
package cn.edu.buaa.practice.uamatcher;

/**
 * @author BurningIce
 *
 */
public class UserAgentInfo {
	private String osName;				// OS name
	private String osVersionName;		// OS version
	private String browserName;			// browser name
	private String browserVersionName;	// browser version
	private String brandName;			// for mobile devices
	private String modelName;			// for mobile devices
	private boolean mobileDevice;
	private boolean desktopDevice;
	
	/**
	 * 
	 */
	public UserAgentInfo() {

	}
	
	
	/**
	 * @param osName
	 * @param osVersionName
	 * @param browserName
	 * @param browserVersionName
	 * @param brandName
	 * @param modelName
	 */
	public UserAgentInfo(	String osName, String osVersionName, 
							String browserName, String browserVersionName, 
							String brandName, String modelName,
							byte connectType) {
		this.osName = osName;
		this.osVersionName = osVersionName;
		this.browserName = browserName;
		this.browserVersionName = browserVersionName;
		this.brandName = brandName;
		this.modelName = modelName;
		this.mobileDevice = false;
		this.desktopDevice = false;
	}


	/**
	 * @return the osName
	 */
	public String getOsName() {
		return osName;
	}
	/**
	 * @param osName the osName to set
	 */
	public void setOsName(String osName) {
		this.osName = osName;
	}
	/**
	 * @return the osVersionName
	 */
	public String getOsVersionName() {
		return osVersionName;
	}
	/**
	 * @param osVersionName the osVersionName to set
	 */
	public void setOsVersionName(String osVersionName) {
		this.osVersionName = osVersionName;
	}
	/**
	 * @return the browserName
	 */
	public String getBrowserName() {
		return browserName;
	}
	/**
	 * @param browserName the browserName to set
	 */
	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}
	/**
	 * @return the browserVersionName
	 */
	public String getBrowserVersionName() {
		return browserVersionName;
	}
	/**
	 * @param browserVersionName the browserVersionName to set
	 */
	public void setBrowserVersionName(String browserVersionName) {
		this.browserVersionName = browserVersionName;
	}
	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}
	/**
	 * @param brandName the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	/**
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}
	/**
	 * @param modelName the modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * @return the mobileDevice
	 */
	public boolean isMobileDevice() {
		return mobileDevice;
	}


	/**
	 * @param mobileDevice the mobileDevice to set
	 */
	public void setMobileDevice(boolean mobileDevice) {
		this.mobileDevice = mobileDevice;
	}


	/**
	 * @return the desktopDevice
	 */
	public boolean isDesktopDevice() {
		return desktopDevice;
	}


	/**
	 * @param desktopDevice the desktopDevice to set
	 */
	public void setDesktopDevice(boolean desktopDevice) {
		this.desktopDevice = desktopDevice;
	}
}
