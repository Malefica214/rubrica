
//AGGIUNGE I VALORI ALLA TABELLA
function aggiungiPersona() {
    validation()
    var voce = {
        nome: document.getElementById("nome").value,
        cognome: document.getElementById("cognome").value,
        telefono: document.getElementById("numero").value
    }
    fetch('http://localhost:8080/api/v1/voce', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(voce)
    })
        .then(getUtenti)
        .then(valoriAggiunti => {
            document.getElementById("nome").value = '',
                document.getElementById("cognome").value = '',
                document.getElementById("numero").value = ''
        });
}
//STAMPA LA TABELLA E STAMPA I VALORI
function getUtenti() {
    let utenti = fetch('http://localhost:8080/api/v1/voci')
        .then(data => data.json())
        .then(valori => {
            var tb = document.getElementById("corpoTabella");
            tb.innerHTML = '';

            valori.forEach(function (value) {

                let tr = document.createElement('tr');
                let tdNo = document.createElement('td');
                let tdCoNo = document.createElement('td');
                let tdNu = document.createElement('td');
                let tdBut = document.createElement('td');

                tr.scope = 'row';

                tdNo.innerHTML = value.nome;
                tdCoNo.innerHTML = value.cognome;
                tdNu.innerHTML = value.telefono;
                tdBut.innerHTML = `
            <div class="btn-group" role="group" aria-label="Basic outlined example">
                <button onclick="deleteVoce(${value.id})" type="button" class="btn btn-outline-primary">DEL</button> 
                <button onclick="showEdit(${value.id})" type="button" class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#editModal">EDIT</button>
            </div>`;

                tr.appendChild(tdNo);
                tr.appendChild(tdCoNo);
                tr.appendChild(tdNu);
                tr.appendChild(tdBut)
                tb.appendChild(tr);
            })

        })
}
getUtenti();

//MODULO DI RICERCA
function searchVoce() {

    let cerca = document.getElementById('search').value;
    let url = 'http://localhost:8080/api/v1/search?key=' + cerca;

    if (cerca.value == null) {
        validation()
    }
    else{
        fetch(url)
            .then(alert('entrato nella fetch'))
            .then(response => response.json())
            .then(data => {
                tbR = document.getElementById("corpoTabella");
                tbR.innerHTML = '';
                data.forEach(value =>
                    document.getElementById('corpoTabella').innerHTML = '<td>' + value.nome + '</td>'
                    + '<td>' + value.cognome + '</td>' + '<td>' + value.telefono + '</td>'
                )
            })
        }
}

//FUNZIONE ELIMINA
function deleteVoce(id) {
    let idDel = 'http://localhost:8080/api/v1/voci/' + id;

    fetch(idDel, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    })
        .then(getUtenti);
    alert('Sei sicuro di voler elimanre il contatto?')
}

function showEdit(id) {

    let linkEdit = 'http://localhost:8080/api/v1/voci/' + id;
    let showVoce = fetch(linkEdit)
        .then(data => data.json())
        .then(risultato => {
            document.getElementById('nomeEdit').value = risultato.nome;
            document.getElementById('cognomeEdit').value = risultato.cognome;
            document.getElementById('telefonoEdit').value = risultato.telefono;
            document.getElementById('salvaValori').addEventListener('click', () => salvaValori(risultato.id))
        })
}

function salvaValori(id) {
    validation()

    var voceEdit = {
        nome: document.getElementById("nomeEdit").value,
        cognome: document.getElementById("cognomeEdit").value,
        telefono: document.getElementById("telefonoEdit").value
    }

        let linkSave = 'http://localhost:8080/api/v1/voci/' + id;
        let saveVoce = fetch(linkSave, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(voceEdit)
        })
            .then(getUtenti)
}

//BOOTSRAP VALIDATION FORM JS CODE
function validation() {
    'use strict'
    var forms = document.querySelectorAll('.needs-validation')            
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)
        })

}