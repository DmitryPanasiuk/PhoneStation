var addressMismatch = document.getElementById("address_mismatch");
var blocking = document.getElementById("blocking_status");
var nameMismatch = document.getElementById("name_mismatch");
var wrongMessage = document.getElementById("wrongname");
var addSubscriber = document.getElementById("add_subscriber");
var removeSubscriber = document.getElementById("remove_subscriber");
var blockingSubscriber = document.getElementById("blocking_subscriber");
var time = document.getElementsByClassName("time");

setInterval(function () {
    time[0].innerHTML = new Date().toLocaleString()
}, 1000);

window.onload = function () {
    var profileText = document.getElementById("profile");
    profileText.innerHTML += myStorage.getItem("name");
}

var addressInput = document.getElementById("address");
addressInput.onclick = addressInputClick;

function addressInputClick() {
    addressMismatch.style.visibility = "hidden";
    addressInput.style.borderColor = "black";
}

var nameInput = document.getElementById("name");
nameInput.onclick = nameInputClick;

function nameInputClick() {
    wrongMessage.style.visibility = "hidden";
    nameMismatch.style.visibility = "hidden";
    nameInput.style.borderColor = "black";
}

var exitButton = document.getElementById("exit_menu");
exitButton.onclick = exitButtonClick;

function exitButtonClick() {
    myStorage.removeItem("name");
    window.location.href = "/index.html";
}

var addMenu = document.getElementById("add_subscriber_menu");
addMenu.onclick = addMenuClick;

function addMenuClick() {
    addSubscriber.style.display = "block";
    removeSubscriber.style.display = "none";
    blockingSubscriber.style.display = "none";
    nameInput.value = "";
    addressInput.value = "";
    wrongMessage.style.visibility = "hidden";
    nameInput.style.borderColor = "black";
}

var add = document.getElementById("add");
add.onclick = addSubscriberClick;

function addSubscriberClick() {
    var matchInfo = true;
    var regularName = /^[a-zа-яё]+( [a-zа-яё]+)?$/iu;
    var regularAddress = /^[a-zа-яё]+[- ]?[a-zа-яё]+,{1}[0-9]{1,3}-{1}[0-9]{1,4}$/iu;

    if (!regularName.test(nameInput.value)) {
        matchInfo = false;
        nameMismatch.style.visibility = "visible";
        nameInput.style.borderColor = "red";
    }

    if (!regularAddress.test(addressInput.value)) {
        matchInfo = false;
        addressMismatch.style.visibility = "visible";
        addressInput.style.borderColor = "red";
    }

    if (matchInfo) {
        var json = JSON.stringify({
            name: nameInput.value,
            address: addressInput.value,
            blocking: blocking.value,
            action: "add"
        });

        var httpRequest = new XMLHttpRequest();

        if (!httpRequest) {
            console.log('Unable to create XMLHTTP instance');
            return false;
        }

        httpRequest.open('POST', '/AdministratorServlet');
        httpRequest.responseType = 'text';
        httpRequest.send(json);

        httpRequest.onload = function () {
            if (httpRequest.status === 200) {
                if (httpRequest.response === "exist") {
                    wrongMessage.style.visibility = "visible";
                    nameInput.style.borderColor = "red";
                } else {
                    nameInput.value = "";
                    addressInput.value = "";
                    alert("Subscriber added");
                }
            }
        }
    }
}

var removeMenu = document.getElementById("remove_subscriber_menu");
removeMenu.onclick = removeMenuClick;

function removeMenuClick() {
    addSubscriber.style.display = "none";
    removeSubscriber.style.display = "block";
    blockingSubscriber.style.display = "none";
    var table = document.getElementById("subscribers_table_remove");

    var json = JSON.stringify({
        action: "getSubscribers"
    });

    var httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    httpRequest.open('POST', '/AdministratorServlet');
    httpRequest.responseType = 'json';
    httpRequest.send(json);

    httpRequest.onload = function () {
        if (httpRequest.status === 200) {
            var array = httpRequest.response;

            for (var i = table.rows.length - 1; i > 0; i--) {
                table.deleteRow(i);
            }

            for (var i = 0; i < array.length; i++) {
                var tr = document.createElement("tr");
                var tdId = document.createElement("td");
                var tdName = document.createElement("td");
                var tdAddress = document.createElement("td");
                var tdBlocking = document.createElement("td");
                var tdDelete = document.createElement("td");

                var id = document.createTextNode(array[i].id);
                var name = document.createTextNode(array[i].name);
                var address = document.createTextNode(array[i].address);
                var blocking = document.createTextNode(array[i].blocking);
                var deleteRow = document.createElement("button");
                var buttonText = document.createTextNode("Delete");
                var atr = document.createAttribute('onclick');
                atr.value = 'deleteRow(this);';
                deleteRow.appendChild(buttonText);
                deleteRow.setAttributeNode(atr);

                tdId.appendChild(id);
                tdName.appendChild(name);
                tdAddress.appendChild(address);
                tdBlocking.appendChild(blocking);
                tdDelete.appendChild(deleteRow);

                tr.appendChild(tdId);
                tr.appendChild(tdName);
                tr.appendChild(tdAddress);
                tr.appendChild(tdBlocking);
                tr.appendChild(tdDelete);

                table.appendChild(tr);

            }
        } else {
            console.log('Something went wrong..!!');
        }
    }
}

