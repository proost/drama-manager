async function requestUpdate(id) {
    let res = await request(id);
    alert(res);
}

async function request(id) {
    const URL = `http://localhost:8080/watched-drama/${id}`;
    return fetch(URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            "lastWatchedSeason": parseInt(document.getElementById("lastWatchedSeasonInput").value)
        })
    }).then(res => res.text())
}
