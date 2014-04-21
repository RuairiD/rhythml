var sock = new WebSocket("ws://127.0.0.1:8080");
			
sock.onopen = function(){
	setStatus('Connected!', "black");
}

sock.onclose = function(){
	setStatus('Disconnected', "red");
}

sock.onmessage = function(event)
{
	receiveText(event.data);
}

function sendText(txt)
{
	sock.send(txt);
}

function receiveText(txt)
{
	document.getElementById('responseText').value = txt;
}

function sendStop()
{
	sendText("bpm 200 sil = BD:|-| > sil");
}

function setStatus(string, color)
{
	document.getElementById('status').style.color = color; 
	document.getElementById('status').innerHTML = string;
}
	
$(document).ready(function()
{

	/*var codeMirror = CodeMirror.fromTextArea(
		document.getElementById('editorText'), 
		{
			value: "bpm 200\n\nBD = inst 34498\nSN = inst 82583\nCH = inst 75037\nOH = inst 104225\nCR = inst 203567",
			lineNumbers: true,
			mode:  "javascript"
		});*/
});