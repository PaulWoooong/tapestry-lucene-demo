// 根据ID获取对象
function byID(docID){
	return document.getElementById(docID);
}

// 获得焦点
function objectGetFocus(o){
	o.focus();
	setCursorPos(o);
}
// 获得焦点
function docGetFocus(docID){
	byID(docID).focus();
	setCursorPos(byID(docID));
}
// 光标停留在文本最后
function setCursorPos(x){
	var txtRange=x.createTextRange();
	txtRange.moveStart("character",x.value.length);
	txtRange.moveEnd("character",0);
	txtRange.select();
}
// 判断是否是中文
function isChn(str){
	var reg = /^([\u4E00-\u9FA5]|[\uE819-\uE831]|[\uE83C-\uE864])+$/;
	if(!reg.test(str)){
		return false;
	}
	return true;
}
function isKoreaLang(str){
	var reg = /^[\uAC00-\uD7A3]+$/;
	if(!reg.test(str)){
		return false;
	}
	return true;
}
//判断是否是中文
function isZhChn(str){
	var reg = /^([A-Z]|[a-z]|[\u4E00-\u9FA5]|[\uE819-\uE831]|[\uE83C-\uE864])+$/;
	if(!reg.test(str)){
		return false;
	}
	return true;
}

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

// 判断是否是英文
function isEng(str){
	var reg = /^[a-zA-Z]+$/;
	if(!reg.test(str)){
		return false;
	}
	return true;
}
// 判断是否是空格
function isSpace(str){
	var reg = /^\s*$/;
	if(!reg.test(str)){
		return false;
	}
	return true;
}
// 转大写
function chageUpperCase(item){
	item.value=item.value.toUpperCase(); 
}
// 去除空格
String.prototype.trim= function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");  
};
// 第一个是否为英文字母
function firstIsChar(s){
	if(s.length==0){return false;}
	var tempChar = s.substring(0,1);
	if(isEng(tempChar)){
		return true;
	}
	return false;
}
// 根据ID判断是否为空
function isBank(id){
	var obj = byID(id).value;
	if(obj == null || obj == ''){
		return true;
	}
	return false;
}
// 根据ID判断是否不为空
function isNoBank(id){
	var obj = byID(id).value;
	if(obj != null && obj != ''){
		return true;
	}
	return false;
}
// 判断是否为空
function isBankValue(value){
	if(value == null || value.trim() == ''){
		return true;
	}
	return false;
}
// 判断是否不为空
function isNoBankValue(value){
	if(value != null && value != ''){
		return true;
	}
	return false;
}