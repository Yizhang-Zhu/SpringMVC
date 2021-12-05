<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>JAVA-fileUpload</title>
  <%
    String path=request.getContextPath();
    request.setAttribute("path", path);
  %>
</head>
<body style="background-color: skyblue">

<h1> 上传文件 </h1>

<br>
<hr>
<br>

<form action="/uploadFile/up" method="post" enctype="multipart/form-data">
  <table>
    <tr>
      <td>UploadFile:</td>
      <td>
        <input type="file" name="fileUP" />
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <input type="submit"/>
        <input type="reset" />
      </td>
    </tr>
  </table>
</form>
</body>
</html>
