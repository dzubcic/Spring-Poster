var main = document.querySelector("main");
var aside = document.querySelector("aside");
var form = document.querySelector("aside form");
var label = document.querySelector("aside label");
var error = document.querySelector("aside #error");
var image = document.querySelector("aside #img");
var title = document.querySelector("aside #title");
var count = 1;

function reset(){
	form.reset();
	aside.style.width = "0";
	label.innerHTML = "Choose image for upload";
	error.innerHTML = "";
}

document.querySelector("button").onclick =  function(){
	aside.style.width = "300px";
}

image.onchange = function(){
	if(this.files[0].size > 2002000){
		error.innerHTML = "Image too large";
		form.reset();
		return;
	}
	var text = this.value.split('\\');
	label.innerHTML = text[2];
}

document.querySelector("aside #cancel").onclick = function(){
	reset();
}

window.onscroll = function() {
	if(window.scrollY + window.innerHeight == document.body.scrollHeight
			|| window.scrollY + window.innerHeight - 40 == document.body.scrollHeight){
		document.body.scrollHeight += 1000;
		 var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function(){
				if (this.readyState == 4 && this.status == 200){
					var resp = this.responseText;
					if(resp.trim() == ""){
						window.onscroll = null;
					}
					main.innerHTML += resp;
					document.body.scrollHeight -= 1000;
				}
			}
			xhr.open("GET", "/more=" + (count++) * 3, true);
			xhr.send();
	 }
	        
};


document.querySelector("aside #post").onclick = function(e){
	e.preventDefault();
	if(image.value == '' || title.value == ''){
		error.innerHTML = "Title and image cannot be empty!";
		return;
	}
	var xhr = new XMLHttpRequest();
	var data = new FormData(form);
	xhr.onreadystatechange = function(){
		if (this.readyState == 4 && this.status == 200){
			var resp = this.responseText;
			switch(resp){
			case "e1":
				error.innerHTML = "Title and image name size must be betweeen 3 and 50 characters";
				break;
			case "e2":
				error.innerHTML = "Uploaded file is not image!";
				break;
			case "e3":
				error.innerHTML = "Uploaded image too large! (Max 2MB)";
				break;
			default:
				reset();
				main.innerHTML = resp + main.innerHTML;
				break;
			}
		}
	}
	xhr.upload.onprogress = function(ev){
		error.innerHTML = "Uploading: " + (Math.round((ev.loaded / ev.total) * 100)) + "%";
	}
	xhr.open("POST", "/", true);
	xhr.send(data);
}