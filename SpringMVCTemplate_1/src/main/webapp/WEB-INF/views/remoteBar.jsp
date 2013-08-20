<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Remote Bar page!  
</h1>

<P>  The time on the server is ${giveMeServerTime}. </P>
<br>
the another attribute value: ${anotherAtt}
<br>
<a href = "hello"> Hello! </a>
<br>
${answerHello}

<a href = "#" id ="sin"> Draw sin </a>

<div id = "result"></div>

<div id = "bar" style = " position: absolute; left:600px; top:400px; height:20px; width: 150px; background:red;" >the bar</div>

<script>
var deg=0;
var j=0;

(function() {
	  var requestAnimationFrame = window.requestAnimationFrame || window.mozRequestAnimationFrame ||
	                              window.webkitRequestAnimationFrame || window.msRequestAnimationFrame;
	  window.requestAnimationFrame = requestAnimationFrame;
	})();



var result = document.getElementById("result");

var Sin_link = document.getElementById("sin");
Sin_link.onclick = function(){requestAnimationFrame(drawSin);}
	
	function drawSin(){	
	//	alert("SSE launched");
		var source = new EventSource("SSE_1");
		source.onmessage = function(event){	
		document.getElementById("result").innerHTML = "event data: " + event.data + " counter= " + j++;
		document.getElementById("bar").style.height = 200 * event.data + "px";
		document.getElementById("bar").style.top = 400 - (200 * event.data) + "px";
		}
	}
	

		
</script>
</body>
</html>
