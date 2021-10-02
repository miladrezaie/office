$(document).ready(function(){
	

	$('.ch').on('change',function(event){
		event.preventDefault();
		var value = $('.ch').val();
			  $.getJSON('http://localhost:8080/findbyjob/'+value, function(data) {
				  var gencheckboxes = "";
				  for ( var i=0;i<data.length; i++) 
				      {
				           console.log(data[i].fullname);
				           gencheckboxes = gencheckboxes + '<input type="checkbox" name="users" value="'+ data[i].id +'">' + data[i].fullname + '<br></input>';
				      };
				  
				      var dwrap = document.getElementById('wrapperfortestnames');
				      dwrap.innerHTML=gencheckboxes; 
			  });
			  
		});	
	$('#txtsearch').keyup(function(e){
		e.preventDefault();
		var v = $('#txtsearch').val();
		window.location.replace('http://localhost:8080/members/?keyword='+v);
		
		
	});
});





















