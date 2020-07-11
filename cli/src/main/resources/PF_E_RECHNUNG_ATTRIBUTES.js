  (function () {
	  var z = document.querySelector("td  > div  > div  > div  > div  > div  > div > div > div > div > table > tbody");
	  z.getElementsByClassName("gwt-TextBox")[0].value = "templateId";
	  z.getElementsByClassName("gwt-TextBox")[2].value = "PF_ERechnung";
	  var buttons = document.getElementsByTagName("button");
	  document.getElementsByTagName("button")[10].click();
	  
	  z=document.querySelector("td  > div  > div  > div  > div  > div  > div > div > div > div > table > tbody");
	  z.getElementsByClassName("gwt-TextBox")[0].value = "emailAddress";
	  z.getElementsByClassName("gwt-TextBox")[2].value = "ipanema-test@otto.de";
	  var buttons = document.getElementsByTagName("button");
	  document.getElementsByTagName("button")[10].click();

	   z=document.querySelector("td  > div  > div  > div  > div  > div  > div > div > div > div > table > tbody");
	  z.getElementsByClassName("gwt-TextBox")[0].value = "uniqueMessageIdentifier";
	  z.getElementsByClassName("gwt-TextBox")[2].value = Math.floor(Math.random()*100000)+"testUniqueMessageIdentifier_positiv";
	  var buttons = document.getElementsByTagName("button");
	  document.getElementsByTagName("button")[10].click();

		z=document.querySelector("td  > div  > div  > div  > div  > div  > div > div > div > div > table > tbody");
		z.getElementsByClassName("gwt-TextBox")[0].value = "uniqueUserId";
		z.getElementsByClassName("gwt-TextBox")[2].value = "eb7bcea295b6bcafed62fe4fb252bad5361c344f";
		var buttons = document.getElementsByTagName("button");
		document.getElementsByTagName("button")[10].click();
	  
  })();
