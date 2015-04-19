<!DOCTYPE html>
<%@page import="model.TeamSettings" import="java.util.*"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<title>xTrEAM - Members</title>
<!-- Bootstrap core CSS -->
<link href="css/bootstrap.css" rel="stylesheet">
<!-- Fontawesome core CSS -->
<link href="css/font-awesome.min.css" rel="stylesheet" />
<!--GOOGLE FONT -->
<link href='http://fonts.googleapis.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css'>
<!-- Timeline -->
<link href="css/timeline.css" rel="stylesheet" />
<!-- custom CSS here -->
<link href="css/style.css" rel="stylesheet" />
</head>
<body>
	<jsp:include page="menu.jsp"><jsp:param name="page"
			value="Members" />
	</jsp:include>
	<br>
	<br>
	<br>
	<br>
	<h2 align="center">Members of this TEAM</h2>
	<br>
	<br>
	<%
		for (String string : getMembersList(application)) {
	%>
	<h4 align="center"><%=string%></h4>
	<%
		}
	%>
	<br>
	<div align="center">
		<form action="memberAdder" method="post">
			<input type="text" name="member" placeholder="Member's userName"><br>
			<br> <input class="btn btn-default btn-xl" type="submit"
				value="Add">
		</form>
	</div>
</body>
</html>

<%!private ArrayList<String> getMembersList(ServletContext application) {
		TeamSettings settings = (TeamSettings) application
				.getAttribute("settings");
		return settings.getTeamMembers();
	}%>