// document.addEventListener('DOMContentLoaded', function () {
//     $('input#input_text, textarea#textarea2').characterCounter();
// });
//
// function validated() {
//     if ($('.invalid')) {
//         $('.invalid').focus();
//         return false;
//     }
//     const emptyValidInputs = $All('.not-empty'); //Array.from($All('.not-empty'));
//     emptyValidInputs.forEach(e => {
//         if (e.value === '') {
//             e.classList.add('invalid');
//             e.focus();
//             return false;
//         }
//     });
//     return true;
// }
//
// const fetchAsync = ({url, method, body}) => (
//     fetch(url, {
//         method,
//         body: JSON.stringify(body),
//         headers: {'content-type': 'application/json'},
//         credentials: 'same-origin'
//     })
//         .then(res => {
//             if (!res.ok) {
//                 return;
//             }
//             return res.json();
//         })
// );

var file = document.querySelector('#getfile');

file.onchange = function () {
    var fileList = file.files ;

    // 읽기
    var reader = new FileReader();
    reader.readAsDataURL(fileList [0]);

    //로드 한 후
    reader.onload = function  () {
        //로컬 이미지를 보여주기
        document.querySelector('#preview').src = reader.result;

        //썸네일 이미지 생성
        var tempImage = new Image(); //drawImage 메서드에 넣기 위해 이미지 객체화
        tempImage.src = reader.result; //data-uri를 이미지 객체에 주입
        tempImage.onload = function() {
            //리사이즈를 위해 캔버스 객체 생성
            var canvas = document.createElement('canvas');
            var canvasContext = canvas.getContext("2d");

            //캔버스 크기 설정
            canvas.width = 100; //가로 100px
            canvas.height = 100; //세로 100px

            //이미지를 캔버스에 그리기
            canvasContext.drawImage(this, 0, 0, 100, 100);
            //캔버스에 그린 이미지를 다시 data-uri 형태로 변환
            var dataURI = canvas.toDataURL("image/jpeg");

            //썸네일 이미지 보여주기
            document.querySelector('#thumbnail').src = dataURI;

            //썸네일 이미지를 다운로드할 수 있도록 링크 설정
            document.querySelector('#download').href = dataURI;
        };
    };
};