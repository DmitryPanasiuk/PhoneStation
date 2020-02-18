var wrongCompare = document.getElementById("wrongcompare");
var newPassword = document.getElementById("new_password");
var confNewPassword = document.getElementById("conf_new_password");
var wrongAmount = document.getElementById("wrongamount");
var info = document.getElementById("info");
var services = document.getElementById("services");
var payment = document.getElementById("payment");
var name = myStorage.getItem("name");
var time = document.getElementsByClassName("time");

setInterval(function () {
    time[0].innerHTML = new Date().toLocaleString()
}, 1000);

window.onload = function () {
    var profileText = document.getElementById("profile");
    profileText.innerHTML += name;
}

var regInput = document.getElementsByClassName("pass_field");
for (var i = 0; i < regInput.length; i++) {
    regInput[i].onclick = hideWrongPassword;
}

function hideWrongPassword() {
    wrongCompare.style.visibility = "hidden";
    newPassword.style.borderColor = "black";
    confNewPassword.style.borderColor = "black";
}

var amountInput = document.getElementById("amount");
amountInput.onclick = hideWrongAmount;

function hideWrongAmount() {
    wrongAmount.style.visibility = "hidden";
    amountInput.style.borderColor = "black";
}

var exitButton = document.getElementById("exit_menu");
exitButton.onclick = exitButtonClick;

function exitButtonClick() {
    myStorage.removeItem("name");
    window.location.href = "/index.html";
}

var infoMenu = document.getElementById("info_menu");
infoMenu.onclick = infoMenuClick;

function infoMenuClick() {
    info.style.display = "block";
    services.style.display = "none";
    payment.style.display = "none";
    var json = JSON.stringify({
        name: name,
        infoType: "info"
    });

    var httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    httpRequest.open('POST', '/SubscriberServlet');
    httpRequest.responseType = 'json';
    httpRequest.send(json);

    httpRequest.onload = function () {
        if (httpRequest.status == 200) {
            var array = httpRequest.response;

            for (var i = 0; i < array.length; i++) {
                var servicesArray = array[i].services;
                var services = '';
                for (var j = 0; j < servicesArray.length; j++) {
                    services += servicesArray[j].name + ' ';
                }
                var table = document.getElementById('info_table');

                for (var i = table.rows.length - 1; i > 0; i--) {
                    table.deleteRow(i);
                }

                var row = table.insertRow(table.rows.length);
                var cell1 = row.insertCell(0);
                var cell2 = row.insertCell(1);
                var cell3 = row.insertCell(2);
                var cell4 = row.insertCell(3);
                var cell5 = row.insertCell(4);
                var cell6 = row.insertCell(5);
                var cell7 = row.insertCell(6);
                var cell8 = row.insertCell(7);

                var id = document.createTextNode(array[i].id);
                var name = document.createTextNode(array[i].name);
                var address = document.createTextNode(array[i].address);
                var block = document.createTextNode(array[i].blocking);
                var accountNumber = document.createTextNode(array[i].account.number);
                var monthPay = document.createTextNode(array[i].account.state);
                var balance = document.createTextNode(array[i].account.allFee);
                var servicesList = document.createTextNode(services);

                cell1.appendChild(id);
                cell2.appendChild(name);
                cell3.appendChild(address);
                cell4.appendChild(block);
                cell5.appendChild(accountNumber);
                cell6.appendChild(monthPay);
                cell7.appendChild(balance);
                cell8.appendChild(servicesList);
            }
        } else {
            console.log('Something went wrong..!!');
        }
    }
}

var changePass = document.getElementById("change_pass");
changePass.onclick = changePassClick;

function changePassClick() {
    var matchPassword = true;

    if (newPassword.value !== confNewPassword.value) {
        wrongCompare.style.visibility = "visible";
        newPassword.style.borderColor = "red";
        confNewPassword.style.borderColor = "red";
        matchPassword = false;
    }

    if (matchPassword) {
        var json = JSON.stringify({
            name: name,
            password: newPassword.value,
            confpassword: confNewPassword.value,
            infoType: "changePassword"
        });

        var httpRequest = new XMLHttpRequest();

        if (!httpRequest) {
            console.log('Unable to create XMLHTTP instance');
            return false;
        }

        httpRequest.open('POST', '/SubscriberServlet');
        httpRequest.send(json);

        httpRequest.onload = function () {
            if (httpRequest.status == 200) {
                newPassword.value = "";
                confNewPassword.value = "";
                alert("Password changed");
            }
        }
    }
}

