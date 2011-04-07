(function ($) {
	
	// 判断是否是数字
	function validNumber(str){
		var newPar=/^\d+$/ ;
	  	return newPar.test(str);   
	}
	// 判断字符串长度是否为strlength
	function validLength(str,strlength){
		if (str.length==strlength)
			return true;
		return false;
	}
	// 判断字符串长度是否大于等于strlength
	function validBigLength(str,strlength){
		if (str.length>=strlength)
			return true;
		return false;
	}
	// 判断字符串是否包含数字
	function validHasNum(str){
		var s = "0123456789";
		for(var i=0;i<str.length;i++){
			var tempchar = str.substring(i,i+1);
			if(s.indexOf(tempchar)!=-1){
				return true;
			}
		}
		return false;
	}
	// 判断输入的日期是否正确
	function isdate(nYear, nMonth, nDay){
		var str = nYear+'-'+nMonth+'-'+nDay;
		arr=str.split("-");
		if(arr.length==3){
			intYear=parseInt(arr[0],10);
			intMonth=parseInt(arr[1],10);
			intDay=parseInt(arr[2],10);
			
			if(isNaN(intYear) || isNaN(intMonth) || isNaN(intDay)){
				return false;
			}
			if(intYear>2100 || intYear<1900 || intMonth>12 || intMonth<1 || intDay>31 || intDay<1){
				return false;
			}
			if((intMonth==4 || intMonth==6 || intMonth==9 || intMonth==11) && intDay>30){
				return false;
			}
			if((intMonth==1 || intMonth==3 || intMonth==5 || intMonth==7 || intMonth==8 || intMonth==10 || intMonth==12) && intDay>31){
				return false;
			}
			if (intMonth==2){ // 2月判断语句可去掉 修正润年的判断
			    if(new Date(intYear , 2 , 0).getDate() == 29){
			        if(intDay>29)
			          return false;
			    }else{
			        if(intDay>28)
			          return false;
			    }
			}
			return true;
		}
		return false;
	}
	// 判断是否只包含英文字母及数字
	/*function validPassPort(passPort){
		if (passPort=="")
			return true;
		var pattern = /^(\d|[A-Z]|[a-z])+$/;
		var patternnumber = /\d+/;
		var patternchar = /([A-Z]|[a-z])+/;
		var b=pattern.test(passPort);
		if(!b)return b;
			return patternnumber.test(passPort) || patternchar.test(passPort);
	}*/
	
	
	$.Certificate={
			// 验证其他证件
			validateOtherCertificate:function(v){
		var pattern = /^(\d|[A-Z]|[a-z])+$/;
		//var critificate = document.getElementById(otherid).value;
		if(v.length >= 6){
			if(pattern.test(v)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
		},
		//验证其他护照
		validatePassPost:function (v){
			var pattern = /^(([0-9][0-9a-zA-Z]*[a-zA-Z][0-9a-zA-Z]*)|([a-zA-Z][0-9a-zA-Z]*[0-9][0-9a-zA-Z]*))$/;
			//var critificate = document.getElementById(otherid).value;
			if(v.length >= 6){
				if(pattern.test(v)){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		},
		// 验证证件号码 ---- 长度大于6，并且包含数字
		validCardNumber:function (value){
			if(validBigLength(value,6) && validHasNum(value)){
				return true;
			}
			return false;
		},
		// 是否是正确身份证
		validIdCardNo:function (num){
		    var factorArr = new Array(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1);
		    var error;
		    var varArray = new Array();
		    var intValue;
		    var lngProduct = 0;
		    var intCheckDigit;
		    var intStrLen = num.length;
		    var idNumber = num;    
		    // initialize
		    if ((intStrLen != 15) && (intStrLen != 18)) {
		        // error = "输入身份证号码长度不对！";
		        // alert(error);
		        // frmAddUser.txtIDCard.focus();
		        return false;
		    }    
		    // check and set value
		    for(i=0;i<intStrLen;i++) {
		        varArray[i] = idNumber.charAt(i);
		        if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
		            // error = "错误的身份证号码！.";
		            // alert(error);
		            return false;
		        } else if (i < 17) {
		            varArray[i] = varArray[i]*factorArr[i];
		        }
		    }
		    if (intStrLen == 18) {
		        // check date
		        var year_ = idNumber.slice(6,10);
		        var month_ = idNumber.slice(10,12);
		        var day_ = idNumber.slice(12,14);
		        if (isdate(year_,month_,day_) == false) {
		            // error = "身份证中日期信息不正确！.";
		            // alert(error);
		            return false;
		        }        
		        // calculate the sum of the products
		        for(i=0;i<17;i++) {
		            lngProduct = lngProduct + varArray[i];
		        }        
		        // calculate the check digit
		        intCheckDigit = 12 - lngProduct % 11;
		        switch (intCheckDigit) {
		            case 10:
		                intCheckDigit = 'X';
		                break;
		            case 11:
		                intCheckDigit = 0;
		                break;
		            case 12:
		                intCheckDigit = 1;
		                break;
		        }        
		        // check last digit
		        if (varArray[17].toUpperCase() != intCheckDigit) {
		            // error = "身份证效验位错误!...正确为： " + intCheckDigit + ".";
		            // alert(error);
		            return false;
		        }
		    } 
		    else{        // length is 15
		        // check date
		        var year__ = "19"+idNumber.slice(6,8);
		        var month__ = idNumber.slice(8,10);
		        var day__ = idNumber.slice(10,12);
		        if (isdate(year__,month__,day__) == false) {
		            // alert("身份证日期信息有误！.");
		            return false;
		        }
		    }
		    // alert ("Correct.");
		    return true;
		}
	};
	
})(jQuery);

