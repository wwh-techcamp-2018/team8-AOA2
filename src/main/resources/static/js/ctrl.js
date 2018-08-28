class Ctrls {

    constructor(wrapper) {
        this.wrapper = wrapper;
        this.limit = { minValue: 1, maxValue: 9999999999 };
    }
    decrement() {
        this.changeValue(Number(this.targetInput.value) - 1);
    };

    increment() {
        this.changeValue(Number(this.targetInput.value) + 1);
    };

    initClass(eventTarget) {
        this.targetInput = $('.ctrl__counter-num', eventTarget.closest('.ctrl'));
        this.limit = {
            minValue: this.targetInput.getAttribute('data-min-value') || this.limit.minValue,
            maxValue: this.targetInput.getAttribute('data-max-value') || this.limit.maxValue,
        };
    };

    updatePrevValue(value) {
        this.targetInput.setAttribute('data-prev', value);
    };
    backToPrevValue(invalidValue) {
        if (this.targetInput.value == invalidValue)
            this.targetInput.value = this.targetInput.getAttribute('data-prev');
    };

    handleClickEvent(event) {
        if (hasClass(event.target, 'ctrl__button--increment')) {
            this.initClass(event.target);
            this.increment();
            return;
        }
        if (hasClass(event.target, 'ctrl__button--decrement')) {
            this.initClass(event.target);
            this.decrement();
            return;
        }
    };
    changeValue(nextNumber) {
        if (!this.validNextNumber(nextNumber)) {
            this.backToPrevValue(nextNumber);
            return;
        }
        this.targetInput.value = nextNumber;
        this.updatePrevValue(nextNumber);
    }
    validNextNumber(nextNumber) {
        return !isNaN(nextNumber)
            && nextNumber <= this.limit.maxValue
            && nextNumber >= this.limit.minValue;
    }
    handleChangeEvent(event) {
        if (hasClass(event.target, 'ctrl__counter-num')) {
            this.initClass(event.target);
            var parseValue = parseInt(event.target.value);
            this.changeValue(parseValue);
        }
    }
    registerEvent() {
        this.wrapper.addEventListener('click', this.handleClickEvent.bind(this));
        this.wrapper.addEventListener('change', this.handleChangeEvent.bind(this));
    }
}

export default Ctrls;