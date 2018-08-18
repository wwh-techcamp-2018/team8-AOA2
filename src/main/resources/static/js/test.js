document.addEventListener('DOMContentLoaded', function () {
    $('input#input_text, textarea#textarea2').characterCounter();
});

function validated() {
    if ($('.invalid')) {
        $('.invalid').focus();
        return false;
    }
    const emptyValidInputs = $All('.not-empty'); //Array.from($All('.not-empty'));
    emptyValidInputs.forEach(e => {
        if (e.value === '') {
            e.classList.add('invalid');
            e.focus();
            return false;
        }
    });
    return true;
}

const fetchAsync = ({url, method, body}) => (
    fetch(url, {
        method,
        body: JSON.stringify(body),
        headers: {'content-type': 'application/json'},
        credentials: 'same-origin'
    })
        .then(res => {
            if (!res.ok) {
                return;
            }
            return res.json();
        })
);

String.prototype.isEmpty = function () {
    return (this.length === 0 || !this.trim());
}