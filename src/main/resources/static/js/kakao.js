Kakao.init('b61fe85153207d259015754ef56d69ab');

var instances = M.Modal.init($("#get-info-modal"));

var modal = M.Modal.getInstance($("#get-info-modal"));

Kakao.Auth.createLoginButton({
    container: '#kakao-login-btn',
    success: function(authObj) {
        getInfo(authObj);
    },
    fail: function(err) {
        console.log(JSON.stringify(err));
    }
});


const getInfo = (authObj) => {
    Kakao.API.request({
        url: '/v2/user/me',
        success: function(res) {
            if (!res.has_signed_up) {
                $('#user-id').value = res.id;
                modal.open();
            } else {
                let obj = {};
                obj['uuid'] = res.id;
                fetchManager('/api/users/login', obj);
            }
        }
    });
}

document.addEventListener('DOMContentLoaded', function () {
    var counter = document.querySelectorAll('.input_counter');
    M.CharacterCounter.init(counter);

    $('#submitBtn').addEventListener('click', (event) => {
        kakaoSignUp();
        modal.close();
    });

    $('#logoutBtn').addEventListener('click', (event) => {
        kakaoLogout();
    });

    $('#signoutBtn').addEventListener('click', (event) => {
        kakaoUnlink();
    });
});

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

function kakaoSignUp () {
    const form = $('form');
    if (!validateForm(form))
        return;

    let formData = {};
    serializeArray(form).forEach(e => formData[e.name] = e.value);
    Kakao.API.request({
        url: '/v1/user/signup',
        data: {
            properties: {
                email: $("#user-email").value
            }
        },
        success: () => {
            fetchManager('/api/users/signup', formData);
        }
    });

}

function kakaoLogout() {
    Kakao.API.request({
        url: '/v1/user/logout',
        success: (res) => {
            let obj = {};
            obj['uuid'] = res.id;
            fetchManager('/api/users/logout', obj);
            console.log("로그아웃 성공");
        },
        fail: (res) => {
            console.log(res);
        }
    });
}

function kakaoUnlink() {
    Kakao.API.request({
        url: '/v1/user/unlink',
        success: (res) => {
            console.log("앱 탈퇴 성공");
        },
        fail: (res) => {
            console.log(res);
            console.log("에러입니다.");
        }
    });
}