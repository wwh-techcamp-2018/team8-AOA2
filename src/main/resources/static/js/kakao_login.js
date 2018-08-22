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

                instances = M.Modal.init($("#get-info-modal"));
                modal = M.Modal.getInstance($("#get-info-modal"));
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

});

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