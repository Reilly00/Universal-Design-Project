// let aliveSecond = 0;
// let heartbeatRate = 2000;

// function time()
// {
// 	let d = new Date();
// 	var currentSecond = d.getTime();
// 	if(currentSecond - aliveSecond > heartbeatRate + 1000)
// 	{
// 		document.getElementById("connection_id").innerHTML="Dead";
// 	}
// 	else
// 	{			
// 		document.getElementById("connection_id").innerHTML="Alive";
// 	}
// 	setTimeout('time()', 1000);
// }

// function keepAlive()
// {
// 	fetch('/keep_alive')
// 	.then(response=>{
// 		if(response.ok){
// 			let date = new Date();
// 			aliveSecond = date.getTime();
// 			return response.json();
// 		}
// 		throw new Error("Server offline");
// 	})
// 	.then(responseJson => {
// 		if(responseJson.motion == 1){
// 			document.getElementById("motion_id").innerHTML = "Motion Detected";
// 		}
// 		else{
// 			document.getElementById("motion_id").innerHTML = "No Motion Detected";
// 		}
// 	})		 
// 	.catch(error=>console.log(error));
// 	setTimeout('keepAlive()', heartbeatRate);
// }


// function handleClick(cb){

// 	if(cb.checked)
// 	{
// 		value="on";
// 	}
// 	else
// 	{
// 		value="off";
// 	}
// 	sendEvent(cb.id + "-"+value);
// }

// function sendEvent(value)
// {
//  fetch("/status="+ value, 
//  {
//  method: "POST",
//  })
// }
