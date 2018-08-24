const menuBoxHTML = ({id, imgUrl, name, description, price, btnName}) => {
    const stringPrice = numberToLocaleString(price);
    return `<li class="collection-item" data-id="${id}">
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
                            <div class="col s8 l9 price-box">
                                <span class="price">${stringPrice}</span>
                                <span class="won">원</span>
                            </div>
                            <button class="col s4 l3  btn waves-effect waves-light"
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
    addMenuForm($("#storeId").value);

    var elems = document.querySelectorAll('.modal');
    var instances = M.Modal.init(elems);

    $('.collection').addEventListener('click', (event) => {
        if(event.target.classList.contains("btn")){
            deleteMenu(event.target.closest(".collection-item"));
        }
    });
});

const deleteMenu = async (removeMenuNode) => {

    const menu = await fetchAsync({
        url : "/api/menus/" + removeMenuNode.attributes["data-id"].value,
        method: "DELETE"
    });
    addClass(removeMenuNode, "off");
    setTimeout(() => {
        removeMenuNode.remove();
    },500);
}

const addMenuForm = async (storeId) => {
    const menuData = await fetchAsync({
        url : '/api/stores/'+storeId+'/menus',
        method: "GET"
    });
    $('.loading-wrapper').remove();
    if(menuData.length === 0) {
        $('.collection').insertAdjacentHTML('beforeend', nonMenu());
        return;
    }
    appendHtmlFromData(menuData, menuBoxHTML, $('.collection'), '삭제하기');
};