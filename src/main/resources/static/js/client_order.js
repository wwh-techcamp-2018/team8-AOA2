

const appendHtmlFromData = (dataArr, templateFunc, parentElement, btnName) => {
    const html = dataArr.reduce((accum, cur) => {
        cur.btnName = btnName;
        return accum + templateFunc(cur);
    }, '');
    parentElement.insertAdjacentHTML('beforeend', html);
};
const menuBoxHTML = ({ menu, maxCount = 0, personalMaxCount = 0, btnName }) => {
    const { id, imgUrl, name, description, price } = menu;
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
const orderItemHTML = ({reservationId, name, count, price }) => {
    return `<div class="card grey lighten-4" data-id="${reservationId}">
    <div class="card-content">
        <span class="card-title">${name}</span>
        <p class="divider"></p>
        <p class="section"><span class="left">${count}개</span>
            <span class="right">${price} 원</span>
        </p>
    </div>
</div>`;
}

class Reservation{
    constructor(wrapper, appendTargetParent, htmlTemplate){
        this.wrapper = wrapper;
        this.appendTargetParent = appendTargetParent;
        this.htmlTemplate = htmlTemplate;
    }
    getOpenReservations = async (storeId) => {
        const menuData = await fetchAsync({
            url: "/api/stores/" + storeId + "/reservations",
            method: "GET"
        });
        //$('.loading-wrapper').remove();
        if (menuData.length === 0) {
            $('.collection').insertAdjacentHTML('beforeend', nonMenu());
        }
        appendHtmlFromData(menuData, menuBoxHTML, $('.collection'), '추가');
        this.addData()
    };
    addData(reservations){
        this.reservations = reservations.reduce( (accum, cur) => { accum[cur.id] = cur; return accum;}, {});
    }
    registerEvent() {
        this.wrapper.addEventListener('click', this.handleClickEvent.bind(this));
    }
    handleClickEvent(event){
        if (event.target.nodeName === 'BUTTON') {
            console.log('click');
            //todo Refactor
            const reservationId = clickBtn.closest('.collection-item').getAttribute('data-id');
        
            const reservationItem = this.reservations[reservationId];
            this.appendTargetParent.insertAdjacentHTML('beforeend', this.htmlTemplate(reservationItem));
            this.appendTargetParent.insertAdjacentHTML('beforeend', html);
        }
    }
}