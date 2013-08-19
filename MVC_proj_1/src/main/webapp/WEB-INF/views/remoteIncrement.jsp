<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Increment</title>
</head>
<body>
<h1>
	Increment Remotely!  
</h1>

<br>
<input type="button" onclick = "location.href = 'increment'" name="save" value="save" />
<br>
<form action="increment">
    <input type="submit" value="Go to Google">  
</form>

 <script>

 </script> 

</body>
</html>
