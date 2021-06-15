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
	var birthday = document.getElementById("birthday");
	var date = birthday.value;
	
	var json = `{"salutation":"${anrede}", "firstname":"${vorname}","lastname":"${nachname}", "birthday":"${date}"}`
	
	fetch("http://localhost:8080/json/person", 
		{
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: json
		}
	);
}