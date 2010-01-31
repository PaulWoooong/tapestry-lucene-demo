
$(function(){	
		try{
			$('.ymPrompt').each(function (){
				$(this).click(function(){
					var _href = null;
					var _title= null;
					var _handler=null;
					_href = $(this).attr("href1");
					_title = $(this).attr("title");
					_handler=$(this).attr("handler");
					ymPrompt.win({message:_href,iframe:true,width:800,height:400,title:_title,showMask:false,handler:_handler});
					return false;
				});
				
			});
		
		}catch(e){
			alert(e);
		}
		

		

});
