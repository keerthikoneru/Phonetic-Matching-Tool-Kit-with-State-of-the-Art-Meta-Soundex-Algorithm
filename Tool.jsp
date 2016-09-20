<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>GETTING STARTED WITH PHONETIC MATCHING</title>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Merriweather"  type="text/css">
        <link href='https://fonts.googleapis.com/css?family=Courgette' rel='stylesheet' type='text/css'>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="css/bootstrap/js/bootstrap.min.js"></script>
        <script>
        $(document).ready(function() {
        	var engAlg = ["Caverphone", "DMetaphone", "Metaphone","MetaSoundex","NYSIIS", "Soundex"];
        	var spaAlg = ["Metaphone","MetaSoundex","Soundex"];
        	$("#ddlang").append('<option value="English">English</option>');
        	$("#ddlang").append('<option value="Spanish">Spanish</option>');
        	if( $("#ddlsug").find('option').length <= 1){
        		$("#ddlsug").hide();
        	}
        	if( $("#ddlmisspell").find('option').length > 1){
        		$("#lblmsg").show();
        		var input = $("#inputtext").val();
        		$("#ddlmisspell").find('option').each(function() {
        			//input = input.replace($(this).val(),'<del>'+$(this).val()+'</del>');
        		});
        		
        		 $("#inputtext").val(input);
        	}
        	  $("#ddlang").change(function() {

        	    var lang = $(this) ;

        	    if(lang.val() === "English" ) {
        	    	$("#ddlalg").find('option').remove();
        	    	$("#ddlalg").append('<option value="Select">-Select Algorithm-</option>');
        	    	for(i=0; i < engAlg.length; i++){
		                $("#ddlalg").append('<option value="'+engAlg[i]+'">'+engAlg[i]+'</option>');
		            }
        	    	
        	    }
        	      else if(lang.val() === "Spanish" ) {
        	    	  $("#ddlalg").find('option').remove();
          	    	$("#ddlalg").append('<option value="Select">-Select Algorithm-</option>');
          	    	for(i=0; i < spaAlg.length; i++){
  		                $("#ddlalg").append('<option value="'+spaAlg[i]+'">'+spaAlg[i]+'</option>');
  		            }
        	      }
        	  });

        	});

            function validate(){
            	if(document.getElementById("inputtext").value == ""){
            		alert("Please enter the text");
            	}
            	else if(document.getElementById('ddlang').value == "Select")
            	{
            		alert("Please Select the language");
            	}
            	else{
            		document.getElementById("formsubmit").submit();
            	}
            }
            
            function startDictation() {
            	 
                if (window.hasOwnProperty('webkitSpeechRecognition')) {
             
                  var recognition = new webkitSpeechRecognition();
             
                  recognition.continuous = false;
                  recognition.interimResults = false;
             
                  recognition.lang = "en-US";
                  recognition.start();
             
                  recognition.onresult = function(e) {
                    document.getElementById('inputtext').value
                                             = e.results[0][0].transcript;
                    recognition.stop();
                  };
             
                  recognition.onerror = function(e) {
                    recognition.stop();
                  }
             
                }
              }
        </script>
    </head>
    <body>
        <div class="container-fluid">
            <nav class="navbar">
                <div id="row">
                        <div  class="col-lg-2">
                            <a href="Index.html">
                                <img class="logo" src="css/home1.jpg"/>
                            </a>
                        </div>
                        <div id="title" align ="center" class="col-lg-8">
                                <h1>PHONETIC MATCHING</h1>
                        </div>
                        <div class="visible-md visible-lg col-lg-2">
                            <img class="logo" align="right" src="css/Image2.jpg"/>
                        </div>
                </div>
            </nav>
        </div>
        <form method="post" id="formsubmit" enctype="multipart/form-data" action="SuggestionServlet">
	        <div id="row">
	        	<div class="col-lg-12">
	                <div class="col-lg-6">
	                    <div id="row">
		                    <div class="col-lg-12">
	                            <div class="col-lg-12">
	                            	<div class="col-lg-12">
		                                <div class="col-lg-3">
			                    		<div><p id="textp">Enter Input Text :</p></div>
		                                </div>
		                                <div class="col-lg-9">
		                        		<div><textarea name="inputtext" spellcheck="false" id = "inputtext">${input}</textarea></div>
		                                </div>
	                                </div>
	                                 <div class="col-lg-12">
	                     			</div>
	                     			 <div class="col-lg-12">
	                     			</div>
	                            <div class="col-lg-12">
	                                <div class="col-lg-6">
	                                    <p id="textp">Language: </p>
	                                    <select name="language" id="ddlang">
	                                        <option value="Select">-Select language-</option>
	                                    </select>
	                                </div>
	                                <div class="col-lg-6" style="padding-top: 40px;">
	                                    <input id="btn" type="submit" onclick="validate()" name="btncheck" value="Spell Check">
	                                    <!-- <img onclick="startDictation()" src="//i.imgur.com/cHidSVu.gif" /> -->
	                                </div>
	                            </div>
	                            <div class="col-lg-12">
	                            	<label id="lblmsg" style="display:none; padding: 15px;">Select one of the misspelled words to retrieve suggestions</label>
	                            </div>
	                            <div class="col-lg-12">
		                               <div class="col-lg-6">
		                                  <p id="textp">Misspelled Words: </p>
		                                   <select name="misspelled" id="ddlmisspell">
	    									<option value="Select">-Select Misspelled Word-</option>
	    										<c:forEach var="word" items="${misspell}">
	      											<option value="${word}">${word}</option>
	    										</c:forEach>
											</select>
		                                </div>
	                                <div class="col-lg-6">
	                                    <p id="textp">Algorithm: </p>
	                                    <select name="algorithms" id="ddlalg">
	                                        <option value="Select">-Select Algorithm-</option>
	                                    </select>
	                                </div>
	                            </div>
	                            <div class="col-lg-12" style="padding-top: 20px;">
	                            	<input id="btn" type="submit" name="btnsugges" value="Retrieve Suggestions">
	                            </div>
		                     </div>
	                	</div>
	            	</div>
	            	</div>
	            	<div class="col-lg-6">
	            		<div id="row">
	            		 	<div class="col-lg-12">
	            		 		<table>
	            		 			<tr>
	            		 				<th style="color: crimson;" colspan=2>${message}</th>
	            		 			</tr>
									<tr>
										<th>${message1}</th>
   										<td>${misword}</td>
									</tr>
									<tr>
										<th>${message2}</th>
   										<td>${algo}</td>
									</tr>
									<tr>
										<th>${message3}</th>
   										<td>${code}</td>
									</tr>
									<tr>
   										<th>${message4}</th>
   										<td>
   										<select id="ddlsug">
	    										<c:forEach var="sug" items="${suggestList}">
	      											<option value="${sug}">${sug}</option>
	    										</c:forEach>
											</select>
   										</td>
									</tr>
								</table>
	            		 	</div>
	            		 </div>
	            	</div>
	        	</div>
	        </div>
        </form>
        <div id="footer">
			<img src="200px-SHSU.jpg" align="left" alt="logo" style="width:70px;height:70px">
			<h5>© 2016 | Design by Keerthi Koneru, Sam Houston State University</h5>
		</div>
    </body>
</html>