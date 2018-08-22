const menuBoxHTML = ({imgUrl, name, description, price, btnName}) => {
    const stringPrice = numberToLocaleString(price);
    return `<li class="collection-item" >
                    <div class="valign-wrapper">
                        <div class="col s3 img-box">
                            <img class="responsive-img" src="${imgUrl}">
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

const nonMenu = () => {
    return `<div class="loader-text">등록된 메뉴가 없습니다.<br> 메뉴를 등록해 주세요. </div>`
}

const appendHtmlFromData = (dataArr, templateFunc, parentElement, btnName) => {
    const html = dataArr.reduce( (accum, cur) => {
        cur.btnName = btnName;
        return accum + templateFunc(cur);
    }, '');
    parentElement.insertAdjacentHTML('beforeend', html);
};

document.addEventListener('DOMContentLoaded', async ()  =>{
    //Ajax로 데이터 볼러와야함.
    addMenuForm(1);

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

const addMenuForm = async (storeId) => {
    const menuData = await fetchAsync({
        url : "/api/owner/1/menu",
        method: "GET"
    });
    $('.loading-wrapper').remove();
    if(menuData.length === 0) {
        $('.collection').insertAdjacentHTML('beforeend', nonMenu());
    }
    appendHtmlFromData(menuData, menuBoxHTML, $('.collection'), '삭제하기');
};
