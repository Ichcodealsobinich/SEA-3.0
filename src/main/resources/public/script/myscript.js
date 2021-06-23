document.getElementById("list").click();
//fetch("http://localhost:8080/personen.json")
//	.then(answer => answer.json()) 	//Lamnda-Ausdruck statt Funktionsparameter
//	.then(json => cell.textContent = json.Personen[0].anrede); //Lamnda-Ausdruck statt Funktionsparameter

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

	var delimiter = "-------------------------------------------------------------";
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
			  `<tr>`
			+ 	`<th> ${element.id}   </th>`
			+	`<td> ${element.salutation}</td>`
			+	`<td> ${element.firstname}</td>`
			+	`<td> ${element.lastname}</td>`
			+	`<td> ${element.emailaddress}</td>`
			+	`<td> ${element.birthday}</td>`
			+	`<td><img class='icon' src='${getIcon(element.salutation)}'></td>`
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
	try {	
		fetch("/json/person", 
			{
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: json
			}		
		).then(checkResponse)
		.then(showSuccessMessage)
		.then(refresh)
		
	} catch(error) {
		showErrorMessage();
	}
}

function deletePerson(id){
		var url = `/json/person/${id}` ;
		var meta = {method: 'DELETE'}
		fetch(url, meta).then(refresh);
}

function checkResponse(response){
	if (response.status==404) {
		throw new Error('Error');
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

function showErrorMessage() {
	// Get the snackbar DIV
  var x = document.getElementById("snackbarBad");

  // Add the "show" class to DIV
  x.className = "show";

  // After 3 seconds, remove the show class from DIV
  setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}


const countParticipants = document.getElementById("fname");
countParticipants.addEventListener("click", checkCount);

function checkCount(){
	console.log("CheckCount aufgerufen");
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
  console.log("ShowHint aufgerufen");
  console.log(count);
  if (count > 9) {
	  console.log("ShowHint aufgerufen");
	  // Get the snackbar DIV
	  var x = document.getElementById("snackbarFull");
	
	  // Add the "show" class to DIV
	  x.className = "show";
	
	  // After 3 seconds, remove the show class from DIV
	  setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
   }
}










