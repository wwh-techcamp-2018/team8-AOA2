const serviceSignOut = async (uuid) => {
    const response = await fetchAsync({
        url : '/api/users/signout',
        method : 'POST',
        body : {
            uuid : uuid,
        },
    });

    document.location = response.url;
}


function kakaoLogout() {
    Kakao.API.request({
        url: '/v1/user/logout',
        success: (res) => {
            console.log(res);
            serviceSignOut(res.id);
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
            serviceSignOut(res.id);
        },
        fail: (res) => {
            console.log(res);
            console.log("에러입니다.");
        }
    });
}