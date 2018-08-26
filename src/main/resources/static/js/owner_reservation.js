
class OpenReservation {
    constructor(storeId, wrapper, callback = {callbackOnDelete : null }){
        this.storeId = storeId;
        this.wrapper = wrapper;
        this.openReservationBtn = $('#openReservationBtn');
        this.callback = callback;
    }
    registEvent(){
        this.wrapper.addEventListener('click', ((event) => {
            if (event.target.classList.contains('btn')) {
                this.deleteReservation(event.target);
            }
        }).bind(this));
        this.openReservationBtn.addEventListener('click', this.fetchOpenReservation.bind(this));

    };
    deleteReservation(elem){
        const forRemove = elem.closest('.collection-item');
        const menuId = $('input[name=menuId]', forRemove).value;
        addClass(forRemove, 'off');
        setTimeout(() => {
            forRemove.remove();
        }, 500);
        if(this.callback && this.callback.callbackOnDelete)
            this.callback.callbackOnDelete(menuId);
        };
    async fetchOpenReservation(){
        if(!this.validate()){
            return;
        }
        const data = this.buildRequestBody();
        const result = await fetchAsync({url: '/api/stores/' + this.storeId + '/reservations', method: 'post', body: data});
        console.log(result);
        document.location = result.data.url;
    }
    validate(){
        //todo validation
        return true;
    }
    buildRequestBody(){
        const timeArr = $('input[name=pickupTime]').value.split(':');
        const reservations = Array.from($All('.collection-item', this.wrapper));
        const reservationDTOs = reservations.reduce((accum, cur) => {
            const dto = {
                'menuId': $('input[name=menuId]', cur).value,
                'maxCount': $('input[name=maxCount]', cur).value,
                'personalMaxCount': $('input[name=personalMaxCount]', cur).value
            }
            accum.push(dto);
            return accum;

        }, []);
        return {'hourToClose': timeArr[0], 'minuteToClose': timeArr[1] , 'reservationDTOs' : reservationDTOs};
    }
    async getLastUsedMenus () {
        const menuData = await fetchAsync({
            url: '/api/stores/' + this.storeId + '/menus?condition=last',
            method: 'GET'
        });
        appendHtmlFromData(menuData, this.menuBoxHTML, this.wrapper, '삭제하기');
    };
    addMenu(menuItem){
        //todo append... 이것좀 고치자 ㅠㅠ
        menuItem.btnName = '삭제하기';
        const htmlTemplate = this.menuBoxHTML(menuItem);
        this.wrapper.insertAdjacentHTML('beforeend', htmlTemplate);
    };
    menuBoxHTML({id, imgUrl, name, description, price, btnName, maxCount = 0, personalMaxCount = 0}){
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
}
class Menu {
    constructor(storeId, wrapper, callback = {callbackOnAdd : null, callbackOnInit : null}) {
        this.storeId =  storeId;
        this.wrapper = wrapper;
        this.callback = callback;
        this.menus = {};
    };

    registerEvent() {
        this.wrapper.addEventListener('click', this.handleClickEvent.bind(this));
    };

    async getAllMenus(){
        const menuData = await fetchAsync({
            url: '/api/stores/' + this.storeId + '/menus',
            method: 'GET'
        });

        this.renderMenus(menuData);
        this.addData(menuData);
    };
    addData(menus) {
        this.menus = menus.reduce((accum, cur) => {
            accum[cur.id] = cur;
            return accum;
        }, {});
    };


    nonMenu() {
        return `<div class="loader-text">등록된 메뉴가 없습니다.<br> 메뉴를 등록해 주세요. </div>`;
    };
    menuAddBoxHTML({id, imgUrl, name, description, price, btnName, maxCount = 0, personalMaxCount = 0, lastUsed}, btnHtmlTemplate) {
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
                            ${btnHtmlTemplate}
                        </div>
                    </div>
                </li>`
    };
}

class MenuInModal extends Menu{

    renderMenus(menuData){
        if (menuData.length === 0) {
            this.wrapper.insertAdjacentHTML('beforeend', this.nonMenu());
            return;
        }
        appendHtmlFromDataArr(menuData, this.variableHtmlTemplate.bind(this), this.wrapper);
    };

    enableMenu(menuId){
        $('.collection-item[data-id="' + menuId + '"] button:disabled', this.wrapper).removeAttribute('disabled');
    };

    handleClickEvent(event) {
        if (event.target.nodeName === 'BUTTON') {
            console.log('click');
            const clickBtn = event.target;
            //disableMenu
            clickBtn.setAttribute('disabled', 'disabled');

            const menuId = clickBtn.closest('.collection-item').getAttribute('data-id');
            if(this.callback && this.callback.callbackOnAdd)
                this.callback.callbackOnAdd(this.menus[menuId]);

        }
    };

    addBtnTemplate( {lastUsed}){
        const disabled = lastUsed ? 'disabled':'';
        return `<button class="col s4 l3  btn waves-effect waves-light" ${disabled}
                type="button">추가하기
            </button>`;
    };
    variableHtmlTemplate(menu) {
        return this.menuAddBoxHTML(menu, this.addBtnTemplate(menu));
    };
}

class MenuInForm extends Menu {
    renderMenus(menuData){
        if(this.callback && this.callback.callbackOnInit)
            this.callback.callbackOnInit();
        if (menuData.length === 0) {
            this.wrapper.insertAdjacentHTML('beforeend', this.nonMenu());
            return;
        }
        appendHtmlFromData(menuData, this.variableHtmlTemplate.bind(this), this.wrapper, '삭제하기');
    };
    handleClickEvent(event) {
        if(event.target.classList.contains("btn")) {
            this.deleteMenu(event.target.closest(".collection-item"));
        }
    };
    async deleteMenu(removeMenuNode){
        const menu = await fetchAsync({
            url : "/api/menus/" + removeMenuNode.attributes["data-id"].value,
            method: "DELETE"
        });
        addClass(removeMenuNode, "off");
        setTimeout(() => {
            removeMenuNode.remove();
        },500);
    }
    deleteBtnTemplate(){
        return `<button class="col s4 l3  btn waves-effect waves-light"
                type="button">삭제하기
            </button>`;
    };
    variableHtmlTemplate(menu) {
        return this.menuAddBoxHTML(menu, this.deleteBtnTemplate());
    };

}
export {OpenReservation, MenuInModal, MenuInForm}