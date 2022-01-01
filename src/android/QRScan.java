package de.mopsdom.qrscan;

import android.content.Intent;
import android.util.Log;

import com.google.zxing.integration.android.IntentIntegrator;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;


public class QRScan extends CordovaPlugin {

    private final String pluginName = "cordova-plugin-qrscan";
    private CallbackContext callbackContext;
    private final static int REQUEST_CODE = 11010;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("cordova-plugin-qrscan","onActivityResult");

        if(REQUEST_CODE==requestCode)
        {
            PluginResult presult = null;
            if (resultCode == cordova.getActivity().RESULT_OK)
            {
                Log.d("cordova-plugin-qrscan","RESULT_OK");
                try
                {
                     Iterator<String> keys = data.getExtras().keySet().iterator();
                     JSONObject resultjson = new JSONObject();

                     while(keys.hasNext()) {
                            String key = keys.next();

                            try
                            {
                                resultjson.put(key, data.getExtras().get(key));
                            }
                            catch (Exception e)
                            {

                            }
                     }

                     Log.d("cordova-plugin-qrscan","RESULT: "+resultjson.toString());
                     presult = new PluginResult(PluginResult.Status.OK, resultjson.toString());
                     presult.setKeepCallback(true);
                     callbackContext.sendPluginResult(presult);
                }
                catch (Exception e) {
                    Log.e("cordova-plugin-qrscan",e.getMessage());
                    presult = new PluginResult(PluginResult.Status.ERROR, e.getMessage() );
                    presult.setKeepCallback(true);
                    callbackContext.sendPluginResult(presult);
                }

            }

            if (presult==null)
            {
                Log.d("cordova-plugin-qrscan","presult==null");
                Log.e("cordova-plugin-qrscan","RESULT = CANCELD");
                presult = new PluginResult(PluginResult.Status.ERROR, "no data" );
                presult.setKeepCallback(true);
                callbackContext.sendPluginResult(presult);
            }

            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean execute(final String action, final JSONArray data, final CallbackContext callbackContext) {

        if (action.equals("scan")) {
            this.callbackContext=callbackContext;
            try
            {

                long timeout = 10000;
                boolean beep = true;
                boolean image=true;
                String prompt = "QR-Code scannen";

                JSONObject params = data!=null&&data.length()>0&&data.getString(0)!=null?new JSONObject(data.getString(0)):null;
                if (params!=null) {

                    Iterator<String> keys = params.keys();

                    while(keys.hasNext()) {
                        String key = keys.next();

                        if (key.equals("timeout"))
                        {
                            timeout = params.getLong(key);
                        }
                        else
                        if (key.equals("beep"))
                        {
                            beep=params.getBoolean(key);
                        }
                        else
                        if (key.equals("prompt"))
                        {
                            prompt = params.getString(key);
                        }
                        else
                        if (key.equals("image"))
                        {
                            image = params.getBoolean(key);
                        }
                    }
                }

                cordova.setActivityResultCallback(this);
                IntentIntegrator scanQr = new IntentIntegrator(cordova.getActivity());

                scanQr.setTimeout(timeout);
                scanQr.setBeepEnabled(beep);
                scanQr.setPrompt(prompt);
                scanQr.setBarcodeImageEnabled(image);

                Log.d("cordova-plugin-qrscan","Start QR-Code Scanner");
                cordova.startActivityForResult(this, scanQr.createScanIntent(),REQUEST_CODE);

            } catch (Throwable e) {
                Log.e("cordova-plugin-qrscan",e.getMessage(),e);
                callbackContext.error(e.getMessage());
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
