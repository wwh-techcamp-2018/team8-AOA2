Kakao.init('b61fe85153207d259015754ef56d69ab');

var instances = M.Modal.init($("#get-info-modal"));

var test = M.Modal.getInstance($("#get-info-modal"));
Kakao.Auth.createLoginButton({
    container: '#kakao-login-btn',
    success: function(authObj) {
        getInfo(authObj);
    },
    fail: function(err) {
        console.log(JSON.stringify(err));
        alert(JSON.stringify(err));
    }
});


const getInfo = (authObj) => {
    Kakao.API.request({
        url: '/v2/user/me',
        success: function(res) {
            console.log("Auth Obj : " + JSON.stringify(authObj));
            console.log("id : " + res.id);
            console.log(res);
            console.log("email : " + res.kaccount_email);
            console.log("properties : " + res.properties);
            console.log(res.properties);
            console.log("access_token : " + authObj.access_token);
            if (!res.has_signed_up) {
                test.open();
            }
        }
    });
}

$('#submitBtn').addEventListener('click', (event) => {
    let formData = new FormData();
    serializeArray($('form')).forEach(e => formData.append(e.name, e.value));
    console.log(formData);

    Kakao.API.request({
        url: '/v1/user/signup',
        data: {
            properties: {

                email: $("#user-email").value
            }
        },
        success: () => {
            Kakao.API.request({
                url: '/v2/api/talk/memo/send',
                data: {
                    template_id: 11791
                },
                success: (res) => {
                    console.log(res);
                    console.log("success");
                }
            });
        }
    });
    test.close();

});

$('#siginoutBtn').addEventListener('click', (event) => {
    Kakao.API.request({
        url: '/v1/user/unlink',
        success: (res) => {
            alert("탈퇴성공");
        },
        fail: (res) => {
            console.log(res);
            alert("에러입니다.");
        }
    });
});