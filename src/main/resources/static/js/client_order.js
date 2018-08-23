

const appendHtmlFromData = (dataArr, templateFunc, parentElement, btnName) => {
    const html = dataArr.reduce((accum, cur) => {
        cur.btnName = btnName;
        return accum + templateFunc(cur);
    }, '');
    parentElement.insertAdjacentHTML('beforeend', html);
};
const menuBoxHTML = ({ id, menu, maxCount = 0, personalMaxCount = 0, btnName }) => {
    const menuId = menu.id;
    const { imgUrl, name, description, price } = menu;
    const stringPrice = numberToLocaleString(price);
    return `<li class="collection-item" data-id="${id}" >
                <input type="hidden" name="menuId" min="0" max="100" value="${menuId}"/>
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
                                            <input type="text" class='ctrl__counter-num browser-default' name="amount" value="1"> </input>
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
const orderItemHTML = ({ id, amount, menu }) => {
    const { name, maxCount, personalMaxCount, price } = menu;
    const totalPrice = price * amount;
    return `<div class="card grey lighten-4" data-id="${id}">
    <div class="card-content">
        <span class="card-title">${name}</span>
        <p class="divider"></p>
        <p class="section"><span class="left order-amount">${amount}개</span>
            <span class="right"><span class="order-price">${totalPrice} </span>원</span>
        </p>
    </div>
</div>`;
}

class Reservation {
    constructor(wrapper, appendTargetParent, htmlTemplate) {
        this.wrapper = wrapper;
        this.appendTargetParent = appendTargetParent;
        this.htmlTemplate = htmlTemplate;
        this.orderItems = [];
    }
    async getOpenReservations(storeId) {
        const menuData = await fetchAsync({
            url: "/api/stores/" + storeId + "/reservations",
            method: "GET"
        });
        //$('.loading-wrapper').remove();
        if (menuData.length === 0) {
            $('.collection').insertAdjacentHTML('beforeend', nonMenu());
        }
        appendHtmlFromData(menuData, menuBoxHTML, $('.collection'), '추가');
        this.addData(menuData);
    };
    addData(reservations) {
        this.reservations = reservations.reduce((accum, cur) => { accum[cur.id] = cur; return accum; }, {});
    }
    updateTotalPrice(){
        const price = Array.from($All('.order-price', this.parentElement)).reduce( (accum, cur) => (accum + Number(cur.innerHTML)), 0);
        $('#totalPrice').value = price;
    }
    registerEvent() {
        this.wrapper.addEventListener('click', this.handleClickEvent.bind(this));
    }
    handleClickEvent(event) {
        if (event.target.nodeName === 'BUTTON') {
            console.log('click');
            //todo Refactor
            const parent = event.target.closest('.collection-item');
            const reservationId = parent.getAttribute('data-id');
            let reservationItem = this.reservations[reservationId];
            reservationItem.amount = $('input[name=amount]', parent).value;
            if(!this.orderItems.includes(reservationId)){
                this.appendTargetParent.insertAdjacentHTML('beforeend', this.htmlTemplate(reservationItem));
                this.updateTotalPrice();
                this.orderItems.push(reservationId);
                return;
            }
            const orderItem = $('[data-id="'+reservationId+'"]', this.appendTargetParent);
            $('.order-amount', orderItem).innerHTML = reservationItem.amount;
            $('.order-price', orderItem).innerHTML = reservationItem.amount * reservationItem.menu.price;
            this.updateTotalPrice();
            
        }
    }
}