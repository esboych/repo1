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
<br>
<a href = "remoteIncrement"> Remote Increment </a>

<br>
result here :
<div id=result>result here : </div>
<br>
<div id=msg>msg here : </div>
<br>
<div id=id>id here : </div>

 <script>
 var source=new EventSource("SSE");
 source.onmessage=function(event)
  {
  document.getElementById("result").innerHTML=event.data;
  };
  
  var complexSource=new EventSource("complexSSE");
  complexSource.onmessage=function(event)
   {
	  var jsonData = JSON.parse(event.data);
	  document.getElementById("msg").innerHTML=jsonData.msg;
	  document.getElementById("id").innerHTML=jsonData.id;
   };

  </script> 

</body>
</html>
