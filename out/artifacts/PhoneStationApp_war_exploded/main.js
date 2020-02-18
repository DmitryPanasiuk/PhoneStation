var wrongAccount = document.getElementById("wrongaccount");
var inputs = document.getElementsByClassName("account");
var signInButton = document.getElementById("signin");
signInButton.onclick = signInButtonPress;

var signInInput = document.getElementsByClassName("account");
for (var i = 0; i < signInInput.length; i++) {
    signInInput[i].onclick = hideWrongLoginMessage;
}

function hideWrongLoginMessage() {
    wrongAccount.style.visibility = "hidden";
    for (var i = 0; i < inputs.length; i++) {
        inputs[i].style.borderColor = "black";
    }
}

function signInButtonPress() {
    var json;
    var name = document.getElementById("name").value;
    var password = document.getElementById("password").value;

    json = JSON.stringify({
        name: name,
        password: password
    });

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log("Unable to create XMLHTTP instance");
        return false;
    }

    httpRequest.open('POST', '/Login');
    httpRequest.send(json);

    httpRequest.onload = function () {

        if (httpRequest.status === 401) {
            wrongAccount.style.visibility = "visible";
            for (var i = 0; i < inputs.length; i++) {
                inputs[i].style.borderColor = "red";
            }
        } else if (httpRequest.status === 200) {
            var myStorage = localStorage;
            myStorage.setItem("name", name);
            window.location.href = httpRequest.response;
        }
    }
}
