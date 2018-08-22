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