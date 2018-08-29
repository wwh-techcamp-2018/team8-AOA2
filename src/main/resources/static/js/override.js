/*
$(document).ready(function () {
    // Text based inputs
    var input_selector = 'input[type=text], input[type=password], input[type=email], input[type=url], input[type=tel], input[type=number], input[type=search], input[type=date], input[type=time], textarea';

    // Add active if form auto complete
    $(document).on('change', input_selector, function () {
        if (this.value.length !== 0 || $(this).attr('placeholder') !== null) {
            $(this).siblings('label').addClass('active');
        }
        M.validate_field($(this));
    });

});

M.validate_field = function (object) {
    var hasLength = (object.attr('data-length') !== null || object.attr('data-min-length') !== null);
    var lenAttr = parseInt(object.attr('data-length'));
    var minLenAttr = parseInt(object.attr('data-min-length')) || 0;
    var len = object[0].value.length;

    var hasRegex = object.attr('data-regex') !== null;
    var regex = new RegExp(object.attr('data-regex'));
    var str = object[0].value;

    if (len === 0 && object[0].validity.badInput === false && !object.is(':required')) {
        if (object.hasClass('validate')) {
            object.removeClass('valid');
            object.removeClass('invalid');
        }
    } else {
        if (object.hasClass('validate')) {
            // Check for character counter attributes
            if(object.is(':valid')
                && (!hasLength || hasLength && len <= lenAttr && len >= minLenAttr)
                && (!hasRegex || hasRegex &&  str.match(regex))){
                object.removeClass('invalid');
                object.addClass('valid');
            }else {
                object.removeClass('valid');
                object.addClass('invalid');
            }
        }
    }
};
*/


class CustomInputValidator {
    constructor() {
        // Text based inputs
        const input_selector = 'input[type=text], input[type=password], input[type=email], input[type=url], input[type=tel], input[type=number], input[type=search], input[type=date], input[type=time], textarea';
        const inputArr = Array.from(document.querySelectorAll(input_selector));
        // Add active if form auto complete
        document.addEventListener('change', (function (event) {
            const elem = event.target;
            if (inputArr.includes(elem)) {
                this.validate_field(elem);
            }
            // if (elem.value.length !== 0 || elem.getAttribute('placeholder') !== null) {
            //     elem.siblings('label').addClass('active');
            // }

        }).bind(this));
        document.addEventListener('input', (function (event) {
            const elem = event.target;
            if (inputArr.includes(elem)) {
                this.customUpdateCounter(elem);
            }
            // if (elem.value.length !== 0 || elem.getAttribute('placeholder') !== null) {
            //     elem.siblings('label').addClass('active');
            // }

        }).bind(this));
    }

    customUpdateCounter(elem) {
        var maxLength = +elem.getAttribute('data-length'),
            minLength = +elem.getAttribute('data-min-length') || 0,
            actualLength = elem.value.length;
        this.isValidLength = actualLength <= maxLength && actualLength >= minLength;

        if (maxLength || minLength) {
            this._validateInput(elem);
        }
    };

    /**
     * Add validation classes
     */

    _validateInput(elem) {
        if (this.isValidLength && this.isInvalid) {
            this.isInvalid = false;
            removeClass(elem, 'invalid');
        } else if (!this.isValidLength && !this.isInvalid) {
            this.isInvalid = true;
            removeClass(elem, 'valid');
            addClass(elem, 'invalid');
        }
    };

    validate_field(object) {
        var hasLength = (object.getAttribute('data-length') !== null || object.getAttribute('data-min-length') !== null);
        var lenAttr = parseInt(object.getAttribute('data-length'));
        var minLenAttr = parseInt(object.getAttribute('data-min-length')) || 0;
        var len = object.value.length;

        var hasRegex = object.getAttribute('data-regex') !== null;
        var regex = new RegExp(object.getAttribute('data-regex'));
        var str = object.value;
        var regexSQL = /\b(ALTER|CREATE|DELETE|DROP|EXEC(UTE){0,1}|INSERT( +INTO){0,1}|MERGE|SELECT|UPDATE|UNION( +ALL){0,1})\b/i;
        var regexHTML = /<.*?>|<\/.*?>/g;
        if (len === 0 && !object.hasAttribute('required')) {
            if (hasClass(object, 'custom-validate')) {
                removeClass(object, 'valid');
                removeClass(object, 'invalid');
            }
        } else {
            if(str.match(regexSQL) || str.match(regexHTML)){
                removeClass(object, 'valid');
                addClass(object, 'invalid');
            }
            else if (hasClass(object, 'custom-validate')) {
                // Check for character counter attributes
                if (//object.hasAttribute('valid')&&
                     (!hasLength || hasLength && len <= lenAttr && len >= minLenAttr)
                    && (!hasRegex || hasRegex && str.match(regex))) {
                    removeClass(object, 'invalid');
                    addClass(object, 'valid');
                } else {
                    removeClass(object, 'valid');
                    addClass(object, 'invalid');
                }
            }
        }
    };
}


export { CustomInputValidator };