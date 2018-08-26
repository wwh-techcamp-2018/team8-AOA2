
class OpenReservation {
    constructor(wrapper, callback = {callbackOnDelete : null }){
        this.wrapper = wrapper;
        this.openReservationBtn = $('#openReservationBtn');
        this.callbackOnDelete = callback.callbackOnDelete;
        this.storeId =  $('input[name=storeId]').value|| $('#storeId').value; // todo get from ... ?

    }
    registEvent(){
        this.wrapper.addEventListener('click', ((event) => {
            if (event.target.classList.contains('btn')) {
                this.deleteReservation(event.target);
            }
        }).bind(this));
        this.openReservationBtn.addEventListener('click', this.fetchOpenReservation);

    };
    deleteReservation(elem){
        const forRemove = elem.closest('.collection-item');
        const menuId = $('input[name=menuId]', forRemove).value;
        addClass(forRemove, 'off');
        setTimeout(() => {
            forRemove.remove();
        }, 500);
        if(this.callbackOnDelete)
            this.callbackOnDelete(menuId);
        //$( '.collection-item[data-id="'+ menuId + '"] button:disabled', $('#menu-collection')).removeAttribute('disabled');
    };
    async fetchOpenReservation(){
        const storeId = $('input[name=storeId]').value;
        const timeArr = $('input[name=pickupTime]').value.split(':');
        let data = {'hourToClose': timeArr[0], 'minuteToClose': timeArr[1]};
        data.reservationDTOs = Array.from($All('.collection-item', this.wrapper)).reduce((accum, cur) => {
            const dto = {
                'menuId': $('input[name=menuId]', cur).value,
                'maxCount': $('input[name=maxCount]', cur).value,
                'personalMaxCount': $('input[name=personalMaxCount]', cur).value
            }
            accum.push(dto);

        });
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
    constructor(wrapper, callback = {callbackOnAdd : null}) {
        this.wrapper = wrapper;
        this.callbackOnAdd = callback.callbackOnAdd;
    };
    registerEvent() {
        this.wrapper.addEventListener('click', this.handleClickEvent.bind(this));
    };

    async getAllMenus(){
        const menuData = await fetchAsync({
            url: '/api/stores/' + $('#storeId').value + '/menus',
            method: 'GET'
        });

        this.renderMenus(menuData);
        this.addData(menuData);
    };
    renderMenus(menuData){
        if (menuData.length === 0) {
            this.wrapper.insertAdjacentHTML('beforeend', this.nonMenu());
            return;
        }
        appendHtmlFromData(menuData, this.menuAddBoxHTML, this.wrapper, '추가하기');
    };

    addData(menus) {
        this.menus = menus.reduce((accum, cur) => {
            accum[cur.id] = cur;
            return accum;
        }, {});
    };

    handleClickEvent(event) {
        if (event.target.nodeName === 'BUTTON') {
            console.log('click');
            const clickBtn = event.target;
           //disableMenu
            clickBtn.setAttribute('disabled', 'disabled');

            const menuId = clickBtn.closest('.collection-item').getAttribute('data-id');
            if(this.callbackOnAdd)
                this.callbackOnAdd(this.menus[menuId]);

        }
    };
    enableMenu(menuId){
        $('.collection-item[data-id="' + menuId + '"] button:disabled', this.wrapper).removeAttribute('disabled');
    };

    menuAddBoxHTML({id, imgUrl, name, description, price, btnName, maxCount = 0, personalMaxCount = 0, lastUsed}) {
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
    nonMenu() {
        return `<div class="loader-text">등록된 메뉴가 없습니다.<br> 메뉴를 등록해 주세요. </div>`;
    };


}

export {OpenReservation, Menu}