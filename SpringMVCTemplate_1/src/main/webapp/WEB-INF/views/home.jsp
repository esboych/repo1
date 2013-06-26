<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${giveMeServerTime}. </P>
<br>
the another attribute value: ${anotherAtt}
<br>
<a href = "hello"> Hello! </a>
<br>
${answerHello}
<br>
<a href = "/template1"> Back </a>
<br>
<a href = "#" id = "SSE_link"> SSE </a>
<br>
<a href = "#" id = "XHR_link"> XHR </a>
<br>
<a href = "getHi"> Hi as a static page </a>
<br>
<a href = "#" id ="sin"> Draw sin </a>

<div id = "result"></div>

<div id = "bar" style = " position: absolute; left:600px; top:400px; height:20px; width: 150px; background:red;" >the bar</div>

<script>
var j=0;
var delta = 0;
var deg=0;
var degDownCount = true;

(function() {
	  var requestAnimationFrame = window.requestAnimationFrame || window.mozRequestAnimationFrame ||
	                              window.webkitRequestAnimationFrame || window.msRequestAnimationFrame;
	  window.requestAnimationFrame = requestAnimationFrame;
	})();



var result = document.getElementById("result");
var SSE_link = document.getElementById("SSE_link");
SSE_link.onclick = function(){createSSE();}

var XHR_link = document.getElementById("XHR_link");
XHR_link.onclick = function(){launchScheduledXHR();}
//XHR_link.addEventListener("click", cXHR(), false);

var Sin_link = document.getElementById("sin");
//Sin_link.onclick = function(){setInterval("drawSin()",200);}
Sin_link.onclick = function(){requestAnimationFrame(drawSin);}

	function launchScheduledXHR(){
		setInterval("cXHR()", 400);
	}

	function cXHR(){	
//		document.getElementById("result").innerHTML = "XHR clicked " + j++ + " times";
		var XHR = new XMLHttpRequest();
		var params = 'name=' + encodeURIComponent("Pieter") + "&age=" + encodeURIComponent(27);
//		XHR.open("GET", "XHR?"+params, true);
		XHR.open("GET", "XHR?name=Mike&age=31", true);
		document.getElementById("result").innerHTML = "loading..";
		XHR.onreadystatechange = function() {
			  if (XHR.readyState == 4) {
				 document.getElementById("result").innerHTML = "";
			     if(XHR.status == 200) {
//			       alert(XHR.responseText);
					 var response = XHR.responseText;
			    	 result.innerHTML = " Answer: " + response + " , called " + j++ + " times";
			         }
			  }
			};
		XHR.send(null);
	}
	
	function createSSE(){	
	//	alert("SSE launched");
		var source = new EventSource("SSE");
		source.onmessage = function(event){	
		document.getElementById("result").innerHTML = event.data;
		}
	}
	
	
	function drawSin(){
		if(deg>89 & !degDownCount){
			degDownCount = true;
			delta = -2;
		}
		if(deg<1 & degDownCount){
			degDownCount = false;
			delta = 2;
		}		
		deg+=delta;
		var res = Math.sin(Math.PI * (deg/180));
		result.innerHTML = "sin is: " + res + " , deg is: " +deg + " degDownCount= " + degDownCount;
		document.getElementById("bar").style.height = (200 * res) + "px";
		document.getElementById("bar").style.top = 400 - (200 * res) + "px";
		document.getElementById("bar").innerHTML = res;
		requestAnimationFrame(drawSin);
	}
		
</script>
</body>
</html>
