document.getElementById("list").click();
//fetch("http://localhost:8080/personen.json")
//	.then(answer => answer.json()) 	//Lamnda-Ausdruck statt Funktionsparameter
//	.then(json => cell.textContent = json.Personen[0].anrede); //Lamnda-Ausdruck statt Funktionsparameter

refresh();

function refresh(){
	fetch("http://localhost:8080/json/persons/all",
		{
			method: 'GET',
			headers: {
				'Content-Type': 'application/json'
			}
		})
	.then(getJson)     //Functional programming in JS
	.then(buildTable); //Functional programming in JS
}

function getJson(ServerResponse){
	return ServerResponse.json();
}

function buildTable(json) {
	var tabelle = document.getElementById("idtable");
	var i =0;
	
	//clean up table
	Array.prototype.slice.call(document.getElementsByTagName('tr')).forEach(
  		function(item) {
    	item.remove();
	});
	
	//build table completely new
	for (var element of json.persons) {		
		tabelle.insertAdjacentHTML("beforeend", 
			  `<tr>`
			+ 	`<th scope='row'>  ${i}   </th>`
			+	`<td> ${element.salutation}</td>`
			+	`<td> ${element.firstname}</td>`
			+	`<td> ${element.lastname}</td>`
			+	`<td> ${element.emailaddress}</td>`
			+	`<td> ${element.birthday}</td>`
			+	`<td><img src='${getIcon(element.salutation)}'></td>`
			+	`<td><img id='delete${i}'src='img/delete.jpeg' onclick='deletePerson(${i})'></td>`
			+ "</tr>"			
		);
		i++;
	}
}

function getIcon(anrede) {
	switch(anrede) {
  		case "Herr":
		case "MR":
		case "Mr":
		case "mr":
    		return 'img/mann.png'
  		case "Frau":
		case "Miss":
		case "Mrs":
		case "MISS":
		case "MRS": 
    		return 'img/frau.png'
  		default:
    		return 'img/frau.png'
	} 
}

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
	var email = document.getElementById("email");
	var emailaddress = email.value;
	var birthday = document.getElementById("birthday");
	var date = birthday.value;
	
	var json = `{"salutation":"${anrede}", "firstname":"${vorname}","lastname":"${nachname}", "emailaddress":"${emailaddress}", "birthday":"${date}"}`
	
	fetch("http://localhost:8080/json/person", 
		{
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: json
		}		
	);
	document.getElementById("list").click();
}

function deletePerson(id){
		var url = `http://localhost:8080/json/person/${id}` ;
		var meta = {method: 'DELETE'}
		fetch(url, meta).then(refresh);
}





