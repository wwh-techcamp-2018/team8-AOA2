const menuBoxHTML = ({imgPath, name, description, price, btnName}) => {
    const stringPrice = numberToLocaleString(price);
    return `<li class="collection-item" >
                    <div class="valign-wrapper">
                        <div class="col s3 img-box">
                            <img class="responsive-img" src="${imgPath}">
                        </div>
                        <div class="col s9">
                            <div class="col s12 title-box">
                                ${name}
                            </div>
                            <div class="col s12 description-text grey-text">
                                ${description}
                            </div>
                            <div class="col s6 l4 price-box">
                                <span class="price">${stringPrice}</span>
                                <span class="won">원</span>
                            </div>
                            <button class="col s4 l3 offset-s2 offset-l5 btn waves-effect waves-light"
                                    type="button">${btnName}
                            </button>

                        </div>
                    </div>
                </li>`
};

const appendHtmlFromData = (dataArr, templateFunc, parentElement) => {
    const html = dataArr.reduce( (accum, cur) => {
        return accum + templateFunc(cur);
    }, '');
    parentElement.insertAdjacentHTML('beforeend', html);
};

document.addEventListener('DOMContentLoaded', function () {
    //Ajax로 데이터 볼러와야함.


    $('.collection').addEventListener('click', (event) => {
        if(event.target.classList.contains("btn")){
            const forRemove = event.target.closest('.collection-item');
            addClass(forRemove, "off");
            setTimeout(() => {
                forRemove.remove();
            },500);
        }
    });
});



//테스트용 메소드. Ajax 붙인후 삭제예정.
var test = () => {
    appendHtmlFromData(testdata, menuBoxHTML, $('.collection'));
}

var testdata = [
    {
        imgPath : "/images/macaron1.png",
        name : "테스트입니다",
        description : "잘되면 좋겠습니다",
        price : 3000,
        btnName : "되어라"
    }
];