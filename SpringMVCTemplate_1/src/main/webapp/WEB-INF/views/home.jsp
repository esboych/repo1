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
<a href = "#" id = "XHR_link"> Send bar movement info to server via XHR </a>
<br>
<a href = "getRemoteBar"> Get RemoteBar </a>
<br>
<a href = "#" id ="sin"> Draw sin! </a>

<div id = "result"></div>
<div id = "xResult"></div>
<div id = "timeResult"></div>

<div id = "bar" style = " position: absolute; left:600px; top:400px; height:20px; width: 150px; background:red;" >the bar</div>

<script>
var j=0;
var delta = 0;
var deg=0;
var degDownCount = true;
var res=0;
var XHR = new XMLHttpRequest();
var sentTime; // fix the time request send to calculate the interval
var deltaTime; // time difference

(function() {
	  var requestAnimationFrame = window.requestAnimationFrame || window.mozRequestAnimationFrame ||
	                              window.webkitRequestAnimationFrame || window.msRequestAnimationFrame;
	  window.requestAnimationFrame = requestAnimationFrame;
	})();



var result = document.getElementById("result");
var xResult = document.getElementById("xResult");
var timeResult = document.getElementById("timeResult");
var SSE_link = document.getElementById("SSE_link");
SSE_link.onclick = function(){createSSE();}

var XHR_link = document.getElementById("XHR_link");
XHR_link.onclick = function(){launchScheduledXHR();}
//XHR_link.addEventListener("click", cXHR(), false);

var Sin_link = document.getElementById("sin");
//Sin_link.onclick = function(){setInterval("drawSin()",200);}
Sin_link.onclick = function(){requestAnimationFrame(drawSin);}

	function launchScheduledXHR(){
		setInterval("cXHR()", 1000);
	}
	


	
	
	function cXHR(){	
//		document.getElementById("result").innerHTML = "XHR clicked " + j++ + " times";
//		XHR.abort();
//		var XHR = new XMLHttpRequest();
//		var params = 'name=' + encodeURIComponent("Pieter") + "&age=" + encodeURIComponent(27);
		
		xResult.innerHTML = "Server response: loading..";
		
		XHR.onreadystatechange  = function (e) {
		  if (XHR.readyState === 4) {
		    if (XHR.status === 200) {
		      console.log(XHR.responseText);
		      xResult.innerHTML = "Server response: " + XHR.responseText;
		      timeResult.innerHTML = "sentTime is " + sentTime + "Response round-trip is: " + (new Date().getTime() - sentTime) + " ms";
		    } else {
		      console.error(XHR.statusText);
		    }
		  }
		};
		var params = 'deg=' + encodeURIComponent(res);
		XHR.open("GET", "XHR?"+params, true);
		sentTime = new Date().getTime();
		XHR.send(null);
//		XHR.open("GET", "XHR?name=Mike&age=31", true);

//		XHR.onreadystatechange = function() { XHR.abort(); }
//        XHR.onreadystatechange = function() { // do nothing }

	
	}
	
	function createSSE(){	
	//	alert("SSE launched");
		var source = new EventSource("SSE_1");
		source.onmessage = function(event){	
		document.getElementById("result").innerHTML = event.data;
		}
	}
	
	
	function drawSin(){
		if(deg>89 & !degDownCount){
			degDownCount = true;
			delta = -.1;
		}
		if(deg<1 & degDownCount){
			degDownCount = false;
			delta = .1;
		}		
		deg+=delta;
		res = Math.sin(Math.PI * (deg/180));
		result.innerHTML = "sin is: " + res + " , deg is: " +deg + " degDownCount= " + degDownCount;
		document.getElementById("bar").style.height = (200 * res) + "px";
		document.getElementById("bar").style.top = 400 - (200 * res) + "px";
		document.getElementById("bar").innerHTML = res;
		requestAnimationFrame(drawSin);
	}
		
</script>
</body>
</html>
