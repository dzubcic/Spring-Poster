<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach items="${posts}" var = "post">
	<div class="post">
		<h2 id="${post.id}">${post.title}</h2>
		<img src="images/${post.imageName}"/>
	</div>
</c:forEach>