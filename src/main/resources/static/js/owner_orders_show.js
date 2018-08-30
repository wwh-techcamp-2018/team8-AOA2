const loadOrders = async (card_content) => {
    const pickupDate = $('#pickupDate').value;
    const storeId = $('#storeId').value;

    const orders = await fetchAsync({
        url: "/api/stores/" + storeId + "/orders?pickupDate=" + pickupDate,
        method: "get",
    });


    console.log(orders);
    const ordersHtml = orders.reduce((accum, cur) => {
        return accum + orderDisplayHtmlTmp(cur);
    }, '');

    const timelinedOrdersHtml = timeEventHtmlTmp( '11:00', ordersHtml);
    $('#timeline-parent').insertAdjacentHTML('beforeend', ordersHtml);

};


const timeEventHtmlTmp = ({minHour}, orders)=>  (`<li class="event" data-date="${minHour}"> ${orders} </li>`);

const orderDisplayHtmlTmp = ({ id, orderItems, name, phoneNumber, pickedup, pickupTime, orderTotalPrice, reservation }) => {
    const orderItemsHtml = orderItems.reduce((accum, cur) => {
        return accum + orderItemsHtmlTmp(cur);
    }, '');
    const offStyle = pickedup ? 'off' : '';
    return `
   <div class="card order-collection">
       <div class="card-content ${offStyle}" data-id="${id}" data-pickup-hour="${pickupTime}">
            <div class="black-wrapper">
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
       </div>
   </div>`;
};



const orderItemsHtmlTmp = ({ reservation, itemCount, itemTotalPrice }) =>
    (
        `<tr>
        <td>${reservation.menu.name}</td>
        <td>${itemCount}</td>
        <td>${itemTotalPrice}</td>
    </tr>`
    );
