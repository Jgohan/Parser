function sendString() {
    var input = document.getElementById('input');
    fetch('http://localhost:8080/parser', { method: 'POST', body: input.value }).then(console.log);
    input.value = '';
    alert('Your string was sent to the server');
}