<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<body>


<h1>파일 업로드 테스트</h1>


<form method="post" action="/upload" enctype="multipart/form-data">



    <label>파일:</label>

    <input type="file" name="image">



    <br><br>



    <input type="submit" value="upload">

</form>


</body>