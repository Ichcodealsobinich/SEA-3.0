document.getElementById("list").click();
//fetch("http://localhost:8080/personen.json")
//	.then(answer => answer.json()) 	//Lamnda-Ausdruck statt Funktionsparameter
//	.then(json => cell.textContent = json.Personen[0].anrede); //Lamda-Ausdruck statt Funktionsparameter

refresh();

function refresh(){
	try {
		fetch("/json/persons/all",
			{
				method: 'GET',
				headers: {
					'Content-Type': 'application/json'
				}
			})
		.then(checkResponse)     //Functional programming in JS
		.then(buildTable) 		 //Functional programming in JS
	} catch(error) {
		console.log(error);
	}
}


function buildTable(json) {
	
	//clean up table
	var tbody= document.getElementById("idbody");
	tbody.innerHTML="";
	
	//build table completely new
	var i = 0;

	var delimiter = "----------------------------------------------------------";
	for (var element of json.persons) {	
		if (i==10) {
			tbody.insertAdjacentHTML("beforeend", 
				`<tr><td bgcolor="#FFFFFF" style="line-height:15px;" colspan="8">&nbsp;</td></tr>`
			  	+ `<tr><th colspan="8">`
				+ delimiter
				+ `  Warteliste:  `
				+ delimiter
				+ `</th></tr>`		
			);
		}	
		tbody.insertAdjacentHTML("beforeend", 
			  `<tr id="row${i}">`
			+ 	`<th> ${element.id}   </th>`
			+	`<td> ${element.salutation}</td>`
			+	`<td> ${element.firstname}</td>`
			+	`<td> ${element.lastname}</td>`
			+	`<td> ${element.emailaddress}</td>`
			+	`<td> ${element.birthday}</td>`
			+	`<td> ${element.location}</td>`
			+	`<td><img class='icon' src='${getIcon(element.salutation)}'></td>`
			+	`<td><img class='icon' id='edit${element.id}'src='img/edit.png' onclick='editPerson("row${i}", ${element.id}, ${i}, ${element.version})' title='Bearbeiten'></td>`
			+	`<td><img class='icon' id='delete${element.id}'src='img/delete.jpeg' onclick='deletePerson(${element.id})' title='Entfernen'></td>`
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
    		return 'img/divers.jpeg'
	} 
}

var input = document.getElementById("button");
input.addEventListener("click", oninputclick);

async function oninputclick(event){
	event.preventDefault(); //prevents default behavior for submitbutton
	form=document.getElementById("form");
	if (!form.checkValidity()) {
		alert ("Bitte nur valide Eingaben abschicken");
	}else {
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
		var loc = document.getElementById("locations");
		var location = loc.value;
		
		var json = `{"salutation":"${anrede}", "firstname":"${vorname}","lastname":"${nachname}", "emailaddress":"${emailaddress}", "birthday":"${date}", "location":"${location}"}`	
		try {	
			fetch("/json/person", 
				{
					method: 'POST',
					headers: {
						'Content-Type': 'application/json'
					},
					body: json
				}		
			).then(checkPostResponse)
			.then(refresh)
			
		} catch(error) {
			showErrorMessage();
		}
	}

}

function deletePerson(id){
		var url = `/json/person/${id}` ;
		var meta = {method: 'DELETE'}
		fetch(url, meta).then(refresh);
}

function checkPostResponse(response){
	if (!response.ok) {
		showErrorMessage(response.statusText);
    }else {
		showSuccessMessage();
	}
    return response.json();
}

function checkPutResponse(response){
	if (!response.ok) {
		showErrorMessage();
    }else {
		showSuccessMessage();
	}
    return response.json();
}

function checkResponse(response){
	if (!response.ok) {
		throw new Error("Error");
    }
    return response.json();
}

function showSuccessMessage() {
	// Get the snackbar DIV
  var x = document.getElementById("snackbar");

  // Add the "show" class to DIV
  x.className = "show";

  // After 3 seconds, remove the show class from DIV
  setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}

function showErrorMessage(message) {
	// Get the snackbar DIV
  var x = document.getElementById("snackbarBad");

  // Add the "show" class to DIV
  x.className = "show";
  x.innerText= x.innerText + message;

  // After 3 seconds, remove the show class from DIV
  setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}


const countParticipants = document.getElementById("fname");
countParticipants.addEventListener("click", checkCount);

function checkCount(){
	var count = -1;
	try {
		fetch("/json/persons/count",
			{
				method: 'GET',
				headers: {
					'Content-Type': 'application/json'
				}
			})
		.then(result=>result.json()) 
		.then(showHint)     //Functional programming in JS											 //Functional programming in JS
	} catch(error) {
		console.log(error);
	}
	return count;
}

function showHint(count){	
  console.log(count);
  if (count > 9) {
	  // Get the snackbar DIV
	  var x = document.getElementById("snackbarFull");
	
	  // Add the "show" class to DIV
	  x.className = "show";
	
	  // After 3 seconds, remove the show class from DIV
	  setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
   }
}


function editPerson(row, id, counter, version) {
    var editRow = document.getElementById(row);
    var id = editRow.cells[0].innerHTML;
	var salut = editRow.cells[1].innerHTML;
	var firstname = editRow.cells[2].innerHTML;
	var lastname = editRow.cells[3].innerHTML;
	var email = editRow.cells[4].innerHTML;
	var birthday = editRow.cells[5].innerHTML;
	var location = editRow.cells[6].innerHTML;
	editRow.innerHTML = `<th> ${id}   </th>`
			+	`<td contenteditable> ${salut}</td>` 
			+	`<td contenteditable> ${firstname}</td>`
			+	`<td contenteditable> ${lastname}</td>`
			+	`<td contenteditable> ${email}</td>`
			+	`<td contenteditable> ${birthday}</td>`
			+	`<td contenteditable> ${location}</td>`
			+	`<td><img class='icon' src='${getIcon(salut)}'></td>`
			+	`<td><img class='icon' id='save${id}'src='img/save.png' onclick='savePerson("row${counter}", ${id}, ${counter}, ${version})' title='Speichern'></td>`
			+	`<td><img class='icon' id='delete${id}'src='img/delete.jpeg' onclick='deletePerson(${id})' title='Entfernen'></td>`;

}

function savePerson(row, id, counter, version) {
	var editRow = document.getElementById(row);
    var id = editRow.cells[0].innerHTML.trim();
	var salut = editRow.cells[1].innerHTML.trim();
	var firstname = editRow.cells[2].innerHTML.trim();
	var lastname = editRow.cells[3].innerHTML.trim();
	var email = editRow.cells[4].innerHTML.trim();
	var birthday = editRow.cells[5].innerHTML.trim();
	var location = editRow.cells[6].innerHTML.trim();
	editRow.innerHTML = `<th> ${id}   </th>`
			+	`<td> ${salut}</td>`
			+	`<td> ${firstname}</td>`
			+	`<td> ${lastname}</td>`
			+	`<td> ${email}</td>`
			+	`<td> ${birthday}</td>`
			+	`<td> ${location}</td>`
			+	`<td><img class='icon' src='${getIcon(salut)}'></td>`
			+	`<td><img class='icon' id='edit${id}'src='img/edit.png' onclick='editPerson("row${counter}", ${id}, ${counter}, ${version})' title='Speichern'></td>`
			+	`<td><img class='icon' id='delete${id}'src='img/delete.jpeg' onclick='deletePerson(${id})' title='Entfernen'></td>`;
	try {	
		var json = `{"id":"${id}", "version":"${version}", "salutation":"${salut}", "firstname":"${firstname}","lastname":"${lastname}", "emailaddress":"${email}", "birthday":"${birthday}", "location":"${location}"}`	
		fetch("/json/person", 
			{
				method: 'PUT',
				headers: {
					'Content-Type': 'application/json'
				},
				body: json
			}		
		).then(checkPutResponse)
		.then(showSuccessMessage)
		.then(refresh)
		
	} catch(error) {
		showErrorMessage();
	}	
}

function populateLocations(){

	try {
		fetch("/json/locations/",
			{
				method: 'GET',
				headers: {
					'Content-Type': 'application/json'
				}
			})
		.then(checkResponse)     //Functional programming in JS
		.then(buildDropdown) 		 //Functional programming in JS
	} catch(error) {
		console.log(error);
	}
}
const loc = document.getElementById("locations");
loc.addEventListener("click", populateLocations);

function buildDropdown(json){
	var dropdown = document.getElementById("standorte");
	dropdown.innerHTML="";
	for (var location of json) {
		dropdown.insertAdjacentHTML("beforeend", `<option value="${location}">`)
	}
}





