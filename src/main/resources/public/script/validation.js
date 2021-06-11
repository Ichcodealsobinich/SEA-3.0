var input = document.getElementById("button");
input.addEventListener("click", oninputclick);

function oninputclick(event){
	event.preventDefault(); //prevents default behavior for submitbutton
	var fname = document.getElementById("fname");
	var vorname = fname.value;
	var lname = document.getElementById("lname");
	var nachname = lname.value;
	var salut = document.getElementById("salut");
	var anrede = salut.value;
	
	var json = `{"anrede":"${anrede}", "vorname":"${vorname}","nachname":"${nachname}"}`
	console.log(json);
	
	fetch("http://localhost:8080/submitPerson", 
		{
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: json
		}
	);
}