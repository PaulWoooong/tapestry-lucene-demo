function ajaxauto(el){
	new Ajax.Autocompleter(el,'auto_component', autoc_url,{"paramName":"search","minChars":2});
}

function addTelTr(){
var psgtable=document.getElementById("customerteltable");
var startindex=0;
while(true){
	var tr=document.getElementById("teltr"+startindex);
	if(tr!=null){
	 startindex++;
	}else{
	 break;
	}
}
if(startindex>50){
    alert('记录不能大于50条!');
    return;
}
var tr=psgtable.insertRow(-1);
tr.id="teltr"+startindex;
tr.name="teltr"+startindex;
tr.setAttribute("align","center");
//tr.bgColor="#f7f7f7";

var td=tr.insertCell(-1);
var inp=createTelInput('acct_'+startindex,'');
td.appendChild(inp);
ajaxauto(inp);
td=tr.insertCell(-1);
inp=addTelSelectOptions('direct_'+startindex);
td.appendChild(inp);

td=tr.insertCell(-1);
inp=createTelInput('amt_'+startindex,'',6);
td.appendChild(inp);

td=tr.insertCell(-1);
inp=createTelInput('cmp_'+startindex,'',8);
td.appendChild(inp);


td=tr.insertCell(-1);
inp=addtelbutton('删除','teltr'+startindex);
td.appendChild(inp);
}
function delTelTr(trid){
    var psgtable=document.getElementById("customerteltable");
    var teltr=document.getElementById(trid);
    psgtable.deleteRow(teltr.rowIndex);
}
function addtelbutton(value,name) {
	var delbutton = document.createElement("button");
	delbutton.appendChild(document.createTextNode(value));
	delbutton.className="button";
    delbutton.onclick=new Function("delTelTr('"+ name+"')");
	return delbutton;
}
function createTelInput(name,value,sizes){
	var inp= document.createElement("input");
	inp.setAttribute("name" ,name);
	inp.setAttribute("id",name);
	inp.setAttribute("type","text");
	inp.setAttribute("value",value);
	inp.setAttribute("size",sizes);
	inp.className='txt';
	return inp;
	}
function addTelSelectOptions(name){
    var inp=document.createElement("select");
    inp.setAttribute("name",name);
	inp.setAttribute("id",name);
	var selectvalue=new Array('DEBIT','CREDIT');
	var selectdisp=new Array('借','贷');
	 for(i=0;i<2;i++){
		op=document.createElement("option");
		op.setAttribute("value",selectvalue[i]);
		text= document.createTextNode(selectdisp[i]);
		op.appendChild(text);
		inp.appendChild(op);
	 }
	 return inp;
}