function deleteRow(btn) {
    var table = document.getElementById("subscribers_table_remove");
    var index = btn.parentNode.parentNode.rowIndex;

    var json = JSON.stringify({
        id: table.rows[index].cells[0].innerHTML,
        name: table.rows[index].cells[1].innerHTML,
        address: table.rows[index].cells[2].innerHTML,
        blocking: table.rows[index].cells[3].innerHTML,
        action: "deleteSubscribers"
    });

    var httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    httpRequest.open('POST', '/AdministratorServlet');
    httpRequest.responseType = 'json';
    httpRequest.send(json);

    httpRequest.onload = function () {
        if (httpRequest.status === 200) {
            table.deleteRow(index);
        }
    }
}

var blockingMenu = document.getElementById("blocking_menu");
blockingMenu.onclick = blockingMenuClick;

function blockingMenuClick() {
    addSubscriber.style.display = "none";
    removeSubscriber.style.display = "none";
    blockingSubscriber.style.display = "block";
    var table = document.getElementById("subscribers_table_blocking");

    var json = JSON.stringify({
        action: "getNegativeAccounts"
    });

    var httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    httpRequest.open('POST', '/AdministratorServlet');
    httpRequest.responseType = 'json';
    httpRequest.send(json);

    httpRequest.onload = function () {
        if (httpRequest.status === 200) {
            var array = httpRequest.response;

            for (var i = table.rows.length - 1; i > 0; i--) {
                table.deleteRow(i);
            }

            for (var i = 0; i < array.length; i++) {
                var tr = document.createElement("tr");
                var tdId = document.createElement("td");
                var tdName = document.createElement("td");
                var tdAddress = document.createElement("td");
                var tdLockStatus = document.createElement("td");
                var tdAccountNumber = document.createElement("td");
                var tdAccountState = document.createElement("td");
                var tdBlocking = document.createElement("td");

                var id = document.createTextNode(array[i].idSubscriber);
                var name = document.createTextNode(array[i].owner.name);
                var address = document.createTextNode(array[i].owner.address);
                var lockStatus = document.createTextNode(array[i].owner.blocking)
                var accountNumber = document.createTextNode(array[i].number);
                var accountState = document.createTextNode(array[i].state);
                var subscriberBlocking = document.createElement("button");
                var buttonText = document.createTextNode("Block");
                var atr = document.createAttribute('onclick');
                atr.value = 'blockSubscriber(this);';
                subscriberBlocking.appendChild(buttonText);
                subscriberBlocking.setAttributeNode(atr);

                tdId.appendChild(id);
                tdName.appendChild(name);
                tdAddress.appendChild(address);
                tdLockStatus.appendChild(lockStatus);
                tdAccountNumber.appendChild(accountNumber);
                tdAccountState.appendChild(accountState);
                tdBlocking.appendChild(subscriberBlocking);

                tr.appendChild(tdId);
                tr.appendChild(tdName);
                tr.appendChild(tdAddress);
                tr.appendChild(tdLockStatus);
                tr.appendChild(tdAccountNumber);
                tr.appendChild(tdAccountState);
                tr.appendChild(tdBlocking);

                table.appendChild(tr);
            }
        } else {
            console.log('Something went wrong..!!');
        }
    }
}

function blockSubscriber(btn) {
    var table = document.getElementById("subscribers_table_blocking");
    var index = btn.parentNode.parentNode.rowIndex;

    var json = JSON.stringify({
        id: table.rows[index].cells[0].innerHTML,
        name: table.rows[index].cells[1].innerHTML,
        address: table.rows[index].cells[2].innerHTML,
        blocking: table.rows[index].cells[3].innerHTML,
        action: "blockingSubscribers"
    });

    var httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    httpRequest.open('POST', '/AdministratorServlet');

    httpRequest.send(json);

    httpRequest.onload = function () {
        if (httpRequest.status === 200) {
            table.deleteRow(index);
        }
    }
}

var blockAll = document.getElementById("blockAll");
blockAll.onclick = blockAllClick;

function blockAllClick() {
    var table = document.getElementById("subscribers_table_blocking");

    var json = JSON.stringify({
        action: "blockAll"
    });

    var httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    httpRequest.open('POST', '/AdministratorServlet');
    httpRequest.responseType = 'json';
    httpRequest.send(json);

    httpRequest.onload = function () {
        if (httpRequest.status === 200) {
            for (var i = table.rows.length - 1; i > 0; i--) {
                table.deleteRow(i);
            }
        }
    }
}