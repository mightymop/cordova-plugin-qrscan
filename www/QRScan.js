var exec = require('cordova/exec');
var PLUGIN_NAME = 'QRScan';

var qrscan = {

	scan : function (params,success, error ) {
		exec(success, error, 'QRScan', 'scan', [params]);
	}
};

module.exports = qrscan;
