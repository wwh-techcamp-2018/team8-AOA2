document.addEventListener('DOMContentLoaded', function () {
    var elems = document.querySelectorAll('.sidenav');
    var instances = M.Sidenav.init(elems, {});

    $('#logoutBtn').addEventListener('click', (event) => {
        console.log(event);
        kakaoLogout();
    });

    $('#unregisterBtn').addEventListener('click', (event) => {
        kakaoUnlink();
    });
});