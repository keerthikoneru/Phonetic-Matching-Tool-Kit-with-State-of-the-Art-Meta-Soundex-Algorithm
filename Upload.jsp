<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Phonetic Matching Algorithms</title>
<link rel="stylesheet" href="css/style2.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Merriweather"  type="text/css">
<link href='https://fonts.googleapis.com/css?family=Courgette' rel='stylesheet' type='text/css'>

<script type="text/javascript">
function validate() {
	if(document.getElementById('file1').value == "")
	{
		alert("Please upload the Files");
		return false;
	}
	if(document.getElementById('file2').value == "")
	{
		alert("Please upload the Second File");
		return false;
	}
	if(document.getElementById('dropdown').value == "Select")
	{
		alert("Please Select the language");
		return false;
	}
	
	file1name=document.getElementById('file1').value;
	file2name=document.getElementById('file2').value;
	file1ext=file1name.substr(file1name.lastIndexOf('.')+1);
	file2ext=file2name.substr(file2name.lastIndexOf('.')+1);
	
	
	if(((file1ext == 'csv') || (file1ext =='txt')) && ((file2ext == 'csv') || (file2ext =='txt')))
	{
		if(file1ext != file2ext)
		{
			alert("Both files should be of same format.Please check.");
			return false;
		}
	}		
	else
	{
		alert("Only text/csv files can be compared.Please upload them.");
		return false;
	}  	
}
</script>
</head>
<body>
<div id="header">
<a href="Index.html"><img src="css/home1.jpg" align="left" alt="logo" style="padding-left:10px; width:70px;height:65px"></a>
	<h2>Phonetic Matching Algorithms. Comparison and Correlation.</h2>
	
</div>
<form method="post" enctype="multipart/form-data" action="UploadServlet" onsubmit="return validate()">

	<div id="left">
		
	</div>
	
	<div id="center">
		<table class="ctr">
		<tr> 
		  <td>Select the language:</td>
		  <td><select name="dropdown" id="dropdown">
  			<option value="Select">--Select--</option>
  			<option value="English">English</option>
  			<option value="Spanish">Spanish</option>
			</select></td>
		</tr>
		<tr>
		<tr>
		  <td>Upload Reference File:</td>
		  <td><input type = "file" accept="text/csv" name = "file1" size="60" id="file1" value="1"/></td>
		</tr>
		<tr>
		  <td>Upload Incorrect File:</td>
		  <td><input type = "file" accept="text/csv" name = "file2" size="60" id="file2" value="2"/></td>
		</tr>
		<tr> 
		  <td></td>
		  <td><input id= "btn" style="height: 25px; width: 80px;" type = "submit" align="left" value = "Submit"/></td>
		</tr>
		<tr> 
		  <td colspan="2"> The tool accepts only files having extension .txt or .csv</td>
		</tr>
		</table>
	</div>
	
</form>

<div id="footer">
	<img src="200px-SHSU.jpg" align="left" alt="logo" style="width:70px;height:70px">
	<h5>© 2016 | Design by Keerthi Koneru, Sam Houston State University</h5>
</div>
</body>
</html>