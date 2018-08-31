const loadOrders = async (card_content) => {
    const pickupDate = $('#pickupDate').value;
    const storeId = $('#storeId').value;

    const orders = await fetchAsync({
        url: "/api/stores/" + storeId + "/orders?pickupDate=" + pickupDate,
        method: "get",
    });
    initialRenderOrders(orders);
};

const notEmptyTimeline = ()=> {
    if( hasClass($('#timeline-parent'),'empty-timeline')){
        removeClass($('#timeline-parent'),'empty-timeline');
    }
}

const initialRenderOrders = (orders) => {

    if(orders.length == 0){
        addClass($('#timeline-parent'),'empty-timeline');
        return;
     }
    const timedOrders = orders.reduce((timedOrders, cur) => {
        if(!timedOrders[cur.pickupTime]){
            timedOrders[cur.pickupTime] = [];
        }
        timedOrders[cur.pickupTime].push(cur);
        return timedOrders;
     }, {});


    const timelineHtml = ordersHtml = Object.keys(timedOrders).reduce(function(htmlMade, dateTime) {
        const ordersHtml = timedOrders[dateTime].reduce((accum, cur) => {
                return accum + orderDisplayHtmlTmp(cur);
            }, '');
        return htmlMade + timeEventHtmlTmp( dateTime , ordersHtml);
    }, '');
    $('#timeline-parent').insertAdjacentHTML('beforeend', timelineHtml);
    removeClass( $('#timeline-parent'), 'empty-timeline');

}

const timeEventHtmlTmp = (dataTime, orders)=>  (`<li class="event" data-date="${dataTime}"> ${orders} </li>`);

const orderDisplayHtmlTmp = ({ id, orderItems, name, phoneNumber, pickedup, pickupTime, orderTotalPrice, menuName }) => {
    const orderItemsHtml = orderItems.reduce((accum, cur) => {
        return accum + orderItemsHtmlTmp(cur);
    }, '');
    const offStyle = pickedup ? '' : 'off';
    return `
   <div class="card order-collection">
       <div class="card-content" data-id="${id}" data-pickup-hour="${pickupTime}">
            <div class="black-wrapper ${offStyle}">
                <span class="card-title activator white-text">수령완료</span>
                <p >
                    <button class="btn return-btn waves-effect waves-light" type="button" style="min-width: 100px;">
                        되돌리기
                               </button>
                </p>
            </div>
           <input type="hidden" name="isPickedup" value="${pickedup}" />
           <span class="card-title activator grey-text text-darken-4">
               수령인: ${name}
               <i class="material-icons right">more_vert</i></span>
           <div class="col s9">
               <div class="col s12  grey-text">
                   <label>전화번호 : </label>
                   ${phoneNumber}
               </div>
               <div class="col s12  grey-text">
                   <label>테이크아웃 시간 : </label>
                   ${pickupTime}
               </div>
           </div>
           <p >
               <button class="btn accept-btn waves-effect waves-light" type="button" style="min-width: 100px;">
                   수령하기

           </button>
           </p>

       </div>
       <div class="card-reveal">
         <span class="card-title grey-text text-darken-4">주문목록<i class="material-icons right">close</i></span>
         <table>
             <thead>
                 <tr>
                     <th>Menu Name</th>
                     <th>Item Count</th>
                     <th>Total Price</th>
                 </tr>
             </thead>
             <tbody>
                 ${orderItemsHtml}
             </tbody>
         </table>
     </div>
   </div>`;
};



const orderItemsHtmlTmp = ({ menuName, itemCount, itemTotalPrice }) =>
    (
        `<tr>
        <td>${menuName}</td>
        <td>${itemCount}</td>
        <td>${itemTotalPrice}</td>
    </tr>`
    );


const updatePickedUpStatus = async (card_content) => {
        const isPickedup = $('input[name=isPickedup]', card_content);
        const storeId = $('#storeId').value;
        const picked = await fetchAsync({
            url: "/api/stores/" + storeId + "/orders/" + card_content.getAttribute('data-id'),
            method: "POST",
            body: {"isPickedup": (isPickedup.value == 'true')}
        });

        console.log(picked);
        isPickedup.value = picked.isPickedup;
};
updateCurrentReservations = (order) => {
    notEmptyTimeline();
    const parent = $('.event[data-date="'+ order.pickupTime +'"]');
    if(!parent){
        const laterEvents = Array.from($All('.event')).filter( (n, i) => n.getAttribute('data-date') > order.pickupTime );
        const newOrderHtml = timeEventHtmlTmp( order.pickupTime , orderDisplayHtmlTmp(order));
         if( laterEvents.length == 0)
            $('#timeline-parent').insertAdjacentHTML('beforeend', newOrderHtml);
        else
            laterEvents[0].insertAdjacentHTML('beforebegin', newOrderHtml);
        return;
    }
    const newOrderHtml = orderDisplayHtmlTmp(order);
    parent.insertAdjacentHTML('beforeend', newOrderHtml);


}


document.addEventListener('DOMContentLoaded', function () {
    loadOrders();
    let socket = new SockJS('/orderCondition');
        let stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log("소켓 연결 성공", frame);
            const url = "/topic/stores/orders/" + $('#storeId').value;
            stompClient.subscribe(url, function (response) {
                console.log(JSON.parse(response.body));
                const order = JSON.parse(response.body);
                updateCurrentReservations(order);
            });
        });

    $('#show-order-collections').addEventListener('click', (event) => {

        if(event.target.nodeName == 'BUTTON'){
            const order_collection = event.target.closest('.order-collection');
            const card_content = $('.card-content', order_collection);
            const black_wrapper = $('.black-wrapper', order_collection);

            if (hasClass(event.target, 'accept-btn')) {
                //addClass(card_content, 'off');
                removeClass(black_wrapper, 'off');
            } else if (hasClass(event.target, 'return-btn')) {
                //removeClass(card_content, 'off');
                addClass(black_wrapper, 'off');
            }

            updatePickedUpStatus(card_content);
        }
    });

});
