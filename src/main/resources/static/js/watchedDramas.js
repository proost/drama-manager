async function requestRemove() {
    let res = await request("http://localhost:8080/watched-dramas", "DELETE");
    alert(res);
}

function getCheckedCheckbox() {
    let checkboxes = Array.from(document.getElementsByClassName("form-check-input"));
    let dramaIds = [];
    checkboxes.forEach(checkbox => {
        if (checkbox.checked === true) {
            dramaIds.push({
                "dramaId": parseInt(checkbox.value)
            });
        }
    });
    return dramaIds;
}

async function request(url, method) {
    let dramaIds = getCheckedCheckbox();
    return fetch(url, {
        method: method,
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(dramaIds)
    }).then(res => res.text())
}

function redirect(dramaId) {
    window.location.href = `http://localhost:8080/watched-drama/${dramaId}`;
}

