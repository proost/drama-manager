async function requestRemove() {
    let res = await request("http://localhost:8080/reserved-dramas", "DELETE");
    alert(res);
}

async function requestRegisterToWatched() {
    let res = await request("http://localhost:8080/watched-dramas", "POST");
    alert(res);
}

function getCheckedCheckbox() {
    let checkboxes = Array.from(document.getElementsByClassName("form-check-input"));
    let dramaIds = [];
    checkboxes.forEach(checkbox => {
        if (checkbox.checked === true) {
            dramaIds.push({
                "id": parseInt(checkbox.value)
            });
        }
    })
    return dramaIds;
}

async function request(url, method) {
    let dramaIds = getCheckedCheckbox();
    return fetch(url, {
        method: method,
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(dramaIds)
    }).then(res => res.text());
}


