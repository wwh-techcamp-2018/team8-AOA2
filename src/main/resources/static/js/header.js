document.addEventListener('DOMContentLoaded', function () {
    var elems = document.querySelectorAll('.sidenav');
    var instances = M.Sidenav.init(elems, {});

    $('#logoutBtn').addEventListener('click', (event) => {
        kakaoLogout();
    });


});