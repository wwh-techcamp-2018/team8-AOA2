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
                console.log("id : " + res.id);
                let obj = {};
                obj['userId'] = res.id;
                fetch('/users/login', {
                    method: 'post',
                    headers: {"content-type": "application/json"},
                    body: JSON.stringify(obj),
                    credentials: 'same-origin'
                })
                    .then(displaySuccess);
            }
        }
    });
}

$('#submitBtn').addEventListener('click', (event) => {
    let formData = {};
    serializeArray($('form')).forEach(e => formData[e.name] = e.value);
    Kakao.API.request({
        url: '/v1/user/signup',
        data: {
            properties: {
                email: $("#user-email").value
            }
        },
        success: () => {
            fetch('/users/signup', {
                method: 'post',
                headers: {"content-type": "application/json"},
                body: JSON.stringify(formData),
                credentials: 'same-origin'
            })
                .then(displaySuccess);
        }
    });

    modal.close();

});
function displaySuccess(response) {
    console.log(response);
}

$('#logoutBtn').addEventListener('click', (event) => {
    Kakao.API.request({
        url: '/v1/user/logout',
        success: (res) => {
            alert("로그아웃 성공");
        },
        fail: (res) => {
            console.log(res);
            alert("에러입니다.");
        }
    });
})

$('#signoutBtn').addEventListener('click', (event) => {
    Kakao.API.request({
        url: '/v1/user/unlink',
        success: (res) => {
            alert("앱 탈퇴 성공");
        },
        fail: (res) => {
            console.log(res);
            alert("에러입니다.");
        }
    });
})