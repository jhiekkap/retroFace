

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

    let divi = document.createElement('div')
    divi.className = "message"
    //let teksti = document.createTextNode(content)
    let liketys =  `<button onclick='likeMessage(4, ${profile})'>LIKETÄ</button>`
    let teksti = `<p>${content}</p>`
    let tiedot = `<span>Björn</span> <span>2019-05-03T13:45:29.720</span>`
//    divi.appendChild(liketys)
//    divi.innerHTML = "<button th:onclick='likeMessage([[${message.id}]], [[${user.profile}]])'>LIKETÄ</button>"
//    divi.innerHTML = "<p>helouta</p>"
//    divi.innerHTML = "teksti"
    // document.getElementById("messages").appendChild(p)
    //let liketys = `<button>push me</button>`
    divi.innerHTML = liketys + "<br/>" + tiedot + teksti
    //divi.appendChild(teksti)
    var viestit = document.getElementById("messages");
    viestit.insertBefore(divi, viestit.childNodes[0]);
}