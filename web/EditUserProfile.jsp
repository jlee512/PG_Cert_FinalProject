<%--
  Created by IntelliJ IDEA.
  User: Julian
  Date: 02-Jun-17
  Time: 10:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="Javascript/form_security_validation.js"></script>
    <title>Edit User Profile</title>
</head>
<body>

<%--Phone Number input--%>
<label for="phone">mobile phone number</label>
<br>
<%--Note regex pattern tested using regexr.com --%>
<input type="text" id="phone" name="phone" placeholder="mobile phone number" onchange="checkForSpaces(this)"
       pattern="^(02){1}\d{1}(\s|-)?\d{3}(\s|-)?\d{4,5}$"
       title="Enter a valid New Zealand mobile phone number seperated by spaces only (e.g 021 123 4567)"/>

<br><br>

<%--Occupation input--%>
<label for="occupation">occupation</label>
<br>
<%--Note regex pattern tested using regexr.com --%>
<input type="text" id="occupation" name="occupation" placeholder="your occupation"
       onchange="checkForSpaces(this)"/>

<br><br>

<%--City input--%>
<label for="city">city</label>
<br>
<%--Note regex pattern tested using regexr.com --%>
<input type="text" id="city" name="city" placeholder="city"
       onchange="checkForSpaces(this)"/>

<br><br>

<label for="city">city</label>
<br>
<%--Note regex pattern tested using regexr.com --%>
<input type="text" id="city" name="city" placeholder="city"
       onchange="checkForSpaces(this)"/>

<br><br>

</body>
</html>
