const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/socket?Authorization=Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ1c2VyMUBnbWFpbC5jb20iLCJpYXQiOjE2OTUyODUwODQsImV4cCI6MTY5NTg4OTg4NH0.Tz37qfacXUkgV5OoWqJG5XDUTd9ANO3J7A3zfKFIFot0jABuEKlWey6HQ-2ZoHRU',
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/greetings', (greeting) => {
        showGreeting(greeting.body);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    console.log(stompClient);
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    console.log('start')
    stompClient.publish({
        destination: "/app/direct-message",
        body: JSON.stringify({'receiverUserId': '123', 'content': $("#name").val()})
    });
    console.log('end')
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendName());
});
