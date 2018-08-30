const $ = (selector, context = document) => (
    context.querySelector(selector)
);

const $All = (selector, context = document) => (
    context.querySelectorAll(selector)
);

const generateJsonObject = form => (
    serializeArray(form).reduce((accum, cur) => {
        accum[cur.name]= cur.value;
        return accum;
    }, {})
);

const fetchAsync = async ({url, method, body}) => {
    const res = await fetch(url, {
        method,
        body: JSON.stringify(body),
        headers: {'content-type': 'application/json'},
        credentials: 'same-origin'
    });
    if(!res.ok){

        return;
    }
    //todo : 나중에 FIX
    return await res.json();
};



const serializeArray = form => {
    let field, l, s = [];
    if (typeof form == 'object' && form.nodeName == 'FORM') {
        const len = form.elements.length;
        for (var i = 0; i < len; i++) {
            field = form.elements[i];
            if (field.name && !field.disabled && field.type != 'file' && field.type != 'reset' && field.type != 'submit' && field.type != 'button') {
                if (field.type == 'select-multiple') {
                    l = form.elements[i].options.length;
                    for (j = 0; j < l; j++) {
                        if (field.options[j].selected)
                            s[s.length] = {name: field.name, value: field.options[j].value};
                    }
                } else if ((field.type != 'checkbox' && field.type != 'radio') || field.checked) {
                    s[s.length] = {name: field.name, value: field.value};
                }
            }
        }
    }
    return s;
};

//cross-browser
const hasClass = (el, className)=>{
    if (el.classList)
        return el.classList.contains(className)
    else
        return !!el.className.match(new RegExp('(\\s|^)' + className + '(\\s|$)'))
};

const addClass = (el, className)=>{
    if (el.classList)
        el.classList.add(className)
    else if (!hasClass(el, className)) el.className += " " + className
};

const removeClass = (el, className)=>{
    if (el.classList)
        el.classList.remove(className)
    else if (hasClass(el, className)) {
        var reg = new RegExp('(\\s|^)' + className + '(\\s|$)')
        el.className=el.className.replace(reg, ' ')
    }
};

const replaceClass = (el, preClassName, postClassName) => {
    removeClass(el, preClassName);
    addClass(el, postClassName);
};

const toggleClass = (elem, className)=>{
    var newClass = ' ' + elem.className.replace( /[\t\r\n]/g, ' ' ) + ' ';
    if (hasClass(elem, className)) {
        while (newClass.indexOf(' ' + className + ' ') >= 0 ) {
            newClass = newClass.replace( ' ' + className + ' ' , ' ' );
        }
        elem.className = newClass.replace(/^\s+|\s+$/g, '');
    } else {
        elem.className += ' ' + className;
    }
};

const numberToLocaleString = (number) => {
    return number.toLocaleString();
};

const localeStringToNumber = (string) => {
    return Number(string.replace(/[,원]/gi, ""));
};

/*
String.prototype.isEmpty = function () {
    return (this.length === 0 || !this.trim());
}
*/

//when using validation form
const isEmpty = (target) => {
    if( target.constructor === String )
        return (target.length === 0 || !target.trim());
    return Object.keys(target).length === 0 && target.constructor === Object;
}

const validateForm = (formEl) => {
    if (!formEl)
        return false;
    const requiredInputs = Array.from ($All(':required', formEl));

    requiredInputs.forEach( e => {
        if(isEmpty(e.value)){
            addClass(e, 'invalid');
        }
    });

    if($('.invalid', formEl)) {
        $('.invalid',formEl).focus();
        return false;
    }
    return true;
};

const formatDate = (date) => {
        var d = new Date(date),
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

        if (month.length < 2) month = '0' + month;
        if (day.length < 2) day = '0' + day;

        return [year, month, day].join('-');
};



function collapse() {
    if ($('#menu-cart').classList.contains('open')) {
        $('#menu-cart').classList.remove('open');
        return;
    }
    $('#menu-cart').classList.add('open');
}

function reduce() {
    if ($('#sidebar').classList.contains('reduce')) {
        $('#sidebar').classList.add('reduce-animate');
        setTimeout(() => {
            $('#sidebar').classList.remove('reduce-animate');
            $('#sidebar').classList.remove('reduce');
        }, 300);

        return;
    }

    $('#sidebar').classList.add('reduce-animate');
    setTimeout(() => {
        $('#sidebar').classList.remove('reduce-animate');
        $('#sidebar').classList.add('reduce');
    }, 300);
}

function reduce() {
    // if ($('#sidebar').classList.contains('reduce')) {
    //     $('#sidebar').classList.add('reduce-animate');
    //     setTimeout(() => {
    //         $('#sidebar').classList.remove('reduce-animate');
    //         $('#sidebar').classList.remove('reduce');
    //     }, 300);
    //
    //     return;
    // }
    //
    // $('#sidebar').classList.add('reduce-animate');
    // setTimeout(() => {
    //     $('#sidebar').classList.remove('reduce-animate');
    //     $('#sidebar').classList.add('reduce');
    // }, 300);

    $('#sidebar').focus();
}

const appendHtmlFromData = (dataArr, templateFunc, parentElement, btnName) => {
    const html = dataArr.reduce((accum, cur) => {
        cur.btnName = btnName;
        return accum + templateFunc(cur);
    }, '');
    parentElement.insertAdjacentHTML('beforeend', html);
};

const appendHtmlFromDataArr = (dataArr, templateFunc, parentElement) => {
    const html = dataArr.reduce((accum, cur) => {
        return accum + templateFunc(cur);
    }, '');
    parentElement.insertAdjacentHTML('beforeend', html);
};

const sleep = ms => new Promise(res => setTimeout(res, ms));

const formatPhoneNumber = phoneNumberString => {
    const cleaned = ('' + phoneNumberString).replace(/\D/g, '');
    let regexPhoneNumber = /^(\d{3})(\d{3})(\d{4})$/;
    if (cleaned.length == 11) {
        regexPhoneNumber = /^(\d{3})(\d{4})(\d{4})$/;
    }
    var match = cleaned.match(regexPhoneNumber);
    return (!match) ? null : '' + match[1] + '-' + match[2] + '-' + match[3];
};
