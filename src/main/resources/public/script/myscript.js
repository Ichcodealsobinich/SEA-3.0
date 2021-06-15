
//fetch("http://localhost:8080/personen.json")
//	.then(answer => answer.json()) 	//Lamnda-Ausdruck statt Funktionsparameter
//	.then(json => cell.textContent = json.Personen[0].anrede); //Lamnda-Ausdruck statt Funktionsparameter

fetch("http://localhost:8080/participants")
	.then(getJson)     //Functional programming in JS
	.then(buildTable); //Functional programming in JS

function getJson(ServerResponse){
	return ServerResponse.json();
}

function buildTable(json) {
	var tabelle = document.getElementById("idtable");
	var i =0;
	for (var element of json.Personen) {
		tabelle.insertAdjacentHTML("beforeend", 
			  "<tr>"
			+ 	`<th scope='row'>  ${i++}   </th>`
			+	`<td> ${element.anrede}</td>`
			+	`<td> ${element.vorname}</td>`
			+	`<td> ${element.nachname}</td>`
			+	`<td><img src='${getIcon(element.anrede)}'></td>`
			+ "</tr>"			
		);
	}
}

function getIcon(anrede) {
	switch(anrede) {
  		case "Herr":
    		return 'img/mann.png'
  		case "Frau":
    		return 'img/frau.png'
  		default:
    		return 'img/frau.png'
	} 
}




