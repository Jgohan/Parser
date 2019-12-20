function sendString() {
    var stringInput = document.getElementById('stringInput');

    console.log('Your string was sent to the server');
    fetch('http://localhost:8080/parser', {
        method: 'POST',
        body: stringInput.value
    })
    .then(console.log);

    stringInput.value = '';
}

 function addTemplate() {
     var templateStringInput = document.getElementById('templateStringInput');
     var templateNameInput = document.getElementById('templateNameInput');

     var data = {
         templateString: templateStringInput.value,
         templateName: templateNameInput.value
     };

     console.log('Your add request was sent to the server');
     fetch('http://localhost:8080/template', {
         method: 'POST',
         headers: {
             'Content-Type' : 'application/json'
         },
         body: JSON.stringify(data)
     })
     .then(console.log);

     templateStringInput.value = '';
     templateNameInput.value = '';
 }

function updateTemplate() {
    var updateIdInput = document.getElementById('updateIdInput');
    var updateStringInput = document.getElementById('updateStringInput');
    var updateNameInput = document.getElementById('updateNameInput');

    var data = {
        id: updateIdInput.value,
        templateString: updateStringInput.value,
        templateName: updateNameInput.value
    };

    console.log('Your put request was sent to the server');
    fetch('http://localhost:8080/template', {
        method: 'PUT',
        headers: {
            'Content-Type' : 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(console.log);

    updateIdInput.value = '';
    updateStringInput.value = '';
    updateNameInput.value = '';
}

function deleteTemplate() {
    var idInput = document.getElementById('idInput');

    var data = {
        id: idInput.value
    };

    console.log('Your delete request was sent to the server');
    fetch('http://localhost:8080/template', {
        method: 'DELETE',
        headers: {
            'Content-Type' : 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(console.log);

    idInput.value = '';
}