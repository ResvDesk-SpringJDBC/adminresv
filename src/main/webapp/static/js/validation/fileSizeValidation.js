 function validateFileSize(fileFieldId,errorDivId,fieldname,maxFileSize) {
		var fileSize =  -1;
		var allowedMaxFileSize = (maxFileSize * 1024*1024);
		var isValid =  true;
		var file = $('#'+fileFieldId).val();
		if(file!=""){
			isValid = validateFileFormats(file,errorDivId);
			if(isValid) {				
				try{
					fileSize = $("#"+fileFieldId)[0].files[0].size ;
				}catch(e){
					//alert("Exception : "+e);
				}
				if(fileSize==-1 || fileSize>allowedMaxFileSize){
					isValid =  false;
				}
				if(!isValid){
					var errorMessage = "The uploaded file is too large. The allowed file size is limited to "+maxFileSize+"MB.";
					if (fieldname!=undefined) {
						errorMessage = fieldname+" uploaded file is too large. The allowed file size is limited to "+maxFileSize+"MB.";
					}
					$("#"+errorDivId).html(errorMessage);
				}else{
					$("#"+errorDivId).html("");
				}
			}
		}else{
			isValid =  false;
			$("#"+errorDivId).html(fieldname+" can not be empty");
		}
		return isValid;
		//return false;
}


function validateFileFormats(file,errorDivId) {
		var isValid =  false;
	
		var index=file.lastIndexOf(".");
		if(index!=-1){
			var arrayStr = file.split(".");
			var extension = arrayStr[arrayStr.length-1];
			var extensionUpper = extension.toUpperCase();
			if(extensionUpper=="MP3" || extension=="mp3"){
				$("#"+errorDivId).html("");
				isValid =  true;
			}else{
				$("#"+errorDivId).html("Invalid file type, allowed file type is  mp3");
			}
		}else{
			$("#"+errorDivId).html("Invalid file type, allowed file type is  mp3");
		}
		return isValid;
}