var servicesMenu = document.getElementById("services_menu");
servicesMenu.onclick = servicesMenuClick;

function servicesMenuClick() {
    info.style.display = "none";
    services.style.display = "block";
    payment.style.display = "none";
    var json = JSON.stringify({
        name: name,
        infoType: "services"
    });

    var httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    httpRequest.open('POST', '/SubscriberServlet');
    httpRequest.responseType = 'json';
    httpRequest.send(json);

    httpRequest.onload = function () {
        if (httpRequest.status === 200) {
            var array = httpRequest.response;

            var table = document.getElementById('services_table');

            for (var i = table.rows.length - 1; i > 0; i--) {
                table.deleteRow(i);
            }

            for (var i = 0; i < array.length; i++) {
                var tr = document.createElement('tr');
                var tdChecked = document.createElement('td');
                var tdId = document.createElement('td');
                var tdName = document.createElement('td');
                var tdCost = document.createElement('td');

                var id = document.createTextNode(array[i].id);
                var ServiceName = document.createTextNode(array[i].name);
                var ServiceCost = document.createTextNode(array[i].cost);
                var checked = document.createElement('input');
                var atrChecked = document.createAttribute('checked');
                var atrType = document.createAttribute('type');

                if (array[i].checked.toString() === "true") {
                    checked.setAttributeNode(atrChecked);
                }

                atrType.value = 'checkbox';
                checked.setAttributeNode(atrType);

                tdChecked.appendChild(checked);
                tdId.appendChild(id);
                tdName.appendChild(ServiceName);
                tdCost.appendChild(ServiceCost);
                tr.appendChild(tdChecked);
                tr.appendChild(tdId);
                tr.appendChild(tdName);
                tr.appendChild(tdCost);

                table.appendChild(tr);
            }
        } else {
            console.log('Something went wrong..!!');
        }
    }
}

var changeServices = document.getElementById("change_services");
changeServices.onclick = changeServicesClick;

function changeServicesClick() {
    var table = document.getElementById('services_table');
    var array = [];
    for (var i = 1; i < table.rows.length; i++) {
        if (table.rows[i].cells[0].childNodes[0].checked) {
            var service = {
                id: table.rows[i].cells[1].innerHTML,
                name: table.rows[i].cells[2].innerHTML,
                cost: table.rows[i].cells[3].innerHTML
            };
            array.push(service);
        }
    }

    var response = {
        data: array,
        name: name,
        infoType: "change_services"
    };
    var json = JSON.stringify(response);

    var httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }

    httpRequest.open('POST', '/SubscriberServlet');
    httpRequest.responseType = 'json';
    httpRequest.send(json);

    httpRequest.onload = function () {
        if (httpRequest.status == 200) {
            alert("Data updated");
        }
    }
}

var paymentMenu = document.getElementById("payment_menu");
paymentMenu.onclick = paymentMenuClick;

function paymentMenuClick() {
    info.style.display = "none";
    services.style.display = "none";
    payment.style.display = "block";
}

var pay = document.getElementById("pay");
pay.onclick = payClick;

function payClick() {
    var regular = /^[1-9]\d*$/;
    if (!regular.test(amount.value)) {
        wrongAmount.style.visibility = "visible";
        amountInput.style.borderColor = "red";
    } else {
        var json = JSON.stringify({
            name: name,
            amount: amountInput.value,
            infoType: "pay"
        });

        var httpRequest = new XMLHttpRequest();

        if (!httpRequest) {
            console.log('Unable to create XMLHTTP instance');
            return false;
        }

        httpRequest.open('POST', '/SubscriberServlet');
        httpRequest.responseType = 'json';
        httpRequest.send(json);

        httpRequest.onload = function () {
            if (httpRequest.status === 200) {
                alert("Amount paid");
                amountInput.value = "";
            }
        }
    }
}