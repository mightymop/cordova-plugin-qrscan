# Android:

### 1. Add plugin
cordova plugin add https://github.com/mightymop/cordova-plugin-qrscan.git
### 2. For Typescript add following code to main ts file: 
/// &lt;reference types="cordova-plugin-qrscan" /&gt;<br/>
### 3. Usage:
```
window.qrscan.scan(params,succescallback,errorcallback);

params= { timeout : 10000, //millis
		  beep : true,  //beep when qr was found
		  image: true, //save image and give file path back
		  prompt: 'QR-Code scannen' //prompt title in activity
		}

Example result:
result = {
			"SCAN_RESULT":"DATA IN QR CODE", 
			"SCAN_RESULT_FORMAT":"QR_CODE",
			"SCAN_RESULT_ERROR_CORRECTION_LEVEL":"M",
			"SCAN_RESULT_IMAGE_PATH":"/data/user/0/de.mopsdom.test/cache/barcodeimage8337015256730443545.jpg"
		 }
```
