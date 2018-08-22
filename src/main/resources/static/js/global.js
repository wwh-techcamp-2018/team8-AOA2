Kakao.init('b61fe85153207d259015754ef56d69ab');

function redirectPage(response) {
    document.location = response.data;
}

function fetchManager(url, obj) {
    fetch(url, {
        method: 'post',
        headers: {"content-type": "application/json"},
        body: JSON.stringify(obj),
        credentials: 'same-origin'
    })
        .then(response => response.json()).then(redirectPage);
}