

const fetchOpenReservation = async() => {
    const storeId = $('input[name=storeId]').value;
    const timeArr = $('input[name=pickupTime]').value.split(':');
    let data = {'hourToClose': timeArr[0], 'minuteToClose': timeArr[1]};
    data.reservationDTOs = Array.from($All('.collection-item', $('#open-menu-collection'))).reduce((accum, cur) => {
        const dto = {
            'menuId': $('input[name=menuId]', cur).value,
            'maxCount': $('input[name=maxCount]', cur).value,
            'personalMaxCount': $('input[name=personalMaxCount]', cur).value
        }
        accum.push(dto);
        return accum;
    }, []);
    const result = await fetchAsync({url: '/api/stores/' + storeId + '/reservations', method: 'post', body: data});
    console.log(result);
    document.location = result.data.url;
}

const addMenuForm = async (storeId) => {
    const menuData = await fetchAsync({
        url: '/api/stores/' + storeId + '/menus?condition=last',
        method: 'GET'
    });
    //$('.loading-wrapper').remove();
    if (menuData.length === 0) {
        $('#menu-collection').insertAdjacentHTML('beforeend', nonMenu());
    }
    appendHtmlFromData(menuData, menuBoxHTML, $('#open-menu-collection'), '삭제하기');
};

const appendHtmlFromData = (dataArr, templateFunc, parentElement, btnName) => {
    const html = dataArr.reduce((accum, cur) => {
        cur.btnName = btnName;
        return accum + templateFunc(cur);
    }, '');
    parentElement.insertAdjacentHTML('beforeend', html);
};
const nonMenu = () => {
    return `<div class="loader-text">등록된 메뉴가 없습니다.<br> 메뉴를 등록해 주세요. </div>`
};
const menuBoxHTML = ({id, imgUrl, name, description, price, btnName, maxCount = 0, personalMaxCount = 0}) => {
    const stringPrice = numberToLocaleString(price);
    return `<li class="collection-item" >
                    <input type="hidden" name="menuId" min="0" max="100" value="${id}"/>
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
                            <div class="col s12 valign-wrapper">
                                 <div class="col s9">
                                    <div class="col s3"><span> 최대 수량 </span></div>
                                     <div class="col s9">
                                        <div class='ctrl'>
                                            <div class='ctrl__button ctrl__button--decrement'>&ndash;</div>
                                            <div class='ctrl__counter'>
                                                <input type="text" class='ctrl__counter-num browser-default' name="maxCount" value="${maxCount}"> </input>
                                            </div>
                                            <div class='ctrl__button ctrl__button--increment'>+</div>
                                        </div>
                                      </div>
                                      <div class="col s3"><span>  인당 최대 예약가능 수량 </span></div>
                                     <div class="col s9">
                                          <div class='ctrl'>
                                                <div class='ctrl__button ctrl__button--decrement'>&ndash;</div>
                                                <div class='ctrl__counter'>
                                                    <input type="text" class='ctrl__counter-num browser-default' name="personalMaxCount" value="${personalMaxCount}"> </input>
                                                </div>
                                                <div class='ctrl__button ctrl__button--increment'>+</div>
                                           </div>
                                      </div>
                                  </div>

                                <div class="col s3">
                                    <button class="btn waves-effect waves-light" type="button" style="min-width: 100px;">${btnName}</button>
                                </div>
                            </div>

                        </div>
                    </div>
                </li>`
};

const menuAddBoxHTML = ({id, imgUrl, name, description, price, btnName, maxCount = 0, personalMaxCount = 0, lastUsed}) => {
    const stringPrice = numberToLocaleString(price);
    const disabled = lastUsed ? 'disabled':'';
    return `<li class="collection-item" data-id="${id}" data-max-count="${maxCount}" data-person-max-count="${personalMaxCount}" >
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
                            <button class="col s4 l3  btn waves-effect waves-light" ${disabled}
                                    type="button">${btnName}
                            </button>

                        </div>
                    </div>
                </li>`
};


class Menu {
    constructor(wrapper, appendCallback, htmlTemplate) {
        this.wrapper = wrapper;
        this.appendCallback = appendCallback;
        this.htmlTemplate = htmlTemplate;
    }

    addData(menus) {
        this.menus = menus.reduce((accum, cur) => {
            accum[cur.id] = cur;
            return accum;
        }, {});
    }

    registerEvent() {
        this.wrapper.addEventListener('click', this.handleClickEvent.bind(this));
    }

    handleClickEvent(event) {
        if (event.target.nodeName === 'BUTTON') {
            console.log('click');
            const clickBtn = event.target;
            const menuId = clickBtn.closest('.collection-item').getAttribute('data-id');
            //todo Refactor
            appendHtmlFromData([this.menus[menuId]], menuBoxHTML, $('#open-menu-collection'), '삭제하기');
            clickBtn.setAttribute('disabled', 'disabled');
        }
    }
}