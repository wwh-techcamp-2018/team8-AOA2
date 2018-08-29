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
        success: (res) => {
            if (!res.has_signed_up) {
                $('#user-id').value = res.id;

                instances = M.Modal.init($("#get-info-modal"));
                modal = M.Modal.getInstance($("#get-info-modal"));
                modal.open();

            } else {
                (async (uuid) => {
                    console.log(uuid);
                    const response = await fetchAsync({
                        url : '/api/users/signin',
                        method : 'POST',
                        body : {
                            uuid : uuid,
                        },
                    });
                    document.location = response.url;
                })(res.id);
            }
        },
    });
};



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
            (async () => {
                const response = await fetchAsync({
                    url: '/api/users/signup',
                    method: 'POST',
                    body: formData,
                });
                modal.close();
                document.location = response.url;
            })();
        }
    });
};