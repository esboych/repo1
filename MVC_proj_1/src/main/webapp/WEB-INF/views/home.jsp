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
result here :
<div id=result>result here : </div>

 <script>
 var source=new EventSource("SSE");
 source.onmessage=function(event)
  {
  document.getElementById("result").innerHTML=event.data;
  };

  </script> 

</body>
</html>
