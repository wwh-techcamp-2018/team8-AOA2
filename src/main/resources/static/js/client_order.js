class OrderItem {

    constructor(storeId, wrapper, callback = {callbackBeforeProcessOrder : null, callbackOnSubmitOrder : null}){
        this.storeId = storeId;
        this.orderBtn = $('#orderBtn');
        this.wrapper = wrapper;
        this.callback = callback;
        this.orderItems = [];

    };
    registerEvent(){
        this.wrapper.addEventListener('click', this.handleClickEvent.bind(this));
        this.orderBtn.addEventListener('click', this.moveStepForward.bind(this));
    };
    handleClickEvent(event){
        if (hasClass(event.target, 'delete')) {
            console.log('click');
            this.deleteOrderItem(event.target.closest('.card'));
        }
    };
    deleteOrderItem(orderItemElem){
        const reservationId = orderItemElem.getAttribute('data-id');
        this.orderItems.splice( this.orderItems.indexOf(reservationId), 1 );

        orderItemElem.remove();
        $('#totalPrice').innerHTML = Number($('#totalPrice').innerHTML ) - Number($('.order-price', orderItemElem).innerHTML);
        this.toggleOrderBtn();
    };
    toggleOrderBtn(){
        const hasDisabled = this.orderBtn.hasAttribute('disabled');
        const itemLength = this.orderItems.length;
        if( hasDisabled && itemLength > 0){
            this.orderBtn.removeAttribute('disabled');
        }
        else if(!hasDisabled && itemLength == 0){
            this.orderBtn.setAttribute('disabled', 'disabled');
        }
    }
    moveStepForward(){
        let stopMove = false;
        if(this.callback.callbackBeforeProcessOrder){
            stopMove = this.callback.callbackBeforeProcessOrder();
        }
        if(stopMove)
            this.submitOrder();
    };

    async submitOrder(){
        const result = await fetchAsync({
            url: "/api/stores/"+this.storeId+"/orders",
            method: "post",
            body: this.constructDTO(),
        });
        //document.location = result.data.url;
        if(this.callback.callbackOnSubmitOrder)
            this.callback.callbackOnSubmitOrder(result);
    };
    constructDTO(){
        const orderItemDTOs = Array.from($All('.card', this.wrapper)).reduce( (accum, cur) => {
            accum.push( { reservationId : cur.getAttribute('data-id'), itemCount: $('.order-amount', cur).innerHTML });
            return accum;
        }, []);

        return { name : $('input[name=name]').value,
                phoneNumber_1 : $('input[name=phoneNumber_1]').value,
                phoneNumber_2:  $('input[name=phoneNumber_2]').value,
                phoneNumber_3:  $('input[name=phoneNumber_3]').value,
                pickupTime: $('input[name=pickupTime]').value, //HH:mm
                orderItemDTOs: orderItemDTOs,
        };

    }
    addOrderItem(reservationItem){
        if(!this.orderItems.includes(reservationItem.id)){
            this.insertOrderItem(reservationItem);
            return;
        }
        this.updateOrderItem(reservationItem);
    }
    insertOrderItem(reservationItem){
        this.orderItems.push(reservationItem.id);

        this.wrapper.insertAdjacentHTML('beforeend', this.orderItemHTML(reservationItem));
        this.updateTotalPrice();
        this.toggleOrderBtn();
    }
    updateOrderItem({id, amount, menu}){
        const orderItem = $('[data-id="'+id+'"]', this.wrapper);
        $('.order-amount', orderItem).innerHTML = Number( $('.order-amount', orderItem).innerHTML) + Number( amount );
        $('.order-price', orderItem).innerHTML =  Number($('.order-price', orderItem).innerHTML ) + Number( amount * menu.price);
        this.updateTotalPrice();
    }
    updateTotalPrice(){
        const price = Array.from($All('.order-price', this.wrapper)).reduce( (accum, cur) => (accum + Number(cur.innerHTML)), 0);
        $('#totalPrice').innerHTML = price;
    };
    orderItemHTML({ id, amount, menu }){
        const { name, maxCount, personalMaxCount, price } = menu;
        const totalPrice = price * amount;
        return `<div class="card" data-id="${id}">
            <a class="btn-small btn-floating halfway-top right waves-effect waves-light mayac-light-blue"><i class="delete material-icons">clear</i></a>
            <div class="card-content">
                <span class="card-title">${name}</span>
                <p class="divider"></p>
                <p class="section"><span class="left"><span class="order-amount">${amount}</span> 개</span>
                    <span class="right"><span class="order-price">${totalPrice}</span> 원</span>
                </p>
            </div>
    </div>`;
    };
}

class Reservation {
    constructor(storeId, wrapper, callback = {callbackOnClick : null}) {
        this.storeId = storeId;
        this.wrapper = wrapper;
        this.callback = callback;
    }

    async getOpenReservations() {
        const menuData = await fetchAsync({
            url: "/api/stores/" + this.storeId + "/reservations",
            method: "GET"
        });
        this.renderOpenReservations(menuData);
        this.addData(menuData);
    };
    renderOpenReservations(menuData){
        appendHtmlFromData(menuData, this.menuBoxHTML, $('.collection'), '추가');
    }
    addData(reservations) {
        this.reservations = reservations.reduce((accum, cur) => { accum[cur.id] = cur; return accum; }, {});
    };

    registerEvent() {
        this.wrapper.addEventListener('click', this.handleClickEvent.bind(this));
    };

    handleClickEvent(event) {
        if (event.target.nodeName === 'BUTTON') {
            console.log('click');
            //todo Refactor

            const parent = event.target.closest('.collection-item');

            const reservationId = parent.getAttribute('data-id');
            let reservationItem = this.reservations[reservationId];
            reservationItem.amount = $('input[name=amount]', parent).value;

            if($('input[name=amount]', parent).value < 1) return;


            if(this.callback.callbackOnClick){
                this.callback.callbackOnClick(reservationItem);
            }
        }
    };


    menuBoxHTML({ id, menu, maxCount = 0, personalMaxCount = 0, btnName }){
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
                             <div class="col s9 valign-wrapper">
                                <div class="col s3"><span> 수량 </span></div>
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
            </li>`;
    };



}

export  {OrderItem, Reservation};