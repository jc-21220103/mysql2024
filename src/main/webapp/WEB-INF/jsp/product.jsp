<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JK3B04</title>
</head>

<%
   ArrayList<String[]> maker1= (ArrayList<String[]>) request.getAttribute("maker");
   
   ArrayList<String[]> product2 = (ArrayList<String[]>) request.getAttribute("product3");
%>

<body>
<FORM METHOD="GET" ACTION="./product">

<SELECT NAME="ID">

<% 
for (String[] ss : maker1) { 
%>
    <OPTION VALUE="<%= ss[0] %>">
        <%= ss[1] %>
    </OPTION>
<% 
} 
%>

</SELECT>
<INPUT TYPE="SUBMIT" VALUE="絞り込む"/>
</FORM>


<h1>プロダクト一覧表</h1>

<TABLE>
<% for (String[] ss : product2) {%>
     <TR>
         <TD> <%= ss[0] %></TD>
         <TD> <%= ss[1] %></TD>
         <TD> <%= ss[2] %></TD>
     </TR>
     <% } %>
</TABLE>
</body>
</html>
