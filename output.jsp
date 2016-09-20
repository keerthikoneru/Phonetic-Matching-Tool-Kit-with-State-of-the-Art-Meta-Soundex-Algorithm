<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="PhoneticMatching.WordSuggestions" import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Output</title>
<link rel="stylesheet" href="css/style3.css">
</head>
<body>
<div id="header">
<a href="Index.html"><img src="css/home1.jpg" align="left" alt="logo" style="padding-left:10px; width:70px;height:65px"></a>
	<h2>Phonetic Matching Algorithms. Comparison and Correlation. English</h2>
</div>
	
<div id="wrapper">
	<div id="mid">
		<h2>${requestScope.message1}</h2>
	</div>
	<div id="left">
		<h3>Metaphone</h3>
		<h3>${requestScope.message4}</h3>
		<h3>${requestScope.message5}</h3>
		<h3>${requestScope.message14}</h3>
		<br>
		<h3>Nysiis</h3>
		<h3>${requestScope.message12}</h3>
		<h3>${requestScope.message13}</h3>
		<h3>${requestScope.message18}</h3>
	</div>
	<div id="center">
		<h3>Soundex</h3>
		<h3>${requestScope.message6}</h3>
		<h3>${requestScope.message7}</h3>
		<h3>${requestScope.message15}</h3>
		<br>
		<h3>MetaSoundex</h3>
		<h3>${requestScope.message19}</h3>
		<h3>${requestScope.message20}</h3>
		<h3>${requestScope.message21}</h3>
		<br>
		<h4>Path for generated results:</h4>
		<h4 style="color:green">${requestScope.message22}</h4>
	</div>
	<div id="right">
		<h3>Caverphone</h3>
		<h3>${requestScope.message8}</h3>
		<h3>${requestScope.message9}</h3>
		<h3>${requestScope.message16}</h3>
		<br>
		<h3>Double_Metaphone</h3>
		<h3>${requestScope.message10}</h3>
		<h3>${requestScope.message11}</h3>
		<h3>${requestScope.message17}</h3>
	</div>
</div>
<div id="footer">
	<img src="200px-SHSU.jpg" align="left" alt="logo" style="width:70px;height:70px">
	<h3>Phonetic Matching Algorithms</h3>
</div>

</body>
</html>