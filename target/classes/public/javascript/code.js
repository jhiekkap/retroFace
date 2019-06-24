

function sayHello() {
    alert("hello there")
}

function likeMessage(id, profile) {
    console.log("LIKED", id, profile)
}

function commentMessage(id, profile) {
    console.log("COMMENTED", id, profile, document.getElementById("commentti").value)
}

function messageWrite(profile) {

    //console.log("helo")

    let content = document.getElementById("mesage").value
    console.log(profile, content)

//    let divi = document.createElement('div')
//    divi.className = "message"
//    let teksti = document.createTextNode(content)
//    divi.appendChild(teksti)
//    divi.innerHTML = "<button th:onclick='likeMessage([[${message.id}]], [[${user.profile}]])'>LIKETÄ</button>"
//    divi.innerHTML = "<p>helouta</p>"
//    divi.innerHTML = "teksti"
//    document.getElementById("messages").appendChild(p)
//    var viestit = document.getElementById("messages");    // Get the <ul> element to insert a new node
//    viestit.insertBefore(divi, viestit.childNodes[0]);
}