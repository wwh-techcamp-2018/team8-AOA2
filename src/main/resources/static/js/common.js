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
    const test = res.json();
    return test;
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
const isEmpty = (str) => {
    return (str.length === 0 || !str.trim());
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
