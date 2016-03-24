<%-- 
    Document   : Search
    Created on : Mar 17, 2016, 1:50:53 PM
    Author     : WeeJing
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="MasterPage.jsp"/>
        <title>Search</title>
    </head>
    <body>
        <form action ="listProducts">
            type <br/>
            <input type ="text" name="type"> <br/>
            brand<br/>
            <input type ="text" name ="brand"> <br/>
            model <br/>
            <input type="text" name="model"><br/>
            Min Price <br/>
            <input type ="number" name="minPrice"><br/>
            Max Price <br/>
            <input type ="number" name ="maxPrice"><br/>
            <input type ="submit" name="submit" value ="submit">
           
        </form>
</html>
