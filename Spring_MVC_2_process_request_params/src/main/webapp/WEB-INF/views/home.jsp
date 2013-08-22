<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<style type="text/css">
	#chart{
		height:450px;
		width:3200px;
		top:250px;
		left:5px;
		background:	#eee;
		position:absolute;
	}
	.dot
	{
		height:5px;
		width:1px;
		top:400px;
		left:50px;
		background:	#3d3;
		position:absolute;
	}
	</style>
</head>
<body>
<h1>
	Sensor data 
</h1>

<div id = "result"></div>
<div id = "formattedDate">formattedDate</div>
<div id = "count">count</div>
<div id = "sens0">sens0</div>
<div id = "sens1">sens1</div>
<div id = "sens2">sens2</div>
<div id = "sens3">sens3</div>
<div id = "sens4">sens4</div>
<div id = "sens5">sens5</div>
<div id = "sens6">sens6</div>
<div id = "chart"></div>

<script>
var dotCoordinate = 5;
var oldValue=20;
var sens0, sens1;
function createDot(xBias)
	{
		var dot = document.createElement('div');
		dot.className = 'dot';
		dotCoordinate++;
		dot.style.left=dotCoordinate;
		dot.style.top=700 - (xBias);
		document.body.appendChild(dot);
	}	
		
</script>


<script>
 createDot(150);
 createDot(170);
 createDot(180);
 var source = new EventSource("SSE");
/* 
 source.onmessage = function(event){  //without JSON
//	 document.getElementById("result").innerHTML = "sensor data " + event.data;
	 var dataInt = parseInt(event.data);
	 dataInt=(dataInt-462000)/5;
	 if(dataInt<0) dataInt=100;
	 if(dataInt>oldValue) oldValue++;
	 if(dataInt<oldValue) oldValue--;
	 document.getElementById("result").innerHTML = "sensor data " + event.data + " uV" + " dataInt= " + dataInt + " oldValue=" + oldValue;
	 createDot(oldValue);
 }
 */ 
  source.onmessage = function(event){ 
	 var data = JSON.parse(event.data);
//	 document.getElementById("result").innerHTML = "sensor data " + data;
	 sens0 = parseInt(data.sens0);
	 sens1 = parseInt(data.sens1);
	 sens2 = parseInt(data.sens2);
	 sens3 = parseInt(data.sens3);
	 sens4 = parseInt(data.sens4);
	 sens5 = parseInt(data.sens5);
	 sens6 = parseInt(data.sens6);
	 document.getElementById("formattedDate").innerHTML = "Time on server is: " + data.formattedDate;
	 document.getElementById("count").innerHTML = "count= " + data.count;
	 document.getElementById("sens0").innerHTML = "sens0= " + sens0;
	 document.getElementById("sens1").innerHTML = "sens1= " + sens1;
	 document.getElementById("sens2").innerHTML = "sens2= " + sens2;
	 document.getElementById("sens3").innerHTML = "sens3= " + sens3;
	 document.getElementById("sens4").innerHTML = "sens4= " + sens4;
	 document.getElementById("sens5").innerHTML = "sens5= " + sens5;
	 document.getElementById("sens6").innerHTML = "sens6= " + sens6;
	 createDot(oldValue);
 }


</script>
</body>
</html>
