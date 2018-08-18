const $ = (selector, context = document) => (
    context.querySelector(selector)
)

const $All = (selector, context = document) => (
    context.querySelectorAll(selector)
)

const generateJsonObject = form => (
    serializeArray(form).reduce((accum, cur) => {
        accum[cur.name]= cur.value;
        return accum;
    }, {})
)

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
}
