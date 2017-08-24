<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<meta charset="UTF-8"/>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>SpringPoster</title>
		<link rel="stylesheet" href="style1.css"/>
	</head>
	<body>
		<h1>Spring<span>Poster<span></span></span></h1>
		<main>
			<c:forEach items="${posts}" var = "post">
				<div class="post">
					<h2 id="${post.id}">${post.title}</h2>
					<img src="images/${post.imageName}"/>
				</div>
			</c:forEach>
		</main>
		<aside>
			<h2>MAKE A POST</h2>
			<p id="error"></p>
			<form method="post" enctype="multipart/form-data">
				<input type="text" name="title" placeholder="Title" id="title"/>
				<br/>
				<label for="img" title="Max image size is 2MB!">Choose image for upload</label>
				<input type="file" accept="image/*" name="img" id="img"/>
				<br/>
				<input type="submit" value="POST" id="post"/>
				<br/>
				<input type="reset" value="CANCEL" id="cancel"/>
			</form>
		</aside>
		<button>+</button>
	<script src="script.js"></script>
</body>
</html>